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
package cc.kune.domain;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

// TODO: Auto-generated Javadoc
/**
 * The Class ToolConfiguration.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Entity
@Table(name = "tool_configurations")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ToolConfiguration {

  /** The enabled. */
  @Basic
  @Column(columnDefinition = "BIT", length = 1)
  private boolean enabled;

  /** The id. */
  @Id
  @GeneratedValue
  private Long id;

  /** The root. */
  @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
  private Container root;

  /**
   * Instantiates a new tool configuration.
   */
  public ToolConfiguration() {
    enabled = true;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Gets the root.
   * 
   * @return the root
   */
  public Container getRoot() {
    return root;
  }

  /**
   * Checks if is enabled.
   * 
   * @return true, if is enabled
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Sets the enabled.
   * 
   * @param enabled
   *          the new enabled
   */
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  public void setId(final Long id) {
    this.id = id;
  }

  /**
   * Sets the root.
   * 
   * @param root
   *          the root
   * @return the container
   */
  public Container setRoot(final Container root) {
    this.root = root;
    return root;
  }

}
