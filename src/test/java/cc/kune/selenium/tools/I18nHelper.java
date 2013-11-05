/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.selenium.tools;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.i18n.client.Messages;
import com.google.gwt.i18n.client.Messages.AlternateMessage;
import com.google.gwt.i18n.client.Messages.DefaultMessage;

public class I18nHelper {

  public static String get(final Class<? extends Messages> classToMock, final Method method,
      final Object[] arguments) {
    return get(classToMock, method.getName(), arguments);
  }

  public static String get(final Class<? extends Messages> messageType, final String methodName,
      final Object... params) {
    try {
      final Class<?>[] classes = new Class<?>[params.length];
      for (int i = 0; i < params.length; i++) {
        final Class<? extends Object> class1 = params[i].getClass();
        classes[i] = class1 == Integer.class ? int.class : class1;
      }
      final Method method = messageType.getMethod(methodName, classes);
      final String defaultValue = i18nDefaultValue(method, classes);
      final String pluralOrDefValue = getPlural(method, getFirstInt(params), defaultValue);
      return MessageFormat.format(pluralOrDefValue, params);
    } catch (final Exception e) {
      e.printStackTrace();
      return "Error: Inexistent i18n String definition for method: " + methodName;
    }
  }

  private static int getFirstInt(final Object[] params) {
    for (final Object param : params) {
      final Class<? extends Object> class1 = param.getClass();
      if (class1 == Integer.class) {
        return (Integer) param;
      }
    }
    return 0;
  }

  private static String getPlural(final Method method, final int value, final String def) {
    final AlternateMessage pluralText = method.getAnnotation(AlternateMessage.class);
    if (pluralText != null) {
      final Map<String, String> pluralMap = new HashMap<String, String>();
      final String[] pluralForms = pluralText.value();
      for (int i = 0; i + 1 < pluralForms.length; i += 2) {
        pluralMap.put(pluralForms[i], pluralForms[i + 1]);
      }
      switch (value) {
      case 0:
        final String zero = pluralMap.get("none");
        return zero == null ? def : zero;
      case 1:
        final String one = pluralMap.get("one");
        return one == null ? def : one;
      default:
        final String other = pluralMap.get("other");
        return other == null ? def : other;
      }
    }
    return def;
  }

  private static String i18nDefaultValue(final Method method, final Class<?>... params) {
    return method.getAnnotation(DefaultMessage.class).value();
  }

  private final Class<? extends Messages> messagesClass;

  public I18nHelper(final Class<? extends Messages> messagesClass) {
    this.messagesClass = messagesClass;
  }

  public String get(final String methodName, final Object... params) {
    return get(messagesClass, methodName, params);
  }
}
