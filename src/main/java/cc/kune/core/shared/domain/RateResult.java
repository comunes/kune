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
package cc.kune.core.shared.domain;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RateResult implements IsSerializable {
  private Double rate;
  private Integer rateByUsers;
  private Double currentUserRate;

  public RateResult() {
    this(null, null, null);
  }

  public RateResult(final Double rate, final Integer rateByUsers, final Double currentUserRate) {
    this.rate = rate;
    this.rateByUsers = rateByUsers;
    this.currentUserRate = currentUserRate;
  }

  public Double getCurrentUserRate() {
    return currentUserRate;
  }

  public Double getRate() {
    return rate;
  }

  public Integer getRateByUsers() {
    return rateByUsers;
  }

  public void setCurrentUserRate(final Double currentUserRate) {
    this.currentUserRate = currentUserRate;
  }

  public void setRate(final Double rate) {
    this.rate = rate;
  }

  public void setRateByUsers(final Integer rateByUsers) {
    this.rateByUsers = rateByUsers;
  }

}
