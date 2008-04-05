
package org.ourproject.kune.platf.server.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.google.inject.BindingAnnotation;

/**
 * Use in *RPC methods to check if user is authenticated
 * 
 * The first param in the method must be the userHash
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface Authenticated {

    /**
     * if false, only checks for session expiration
     * 
     * @return
     */
    boolean mandatory() default true;

}
