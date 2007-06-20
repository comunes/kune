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

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class KuneDesktop extends VerticalPanel {
	public static final int FIRSTRESIZE = -1;
	
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

    private HorizontalPanel generalHP = null;
	private VerticalPanel localNavVP = null;
	private VerticalPanel contextVP = null;
	public HorizontalSplitPanel contextHSP = null;
	
	int currentContextWidth = FIRSTRESIZE; 
	
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
		contextMessagesBar = new SiteMessageDialog();
		siteBar = new SiteBar();
		entityLogo = new EntityLogo();
		localNavBar = new LocalNavBar();
		contextDropDowns = new ContextDropDowns();
		contextNavBar = new ContextNavBar();
		contextToolBar = new ContextToolBar();
		contextTitle = new ContextTitle();
		contextContents = new ContextContents();
		contextBottomBar = new ContextBottomBar();
		
		generalHP = new HorizontalPanel();
		localNavVP = new VerticalPanel();
		contextVP = new VerticalPanel();
		contextHSP = new HorizontalSplitPanel();
	}
	
	private void layout() {
		this.add(siteBar);
		this.add(new BorderPanel(entityLogo, 0, 0, 4, 0));
		this.add(generalHP);
		
		generalHP.add(contextVP);
        generalHP.add(localNavVP);
		
		localNavVP.add(localNavBar);
		localNavVP.add(new BorderPanel(contextDropDowns, 5, 0, 0, 5));
		contextVP.add(contextToolBar);
		contextVP.add(contextTitle);
		contextVP.add(contextHSP);
		contextVP.add(contextBottomBar);
		        
		contextHSP.setLeftWidget(new BorderPanel(contextContents, 5));
		contextHSP.setRightWidget(contextNavBar);
		
		contextHSP.sinkEvents(Event.ONMOUSEUP | Event.ONMOUSEDOWN |	Event.ONMOUSEMOVE |	Event.ONMOUSEOUT | Event.ONMOUSEOVER | Event.ONMOUSEUP);
	}
	
	private void setProperties() {
		this.setBorderWidth(0);
		this.setSpacing(0);
		this.setWidth("100%");
		
		generalHP.setWidth("100%");
		
        localNavVP.setBorderWidth(0);
        localNavVP.setSpacing(0);
        localNavVP.setWidth("150");
        
        contextVP.setBorderWidth(0);
        contextVP.setSpacing(0);
        contextVP.setWidth("100%");
        contextVP.addStyleName("general-context");
		contextVP.setStyleName("general-context");
		
        contextContents.setWidth("100%");
        contextContents.setHeight("100%");
        contextNavBar.setWidth("100%");
        contextNavBar.setHeight("100%"); 
	}
	
	public void adjustSize(int windowWidth, int windowHeight) {
		int contextWidth = windowWidth - 160 - 3;
		int contextHeight = windowHeight - 200;

//		if (currentContextWidth != FIRSTRESIZE) {
//			if ((contextHSP.getRightWidget().getOffsetWidth() + 20) > contextWidth) {
//				// if right widget greater than context part of the window, set split position
//				float oldPercent = ((float) contextHSP.getLeftWidget().getOffsetWidth()) / currentContextWidth;
//				int newContextNavBarWidth = (int) (contextWidth * oldPercent);
//				contextHSP.setSplitPosition("" + newContextNavBarWidth + "px");	
//				SiteMessageDialog.get().setMessageInfo("setsize: " + contextWidth 
//						+ "/" + contextHeight + "(" + oldPercent + "/" + newContextNavBarWidth + ")");
//			}
//			contextHSP.setSize("" + contextWidth + "px", "" + contextHeight + "px");
//		}
//		currentContextWidth = contextWidth;
		// While 1.4 stabilizes:
		contextHSP.setSize("" + contextWidth + "px", "" + contextHeight + "px");
		contextHSP.setSplitPosition("" + (contextWidth - 95) + "px");
	}
	
    public void onBrowserEvent(Event event) {
    	//if (DOM.eventGetType(event) == Event.ONMOUSEUP) {
            SiteMessageDialog.get().setMessageInfo("Mouse");
    //    }
    }
}
