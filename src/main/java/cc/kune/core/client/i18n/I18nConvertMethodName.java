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
package cc.kune.core.client.i18n;

// TODO: Auto-generated Javadoc
/**
 * The Class I18nConvertMethodName.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class I18nConvertMethodName {

  /**
   * The main method.
   * 
   * @param args
   *          the arguments
   */
  public static void main(final String... args) {
    if (args.length == 0) {
      System.err.print("Syntax: I18nConvertMethodName 'A string message to convert'\n");
    } else {
      final StringBuffer buf = new StringBuffer();
      for (final String arg : args) {
        buf.append(arg).append(" ");
      }
      System.out.print(I18nUtils.convertMethodName(buf.toString()));
    }
  }
}
