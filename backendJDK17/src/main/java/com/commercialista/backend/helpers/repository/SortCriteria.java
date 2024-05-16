package com.commercialista.backend.helpers.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SortCriteria {

    SortBy[] fields();

    @interface SortBy {
        String join() default "";
        String name();
        String field() default "";
        String chiave() default "";
    }


}
