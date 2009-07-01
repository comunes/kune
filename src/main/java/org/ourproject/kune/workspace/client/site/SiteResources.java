package org.ourproject.kune.workspace.client.site;

import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;

public interface SiteResources extends ImmutableResourceBundle {

    @Deprecated
    // not used now, only here for future use
    @Resource("about.html")
    public TextResource aboutKune();

}