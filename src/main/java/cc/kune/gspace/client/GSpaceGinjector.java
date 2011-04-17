package cc.kune.gspace.client;

import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;

public interface GSpaceGinjector extends Ginjector {

    ContentViewerSelector getContentViewerSelector();

    AsyncProvider<EntityLicensePresenter> getEntityLicensePresenter();

    GSpaceParts getGSpaceParts();

    AsyncProvider<TagsSummaryPresenter> getTagsSummaryPresenter();

    AsyncProvider<ToolSelectorPresenter> getToolSelectorPresenter();
}
