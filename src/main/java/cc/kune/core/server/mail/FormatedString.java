package cc.kune.core.server.mail;

import java.util.Arrays;

import com.calclab.emite.core.client.packet.TextUtils;
import com.google.common.base.Preconditions;

/**
 * The Class FormatedString is used to separate, String templates (usually html)
 * from args (indicated with %s) and also to allow the translation of this
 * templates. More info
 * http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Formatter.html#syntax
 */
public class FormatedString {

  public static FormatedString build(final boolean shouldBeTranslated, final String template,
      final Object... args) {
    return new FormatedString(shouldBeTranslated, template, args);
  }

  /**
   * Builds with only a message without args.
   * 
   * @param plainMsg
   *          the plain msg
   * @return the formated string
   */
  public static FormatedString build(final String plainMsg) {
    return new FormatedString(plainMsg);
  }

  /**
   * Builds the.
   * 
   * @param template
   *          the template
   * @param args
   *          the args
   * @return the formated string
   */
  public static FormatedString build(final String template, final Object... args) {
    return new FormatedString(template, args);
  }

  /** The args. */
  private final Object[] args;

  /** The should be translated. */
  private boolean shouldBeTranslated;

  /** The template. */
  private String template;

  /**
   * Instantiates a new formated string.
   * 
   * @param template
   *          the template
   * @param shouldBeTranslated
   *          the should be translated
   * @param args
   *          the args that will be formatted inside the template (%s, etc)
   */
  public FormatedString(final boolean shouldBeTranslated, final String template, final Object... args) {
    this.template = template;
    this.shouldBeTranslated = shouldBeTranslated;
    this.args = args;
  }

  /**
   * Instantiates a new formated string.
   * 
   * @param plainMsg
   *          the plain msg
   */
  public FormatedString(final String plainMsg) {
    template = plainMsg;
    args = null;
    shouldBeTranslated = true;
  }

  /**
   * Instantiates a new formated string.
   * 
   * @param template
   *          the template
   * @param args
   *          the args that will be formatted inside the template (%s, etc)
   */
  public FormatedString(final String template, final Object... args) {
    this.template = template;
    this.args = args;
    this.shouldBeTranslated = true;
  }

  public FormatedString copy() {
    return new FormatedString(shouldBeTranslated, template, args);
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final FormatedString other = (FormatedString) obj;
    if (!Arrays.equals(args, other.args)) {
      return false;
    }
    if (shouldBeTranslated != other.shouldBeTranslated) {
      return false;
    }
    if (template == null) {
      if (other.template != null) {
        return false;
      }
    } else if (!template.equals(other.template)) {
      return false;
    }
    return true;
  }

  /**
   * Gets the string.
   * 
   * @return the string
   */
  public String getString() {
    Preconditions.checkNotNull(template, "Template of FormatedString cannot be null");
    return args == null ? template : String.format(template, args);
  }

  /**
   * Gets the template.
   * 
   * @return the template
   */
  public String getTemplate() {
    return template;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(args);
    result = prime * result + (shouldBeTranslated ? 1231 : 1237);
    result = prime * result + ((template == null) ? 0 : template.hashCode());
    return result;
  }

  /**
   * Sets the should be translated.
   * 
   * @param shouldBeTranslated
   *          the new should be translated
   */
  public void setShouldBeTranslated(final boolean shouldBeTranslated) {
    this.shouldBeTranslated = shouldBeTranslated;
  }

  /**
   * Sets the template. Used to translate the template to the user language
   * (when you don't know already the language of the user)
   * 
   * @param template
   *          the new template
   */
  public void setTemplate(final String template) {
    this.template = template;
  }

  /**
   * If should be translated (sometimes the template is only html and should not
   * be translated)
   * 
   * @return true, if should be
   */
  public boolean shouldBeTranslated() {
    return shouldBeTranslated;
  }

  @Override
  public String toString() {
    return "FormatedString ['" + TextUtils.ellipsis(template, 40) + "', args=" + Arrays.toString(args)
        + ", shouldBeTranslated=" + shouldBeTranslated + "]";
  }

}
