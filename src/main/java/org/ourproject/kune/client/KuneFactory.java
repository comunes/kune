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

import org.ourproject.kune.client.ui.WebSafePalette;
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Image;

/**
 * A class for kune using the factory method pattern 
 */
public class KuneFactory {
    private WebSafePalette palette = null;
    private SiteMessageDialog siteMessageDialog = null;
    
    private KuneFactory() {
		DeferredCommand.addCommand(new Command() {
			public void execute() {
				preFetchImpImages();
			}
		});
    }
   
    private static class SingletonHolder { 
        private final static KuneFactory INSTANCE = new KuneFactory();
    }
   
    public static KuneFactory get() {
        return SingletonHolder.INSTANCE;
    }   
        
    public WebSafePalette getWebSafePalette() {
    	if (palette == null)
    		palette = new WebSafePalette();
    	return palette;
    }
    
    public SiteMessageDialog getSiteMessageDialog() {
    	if (siteMessageDialog == null)
    		siteMessageDialog = new SiteMessageDialog();
    	return siteMessageDialog;
    }
    
    void preFetchImpImages() {
    	Image.prefetch("css/img/button15cdark.png");
    	Image.prefetch("css/img/button15clight.png");
    	Image.prefetch("css/img/button15cxlight.png");
    	Image.prefetch("css/img/button17cdark.png");
    	Image.prefetch("css/img/button17clight.png");
    	Image.prefetch("css/img/button17cxlight.png");
    	Image.prefetch("css/img/button20cdark.png");
    	Image.prefetch("css/img/button20clight.png");
    	Image.prefetch("css/img/button20cxlight.png");
    	Image.prefetch("css/img/button-bg-soft.gif");
    	Image.prefetch("css/img/button-bg-hard.gif");
    	
    	Image.prefetch("gwm/themes/alphacubecustom/b.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/bl.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/br.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/close-btn.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/close-btn-on.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/l.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/max-btn.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/max-btn-on.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/min-btn.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/min-btn-on.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/resize-btn.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/restore-btn.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/r.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/t.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/tr.gif");
    	Image.prefetch("gwm/themes/alphacubecustom/tl.gif");
    }
	
}
