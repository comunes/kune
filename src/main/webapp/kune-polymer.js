/* globals $ getMetaContent locale Flickity lorem location */
// Following https://github.com/ebidel/polymer-gmail
var start = new Date().getTime();
var devMode = false;
var devSpace = 0;

var kt = document.querySelector('#kunetemplate');

kt.spin_active = true;
kt.spin_size = '230px';

kt.swcachedisabled = devMode;

kt.toggle_social_net = function () {
  document.querySelector('#core_drawer_group_header').togglePanel();
};

kt.refreshApp = function () {
  location.reload();
};

function setContentMinHeight () {
  kt.docContentMinHeight = (document.documentElement.clientHeight - 350);
}

/* Screen sizes (inspired in bootstrap) */
kt.screenlg = 1200;
kt.screenmd = 992;
kt.screensm = 768;
kt.screenxs = 480;
kt.phone = kt.xsmall;
kt.table = kt.small;

kt.headers = 140;
kt.headerm = 160;
kt.headerl = 192;

kt.toolbarFabMargins = 70;
kt.toolbarFabMinMargins = 60;

kt.group_header_drawer_width = '80%';

/* Avatar sizes */
kt.avatarsizel = 100;
kt.avatarsizem = 80;
kt.avatarsizes = 50;

kt.homebackimg = 'others/home-back-trees.png';

kt.showingSearch = false;

kt.main_forcenarrow = true;
kt.main_disableEdgeSwipe = false;
kt.main_disableSwipe = false;

/* just used for testing */
kt.red = 'red';

function toggleVis (id) {
  var el = document.querySelector(id);
  if (el != null) el.classList.toggle('sitebar_search_hide');
  if (el != null) el.classList.toggle('sitebar_search_on');
}

kt.closeMainDrawer = function (e, detail, sender) {
  kt.main_forcenarrow = true;
  document.getElementById('main_core_drawer_panel').closeDrawer();
};

kt.showSpinner = function (e, detail, sender) {
  $('#k_home_spinner_container').show();
  kt.spin_active = true;
};

kt.hideSpinner = function (e, detail, sender) {
  $('#k_home_spinner_container').hide();
  kt.spin_active = false;
};

kt.toggleSearch = function (e, detail, sender) {
  if (e) {
    e.stopPropagation();
  }
  if (e && e.target === kt.sitebar_search_input) {
    return;
  }

  kt.showingSearch = !kt.showingSearch;

  toggleVis('#sitebar_language_btn');
  toggleVis('#sitebar_mygroups_btn');
  toggleVis('#space_selector_paper_tabs');
  toggleVis('#sitebar_user_space_icon_group');
  toggleVis('#sitebar_chat_icon_group');
  // Problems with chat status toggleVis('#sitebar_user_btn');
  toggleVis('#sitebar_search_input');
  toggleVis('#sitebar_left_extensionbar');
  toggleVis('#sitebar_right_extensionbar');
  toggleVis('#sitebar_flex');
  toggleVis('#sitebar_more_icon');
  toggleVis('#sitebar_close_icon');
  toggleVis('#sitebar_flex_end');

  this.async(function () {
    document.querySelector('#sitebar_search_input').focus();
  });
};

/* Color of group header */
window.addEventListener('paper-header-transform', function (e) {
  var miga = $('.breadcrumb > div > div > div > button > i').add('#miga a');
  // var toolbar = $('#core_scroll_header_panel');

  if (miga == null) {
    return;
  }
  // console.log('page-header-transform event');

  var d = e.detail;
  // d.y: the amount that the header moves up
  // d.height: the height of the header when it is at its full size
  // d.condiensedHeight: the height of the header when it is condensed
  // scale header's title
  // var m = d.height - d.condensedHeight;
  // var scale = Math.max(0.75, (m - d.y) / (m / 0.25) + 0.75);
  // titleStyle.transform = titleStyle.webkitTransform =  'scale(' + scale + ') translateZ(0)';
  // adjust header's color
  // toolbar.style.color = (d.y >= d.height - d.condensedHeight) ? 'blue' : 'green';
  miga.css('color', (d.y >= d.height - d.condensedHeight) ? kt.c2 : kt.c6);
});

/* Default theme (useful for development without GWT) */
if (devMode) {
  kt.bg1 = '#deaa87';
  kt.bg2 = '#d99e76';
  kt.bg3 = '#ce7f4b';
  kt.bg4 = '#d99e76';
  kt.bg5 = '#f6e7dd';
  kt.bg6 = '#d99e76';
  kt.bg7 = '#FFF';
  kt.bg8 = '#f8eee7';

  kt.c1 = '#FFF';
  kt.c2 = '#FFF';
  kt.c3 = '#FFF';
  kt.c4 = '#FFF';
  kt.c5 = '#552200';
  kt.c6 = '#FFF';
  kt.c7 = '#a05a2c';
  kt.c8 = '#a05a2c';
}

var d = new Date();
kt.this_year = d.getFullYear();

kt.group_scroll_header_height = function (xsmall, small) {
  return xsmall ? kt.headers : small ? kt.headerm : kt.headerl;
};

kt.main_drawer_width = function (xsmall, small) {
  var value = (xsmall ? 270 : small ? 300 : 400) + 'px';
  // console.log('Draw size: ' + value);
  return value;
};

kt.logo_size = function (xsmall, small) {
  return small ? '-big' : xsmall ? '-med' : '-big';
};

kt.docContentMinHeightCalc = function (xsmall) {
  return kt.docContentMinHeight + (xsmall ? 100 : 0) + 'px';
};

// https://stackoverflow.com/questions/30664216/polymer-1-0-is-there-any-way-to-use-layout-as-an-attribute-instead-of-as-a-cs
kt.addClassIf = function (classToAdd, shouldAdd) {
  if (shouldAdd) {
    return classToAdd;
  }
};

kt.verticalIfNot = function (condition) {
  return !condition ? 'vertical' : 'horizontal';
};

kt.verticalIf = function (condition) {
  return condition ? 'vertical' : 'horizontal';
};

kt.inbox_close_visibility = function () {
  return kt.main_forcenarrow ? 'none' : 'inline-block';
};

kt.homebackcolors = ['#c9baa4', '#b6c9a4', '#e3cd77', '#a6c4d1', '#cba7ae'];

var loadTestData = function () {
  /* The initial space (0: for home, 2: for group space) useful during tests */
  kt.spaceselected = devSpace;

  kt.group_back_image_url = 'http://lorempixel.com/1500/1500';
  kt.user_icon_back_image_url = 'http://lorempixel.com/50/50';
  $('#header_group_logo').attr('src', 'http://lorempixel.com/100/100');

  // https://github.com/shyiko/lorem
  var script = document.createElement('script');
  script.src = 'https://rawgit.com/shyiko/lorem/master/src/library/lorem.js';
  script.onload = function () {
    $('#doc_content').append(lorem.ipsum('p40_5x40'));
    $('#header_group_name').append(lorem.ipsum('s_4x10'));
    $('#header_short_group_name').append(lorem.ipsum('s_4x10'));
  };
  document.head.appendChild(script);
};

var homeResize = function () {
  var backs = ['others/home-back-trees.png',
               'others/home-back-buck.png',
               'others/home-back-decen.png',
               'others/home-back-four-handed-seat.png',
               'others/home-back-op-en.png',
               'others/home-back-planets.png',
               'others/home-back-trees.png' // again
              ];

  var caroElem = document.querySelector('#k_home_center');

  var flkty = new Flickity(caroElem, {
    pageDots: true,
    // setGallerySize: false,
    autoPlay: 10000,
    imagesLoaded: true,
    wrapAround: true
  });

  function resizeSlider () {
    var artHeight = $('#k_home_center').outerHeight() - 40;
    // var artHeight = $('#k_home_scroller').outerHeight() - 40;
    console.log('Resizing carousel with home height: ' + artHeight);
    clearInterval(interval);
    // return;
    $('.flickity-viewport').css({
      'height': artHeight
    });
    $('.flickity-viewport').animate({ // quick and dirty
      'opacity': 1
    });
    // flkty.reposition();
    flkty.resize();
  }

  flkty.on('cellSelect', function () {
    kt.homebackimg = backs[flkty.selectedIndex];
    resizeSlider();
  });

  window.onresize = function () {
    resizeSlider();
    setContentMinHeight();
  };

  setContentMinHeight();

  var interval = setInterval(resizeSlider, 3000);

  // This is done by gwt, but useful for development without gwt
  if (devMode) {
    kt.hideSpinner();
  }
};

var onImportLoadedFired = false;

var finishLazyLoadingImports = function () {
  // Use native Shadow DOM if it's available in the browser.
  window.Polymer = window.Polymer || {dom: 'shadow'};
  console.log('Lazy loading imports');

  var onImportLoaded = function () {
    console.log('All imports have been loaded, took ' + (new Date().getTime() - start) + 'ms');
    if (onImportLoadedFired) {
      console.log('On import already fired');
      return;
    }
    onImportLoadedFired = true;
    var loadContainer = document.getElementById('splash');
    loadContainer.addEventListener('transitionend', function (e) {
      loadContainer.parentNode.removeChild(loadContainer); // IE 10 doesn't support el.remove()
    });

    if (!devMode) {
      var script = document.createElement('script');
      script.type = 'text/javascript';
      script.src = 'ws/ws.nocache.js';
      // Comment this line for develop this initial page without gwt code
      document.head.appendChild(script);
      console.log('Webcomponent event loads GWT code');
    } else {
      loadTestData();
      onGwtReady();
    }
  };

  // crbug.com/504944 - readyState never goes to complete until Chrome 46.
  // crbug.com/505279 - Resource Timing API is not available until Chrome 46.
  var link = document.querySelector('#kunebundle');
  if (link.import && link.import.readyState === 'complete') {
    console.log('Import load event not necessary');
    onImportLoaded();
  } else {
    console.log('Adding import load event');
    link.addEventListener('load', onImportLoaded);
  }
};

// Conditionally load webcomponents polyfill (if needed).
var webComponentsSupported = (
  'registerElement' in document &&
    'import' in document.createElement('link') &&
    'content' in document.createElement('template'));

if (!webComponentsSupported) {
  console.log('Your browser does not support web components natively. Loading polyfill.');
  var script = document.createElement('script');
  script.async = true;
  script.src = '/bower_components/webcomponentsjs/webcomponents-lite.min.js';
  script.onload = finishLazyLoadingImports;
  document.head.appendChild(script);
} else {
  console.log('Your browser supports web components natively, no polyfill needed.');
  finishLazyLoadingImports();
}

function onGwtReady () {
  console.log('On GWT ready');
  kt.homebackcolor = kt.homebackcolors[Math.floor((Math.random() * kt.homebackcolors.length))];
  document.body.classList.remove('sploading');
  kt.spin_size = '100px';
}

var domChangeListenFired = false;

kt.addEventListener('dom-change', function (e) {
  console.log('dom-change event');
  if (domChangeListenFired) {
    console.log('dom-change already fired');
    return;
  }
  domChangeListenFired = true;

  // Show proper language messages
  var meta = getMetaContent('kune.home.ids');

  var HOME_IDS_DEF_SUFFIX = '_def';
  var HOME_IDS_PREFIX = 'k_home_';

  // console.log('Locale: ' + locale);
  var currentLocale = locale;

  if (meta != null) {
    // console.log('Meta: ' + meta);
    var ids = meta.split(/[\s,]+/);
    for (var i = 0; i < ids.length; i++) {
      var id = ids[i];
      var lid = HOME_IDS_PREFIX + id + '_' + currentLocale;
      var ldefid = HOME_IDS_PREFIX + id + HOME_IDS_DEF_SUFFIX;
      // console.log('Id: ' + ldefid);
      var someElement = document.getElementById(lid);
      var defElement = document.getElementById(ldefid);
      if (someElement != null) {
        someElement.style.display = 'inherit';
      } else if (defElement != null) {
        // console.log('Setting def element ' + defElement);
        defElement.style.display = 'inherit';
      }
    }
  }
  homeResize();
});

var sw = document.querySelector('platinum-sw-register');
sw.addEventListener('service-worker-installed', function () {
  var toast = document.querySelector('#swtoast');
  toast.show();
});

sw.addEventListener('service-worker-updated', function () {
  var toast = document.querySelector('#swtoast');
  toast.text = 'A new version is available. Tap to refresh';
  toast.show();
});
