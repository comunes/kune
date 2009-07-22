// embed.js retreived from http://wave-api.appspot.com/public/embed.js on 2009-06-02
var a, WAVEPANEL_nextId = 0;
if(typeof gadgets == "undefined" || !gadgets.rpc)document.write('<script src="http://wave.google.com/gadgets/js/core:rpc?debug=1&c=1" type="text/javascript"><\/script>');
function WavePanel(b) {
  this.id_ = WAVEPANEL_nextId++;
  this.frameId_ = "iframe_panel_" + this.id_;
  this.eventListeners_ = {};
  this.init_ = false;
  this.waveRootUrl = b || "http://wave-devel.corp.google.com/a/google.com/"
}
WavePanel.prototype.getId = function() {
  return this.id_
};
WavePanel.prototype.setContactProvider = function(b) {
  if(this.init_)throw"Can only set contact provider before calling init";this.contactProvider_ = b
};
WavePanel.prototype.init = function(b, c) {
  this.init_ = true;
  if(this.initWaveId_ && this.initSearch_)throw"Both an initial wave ID and a search were specified";this.setupRpc_(c);
  this.createFrame_(b, this.contactProvider_, this.initWaveId_, this.initSearch_, this.uiConfig_);
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
    gadgets.rpc.register("request_contacts", function() {
      if(!c.contactProvider_)throw"Got a contacts request but no contact provider is set.";c.contactProvider_(this.a[0])
    });
    gadgets.rpc.registerDefault(function() {
      var d = this.s, f = this.a[0], h = c.eventListeners_[d];
      if(h)for(var g in h)h[g](f)
    })
  }
};
a.createFrame_ = function(b, c, e, d, f) {
  var h = document.createElement("div");
  h.innerHTML = '<iframe name="' + this.frameId_ + '" >';
  var g = h.firstChild;
  g.id = this.frameId_;
  g.width = "100%";
  g.height = "100%";
  g.frameBorder = "no";
  g.scrolling = "no";
  g.marginHeight = 0;
  g.marginWidth = 0;
  g.className = "embed-iframe";
  g.src = this.iframeUrl_(c, e, d, f);
  b = b || document.body;
  b.appendChild(g);
  return g
};
a.iframeUrl_ = function(b, c, e, d) {
  var f = [];
  f.push("client.type=embedded");
  f.push("parent=" + escape("http://" + window.location.host + window.location.pathname));
  b && f.push("ext_contacts=1");
  c && f.push("wave_id=" + encodeURIComponent(c));
  e && f.push("search_query=" + encodeURIComponent(e));
  if(d) {
    d.bgcolor && f.push("bgcolor=" + encodeURIComponent(d.bgcolor));
    d.color && f.push("color=" + encodeURIComponent(d.color));
    d.font && f.push("font=" + encodeURIComponent(d.font));
    d.fontsize && f.push("fontsize=" + encodeURIComponent(d.fontsize))
  }return this.waveRootUrl + "?" + f.join("&")
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
    for(var d = 0;d < e.length;++d) {
      if(e[d] == "/")break;
      b += e[d]
    }b += "/"
  }return b + "gadgets/files/container/rpc_relay.html"
};
a.loadWave = function(b, c) {
  if(this.init_) {
    var e = WavePanel.pushCallback_(c);
    gadgets.rpc.call(this.getFrameId(), "load_wave", null, e, b)
  }else this.initWaveId_ = b
};
a.loadSearch = function(b, c) {
  if(this.init_) {
    var e = WavePanel.pushCallback_(c);
    gadgets.rpc.call(this.getFrameId(), "digest_search", null, e, b)
  }else this.initSearch_ = b
};
a.setUIConfig = function(b, c, e, d) {
  if(this.init_)throw"Cannot change the UIConfig after Init has been called.";else this.uiConfig_ = {bgcolor:b, color:c, font:e, fontsize:d}
};
a.addParticipant = function() {
  if(!this.init_)throw"Init not called.";gadgets.rpc.call(this.getFrameId(), "add_participant", null, "")
};
a.addReply = function(b, c) {
  if(!this.init_)throw"Init not called.";var e = WavePanel.pushCallback_(c);
  gadgets.rpc.call(this.getFrameId(), "add_reply", null, e, b)
};
a.provideContacts = function(b) {
  if(!this.init_)throw"Init not called.";var c = [];
  for(var e in b) {
    var d = b[e];
    c.push(e);
    c.push(d.firstName);
    c.push(d.lastName);
    c.push(d.photoUrl)
  }c.unshift(c.length / 4);
  gadgets.rpc.call(this.getFrameId(), "provide_contacts", null, c)
};
