/*
 * jQuery jScrollTouch plugin 1.1
 *
 * Copyright (c) 2010 Damien Rottemberg damien@dealinium.com
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 *
 */


(function($){
  $.fn.jScrollTouch = function () {
	if((navigator.userAgent.match(/iPhone/i)) || (navigator.userAgent.match(/iPod/i)) || (navigator.userAgent.match(/iPad/i))) {
 		var isTouchScreen = 1;
 	}else{
 		var isTouchScreen = 0;
 	}
 	$(this).css({'overflow': 'auto','position':'relative'});
	return this.each(function() {
		var cont = $(this);
		
		var height = 0;
		var cpos = cont.scrollTop()
		cont.scrollTop(100000);
		height = cont.scrollTop();
		cont.scrollTop(cpos);
		var fullheight = height + cont.outerHeight();
		var scrollbarV_length = cont.innerHeight()*(cont.innerHeight()/fullheight)+2;
		
		var width = 0;
		var lpos = cont.scrollLeft();
		cont.scrollLeft(100000);
		width = cont.scrollLeft();
		cont.scrollLeft(lpos);
		var fullwidth = width + cont.outerWidth();
		var scrollbarH_length = cont.innerWidth()*(cont.innerWidth()/fullwidth)+2;
		
		
		if(isTouchScreen){
			var scrollbarV = $('<div></div>');
			scrollbarV.css({'display':'none','position':'absolute','width':'5px','height':scrollbarV_length+'px','left':cont.innerWidth()-7+'px','top':0,'background':'black','border':'1px white solid','-webkit-border-radius':'5px','opacity':'0.9'});
			
			var scrollbarH = $('<div></div>');
			scrollbarH.css({'display':'none','position':'absolute','height':'5px','width':scrollbarH_length+'px','top':cont.innerHeight()-7+'px','left':0,'background':'black','border':'1px white solid','-webkit-border-radius':'5px','opacity':'0.9'});
			
			
			if(height) cont.append(scrollbarV);
			if(width) cont.append(scrollbarH);
		}
		
	
		cont.bind('mousedown touchstart',function(e){
			cpos = cont.scrollTop();
					
			if(isTouchScreen){
				e = e.originalEvent.touches[0];
			}
			
			if(isTouchScreen){
				scrollbarV.show();
				scrollbarH.show();
			}
			
			var sY = e.pageY;
			var sX = e.pageX;
			
			cont.bind('mousemove touchmove ',function(ev){
				if(isTouchScreen){
					ev.preventDefault();
					ev = ev.originalEvent.touches[0];
					
				}	
				
				var top = cpos-(ev.pageY-sY);
				var left =  lpos-(ev.pageX-sX);
				
				cont.scrollTop(top);
				cpos = cont.scrollTop();
				sY = ev.pageY;
				
				cont.scrollLeft(left);
				lpos = cont.scrollLeft();
				sX = ev.pageX;
				if(isTouchScreen){
					scrollbarV.css({'left':Math.min(cont.innerWidth()-7+lpos,fullwidth)+'px','top':Math.min(cpos+cpos*cont.innerHeight()/fullheight,fullheight-scrollbarV_length)+'px'});
					scrollbarH.css({'top':Math.min(cont.innerHeight()-7+cpos,fullheight)+'px','left':Math.min(lpos+lpos*cont.innerWidth()/fullwidth,fullwidth-scrollbarH_length)+'px'});
					
				}	
			});
			cont.bind('mouseup touchend',function(ev){	
				cont.unbind('mousemove touchmove mouseup touchend');
				if(isTouchScreen){
					scrollbarV.fadeOut();
					scrollbarH.fadeOut();
				}
				
			});
		});
	});
}
})(jQuery);   