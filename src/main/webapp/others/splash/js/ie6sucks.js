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
function fixLayout() {
  var targetHeight = $(window).height() - 70;
  $('#feed').css("height", targetHeight);
  $('#wave').css("height", targetHeight);

  var targetWidth = 0.7 * $(window).width();
  $('#wave').css("width", targetWidth + 'px');
}

/**
 * Init, separate from the main init.
 */
$(document).ready(function() {
  $('#feed').css("top", 0);
  $('#content').css("position", "relative");
  $('#content').css("top", 0);

  fixLayout();

  $(window).resize(fixLayout);

  // Do this everytime a wave is opened
  window.wave.uiCallbacks_.waveOpened = function() {
    var waveRef = $('#wave');

    $('.blip', waveRef).each(function() {
      var leftRef = $('.left', $(this));
      var rightRef = $('.right', $(this));
      var leftHeight = leftRef.height();
      var rightHeight = rightRef.height();

      // Normalize heights
      if (rightHeight < leftHeight) {
        rightRef.css('height', leftHeight);
      }

      // The right half needs to be explicitly floated right or
      // it just flows to the next line on the left.
      rightRef.css('float', 'right');
      rightRef.css('behavior', 'url(border-radius.htc)');

      // The left half needs to be position absolute. Otherwise,
      // it does not layout inside the correct blip.
      leftRef.css({
        'position': 'absolute',
        'margin-top': '-12px'
      });
      
      // Set the height of a blip to the max of the
      // left, right divs inside the blip. We add 10px for spacing.
      $(this).css('height', Math.max(leftHeight, rightHeight) + 60);
    });
  };

  // Do this everytime a search is rendered
  window.wave.uiCallbacks_.feedOpened = function() {
    $('.item', $('#feed')).css('overflow', 'hidden');
    $('.snippet', $('#feed')).css('overflow', 'hidden');
  };
});
