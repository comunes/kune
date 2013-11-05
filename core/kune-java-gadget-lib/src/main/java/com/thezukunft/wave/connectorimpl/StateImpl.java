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
package com.thezukunft.wave.connectorimpl;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.thezukunft.wave.connector.State;

/**
 * State class for managing the gadget state.
 */
public class StateImpl extends JavaScriptObject implements State {

  /**
   * JavaScript version of map.
   * 
   * @param <V>
   *          value type
   */
  private static class JsMap<V> extends JavaScriptObject {

    public static StateImpl.JsMap<?> create() {
      return JavaScriptObject.createObject().cast();
    }

    @SuppressWarnings("unused")
    protected JsMap() {
    }

    @SuppressWarnings("unused")
    public final native V unsafeGet(String key) /*-{
                                                return this[key];
                                                }-*/;

    public final native V unsafePut(String key, V value) /*-{
                                                         var oldValue = this[key] || null;
                                                         this[key] = value;
                                                         return oldValue || value;
                                                         }-*/;
  }

  protected StateImpl() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.onetwopoll.wave.State#get(java.lang.String)
   */
  public final native String get(String key) /*-{
                                             return this.get(key);
                                             }-*/;

  /*
   * (non-Javadoc)
   * 
   * @see com.onetwopoll.wave.State#getInt(java.lang.String)
   */
  public final Integer getInt(String key) {
    final String value = get(key);

    return value != null ? Integer.decode(value) : null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.onetwopoll.wave.State#get(java.lang.String, java.lang.String)
   */
  public final native String get(String key, String opt_default) /*-{
                                                                 return this.get(key, opt_default);
                                                                 }-*/;

  /*
   * (non-Javadoc)
   * 
   * @see com.onetwopoll.wave.State#getKeys()
   */
  public final native JsArrayString getKeys() /*-{
                                              return this.getKeys();
                                              }-*/;

  /*
   * (non-Javadoc)
   * 
   * @see com.onetwopoll.wave.State#reset()
   */
  public final native void reset() /*-{
                                   this.reset();
                                   }-*/;

  /*
   * (non-Javadoc)
   * 
   * @see com.onetwopoll.wave.State#submitDelta(java.util.HashMap)
   */
  public final void submitDelta(HashMap<String, String> delta) {
    final JsMap<String> jsDelta = JsMap.create().cast();

    for (String key : delta.keySet()) {
      jsDelta.unsafePut(key, delta.get(key));
    }
    submitDelta(jsDelta);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.onetwopoll.wave.State#submitDelta(com.google.gwt.core.client.
   * JavaScriptObject)
   */
  public final native void submitDelta(JavaScriptObject delta) /*-{
                                                               this.submitDelta(delta);
                                                               }-*/;

  /*
   * (non-Javadoc)
   * 
   * @see com.onetwopoll.wave.State#submitValue(java.lang.String,
   * java.lang.String)
   */
  public final native void submitValue(String key, String value) /*-{
                                                                 this.submitValue(key, value);
                                                                 }-*/;
}
