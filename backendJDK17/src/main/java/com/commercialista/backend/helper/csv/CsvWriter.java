package com.commercialista.backend.helper.csv;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class CsvWriter {

    public List<String> getCsvHeaders(Class clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CsvField.class) || field.isAnnotationPresent(CsvObject.class))
                .sorted(getComparator())
                .map(field -> buildHeaderName("", field, new ArrayList<>()))
                .flatMap(list -> list.stream())
                .toList();
    }

    private List<String> buildHeaderName(String prefix, Field field, List<String> visibleFields) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(CsvObject.class)) {
            CsvObject annotation = field.getAnnotation(CsvObject.class);
            Class<?> type = field.getType();
            return Arrays.stream(type.getDeclaredFields())
                    .filter(childField -> childField.isAnnotationPresent(CsvField.class) || childField.isAnnotationPresent(CsvObject.class))
                    .sorted(getComparator())
                    .map(childField -> buildHeaderName(prefix + annotation.prefix().replaceAll("\"", "\"\""), childField, visibleFields))
                    .flatMap(listHeaders -> listHeaders.stream())
                    .filter(header -> header != null)
                    .toList();

        } else {
            CsvField annotation = field.getAnnotation(CsvField.class);
            field.setAccessible(false);
            String header = prefix + annotation.header().replaceAll("\"", "\"\"");
            return Collections.singletonList(header);
        }
    }

    public String getCsvString(List<?> rows, Class clazz, List<String> originalVisibleFields) {
        if (originalVisibleFields == null) {
            originalVisibleFields = new ArrayList<>();
        }
        List<String> visibleFields = originalVisibleFields.stream()
                .map(field -> "\"" + field + "\"")
                .toList();
        String header = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CsvField.class) || field.isAnnotationPresent(CsvObject.class))
                .sorted(getComparator())
                .map(field -> getHeader("", field, visibleFields))
                .filter(field -> field != null)
                .collect(Collectors.joining(","));

        String body = rows.stream()
                .map(row -> getRow(row, visibleFields))
                .collect(Collectors.joining("\r\n"));
        String content = header;
        if (body != null) {
            content = header + "\r\n" + body;
        }
        return content;
    }

    private String getValue(Field field, Object row, List<String> visibleFields, String prefix) {
        field.setAccessible(true);
        try {
            Object value = field.get(row);
            field.setAccessible(false);

            if (field.isAnnotationPresent(CsvObject.class)) {
                CsvObject annotation = field.getAnnotation(CsvObject.class);
                return Arrays.stream(field.getType().getDeclaredFields())
                        .filter(childField -> childField.isAnnotationPresent(CsvField.class) || childField.isAnnotationPresent(CsvObject.class))
                        .sorted(getComparator())
                        .map(childField -> getValue(childField, value, visibleFields, prefix + annotation.prefix().replaceAll("\"", "\"\"")))
                        .filter(childValue -> childValue != null)
                        .collect(Collectors.joining(","));
            } else {
                CsvField annotation = field.getAnnotation(CsvField.class);
                String header = "\"" + prefix + annotation.header().replaceAll("\"", "\"\"") + "\"";
                if (!visibleFields.isEmpty() && !visibleFields.contains(header)) {
                    return null;
                }
                if (value == null) {
                    return "";
                }
                if (!annotation.formatter().equals("")) {
                    return "\"" + String.format(annotation.formatter(), value).replaceAll("\"", "\"\"") + "\"";
                } else {
                    return "\"" + value.toString().replaceAll("\"", "\"\"") + "\"";
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("errore", e);
        }

    }

    private String getRow(Object row, List<String> visibleFields) {
        return Arrays.stream(row.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(CsvField.class) || field.isAnnotationPresent(CsvObject.class))
                .sorted(getComparator())
                .map(field -> getValue(field, row, visibleFields, ""))
                .filter(value -> value != null)
                .collect(Collectors.joining(","));
    }


    private String getHeader(String prefix, Field field, List<String> visibleFields) {
        field.setAccessible(true);
        if (field.isAnnotationPresent(CsvObject.class)) {
            CsvObject annotation = field.getAnnotation(CsvObject.class);
            Class<?> type = field.getType();
            return Arrays.stream(type.getDeclaredFields())
                    .filter(childField -> childField.isAnnotationPresent(CsvField.class) || childField.isAnnotationPresent(CsvObject.class))
                    .sorted(getComparator())
                    .map(childField -> getHeader(prefix + annotation.prefix().replaceAll("\"", "\"\""), childField, visibleFields))
                    .filter(header -> header != null)
                    .collect(Collectors.joining(","));
        } else {
            CsvField annotation = field.getAnnotation(CsvField.class);
            field.setAccessible(false);
            String header = "\"" + prefix + annotation.header().replaceAll("\"", "\"\"") + "\"";
            if (visibleFields.isEmpty() || visibleFields.contains(header)) {
                return header;
            }
            return null;
        }
    }

    private Comparator<Field> getComparator() {
        return Comparator.comparingInt((Field field) -> {
            field.setAccessible(true);
            if (field.isAnnotationPresent(CsvObject.class)) {
                CsvObject annotation = field.getAnnotation(CsvObject.class);
                field.setAccessible(false);
                return annotation.order();
            } else {
                CsvField annotation = field.getAnnotation(CsvField.class);
                field.setAccessible(false);
                return annotation.order();
            }
        });
    }
}
