package cc.kune.core.server.persist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface KuneTransactional {

  /**
   * A list of exceptions to <b>not<b> rollback on. A caveat to the rollbackOn
   * clause. The disjunction of rollbackOn and ignore represents the list of
   * exceptions that will trigger a rollback. The complement of rollbackOn and
   * the universal set plus any exceptions in the ignore set represents the list
   * of exceptions that will trigger a commit. Note that ignore exceptions take
   * precedence over rollbackOn, but with subtype granularity.
   */
  Class<? extends Exception>[] ignore() default {};

  /**
   * A list of exceptions to rollback on, if thrown by the transactional method.
   * These exceptions are propagated correctly after a rollback.
   */
  Class<? extends Exception>[] rollbackOn() default RuntimeException.class;
}