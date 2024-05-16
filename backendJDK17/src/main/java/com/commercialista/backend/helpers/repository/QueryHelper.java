package com.commercialista.backend.helpers.repository;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.commercialista.backend.dto.exchange.WsMetaPaginazione;
import com.commercialista.backend.dto.exchange.WsRispostaPaginata;
import com.commercialista.backend.exception.OrdinamentoNonValidoException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;

public class QueryHelper {
    private Logger logger = LoggerFactory.getLogger(QueryHelper.class);

    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private Object request;
    private String lingua;
    private Class entityClass;
    private Pageable pageable;

    private Map<String, Expression> joinMap = new HashMap<>();

    public QueryHelper(EntityManager entityManager, Class entityClass, Object request, String lingua, Pageable pageable) {
        this.entityManager = entityManager;
        this.request = request;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.lingua = lingua;
        this.entityClass = entityClass;
        this.pageable = pageable;
    }

    public static String getDatabaseType(EntityManager entityManager) {
        EntityManagerFactory emf = entityManager.getEntityManagerFactory();
        Map<String, Object> emfProperties = emf.getProperties();
        String hibernateDialect = (String) emfProperties.get("hibernate.dialect");
        if (hibernateDialect.toLowerCase().contains("postgres")) {
            return "postgres";
        } else {
            return "oracle";
        }
    }


    public long count() {
        joinMap = new HashMap<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root root = criteriaQuery.from(entityClass);
        criteriaQuery.select(criteriaBuilder.countDistinct(root));
        List<Predicate> predicates = buildPredicates(root);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return (Long) entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    public static boolean hasValue(Object requestValue) {
        if (requestValue instanceof Collection) {
            Collection requestCollection = (Collection) requestValue;
            return requestCollection != null && !requestCollection.isEmpty();
        } else if (requestValue instanceof String) {
            String requestString = (String) requestValue;
            return requestString != null && !requestString.trim().isEmpty();
        } else {
            return requestValue != null;
        }
    }

    private Expression getPath(Root root, String fieldString, String joinString, String chiave) {
        Expression path = root;
        if (!joinString.equals("")) {
            if (joinMap.get(joinString) != null) {
                path = joinMap.get(joinString);
            } else {
                String[] joins = joinString.split("\\.");
                for (String join : joins) {
                    join = join.trim();
                    if (path instanceof Root) {
                        path = ((Root) path).join(join, JoinType.LEFT);
                    } else {
                        path = ((Join) path).join(join, JoinType.LEFT);
                    }
                }
                joinMap.put(joinString, path);
            }
        }
        if (!chiave.equals("")) {
            Join join = (Join) path;
            join.on(
                    criteriaBuilder.equal(join.get("lingua"), lingua),
                    criteriaBuilder.equal(join.get("chiave"), chiave)
            );
        }

        if (!fieldString.equals("")) {
            String[] fields = fieldString.split("\\.");
            for (String field : fields) {
                path = ((Path) path).get(field);
            }
        }
        if (!chiave.equals("")) {
            if (getDatabaseType(entityManager).equals("postgres")) {
                path = criteriaBuilder.function("LOWER", String.class, ((Path) path).get("valore"));
            } else {
                path = criteriaBuilder.function("LOWER", String.class, criteriaBuilder.function("TO_CHAR", String.class, ((Path) path).get("valore")));
            }
        }
        return path;
    }

    private Expression getId(Root root) {
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                return root.get(field.getName());
            }
            field.setAccessible(false);
        }
        for (Method method : entityClass.getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(Id.class)) {
                if (method.getName().startsWith("get")) {
                    String fieldName = Introspector.decapitalize(method.getName().substring(3));
                    return root.get(fieldName);
                } else if (method.getName().startsWith("is")) {
                    String fieldName = Introspector.decapitalize(method.getName().substring(2));
                    return root.get(fieldName);
                }
            }
            method.setAccessible(false);
        }
        throw new RuntimeException("Should never goes here, no Id defined");
    }


    private Predicate buildPredicate(Root root, QueryCriteria.Operator operator, Expression path, Object value) {
        if (path.getJavaType().equals(LocalDateTime.class) && value instanceof LocalDate) {
            path = criteriaBuilder.function(
                    "TO_DATE", Date.class,
                    criteriaBuilder.function("TO_CHAR", String.class, path, criteriaBuilder.literal("YYYY-MM-DD")),
                    criteriaBuilder.literal("YYYY-MM-DD")
            );
            value = java.sql.Date.valueOf((LocalDate) value);
        }
        switch (operator) {
            case EQ:
                return criteriaBuilder.equal(path, value);
            case NOT_EQ:
                return criteriaBuilder.notEqual(path, value);
            case IN:
                return path.in(((Collection) value).toArray());
            case LIKE:
                String stringValue = (String) value;
                return criteriaBuilder.like(criteriaBuilder.lower(path), "%" + stringValue.trim().toLowerCase() + "%");
            case LT:
                return criteriaBuilder.lessThan(path, (Comparable) value);
            case GT:
                return criteriaBuilder.greaterThan(path, (Comparable) value);
            case LTE:
                return criteriaBuilder.lessThanOrEqualTo(path, (Comparable) value);
            case GTE:
                return criteriaBuilder.greaterThanOrEqualTo(path, (Comparable) value);
            case NULL:
                return criteriaBuilder.isNull(path);
            case NOT_NULL:
                return criteriaBuilder.isNotNull(path);
        }
        return null;
    }

    private Predicate buildPredicate(Root root, QueryCriteria queryCriteria, Object value) {
        Expression path = getPath(root, queryCriteria.field(), queryCriteria.join(), queryCriteria.chiave());

        Predicate predicate = buildPredicate(root, queryCriteria.operator(), path, value);
        if (predicate != null) {
            if (queryCriteria.field2().length > 0) {
               Map<String, String> fieldJoinTable = Arrays.stream(queryCriteria.field2())
                        .collect(Collectors.toMap(campoTabellaJoin -> campoTabellaJoin.split(":")[0],
                                campoTabellaJoin -> campoTabellaJoin.split(":").length > 1 ?
                                        campoTabellaJoin.split(":")[1] : ""));
                for(Map.Entry<String, String> campoTabellaJoin : fieldJoinTable.entrySet()) {
                    path = getPath(root, campoTabellaJoin.getKey(), campoTabellaJoin.getValue(), queryCriteria.chiave());
                    Predicate predicate2 = buildPredicate(root, queryCriteria.operator(), path, value);
                    Predicate predicateOr = criteriaBuilder.or(predicate, predicate2);
                    predicate = predicateOr;
                }
            }

            return predicate;
        }

        if (value instanceof Boolean) {
            boolean booleanValue = (Boolean) value;
            if (booleanValue) {
                if (queryCriteria.operatorTrue() != null) {
                    return buildPredicate(root, queryCriteria.operatorTrue(), path, value);
                }
            } else {
                if (queryCriteria.operatorFalse() != null) {
                    return buildPredicate(root, queryCriteria.operatorFalse(), path, value);
                }
            }
        }
        return null;
    }

    private SortCriteria.SortBy getSortBy(SortCriteria sortCriteria, String field) {
        for (SortCriteria.SortBy sortBy : sortCriteria.fields()) {
            if (sortBy.name().equals(field)) {
                return sortBy;
            }
        }
        return null;
    }

    private List<Order> getOrders(Root root) {
        List<Order> orders = new ArrayList<>();
        SortCriteria sortCriteria = request.getClass().getAnnotation(SortCriteria.class);


        if (pageable == null) {
            pageable = PageRequest.of(0, Integer.MAX_VALUE);
        }

        for (org.springframework.data.domain.Sort.Order order : pageable.getSort()) {
            SortCriteria.SortBy sortBy = getSortBy(sortCriteria, order.getProperty());
            if (sortBy == null) {
                String ordinamentiPossibili = Arrays.stream(sortCriteria.fields())
                        .map(sortByItem -> sortByItem.name())
                        .collect(Collectors.joining(", "));
                throw new OrdinamentoNonValidoException(ordinamentiPossibili);
            }
            Expression path = getPath(root, sortBy.field(), sortBy.join(), sortBy.chiave());
            if (path.getJavaType().equals(String.class)) {
                path = criteriaBuilder.lower(path);
            }
            if (order.isAscending()) {
                orders.add(criteriaBuilder.asc(path));
            } else {
                orders.add(criteriaBuilder.desc(path));
            }
        }
        return orders;
    }

    private List<Predicate> buildPredicates(Root root) {
        try {
            List<Predicate> predicates = new ArrayList<>();

            for (Field field : request.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object requestValue = field.get(request);
                if (!hasValue(requestValue)) {
                    continue;
                }
                if (field.isAnnotationPresent(QueryCriteria.class)) {
                    QueryCriteria queryCriteria = field.getAnnotation(QueryCriteria.class);
                    predicates.add(buildPredicate(root, queryCriteria, requestValue));
                } else if (field.isAnnotationPresent(OrCondition.class)) {
                    OrCondition orCondition = field.getAnnotation(OrCondition.class);
                    predicates.add(criteriaBuilder.or(Arrays.stream(orCondition.fields())
                            .map(queryCriteria -> buildPredicate(root, queryCriteria, requestValue))
                            .toArray(Predicate[]::new)));
                }
                field.setAccessible(false);
            }
//            if (HasIdApplicazione.class.isAssignableFrom(entityClass) && datiSessioneUtente.getAnagraficaApplicazioneId() != null) {
//                Expression pathIdApplicazione = getPath(root, "idApplicazione", "", "");
//                predicates.add(criteriaBuilder.or(
//                    criteriaBuilder.equal(pathIdApplicazione, datiSessioneUtente.getAnagraficaApplicazioneId()),
//                    criteriaBuilder.isNull(pathIdApplicazione)
//                ));
//            }

            return predicates;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private CriteriaQuery getBaseQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Object.class);
        Root root = criteriaQuery.from(entityClass);
        //criteriaQuery.select(root).distinct(true);
        List<Order> orders = getOrders(root);
        List<Predicate> predicates = buildPredicates(root);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        if (orders.size() > 0) {
            List<Selection> selections = new ArrayList<>();
            selections.add(getId(root));
            for (Order order : orders) {
                selections.add(order.getExpression());
            }
            criteriaQuery.multiselect(selections).distinct(true);
        } else {
            criteriaQuery.multiselect(getId(root), getId(root)).distinct(true);
        }


        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        //criteriaQuery.groupBy(getGroupByFields(root, orders));
        criteriaQuery.orderBy(orders);
        return criteriaQuery;
    }

    public List getItems() {
        CriteriaQuery idCriteriaQuery = getBaseQuery();

        List idsSort = entityManager
                .createQuery(idCriteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize()).getResultList();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root root = criteriaQuery.from(entityClass);
        CriteriaBuilder.In inClause = criteriaBuilder.in(getId(root));
        for (Object id : idsSort) {
            inClause.value(((Object[]) id)[0]);
        }
        criteriaQuery.where(inClause);
        List ids = idsSort.stream()
                .map(id -> ((Object[]) id)[0])
                .toList();
        List results = entityManager.createQuery(criteriaQuery).getResultList();
        results.sort(
                (a, b) -> ids.indexOf(getIdValue(a)) - ids.indexOf(getIdValue(b))
        );
        return results;
    }

    private Object getIdValue(Object obj) {
        for (Field field : entityClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                try {
                    return field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for (Method method : entityClass.getDeclaredMethods()) {
            method.setAccessible(true);
            if (method.isAnnotationPresent(Id.class)) {
                if (method.getName().startsWith("get")) {
                    try {
                        String fieldName = Introspector.decapitalize(method.getName().substring(3));
                        Field fieldId = entityClass.getDeclaredField(fieldName);
                        fieldId.setAccessible(true);
                        return fieldId.get(obj);
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else if (method.getName().startsWith("is")) {
                    try {
                        String fieldName = Introspector.decapitalize(method.getName().substring(2));
                        Field fieldId = entityClass.getDeclaredField(fieldName);
                        fieldId.setAccessible(true);
                        return fieldId.get(obj);
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            method.setAccessible(false);
        }
        throw new RuntimeException("Should never goes here, no Id defined");

    }


    public static List getAll(EntityManager entityManager, Class entityClass, Object request, String lingua, Pageable pageable, Function fn) {
        QueryHelper queryHelper = new QueryHelper(entityManager, entityClass, request, lingua, pageable);
        return (List) queryHelper.getItems().stream().map(item -> fn.apply(item)).collect(Collectors.toList());
    }

    public static WsRispostaPaginata getWsRispostaPaginata(EntityManager entityManager, Class entityClass, Object request, String lingua, Pageable pageable, Function fn) {
        QueryHelper queryHelper = new QueryHelper(entityManager, entityClass, request, lingua, pageable);

        return WsRispostaPaginata.of(queryHelper.getItems().stream().map(item -> fn.apply(item)).collect(Collectors.toList()), WsMetaPaginazione.of(pageable.getPageNumber(), pageable.getPageSize(), queryHelper.count()));
    }

    public static Page getPage(EntityManager entityManager, Class entityClass, Object request, String lingua, Pageable pageable, Function fn) {
        QueryHelper queryHelper = new QueryHelper(entityManager, entityClass, request, lingua, pageable);
        return new PageImpl(
                (List) queryHelper.getItems().stream()
                    .map(item -> fn.apply(item))
                    .collect(Collectors.toList()),
                pageable,
                queryHelper.count());
    }
}