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
* /**
 * @fileoverview Gadgets-RPC relay and embed client x-iframe support.
 *
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */

window.wave = window.wave || {};
var wave = window.wave;

/**
 * Service name for loading a new wave in the current view.
 */
var LOAD_WAVE = "load_wave";

/**
 * Service name for loading a new wave in the current view,
 * specific to a given user (without an explicit wave id).
 */
var LOAD_USER_WAVE = "load_user_wave";

/**
 * Event that is fired when wave load completes.
 */
var LOAD_DONE = "load_done";

/**
 * Api to communicate with the parent of this iframe.
 * @constructor
 * @export
 */
wave.EmbedApi = function() {
  // Tests if gadgets library is loaded.
  if (window.gadgets && window.gadgets.rpc && window.gadgets.config) {
    this.configParentRelay_();
    this.registerService(LOAD_WAVE);
    this.registerService(LOAD_USER_WAVE);
  } else {
    throw "Error: gadget library has not loaded yet.";
  }
};

/**
 * Default configuration for the gadget relay.
 */
wave.EmbedApi.GADGET_CONFIG = {
  rpc : {
    useLegacyProtocol : false,
    parentRelayUrl :
        "http://wave.google.com/gadgets/files/container/rpc_relay.html"
  },
  "core.io" : {
    jsonProxyUrl : "http://%host%/gadgets/makeRequest",
    proxyUrl : "http://%host%/gadgets/proxy?refresh=%refresh%&url=%url%"
  }
};

/**
 * Registers an inter-iframe callback service provided by the embedded client.
 *
 * @param service {string} the name of the service.
 */
wave.EmbedApi.prototype.registerService = function(service) {
  var hub = window.gadgets.rpc;
  if (hub) {
    hub.register(service, function() {
      var service = this['s'];
      var gadgetId = this['f'];
      var args = this['a'];
      this.callback(service, gadgetId, args);
    });
  }
};

/**
 * Configures parent relay to send RPCs to wave-embedding container.
 * @private
 */
wave.EmbedApi.prototype.configParentRelay_ = function() {
  window.gadgets.config.init(wave.EmbedApi.GADGET_CONFIG);
};

/**
 * Fires an event to the outer page via a callback.
 *
 * @private
 * @param event {string} indicating the name of the event to fire.
 * @param opt_args {Array.<Object>=} a list of parameters that the other side
 *     needs specific to the event (optional).
 */
wave.EmbedApi.prototype.fireEvent = function(event, opt_args) {
  if (window.gadgets && window.gadgets.rpc) {
    var base = [null, event, null];
    var args = base.concat(opt_args);
    window.gadgets.rpc.call.apply(null, args);
  }
};

/**
 * Receives events from the outer page (complement to
 *     <code>wave.EmbedApi.fireEvent()</code>).
 * @export
 * @param service {string} The name of the service that the gadget wants to
 *     call.
 * @param gadgetId {string} The id of the gadget that initiated the RPC.
 * @param args {Array<Object>} Generic RPC call argument array.
 */
wave.EmbedApi.prototype.callback = function(service, gadgetId, args) {
  if (LOAD_WAVE == service) {
    // load a new wave with wave id
    window.wave.Rpc.getInstance().rpcOpenWave(args[0]);
  } else if (LOAD_USER_WAVE == service) {
    // load a "myembed" wave
    // TODO(dhanji): Call the appropriate rpc when it is available.
  }
};

/**
 * Sends a notification to the containing page that wave load has completed.
 *
 * @export
 */
wave.EmbedApi.prototype.loaded = function() {
  this.fireEvent(LOAD_DONE);
};

/**
 * Register the singleton instance of <code>EmbedApi</code> as soon as possible.
 */
(wave.EmbedApi.initialize_ = function() {
  window.wave.embedApi = window.wave.embedApi || new wave.EmbedApi();
})();

