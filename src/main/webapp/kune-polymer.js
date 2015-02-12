var kt = document.querySelector('#kunetemplate');

kt.toggle_social_net = function() {
  document.querySelector('#core_drawer_group_header').togglePanel();
}

/* The initial space (0: for home, 1: for group space) useful during tests */
kt.spaceselected=0;

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

/* Avatar sizes */
kt.avatarsizel=80;
kt.avatarsizem=60;
kt.avatarsizes=50;

kt.homebackcolor='rgb(255, 204, 170)';
kt.group_back_image_url='url(http://lorempixel.com/1000/1000)';
kt.showingSearch=false;

function toggleVis(id) {
  el = document.querySelector(id);
  if (el != null) el.classList.toggle("sitebar_search_hide");
  if (el != null) el.classList.toggle("sitebar_search_on");
}

kt.toggleSearch = function(e,detail,sender) {
  if(e) {
    e.stopPropagation()
  }
  if(e.target===kt.sitebar_search_input){
    return
  }
  
  kt.showingSearch=!kt.showingSearch;
  
  toggleVis('#sitebar_language_btn');
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

kt.onKeyPressSearch = function(e,detail,sender){
  if(e.keyCode==13) {
    if(sender.value) {
      // recordSearch(sender.value);
      var q="site:kune.cc+"+sender.value;
      window.open("https://www.google.com/search?q="+q)
    }
  }
}
