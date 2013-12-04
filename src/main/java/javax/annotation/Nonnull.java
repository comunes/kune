package javax.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

/**
 * The Interface Nonnull follows
 * 
 * @see <a
 *      href="http://stackoverflow.com/questions/3800033/guava-r07-gwt-and-javax-annotation-nullable">Guava
 *      r07, GWT and javax.annotation.Nullable</a>
 */

@Documented
@TypeQualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Nonnull {
  static class Checker implements TypeQualifierValidator<Nonnull> {

    @Override
    public When forConstantValue(final Nonnull qualifierqualifierArgument, final Object value) {
      if (value == null) {
        return When.NEVER;
      }
      return When.ALWAYS;
    }
  }

  When when() default When.ALWAYS;
}
