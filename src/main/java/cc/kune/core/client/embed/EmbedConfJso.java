package cc.kune.core.client.embed;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * The Class EmbedConfJso is used to configure the embed system via a JSNI call
 * from outside.
 */
public class EmbedConfJso extends JavaScriptObject {

  protected EmbedConfJso() {
  }

  /**
   * Gets if this wave should be read only.
   * 
   * @return the read only
   */
  public final native boolean getReadOnly() /*-{
		return this.readOnly || false;
  }-*/;

  /**
   * Gets the kune server hostname.
   * 
   * @return the server
   */
  public final native String getServerUrl() /*-{
		return this.serverUrl;
  }-*/;

  public final native boolean getShowSignIn() /*-{
		return this.showSignIn || true;
  }-*/;

  public final native boolean getShowSignOut() /*-{
		return this.showSignOut || true;
  }-*/;

  public final native String getSignInText() /*-{
		return this.signInText || 'Participate';
  }-*/;

  /**
   * Gets the sitebar position offset.
   * 
   * @return the sitebar position
   */
  public final native int getSitebarPosition() /*-{
		return this.sitebarPosition || 30;
  }-*/;

}
