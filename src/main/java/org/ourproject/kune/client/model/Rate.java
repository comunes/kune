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

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.client.Session;
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A Class that implements a rate for a object of the model
 * 
 * @author Vicente J. Ruiz Jurado
 *
 */
public class Rate extends Model implements IsSerializable, Cloneable,
		RateItController {
	private float rate;

	private int byUsers;

	ArrayList rateListeners = new ArrayList();

	ArrayList rateItListeners = new ArrayList();
	
	public final static int NO_DESC = -1;

	public Rate() {
		rate = 0;
		byUsers = 0;
		rateListeners.clear();
		rateItListeners.clear();
	}

	public Rate(float rate, int byUsers) {
		rate = normalizeRate(rate);
		this.rate = rate;
		this.byUsers = byUsers;
		this.fireRateListeners(genStars(rate), byUsers);
	}

	public void addRate(float rate) {
		rate = normalizeRate(rate);
		this.rate = ((this.rate * this.byUsers) + rate) / (++this.byUsers);
		this.fireRateListeners(genStars(this.rate), this.byUsers);
		// TODO: i18n 
		SiteMessageDialog.get().setMessageInfo("Rate saved");
	}

	public void addRateItListener(RateItListener listener) {
		rateItListeners.add(listener);
	}

	public void addRateListener(RateListener listener) {
		rateListeners.add(listener);
	}

	public void changeRate(float oldRate, float newRate) {
		newRate = normalizeRate(newRate);
		oldRate = normalizeRate(oldRate);
		this.rate = this.rate + (newRate - oldRate) / byUsers;
		this.fireRateListeners(genStars(this.rate), this.byUsers);
		SiteMessageDialog.get().setMessageInfo("Rate saved");
	}

	public Object clone() {
		Rate rateClone = new Rate(this.getRate(), this.getByUsers());
		return rateClone;
	}

	protected void fireRateItListeners(Star[] stars) {
		for (Iterator it = rateItListeners.iterator(); it.hasNext();) {
			((RateItListener) it.next()).onRateItChange(stars);
		}
	}

	protected void fireRateItListeners(Star[] stars, int rate) {
		for (Iterator it = rateItListeners.iterator(); it.hasNext();) {
			((RateItListener) it.next()).onRateItChange(stars, rate);
		}
	}

	protected void fireRateListeners(Star[] stars, int byUsers) {
		for (Iterator it = rateListeners.iterator(); it.hasNext();) {
			((RateListener) it.next()).onRateChange(stars, byUsers);
		}
	}

	private Star[] genStars(float rate) {
		Star[] stars;
		stars = new Star[5];
		int rateTruncated = (int) rate;
		float rateDecimals = rate - rateTruncated;

		for (int i = 0; i < 5; i++) {
			if (i < rateTruncated) {
				stars[i] = new Star(Star.YELLOW);
			} else {
				if (i == rateTruncated) {
					stars[i] = new Star(rateDecimals);
				} else {
					stars[i] = new Star(Star.GREY);
				}
			}
		}
		return stars;
	}

	public int getByUsers() {
		return this.byUsers;
	}

	public float getRate() {
		return this.rate;
	}

	protected float normalizeRate(float rate) {
		if (rate < 0) {
			return 0;
		}
		if (rate > 5) {
			return 5;
		}
		return rate;
	}

	public void reflesh() {
		revertUserCurrentRate();
		this.fireRateListeners(genStars(rate), byUsers);
	}

	public void revertUserCurrentRate() {
		Star[] stars = new Star[5];
		User user = Session.get().currentUser();
		if (Session.get().isRated(user, this)) {
			float value = Session.get().getRate(user, this);
			stars = genStars(value);
			fireRateItListeners(stars, Rate.NO_DESC);
		} else {
			stars = Star.CLEAR;
			fireRateItListeners(stars, Rate.NO_DESC);
		}
	}

	public void starClicked(int starClicked) {
		Star[] stars = new Star[5];
		User user = Session.get().currentUser();
		float newValue = starClicked + 1;
		if (Session.get().isRated(user, this)) {
			// Already rated by this user
			float oldValue = Session.get().getRate(user, this); // FIXME (get from server)
			if (Math.ceil(oldValue) == newValue) {
				// Same star, rest 1/2
				newValue = oldValue - ((float) 0.5);
			}
			Session.get().setRate(user, this, newValue); // FIXME (set in server)
			changeRate(oldValue, newValue);
		} else {
			// Not yet rated by this used
			Session.get().addRate(
					new UserRate(user.getId(), this.getId(), newValue)); // FIXME (set in server)
			addRate(newValue);
		}
		stars = genStars(newValue);
		fireRateItListeners(stars, Rate.NO_DESC);
	}

	public void starOver(int starMouseOver) {
		Star[] stars = new Star[5];
		User user = Session.get().currentUser();
		float value = starMouseOver + 1;
		if (Session.get().isRated(user, this)) {
			float oldValue = Session.get().getRate(user, this);
			if (Math.ceil(oldValue) == value) {
				// use user already rated -> mantein value when mouse is over
				value = oldValue;
			}
		}
		stars = genStars(value);
		fireRateItListeners(stars, (int) Math.ceil(value));
	}

}
