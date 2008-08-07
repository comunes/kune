package org.ourproject.kune.platf.server.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.ourproject.kune.platf.server.access.AccessType;

import com.google.inject.BindingAnnotation;

/**
 * Use in RPC methods, for instance: <code>
 * GroupAuthorizated(authLevelRequired = AuthLevelRequired.COLLAB) </code>
 * 
 * The first parameter in the method must be the userHash, the second the group
 * shortName
 * 
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface GroupAuthorizated {

    AccessType accessTypeRequired() default AccessType.READ;

}
