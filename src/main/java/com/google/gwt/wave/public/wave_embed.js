var a, WAVEPANEL_nextId = 0;
if(typeof gadgets == "undefined" || !gadgets.rpc)document.write('<script src="https://wave.google.com/gadgets/js/core:rpc?debug=1&c=1" type="text/javascript"><\/script>');
function WavePanel(b) {
  this.id_ = WAVEPANEL_nextId++;
  this.frameId_ = "iframe_panel_" + this.id_;
  this.eventListeners_ = {};
  this.init_ = false;
  this.waveRootUrl = b || "https://wave.google.com/a/wavesandbox.com/"
}
WavePanel.prototype.getId = function() {
  return this.id_
};
WavePanel.prototype.setContactProvider = function(b) {
  if(this.init_)throw"Can only set profile provider before calling init";this.profileProvider_ = b
};
WavePanel.prototype.init = function(b, c) {
  this.init_ = true;
  if(this.initWaveId_ && this.initSearch_)throw"Both an initial wave ID and a search were specified";this.setupRpc_(c);
  this.createFrame_(b);
  gadgets.rpc.setRelayUrl(this.getFrameId(), this.getRelayUrl_(), false);
  delete this.initSearch_;
  delete this.initWaveId_
};
WavePanel.rpcSetup_ = false;
WavePanel.callbackQueue_ = {};
WavePanel.nextCallbackId = 0;
WavePanel.pushCallback_ = function(b) {
  if(!b)return"";
  var c = "" + WavePanel.nextCallbackId;
  WavePanel.nextCallbackId++;
  WavePanel.callbackQueue_[c] = b;
  return c
};
WavePanel.popCallback_ = function(b) {
  if(b == "")return null;
  var c = WavePanel.callbackQueue_[b];
  delete WavePanel.callbackQueue_[b];
  return c
};
a = WavePanel.prototype;
a.setupRpc_ = function(b) {
  if(!this.rpcSetup_) {
    this.rpcSetup_ = true;
    var c = this;
    gadgets.rpc.register("load_done", function() {
      b && b()
    });
    var e = function(d) {
      var f = WavePanel.popCallback_(d.a[0]);
      f && f(d.a[1])
    };
    gadgets.rpc.register("load_wave_done", function() {
      e(this)
    });
    gadgets.rpc.register("digest_search_done", function() {
      e(this)
    });
    gadgets.rpc.register("request_profiles", function() {
      if(!c.profileProvider_)throw"Got a profiles request but no profile provider is set.";c.profileProvider_(this.a[0])
    });
    gadgets.rpc.registerDefault(function() {
      var d = this.s, f = this.a[0];
      if(d = c.eventListeners_[d])for(var g in d)d[g](f)
    })
  }
};
a.createFrame_ = function(b) {
  var c = document.createElement("div");
  c.innerHTML = '<iframe name="' + this.frameId_ + '" >';
  c = c.firstChild;
  c.id = this.frameId_;
  c.width = "100%";
  c.height = "100%";
  c.frameBorder = "no";
  c.scrolling = "no";
  c.marginHeight = 0;
  c.marginWidth = 0;
  c.className = "embed-iframe";
  c.src = this.iframeUrl_();
  b = b || document.body;
  b.appendChild(c);
  return c
};
a.iframeUrl_ = function() {
  var b = [];
  b.push("client.type=embedded");
  b.push("parent=" + escape(window.location.protocol + "//" + window.location.host + window.location.pathname));
  this.profileProvider_ && b.push("ext_profiles=1");
  this.authToken_ && b.push("auth=" + encodeURIComponent(this.authToken_));
  this.initWaveId_ && b.push("wave_id=" + encodeURIComponent(this.initWaveId_));
  this.initSearch_ && b.push("search_query=" + encodeURIComponent(this.initSearch_));
  if(this.uiConfig_) {
    var c = this.uiConfig_;
    b.push("bgcolor=" + encodeURIComponent(c.getBgcolor()));
    b.push("color=" + encodeURIComponent(c.getColor()));
    b.push("font=" + encodeURIComponent(c.getFont()));
    b.push("fontsize=" + encodeURIComponent(c.getFontSize()));
    b.push("embed_header=" + c.getHeaderEnabled());
    b.push("embed_footer=" + c.getFooterEnabled());
    b.push("embed_toolbar=" + c.getToolbarEnabled())
  }return this.waveRootUrl + "?" + b.join("&")
};
a.addListener = function(b, c) {
  var e = this.eventListeners_, d = e[b];
  if(!d) {
    d = [];
    e[b] = d
  }d.push(c)
};
a.getFrameId = function() {
  return this.frameId_
};
a.getRelayUrl_ = function() {
  var b = this.waveRootUrl, c = b.split("://");
  if(c && c.length > 0) {
    var e = c[c.length - 1];
    b = c[0] + "://";
    for(c = 0;c < e.length;++c) {
      if(e[c] == "/")break;
      b += e[c]
    }b += "/"
  }return b + "gadgets/files/container/rpc_relay.html"
};
a.loadWave = function(b, c) {
  if(this.init_) {
    c = WavePanel.pushCallback_(c);
    gadgets.rpc.call(this.getFrameId(), "load_wave", null, c, b)
  }else this.initWaveId_ = b
};
a.loadSearch = function(b, c) {
  if(this.init_) {
    c = WavePanel.pushCallback_(c);
    gadgets.rpc.call(this.getFrameId(), "digest_search", null, c, b)
  }else this.initSearch_ = b
};
a.setUIConfig = function(b, c, e, d) {
  if(this.init_)throw"Cannot change the UIConfig after Init has been called.";else {
    if(!this.uiConfig_)this.uiConfig_ = new WavePanel.UIConfig;
    this.uiConfig_.setBgcolor(b);
    this.uiConfig_.setColor(c);
    this.uiConfig_.setFont(e);
    this.uiConfig_.setFontSize(d)
  }
};
a.setUIConfigObject = function(b) {
  if(this.init_)throw"Cannot change the UIConfig after Init has been called.";else this.uiConfig_ = b
};
a.addParticipant = function() {
  if(!this.init_)throw"Init not called.";gadgets.rpc.call(this.getFrameId(), "add_participant", null, "")
};
a.addReply = function(b, c) {
  if(!this.init_)throw"Init not called.";c = WavePanel.pushCallback_(c);
  gadgets.rpc.call(this.getFrameId(), "add_reply", null, c, b)
};
a.provideProfiles = function(b) {
  if(!this.init_)throw"Init not called.";var c = [];
  for(var e in b) {
    var d = b[e];
    c.push(e);
    c.push(d.firstName);
    c.push(d.lastName);
    c.push(d.photoUrl)
  }c.unshift(c.length / 4);
  gadgets.rpc.call(this.getFrameId(), "provide_profiles", null, c)
};
a.setAuthToken = function(b) {
  this.authToken_ = b
};
a.setContacts = function(b) {
  if(!this.init_)throw"Init not called.";gadgets.rpc.call(this.getFrameId(), "set_contacts", null, b)
};
a.setEditMode = function(b) {
  if(!this.init_)throw"Init not called.";b = b ? "true" : "false";
  gadgets.rpc.call(this.getFrameId(), "set_edit_mode", null, b)
};
a.setToolbarVisible = function(b) {
  if(!this.init_)throw"Init not called.";b = b ? "true" : "false";
  gadgets.rpc.call(this.getFrameId(), "set_toolbar_visible", null, b)
};
WavePanel.UIConfig = function() {
  this.bgcolor_ = "transparent";
  this.color_ = "black";
  this.font_ = "Arial";
  this.fontsize_ = "8pt";
  this.toolbar_ = this.footer_ = this.header_ = false
};
a = WavePanel.UIConfig.prototype;
a.getBgcolor = function() {
  return this.bgcolor_
};
a.setBgcolor = function(b) {
  this.bgcolor_ = b
};
a.getColor = function() {
  return this.color_
};
a.setColor = function(b) {
  this.color_ = b
};
a.getFont = function() {
  return this.font_
};
a.setFont = function(b) {
  this.font_ = b
};
a.getFontSize = function() {
  return this.fontsize_
};
a.setFontSize = function(b) {
  this.fontsize_ = b
};
a.getHeaderEnabled = function() {
  return this.header_
};
a.setHeaderEnabled = function(b) {
  this.header_ = b
};
a.getFooterEnabled = function() {
  return this.footer_
};
a.setFooterEnabled = function(b) {
  this.footer_ = b
};
a.getToolbarEnabled = function() {
  return this.toolbar_
};
a.setToolbarEnabled = function(b) {
  this.toolbar_ = b
};
