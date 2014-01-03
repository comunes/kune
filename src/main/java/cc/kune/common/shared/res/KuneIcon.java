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
package cc.kune.common.shared.res;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneIcon is used with a kune SVG font (see the layer names in
 * kune-artwork/iconic/iconsfonts.svg) to show kune icons from the kune icons
 * generated ttf.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneIcon {

  /** The character. */
  private final Character character;

  /**
   * Instantiates a new kune icon.
   * 
   * @param character
   *          the character that maps the icon in the ttf
   */
  public KuneIcon(final Character character) {
    this.character = character;
  }

  /**
   * Gets the character.
   * 
   * @return the character
   */
  public Character getCharacter() {
    return character;
  };

}
