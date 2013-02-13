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
package com.thezukunft.wave.connectormock;

import java.util.HashMap;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.Window;
import com.thezukunft.wave.connector.State;

public class StateMock implements State {

	protected HashMap<String, String> map;
	private WaveMock wave;
	
	public StateMock() {
		map = new HashMap<String, String>();
	}
	
	@Override
	public String get(String key) {
		return map.get(key);
	}

	@Override
	public String get(String key, String optDefault) {
		if(map.containsKey(key)) return map.get(key);
		else return optDefault;
	}

	@Override
	public Integer getInt(String key) {
		if(!map.containsKey(key)) return null;
		Integer value;
		try {
			value = Integer.parseInt(map.get(key));
		} catch(NumberFormatException e) {
			return null;
		}
		return value;
	}

	@Override
	public JsArrayString getKeys() {
		// this seems pretty inefficient but oh well ...
		JsArrayString array = JsArray.createArray().cast();
		for(String k : map.keySet()) {
			array.push(k);
		}
		return array;
	}

	@Override
	public void reset() {
		map.clear();
	}

	@Override
	public void submitDelta(HashMap<String, String> delta) {
		for(String k : delta.keySet()) {
			map.put(k, delta.get(k));
		}
		wave.manualStateChange();
	}

	@Override
	public void submitDelta(JavaScriptObject delta) {
		Window.alert("try to submit deltas as a hash-map ...  this doesnt work in mock mode");
	}

	@Override
	public void submitValue(String key, String value) {
		HashMap<String, String> delta = new HashMap<String, String>();
		delta.put(key, value);
		submitDelta(delta);
	}

	
	public void setWave(WaveMock wave) {
		this.wave = wave;
	}
	
}
