/**
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

/**
 * @fileoverview Tests basic functionality of the Embed API
 *
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */

/**
 * Stubs.
 */
var window = {};
var gadgets = {};
window.gadgets = gadgets;
gadgets.util = {};
gadgets.rpc = {};
gadgets.config = {};

/**
 * Setup a light fake implementation of gadget.rpc:
 */
var callbacks = {};

gadgets.rpc.call = function(frameid, method, c, param1, param2) {
  method += "_done";
  for (var name in callbacks){
    if (method == name) {
      tocall = callbacks[method];
      tocall['a'] = new Array(param1, param2);
      tocall['s'] = method;
      tocall['f'] = frameid;
      tocall['c'] = null;
      tocall.call(tocall);
      return;
    }
  }
}

gadgets.rpc.register = function(name, callback) {
  callbacks[name] = callback;
}

gadgets.rpc.registerDefault = function(method) {
}

gadgets.rpc.setRelayUrl = function(url) {
}

/**
 * Tests that we register available services on the gadget api,
 * and perform the appropriate initialization steps.
 */
function testServicesInitialized() {
  var initCalled = false;
  gadgets.config.init = function() {
    initCalled = true;
  };
  var embedApi = new EmbedApi();
  assertTrue(callbacks['load_wave']);
  assertTrue(callbacks['load_user_wave']);
  assertTrue(initCalled);
}

/**
 * Tests the event propagation system by simulating the
 * firing of the 'loaded' event.
 */
function testLoadedEventFired() {
  var embedApi = new EmbedApi();

  var loadCalled = false;
  gadgets.rpc.register('load_done', function() {
    loadCalled = true;
  });

  // Trigger the load event and make sure it propagates
  embedApi.loaded();
  assertTrue(loadCalled);
}

/**
 * Tests the event propagation system by simulating the
 * firing of the 'loaded' event.
 */
function testLoadWave() {
  var waveOpenId = '';
  window.rpcOpenWave = function(waveId) {
    waveOpenId = waveId;
  };

  var embedApi = new EmbedApi();
  var expectedWaveId = 'google.com!w+somewaveid';
  gadgets.rpc.call('frameId_1', 'load_wave', null, 'gadgetId', expectedWaveId);

  // Make sure the correct wave id was passed thru.
  assertEquals(expectedWaveId, waveOpenId);
}
