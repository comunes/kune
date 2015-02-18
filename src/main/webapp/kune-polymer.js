var kt = document.querySelector('#kunetemplate');

kt.toggle_social_net = function() {
  document.querySelector('#core_drawer_group_header').togglePanel();
}

window.onresize = function(event) {
  setContentMinHeight(); 
};

function setContentMinHeight() {
  kt.docContentMinHeight = (document.documentElement.clientHeight - 350);  
}

/* The initial space (0: for home, 2: for group space) useful during tests */
kt.spaceselected=2;

/* Screen sizes (inspired in bootstrap) */
kt.screenlg=1200;
kt.screenmd=992;
kt.screensm=768;
kt.screenxs=480;
kt.phone=kt.xsmall;
kt.table=kt.small;

kt.headers=140;
kt.headerm=160;
kt.headerl=192;

kt.toolbarFabMargins=70;
kt.toolbarFabMinMargins=60;

/* Avatar sizes */
kt.avatarsizel=100;
kt.avatarsizem=80;
kt.avatarsizes=50;

kt.homebackcolor='rgb(255, 204, 170)';
/* kt.group_back_image_url='http://lorempixel.com/1500/1500'; */
kt.showingSearch=false;

kt.main_forcenarrow=true;
kt.main_disableEdgeSwipe=true;
kt.main_disableSwipe=true;

setContentMinHeight();

/* user for testing */
kt.red="red";

function toggleVis(id) {
  el = document.querySelector(id);
  if (el != null) el.classList.toggle("sitebar_search_hide");
  if (el != null) el.classList.toggle("sitebar_search_on");
}

kt.closeMainDrawer = function(e,detail,sender) {
  kt.main_forcenarrow=true;
  document.getElementById('main_core_drawer_panel').closeDrawer();  
}


kt.toggleSearch = function(e,detail,sender) {
  if(e) {
    e.stopPropagation()
  }
  if(e && e.target===kt.sitebar_search_input){
    return
  }
  
  kt.showingSearch=!kt.showingSearch;
  
  toggleVis('#sitebar_language_btn');
  toggleVis('#sitebar_mygroups_btn');
  toggleVis('#space_selector_paper_tabs');
  toggleVis('#sitebar_user_space_icon_group');
  toggleVis('#sitebar_chat_icon_group');
  toggleVis('#sitebar_user_btn');
  toggleVis('#sitebar_search_input');
  toggleVis('#sitebar_left_extensionbar');
  toggleVis('#sitebar_right_extensionbar');
  toggleVis('#sitebar_flex');  
  toggleVis('#sitebar_more_icon');
  toggleVis('#sitebar_close_icon');
  toggleVis('#sitebar_flex_end');
  
  this.async(function(){
    document.querySelector('#sitebar_search_input').focus()
  })
};
