package org.ourproject.kune.workspace.client.ui.newtmp.sitebar.sitenewgroup;

import org.ourproject.kune.workspace.client.ui.newtmp.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.ui.Hyperlink;

public class SiteNewGroupLinkPanel implements SiteNewGroupLinkView {

    private final Hyperlink newGroupHyperlink;

    public SiteNewGroupLinkPanel(final SiteNewGroupLinkPresenter presenter, final WorkspaceSkeleton ws) {
	newGroupHyperlink = new Hyperlink();
    }
}
