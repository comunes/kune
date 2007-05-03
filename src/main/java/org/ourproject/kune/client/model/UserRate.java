/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package org.ourproject.kune.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserRate extends Model implements IsSerializable, Cloneable {
	private long userId;
    private long rateId;
    private float rateValue;
    
	public UserRate(long userId, long rateId, float rateValue) {
		this.userId = userId;
		this.rateId = rateId;
		this.rateValue = rateValue;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
    }
	
	public void setRateId(long rateId) {
        this.rateId = rateId;
    }
	
	public void setRateValue(float rateValue) {
		this.rateValue = rateValue;
	}
	
	public long getUserId() {
        return this.userId;
	}

	public long getRateId() {
        return this.rateId;
	}

	public float getRateValue() {
        return this.rateValue;
	}
 	
    public Object clone() {
 	 	UserRate userRateClone = new UserRate(this.getUserId(), this.getRateId(), this.getRateValue());
 	 	return userRateClone;
    }
    
}

