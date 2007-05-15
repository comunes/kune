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
package org.ourproject.kune.client.ui.desktop;

import org.ourproject.kune.client.ui.BorderPanel;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Vicente J. Ruiz Jurado
 *
 */
public class KuneDesktop extends VerticalPanel {
	private static KuneDesktop singleton;
		
	public SiteBar siteBar = null;
	public EntityLogo entityLogo = null;
	public SiteMessageDialog contextMessagesBar = null;
	public LocalNavBar localNavBar = null;
	public ContextDropDowns contextDropDowns = null;
	public ContextNavBar contextNavBar = null;
	public ContextToolBar contextToolBar = null;
	public ContextTitle contextTitle = null;
	public ContextContents contextContents = null;
	public ContextBottomBar contextBottomBar = null;

	//private HorizontalPanel generalHSP = null;
    private HorizontalSplitPanel generalHSP = null;
	private VerticalPanel localNavVP = null;
	private VerticalPanel contextVP = null;
	private VerticalPanel contextNavVP = null;
	public HorizontalSplitPanel contextHSP = null;
//	private HorizontalPanel contextHSP = null;
	private VerticalPanel contextContentsVP = null;
	
	public KuneDesktop() {
		super();
	    singleton = this;
		initialize();
		layout();
		setProperties();
	}
	
	public static KuneDesktop get() {
        return singleton;
    }
	
	private void initialize() {
		siteBar = new SiteBar();
		entityLogo = new EntityLogo();
		contextMessagesBar = new SiteMessageDialog();
		localNavBar = new LocalNavBar();
		contextDropDowns = new ContextDropDowns();
		contextNavBar = new ContextNavBar();
		contextToolBar = new ContextToolBar();
		contextTitle = new ContextTitle();
		contextContents = new ContextContents();
		contextBottomBar = new ContextBottomBar();
		
		//generalHSP = new HorizontalPanel();
		generalHSP = new HorizontalSplitPanel();
		localNavVP = new VerticalPanel();
		contextVP = new VerticalPanel();
//		contextHSP = new HorizontalSplitPanel();
		contextHSP = new HorizontalSplitPanel();
		contextNavVP = new VerticalPanel();
		contextContentsVP = new VerticalPanel();
	}
	
	private void layout() {
		this.add(siteBar);
		this.add(new BorderPanel(entityLogo, 0, 0, 3, 0));
		this.add(generalHSP);
		
        generalHSP.setRightWidget(localNavVP);
        generalHSP.setLeftWidget(contextVP);
		
//		generalHSP.add(contextVP);
//		generalHSP.add(localNavVP);
		
		localNavVP.add(localNavBar);
		localNavVP.add(new BorderPanel(contextDropDowns, 5, 0, 0, 5));
		contextVP.add(contextToolBar);
		contextVP.add(contextTitle);
		contextVP.add(contextHSP);
        contextVP.add(contextBottomBar);
		
        contextHSP.setRightWidget(contextNavVP);
		contextHSP.setLeftWidget(contextContentsVP);
		contextHSP.setSplitPosition("80%");	
        
//      contextHSP.add(contextContentsVP);
//		contextHSP.add(contextNavVP);
				
		contextNavVP.add(contextNavBar);
		contextContentsVP.add(contextContents);
	}
	
	private void setProperties() {
		this.setBorderWidth(0);
		this.setSpacing(0);
		this.setWidth("100%");
		
        localNavVP.setBorderWidth(0);
        localNavVP.setSpacing(0);
        localNavVP.setWidth("150");
        
        contextVP.setBorderWidth(0);
        contextVP.setSpacing(0);
        
        contextContentsVP.setBorderWidth(0);
        contextContentsVP.setSpacing(0);
        
        contextNavVP.setBorderWidth(0);
        contextNavVP.setSpacing(0);
        
        generalHSP.setStyleName("general-hsp");
	}	
}
