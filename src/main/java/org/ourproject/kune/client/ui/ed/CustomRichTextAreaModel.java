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
package org.ourproject.kune.client.ui.ed;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;

public class CustomRichTextAreaModel implements CustomRichTextAreaController {
	
	private CustomRichTextAreaView view;
	
	private Timer saveTimer;
	
	private boolean savePending = false;
	
	private boolean autoSave = false;	
	
	private Command saveCmd;
	
	public void init(String html, CustomRichTextAreaView view, boolean autoSave, Command cmd) {
		this.view = view;
		this.view.enableSaveButton(false);
		this.view.setHTML(html);
		this.view.setEnabled(true);
		this.saveCmd = cmd;
		this.autoSave = autoSave;
		
		saveTimer = new Timer() {
			public void run() {
				onSave();
			}
		};
		
	}
	
    public void onEdit() {
		if (!savePending) {
            savePending = true;
            this.view.enableSaveButton(true);
            if (autoSave) {
                saveTimer.schedule(10000);
            }
		}    	
    }
    
    public void onSave() {
    	saveCmd.execute();
    }
    
    public void onCancel() {
    	// TODO
    }
    

	public void setAutoSave(Command saveCmd) {
		this.autoSave = true;
		this.saveCmd = saveCmd;
	}
	
	public void afterSaved() {
        saveTimer.cancel();
		savePending = false;
		view.enableSaveButton(false);
	}
	
	public void afterFailedSave() {
        saveTimer.schedule(20000);
	}

}
