/* globals $ */
/* FIXME var kt = document.querySelector('#kunetemplate'); */
kt = document.querySelector('#kunetemplate');

kt.spin_active = true;

kt.toggle_social_net = function () {
  document.querySelector('#core_drawer_group_header').togglePanel();
};

window.onresize = function (event) {
  setContentMinHeight();
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

// kt.homebackcolors = ['#e8c6b2'];
kt.homebackcolors = ['#c9baa4', '#b6c9a4', '#e3cd77', 'a6c4d1', '#cba7ae'];

kt.homebackcolor = kt.homebackcolors[Math.floor((Math.random() * kt.homebackcolors.length))];

/* kt.group_back_image_url = 'http://lorempixel.com/1500/1500'; */
kt.showingSearch = false;

kt.main_forcenarrow = true;
kt.main_disableEdgeSwipe = true;
kt.main_disableSwipe = true;

setContentMinHeight();

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

kt.beat = function (id, play) {
  var animation = document.getElementById('opacity-scale');
  if (play) {
    animation.target = document.getElementById(id);
    animation.play();
  } else {
    animation.cancel();
  }
};

kt.blink = function (id, play) {
  var animation = document.getElementById('opacity-infinite');
  if (play) {
    animation.target = document.getElementById(id);
    animation.play();
  } else {
    animation.cancel();
  }
};

kt.showSpinner = function (e, detail, sender) {
  $('#k_home_spin_container').show();
  kt.spin_active = true;
};

kt.hideSpinner = function (e, detail, sender) {
  $('#k_home_spin_container').hide();
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
addEventListener('paper-header-transform', function (e) {
  var miga = $('.breadcrumb > div > div > div > button > i').add('#miga a');
  var toolbar = $('#core_scroll_header_panel');

  if (miga == null) {
    return;
  }

  var d = e.detail;
  // d.y: the amount that the header moves up
  // d.height: the height of the header when it is at its full size
  // d.condiensedHeight: the height of the header when it is condensed
  // scale header's title
  var m = d.height - d.condensedHeight;
  var scale = Math.max(0.75, (m - d.y) / (m / 0.25) + 0.75);
  // titleStyle.transform = titleStyle.webkitTransform =  'scale(' + scale + ') translateZ(0)';
  // adjust header's color
  // toolbar.style.color = (d.y >= d.height - d.condensedHeight) ? 'blue' : 'green';
  miga.css('color', (d.y >= d.height - d.condensedHeight) ? kt.c2 : kt.c6);
});

/* Default theme (useful for development without GWT)
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
 kt.c8 = '#a05a2c"; */

var d = new Date();
kt.this_year = d.getFullYear();

/* The initial space (0: for home, 2: for group space) useful during tests */
// kt.spaceselected = 2;

kt.main_drawer_width = function (xsmall, small) {
  var value = (xsmall ? 270 : small ? 300 : 400) + 'px';
  console.log('Draw size: ' + value);
  return value;
};

kt.logo_size = function (xsmall, small) {
  return small ? '-big' : xsmall ? '-med' : '-big';
};

// https://stackoverflow.com/questions/30664216/polymer-1-0-is-there-any-way-to-use-layout-as-an-attribute-instead-of-as-a-cs
kt.addClassIf = function (classToAdd, shouldAdd) {
  if (shouldAdd) {
    return classToAdd;
  }
};

kt.inbox_close_visibility = function () {
  return kt.main_forcenarrow ? 'none': 'inline-block';
};
