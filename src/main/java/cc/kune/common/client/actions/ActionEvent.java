/* ActionEvent.java -- an action has been triggered
   Copyright (C) 1999, 2002, 2005, 2012  Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
02110-1301 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package cc.kune.common.client.actions;

import com.google.gwt.user.client.Event;

// TODO: Auto-generated Javadoc
/**
 * This event is generated when an action on a component (such as a button
 * press) occurs.
 * 
 * @author Aaron M. Renn (arenn@urbanophile.com)
 * @author Adapted version for GWT (C) The kune development team
 * @see ActionListener
 * @since 1.1
 * @status updated to 1.4
 */
public class ActionEvent {

  /** The Constant NO_TARGET. */
  public static final Object NO_TARGET = String.valueOf("No target");
  
  /** The event. */
  private final Event event;
  
  /** The source. */
  private final Object source;
  
  /** The target. */
  private final Object target;

  /**
   * Instantiates a new action event.
   *
   * @param source the source
   * @param event the event
   */
  public ActionEvent(final Object source, final Event event) {
    this(source, NO_TARGET, event);
  }

  /**
   * Instantiates a new action event.
   *
   * @param source the source
   * @param target the target
   * @param event the event
   */
  public ActionEvent(final Object source, final Object target, final Event event) {
    this.source = source;
    this.event = event;
    this.target = target;
  }

  /**
   * Gets the event.
   *
   * @return the event
   */
  public Event getEvent() {
    return event;
  }

  /**
   * Gets the source.
   *
   * @return the source
   */
  public Object getSource() {
    return source;
  }

  /**
   * Gets the target.
   *
   * @return the target
   */
  public Object getTarget() {
    return target;
  }
}
