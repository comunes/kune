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
 * @fileoverview Client/Server RPC abstraction and UI update callback layer.
 * The current user is provided to us by means for the global
 * <code>BOOTSTRAP</code> constant, in the containing HTML template. This
 * file also relies on the presence of the jQuery 1.4.2 library
 * (http://code.jquery.com/jquery-1.4.2.js) and the jQuery lightbox 0.5.0
 * plugin (http://leandrovieira.com/projects/jquery/lightbox/).
 * We also require <code>window.waveOptions_</code> to be an object that is
 * set to the current client state (essentially the open wave + version).
 * Classes:
 *   wave.DataView
 *   wave.Rpc
 * @author dhanji@google.com (Dhanji R. Prasanna)
 */


var wave = wave || {};

/**
 * Global user info.
 *
 * @type {!Object}
 */
wave.user = wave.user || {};

/**
 * A registry of UI callbacks.
 *
 * @type {!Object.<string,function()>}
 * @private
 */
wave.uiCallbacks_ = wave.uiCallbacks_ || {};

/**
 * @return {string} Email address of the current user.
 */
wave.user.getEmail = function() {
  // BOOTSTRAP is current user info provided by the HTML template.
  return BOOTSTRAP.email;
};

/**
 * @return {string} Full name of the current user.
 */
wave.user.getDisplayName = function() {
  // BOOTSTRAP is current user info provided by the HTML template.
  return BOOTSTRAP.displayName;
};


/**
 * Represents all UI access to the data controls of the current client.
 * Data from/to the server should be read and written to the UI using
 * this class.
 *
 * @constructor
 * @export
 */
wave.DataView = function() {
  /**
   * List of dirty blips that represent pending edits to the wave.
   *
   * @type {!Array<Element>}
   * @private
   */
  this.pendingEdits_ = [];

  /**
   * A reference to the conversation element of the wave.
   *
   * @type {Element}
   * @private
   */
  this.conversation_ = $('#conversation');
};

/**
 * The nominal height of the wave header in pixels.
 *
 * @type {number}
 * @private
 */
wave.DataView.WAVE_HEADER_HEIGHT_PX_ = 18;

/**
 * Gathers all the edited blips from the DOM (marked by class content-dirty)
 * and adds them to the current client state as new or edited blips.
 */
wave.DataView.prototype.addPendingEdits = function() {
  var requestId = 0;
  $('.content-dirty').each(function() {
    var dirtyRef = $(this);
    var blipId = dirtyRef.attr('blip_id');
    var blipRef = $('#' + blipId);
    var parentId = blipRef.data('parentId');
    var threadId = blipRef.parent('div.thread').attr('id');
    var content = dirtyRef.html();
    if (parentId) {
      // If the parent id exists, we assume this is a new reply.
      window.waveOptions_['newblip_' + requestId + '_previousBlip'] = parentId;
      window.waveOptions_['newblip_' + requestId + '_text'] = content;
      window.waveOptions_['newblip_' + requestId + '_threadId'] = threadId;
    } else {
      window.waveOptions_['editblip_' + blipId] = content;
    }
    requestId++;
    this.pendingEdits_.push(dirtyRef);
  });
};

/**
 * Clears all the edited blips state stored in the client state, typically
 * called after this state has been synchronized with the server. Acts as
 * as complement to <code>addPendingEdits</code>.
 */
wave.DataView.prototype.clearPendingEdits = function() {
  var requestId = 0;
  for (var i = 0; i < this.pendingEdits_.length; i++) {
    var dirtyRef = this.pendingEdits[i];
    var blipId = dirtyRef.attr('blip_id');
    var blipRef = $('#' + blipId);
    var parentId = blipRef.data('parentId');

    delete window.waveOptions_['newblip_' + requestId + '_previousBlip'];
    delete window.waveOptions_['newblip_' + requestId + '_text'];
    delete window.waveOptions_['newblip_' + requestId + '_threadId'];
    delete window.waveOptions_['editblip_' + blipId];
    dirtyRef.removeClass('content-dirty');
    requestId++;
  }
  
  // Finally, clear all pending edits.
  this.pendingEdits_.length = 0;
};

/**
 * Updates the the time-measure field with the value from the last
 * action. For example, "Search completed in XX.Xs"
 *
 * @param {string} html A String of processed html representing the
 *     measurement to be displayed.
 */
wave.DataView.prototype.measure = function(html) {
  $('#measure').html(html);
};

/**
 * Appends a conversation fragment to the existing wave conversation.
 *
 * @param {string} html A String of processed html representing the
 *     fragment of conversation to be appended.
 */
wave.DataView.prototype.appendConversation = function(html) {
  this.conversation_.append($(html));
  $('#wave-loading').remove();
  $('#wave-header').show();

  // Set up the lightbox for any slideshows in the wave (using the jQuery
  // lightbox plugin).
  $('a.lightbox').lightBox();
};

/**
 * Replaces the existing wave conversation with a newly rendered html
 * conversation.
 *
 * @param {string} html A string of processed html representing the
 *     fragment of conversation to place.
 * @return {boolean} True if there is more conversation to be loaded for
 *     this wave.
 */
wave.DataView.prototype.insertConversation = function(html) {
  this.conversation_.html($(html));

  // Set up the lightbox for any slideshows in the wave.
  $('a.lightbox').lightBox();

  return $('#wave-loading').length > 0;
};

/**
 * Replaces the existing wave header with a newly rendered html
 * list of participants.
 *
 * @param {string} html A string of processed html representing the
 *     header of the wave.
 */
wave.DataView.prototype.insertHeader = function(html) {
  var headerRef = $('#wave-header');
  headerRef.removeClass('expanded');
  headerRef.html($(html));
  headerRef.show();

  // Determine the actual height with all participants.
  var participantsRef = $('.participants');
  participantsRef.css('height', 'auto');
  var height = participantsRef.height();
  participantsRef.css('height', null);

  // Display a "show more" option if the content height is greater than
  // the default height of the wave header.
  if (height > wave.DataView.WAVE_HEADER_HEIGHT_PX_) {
    $('.header .detail').show();
  }
};

/**
 * Deletes the specified blip from view with an animation.
 *
 * @param {string} blipId A DOM selector for the blip to delete.
 */
wave.DataView.prototype.deleteBlip = function(blipId) {
  // Animate by sliding it up out of view.
  $(blipId).slideUp(function() {
    $(this).remove();
  });
};

/**
 * Inserts a new blip into the wave conversation at the appropriate spot in the
 * DOM.
 *
 * @param {string} blipId Id of the blip to be inserted into the DOM.
 * @param {string=} opt_parent DOM selector of the parent blip to insert under
 *     (optional).
 * @param {boolean=} opt_indent True if this blip should be rendered inside a
 *     new indent level under the parent. Default is false (optional).
 * @param {string=} opt_html Processed html containing the content of the blip
 *     itself to be inserted directly into the DOM (optional).
 * @param {number=} opt_version Version number of the of the new blip
 *     (optional).
 * @return {Element} A reference to the blip DOM element that was just inserted.
 */
wave.DataView.prototype.insertBlip = function(blipId, opt_parent, opt_indent,
    opt_html, opt_version) {
  var indent = opt_indent || false;
  var html = opt_html || 'TEST';
  var version = opt_version || '0';

  // Construct a blip html fragment and slide it in.
  var htmlBuilder = [
    '<div id="',
    blipId,
    '" class="blip" style="display:none">',
    html,
    '</div>'
  ];
  blipRef = $(htmlBuilder.join(''));

  // Handle the indented reply case.
  if (!opt_parent) {
    // If this is the root blip. Add to container.
    this.conversation_.prepend(blip);
  } else if (indent) {
    // See if the parent blip already has an inline child.
    var parentRef = $(opt_parent);
    var indentRef = parentRef.children('.indent');
    if (indentRef.length) {
      // If it does, insert into it.
      indentRef.append(blipRef);
    } else {
      // Insert an indent wrapper.
      indentRef = $('<div class="indent"></div>');
      parentRef.after(indentRef);
      indentRef.append(blipRef);
    }
  } else {
    $(opt_parent).after(blipRef);
  }
  return blipRef;
};

/**
 * Updates an existing blip in the wave conversation in place.
 *
 * @param {string} blipId A DOM selector of the blip id to update in place.
 * @param {string} html Processed HTML of the new content of the blip.
 */
wave.DataView.prototype.replaceBlip = function(blipId, html) {
  var blip = $(blipId);

  // Animated transition from the old to the new content.
  blip.fadeTo('slow', 0, function() {
    blip.html(html);
    blip.fadeTo('slow', 1);
  });
};

/**
 * Inserts the incoming HTML into the feed before the more feed button, and
 * removes the feed loading indicator.
 *
 * @param {string} html Processed HTML of the new content of the feed.
 */
wave.DataView.prototype.insertFeed = function(html) {
  var newFeed = $(html);

  // Insert fragment into feed invisible, then slide in.
  $('#feed-loading').remove();

  // Insert it before the more button.
  $('#more-feed').before(newFeed);
  newFeed.show();
};

/**
 * Tests if an element(s) exists in the DOM by its selector.
 *
 * @param {string} selector A DOM selector in CSS fashion.
 * @return {boolean} True if this CSS selector exists in the DOM.
 */
wave.DataView.prototype.exists = function(selector) {
  return $(selector).length > 0;
};

/**
 * Determines if the given client action data is newer than the version of the
 * existing blip.
 *
 * @param {!Object} data A client action that may or may not contain a version.
 * @param {Element} blip A blip element on the page.
 * @return {boolean} True if the data is newer than the blip on page.
 */
wave.DataView.prototype.hasNewer = function(data, blip) {
  return data.version && blip.data('version') >= data.version;
};

/**
 * Handles all high-level RPC communication with the web server.
 *
 * @param {!wave.DataView} dataView A handle to the underlying data view.
 * @export
 * @constructor
 */
wave.Rpc = function(dataView) {
  /**
   * The singleton data view to modify the UI.
   *
   * @type {wave.DataView}
   * @private
   */
  this.dataView_ = dataView;
  // Register global RPC server callbacks.
  this.callbacks_ = {
    'add-blip': this.addBlipCallback_,
    'update-blip': this.updateBlipCallback_,
    'delete-blip': this.deleteBlipCallback_,
    'update-feed': this.updateFeedCallback_,
    'update-wave-version': this.updateWaveVersionCallback_,
    'update-wave': this.updateWaveCallback_,
    'update-header': this.updateHeaderCallback_,
    'measure': this.measureCallback_
  };
};

/**
 * Global instance.
 *
 * @type {wave.Rpc}
 * @private
 */
wave.Rpc.instance_ = null;

/**
 * Singleton factory method.
 *
 * @return {wave.Rpc} Singleton instance of <code>wave.Rpc</code> class.
 * @export
 */
wave.Rpc.getInstance = function() {
  if (!wave.Rpc.instance_) {
    wave.Rpc.instance_ = new wave.Rpc(new wave.DataView());
  }

  return wave.Rpc.instance_;
};

/**
 * Default RPC endpoints requested by this edition of the client.
 * A comma-separated list of rpc names that will be executed each
 * time a short-poll request is made in the background.
 *
 * @type {string}
 * @private
 */
wave.Rpc.RPC_UPDATE_OPTIONS_ = 'update_wave,edit_wave';

/**
 * The Ajax URL where RPCs should be sent for background batch
 * execution. This URL accepts only POST messages.
 *
 * @type {string}
 * @private
 */
wave.Rpc.ASYNC_UPDATE_URL_ = '/async/update?rpc=' +
    wave.Rpc.RPC_UPDATE_OPTIONS_;

/**
 * The Ajax URL for executing searches immediately.
 *
 * @type {string}
 * @private
 */
wave.Rpc.SYNC_SEARCH_URL_ = '/async/search?rpc=search';

/**
 * The Ajax URL for executing wave-open requests immediately.
 *
 * @type {string}
 * @private
 */
wave.Rpc.SYNC_WAVE_URL_ = '/async/wave?rpc=open_wave';

/**
 * An internal map of RPC names to callbacks.
 *
 * @type {Object.<string,function(Object)>}
 * @private
 */
wave.Rpc.prototype.callbacks_;

/**
 * Executes a search RPC immediately. Responses are handled
 * via <code>updateFeedCallback</code>.
 *
 * @export
 */
wave.Rpc.prototype.rpcSearch = function() {
  this.rpc_(wave.Rpc.SYNC_SEARCH_URL_);
};

/**
 * Executes an open wave RPC immediately. Responses are handled
 * via <code>updateWaveCallback</code>.
 *
 * @export
 */
wave.Rpc.prototype.rpcOpenWave = function() {
  this.rpc_(wave.Rpc.SYNC_WAVE_URL_);
};

/**
 * Synchronizes the client state with the server via a periodic
 * short poll. This function is typically called by a timer and
 * sends a batch of RPCs including edits, new blip additions, etc.
 *
 * @export
 */
wave.Rpc.prototype.rpcAsyncUpdate = function() {
  this.dataView_.addPendingEdits();
  this.rpc_(wave.Rpc.ASYNC_UPDATE_URL_);
  this.dataView_.clearPendingEdits();
};

/**
 * Tests if the given wave id is currently open in the client.
 *
 * @param {string} waveId A wave id of the form "[domain.com]![id]"
 *     as described in the wave protocol specification.
 * @return {boolean} True if the given wave id is currently open.
 * @private
 */
wave.Rpc.prototype.isCurrentWave_ = function(waveId) {
  return window.waveOptions_.waveId == waveId;
};

/**
 * A callback for handling network communication failure in the background.
 * Displays a useful message to the user.
 * TODO(dhanji): Revisit for i18n.
 *
 * @private
 */
wave.Rpc.prototype.onAjaxFailure_ = function() {
  alert('Oops, there was a network failure. Please give it a few seconds' +
      ' and try again.');
};

/**
 * The central RPC invoker, takes the entire state of the client and posts it
 * as name/value pairs to a given RPC endpoint. Asynchronous responses are
 * processed in <code>handleRpcResponse_</code>.
 *
 * @param {string} url  The relative URL representing an RPC endpoint.
 * @private
 */
wave.Rpc.prototype.rpc_ = function(url) {
  var self = this;
  var success = function(data) {
    self.handleRpcResponse_.call(self, data);
  };
  $.ajax({
    type: 'POST',
    url: url,
    dataType: 'json',
    data: window.waveOptions_,
    success: success,
    failure: self.onAjaxFailure_
  });
};

/**
 * Callback dispatcher, invoked when the server responds with
 * any data at all, dispatches to the various *Callback() functions.
 *
 * @param {Array.<Object>} data The server response in the form of a
 *     list of <code>ClientAction</code> objects.
 * @private
 */
wave.Rpc.prototype.handleRpcResponse_ = function(data) {
  // Data is a list of client actions to perform.
  if (data == null) {
    warn('handleRpcResponse_ called with null data');
    return;
  }
  for (var i = 0; i < data.length; i++) {
    var clientAction = data[i];

    // Dispatch result based on action name.
    this.callbacks_[clientAction.action].call(this, clientAction);
  }
};

/**
 * Updates the the time-measure field with the value from the last
 * action. For example, "Search completed in XX.Xs"
 *
 * @param {!Object} data A client action containing processed html
 *     representing the measurement to be displayed.
 * @private
 */
wave.Rpc.prototype.measureCallback_ = function(data) {
  this.dataView_.measure(data.html);
};

/**
 * Updates the wave version that the client thinks it has. This is generally
 * done when we have the streaming blip updates set, so we know to request a
 * diff from the known version next time.
 *
 * @param {!Object} data A client action containing the current version of the
 *     wave being displayed.
 * @private
 */
wave.Rpc.prototype.updateWaveVersionCallback_ = function(data) {
  if (!this.isCurrentWave_(data.waveId)) {
    return;
  }

  // Store the wave version as the current wave version.
  window.waveOptions_.waveVersion = data.version;
};

/**
 * Updates the wave window with an entire pre-rendered wave. Used for
 * entire wave open, rather than incremental updates/adds of blips.
 * This function actually pages in the wave, by loading an initial fragment
 * and then if necessary issuing an immediate background request for the rest
 * of the conversation.
 *
 * @param {!Object} data A client action containing the rendered html
 *    of the currently open wave conversation.
 * @private
 */
wave.Rpc.prototype.updateWaveCallback_ = function(data) {
  if (!this.isCurrentWave_(data.waveId)) {
    return;
  }

  // Store the version we just fetched.
  window.waveOptions_.waveVersion = data.version;

  if (window.waveOptions_.page > 0) {
    this.dataView_.appendConversation(data.html);
  } else {
    var hasMore = this.dataView_.insertConversation(data.html);
    if (hasMore) {
      // Advance the page and call wave open again (any page > 0 is the
      // rest of the conversation).
      window.waveOptions_.page = 1;
      this.rpcOpenWave();
    }
  }

  // Callback various event handlers to say the wave is ready.
  if (window.embedApi) {
    window.embedApi.loaded();
  }
  if (wave.uiCallbacks_.waveOpened) {
    wave.uiCallbacks_.waveOpened();
  }
};

/**
 * Updates the wave window header with participant information and avatars.
 * Replaces any existing content in the header.
 *
 * @param {!Object} data A client action object containing the processed html
 *     with participants and avatars.
 * @private
 */
wave.Rpc.prototype.updateHeaderCallback_ = function(data) {
  if (!this.isCurrentWave_(data.waveId)) {
    return;
  }

  this.dataView_.insertHeader(data.html);
};

/**
 * Updates the feed column with newer digest items, inserts
 * them directly into the feed. Appends these items to the feed
 * rather than replacing them directly.
 *
 * @param {!Object} data A client action containing processed html
 *     of the entire list of feed items.
 * @private
 */
wave.Rpc.prototype.updateFeedCallback_ = function(data) {
  this.dataView_.insertFeed(data.html);

  if (wave.uiCallbacks_.feedOpened) {
    wave.uiCallbacks_.feedOpened();
  }
};

/**
 * Deletes an existing blip from the wave conversation.
 *
 * @param {!Object} data A client action object containing a DOM selector
 *     of the blip id to be deleted.
 * @private
 */
wave.Rpc.prototype.deleteBlipCallback_ = function(data) {
  // We already have this version.
  if (this.dataView_.hasNewer(data, $(data.blipId))) {
    return;
  }

  this.dataView_.deleteBlip(data.blipId);
};

/**
 * Append blip to the current wave conversation. If this blip already
 * exists in the DOM, perform an update-in-place with the new content
 * instead.
 *
 * @param {!Object} data A client action object containing the blip id
 *      of the the new blip and processed HTML of its content.
 * @private
 */
wave.Rpc.prototype.addBlipCallback_ = function(data) {
  // Kill the loading indicator if it's on.
  if ($('#loading').length > 0) {
    $('#loading').hide();
  }

  // If this element already exists, update it in place instead.
  var selector = '#' + data.blipId;
  if (this.dataView_.exists(selector)) {
    data.blipId = selector;
    this.updateBlipCallback_(data);
    return;
  }

  this.dataView_.insertBlip(data.blipId, data.parent, data.indent, data.html,
      data.version).slideDown();
};

/**
 * Update blip in place, with new version. Fades out old content, replace, and
 * fades in new content.
 *
 * @param {!Object} data A client action object containing DOM selector of the
 *     blip to update, its version for comparison and html content of the new
 *     state of the blip.
 * @private
 */
wave.Rpc.prototype.updateBlipCallback_ = function(data) {
  var blip = $(data.blipId);

  // We already have this version.
  if (this.dataView_.hasNewer(data, $(data.blipId))) {
    return;
  }

  this.dataView_.replaceBlip(data.blipId, data.html);

  // Remember this version so we ignore redundant updates.
  blip.data('version', data.version);
};
