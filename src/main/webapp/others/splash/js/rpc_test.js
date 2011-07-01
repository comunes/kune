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
 * NOTE(dhanji): Edited for readability (temporary).
 * @fileoverview Unit tests for rpc.js.
 * @author dhanji@gmail.com (Dhanji R. Prasanna)
 */

function testMeasureCallback() {
  var result;
  var rpc = new wave.Rpc({
    measure: function(html) {
      result = html;
    }
  });

  // Simulate data coming back from the server.
  var expected = '<some_html>';
  rpc.handleRpcResponse_([{
    action: 'measure',
    html: expected
  }]);

  assertEquals(expected, result);
}


function testUpdateWaveVersion() {
  window = {};
  window.waveOptions_ = {};
  window.waveOptions_.waveId = 'aWaveId';

  var rpc = new wave.Rpc({});

  // Simulate data coming back from the server.
  var expectedVersion = 1234567;
  rpc.handleRpcResponse_([{
    action: 'update-wave-version',
    waveId: 'aWaveId',
    version: expectedVersion
  }]);

  assertEquals(expectedVersion, window.waveOptions_.waveVersion);
}


function testUpdateWaveAppendsToConversation() {
  window = {};
  window.waveOptions_ = {};
  window.waveOptions_.waveId = 'aWaveId';
  window.waveOptions_.page = 1; // Forces append rather than replace.

  var result;
  var rpc = new wave.Rpc({
    appendConversation: function(html) {
      result = html;
    }
  });

  // Simulate data coming back from the server.
  var expectedVersion = 12345678;
  var expectedHtml = '<some_html>';
  rpc.handleRpcResponse_([{
    action: 'update-wave',
    waveId: 'aWaveId',
    version: expectedVersion,
    html: expectedHtml
  }]);

  // Ensure version was updated too.
  assertEquals(expectedVersion, window.waveOptions_.waveVersion);
  assertEquals(expectedHtml, result);
}


function testUpdateWaveInsertsConversationOnePage() {
  window = {};
  window.waveOptions_ = {};
  window.waveOptions_.waveId = 'aWaveId';
  window.waveOptions_.page = 0; // Forces replace conversation.

  // Test on-wave opened callbacks too.
  var embedApiSignaled = false, waveOpenCalled = false;
  window.embedApi = {};
  window.embedApi.loaded = function() {
    embedApiSignaled = true;
  };
  window.wave.uiCallbacks_ = {};
  window.wave.uiCallbacks_.waveOpened = function() {
    waveOpenCalled = true;
  };

  var result;
  var rpc = new wave.Rpc({
    insertConversation: function(html) {
      result = html;
      return false; // Only one page exists.
    }
  });

  // Simulate data coming back from the server.
  var expectedVersion = 125678;
  var expectedHtml = '<some_other_html_1>';
  rpc.handleRpcResponse_([{
    action: 'update-wave',
    waveId: 'aWaveId',
    version: expectedVersion,
    html: expectedHtml
  }]);

  // Ensure version was updated too.
  assertEquals(expectedVersion, window.waveOptions_.waveVersion);
  assertEquals(expectedHtml, result);

  // Page should not have advanced.
  assertEquals(0, window.waveOptions_.page);

  // Callbacks should fire.
  assertTrue(embedApiSignaled);
  assertTrue(waveOpenCalled);
}


function testUpdateWaveInsertsConversationManyPages() {
  window = {};
  window.waveOptions_ = {};
  window.waveOptions_.waveId = 'aWaveId';
  window.waveOptions_.page = 0; // Forces replace conversation.

  // Mock Jquery ajax function
  var ajaxPostData;
  window.$ = {};
  window.$.ajax = function(data) {
    ajaxPostData = data;
  };

  var result;
  var rpc = new wave.Rpc({
    insertConversation: function(html) {
      result = html;
      return true; // Triggers load of additional page
    }
  });

  // Simulate data coming back from the server.
  var expectedVersion = 125678;
  var expectedHtml = '<some_other_html>';
  rpc.handleRpcResponse_([{
    action: 'update-wave',
    waveId: 'aWaveId',
    version: expectedVersion,
    html: expectedHtml
  }]);

  // Ensure version was updated too.
  assertEquals(expectedVersion, window.waveOptions_.waveVersion);
  assertEquals(expectedHtml, result);

  // Page should have advanced
  assertEquals(1, window.waveOptions_.page);

  // Assert the right RPC was called for the second page
  assertEquals(window.waveOptions_, ajaxPostData.data);
  assertEquals('POST', ajaxPostData.type);
}


function testUpdateWaveHeader() {
  window = {};
  window.waveOptions_ = {};
  window.waveOptions_.waveId = 'aWaveId';

  var result;
  var rpc = new wave.Rpc({
    insertHeader: function(html) {
      result = html;
    }
  });

  // Simulate data coming back from the server.
  var expected = '<some_header_html>';
  rpc.handleRpcResponse_([{
    action: 'update-header',
    waveId: 'aWaveId',
    html: expected
  }]);

  assertEquals(expected, result);
}


function testUpdateFeed() {
  window = {};
  window.wave.uiCallbacks_ = {};
  var feedOpenCalled = false;
  window.wave.uiCallbacks_.feedOpened = function() {
    feedOpenCalled = true;
  };

  var result;
  var rpc = new wave.Rpc({
    insertFeed: function(html) {
      result = html;
    }
  });

  // Simulate data coming back from the server.
  var expected = '<some_feed_html>';
  rpc.handleRpcResponse_([{
    action: 'update-feed',
    html: expected
  }]);

  assertEquals(expected, result);
  assertTrue(feedOpenCalled);
}


function testDeleteBlipOnNewerVersion() {
  // Mock the jquery version discovery logic.
  var expectedBlipId = 'aBlipId';
  var expectedVersion = 7658;
  $ = function(blipId) {
    if (expectedBlipId = blipId) {
      return {
        data: function(arg) {
          if ('version' == arg) {
            expectedVersion - 1; /* -1 version should trigger a delete */
          }
        }
      };
    }
  };

  var toDelete;
  var rpc = new wave.Rpc({
    deleteBlip: function(blipId) {
      toDelete = blipId;
    },
    hasNewer: function(data, blip) {
      return false;
    }
  });

  // Simulate data coming back from the server.
  rpc.handleRpcResponse_([{
    action: 'delete-blip',
    blipId: 'aBlipId',
    version: expectedVersion
  }]);

  assertEquals(expectedBlipId, toDelete);
}


function testUpdateBlip() {
  var expectedBlipId = 'aBlipId';
  var expectedHtml = '<a_replaced_blip>';
  var expectedVersion = 128973;

  var updatedVersion;
  $ = function() {
    return {
      data: function(arg, version) {
        if ('version' == arg) {
          updatedVersion = version;
        }
      }
    };
  };

  var replacedBlipId;
  var replacedHtml;
  var rpc = new wave.Rpc({
    replaceBlip: function(blipId, html) {
      replacedBlipId = blipId;
      replacedHtml = html;
    },
    hasNewer: function(data, blip) {
      return false;
    }
  });

  // Simulate data coming back from the server.
  rpc.handleRpcResponse_([{
    action: 'update-blip',
    blipId: 'aBlipId',
    html: expectedHtml,
    version: expectedVersion
  }]);

  assertEquals(expectedVersion, updatedVersion);
  assertEquals(expectedBlipId, replacedBlipId);
  assertEquals(expectedHtml, replacedHtml);
}
