// Called when all components have been loaded.
// http://www.html5rocks.com/en/tutorials/webcomponents/imports/
// http://w3c.github.io/webcomponents/spec/imports/#fetching-import
// -> "Every import that is not marked as async delays the load event in the Document."

var start = new Date().getTime();

var onImportLoaded = function () {
  console.log('All imports have been loaded, took ' + (new Date().getTime() - start) + 'ms');
  /* FIXME initRouting();
  installMaterializeCallbacks();
  checkBrowser();
  // remove the loading class so that app is now visible
   document.body.classList.remove('loading'); */
  var script = document.createElement('script');
  script.async = false;
  script.src = 'kune-polymer.js';
//  document.head.appendChild(script);
};

// this method will only be called if webcomponents are supported natively by the browser
function waitUntilElementsFullyParsed () {
  var link = document.querySelector('#bundle');
  var allImportsDoc = link.import.querySelector('#allImports');
  if (!allImportsDoc) {
    console.log('Needed import element not yet in document, waiting 5ms.');
    setTimeout(waitUntilElementsFullyParsed, 5);
  } else {
    console.log('Import document ready, continuing.');
    // 5. Go if the async Import loaded quickly. Otherwise wait for it.
    // crbug.com/504944 - readyState never goes to complete until Chrome 46.
    // crbug.com/505279 - Resource Timing API is not available until Chrome 46.
    if (allImportsDoc.import && allImportsDoc.import.readyState === 'complete') {
      console.log('All components have already been loaded. Continuing initialization.');
      onImportLoaded();
    } else {
      console.log('Not yet all components have been loaded. Waiting until done.');
      allImportsDoc.addEventListener('load', onImportLoaded);
    }
  }
}

// See https://github.com/Polymer/polymer/issues/1381
window.addEventListener('WebComponentsReady', function () {
  // imports are loaded and elements have been registered
  console.log('WebComponentsReady event caught, took: ' + (new Date().getTime() - start) + 'ms');
  onImportLoaded();

  /* removeLoaderElement (); */
});

// make sure to install the event listener early enough that removes the splash div
/* var loadEl = document.getElementById('splash');
var transitionEndEvt = transitionEndEventName();
loadEl.addEventListener(transitionEndEvt, function () {
  console.log('transitionend event caught, removing splash screen.');
   removeLoaderElement();
}); */

var fireReady = function () {
  // For native Imports, manually fire WCR so user code
  // can use the same code path for native and polyfill'd imports.
  if (!window.HTMLImports) {
    document.dispatchEvent(
      new CustomEvent('WebComponentsReady', {bubbles: true}));
  }
};

// 4. Conditionally load the webcomponents polyfill if needed by the browser.
// This feature detect will need to change over time as browsers implement
// different features.
var webComponentsSupported = ('registerElement' in document &&
                              'import' in document.createElement('link') &&
                              'content' in document.createElement('template'));

if (!webComponentsSupported) {
  console.log('Your browser does not support web components natively. Loading polyfill.');
  var script = document.createElement('script');
  script.async = true;
  script.src = '/bower_components/webcomponentsjs/webcomponents-lite.min.js';
  document.head.appendChild(script);
} else {
  console.log('Your browser supports web components natively, no polyfill needed.');
  waitUntilElementsFullyParsed();
  fireReady();
}
