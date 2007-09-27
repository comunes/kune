/**
 * @fileoverview Wrapper to make working with XmlHttpRequest and the
 * DOM more convenient (cross browser compliance).
 * this code is taken from
 * http://webfx.eae.net/dhtml/xmlextras/xmlextras.html
 * @author Stefan Strigler steve@zeank.in-berlin.de
 * @version $Revision: 294 $
 */

/**
 * XmlHttp factory
 * @private
 */
function XmlHttp() {}

/**
 * creates a cross browser compliant XmlHttpRequest object
 */
XmlHttp.create = function () {
  try {
    if (window.XMLHttpRequest) {
      var req = new XMLHttpRequest();
      
      // some versions of Moz do not support the readyState property
      // and the onreadystate event so we patch it!
      if (req.readyState == null) {
	req.readyState = 1;
	req.addEventListener("load", function () {
			       req.readyState = 4;
			       if (typeof req.onreadystatechange == "function")
				 req.onreadystatechange();
			     }, false);
      }
      
      return req;
    }
    if (window.ActiveXObject) {
      return new ActiveXObject(XmlHttp.getPrefix() + ".XmlHttp");
    }
  }
  catch (ex) {}
  // fell through
  throw new Error("Your browser does not support XmlHttp objects");
};

/**
 * used to find the Automation server name
 * @private
 */
XmlHttp.getPrefix = function() {
  if (XmlHttp.prefix) // I know what you did last summer
    return XmlHttp.prefix;
  
  var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
  var o;
  for (var i = 0; i < prefixes.length; i++) {
    try {
      // try to create the objects
      o = new ActiveXObject(prefixes[i] + ".XmlHttp");
      return XmlHttp.prefix = prefixes[i];
    }
    catch (ex) {};
  }
  
  throw new Error("Could not find an installed XML parser");
};


/**
 * XmlDocument factory
 * @private
 */
function XmlDocument() {}

XmlDocument.create = function (name,ns) {
  name = name || 'foo';
  ns = ns || '';
  try {
    var doc;
    // DOM2
    if (document.implementation && document.implementation.createDocument) {
      doc = document.implementation.createDocument("", "", null);
      // some versions of Moz do not support the readyState property
      // and the onreadystate event so we patch it!
      if (doc.readyState == null) {
	doc.readyState = 1;
	doc.addEventListener("load", function () {
			       doc.readyState = 4;
			       if (typeof doc.onreadystatechange == "function")
				 doc.onreadystatechange();
			     }, false);
      }
    }
    if (window.ActiveXObject)
      doc = new ActiveXObject(XmlDocument.getPrefix() + ".DomDocument");
    
    try { 
      if (ns != '')
	doc.appendChild(doc.createElement(name)).setAttribute('xmlns',ns);
      else
	doc.appendChild(doc.createElement(name));
    } catch (dex) { 
      doc = document.implementation.createDocument(ns,name,null);
      
      if (doc.documentElement == null)
	doc.appendChild(doc.createElement(name));
      
      if (ns != '' && 
	  doc.documentElement.getAttribute('xmlns') != ns) // fixes buggy opera 8.5x
	doc.documentElement.setAttribute('xmlns',ns);
    }
    
    return doc;
  }
  catch (ex) { }
  throw new Error("Your browser does not support XmlDocument objects");
};

/**
 * used to find the Automation server name
 * @private
 */
XmlDocument.getPrefix = function() {
  if (XmlDocument.prefix)
    return XmlDocument.prefix;
	
  var prefixes = ["MSXML2", "Microsoft", "MSXML", "MSXML3"];
  var o;
  for (var i = 0; i < prefixes.length; i++) {
    try {
      // try to create the objects
      o = new ActiveXObject(prefixes[i] + ".DomDocument");
      return XmlDocument.prefix = prefixes[i];
    }
    catch (ex) {};
  }
  
  throw new Error("Could not find an installed XML parser");
};


// Create the loadXML method 
if (typeof(Document) != 'undefined' && window.DOMParser) {

  /** 
   * XMLDocument did not extend the Document interface in some
   * versions of Mozilla.
   * @private
   */
  Document.prototype.loadXML = function (s) {
		
    // parse the string to a new doc	
    var doc2 = (new DOMParser()).parseFromString(s, "text/xml");
		
    // remove all initial children
    while (this.hasChildNodes())
      this.removeChild(this.lastChild);
			
    // insert and import nodes
    for (var i = 0; i < doc2.childNodes.length; i++) {
      this.appendChild(this.importNode(doc2.childNodes[i], true));
    }
  };
 }

// Create xml getter for Mozilla
if (window.XMLSerializer &&
    window.Node && Node.prototype && Node.prototype.__defineGetter__) {
	
  /**
   * xml getter
   *
   * This serializes the DOM tree to an XML String
   *
   * Usage: var sXml = oNode.xml
   * @deprecated
   * @private
   */
  // XMLDocument did not extend the Document interface in some versions
  // of Mozilla. Extend both!
  XMLDocument.prototype.__defineGetter__("xml", function () {
                                           return (new XMLSerializer()).serializeToString(this);
                                         });
  /**
   * xml getter
   *
   * This serializes the DOM tree to an XML String
   *
   * Usage: var sXml = oNode.xml
   * @deprecated
   * @private
   */
  Document.prototype.__defineGetter__("xml", function () {
                                        return (new XMLSerializer()).serializeToString(this);
                                      });
	
  /**
   * xml getter
   *
   * This serializes the DOM tree to an XML String
   *
   * Usage: var sXml = oNode.xml
   * @deprecated
   * @private
   */
  Node.prototype.__defineGetter__("xml", function () {
                                    return (new XMLSerializer()).serializeToString(this);
                                  });
 }


/**
 * @fileoverview Collection of functions to make live easier
 * @author Stefan Strigler
 * @version $Revision: 307 $
 */

/**
 * Convert special chars to HTML entities
 * @addon
 * @return The string with chars encoded for HTML
 * @type String
 */
String.prototype.htmlEnc = function() {
  var str = this.replace(/&/g,"&amp;");
  str = str.replace(/</g,"&lt;");
  str = str.replace(/>/g,"&gt;");
  str = str.replace(/\"/g,"&quot;");
  str = str.replace(/\n/g,"<br />");
  return str;
};

/**
 * Converts from jabber timestamps to JavaScript Date objects
 * @addon
 * @param {String} ts A string representing a jabber datetime timestamp as 
 * defined by {@link http://www.xmpp.org/extensions/xep-0082.html XEP-0082}
 * @return A javascript Date object corresponding to the jabber DateTime given
 * @type Date
 */
Date.jab2date = function(ts) {
  var date = new Date(Date.UTC(ts.substr(0,4),ts.substr(5,2)-1,ts.substr(8,2),ts.substr(11,2),ts.substr(14,2),ts.substr(17,2)));
  if (ts.substr(ts.length-6,1) != 'Z') { // there's an offset
    var offset = new Date();
    offset.setTime(0);
    offset.setUTCHours(ts.substr(ts.length-5,2));
    offset.setUTCMinutes(ts.substr(ts.length-2,2));
    if (ts.substr(ts.length-6,1) == '+')
      date.setTime(date.getTime() - offset.getTime());
    else if (ts.substr(ts.length-6,1) == '-')
      date.setTime(date.getTime() + offset.getTime());
  }
  return date;
};

/**
 * Takes a timestamp in the form of 2004-08-13T12:07:04+02:00 as argument
 * and converts it to some sort of humane readable format
 * @addon
 */
Date.hrTime = function(ts) {
  return Date.jab2date(ts).toLocaleString();
};

/**
 * somewhat opposit to {@link #hrTime}
 * expects a javascript Date object as parameter and returns a jabber 
 * date string conforming to 
 * {@link http://www.xmpp.org/extensions/xep-0082.html XEP-0082}
 * @see #hrTime
 * @return The corresponding jabber DateTime string
 * @type String
 */
Date.prototype.jabberDate = function() {
  var padZero = function(i) {
    if (i < 10) return "0" + i;
    return i;
  };

  var jDate = this.getUTCFullYear() + "-";
  jDate += padZero(this.getUTCMonth()+1) + "-";
  jDate += padZero(this.getUTCDate()) + "T";
  jDate += padZero(this.getUTCHours()) + ":";
  jDate += padZero(this.getUTCMinutes()) + ":";
  jDate += padZero(this.getUTCSeconds()) + "Z";

  return jDate;
};

/**
 * Determines the maximum of two given numbers
 * @addon
 * @param {Number} A a number
 * @param {Number} B another number 
 * @return the maximum of A and B
 * @type Number
 */
Number.max = function(A, B) {
  return (A > B)? A : B;
};


/**
 * @fileoverview Collection of MD5 and SHA1 hashing and encoding
 * methods.
 * @author Stefan Strigler steve@zeank.in-berlin.de
 * @version $Revision: 295 $
 */

/*
 * A JavaScript implementation of the Secure Hash Algorithm, SHA-1, as defined
 * in FIPS PUB 180-1
 * Version 2.1a Copyright Paul Johnston 2000 - 2002.
 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
 * Distributed under the BSD License
 * See http://pajhome.org.uk/crypt/md5 for details.
 */

/*
 * Configurable variables. You may need to tweak these to be compatible with
 * the server-side, but the defaults work in most cases.
 */
var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
var b64pad  = "="; /* base-64 pad character. "=" for strict RFC compliance   */
var chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */

/*
 * These are the functions you'll usually want to call
 * They take string arguments and return either hex or base-64 encoded strings
 */
function hex_sha1(s){return binb2hex(core_sha1(str2binb(s),s.length * chrsz));}
function b64_sha1(s){return binb2b64(core_sha1(str2binb(s),s.length * chrsz));}
function str_sha1(s){return binb2str(core_sha1(str2binb(s),s.length * chrsz));}
function hex_hmac_sha1(key, data){ return binb2hex(core_hmac_sha1(key, data));}
function b64_hmac_sha1(key, data){ return binb2b64(core_hmac_sha1(key, data));}
function str_hmac_sha1(key, data){ return binb2str(core_hmac_sha1(key, data));}

/*
 * Perform a simple self-test to see if the VM is working
 */
function sha1_vm_test()
{
  return hex_sha1("abc") == "a9993e364706816aba3e25717850c26c9cd0d89d";
}

/*
 * Calculate the SHA-1 of an array of big-endian words, and a bit length
 */
function core_sha1(x, len)
{
  /* append padding */
  x[len >> 5] |= 0x80 << (24 - len % 32);
  x[((len + 64 >> 9) << 4) + 15] = len;

  var w = Array(80);
  var a =  1732584193;
  var b = -271733879;
  var c = -1732584194;
  var d =  271733878;
  var e = -1009589776;

  for(var i = 0; i < x.length; i += 16)
    {
      var olda = a;
      var oldb = b;
      var oldc = c;
      var oldd = d;
      var olde = e;

      for(var j = 0; j < 80; j++)
        {
          if(j < 16) w[j] = x[i + j];
          else w[j] = rol(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1);
          var t = safe_add(safe_add(rol(a, 5), sha1_ft(j, b, c, d)),
                           safe_add(safe_add(e, w[j]), sha1_kt(j)));
          e = d;
          d = c;
          c = rol(b, 30);
          b = a;
          a = t;
        }

      a = safe_add(a, olda);
      b = safe_add(b, oldb);
      c = safe_add(c, oldc);
      d = safe_add(d, oldd);
      e = safe_add(e, olde);
    }
  return Array(a, b, c, d, e);

}

/*
 * Perform the appropriate triplet combination function for the current
 * iteration
 */
function sha1_ft(t, b, c, d)
{
  if(t < 20) return (b & c) | ((~b) & d);
  if(t < 40) return b ^ c ^ d;
  if(t < 60) return (b & c) | (b & d) | (c & d);
  return b ^ c ^ d;
}

/*
 * Determine the appropriate additive constant for the current iteration
 */
function sha1_kt(t)
{
  return (t < 20) ?  1518500249 : (t < 40) ?  1859775393 :
    (t < 60) ? -1894007588 : -899497514;
}

/*
 * Calculate the HMAC-SHA1 of a key and some data
 */
function core_hmac_sha1(key, data)
{
  var bkey = str2binb(key);
  if(bkey.length > 16) bkey = core_sha1(bkey, key.length * chrsz);

  var ipad = Array(16), opad = Array(16);
  for(var i = 0; i < 16; i++)
    {
      ipad[i] = bkey[i] ^ 0x36363636;
      opad[i] = bkey[i] ^ 0x5C5C5C5C;
    }

  var hash = core_sha1(ipad.concat(str2binb(data)), 512 + data.length * chrsz);
  return core_sha1(opad.concat(hash), 512 + 160);
}

/*
 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
 * to work around bugs in some JS interpreters.
 */
function safe_add(x, y)
{
  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
  return (msw << 16) | (lsw & 0xFFFF);
}

/*
 * Bitwise rotate a 32-bit number to the left.
 */
function rol(num, cnt)
{
  return (num << cnt) | (num >>> (32 - cnt));
}

/*
 * Convert an 8-bit or 16-bit string to an array of big-endian words
 * In 8-bit function, characters >255 have their hi-byte silently ignored.
 */
function str2binb(str)
{
  var bin = Array();
  var mask = (1 << chrsz) - 1;
  for(var i = 0; i < str.length * chrsz; i += chrsz)
    bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (32 - chrsz - i%32);
  return bin;
}

/*
 * Convert an array of big-endian words to a string
 */
function binb2str(bin)
{
  var str = "";
  var mask = (1 << chrsz) - 1;
  for(var i = 0; i < bin.length * 32; i += chrsz)
    str += String.fromCharCode((bin[i>>5] >>> (32 - chrsz - i%32)) & mask);
  return str;
}

/*
 * Convert an array of big-endian words to a hex string.
 */
function binb2hex(binarray)
{
  var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i++)
    {
      str += hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8+4)) & 0xF) +
        hex_tab.charAt((binarray[i>>2] >> ((3 - i%4)*8  )) & 0xF);
    }
  return str;
}

/*
 * Convert an array of big-endian words to a base-64 string
 */
function binb2b64(binarray)
{
  var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i += 3)
    {
      var triplet = (((binarray[i   >> 2] >> 8 * (3 -  i   %4)) & 0xFF) << 16)
        | (((binarray[i+1 >> 2] >> 8 * (3 - (i+1)%4)) & 0xFF) << 8 )
        |  ((binarray[i+2 >> 2] >> 8 * (3 - (i+2)%4)) & 0xFF);
      for(var j = 0; j < 4; j++)
        {
          if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
          else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
        }
    }
  return str;
}

/*
 * A JavaScript implementation of the RSA Data Security, Inc. MD5 Message
 * Digest Algorithm, as defined in RFC 1321.
 * Version 2.1 Copyright (C) Paul Johnston 1999 - 2002.
 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
 * Distributed under the BSD License
 * See http://pajhome.org.uk/crypt/md5 for more info.
 */

/*
 * Configurable variables. You may need to tweak these to be compatible with
 * the server-side, but the defaults work in most cases.
 */
// var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
// var b64pad  = ""; /* base-64 pad character. "=" for strict RFC compliance   */
// var chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */

/*
 * These are the functions you'll usually want to call
 * They take string arguments and return either hex or base-64 encoded strings
 */
function hex_md5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
function b64_md5(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));}
function str_md5(s){ return binl2str(core_md5(str2binl(s), s.length * chrsz));}
function hex_hmac_md5(key, data) { return binl2hex(core_hmac_md5(key, data)); }
function b64_hmac_md5(key, data) { return binl2b64(core_hmac_md5(key, data)); }
function str_hmac_md5(key, data) { return binl2str(core_hmac_md5(key, data)); }

/*
 * Perform a simple self-test to see if the VM is working
 */
function md5_vm_test()
{
  return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72";
}

/*
 * Calculate the MD5 of an array of little-endian words, and a bit length
 */
function core_md5(x, len)
{
  /* append padding */
  x[len >> 5] |= 0x80 << ((len) % 32);
  x[(((len + 64) >>> 9) << 4) + 14] = len;

  var a =  1732584193;
  var b = -271733879;
  var c = -1732584194;
  var d =  271733878;

  for(var i = 0; i < x.length; i += 16)
  {
    var olda = a;
    var oldb = b;
    var oldc = c;
    var oldd = d;

    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
    c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
    d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
    a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
    a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
    b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);

    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
    c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
    d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
    a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
    b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
    c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);

    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
    c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
    d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
    a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
    b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
    c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);

    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
    d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
    a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
    a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
    b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
    a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
    c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);

    a = safe_add(a, olda);
    b = safe_add(b, oldb);
    c = safe_add(c, oldc);
    d = safe_add(d, oldd);
  }
  return Array(a, b, c, d);

}

/*
 * These functions implement the four basic operations the algorithm uses.
 */
function md5_cmn(q, a, b, x, s, t)
{
  return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
}
function md5_ff(a, b, c, d, x, s, t)
{
  return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
}
function md5_gg(a, b, c, d, x, s, t)
{
  return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
}
function md5_hh(a, b, c, d, x, s, t)
{
  return md5_cmn(b ^ c ^ d, a, b, x, s, t);
}
function md5_ii(a, b, c, d, x, s, t)
{
  return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
}

/*
 * Calculate the HMAC-MD5, of a key and some data
 */
function core_hmac_md5(key, data)
{
  var bkey = str2binl(key);
  if(bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);

  var ipad = Array(16), opad = Array(16);
  for(var i = 0; i < 16; i++)
  {
    ipad[i] = bkey[i] ^ 0x36363636;
    opad[i] = bkey[i] ^ 0x5C5C5C5C;
  }

  var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
  return core_md5(opad.concat(hash), 512 + 128);
}

/*
 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
 * to work around bugs in some JS interpreters.
 */
function safe_add(x, y)
{
  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
  return (msw << 16) | (lsw & 0xFFFF);
}

/*
 * Bitwise rotate a 32-bit number to the left.
 */
function bit_rol(num, cnt)
{
  return (num << cnt) | (num >>> (32 - cnt));
}

/*
 * Convert a string to an array of little-endian words
 * If chrsz is ASCII, characters >255 have their hi-byte silently ignored.
 */
function str2binl(str)
{
  var bin = Array();
  var mask = (1 << chrsz) - 1;
  for(var i = 0; i < str.length * chrsz; i += chrsz)
    bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
  return bin;
}

/*
 * Convert an array of little-endian words to a string
 */
function binl2str(bin)
{
  var str = "";
  var mask = (1 << chrsz) - 1;
  for(var i = 0; i < bin.length * 32; i += chrsz)
    str += String.fromCharCode((bin[i>>5] >>> (i % 32)) & mask);
  return str;
}

/*
 * Convert an array of little-endian words to a hex string.
 */
function binl2hex(binarray)
{
  var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i++)
  {
    str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) +
           hex_tab.charAt((binarray[i>>2] >> ((i%4)*8  )) & 0xF);
  }
  return str;
}

/*
 * Convert an array of little-endian words to a base-64 string
 */
function binl2b64(binarray)
{
  var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i += 3)
  {
    var triplet = (((binarray[i   >> 2] >> 8 * ( i   %4)) & 0xFF) << 16)
                | (((binarray[i+1 >> 2] >> 8 * ((i+1)%4)) & 0xFF) << 8 )
                |  ((binarray[i+2 >> 2] >> 8 * ((i+2)%4)) & 0xFF);
    for(var j = 0; j < 4; j++)
    {
      if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
      else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
    }
  }
  return str;
}

/* #############################################################################
   UTF-8 Decoder and Encoder
   base64 Encoder and Decoder
   written by Tobias Kieslich, justdreams
   Contact: tobias@justdreams.de				http://www.justdreams.de/
   ############################################################################# */

// returns an array of byterepresenting dezimal numbers which represent the
// plaintext in an UTF-8 encoded version. Expects a string.
// This function includes an exception management for those nasty browsers like
// NN401, which returns negative decimal numbers for chars>128. I hate it!!
// This handling is unfortunately limited to the user's charset. Anyway, it works
// in most of the cases! Special signs with an unicode>256 return numbers, which
// can not be converted to the actual unicode and so not to the valid utf-8
// representation. Anyway, this function does always return values which can not
// misinterpretd by RC4 or base64 en- or decoding, because every value is >0 and
// <255!!
// Arrays are faster and easier to handle in b64 encoding or encrypting....
function utf8t2d(t)
{
  t = t.replace(/\r\n/g,"\n");
  var d=new Array; var test=String.fromCharCode(237);
  if (test.charCodeAt(0) < 0) 
    for(var n=0; n<t.length; n++)
      {
        var c=t.charCodeAt(n);
        if (c>0)
          d[d.length]= c;
        else {
          d[d.length]= (((256+c)>>6)|192);
          d[d.length]= (((256+c)&63)|128);}
      }
  else
    for(var n=0; n<t.length; n++)
      {
        var c=t.charCodeAt(n);
        // all the signs of asci => 1byte
        if (c<128)
          d[d.length]= c;
        // all the signs between 127 and 2047 => 2byte
        else if((c>127) && (c<2048)) {
          d[d.length]= ((c>>6)|192);
          d[d.length]= ((c&63)|128);}
        // all the signs between 2048 and 66536 => 3byte
        else {
          d[d.length]= ((c>>12)|224);
          d[d.length]= (((c>>6)&63)|128);
          d[d.length]= ((c&63)|128);}
      }
  return d;
}
		
// returns plaintext from an array of bytesrepresenting dezimal numbers, which
// represent an UTF-8 encoded text; browser which does not understand unicode
// like NN401 will show "?"-signs instead
// expects an array of byterepresenting decimals; returns a string
function utf8d2t(d)
{
  var r=new Array; var i=0;
  while(i<d.length)
    {
      if (d[i]<128) {
        r[r.length]= String.fromCharCode(d[i]); i++;}
      else if((d[i]>191) && (d[i]<224)) {
        r[r.length]= String.fromCharCode(((d[i]&31)<<6) | (d[i+1]&63)); i+=2;}
      else {
        r[r.length]= String.fromCharCode(((d[i]&15)<<12) | ((d[i+1]&63)<<6) | (d[i+2]&63)); i+=3;}
    }
  return r.join("");
}

// included in <body onload="b64arrays"> it creates two arrays which makes base64
// en- and decoding faster
// this speed is noticeable especially when coding larger texts (>5k or so)
function b64arrays() {
  var b64s='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';
  b64 = new Array();f64 =new Array();
  for (var i=0; i<b64s.length ;i++) {
    b64[i] = b64s.charAt(i);
    f64[b64s.charAt(i)] = i;
  }
}

// creates a base64 encoded text out of an array of byerepresenting dezimals
// it is really base64 :) this makes serversided handling easier
// expects an array; returns a string
function b64d2t(d) {
  var r=new Array; var i=0; var dl=d.length;
  // this is for the padding
  if ((dl%3) == 1) {
    d[d.length] = 0; d[d.length] = 0;}
  if ((dl%3) == 2)
    d[d.length] = 0;
  // from here conversion
  while (i<d.length)
    {
      r[r.length] = b64[d[i]>>2];
      r[r.length] = b64[((d[i]&3)<<4) | (d[i+1]>>4)];
      r[r.length] = b64[((d[i+1]&15)<<2) | (d[i+2]>>6)];
      r[r.length] = b64[d[i+2]&63];
      if ((i%57)==54)
        r[r.length] = "\n";
      i+=3;
    }
  // this is again for the padding
  if ((dl%3) == 1)
    r[r.length-1] = r[r.length-2] = "=";
  if ((dl%3) == 2)
    r[r.length-1] = "=";
  // we join the array to return a textstring
  var t=r.join("");
  return t;
}

// returns array of byterepresenting numbers created of an base64 encoded text
// it is still the slowest function in this modul; I hope I can make it faster
// expects string; returns an array
function b64t2d(t) {
  var d=new Array; var i=0;
  // here we fix this CRLF sequenz created by MS-OS; arrrgh!!!
  t=t.replace(/\n|\r/g,""); t=t.replace(/=/g,"");
  while (i<t.length)
    {
      d[d.length] = (f64[t.charAt(i)]<<2) | (f64[t.charAt(i+1)]>>4);
      d[d.length] = (((f64[t.charAt(i+1)]&15)<<4) | (f64[t.charAt(i+2)]>>2));
      d[d.length] = (((f64[t.charAt(i+2)]&3)<<6) | (f64[t.charAt(i+3)]));
      i+=4;
    }
  if (t.length%4 == 2)
    d = d.slice(0, d.length-2);
  if (t.length%4 == 3)
    d = d.slice(0, d.length-1);
  return d;
}

if (typeof(atob) == 'undefined' || typeof(btoa) == 'undefined')
  b64arrays();

if (typeof(atob) == 'undefined') {
  atob = function(s) {
    return utf8d2t(b64t2d(s)); 
  }
}

if (typeof(btoa) == 'undefined') {
  btoa = function(s) { 
    return b64d2t(utf8t2d(s));
  }
}

function cnonce(size) {
  var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  var cnonce = '';
  for (var i=0; i<size; i++) {
    cnonce += tab.charAt(Math.round(Math.random(new Date().getTime())*(tab.length-1)));
  }
  return cnonce;
}

/**
 * @fileoverview Extends standard types (aray, string, object...) with JSON related methods
 * @author Stefan Strigler steve@zeank.in-berlin.de
 * @version $Revision: 258 $
 */

/*
    json.js
    2007-06-11

    Public Domain

    This file adds these methods to JavaScript:

        array.toJSONString()
        boolean.toJSONString()
        date.toJSONString()
        number.toJSONString()
        object.toJSONString()
        string.toJSONString()
            These methods produce a JSON text from a JavaScript value.
            It must not contain any cyclical references. Illegal values
            will be excluded.

            The default conversion for dates is to an ISO string. You can
            add a toJSONString method to any date object to get a different
            representation.

        string.parseJSON(filter)
            This method parses a JSON text to produce an object or
            array. It can throw a SyntaxError exception.

            The optional filter parameter is a function which can filter and
            transform the results. It receives each of the keys and values, and
            its return value is used instead of the original value. If it
            returns what it received, then structure is not modified. If it
            returns undefined then the member is deleted.

            Example:

            // Parse the text. If a key contains the string 'date' then
            // convert the value to a date.

            myData = text.parseJSON(function (key, value) {
                return key.indexOf('date') >= 0 ? new Date(value) : value;
            });

    It is expected that these methods will formally become part of the
    JavaScript Programming Language in the Fourth Edition of the
    ECMAScript standard in 2008.

    This file will break programs with improper for..in loops. See
    http://yuiblog.com/blog/2006/09/26/for-in-intrigue/

    This is a reference implementation. You are free to copy, modify, or
    redistribute.

    Use your own copy. It is extremely unwise to load untrusted third party
    code into your pages.
*/

/*jslint evil: true */

// Augment the basic prototypes if they have not already been augmented.

if (!Object.prototype.toJSONString) {

    Array.prototype.toJSONString = function () {
        var a = [],     // The array holding the member texts.
            i,          // Loop counter.
            l = this.length,
            v;          // The value to be stringified.


// For each value in this array...

        for (i = 0; i < l; i += 1) {
            v = this[i];
            switch (typeof v) {
            case 'object':

// Serialize a JavaScript object value. Ignore objects thats lack the
// toJSONString method. Due to a specification error in ECMAScript,
// typeof null is 'object', so watch out for that case.

                if (v) {
                    if (typeof v.toJSONString === 'function') {
                        a.push(v.toJSONString());
                    }
                } else {
                    a.push('null');
                }
                break;

            case 'string':
            case 'number':
            case 'boolean':
                a.push(v.toJSONString());

// Values without a JSON representation are ignored.

            }
        }

// Join all of the member texts together and wrap them in brackets.

        return '[' + a.join(',') + ']';
    };


    Boolean.prototype.toJSONString = function () {
        return String(this);
    };


    Date.prototype.toJSONString = function () {

// Ultimately, this method will be equivalent to the date.toISOString method.

        function f(n) {

// Format integers to have at least two digits.

            return n < 10 ? '0' + n : n;
        }

        return '"' + this.getFullYear() + '-' +
                f(this.getMonth() + 1) + '-' +
                f(this.getDate()) + 'T' +
                f(this.getHours()) + ':' +
                f(this.getMinutes()) + ':' +
                f(this.getSeconds()) + '"';
    };


    Number.prototype.toJSONString = function () {

// JSON numbers must be finite. Encode non-finite numbers as null.

        return isFinite(this) ? String(this) : 'null';
    };


    Object.prototype.toJSONString = function () {
        var a = [],     // The array holding the member texts.
            k,          // The current key.
            v;          // The current value.

// Iterate through all of the keys in the object, ignoring the proto chain.

        for (k in this) {
            if (this.hasOwnProperty(k)) {
                v = this[k];
                switch (typeof v) {
                case 'object':

// Serialize a JavaScript object value. Ignore objects that lack the
// toJSONString method. Due to a specification error in ECMAScript,
// typeof null is 'object', so watch out for that case.

                    if (v) {
                        if (typeof v.toJSONString === 'function') {
                            a.push(k.toJSONString() + ':' + v.toJSONString());
                        }
                    } else {
                        a.push(k.toJSONString() + ':null');
                    }
                    break;

                case 'string':
                case 'number':
                case 'boolean':
                    a.push(k.toJSONString() + ':' + v.toJSONString());

// Values without a JSON representation are ignored.

                }
            }
        }

// Join all of the member texts together and wrap them in braces.

        return '{' + a.join(',') + '}';
    };


    (function (s) {

// Augment String.prototype. We do this in an immediate anonymous function to
// avoid defining global variables.

// m is a table of character substitutions.

        var m = {
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        };


        s.parseJSON = function (filter) {
            var j;

            function walk(k, v) {
                var i;
                if (v && typeof v === 'object') {
                    for (i in v) {
                        if (v.hasOwnProperty(i)) {
                            v[i] = walk(i, v[i]);
                        }
                    }
                }
                return filter(k, v);
            }


// Parsing happens in three stages. In the first stage, we run the text against
// a regular expression which looks for non-JSON characters. We are especially
// concerned with '()' and 'new' because they can cause invocation, and '='
// because it can cause mutation. But just to be safe, we will reject all
// unexpected characters.

            if (/^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/.
                    test(this)) {

// In the second stage we use the eval function to compile the text into a
// JavaScript structure. The '{' operator is subject to a syntactic ambiguity
// in JavaScript: it can begin a block or an object literal. We wrap the text
// in parens to eliminate the ambiguity.

                try {
                    j = eval('(' + this + ')');
                } catch (e) {
                    throw new SyntaxError('parseJSON');
                }
            } else {
                throw new SyntaxError('parseJSON');
            }

// In the optional third stage, we recursively walk the new structure, passing
// each name/value pair to a filter function for possible transformation.

            if (typeof filter === 'function') {
                j = walk('', j);
            }
            return j;
        };


        s.toJSONString = function () {

// If the string contains no control characters, no quote characters, and no
// backslash characters, then we can simply slap some quotes around it.
// Otherwise we must also replace the offending characters with safe
// sequences.

            if (/["\\\x00-\x1f]/.test(this)) {
                return '"' + this.replace(/([\x00-\x1f\\"])/g, function (a, b) {
                    var c = m[b];
                    if (c) {
                        return c;
                    }
                    c = b.charCodeAt();
                    return '\\u00' +
                        Math.floor(c / 16).toString(16) +
                        (c % 16).toString(16);
                }) + '"';
            }
            return '"' + this + '"';
        };
    })(String.prototype);
}

/**
 * @fileoverview OO interface to handle cookies.
 * Taken from {@link http://www.quirksmode.org/js/cookies.html 
 * http://www.quirksmode.org/js/cookies.html}
 * @author Stefan Strigler
 * @version $Revision: 314 $
 */

/**
 * Creates a new Cookie
 * @class Class representing browser cookies for storing small amounts of data
 * @constructor
 * @param {String} name  The name of the value to store
 * @param {String} value The value to store
 * @param {int}    secs  Number of seconds until cookie expires (may be empty)
 */
function JSJaCCookie(name,value,secs)
{
  /** 
   * This cookie's name
   * @type String
   */
  this.name = name;
  /**
   * This cookie's value
   * @type String
   */
  this.value = value;
  /**
   * Time in seconds when cookie expires (thus being delete by
   * browser). A value of -1 denotes a session cookie which means that
   * stored data gets lost when browser is being closed.  
   * @type int
   */
  this.expires = secs;

  /**
   * Stores this cookie
   */
  this.write = function() {
    if (this.secs) {
      var date = new Date();
      date.setTime(date.getTime()+(this.secs*1000));
      var expires = "; expires="+date.toGMTString();
    } else
      var expires = "";
    document.cookie = this.getName()+"="+this.getValue()+expires+"; path=/";
  };
  /**
   * Deletes this cookie
   */
  this.erase = function() {
    var c = new JSJaCCookie(this.getName(),"",-1);
    c.write();
  }

  /**
   * Gets the name of this cookie
   * @return The name
   * @type String
   */
  this.getName = function() {
    return this.name;
  }
  
  /**
   * Sets the name of this cookie
   * @param {String} name The name for this cookie
   * @return This cookie
   * @type Cookie
   */
  this.setName = function(name) {
    this.name = name;
    return this;
  }

  /**
   * Gets the value of this cookie
   * @return The value
   * @type String
   */
  this.getValue = function() {
    return this.value;
  }
  
  /**
   * Sets the value of this cookie
   * @param {String} value The value for this cookie
   * @return This cookie
   * @type Cookie
   */
  this.setValue = function(value) {
    this.value = value;
    return this;
  }
}

/**
 * Reads the value for given <code>name</code> from cookies and return new
 * <code>Cookie</code> object
 * @param {String} name The name of the cookie to read
 * @return A cookie object of the given name
 * @type Cookie
 * @throws CookieException when cookie with given name could not be found
 */
JSJaCCookie.read = function(name) {
  var nameEQ = name + "=";
  var ca = document.cookie.split(';');
  for(var i=0;i < ca.length;i++) {
    var c = ca[i];
    while (c.charAt(0)==' ') c = c.substring(1,c.length);
    if (c.indexOf(nameEQ) == 0) return new JSJaCCookie(name, c.substring(nameEQ.length,c.length));
  }
  throw new JSJaCCookieException("Cookie not found");
};

/**
 * Some exception denoted to dealing with cookies
 * @constructor
 * @param {String} msg The message to pass to the exception
 */
function JSJaCCookieException(msg) {
  this.message = msg;
  this.name = "CookieException";
}

/**
 * @fileoverview This file contains all things that make life easier when
 * dealing with JIDs
 * @author Stefan Strigler
 * @version $Revision: 237 $
 */

/**
 * list of forbidden chars for nodenames
 * @private
 */
var JSJACJID_FORBIDDEN = ['"',' ','&','\'','/',':','<','>','@']; 

/**
 * Creates a new JSJaCJID object
 * @class JSJaCJID models xmpp jid objects
 * @constructor
 * @param {Object} jid jid may be either of type String or a JID represented 
 * by JSON with fields 'node', 'domain' and 'resource'
 * @throws JSJaCJIDInvalidException Thrown if jid is not valid
 * @return a new JSJaCJID object
 */
function JSJaCJID(jid) {
  /**
   *@private
   */
  this._node = '';
  /**
   *@private
   */
  this._domain = '';
  /**
   *@private
   */
  this._resource = '';

  if (typeof(jid) == 'string') {
    if (jid.indexOf('@') != -1) {
        this.setNode(jid.substring(0,jid.indexOf('@')));
        jid = jid.substring(jid.indexOf('@')+1);
    }
    if (jid.indexOf('/') != -1) {
      this.setResource(jid.substring(jid.indexOf('/')+1));
      jid = jid.substring(0,jid.indexOf('/'));
    }
    this.setDomain(jid);
  } else {
    this.setNode(jid.node);
    this.setDomain(jid.domain);
    this.setResource(jid.resource);
  }
}


/**
 * Gets the node part of the jid
 * @return A string representing the node name
 * @type String
 */
JSJaCJID.prototype.getNode = function() { return this._node; };

/**
 * Gets the domain part of the jid
 * @return A string representing the domain name
 * @type String
 */
JSJaCJID.prototype.getDomain = function() { return this._domain; };

/**
 * Gets the resource part of the jid
 * @return A string representing the resource
 * @type String
 */
JSJaCJID.prototype.getResource = function() { return this._resource; };


/**
 * Sets the node part of the jid
 * @param {String} node Name of the node
 * @throws JSJaCJIDInvalidException Thrown if node name contains invalid chars
 * @return This object
 * @type JSJaCJID
 */
JSJaCJID.prototype.setNode = function(node) {
  JSJaCJID._checkNodeName(node);
  this._node = node || '';
  return this;
};

/**
 * Sets the domain part of the jid
 * @param {String} domain Name of the domain
 * @throws JSJaCJIDInvalidException Thrown if domain name contains invalid 
 * chars or is empty
 * @return This object
 * @type JSJaCJID
 */
JSJaCJID.prototype.setDomain = function(domain) {
  if (!domain || domain == '')
    throw new JSJaCJIDInvalidException("domain name missing");
  // chars forbidden for a node are not allowed in domain names
  // anyway, so let's check
  JSJaCJID._checkNodeName(domain); 
  this._domain = domain;
  return this;
};

/**
 * Sets the resource part of the jid
 * @param {String} resource Name of the resource
 * @return This object
 * @type JSJaCJID
 */
JSJaCJID.prototype.setResource = function(resource) {
  this._resource = resource || '';
  return this;
};

/**
 * The string representation of the full jid
 * @return A string representing the jid
 * @type String
 */
JSJaCJID.prototype.toString = function() {
  var jid = '';
  if (this.getNode() && this.getNode() != '')
    jid = this.getNode() + '@';
  jid += this.getDomain(); // we always have a domain
  if (this.getResource() && this.getResource() != "")
    jid += '/' + this.getResource();
  return jid;
};

/**
 * Removes the resource part of the jid
 * @return This object
 * @type JSJaCJID
 */
JSJaCJID.prototype.removeResource = function() {
  return this.setResource();
};

/**
 * Check if node name is valid
 * @private
 * @param {String} node A name for a node
 * @throws JSJaCJIDInvalidException Thrown if name for node is not allowed
 */
JSJaCJID._checkNodeName = function(nodeprep) {
    if (!nodeprep || nodeprep == '')
      return;
    for (var i=0; i< JSJACJID_FORBIDDEN.length; i++) {
      if (nodeprep.indexOf(JSJACJID_FORBIDDEN[i]) != -1) {
        throw new JSJaCJIDInvalidException("forbidden char in nodename: "+JSJACJID_FORBIDDEN[i]);
      }
    }
};

/**
 * Creates a new Exception of type JSJaCJIDInvalidException
 * @class Exception to indicate invalid values for a jid
 * @constructor
 * @param {String} message The message associated with this Exception
 */
function JSJaCJIDInvalidException(message) {
  /**
   * The exceptions associated message
   * @type String
   */
  this.message = message;
  /**
   * The name of the exception
   * @type String
   */
  this.name = "JSJaCJIDInvalidException";
}


/**
 * @private
 * This code is taken from {@link
 * http://wiki.script.aculo.us/scriptaculous/show/Builder
 * script.aculo.us' Dom Builder} and has been modified to suit our
 * needs.<br/> 
 * The original parts of the code do have the following
 * copyright and license notice:<br/> 
 * Copyright (c) 2005, 2006 Thomas Fuchs (http://script.aculo.us, 
 * http://mir.acu lo.us) <br/>
 * script.aculo.us is freely distributable under the terms of an
 * MIT-style license.<br>
 * For details, see the script.aculo.us web site: 
 * http://script.aculo.us/<br>
 */
var JSJaCBuilder = {
  /**
   * @private
   */
  buildNode: function(doc, elementName) {

    var element;

    // attributes (or text)
    if(arguments[2])
      if(JSJaCBuilder._isStringOrNumber(arguments[2]) ||
         (arguments[2] instanceof Array)) {
        element = doc.createElement(elementName);
        JSJaCBuilder._children(doc, element, arguments[2]);
      } else {
        if (arguments[2]['xmlns']) {
          try {
            element = doc.createElementNS(arguments[2]['xmlns'],elementName);
          } catch(e) { element = doc.createElement(elementName); }
        } else
          element = doc.createElement(elementName);
        for(attr in arguments[2]) {
          if (arguments[2].hasOwnProperty(attr)) {
            if (attr == 'xmlns' && element.namespaceURI == attr)
              continue;
            element.setAttribute(attr, arguments[2][attr]);
          }
        }
            
      }
    else
      element = doc.createElement(elementName);    
    // text, or array of children
    if(arguments[3])
      JSJaCBuilder._children(doc, element, arguments[3]);
    
    return element;
  },

  /**
   * @private
   */
  _text: function(doc, text) {
    return doc.createTextNode(text);
  },

  /**
   * @private
   */
  _children: function(doc, element, children) {
    if(typeof children=='object') { // array can hold nodes and text
      for (var i in children) {
        if (children.hasOwnProperty(i)) {
          var e = children[i];
          if (typeof e=='object') {
            if (e instanceof Array) {
              var node = JSJaCBuilder.buildNode(doc, e[0], e[1], e[2]);
              element.appendChild(node);
            } else {
              element.appendChild(e);
            }
          } else {
            if(JSJaCBuilder._isStringOrNumber(e)) {
              element.appendChild(JSJaCBuilder._text(doc, e));
            }
          }
        }
      }
    } else {
      if(JSJaCBuilder._isStringOrNumber(children)) {
        element.appendChild(JSJaCBuilder._text(doc, children));
      }
    }
  },
  
  _attributes: function(attributes) {
    var attrs = [];
    for(attribute in attributes)
      if (attributes.hasOwnProperty(attribute))
        attrs.push(attribute +
          '="' + attributes[attribute].toString().htmlEnc() + '"');
    return attrs.join(" ");
  },
  
  _isStringOrNumber: function(param) {
    return(typeof param=='string' || typeof param=='number');
  }
};


/**
 * @fileoverview Contains all Jabber/XMPP packet related classes.
 * @author Stefan Strigler steve@zeank.in-berlin.de
 * @version $Revision: 310 $
 */

var JSJACPACKET_USE_XMLNS = true;

/**
 * Creates a new packet with given root tag name (for internal use)
 * @class Somewhat abstract base class for all kinds of specialised packets
 * @param {String} name The root tag name of the packet 
 * (i.e. one of 'message', 'iq' or 'presence')
 */
function JSJaCPacket(name) {
  /**
   * @private
   */
  this.name = name;

  if (typeof(JSJACPACKET_USE_XMLNS) != 'undefined' && JSJACPACKET_USE_XMLNS)
    /**
     * @private
     */
    this.doc = XmlDocument.create(name,'jabber:client');
  else
    /**
     * @private
     */
    this.doc = XmlDocument.create(name,'');

  /**
   * Gets the type (name of root element) of this packet, i.e. one of
   * 'presence', 'message' or 'iq'
   * @return the top level tag name
   * @type String
   */
  this.pType = function() { return this.name; };

  /**
   * Gets the associated Document for this packet.
   * @type {@link http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/core.html#i-Document Document}
   */
  this.getDoc = function() { 
    return this.doc; 
  };
  /**
   * Gets the root node of this packet
   * @type {@link http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/core.html#ID-1950641247 Node}
   */
  this.getNode = function() { if (this.doc) return this.getDoc().documentElement; else return null};

  /**
   * Sets the 'to' attribute of the root node of this packet
   * @param {String} to 
   * @type JSJaCPacket
   */
  this.setTo = function(to) {
    if (!to || to == '')
      this.getNode().removeAttribute('to');
    else if (typeof(to) == 'string')
      this.getNode().setAttribute('to',to); 
    else 
      this.getNode().setAttribute('to',to.toString());
    return this; 
  };
  /**
   * Sets the 'from' attribute of the root node of this
   * packet. Usually this is not needed as the server will take care
   * of this automatically.
   * @type JSJaCPacket
   */
  this.setFrom = function(from) {
    if (!from || from == '')
      this.getNode().removeAttribute('from');
    else if (typeof(from) == 'string')
      this.getNode().setAttribute('from',from); 
    else 
      this.getNode().setAttribute('from',from.toString());
    return this;
  };
  /**
   * Sets 'id' attribute of the root node of this packet.
   * @param {String} id The id of the packet.
   * @type JSJaCPacket
   */
  this.setID = function(id) { 
    if (!id || id == '')
      this.getNode().removeAttribute('id');
    else
      this.getNode().setAttribute('id',id); 
    return this; 
  };
  /**
   * Sets the 'type' attribute of the root node of this packet.
   * @param {String} type The type of the packet.
   * @type JSJaCPacket
   */
  this.setType = function(type) { 
    if (!type || type == '')
      this.getNode().removeAttribute('type');
    else
      this.getNode().setAttribute('type',type);
    return this; 
  };
  /**
   * Sets 'xml:lang' for this packet
   * @param {String} xmllang The xml:lang of the packet.
   * @type JSJaCPacket
   */
  this.setXMLLang = function(xmllang) {
    if (!xmllang || xmllang == '')
      this.getNode().removeAttribute('xml:lang');
    else
      this.getNode().setAttribute('xml:lang',xmllang);
    return this;
  };

  /**
   * Gets the 'to' attribute of this packet
   * @type String
   */
  this.getTo = function() { 
    return this.getNode().getAttribute('to'); 
  };
  /**
   * Gets the 'from' attribute of this packet.
   * @type String
   */
  this.getFrom = function() { 
    return this.getNode().getAttribute('from'); 
  };
  /**
   * Gets the 'to' attribute of this packet as a JSJaCJID object
   * @type JSJaCJID
   */
  this.getToJID = function() { 
    return new JSJaCJID(this.getTo()); 
  };
  /**
   * Gets the 'from' attribute of this packet as a JSJaCJID object
   * @type JSJaCJID
   */
  this.getFromJID = function() { 
    return new JSJaCJID(this.getFrom()); 
  };
  /**
   * Gets the 'id' of this packet
   * @type String
   */
  this.getID = function() { return this.getNode().getAttribute('id'); };
  /**
   * Gets the 'type' of this packet
   * @type String
   */
  this.getType = function() { return this.getNode().getAttribute('type'); };
  /**
   * Gets the 'xml:lang' of this packet
   * @type String
   */
  this.getXMLLang = function() { 
    return this.getNode().getAttribute('xml:lang'); 
  };
  /**
   * Gets the 'xmlns' (xml namespace) of the root node of this packet
   * @type String
   */
  this.getXMLNS = function() { return this.getNode().namespaceURI; };

  /**
   * Gets a child element of this packet.
   * @param {String} name Tagname of child to retrieve.
   * @param {String} ns   Namespace of child
   * @return The child node, null if none found
   * @type {@link http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/core.html#ID-1950641247 Node}
   */ 
  this.getChild = function(name, ns) {
    if (!name && this.getNode().firstChild) {
      // best practice
      return this.getNode().firstChild;
    } else {
      var nodes = this.getNode().getElementsByTagName(name);
      for (var i=0; i<nodes.length; i++) {
        if (ns && nodes.item(i).namespaceURI != ns) {
          continue;
        }
        if (nodes.item(i).tagName == name) {
          return nodes.item(i);
        }
      }
    }
    return null; // fallback
  }

  /**
   * Gets the node value of a child element of this packet.
   * @param {String} name Tagname of child to retrieve.
   * @param {String} ns   Namespace of child
   * @return The value of the child node, empty string if none found
   * @type String
   */ 
  this.getChildVal = function(name, ns) {
    var node = this.getChild(name, ns);
    if (node && node.firstChild) {
      return node.firstChild.nodeValue;
    } else {
      return '';
    }
  }

  /**
   * Returns a copy of this node
   * @return a copy of this node
   * @type JSJaCPacket
   */
  this.clone = function() { return JSJaCPacket.wrapNode(this.getNode()); }

  /**
   * Returns a string representation of the raw xml content of this packet.
   * @type String
   */
  this.xml = function() { 

    if (this.getDoc().xml) // IE
        return this.getDoc().xml;

    var xml = (new XMLSerializer()).serializeToString(this.getNode()); // opera needs the node
    if (typeof(xml) != 'undefined') 
      return xml;
    return (new XMLSerializer()).serializeToString(this.doc); // oldschool

  };

  // PRIVATE METHODS DOWN HERE 

  /**
   * Gets an attribute of the root element
   * @private
   */
  this._getAttribute = function(attr) {
    return this.getNode().getAttribute(attr);
  };

  /**
   * Replaces this node with given node
   * @private
   */
  this._replaceNode = function(aNode) {
    // copy attribs
    for (var i=0; i<aNode.attributes.length; i++)
      if (aNode.attributes.item(i).nodeName != 'xmlns')
        this.getNode().setAttribute(aNode.attributes.item(i).nodeName,aNode.attributes.item(i).nodeValue);

    // copy children
    for (var i=0; i<aNode.childNodes.length; i++)
      if (this.getDoc().importNode)
        this.getNode().appendChild(this.getDoc().importNode(aNode.childNodes.item(i),true));
      else
        this.getNode().appendChild(aNode.childNodes.item(i).cloneNode(true));
  };
  
  /**
   * Set node value of a child node
   * @private
   */
  this._setChildNode = function(nodeName, nodeValue) {
    var aNode = this.getChild(nodeName);
    var tNode = this.getDoc().createTextNode(nodeValue);
    if (aNode)
      try {
        aNode.replaceChild(tNode,aNode.firstChild);
      } catch (e) { }
    else {
      aNode = this.getNode().appendChild(this.getDoc().createElement(nodeName));
      aNode.appendChild(tNode);
    }
    return aNode;
  }

  /**
   * Builds a node using {@link
   * http://wiki.script.aculo.us/scriptaculous/show/Builder
   * script.aculo.us' Dom Builder} notation.
   * This code is taken from {@link
   * http://wiki.script.aculo.us/scriptaculous/show/Builder
   * script.aculo.us' Dom Builder} and has been modified to suit our
   * needs.<br/>
   * The original parts of the code do have the following copyright
   * and license notice:<br/>
   * Copyright (c) 2005, 2006 Thomas Fuchs (http://script.aculo.us,
   * http://mir.acu lo.us) <br/>
   * script.aculo.us is freely distributable under the terms of an
   * MIT-style licen se.  // For details, see the script.aculo.us web
   * site: http://script.aculo.us/<br>
   * @author Thomas Fuchs
   * @author Stefan Strigler
   * @return The newly created node
   * @type {@link http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/core.html#ID-1950641247 Node}
   */
  this.buildNode = function(elementName) {
    return JSJaCBuilder.buildNode(this.getDoc(), 
                                  elementName, 
                                  arguments[1], 
                                  arguments[2]);
  };

  /**
   * Appends node created by buildNode to this packets parent node.
   * @param {@link http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/core.html#ID-1950641247 Node} element The node to append or
   * @param {String} element A name plus an object hash with attributes (optional) plus an array of childnodes (optional)
   * @see #buildNode
   * @return This packet
   * @type JSJaCPacket
   */
  this.appendNode = function(element) {
    if (typeof element=='object') { // seems to be a prebuilt node
      return this.getNode().appendChild(element)
    } else { // build node
      return this.getNode().appendChild(this.buildNode(element, 
                                                       arguments[1], 
                                                       arguments[2]));
    }
  };

}

/**
 * A jabber/XMPP presence packet
 * @class Models the XMPP notion of a 'presence' packet
 * @extends JSJaCPacket
 */
function JSJaCPresence() {
  /**
   * @ignore
   */
  this.base = JSJaCPacket;
  this.base('presence');

  /**
   * Sets the status for this presence packet. 
   * @param {String} status An XMPP complient status indicator. Must
   * be one of 'chat', 'away', 'xa', 'dnd', 'available', 'unavailable'
   * @return this
   * @type JSJaCPacket
   */
  this.setStatus = function(status) {
    this._setChildNode("status", status);
    return this; 
  };
  /** 
   * Sets the status message for current status. Usually this is set
   * to some human readable string indicating what the user is
   * doing/feel like currently.
   * @param {String} show A status message
   * @return this
   * @type JSJaCPacket
   */
  this.setShow = function(show) {
    this._setChildNode("show",show);
    return this; 
  };
  /**
   * Sets the priority of the resource bind to with this connection
   * @param {int} prio The priority to set this resource to
   * @return this
   * @type JSJaCPacket
   */
  this.setPriority = function(prio) {
    this._setChildNode("priority", prio);
    return this; 
  };
  /**
   * Some combined method that allowes for setting show, status and
   * priority at once
   * @param {String} show A status message
   * @param {String} status A status indicator as defined by XMPP
   * @param {int} prio A priority for this resource
   * @return this
   * @type JSJaCPacket
   */
  this.setPresence = function(show,status,prio) {
    if (show)
      this.setShow(show);
    if (status)
      this.setStatus(status);
    if (prio)
      this.setPriority(prio);
    return this; 
  };

  /**
   * Gets the status of this presence
   * @return The status indicator as defined by XMPP
   * @type String
   */
  this.getStatus = function() {	return this.getChildVal('status');	};
  /**
   * Gets the status message of this presence
   * @return The status message
   * @type String
   */
  this.getShow = function() { return this.getChildVal('show'); };
  /**
   * Gets the priority of this status message
   * @return A resource priority
   * @type int
   */
  this.getPriority = function() { return this.getChildVal('priority'); };
}

/**
 * A jabber/XMPP iq packet
 * @class Models the XMPP notion of an 'iq' packet
 * @extends JSJaCPacket
 */
function JSJaCIQ() {
  /**
   * @ignore
   */
  this.base = JSJaCPacket;
  this.base('iq');

  /**
   * Some combined method to set 'to', 'type' and 'id' at once
   * @param {String} to the recepients JID
   * @param {String} type A XMPP compliant iq type (one of 'set', 'get', 'result' and 'error'
   * @param {String} id A packet ID
   * @return this
   * @type JSJaCIQ
   */
  this.setIQ = function(to,type,id) {
    if (to)
      this.setTo(to);
    if (type)
      this.setType(type);
    if (id)
      this.setID(id);
    return this; 
  };
  /**
   * Creates a 'query' child node with given XMLNS
   * @param {String} xmlns The namespace for the 'query' node
   * @return The query node
   * @type {@link  http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/core.html#ID-1950641247 Node}
   */
  this.setQuery = function(xmlns) {
    var query;
    try {
      query = this.getDoc().createElementNS(xmlns,'query');
    } catch (e) {
      // fallback
      query = this.getDoc().createElement('query');
    }
    if (query && query.getAttribute('xmlns') != xmlns) // fix opera 8.5x
      query.setAttribute('xmlns',xmlns);
    this.getNode().appendChild(query);
    return query;
  };

  /**
   * Gets the 'query' node of this packet
   * @return The query node
   * @type {@link  http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/core.html#ID-1950641247 Node}
   */
  this.getQuery = function() {
    return this.getNode().getElementsByTagName('query').item(0);
  };
  /**
   * Gets the XMLNS of the query node contained within this packet
   * @return The namespace of the query node
   * @type String
   */
  this.getQueryXMLNS = function() {
    if (this.getQuery())
      return this.getQuery().namespaceURI;
    else
      return null;
  };
}

/**
 * A jabber/XMPP message packet
 * @class Models the XMPP notion of an 'message' packet
 * @extends JSJaCPacket
 */
function JSJaCMessage() {
  /**
   * @ignore
   */
  this.base = JSJaCPacket;
  this.base('message');

  /**
   * Sets the body of the message
   * @param {String} body Your message to be sent along
   * @return this message
   * @type JSJaCMessage
   */
  this.setBody = function(body) {
    this._setChildNode("body",body);
    return this; 
  };
  /**
   * Sets the subject of the message
   * @param {String} subject Your subject to be sent along
   * @return this message
   * @type JSJaCMessage
   */
  this.setSubject = function(subject) {
    this._setChildNode("subject",subject);
    return this; 
  };
  /**
   * Sets the 'tread' attribute for this message. This is used to identify
   * threads in chat conversations
   * @param {String} thread Usually a somewhat random hash.
   * @return this message
   * @type JSJaCMessage
   */
  this.setThread = function(thread) {
    this._setChildNode("thread", thread);
    return this; 
  };
 /**
   * Gets the 'thread' identifier for this message
   * @return A thread identifier
   * @type String
   */
  this.getThread = function() { return this.getChildVal('thread'); };
  /**
   * Gets the body of this message
   * @return The body of this message
   * @type String
   */
  this.getBody = function() { return this.getChildVal('body'); };
  /**
   * Gets the subject of this message
   * @return The subject of this message
   * @type String
   */
  this.getSubject = function() { return this.getChildVal('subject') };
}

/**
 * Tries to transform a w3c DOM node to JSJaC's internal representation 
 * (JSJaCPacket type, one of JSJaCPresence, JSJaCMessage, JSJaCIQ)
 * @param: {Node
 * http://www.w3.org/TR/2000/REC-DOM-Level-2-Core-20001113/core.html#ID-1950641247}
 * node The node to be transformed
 * @return A JSJaCPacket representing the given node. If node's root
 * elemenent is not one of 'message', 'presence' or 'iq',
 * <code>null</code> is being returned.
 * @type JSJaCPacket
 */
JSJaCPacket.wrapNode = function(node) {
  var aNode;
  switch (node.nodeName.toLowerCase()) {
  case 'presence':
    aNode = new JSJaCPresence();
    break;
  case 'message':
    aNode = new JSJaCMessage();
    break;
  case 'iq':
    aNode = new JSJaCIQ();
    break;
  default : // unknown
    return null;
  }

  aNode._replaceNode(node);

  return aNode;
}

/**
 * @fileoverview Contains all things in common for all subtypes of connections
 * supported.
 * @author Stefan Strigler steve@zeank.in-berlin.de
 * @version $Revision: 313 $
 */

var JSJAC_HAVEKEYS = true;  // whether to use keys
var JSJAC_NKEYS    = 16;    // number of keys to generate
var JSJAC_INACTIVITY = 300; // qnd hack to make suspend/resume work more smoothly with polling
var JSJAC_ERR_COUNT = 10;   // number of retries in case of connection errors

var JSJAC_ALLOW_PLAIN = true; // whether to allow plaintext logins

var JSJAC_CHECKQUEUEINTERVAL = 100; // msecs to poll send queue
var JSJAC_CHECKINQUEUEINTERVAL = 100; // msecs to poll incoming queue

/**
 * Creates a new Jabber connection (a connection to a jabber server)
 * @class Somewhat abstract base class for jabber connections. Contains all
 * of the code in common for all jabber connections
 * @constructor
 * @param {JSON http://www.json.org/index} oArg JSON with properties: <br>
 * * <code>httpbase</code> the http base address of the service to be used for
 * connecting to jabber<br>
 * * <code>oDbg</code> (optional) a reference to a debugger interface
 */
function JSJaCConnection(oArg) {
  oCon = this; // remember reference to ourself
  if (oArg && oArg.oDbg && oArg.oDbg.log)
    /**
     * Reference to debugger interface 
     *(needs to implement method <code>log</code>)
     * @type Debugger
     */
    this.oDbg = oArg.oDbg; 
  else {
    this.oDbg = new Object(); // always initialise a debugger
    this.oDbg.log = function() { };
  }

  if (oArg && oArg.httpbase)
    /** 
     * @private
     */
    this._httpbase = oArg.httpbase;
  
  if (oArg && oArg.allow_plain)
    /** 
     * @private
     */
    this.allow_plain = oArg.allow_plain;
  else 
    this.allow_plain = JSJAC_ALLOW_PLAIN;

  /** 
   * @private
   */
  this._connected = false;
  /** 
   * @private
   */
  this._events = new Array();
  /** 
   * @private
   */
  this._keys = null;
  /** 
   * @private
   */
  this._ID = 0;
  /** 
   * @private
   */
  this._inQ = new Array();
  /** 
   * @private
   */
  this._pQueue = new Array();
  /** 
   * @private
   */
  this._regIDs = new Array();
  /** 
   * @private
   */
  this._req = new Array();
  /** 
   * @private
   */
  this._status = 'intialized';
  /** 
   * @private
   */
  this._errcnt = 0;
  /** 
   * @private
   */
  this._inactivity = JSJAC_INACTIVITY;

  /**
   * Tells whether this connection is connected
   * @return <code>true</code> if this connections is connected, 
   * <code>false</code> otherwise 
   * @type boolean
   */
  this.connected = function() { return this._connected; };
  /**
   * Gets current value of polling interval
   * @return Polling interval in milliseconds
   * @type int
   */
  this.getPollInterval = function() { return this._timerval; };
  /**
   * Registers an event handler (callback) for this connection
   * @param {String} event One of

   * <ul>
   * <li>onConnect - connection has been established and authenticated</li>
   * <li>onDisconnect - connection has been disconnected</li>
   * <li>onResume - connection has been resumed</li>

   * <li>onStatusChanged - connection status has changed, current
   * status as being passed argument to handler. See {@link #status}.</li>

   * <li>onError - an error has occured, error node is supplied as
   * argument, like this:<br><code>&lt;error code='404' type='cancel'&gt;<br>
   * &lt;item-not-found xmlns='urn:ietf:params:xml:ns:xmpp-stanzas'/&gt;<br>
   * &lt;/error&gt;</code></li>

   * <li>packet_in - a packet has been received (argument: the
   * packet)</li>

   * <li>packet_out - a packet is to be sent(argument: the
   * packet)</li>

   * <li>message_in | message - a message has been received (argument:
   * the packet)</li>

   * <li>message_out - a message packet is to be sent (argument: the
   * packet)</li>

   * <li>presence_in | presence - a presence has been received
   * (argument: the packet)</li>

   * <li>presence_out - a presence packet is to be sent (argument: the
   * packet)</li>

   * <li>iq_in | iq - an iq has been received (argument: the packet)</li>
   * <li>iq_out - an iq is to be sent (argument: the packet)</li>
   * </ul>

   * Note: All of the packet handlers for specific packets (like
   * message_in, presence_in and iq_in) fire only if there's no
   * callback associated with the id

   * @param {Function} handler The handler to be called when event occurs
   */
  this.registerHandler = function(event,handler) {
    event = event.toLowerCase(); // don't be case-sensitive here
    if (!this._events[event])
      this._events[event] = new Array(handler);
    else
      this._events[event] = this._events[event].concat(handler);
    this.oDbg.log("registered handler for event '"+event+"'",2);
  };
  /** 
   * Resumes this connection from saved state (cookie)
   * @return Whether resume was successful
   * @type boolean
   */
  this.resume = function() {
    try {
      this._setStatus('resuming');
      var s = unescape(JSJaCCookie.read('JSJaC_State').getValue());
      
      this.oDbg.log('read cookie: '+s,2);

      var o = s.parseJSON();
      
      for (var i in o)
        if (o.hasOwnProperty(i))
          this[i] = o[i];
      
      // copy keys - not being very generic here :-/
      if (this._keys) {
        this._keys2 = new JSJaCKeys();
        var u = this._keys2._getSuspendVars();
        for (var i=0; i<u.length; i++)
          this._keys2[u[i]] = this._keys[u[i]];
        this._keys = this._keys2;
      }

      try {
        JSJaCCookie.read('JSJaC_State').erase();
      } catch (e) {}

      if (this._connected) {
        // don't poll too fast!
        this._handleEvent('onresume');
        setTimeout("oCon._resume()",this.getPollInterval());
      }

      return this._connected;
    } catch (e) {
      if (e.message)
        this.oDbg.log("Resume failed: "+e.message, 1);
      else
        this.oDbg.log("Resume failed: "+e, 1);
      return false;
    }
  }
  this.send = JSJaCSend;
  /**
   * Sets polling interval for this connection
   * @param {int} millisecs Milliseconds to set timer to
   * @return effective interval this connection has been set to
   * @type int
   */
  this.setPollInterval = function(timerval) {
    if (!timerval || isNaN(timerval)) {
      this.oDbg.log("Invalid timerval: " + timerval,1);
      throw "Invalid interval";
    }
    this._timerval = timerval;
    return this._timerval;
  };
  if (oArg && oArg.timerval)
    this.setPollInterval(oArg.timerval);
  /**
   * Returns current status of this connection
   * @return String to denote current state. One of
   * <ul>
   * <li>'initializing' ... well
   * <li>'connecting' if connect() was called
   * <li>'resuming' if resume() was called
   * <li>'processing' if it's about to operate as normal
   * <li>'onerror_fallback' if there was an error with the request object
   * <li>'protoerror_fallback' if there was an error at the http binding protocol flow (most likely that's where you interested in)
   * <li>'internal_server_error' in case of an internal server error
   * <li>'suspending' if suspend() is being called
   * <li>'aborted' if abort() was called
   * <li>'disconnecting' if disconnect() has been called
   * </ul>
   * @type String
   */
  this.status = function() { return this._status; }
  /**
   * Suspsends this connection (saving state for later resume)
   */
  this.suspend = function() {
		
    // remove timers
    clearTimeout(this._timeout);
    clearInterval(this._interval);
    clearInterval(this._inQto);

    this._suspend();

    var u = ('_connected,_keys,_ID,_inQ,_pQueue,_regIDs,_errcnt,_inactivity,domain,username,resource,jid,fulljid,_sid,_httpbase,_timerval,_is_polling').split(',');
    u = u.concat(this._getSuspendVars());
    var s = new Object();

    for (var i=0; i<u.length; i++) {
      if (!this[u[i]]) continue; // hu? skip these!
      if (this[u[i]]._getSuspendVars) {
        var uo = this[u[i]]._getSuspendVars();
        var o = new Object();
        for (var j=0; j<uo.length; j++)
          o[uo[j]] = this[u[i]][uo[j]];
      } else
        var o = this[u[i]];

      s[u[i]] = o;
    }
    var c = new JSJaCCookie('JSJaC_State', escape(s.toJSONString()), this._inactivity);
    this.oDbg.log("writing cookie: "+unescape(c.value)+"\n(length:"+unescape(c.value).length+")",2);
    c.write();

    try {
      var c2 = JSJaCCookie.read('JSJaC_State');
      if (c.value != c2.value) {
        this.oDbg.log("Suspend failed writing cookie.\nRead: "+unescape(JSJaCCookie.read('JSJaC_State')), 1);
        c.erase();
      }

      this._connected = false;

      this._setStatus('suspending');
    } catch (e) {
      this.oDbg.log("Failed reading cookie 'JSJaC_State': "+e.message);
    }

  };

  this._abort                 = JSJaCAbort;
  this._checkInQ              = JSJaCCheckInQ;
  this._checkQueue            = JSJaCHBCCheckQueue;

  this._doAuth                = JSJaCAuth;

  this._doInBandReg           = JSJaCInBandReg;
  this._doInBandRegDone       = JSJaCInBandRegDone;

  this._doLegacyAuth          = JSJaCLegacyAuth;
  this._doLegacyAuth2         = JSJaCLegacyAuth2;
  this._doLegacyAuthDone      = JSJaCLegacyAuthDone;

  this._sendRaw               = JSJaCSendRaw;

  this._doSASLAuth            = JSJaCSASLAuth;

  this._doSASLAuthDigestMd5S1 = JSJaCSASLAuthDigestMd5S1;
  this._doSASLAuthDigestMd5S2 = JSJaCSASLAuthDigestMd5S2;

  this._doSASLAuthDone        = JSJaCSASLAuthDone;

  this._doStreamBind          = JSJaCStreamBind;
  this._doXMPPSess            = JSJaCXMPPSess;
  this._doXMPPSessDone        = JSJaCXMPPSessDone;
  
  /** 
   * @private
   */
  this._handleEvent = function(event,arg) {
    event = event.toLowerCase(); // don't be case-sensitive here
    this.oDbg.log("incoming event '"+event+"'",3);
    if (!this._events[event])
      return;
    this.oDbg.log("handling event '"+event+"'",2);
    for (var i=0;i<this._events[event].length; i++) {
      if (this._events[event][i]) {
        try {
          if (arg)
            this._events[event][i](arg);
          else
            this._events[event][i]();
        } catch (e) { this.oDbg.log(e.name+": "+ e.message); }
      }
    }
  };
  /** 
   * @private
   */
  this._handlePID = function(aJSJaCPacket) {
    if (!aJSJaCPacket.getID())
      return false;
    for (var i in this._regIDs) {
      if (this._regIDs.hasOwnProperty(i) &&
          this._regIDs[i] && i == aJSJaCPacket.getID()) {
        var pID = aJSJaCPacket.getID();
        this.oDbg.log("handling "+pID,3);
        try {
          this._regIDs[i].cb(aJSJaCPacket,this._regIDs[i].arg);
        } catch (e) { this.oDbg.log(e.name+": "+ e.message); }
        this._unregisterPID(pID);
        return true;
      }
    }
    return false;
  };
  this._handleResponse = JSJaCHandleResponse;
  this._parseStreamFeatures = JSJaCParseStreamFeatures;
  this._process = JSJaCProcess;
  /** 
   * @private
   */
  this._registerPID = function(pID,cb,arg) {
    if (!pID || !cb)
      return false;
    this._regIDs[pID] = new Object();
    this._regIDs[pID].cb = cb;
    if (arg)
      this._regIDs[pID].arg = arg;
    this.oDbg.log("registered "+pID,3);
    return true;
  };
  _sendEmpty = JSJaCSendEmpty;
  /** 
   * @private
   */
  this._setStatus = function(status) {
    if (!status || status == '')
      return;
    if (status != this._status) { // status changed!
      this._status = status;
      this._handleEvent('onstatuschanged', status);
      this._handleEvent('status_changed', status);
    }
  }
  /** 
   * @private
   */
  this._unregisterPID = function(pID) {
    if (!this._regIDs[pID])
      return false;
    this._regIDs[pID] = null;
    this.oDbg.log("unregistered "+pID,3);
    return true;
  };

}

/*** *** *** START AUTH STUFF *** *** ***/

/**
 * @private
 */
function JSJaCParseStreamFeatures(doc) {
  if (!doc) {
    this.oDbg.log("nothing to parse ... aborting",1);
    return false;
  }

  this.mechs = new Object(); 
  var lMec1 = doc.getElementsByTagName("mechanisms");
  this.has_sasl = false;
  for (var i=0; i<lMec1.length; i++)
    if (lMec1.item(i).getAttribute("xmlns") == "urn:ietf:params:xml:ns:xmpp-sasl") {
      this.has_sasl=true;
      var lMec2 = lMec1.item(i).getElementsByTagName("mechanism");
      for (var j=0; j<lMec2.length; j++)
        this.mechs[lMec2.item(j).firstChild.nodeValue] = true;
      break;
    }
  if (this.has_sasl)
    this.oDbg.log("SASL detected",2);
  else {
    this.authtype = 'nonsasl';
    this.oDbg.log("No support for SASL detected",2);
  }

  /* [TODO] 
   * check if in-band registration available
   * check for session and bind features
   */
}

/**
 * @private
 */
function JSJaCInBandReg() {
  if (this.authtype == 'saslanon' || this.authtype == 'anonymous')
    return; // bullshit - no need to register if anonymous

  /* ***
   * In-Band Registration see JEP-0077
   */

  var iq = new JSJaCIQ();
  iq.setType('set');
  iq.setID('reg1');
  var query = iq.setQuery('jabber:iq:register');
  query.appendChild(iq.getDoc().createElement('username')).appendChild(iq.getDoc().createTextNode(this.username));
  query.appendChild(iq.getDoc().createElement('password')).appendChild(iq.getDoc().createTextNode(this.pass));

  this.send(iq,this._doInBandRegDone);
}

/**
 * @private
 */
function JSJaCInBandRegDone(iq) {
  if (iq && iq.getType() == 'error') { // we failed to register
    oCon.oDbg.log("registration failed for "+oCon.username,0);
    oCon._handleEvent('onerror',iq.getNode().getElementsByTagName('error').item(0));
    return;
  }

  oCon.oDbg.log(oCon.username + " registered succesfully",0);

  oCon._doAuth();
}

/**
 * @private
 */
function JSJaCAuth() {
  if (this.has_sasl && this.authtype == 'nonsasl')
    this.oDbg.log("Warning: SASL present but not used", 1);

  if (!this._doSASLAuth() && 
      !this._doLegacyAuth()) {
    this.oDbg.log("Auth failed for authtype "+this.authtype,1);
    this.disconnect();
    return false;
  }
  return true;
}

/*** *** *** LEGACY AUTH *** *** ***/

/**
 * @private
 */
function JSJaCLegacyAuth() {
  if (this.authtype != 'nonsasl' && this.authtype != 'anonymous')
    return false;

  /* ***
   * Non-SASL Authentication as described in JEP-0078
   */
  var iq = new JSJaCIQ();
  iq.setIQ(oCon.server,'get','auth1');
  iq.appendNode('query', {xmlns: 'jabber:iq:auth'},
                [['username', oCon.username]]);

  this.send(iq,this._doLegacyAuth2);
  return true;
}

/**
 * @private
 */
function JSJaCLegacyAuth2(iq) {
  if (!iq || iq.getType() != 'result') {
    if (iq && iq.getType() == 'error') 
      oCon._handleEvent('onerror',iq.getChild('error'));
    oCon.disconnect();
    return;
  } 

  var use_digest = (iq.getChild('digest') != null);

  /* ***
   * Send authentication
   */
  var iq = new JSJaCIQ();
  iq.setIQ(oCon.server,'set','auth2');

  query = iq.appendNode('query', {xmlns: 'jabber:iq:auth'},
                        [['username', oCon.username],
                         ['resource', oCon.resource]]);

  if (use_digest) { // digest login
    query.appendChild(iq.buildNode('digest', hex_sha1(oCon.streamid + oCon.pass)));
  } else if (oCon.allow_plain) { // use plaintext auth
    query.appendChild(iq.buildNode('password', oCon.pass));
  } else {
    oCon.oDbg.log("no valid login mechanism found",1);
    oCon.disconnect();
    return false;
  }

  oCon.send(iq,oCon._doLegacyAuthDone);
}

/**
 * @private
 */
function JSJaCLegacyAuthDone(iq) {
  if (iq.getType() != 'result') { // auth' failed
    if (iq.getType() == 'error')
      oCon._handleEvent('onerror',iq.getNode().getElementsByTagName('error').item(0));
    oCon.disconnect();
  } else
    oCon._handleEvent('onconnect');
}

/*** *** *** END LEGACY AUTH *** *** ***/

/*** *** *** SASL AUTH *** *** ***/

/**
 * @private
 */
function JSJaCSASLAuth() {
  if (this.authtype == 'nonsasl' || this.authtype == 'anonymous')
    return false;

  if (this.authtype == 'saslanon') {
    if (this.mechs['ANONYMOUS']) {
      this.oDbg.log("SASL using mechanism 'ANONYMOUS'",2);
      return this._sendRaw("<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='ANONYMOUS'/>",
                           '_doSASLAuthDone');
    }
    this.oDbg.log("SASL ANONYMOUS requested but not supported",1);
  } else {
    if (this.mechs['DIGEST-MD5']) {
      this.oDbg.log("SASL using mechanism 'DIGEST-MD5'",2);
      return this._sendRaw("<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='DIGEST-MD5'/>",
                           '_doSASLAuthDigestMd5S1');
    } else if (this.allow_plain && this.mechs['PLAIN']) {
      this.oDbg.log("SASL using mechanism 'PLAIN'",2);
      var authStr = this.username+'@'+
        this.domain+String.fromCharCode(0)+
        this.username+String.fromCharCode(0)+
        this.pass;
      this.oDbg.log("authenticating with '"+authStr+"'",2);
      authStr = btoa(authStr);
      return this._sendRaw("<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='PLAIN'>"+authStr+"</auth>", 
                           '_doSASLAuthDone');
    }
    this.oDbg.log("No SASL mechanism applied",1);
    this.authtype = 'nonsasl'; // fallback
  }
  return false;
}

/**
 * @private
 */
function JSJaCSASLAuthDigestMd5S1(req) {
  this.oDbg.log(req.r.responseText,2);

  var doc = oCon._prepareResponse(req);
  if (!doc || doc.getElementsByTagName("challenge").length == 0) {
    this.oDbg.log("challenge missing",1);
    this.disconnect();
  } else {
    var challenge = atob(doc.getElementsByTagName("challenge")
                         .item(0).firstChild.nodeValue);
    this.oDbg.log("got challenge: "+challenge,2);
    this._nonce = challenge.substring(challenge.indexOf("nonce=")+7);
    this._nonce = this._nonce.substring(0,this._nonce.indexOf("\""));
    this.oDbg.log("nonce: "+this._nonce,2);
    if (this._nonce == '' || this._nonce.indexOf('\"') != -1) {
      this.oDbg.log("nonce not valid, aborting",1);
      this.disconnect();
      return;
    }

    this._digest_uri = "xmpp/";
//     if (typeof(this.host) != 'undefined' && this.host != '') {
//       this._digest-uri += this.host;
//       if (typeof(this.port) != 'undefined' && this.port)
//         this._digest-uri += ":" + this.port;
//       this._digest-uri += '/';
//     }
    this._digest_uri += this.domain;

    this._cnonce = cnonce(14);

    this._nc = '00000001';

    var A1 = str_md5(this.username+':'+this.domain+':'+this.pass)+
      ':'+this._nonce+':'+this._cnonce;

    var A2 = 'AUTHENTICATE:'+this._digest_uri;

    var response = hex_md5(hex_md5(A1)+':'+this._nonce+':'+this._nc+':'+
                           this._cnonce+':auth:'+hex_md5(A2));

    var rPlain = 'username="'+this.username+'",realm="'+this.domain+
      '",nonce="'+this._nonce+'",cnonce="'+this._cnonce+'",nc="'+this._nc+
      '",qop=auth,digest-uri="'+this._digest_uri+'",response="'+response+
      '",charset=utf-8';
    
    this.oDbg.log("response: "+rPlain,2);

    this._sendRaw("<response xmlns='urn:ietf:params:xml:ns:xmpp-sasl'>"+
                  binb2b64(str2binb(rPlain))+"</response>",
                  '_doSASLAuthDigestMd5S2');
  }
}

/**
 * @private
 */
function JSJaCSASLAuthDigestMd5S2(req) {
  this.oDbg.log(req.r.responseText,2);

  var doc = this._prepareResponse(req);

  if (doc.firstChild.nodeName == 'failure') {
    if (doc.firstChild.xml)
      this.oDbg.log("auth error: "+doc.firstChild.xml,1);
    else 
      this.oDbg.log("auth error",1);
    this.disconnect();
  }

  var response = atob(doc.firstChild.firstChild.nodeValue)
  this.oDbg.log("response: "+response,2);

  var rspauth = response.substring(response.indexOf("rspauth=")+8);
  this.oDbg.log("rspauth: "+rspauth,2);

  var A1 = str_md5(this.username+':'+this.domain+':'+this.pass)+
    ':'+this._nonce+':'+this._cnonce;

  var A2 = ':'+this._digest_uri;

  var rsptest = hex_md5(hex_md5(A1)+':'+this._nonce+':'+this._nc+':'+
                        this._cnonce+':auth:'+hex_md5(A2));
  this.oDbg.log("rsptest: "+rsptest,2);

  if (rsptest != rspauth) {
    this.oDbg.log("SASL Digest-MD5: server repsonse with wrong rspauth",1);
    this.disconnect();
    return;
  }

  if (doc.firstChild.nodeName == 'success')
    this._reInitStream(this.domain,'_doStreamBind');
  else // some extra turn
    this._sendRaw("<response xmlns='urn:ietf:params:xml:ns:xmpp-sasl'/>",
                  '_doSASLAuthDone');
}

/**
 * @private
 */
function JSJaCSASLAuthDone(req) {
  var doc = this._prepareResponse(req);
  if (doc.firstChild.nodeName != 'success') {
    this.oDbg.log("auth failed",1);
    this.disconnect();
  } else
    this._reInitStream(this.domain,'_doStreamBind');
}

/**
 * @private
 */
function JSJaCStreamBind() {
  var iq = new JSJaCIQ();
  iq.setIQ(this.domain,'set','bind_1');
  var eBind = iq.getDoc().createElement("bind");
  eBind.setAttribute("xmlns","urn:ietf:params:xml:ns:xmpp-bind");
  eBind.appendChild(iq.getDoc().createElement("resource"))
    .appendChild(iq.getDoc().createTextNode(this.resource));
  iq.getNode().appendChild(eBind);
  this.oDbg.log(iq.xml());
  this.send(iq,this._doXMPPSess);
}

/**
 * @private
 */
function JSJaCXMPPSess(iq) {
  if (iq.getType() != 'result' || iq.getType() == 'error') { // failed
    oCon.disconnect();
    if (iq.getType() == 'error')
      oCon._handleEvent('onerror',iq.getNode().getElementsByTagName('error').item(0));
    return;
  }
  
  oCon.fulljid = iq.getDoc().firstChild.getElementsByTagName('jid').item(0).firstChild.nodeValue;
  oCon.jid = oCon.fulljid.substring(0,oCon.fulljid.lastIndexOf('/'));
  
  iq = new JSJaCIQ();
  iq.setIQ(this.domain,'set','sess_1');
  var eSess = iq.getDoc().createElement("session");
  eSess.setAttribute("xmlns","urn:ietf:params:xml:ns:xmpp-session");
  iq.getNode().appendChild(eSess);
  oCon.oDbg.log(iq.xml());
  oCon.send(iq,oCon._doXMPPSessDone);
}

/**
 * @private
 */
function JSJaCXMPPSessDone(iq) {
  if (iq.getType() != 'result' || iq.getType() == 'error') { // failed
    oCon.disconnect();
    if (iq.getType() == 'error')
      oCon._handleEvent('onerror',iq.getNode().getElementsByTagName('error').item(0));
    return;
  } else
    oCon._handleEvent('onconnect');
}

/*** *** *** END SASL AUTH *** *** ***/

/*** *** *** END AUTH STUFF *** *** ***/

/**
 * @private
 */
function JSJaCSendRaw(xml,cb,arg) 
{
  var slot = this._getFreeSlot();
  this._req[slot] = this._setupRequest(true);
  
  this._req[slot].r.onreadystatechange = function() {
    if (typeof(oCon) == 'undefined' || !oCon || !oCon.connected())
      return;
    if (oCon._req[slot].r.readyState == 4) {
      oCon.oDbg.log("async recv: "+oCon._req[slot].r.responseText,4);
      if (typeof(cb) != 'undefined')
        eval("oCon."+cb+"(oCon._req[slot],"+arg+")");
    }
  }
  
  if (typeof(this._req[slot].r.onerror) != 'undefined') {
    this._req[slot].r.onerror = function(e) {
      if (typeof(oCon) == 'undefined' || !oCon || !oCon.connected())
        return;
      oCon.oDbg.log('XmlHttpRequest error',1);
      return false;
    }
  }
  
  var reqstr = this._getRequestString(xml);
  this.oDbg.log("sending: " + reqstr,4);
  this._req[slot].r.send(reqstr);
  return true;
}

/**
 * Sends a JSJaCPacket
 * @param {JSJaCPacket} packet  The packet to send
 * @param {Function}    cb      The callback to be called if there's a reply 
 * to this packet (identified by id) [optional]
 * @param {Object}      arg     Arguments passed to the callback 
 * (additionally to the packet received) [optional]
 */
function JSJaCSend(packet,cb,arg) {
  // remember id for response if callback present
  if (packet && cb) {
    if (!packet.getID())
      packet.setID('JSJaCID_'+this._ID++); // generate an ID

    // register callback with id
    this._registerPID(packet.getID(),cb,arg);
  }

  if (packet) {
    try {
      if (packet.pType)
        this._handleEvent(packet.pType()+'_out', packet);
      this._handleEvent("packet_out", packet);
      this._pQueue = this._pQueue.concat(packet.xml());
    } catch (e) {
      this.oDbg.log(e.toString(),1);
    }
  }

  return;
}

/**
 * @private
 */
function JSJaCProcess(timerval) {
  if (!this.connected()) {
    this.oDbg.log("Connection lost ...",1);
    if (this._interval)
      clearInterval(this._interval);
    return;
  }

  if (timerval)
    this.setPollInterval(timerval);

  if (this._timeout)
    clearTimeout(this._timeout);

  var slot = this._getFreeSlot();
	
  if (slot < 0)
    return;

  if (typeof(this._req[slot]) != 'undefined' && typeof(this._req[slot].r) != 'undefined' && this._req[slot].r.readyState != 4) {
    this.oDbg.log("Slot "+slot+" is not ready");
    return;
  }
		
  if (!this.isPolling() && this._pQueue.length == 0 && this._req[(slot+1)%2] && this._req[(slot+1)%2].r.readyState != 4) {
    this.oDbg.log("all slots bussy, standby ...", 2);
    return;
  }

  if (!this.isPolling())
    this.oDbg.log("Found working slot at "+slot,2);

  this._req[slot] = this._setupRequest(true);

  /* setup onload handler for async send */
  this._req[slot].r.onreadystatechange = function() {
    if (typeof(oCon) == 'undefined' || !oCon || !oCon.connected())
      return;
    oCon.oDbg.log("ready state changed for slot "+slot+" ["+oCon._req[slot].r.readyState+"]",4);
    if (oCon._req[slot].r.readyState == 4) {
      oCon._setStatus('processing');
      oCon.oDbg.log("async recv: "+oCon._req[slot].r.responseText,4);
      oCon._handleResponse(oCon._req[slot]);
      // schedule next tick
      if (oCon._pQueue.length) {
        oCon._timeout = setTimeout("oCon._process()",100);
      } else {
        oCon.oDbg.log("scheduling next poll in "+oCon.getPollInterval()+" msec", 4);
        oCon._timeout = setTimeout("oCon._process()",oCon.getPollInterval());
      }
    }
  };

  if (typeof(this._req[slot].r.onerror) != 'undefined') {
    this._req[slot].r.onerror = function(e) {
      if (typeof(oCon) == 'undefined' || !oCon || !oCon.connected())
        return;
      oCon._errcnt++;
      oCon.oDbg.log('XmlHttpRequest error ('+oCon._errcnt+')',1);
      if (oCon._errcnt > JSJAC_ERR_COUNT) {

        // abort
        oCon._abort();
        return false;
      }

      oCon._setStatus('onerror_fallback');
				
      // schedule next tick
      setTimeout("oCon._resume()",oCon.getPollInterval());
      return false;
    };
  }

  var reqstr = this._getRequestString();

  if (typeof(this._rid) != 'undefined') // remember request id if any
    this._req[slot].rid = this._rid;

  this.oDbg.log("sending: " + reqstr,4);
  this._req[slot].r.send(reqstr);
}

/**
 * @private
 */
function JSJaCHBCCheckQueue() {
  if (this._pQueue.length != 0)
    this._process();
  return true;
}

/**
 * send empty request 
 * waiting for stream id to be able to proceed with authentication 
 * @private
 */
function JSJaCSendEmpty() {
  var slot = this._getFreeSlot();
  this._req[slot] = this._setupRequest(true);

  this._req[slot].r.onreadystatechange = function() {
    if (typeof(oCon) == 'undefined' || !oCon)
      return;
    if (oCon._req[slot].r.readyState == 4) {
      oCon.oDbg.log("async recv: "+oCon._req[slot].r.responseText,4);
      oCon._getStreamID(slot); // handle response
    }
  }

  if (typeof(this._req[slot].r.onerror) != 'undefined') {
    this._req[slot].r.onerror = function(e) {
      if (typeof(oCon) == 'undefined' || !oCon || !oCon.connected())
        return;
      oCon.oDbg.log('XmlHttpRequest error',1);
      return false;
    };
  }

  var reqstr = this._getRequestString();
  this.oDbg.log("sending: " + reqstr,4);
  this._req[slot].r.send(reqstr);
}

/**
 * @private
 */
function JSJaCHandleResponse(req) {
  var rootEl = this._prepareResponse(req);

  if (!rootEl)
    return null;

  this.oDbg.log("childNodes: "+rootEl.childNodes.length,3);
  for (var i=0; i<rootEl.childNodes.length; i++) {
    this.oDbg.log("rootEl.childNodes.item("+i+").nodeName: "+rootEl.childNodes.item(i).nodeName,3);
    this._inQ = this._inQ.concat(rootEl.childNodes.item(i));
  }
  return null;
}

/**
 * @private
 */
function JSJaCCheckInQ() {
  for (var i=0; i<this._inQ.length && i<10; i++) {
    var item = this._inQ[0];
    this._inQ = this._inQ.slice(1,this._inQ.length);
    var packet = JSJaCPacket.wrapNode(item);

    if (!packet) {
      this.oDbg.log("wrapNode failed on "+item.xml,2);
      return;
    }

    this._handleEvent("packet_in", packet);

    if (packet.pType && !this._handlePID(packet)) {
        this._handleEvent(packet.pType()+'_in',packet);
        this._handleEvent(packet.pType(),packet);
    }
  }
}

/**
 * @private
 */
function JSJaCAbort() {
  clearTimeout(this._timeout); // remove timer
  this._connected = false;

  this._setStatus('aborted');

  this.oDbg.log("Disconnected.",1);
  this._handleEvent('ondisconnect');
  this._handleEvent('onerror',JSJaCError('500','cancel','service-unavailable'));
}

/**
 * an error packet for internal use
 * @private
 * @constructor
 */
function JSJaCError(code,type,condition) {
  var xmldoc = XmlDocument.create("error","jsjac");

  xmldoc.documentElement.setAttribute('code',code);
  xmldoc.documentElement.setAttribute('type',type);
  xmldoc.documentElement.appendChild(xmldoc.createElement(condition)).setAttribute('xmlns','urn:ietf:params:xml:ns:xmpp-stanzas');
  return xmldoc;
}

/**
 * Creates a new set of hash keys
 * @class Reflects a set of sha1/md5 hash keys for securing sessions
 * @constructor
 * @param {Function} func The hash function to be used for creating the keys
 * @param {Debugger} oDbg Reference to debugger implementation [optional]
 */									  
function JSJaCKeys(func,oDbg) {
  var seed = Math.random();

  /**
   * @private
   */
  this._k = new Array();
  this._k[0] = seed.toString();
  if (oDbg) 
    /**
     * Reference to Debugger
     * @type Debugger
     */
    this.oDbg = oDbg;
  else {
    this.oDbg = {};
    this.oDbg.log = function() {};
  }

  if (func) {
    for (var i=1; i<JSJAC_NKEYS; i++) {
      this._k[i] = func(this._k[i-1]);
      oDbg.log(i+": "+this._k[i],4);
    }
  }

  /**
   * @private
   */
  this._indexAt = JSJAC_NKEYS-1;
  /**
   * Gets next key from stack
   * @return New hash key
   * @type String
   */
  this.getKey = function() { 
    return this._k[this._indexAt--]; 
  };
  /**
   * Indicates whether there's only one key left
   * @return <code>true</code> if there's only one key left, false otherwise
   * @type boolean
   */
  this.lastKey = function() { return (this._indexAt == 0); };
  /**
   * Returns number of overall/initial stack size
   * @return Number of keys created
   * @type int
   */
  this.size = function() { return this._k.length; };

  /**
   * @private
   */
  this._getSuspendVars = function() {
    return ('_k,_indexAt').split(',');
  }
}

/**
 * @fileoverview All stuff related to HTTP Polling
 * @author Stefan Strigler steve@zeank.in-berlin.de
 * @version $Revision: 313 $
 */

/**
 * Instantiates an HTTP Polling session 
 * @class Implementation of {@link
 * http://www.xmpp.org/extensions/xep-0025.html HTTP Polling}
 * @extends JSJaCConnection
 * @constructor
 */
function JSJaCHttpPollingConnection(oArg) {
  /**
   * @ignore
   */
  this.base = JSJaCConnection;
  this.base(oArg);

  // give hint to JSJaCPacket that we're using HTTP Polling ...
  JSJACPACKET_USE_XMLNS = false;

  this.connect = JSJaCHPCConnect;
  this.disconnect = JSJaCHPCDisconnect;
  /**
   * Tells whether this implementation of JSJaCConnection is polling
   * Useful if it needs to be decided
   * whether it makes sense to allow for adjusting or adjust the
   * polling interval {@link JSJaCConnection#setPollInterval}
   * @return <code>true</code> if this is a polling connection, 
   * <code>false</code> otherwise.
   * @type boolean
   */
  this.isPolling = function() { return true; };

  /**
   * @private
   */
  this._getFreeSlot = function() {
    if (typeof(this._req[0]) == 'undefined' || typeof(this._req[0].r) == 'undefined' || this._req[0].r.readyState == 4)
      return 0; 
    else
      return -1;
  }
  this._getRequestString = JSJaCHPCGetRequestString;
  this._getStreamID = JSJaCHPCGetStream;
  /**
   * @private
   */
  this._getSuspendVars = function() {
    return new Array();
  }
  this._prepareResponse = JSJaCHPCPrepareResponse;
  this._reInitStream = JSJaCHPCReInitStream;
  /**
   * @private
   */
  this._resume = function() { 
    this._process(this._timerval);
    this._interval= setInterval("oCon._checkQueue()",JSJAC_CHECKQUEUEINTERVAL);
    this._inQto = setInterval("oCon._checkInQ();",JSJAC_CHECKINQUEUEINTERVAL);
  }
  this._setupRequest = JSJaCHPCSetupRequest;
  /**
   * @private
   */
  this._suspend = function() {};
}

/**
 * @private
 */
function JSJaCHPCSetupRequest(async) {
  var r = XmlHttp.create();
  try {
    r.open("POST",this._httpbase,async);
    r.setRequestHeader('Content-Type','application/x-www-form-urlencoded');
  } catch(e) { this.oDbg.log(e,1); }

  var req = new Object();
  req.r = r;
  return req;
}

/**
 * @private
 */
function JSJaCHPCGetRequestString(raw) {
  var reqstr = this._sid;
  if (JSJAC_HAVEKEYS) {
    reqstr += ";"+this._keys.getKey();
    if (this._keys.lastKey()) {
      this._keys = new JSJaCKeys(b64_sha1,this.oDbg);
      reqstr += ';'+this._keys.getKey();
    }
  }
  reqstr += ',';
  if (raw)
    reqstr += raw;
  while (this._pQueue.length) {
    reqstr += this._pQueue[0];
    this._pQueue = this._pQueue.slice(1,this._pQueue.length);
  }
  return reqstr;
}

/**
 * @private
 */
function JSJaCHPCPrepareResponse(r) {
  var req = r.r;
  if (!this.connected())
    return null;

  /* handle error */
  // proxy error (!)
  if (req.status != 200) {
    this.oDbg.log("invalid response ("+req.status+"):" + req.responseText+"\n"+req.getAllResponseHeaders(),1);

    this._setStatus('internal_server_error');

    clearTimeout(this._timeout); // remove timer
    clearInterval(this._interval);
    clearInterval(this._inQto);
    this._connected = false;
    this.oDbg.log("Disconnected.",1);
    this._handleEvent('ondisconnect');
    this._handleEvent('onerror',JSJaCError('503','cancel','service-unavailable'));
    return null;
  } 

  this.oDbg.log(req.getAllResponseHeaders(),4);
  var aPList = req.getResponseHeader('Set-Cookie');
  aPList = aPList.split(";");
  var sid;
  for (var i=0;i<aPList.length;i++) {
    var aArg = aPList[i].split("=");
    if (aArg[0] == 'ID')
      sid = aArg[1];
  }

  // http polling component error
  if (typeof(sid) != 'undefined' && sid.indexOf(':0') != -1) {
    switch (sid.substring(0,sid.indexOf(':0'))) {
    case '0':
      this.oDbg.log("invalid response:" + req.responseText,1);
      break;
    case '-1':
      this.oDbg.log("Internal Server Error",1);
      break;
    case '-2':
      this.oDbg.log("Bad Request",1);
      break;
    case '-3':
      this.oDbg.log("Key Sequence Error",1);
      break;
    }

    this._setStatus('internal_server_error');

    clearTimeout(this._timeout); // remove timer
    clearInterval(this._interval);
    clearInterval(this._inQto);
    this._handleEvent('onerror',JSJaCError('500','wait','internal-server-error'));
    this._connected = false;
    this.oDbg.log("Disconnected.",1);
    this._handleEvent('ondisconnect');
    return null;
  }

  if (!req.responseText || req.responseText == '')
    return null;

  try {
		
    var doc = JSJaCHttpPollingConnection.parseTree("<body>"+req.responseText+"</body>");

    if (!doc || doc.tagName == 'parsererror') {
      this.oDbg.log("parsererror",1);

      doc = JSJaCHttpPollingConnection.parseTree("<stream:stream xmlns:stream='http://etherx.jabber.org/streams'>"+req.responseText);
      if (doc && doc.tagName != 'parsererror') {
        this.oDbg.log("stream closed",1);

        if (doc.getElementsByTagName('conflict').length > 0)
          this._setStatus("session-terminate-conflict");
				
        clearTimeout(this._timeout); // remove timer
        clearInterval(this._interval);
        clearInterval(this._inQto);
        this._handleEvent('onerror',JSJaCError('503','cancel','session-terminate'));
        this._connected = false;
        this.oDbg.log("Disconnected.",1);
        this._handleEvent('ondisconnect');
      } else
        this.oDbg.log("parsererror:"+doc,1);
			
      return doc;
    }

    return doc;
  } catch (e) {
    this.oDbg.log("parse error:"+e.message,1);
  }
  return null;;
}

/**
 * Actually triggers a connect for this connection
 * @params {JSON} oArg arguments in JSON as follows:
 */
function JSJaCHPCConnect(oArg) {
  // initial request to get sid and streamid

  this.domain = oArg.domain || 'localhost';
  this.username = oArg.username;
  this.resource = oArg.resource || 'jsjac';
  this.pass = oArg.pass;
  this.register = oArg.register;
  this.authtype = oArg.authtype || 'sasl';

  this.jid = this.username + '@' + this.domain;
  this.fulljid = this.jid + this.resource;

  this.authhost = oArg.authhost || this.domain;

  var reqstr = "0";
  if (JSJAC_HAVEKEYS) {
    this._keys = new JSJaCKeys(b64_sha1,this.oDbg); // generate first set of keys
    key = this._keys.getKey();
    reqstr += ";"+key;
  }
  var streamto = this.domain;
  if (this.authhost)
    streamto = this.authhost;
  reqstr += ",<stream:stream to='"+streamto+"' xmlns='jabber:client' xmlns:stream='http://etherx.jabber.org/streams' version='1.0'>";
  this.oDbg.log(reqstr,4);

  this._req[0] = this._setupRequest(false);	
  this._req[0].r.send(reqstr);

  // extract session ID
  this.oDbg.log(this._req[0].r.getAllResponseHeaders(),4);
  var aPList = this._req[0].r.getResponseHeader('Set-Cookie');
  aPList = aPList.split(";");
  for (var i=0;i<aPList.length;i++) {
    aArg = aPList[i].split("=");
    if (aArg[0] == 'ID')
      this._sid = aArg[1];
  }
  this.oDbg.log("got sid: "+this._sid,2);

  oCon = this;
  this._interval= setInterval("oCon._checkQueue()",JSJAC_CHECKQUEUEINTERVAL);
  this._inQto = setInterval("oCon._checkInQ();",JSJAC_CHECKINQUEUEINTERVAL);

  /* wait for initial stream response to extract streamid needed
   * for digest auth
   */
  this._getStreamID();
}

/**
 * @private
 */
function JSJaCHPCGetStream() {

  if (!this._req[0].r.responseXML || this._req[0].r.responseText == '') {
    oCon = this;
    this._timeout = setTimeout("oCon._sendEmpty()",1000);
    return;
  }

  this.oDbg.log(this._req[0].r.responseText,4);

  // extract stream id used for non-SASL authentication
  if (this._req[0].r.responseText.match(/id=[\'\"]([^\'\"]+)[\'\"]/))
    this.streamid = RegExp.$1;
  this.oDbg.log("got streamid: "+this.streamid,2);

  var doc;

  try {
    doc = XmlDocument.create("doc");
    doc.loadXML(this._req[0].r.responseText+'</stream:stream>');
    this._parseStreamFeatures(doc);
  } catch(e) {
    this.oDbg.log("loadXML: "+e.toString(),1);
  }

  if (this.register)
    this._doInBandReg();
  else 
    this._doAuth();

  this._connected = true;
  this._process(this._timerval); // start polling
}

/**
 * @private
 */
function JSJaCHPCReInitStream(to,cb,arg) {
  oCon._sendRaw("<stream:stream xmlns:stream='http://etherx.jabber.org/streams' xmlns='jabber:client' to='"+to+"' version='1.0'>",cb,arg);
}

/**
 * Disconnect from stream, terminate HTTP Polling session
 */
function JSJaCHPCDisconnect() {
  if (!this.connected())
    return;

  this._checkQueue();

  clearInterval(this._interval);
  clearInterval(this._inQto);

  if (this._timeout)
    clearTimeout(this._timeout); // remove timer

  this._req[0] = this._setupRequest(false);
	
  if (JSJAC_HAVEKEYS)
    this._req[0].r.send(this._sid+";"+this._keys.getKey()+",</stream:stream>");
  else
    this._req[0].r.send(this._sid+",</stream:stream>");

  try {
    JSJaCCookie.read('JSJaC_State').erase();
  } catch (e) {}

  this.oDbg.log("Disconnected: "+this._req[0].r.responseText,2);
  this._connected = false;
  this._handleEvent('ondisconnect');
}


/**
 * @private
 */
JSJaCHttpPollingConnection.parseTree = function(s) {
  try {
    var r = XmlDocument.create("body","foo");
    if (typeof(r.loadXML) != 'undefined') {
      r.loadXML(s);
      return r.documentElement;
    } else if (window.DOMParser)
      return (new DOMParser()).parseFromString(s, "text/xml").documentElement;
  } catch (e) { }
  return null;
}


/**
 * @fileoverview All stuff related to HTTP Binding
 * @author Stefan Strigler steve@zeank.in-berlin.de
 * @version $Revision: 313 $
 */
var JSJACHBC_BOSH_VERSION = "1.6";

var JSJACHBC_MAX_HOLD = 1;
var JSJACHBC_MAX_WAIT = 300; 

var JSJACHBC_MAXPAUSE = 120;
/**
 * Instantiates an HTTP Binding session
 * @class Implementation of {@link
 * http://www.xmpp.org/extensions/xep-0206.html XMPP Over BOSH} 
 * formerly known as HTTP Binding.
 * @extends JSJaCConnection
 * @constructor
 */
function JSJaCHttpBindingConnection(oArg) {
  /**
   * @ignore
   */
  this.base = JSJaCConnection;
  this.base(oArg);

  // member vars
  /**
   * @private
   */
  this._hold = JSJACHBC_MAX_HOLD;
  /**
   * @private
   */
  this._inactivity = 0;
  /**
   * @private
   */
  this._last_requests = new Object(); // 'hash' storing hold+1 last requests
  /**
   * @private
   */
  this._last_rid = 0;                 // I know what you did last summer
  /**
   * @private
   */
  this._min_polling = 0;

  /**
   * @private
   */
  this._pause = 0;
  /**
   * @private
   */
  this._wait = JSJACHBC_MAX_WAIT;

  // public methods
  this.connect = JSJaCHBCConnect;
  this.disconnect = JSJaCHBCDisconnect;
  this.inherit = JSJaCHBCInherit;
  /**
   * whether this session is in polling mode
   * @type boolean
   */
  this.isPolling = function() { return (this._hold == 0) }; 
  /**
   * Sets poll interval
   * @param {int} timerval the interval in seconds
   */
  this.setPollInterval = function(timerval) {
    if (!timerval || isNaN(timerval)) {
      this.oDbg.log("Invalid timerval: " + timerval,1);
      return -1;
    }
    if (!this.isPolling()) 
      this._timerval = 100;
    else if (this._min_polling && timerval < this._min_polling*1000)
      this._timerval = this._min_polling*1000;
    else if (this._inactivity && timerval > this._inactivity*1000)
      this._timerval = this._inactivity*1000;
    else
      this._timerval = timerval;
    return this._timerval;
  };

  // private methods
  this._getRequestString = JSJaCHBCGetRequestString;
  /**
   * @private
   */
  this._getFreeSlot = function() {
    for (var i=0; i<this._hold+1; i++)
      if (typeof(this._req[i]) == 'undefined' || typeof(this._req[i].r) == 'undefined' || this._req[i].r.readyState == 4)
        return i;
    return -1; // nothing found
  }
  /**
   * @private
   */
  this._getHold = function() { return this._hold; }
  this._getStreamID = JSJaCHBCGetStreamID;
  /**
   * @private
   */
  this._getSuspendVars = function() {
    return ('host,port,secure,_rid,_last_rid,_wait,_min_polling,_inactivity,_hold,_last_requests,_pause').split(',');
  }
  this._handleInitialResponse = JSJaCHBCHandleInitialResponse;
  this._prepareResponse = JSJaCHBCPrepareResponse;
  this._reInitStream = JSJaCHBCReInitStream;
  /**
   * @private
   */
  this._resume = function() { 
    /* make sure to repeat last request as we can be sure that
     * it had failed (only if we're not using the 'pause' attribute
     */
    if (this._pause == 0 && this._rid >= this._last_rid)
        this._rid = this._last_rid-1; 

    this._process();
    this._inQto = setInterval("oCon._checkInQ();",JSJAC_CHECKINQUEUEINTERVAL);
    this._interval= setInterval("oCon._checkQueue()",JSJAC_CHECKQUEUEINTERVAL);
  }
  /**
   * @private
   */
  this._setHold = function(hold)  {
    if (!hold || isNaN(hold) || hold < 0)
      hold = 0;
    else if (hold > JSJACHBC_MAX_HOLD)
      hold = JSJACHBC_MAX_HOLD;
    this._hold = hold;
    return this._hold;
  };
  this._setupRequest = JSJaCHBCSetupRequest;
  /**
   * @private
   */
  this._suspend = function() {
    if (this._pause == 0)
      return; // got nothing to do

    var slot = this._getFreeSlot();
    // Intentionally synchronous
    this._req[slot] = this._setupRequest(false);

    var reqstr = "<body pause='"+this._pause+"' xmlns='http://jabber.org/protocol/httpbind' sid='"+this._sid+"' rid='"+this._rid+"'";
    if (JSJAC_HAVEKEYS) {
      reqstr += " key='"+this._keys.getKey()+"'";
      if (this._keys.lastKey()) {
        this._keys = new JSJaCKeys(hex_sha1,this.oDbg);
        reqstr += " newkey='"+this._keys.getKey()+"'";
      }

    }
    reqstr += ">";

    while (this._pQueue.length) {
      var curNode = this._pQueue[0];
      reqstr += curNode;
      this._pQueue = this._pQueue.slice(1,this._pQueue.length);
    }

    //reqstr += "<presence type='unavailable' xmlns='jabber:client'/>";
    reqstr += "</body>";

    // Wait for response (for a limited time, 5s)
    var abortTimerID = setTimeout("oCon._req["+slot+"].r.abort();", 5000);
    this.oDbg.log("Disconnecting: " + reqstr,4);
    this._req[slot].r.send(reqstr);
    clearTimeout(abortTimerID); 
  }
}

/**
 * Connects to jabber server, creates an HTTP Binding session, thus
 * creates a stream and handles authentication
 */
function JSJaCHBCConnect(oArg) {
  // initial request to get sid and streamid

  this._setStatus('connecting');

  this.domain = oArg.domain || 'localhost';
  this.username = oArg.username;
  this.resource = oArg.resource;
  this.pass = oArg.pass;
  this.register = oArg.register;
  this.oDbg.log("httpbase: " + this._httpbase + "\domain:" + this.domain,2);
  this.host = oArg.host || this.domain;
  this.port = oArg.port || 5222;
  this.authhost = oArg.authhost || this.domain;
  this.authtype = oArg.authtype || 'sasl';
  if (oArg.secure) {
    this.secure = 'true';
//     if (!oArg.port)
//       this.port = 5223;
  } else 
    this.secure = 'false';

  this.jid = this.username + '@' + this.domain;
  this.fulljid = this.jid + '/' + this.resource;

  if (oArg.wait)
    this._wait = oArg.wait;

  if (oArg.xmllang && oArg.xmllang != '')
    this._xmllang = oArg.xmllang;

  this._rid  = Math.round( 100000.5 + ( ( (900000.49999) - (100000.5) ) * Math.random() ) );

  // setupRequest must be done after rid is created but before first use in reqstr
  var slot = this._getFreeSlot();
  this._req[slot] = this._setupRequest(true); 
  
  var reqstr = "<body hold='"+this._hold+"' xmlns='http://jabber.org/protocol/httpbind' to='"+this.authhost+"' wait='"+this._wait+"' rid='"+this._rid+"'";
  if (oArg.host || oArg.port)
    reqstr += " route='xmpp:"+this.host+":"+this.port+"'";
  if (oArg.secure)
    reqstr += " secure='"+this.secure+"'";
  if (JSJAC_HAVEKEYS) {
    this._keys = new JSJaCKeys(hex_sha1,this.oDbg); // generate first set of keys
    key = this._keys.getKey();
    reqstr += " newkey='"+key+"'";
  }
  if (this._xmllang)
    reqstr += " xml:lang='"+this._xmllang + "'";

  reqstr += " ver='" + JSJACHBC_BOSH_VERSION + "'";
  reqstr += " xmpp:xmlns='urn:xmpp:xbosh'";
  reqstr += " xmpp:version='1.0'";
  reqstr += "/>";


  this.oDbg.log(reqstr,4);

  this._req[slot].r.onreadystatechange = function() {
    if (typeof(oCon) == 'undefined' || !oCon)
      return;
    if (oCon._req[slot].r.readyState == 4) {
      oCon.oDbg.log("async recv: "+oCon._req[slot].r.responseText,4);
      oCon._handleInitialResponse(slot); // handle response
    }
  }

  if (typeof(this._req[slot].r.onerror) != 'undefined') {
    this._req[slot].r.onerror = function(e) {
      if (typeof(oCon) == 'undefined' || !oCon || !oCon.connected())
        return;
      oCon.oDbg.log('XmlHttpRequest error',1);
      return false;
    };
  }

  this._req[slot].r.send(reqstr);
}

/**
 * @private
 */
function JSJaCHBCHandleInitialResponse(slot) {
  try {
    // This will throw an error on Mozilla when the connection was refused
    this.oDbg.log(this._req[slot].r.getAllResponseHeaders(),4);
    this.oDbg.log(this._req[slot].r.responseText,4);
  } catch(ex) {
    this.oDbg.log("No response",4);
  }

  if (this._req[slot].r.status != 200 || !this._req[slot].r.responseXML) {
    this.oDbg.log("initial response broken (status: "+this._req[slot].r.status+")",1);
    this._handleEvent('onerror',JSJaCError('503','cancel','service-unavailable'));
    return;
  }
  var body = this._req[slot].r.responseXML.documentElement;

  if (!body || body.tagName != 'body' || body.namespaceURI != 'http://jabber.org/protocol/httpbind') {
    this.oDbg.log("no body element or incorrect body in initial response",1);
    this._handleEvent("onerror",JSJaCError("500","wait","internal-service-error"));
    return;
  }

  // Check for errors from the server
  if (body.getAttribute("type") == "terminate") {
    this.oDbg.log("invalid response:\n" + this._req[slot].r.responseText,1);
    clearTimeout(this._timeout); // remove timer
    this._connected = false;
    this.oDbg.log("Disconnected.",1);
    this._handleEvent('ondisconnect');
    this._handleEvent('onerror',JSJaCError('503','cancel','service-unavailable'));
    return;
  }

  // get session ID
  this._sid = body.getAttribute('sid');
  this.oDbg.log("got sid: "+this._sid,2);

  // get attributes from response body
  if (body.getAttribute('polling'))
    this._min_polling = body.getAttribute('polling');

  if (body.getAttribute('inactivity'))
    this._inactivity = body.getAttribute('inactivity');
	
  if (body.getAttribute('requests'))
    this._setHold(body.getAttribute('requests')-1);
  this.oDbg.log("set hold to " + this._getHold(),2);

  if (body.getAttribute('ver'))
    this._bosh_version = body.getAttribute('ver');

  if (body.getAttribute('maxpause'))
    this._pause = Number.max(body.getAttribute('maxpause'), JSJACHBC_MAXPAUSE);

  // must be done after response attributes have been collected
  this.setPollInterval(this._timerval);

  /* start sending from queue for not polling connections */
  this._connected = true;

  this._inQto = setInterval("oCon._checkInQ();",JSJAC_CHECKINQUEUEINTERVAL);
  this._interval= setInterval("oCon._checkQueue()",JSJAC_CHECKQUEUEINTERVAL);

  /* wait for initial stream response to extract streamid needed
   * for digest auth
   */
  this._getStreamID(slot);
}

/**
 * @private
 */
function JSJaCHBCGetStreamID(slot) {

  this.oDbg.log(this._req[slot].r.responseText,4);

  if (!this._req[slot].r.responseXML || !this._req[slot].r.responseXML.documentElement) {
    this._handleEvent('onerror',JSJaCError('503','cancel','service-unavailable'));
    return;
  }
  var body = this._req[slot].r.responseXML.documentElement;

  // extract stream id used for non-SASL authentication
  if (body.getAttribute('authid')) {
    this.streamid = body.getAttribute('authid');
    this.oDbg.log("got streamid: "+this.streamid,2);
  } else {
    this._timeout = setTimeout("oCon._sendEmpty()",this.getPollInterval());
    return;
  }

  this._timeout = setTimeout("oCon._process()",this.getPollInterval());

  this._parseStreamFeatures(body);
	
  if (this.register)
    this._doInBandReg();
  else
    this._doAuth();
}

/**
 * Inherit an instantiated HTTP Binding session
 */
function JSJaCHBCInherit(oArg) {
  this.domain = oArg.domain || 'localhost';
  this.username = oArg.username;
  this.resource = oArg.resource;
  this._sid = oArg.sid;
  this._rid = oArg.rid;
  this._min_polling = oArg.polling;
  this._inactivity = oArg.inactivity;
  this._setHold(oArg.requests-1);
  this.setPollInterval(this._timerval);
  if (oArg.wait)
    this._wait = oArg.wait; // for whatever reason

  this._connected = true;

  this._handleEvent('onconnect');

  this._interval= setInterval("oCon._checkQueue()",JSJAC_CHECKQUEUEINTERVAL);
  this._inQto = setInterval("oCon._checkInQ();",JSJAC_CHECKINQUEUEINTERVAL);
  this._timeout = setTimeout("oCon._process()",this.getPollInterval());
}

/**
 * @private
 */
function JSJaCHBCReInitStream(to,cb,arg) {
  /* [TODO] we can't handle 'to' here as this is not (yet) supported
   * by the protocol
   */

  // tell http binding to reinit stream with/before next request
  oCon._reinit = true;
  eval("oCon."+cb+"("+arg+")"); // proceed with next callback

  /* [TODO] make sure that we're checking for new stream features when
   * 'cb' finishes
   */
}

/**
 * Disconnects from stream, terminates HTTP Binding session
 */
function JSJaCHBCDisconnect() {
	
  this._setStatus('disconnecting');

  if (!this.connected())
    return;
  this._connected = false;

  clearInterval(this._interval);
  clearInterval(this._inQto);

  if (this._timeout)
    clearTimeout(this._timeout); // remove timer

  var slot = this._getFreeSlot();
  // Intentionally synchronous
  this._req[slot] = this._setupRequest(false);

  var reqstr = "<body type='terminate' xmlns='http://jabber.org/protocol/httpbind' sid='"+this._sid+"' rid='"+this._rid+"'";
  if (JSJAC_HAVEKEYS) {
    reqstr += " key='"+this._keys.getKey()+"'";
  }
  reqstr += ">";

  while (this._pQueue.length) {
    var curNode = this._pQueue[0];
    reqstr += curNode;
    this._pQueue = this._pQueue.slice(1,this._pQueue.length);
  }

  //reqstr += "<presence type='unavailable' xmlns='jabber:client'/>";
  reqstr += "</body>";

  // Wait for response (for a limited time, 5s)
  var abortTimerID = setTimeout("oCon._req["+slot+"].r.abort();", 5000);
  this.oDbg.log("Disconnecting: " + reqstr,4);
  this._req[slot].r.send(reqstr);	
  clearTimeout(abortTimerID);

  try {
    JSJaCCookie.read('JSJaC_State').erase();
  } catch (e) {}

  oCon.oDbg.log("Disconnected: "+oCon._req[slot].r.responseText,2);
  oCon._handleEvent('ondisconnect');
}

/**
 * @private
 */
function JSJaCHBCSetupRequest(async) {
  var req = new Object();
  var r = XmlHttp.create();
  try {
    r.open("POST",this._httpbase,async);
    r.setRequestHeader('Content-Type','text/xml; charset=utf-8');
  } catch(e) { this.oDbg.log(e,1); }
  req.r = r;
  this._rid++;
  req.rid = this._rid;
  return req;
}

/**
 * @private
 */
function JSJaCHBCGetRequestString(raw) {
  raw = raw || '';
  var reqstr = '';

  // check if we're repeating a request

  if (this._rid <= this._last_rid && typeof(this._last_requests[this._rid]) != 'undefined') // repeat!
    reqstr = this._last_requests[this._rid].xml;
  else { // grab from queue
    var xml = '';
    while (this._pQueue.length) {
      var curNode = this._pQueue[0];
      xml += curNode;
      this._pQueue = this._pQueue.slice(1,this._pQueue.length);
    }

    reqstr = "<body rid='"+this._rid+"' sid='"+this._sid+"' xmlns='http://jabber.org/protocol/httpbind' ";
    if (JSJAC_HAVEKEYS) {
      reqstr += "key='"+this._keys.getKey()+"' ";
      if (this._keys.lastKey()) {
        this._keys = new JSJaCKeys(hex_sha1,this.oDbg);
        reqstr += "newkey='"+this._keys.getKey()+"' ";
      }
    }
    if (this._reinit) {
      reqstr += "xmpp:restart='true' ";
      this._reinit = false;
    }

    if (xml != '' || raw != '') {
      reqstr += ">" + raw + xml + "</body>";
    } else {
      reqstr += "/>"; 
    }

    this._last_requests[this._rid] = new Object();
    this._last_requests[this._rid].xml = reqstr;
    this._last_rid = this._rid;

    for (var i in this._last_requests)
      if (this._last_requests.hasOwnProperty(i) &&
          i < this._rid-this._hold)
        delete(this._last_requests[i]); // truncate
  }
	 
  return reqstr;
}

/**
 * @private
 */
function JSJaCHBCPrepareResponse(req) {
  if (!this.connected())
    return null;

  if (!req)
    return null;

  var r = req.r; // the XmlHttpRequest


  try {
    if (r.status == 404 || r.status == 403) {
      // connection manager killed session
      oCon._abort();
      return null;
    }

    if (r.status != 200 || !r.responseXML) {
      this._errcnt++;
      var errmsg = "invalid response ("+r.status+"):\n" + r.getAllResponseHeaders()+"\n"+r.responseText;
      if (!r.responseXML)
        errmsg += "\nResponse failed to parse!";
      this.oDbg.log(errmsg,1);
      if (this._errcnt > JSJAC_ERR_COUNT) {
        // abort
        oCon._abort();
        return null;
      }
      this.oDbg.log("repeating ("+this._errcnt+")",1);
      
      this._setStatus('proto_error_fallback');
      
      // schedule next tick
      setTimeout("oCon._resume()",oCon.getPollInterval());
      
      return null;
    } 
  } catch (e) {
    this.oDbg.log("XMLHttpRequest error: status not available", 1);
    return null;
  }

  var body = r.responseXML.documentElement;
  if (!body || body.tagName != 'body' || body.namespaceURI != 'http://jabber.org/protocol/httpbind') {
    this.oDbg.log("invalid response:\n" + r.responseText,1);

    this._setStatus('internal_server_error');

    clearTimeout(this._timeout); // remove timer
    clearInterval(this._interval);
    clearInterval(this._inQto);
    this._handleEvent('onerror',JSJaCError('500','wait','internal-server-error'));
    this._connected = false;
    this.oDbg.log("Disconnected.",1);
    this._handleEvent('ondisconnect');
    return null;

  }

  if (typeof(req.rid) != 'undefined' && this._last_requests[req.rid]) {
    if (this._last_requests[req.rid].handled) {
      this.oDbg.log("already handled "+req.rid,2);
      return null;
    } else 
      this._last_requests[req.rid].handled = true;
  }


  // Check for errors from the server
  if (body.getAttribute("type") == "terminate") {
    this.oDbg.log("session terminated:\n" + r.responseText,1);

    clearTimeout(this._timeout); // remove timer
    clearInterval(this._interval);
    clearInterval(this._inQto);

    if (body.getAttribute("condition") == "remote-stream-error")
      if (body.getElementsByTagName("conflict").length > 0)
        this._setStatus("session-terminate-conflict");
    this._handleEvent('onerror',JSJaCError('503','cancel',body.getAttribute('condition')));
    this._connected = false;
    this.oDbg.log("Disconnected.",1);
    this._handleEvent('ondisconnect');
    return null;
  }

  // no error
  this._errcnt = 0;
  return r.responseXML.documentElement;
}

/**
 * @fileoverview Contains Debugger interface for Firebug and Safari
 * @class Implementation of the Debugger interface for {@link
 * http://www.getfirebug.com/ Firebug} and Safari
 * Creates a new debug logger to be passed to jsjac's connection
 * constructor. Of course you can use it for debugging in your code
 * too.
 * @constructor
 * @param {int} level The maximum level for debugging messages to be
 * displayed. Thus you can tweak the verbosity of the logger. A value
 * of 0 means very low traffic whilst a value of 4 makes logging very
 * verbose about what's going on.
 */
function JSJaCConsoleLogger(level) {
  /**
   * @private
   */
  this.level = level || 4;

  /**
   * Empty function for API compatibility
   */
  this.start = function() {};
  /**
   * Logs a message to firebug's/safari's console
   * @param {String} msg The message to be logged.
   * @param {int} level The message's verbosity level. Importance is
   * from 0 (very important) to 4 (not so important). A value of 1
   * denotes an error in the usual protocol flow.
   */
  this.log = function(msg, level) {
    level = level || 0;
    if (level > this.level)
      return;
    if (typeof(console) == 'undefined') 
      return;
    try {
      switch (level) {
      case 0:
        console.warn(msg);
        break;
      case 1:
        console.error(msg);
        break;
      case 2:
        console.info(msg);
        break;
      case 4:
        console.debug(msg);
        break;
      default:
        console.log(msg);
        break;
      }
    } catch(e) { try { console.log(msg) } catch(e) {} }
  };

  /**
   * Sets verbosity level.
   * @param {int} level The maximum level for debugging messages to be
   * displayed. Thus you can tweak the verbosity of the logger. A
   * value of 0 means very low traffic whilst a value of 4 makes
   * logging very verbose about what's going on.
   * @return This debug logger
   * @type ConsoleLogger
   */
  this.setLevel = function(level) { this.level = level; return this; }
  /** 
   * Gets verbosity level.
   * @return The level
   * @type int
   */
  this.getLevel = function() { return this.level; }
}
