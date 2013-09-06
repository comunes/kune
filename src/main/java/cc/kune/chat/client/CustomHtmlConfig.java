package cc.kune.chat.client;

import com.calclab.emite.browser.client.PageAssist;

public class CustomHtmlConfig {

    public static CustomHtmlConfig getFromMeta() {
	final CustomHtmlConfig config = new CustomHtmlConfig();
	config.hasLogin = PageAssist.isMetaTrue("hablar.login");
	config.inline = PageAssist.getMeta("hablar.inline");
	config.width = PageAssist.getMeta("hablar.width");
	config.height = PageAssist.getMeta("hablar.height");
	if (config.width == null) {
	    config.width = "400px";
	}
	if (config.height == null) {
	    config.height = "400px";
	}
	return config;
    }

    /**
     * Width
     */
    public String width = "100%";

    /**
     * Height
     */
    public String height = "100%";

    /**
     * Install Logger module
     */
    public boolean hasLogger = false;

    /**
     * Show or not login panel
     */
    public boolean hasLogin = false;

    /**
     * If not null, show 'hablar' inside the div with the id given
     */
    public String inline = null;
}
