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

import com.google.gwt.core.client.JsArray;

/**
 * This is the main interface to communicate with the wave API. IIRC it is
 * exactly the same as the JS API + some convenience functions.
 * 
 * @author ”Jonas Huckestein”
 * 
 */
public interface Wave {

  /**
   * Get host, participant who added this gadget to the blip. Note that the host
   * may no longer be in the participant list.
   * 
   * @return host (null if not known)
   */
  Participant getHost();

  /**
   * Returns the mode the wave is in. Match with states in {@link Mode} class.
   * 
   * @return
   */
  int getMode();

  /**
   * Returns a Participant with the given id.
   * 
   * @param The
   *          id of the participant to retrieve
   * @return The participant with the given id
   */
  Participant getParticipantById(String id);

  /**
   * Returns a list of participants on the Wave.
   * 
   * @return Participant list.
   */
  JsArray<Participant> getParticipants();

  State getState();

  /**
   * Retrieves "gadget time" which is either the playback frame time in the
   * playback mode or the current time otherwise.
   * 
   * @return The gadget time
   */
  long getTime();

  /**
   * Get the participant whose client renders this gadget.
   * 
   * @return the viewer (null if not known)
   */
  Participant getViewer();

  /**
   * Indicates whether the gadget runs inside a wave container.
   * 
   * @return whether the gadget runs inside a wave container
   */
  boolean isInWaveContainer();

  /**
   * Returns the playback state of the wave/wavelet/gadget.
   * 
   * @return whether the gadget should be in the playback state
   */
  boolean isPlayback();

  /**
   * Logs a message in the wave log
   * 
   * @param log
   */
  void log(String log);

}