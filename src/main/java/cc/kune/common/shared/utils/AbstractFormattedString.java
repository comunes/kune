/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.common.shared.utils;

import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class FormattedString is used to separate, String templates (usually
 * html) from args (indicated with %s) and also to allow the translation of this
 * templates. More info
 * http://docs.oracle.com/javase/1.5.0/docs/api/java/util/Formatter.html#syntax
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractFormattedString {

  /** The args. */
  private final Object[] args;

  /** The should be translated. */
  private boolean shouldBeTranslated;

  /** The template. */
  private String template;

  /**
   * Instantiates a new formated string.
   *
   * @param shouldBeTranslated the should be translated
   * @param template the template
   * @param args the args that will be formatted inside the template (%s, etc)
   */
  public AbstractFormattedString(final boolean shouldBeTranslated, final String template,
      final Object... args) {
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
  public AbstractFormattedString(final String plainMsg) {
    this(true, plainMsg);
  }

  /**
   * Instantiates a new formated string.
   * 
   * @param template
   *          the template
   * @param args
   *          the args that will be formatted inside the template (%s, etc)
   */
  public AbstractFormattedString(final String template, final Object... args) {
    this(true, template, args);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
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
    final AbstractFormattedString other = (AbstractFormattedString) obj;
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
   * Format.
   *
   * @param template the template
   * @param args the args
   * @return the string
   */
  public abstract String format(final String template, final Object... args);

  /**
   * Gets the args.
   *
   * @return the args
   */
  public Object[] getArgs() {
    return args;
  }

  /**
   * Gets the string.
   * 
   * @return the string
   */
  public String getString() {
    if (template == null) {
      throw new NullPointerException(String.valueOf("Template of FormatedString cannot be null"));
    }
    return args == null || args.length == 0 ? template : format(template, args);
  }

  /**
   * Gets the template.
   * 
   * @return the template
   */
  public String getTemplate() {
    return template;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
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
   * be translated).
   *
   * @return true, if should be
   */
  public boolean shouldBeTranslated() {
    return shouldBeTranslated;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "FormatedString ['" + TextUtils.ellipsis(template, 40) + "', args=" + Arrays.toString(args)
        + ", shouldBeTranslated=" + shouldBeTranslated + "]";
  }

}
