package org.ourproject.rack.filters.rest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface REST {
	String format() default "json";
	String[] params();
}
