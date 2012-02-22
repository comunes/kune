// NOTE: add confLang() in each SVG like in: function f(){confLang();var g=a.getFrameIndex();
// Also see the idLangList array defined in each SVG. Is a list of ids/langs

function confLang() {   
    var hs = window.location.hash;
    if (typeof(hs)=='string') {
	lang = hs.slice(1)
	if (isNaN(lang)) {
	    for (var i = 0; i < idLangList.length; i ++) {
		// We show the layer id that match that lang
		document.getElementById(idLangList[i].id).style.display =
		    idLangList[i].layer === lang ? 'inline' : 'none';
	    }
	    window.location.hash = "";
	}
    }
}
