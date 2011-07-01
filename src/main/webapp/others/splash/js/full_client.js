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
var RPC_UPDATE_INTERVAL_MS = 3000;

/**
 * Application entry point.
 */
$(document).ready(function() {
  initCommon();

  // Set up the wave panel.
  var wave = $('#wave');

  // toolbar on hover
  $('.blip').live('mouseenter', function() {
    if (!isEditing()) {
      showToolbar($(this));
    }
  }).live('mouseleave', function() {
    hideToolbar($(this));
  });

  // Dismiss yellow alert.
  $('#alertbar').click(function() {
    $(this).fadeOut();
  });

  // Clicking on a feed triggers an animation to open it.
  $('.item').live('click', function() {
    // First close the wave window if it's open (and erase current conversation)
    $('#conversation').html('<img id="wave-loading" src="images/wave-loading.gif">');
    $('#wave-header').hide();

    var item = $(this);

    // Send RPC to open wave at this point.
    // TODO(dhanji): Make it possible to have many waves open
    window.waveOptions_.waveId = item.attr('wave_id');
    window.waveOptions_.waveVersion = -1;
    window.waveOptions_.page = 0;
    window.wave.Rpc.getInstance().rpcOpenWave();
  });

  // Search box actions.
  var searchBox = $('#searchField');

  // Watermark on/off
  $('#searchField').focus(function() {
    searchBox.text('');
  });

  $('#searchField').blur(function() {
    if (searchBox.text() == '') {
      searchBox.text('Search your waves');
    }
  });

  initEditor();
  initGadgetSystem();

  // Enable touch scrolling for both our panels.
  $('#feed').jScrollTouch();
  // TODO(dhanji): Do this for the conversation too.

  var rpc = window.wave.Rpc.getInstance();

  // Run search immediately for our inbox.    
  rpc.rpcSearch();
  setInterval(function() {
    // This is needed to specify the scope of the function.
    rpc.rpcAsyncUpdate.call(rpc);
  }, RPC_UPDATE_INTERVAL_MS);
});

function onSearch() {
  // Clear feed.
  $('#feed')
      .html('<img id="feed-loading" src="images/loading.gif" alt="Loading feed contents...">'
      + '<div id="more-feed" class="more-button">Show More...</div>');

  // Search for new query content
  window.waveOptions_.query = $('#searchField').attr('value');
  window.wave.Rpc.getInstance().rpcSearch();
}
