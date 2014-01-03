/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.gspace.client.actions;

import cc.kune.gspace.client.viewers.TutorialViewer;
import cc.kune.gspace.client.viewers.TutorialViewer.OnTutorialClose;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

// TODO: Auto-generated Javadoc
/**
 * The Class ShowHelpContainerEvent.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShowHelpContainerEvent extends GwtEvent<ShowHelpContainerEvent.ShowHelpContainerHandler> {

  /**
   * The Interface HasShowHelpContainerHandlers.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface HasShowHelpContainerHandlers extends HasHandlers {

    /**
     * Adds the show help container handler.
     * 
     * @param handler
     *          the handler
     * @return the handler registration
     */
    HandlerRegistration addShowHelpContainerHandler(ShowHelpContainerHandler handler);
  }

  /**
   * The Interface ShowHelpContainerHandler.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface ShowHelpContainerHandler extends EventHandler {

    /**
     * On show help container.
     * 
     * @param event
     *          the event
     */
    public void onShowHelpContainer(ShowHelpContainerEvent event);
  }

  /** The Constant TYPE. */
  private static final Type<ShowHelpContainerHandler> TYPE = new Type<ShowHelpContainerHandler>();

  /**
   * Fire.
   * 
   * @param source
   *          the source
   */
  public static void fire(final HasHandlers source) {
    source.fireEvent(new ShowHelpContainerEvent(null));
  }

  /**
   * Fire.
   * 
   * @param source
   *          the source
   * @param onTutorialClose
   *          the on tutorial close
   */
  public static void fire(final HasHandlers source, final TutorialViewer.OnTutorialClose onTutorialClose) {
    source.fireEvent(new ShowHelpContainerEvent(onTutorialClose));
  }

  /**
   * Gets the type.
   * 
   * @return the type
   */
  public static Type<ShowHelpContainerHandler> getType() {
    return TYPE;
  }

  /** The on tutorial close. */
  private final OnTutorialClose onTutorialClose;

  /**
   * Instantiates a new show help container event.
   * 
   * @param onTutorialClose
   *          the on tutorial close
   */
  public ShowHelpContainerEvent(final TutorialViewer.OnTutorialClose onTutorialClose) {
    this.onTutorialClose = onTutorialClose;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.event.shared.GwtEvent#dispatch(com.google.gwt.event.shared
   * .EventHandler)
   */
  @Override
  protected void dispatch(final ShowHelpContainerHandler handler) {
    handler.onShowHelpContainer(this);
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.gwt.event.shared.GwtEvent#getAssociatedType()
   */
  @Override
  public Type<ShowHelpContainerHandler> getAssociatedType() {
    return TYPE;
  }

  /**
   * Gets the on tutorial close.
   * 
   * @return the on tutorial close
   */
  public OnTutorialClose getOnTutorialClose() {
    return onTutorialClose;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.google.web.bindery.event.shared.Event#toString()
   */
  @Override
  public String toString() {
    return "ShowHelpContainerEvent[" + "]";
  }
}
