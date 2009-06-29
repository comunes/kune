package org.ourproject.kune.workspace.client.site;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;
import com.google.gwt.libideas.resources.client.TextResource;

public interface SiteResources extends ImmutableResourceBundle {
    public static final SiteResources INSTANCE = GWT.create(SiteResources.class);

    @Resource("about.html")
    public TextResource aboutKune();
}