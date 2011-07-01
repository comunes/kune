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
function log(msg) {
  if (window.console && window.console.log) {
    console.log(msg);
  }
}

function warn(msg) {
  if (window.console && window.console.warn) {
    console.warn(msg);
  }
}

function showToolbar(blip) {
  var toolbar = $('.toolbar', blip);
  // stop currently running fade
  toolbar.stop(true, true);
  toolbar.delay(200).fadeIn('fast'); // delay to prevent spurious hovers
}

function hideToolbar(blip) {
  var toolbar = $('.toolbar', blip);
  toolbar.stop(true, true);
  toolbar.delay(200).fadeOut('fast');
}

function expandHeader() {
  var participantsRef = $('.participants');
  participantsRef.addClass('expanded');
  participantsRef.css('height', 'auto');
  var height = participantsRef.height();
  participantsRef.css('height', null);
  participantsRef.animate({height: height});
  $('.header .detail').text("Hide");
}

function collapseHeader() {
  var participantsRef = $('.participants');
  participantsRef.removeClass('expanded');
  participantsRef.animate({height: 18});
  $('.header .detail').text("Show");
}

function toggleHeader() {
  if ($('.participants').hasClass('expanded')) {
    collapseHeader();
  } else {
    expandHeader();
  }
}

/**
 * Application entry point.
 */
function initCommon() {
  // Global client state, tells server what we desire each poll.
  // Add a key to this object to create an open state.
  // Manages open searches, waves, etc.
  var waveOptions = {};
  waveOptions.query = "";
  waveOptions.startAt = 0;
  waveOptions.numResults = 10;
  waveOptions.currentFeedTime = 0;
  waveOptions.page = 0;

  window.waveOptions_ = waveOptions;

  $('.header .detail').live('click', function() {
    toggleHeader();
  });

  // Search box actions.
  var searchBox = $('#searchField');

  // Execute search.
  searchBox.keydown(function(event) {
    if (event.keyCode == 13) { /*Enter hit*/
      onSearch()
    }
  });
  $('#searchButton').click(onSearch);

  // Page in more feed items.
  $('#more-feed').live('click', function() {
    // Advance the page and call rpcSearch again.
    window.waveOptions_.startAt += window.waveOptions_.numResults;

    wave.Rpc.getInstance().rpcSearch();
  });

  // Page in more of the wave if applicable.
  // TODO(dhanji): get rid of this or hide behind flag?
  $('#more-wave').live('click', function() {
    // Advance the page and call wave open again.
    window.waveOptions_.page += 1;

    $(this).remove(); // it gets re-rendered.
    wave.Rpc.getInstance().rpcOpenWave();
  });

  // Listen for expansion of collapsed inline replies.
  // TODO(dhanji): Left factor with same impl in permalink_client.js
  $('.inline-reply .count').live('click', function() {
    var replyThreadRef = $('.content', $(this).parent());

    if (replyThreadRef.is(":visible")) {
      replyThreadRef.slideUp();
    } else {
      replyThreadRef.slideDown();
    }
  });

  initEditor();
  initGadgetSystem();
}
