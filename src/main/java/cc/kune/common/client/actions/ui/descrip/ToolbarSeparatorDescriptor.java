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
package cc.kune.common.client.actions.ui.descrip;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolbarSeparatorDescriptor.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ToolbarSeparatorDescriptor extends AbstractSeparatorDescriptor {

  /**
   * The Enum Type.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public enum Type {
    
    /** The fill. */
    fill, 
 /** The separator. */
 separator, 
 /** The spacer. */
 spacer
  }

  /**
   * Builds the.
   *
   * @param type the type
   * @param parent the parent
   * @return the toolbar separator descriptor
   */
  public static ToolbarSeparatorDescriptor build(final Type type, final ToolbarDescriptor parent) {
    return new ToolbarSeparatorDescriptor(type, parent);
  }

  /** The type. */
  private final Type type;

  /**
   * Instantiates a new toolbar separator descriptor.
   *
   * @param type the type
   * @param parent the parent
   */
  public ToolbarSeparatorDescriptor(final Type type, final ToolbarDescriptor parent) {
    super();
    this.type = type;
    setParent(parent);
  }

  /**
   * Gets the separator type.
   *
   * @return the separator type
   */
  public Type getSeparatorType() {
    return type;
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip#getType()
   */
  @Override
  public Class<?> getType() {
    return ToolbarSeparatorDescriptor.class;
  }

}
