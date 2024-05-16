package com.commercialista.backend.helpers.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryCriteria {
    String field() default "";
    //field2 della forma
    //{"nomeCampo/percorsoCampo.campo se root
    // e tabella che contiene campo sono in manytomany con tabella associativa mappata esplicitamente
    // : (OBBLIGATORIO SEMPRE)
    // eventuale percorso tabella da root (con root esclusa) caso relazione 1/n a n da root"}
    String[] field2() default {};

    Operator operator() default Operator.DEFAULT;
    Operator operatorTrue() default Operator.DEFAULT;
    Operator operatorFalse() default Operator.DEFAULT;

    String join() default "";
    String chiave() default "";

    enum Operator {
        DEFAULT, LIKE, EQ, NOT_EQ, IN, GT, LT, NULL, LTE, GTE, NOT_NULL
    }
}
