/**
 * Copyright 2010 Jonas Huckestein, jonas.huckestein@me.com, http://thezukunft.com
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

import com.google.gwt.core.client.JavaScriptObject;

public class Participant extends JavaScriptObject {

  protected Participant() {
  };

  /**
   * Gets the unique identifier of this participant.
   * 
   * @return The participant's id
   */
  public final native String getId() /*-{
                                     return this.getId();
                                     }-*/;

  /**
   * Gets the unique identifier of this participant.
   * 
   * @return The participant's display name
   */
  public final native String getDisplayName() /*-{
                                              return this.getDisplayName();
                                              }-*/;

  /**
   * Gets the url of the thumbnail image for this participant.
   * 
   * @return The participant's thumbnail image url.
   */
  public final native String getThumbnailUrl() /*-{
                                               return this.getThumbnailUrl();
                                               }-*/;

}