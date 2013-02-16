/**
 * Copyright 2010 Jonas Huckestein, jonas.huckestein@me.com, http://thezukunft.com
 * Copyright 2009 Hilbrand Bouwkamp, hs@bouwkamp.com
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 **/
package com.thezukunft.wave.connector;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event fired whenever the height of the gadget's contents changes
 *  
 */
public class GadgetUpdateEvent extends GwtEvent<GadgetUpdateEventHandler> {

	public static final GwtEvent.Type<GadgetUpdateEventHandler> TYPE = new GwtEvent.Type<GadgetUpdateEventHandler>();

  public GadgetUpdateEvent() {
  }

  protected void dispatch(GadgetUpdateEventHandler handler) {
    handler.onUpdate(this);
  }

  @SuppressWarnings("unchecked")
  public Type<GadgetUpdateEventHandler> getAssociatedType() {
    return (Type) TYPE;
  }

}
