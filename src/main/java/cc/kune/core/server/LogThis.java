package cc.kune.core.server;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * Anotate the classes you want to log via Guice interceptors
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface LogThis {
}
