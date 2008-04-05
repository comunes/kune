
package org.ourproject.kune.platf.server.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.ourproject.kune.platf.server.access.AccessType;

import com.google.inject.BindingAnnotation;

/**
 * Use in RPC methods, for instance: <code>
 * Authorizated(authLevelRequired = AuthLevelRequired.COLLAB, checkContent = true) </code>
 * 
 * The first parameter in the method must be the userHash, the second the group
 * shortName, and if you want to check also the content access, the 3rd
 * parameter must be a content id (string)
 * 
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorizated {

    AccessType accessTypeRequired() default AccessType.READ;

    boolean checkContent() default false;

}
