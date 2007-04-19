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

public class User extends Model implements IsSerializable, Cloneable {
	private String nickName = null;

	private String password = null;

	private String fullName = null;
	
	private String email = null;

	private boolean emailVerified = false;

	private String photoAvatar = null;

	private String locale = null;

	private String timezone = null;

	public User(String nickName) {
		this.nickName = nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
		
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
	
	public void setPhotoAvatar(String photoAvatar) {
		this.photoAvatar = photoAvatar;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPassword() {
		return password;
	}

	public String getFullName() {
		return fullName;
	}

	public String getEmail() {
		return email;
	}

	public boolean getEmailVerified() {
		return emailVerified;
	}

	public String getPhotoAvatar() {
		return photoAvatar;
	}

	public String getLocale() {
		return locale;
	}

	public String getTimezone() {
		return timezone;
	}	
	
    public Object clone() {
 	 	User userClone = new User(this.getNickName());
 	 	userClone.setPassword(this.getPassword());
 	 	userClone.setFullName(this.getFullName());
 	 	userClone.setEmail(this.getEmail());
 	 	userClone.setEmailVerified(this.getEmailVerified());
 	 	userClone.setPhotoAvatar(this.getPhotoAvatar());
 	 	userClone.setLocale(this.getLocale());
 	 	userClone.setTimezone(this.getTimezone());
 	 	return userClone;
    }
    
}

