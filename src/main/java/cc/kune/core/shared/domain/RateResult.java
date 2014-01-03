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
package cc.kune.core.shared.domain;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class RateResult.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RateResult implements IsSerializable {

  /** The current user rate. */
  private Double currentUserRate;

  /** The rate. */
  private Double rate;

  /** The rate by users. */
  private Integer rateByUsers;

  /**
   * Instantiates a new rate result.
   */
  public RateResult() {
    this(null, null, null);
  }

  /**
   * Instantiates a new rate result.
   * 
   * @param rate
   *          the rate
   * @param rateByUsers
   *          the rate by users
   * @param currentUserRate
   *          the current user rate
   */
  public RateResult(final Double rate, final Integer rateByUsers, final Double currentUserRate) {
    this.rate = rate;
    this.rateByUsers = rateByUsers;
    this.currentUserRate = currentUserRate;
  }

  /**
   * Gets the current user rate.
   * 
   * @return the current user rate
   */
  public Double getCurrentUserRate() {
    return currentUserRate;
  }

  /**
   * Gets the rate.
   * 
   * @return the rate
   */
  public Double getRate() {
    return rate;
  }

  /**
   * Gets the rate by users.
   * 
   * @return the rate by users
   */
  public Integer getRateByUsers() {
    return rateByUsers;
  }

  /**
   * Sets the current user rate.
   * 
   * @param currentUserRate
   *          the new current user rate
   */
  public void setCurrentUserRate(final Double currentUserRate) {
    this.currentUserRate = currentUserRate;
  }

  /**
   * Sets the rate.
   * 
   * @param rate
   *          the new rate
   */
  public void setRate(final Double rate) {
    this.rate = rate;
  }

  /**
   * Sets the rate by users.
   * 
   * @param rateByUsers
   *          the new rate by users
   */
  public void setRateByUsers(final Integer rateByUsers) {
    this.rateByUsers = rateByUsers;
  }

}
