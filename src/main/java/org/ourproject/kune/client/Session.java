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
package org.ourproject.kune.client;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.client.model.Group;
import org.ourproject.kune.client.model.Rate;
import org.ourproject.kune.client.model.User;
import org.ourproject.kune.client.model.UserRate;

public class Session {
	private static Session singleton;
    public User currentUser = null;
    public Group currentGroup = null;
    // TODO Locale
    
    // sandbox
    ArrayList userRates = new ArrayList();
    
	/**
	 * Gets the singleton Session instance.
	 */
    public Session() {
        singleton = this;
    }
    
	public static Session get() {
	  return singleton;
	}
	    
    public User currentUser() {
    	return this.currentUser;
    }
    
    public Group currentGroup() {
    	return this.currentGroup;
    }
    
    // sandbox (ERASE LATER)
    
    public void addRate(UserRate userRate) {
        userRates.add(userRate);
    }
    
    public boolean isRated(User user, Rate rate) {
    	for (Iterator it = userRates.iterator(); it.hasNext();) {
        	UserRate userRate = (UserRate) it.next();
            if ((userRate.getUserId() == user.getId()) &
                (userRate.getRateId() == rate.getId())) {
            	return true;
            }
        }
        return false;
    }
    
    public float getRate(User user, Rate rate) {
    	for (Iterator it = userRates.iterator(); it.hasNext();) {
            UserRate userRate = (UserRate) it.next();
            if ((userRate.getUserId() == user.getId()) &
                (userRate.getRateId() == rate.getId())) {
                return userRate.getRateValue();
            }
        }
        throw new IllegalArgumentException("Not found");
    }

    public void setRate(User user, Rate rate, float rateValue) {
    	for (Iterator it = userRates.iterator(); it.hasNext();) {
            UserRate userRate = (UserRate) it.next();
            if ((userRate.getUserId() == user.getId()) &
                (userRate.getRateId() == rate.getId())) {
            	userRate.setRateValue(rateValue);
            	return;
            }
        }
        throw new IllegalArgumentException("Not found");
    }
    
}