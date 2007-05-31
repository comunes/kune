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

public class Group extends Model implements IsSerializable, Cloneable {
	private String shortName = null;
    
	private String longName = null;
    
	private String privateDescription = null;
    
	private String publicDescription = null;
    
	private boolean isPublic = false;
    
	private String logo = null;
    
	private String icon = null;
    
	private User createdBy = null; 
    
	public Group(String shortName, String longName) {
		setShortName(shortName);
		setLongName(longName);
	}
	
	public Group() {
    }
	
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public void setPrivateDescription(String privateDescription) {
        this.privateDescription = privateDescription;
    }

    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public void setCreatedBy(User createdBy) {
    	this.createdBy = createdBy;
    }
    
    public String getShortName() {
    	return shortName;
    }
    
    public String getLongName() {
    	return longName;
    }
    public String getPrivateDescription() {
    	return privateDescription;
    }
    
    public String getPublicDescription() {
    	return publicDescription;
    }
    public boolean getIsPublic() {
    	return isPublic;
    }
    
    public String getLogo() {
    	return logo;
    }
    
    public String getIcon() {
    	return icon;
    }
    
    public User getCreatedBy() {
    	return createdBy;
    }
    
    public Object clone() {
        Group groupClone = new Group();
    	groupClone.setShortName(this.shortName);
        groupClone.setLongName(this.longName);
        groupClone.setPrivateDescription(this.privateDescription);
        groupClone.setPublicDescription(this.publicDescription);
        groupClone.setIsPublic(this.isPublic);
        groupClone.setLogo(this.logo);
        groupClone.setIcon(this.icon);
        groupClone.setCreatedBy(this.createdBy);
    	return groupClone;
    }

}
