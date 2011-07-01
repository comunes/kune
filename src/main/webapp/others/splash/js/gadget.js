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
 * @fileoverview Gadget support.
 *
 * @author David Byttow
 */

// TODO(dhanji): we should eventually extract the relay host from
//  the iframe source rather than hardcoding it.
var REMOTE_RPC_RELAY_URL =
    "http://www.gmodules.com/gadgets/files/container/rpc_relay.html";

var DEFAULT_GADGET_MODE = {'${playback}': '1', '${edit}': '0'};

/**
 * Registers an rpc handler.
 *
 * @param service the name of the service.
 * @param handler the handler, which is of type
 *     Function(string, string, array.<string>)
 */
function registerRpc(service, handler) {
  gadgets.rpc.register(service, function() {
    var service = this['s'];
    var gadgetId = this['f'];
    var args = this['a'];
    handler(service, gadgetId, args);
  });
}

/**
 * Extracts gadget state out of the DOM and pushes it down via gadgets.rpc and
 * clear the DOM of the unnecessary state after.
 *
 * @param gadgetId the gadget id to extract.
 */
function extractGadgetState(gadgetId) {
  // TODO: Use global objects or pool?
  var state = {};
  var participants = {
      // TODO: Should be proper ID.
      myId: "anonymous@a.gwave.com",
      authorId: "",
      participants: {}
  };

  // Fill in state and participants.
  var id = '#' + gadgetId + '_state';
  // Single loop and then if-else switch, rather than multiple loops.
  $(id).find('span').each(function() {
    var className = $(this).attr('class');
    var key = $(this).attr('hiddenKey');
    var value = $(this).text()
    if ($(this).hasClass('gadget-state')) {
      state[key] = value;
    } else if ($(this).hasClass('gadget-participant')) {
      // TODO: Profile thumbnail and displayName.
      var p = {
          'id': value,
          'displayName': value,
          'thumbnailUrl': ''
      };
      participants['participants'][value] = p;
    } else if ($(this).hasClass('gadget-author')) {
      participants['authorId'] = value;
    }
    if (key) {
      state[key] = value;
    }
  });

  // TODO: Enable gadget updates.
  gadgets.rpc.call(gadgetId, "wave_gadget_mode", null, DEFAULT_GADGET_MODE);
  gadgets.rpc.call(gadgetId, "wave_participants", null, participants);
  gadgets.rpc.call(gadgetId, "wave_gadget_state", null, state);
  // TODO: Deliver the real private state to the gadgets.
  gadgets.rpc.call(gadgetId, "wave_private_gadget_state", null, {});

  // Clear state out of DOM.
  $(id).remove();
}

/**
 * Initializes the gadget system, call this once at startup.
 */
function initGadgetSystem() {
  // Once a gadget has called us back, we can inject the state/participants.
  registerRpc("wave_enable", function(service, gadgetId, args) {
    gadgets.rpc.setRelayUrl(gadgetId, REMOTE_RPC_RELAY_URL);
    extractGadgetState(gadgetId);
  });

  registerRpc("resize_iframe", function(service, gadgetId, args) {
    $('#' + gadgetId).attr('height', args[0]);
  });

  gadgets.rpc.registerDefault(function() {
    var eventType = this['s'];
    var eventObj = this['a'][0];
    //log(eventType + ": " + eventObj);
  });
}

