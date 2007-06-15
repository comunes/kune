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

package org.ourproject.kune.client.rpc;

import org.ourproject.kune.client.rpc.dto.KuneDoc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * 
 * @author Daniel Gomez
 *
 */
public interface KuneDocumentService extends RemoteService {

	public KuneDoc getRootDocument(String projectName);
	
	public void setRootDocument(String projectName, KuneDoc doc);
	
	   public static class App {
	        private static KuneDocumentServiceAsync ourInstance = null;

	        public static synchronized KuneDocumentServiceAsync getInstance() {
	            if (ourInstance == null) {
	                ourInstance = (KuneDocumentServiceAsync) GWT.create(KuneDocumentService.class);
	                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KuneDocumentService");
	            }
	            return ourInstance;
	        }
	    }
}
