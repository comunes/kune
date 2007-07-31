package to.tipit.gwtlib.firelog;

import com.google.gwt.core.client.JavaScriptObject;

class FireBug extends FireLogImpl {
	boolean loadStart;
	boolean initialized;
	JavaScriptObject queueStore, updTimer;
	
	public native void count(String title) /*-{
		var self = this;
		
		var cc = function() {
			if ( title == null ) $wnd.console.count();
			else $wnd.console.count(title);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	native void startLoad() /*-{
		var self = this;
		
		self.@to.tipit.gwtlib.firelog.FireBug::loadStart = true;
		if ( $wnd.console && $wnd.console.log && $wnd.console.trace ) {
			self.@to.tipit.gwtlib.firelog.FireBug::initialized = true;
			self.@to.tipit.gwtlib.firelog.FireBug::runQueue()();
		} else {
			var updCheck = function() {
				if ( $wnd.console && $wnd.console.log && $wnd.console.trace ) {
					self.@to.tipit.gwtlib.firelog.FireBug::initialized = true;
					clearInterval(self.@to.tipit.gwtlib.firelog.FireBug::updTimer);
					self.@to.tipit.gwtlib.firelog.FireBug::runQueue()();
				}
			};
			
			self.@to.tipit.gwtlib.firelog.FireBug::updTimer = setInterval(updCheck, 100);
			
			var scr = $doc.createElement("script");
			scr.onload = updCheck;
			scr.setAttribute("src", @to.tipit.gwtlib.GwtLibServerPaths::FILE_PREFIX + "firebug/firebug.js");
			$doc.body.appendChild(scr);
			updCheck();
		}
	}-*/;
	
	native void queue(JavaScriptObject fnc) /*-{
		var self = this;
		
		if ( !self.@to.tipit.gwtlib.firelog.FireBug::queueStore )
			self.@to.tipit.gwtlib.firelog.FireBug::queueStore = new Array();
		
		self.@to.tipit.gwtlib.firelog.FireBug::queueStore.push(fnc);
		
		if ( !self.@to.tipit.gwtlib.firelog.FireBug::loadStart )
			self.@to.tipit.gwtlib.firelog.FireBug::startLoad()();
	}-*/;
	
	native void runQueue() /*-{
		var self = this;
		
		var qs = self.@to.tipit.gwtlib.firelog.FireBug::queueStore;
		
		if ( !qs ) return;
		for ( var i = 0, len = qs.length ; i < len ; ++i ) qs[i]();
	}-*/;
	
	public native void debug(String message) /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.debug(message);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void error(String message) /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.error(message);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void group(String message) /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.group(message);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void groupEnd() /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.groupEnd();
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void info(String message) /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.info(message);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void log(String message) /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.log(message);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void profile(String title) /*-{
		var self = this;
		
		var cc = function() {
			if ( title == null ) $wnd.console.profile();
			else $wnd.console.profile(title);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void profileEnd() /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.profileEnd();
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void time(String name) /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.time(name);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void timeEnd(String name) /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.timeEnd(name);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void trace() /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.trace();
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
	
	public native void warn(String message) /*-{
		var self = this;
		
		var cc = function() {
			$wnd.console.warn(message);
		};
		
		if ( self.@to.tipit.gwtlib.firelog.FireBug::initialized ) cc();
		else self.@to.tipit.gwtlib.firelog.FireBug::queue(Lcom/google/gwt/core/client/JavaScriptObject;)(cc);
	}-*/;
}
