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

package cc.kune.polymer.client;

/**
 * The Enum Layout.
 *
 * @see <a
 *      href="https://www.polymer-project.org/docs/polymer/layout-attrs.html">Polymer
 *      layouts</a>
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public enum Layout {
  /* @formatter:off */
  AROUND_JUSTIFIED("around-justified"),
  AUTO_VERTICAL("auto-vertical"),
  BLOCK("block"),
  CENTER("center"),
  CENTER_JUSTIFIED("center-justified"),
  END("end"),
  END_JUSTIFIED("end-justified"),
  FIT("fit"),
  FLEX("flex"),
  FULLBLEED("fullbleed"),
  HIDDEN("hidden"),
  HORIZONTAL("horizontal"),
  JUSTIFIED("justified"),
  LAYOUT("layout"),
  RELATIVE("relative"),
  REVERSE("reverse"),
  SELF_CENTER("self-center"),
  SELF_END("self-end"),
  SELF_START("self-start"),
  SELF_STRETCH("self-stretch"),
  START("start"),
  START_JUSTIFIED("start-justified"),
  THREE("three"),
  TWO("two"),
  VERTICAL("vertical"),
  WRAP("wrap");
  /* @formatter:on */

  private final String attribute;

  Layout(final String attribute) {
    this.attribute = attribute;
  }

  public String getAttribute() {
    return attribute;
  }

}
