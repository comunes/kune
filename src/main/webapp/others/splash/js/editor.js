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
function isEditing() {
  return $('#editor').length > 0
}

function startEditMode(obj) {
  stopEditMode();
  var blipId = obj.closest('.blip').attr('id');
  var contentRef = $('#' + blipId + ' .content');
  contentRef.addClass('editor')
      .attr('id', 'editor')
      .attr('contentEditable', 'true')
      .attr('blip_id', blipId)
      .focus();
  hideToolbar($('#' + blipId));
}

function stopEditMode() {
  if (!isEditing()) {
    // Not editing.
    return;
  }
  var editorRef = $('#editor');
  // Note: You cannot "removeAttr('contentEditable')" once its been added in
  // FF
  editorRef.removeClass('editor')
      .attr('contentEditable', 'false')
      .addClass('content-dirty')
      .removeAttr('id');
}

function startReply(ref) {
  var parentId = ref.closest('.blip').attr('id');
  var parentRef = $('#' + parentId);
  var newBlipRef = insertNewBlip('TBD_' + parentId, parentRef, null,
      parentRef.html());
  newBlipRef.find('.author').text(user.getDisplayName());
  newBlipRef.find('.content').html('');
  newBlipRef.data('parentId', parentId);
  // TODO: Calling slideDown followed by focus does not seem
  // to work well on FF, even when using a callback on slideDown.
  newBlipRef.show();
  startEditMode(newBlipRef);
}

function initEditor() {
  $('.edit-button').live('click', function() {
    startEditMode($(this));
  });
  $('.reply-button').live('click', function() {
    startReply($(this));
  });

  // TODO: Add other ways to get into edit mode.

  $('.editor').live('keypress', function(event) {
    if (event.shiftKey && event.keyCode == '13') {
      // Shift+enter!
      stopEditMode();
      event.preventDefault();
    }
  });
}
