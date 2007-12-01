package org.ourproject.rack.filters.json;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface JSONMethod {
	String name();
	String[] params();
}
