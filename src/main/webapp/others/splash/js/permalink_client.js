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
 * @fileoverview JS for the standalone (permalink) version of the client.
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */


var RPC_UPDATE_INTERVAL_MS = 3000;

window.waveOptions_ = {};

/**
 * Application entry point.
 */
$(document).ready(function() {
  var path = window.location.pathname;
  
  // adjust the height of the content frame to fill the iframe height
  $("#contentInner3")
    .css("height", 0)
    .css("height",
      $(window).height() - $("#header").outerHeight()
          - $("#content").outerHeight());

  // extract wave id.
  var waveId = $('#wave').attr('wave_id');

  // run rpc right away to open the wave.
  window.waveOptions_.waveId = waveId;
  window.waveOptions_.page = 0;
  window.waveOptions_.waveVersion = -1;

  // Options for rendering the wave. Maybe we need a more robust solution.
  window.waveOptions_.showHeader = $('#wave').attr('show_header');

  // Override async update options, coz we don't need search and stuff.
  // TODO(dhanji): this is broken, fix.
  RPC_UPDATE_OPTIONS = "update_wave";

  $('#share-link').click(function() {
    var shareRef = $('#share-popup');
    shareRef.fadeIn();
    return false;
  });

  $(".dialog-close").mousedown(function() {
    $(this).addClass("down");
  });

  $(".dialog-close").mouseout(function() {
    $(this).removeClass("down");
  });

  $(document).click(function(event) {
    if ($(event.target).is("#share-popup, #share-popup *")
        && !$(event.target).is(".dialog-close, .dialog-close *"))
    {
      return false;
    }

    $('#share-popup').fadeOut();
  });

  // Listen for expansion of collapsed inline replies.
  // TODO(dhanji): Merge into a common.js (NOT common_client.js)
  $('.inline-reply .count').live('click', function() {
    var inlineReplyId = '#ir-' + $(this).parent().attr('ir-id');
    var countRef = $(this);

    if (countRef.hasClass('expanded')) {
      // Move this back to purgatory!
      $(inlineReplyId).appendTo('#purgatory');
      countRef.removeClass('expanded');
    } else {
      $(inlineReplyId).insertAfter(countRef);
      countRef.addClass('expanded');
    }
  });

  var rpc = window.wave.Rpc.getInstance();

  initGadgetSystem();
  rpc.rpcOpenWave();

  setInterval(function() {
    // This is needed to specify the scope of the function.
    window.wave.Rpc.getInstance().rpcAsyncUpdate();
  }, RPC_UPDATE_INTERVAL_MS);
});

