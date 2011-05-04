package cc.kune.gspace.client;

import cc.kune.core.client.sitebar.search.SitebarSearchPresenter;
import cc.kune.gspace.client.tags.TagsSummaryPresenter;
import cc.kune.gspace.client.tool.ContentViewerSelector;
import cc.kune.gspace.client.tool.selector.ToolSelectorPresenter;
import cc.kune.gspace.client.ui.footer.license.EntityLicensePresenter;
import cc.kune.gspace.client.viewers.DocViewerPresenter;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.Ginjector;

public interface GSpaceGinjector extends Ginjector {

  ContentViewerSelector getContentViewerSelector();

  AsyncProvider<DocViewerPresenter> getDocsViewerPresenter();

  AsyncProvider<EntityLicensePresenter> getEntityLicensePresenter();

  AsyncProvider<FolderViewerPresenter> getFolderViewerPresenter();

  GSpaceParts getGSpaceParts();

  AsyncProvider<SitebarSearchPresenter> getSitebarSearchPresenter();

  AsyncProvider<TagsSummaryPresenter> getTagsSummaryPresenter();

  AsyncProvider<ToolSelectorPresenter> getToolSelectorPresenter();
}
