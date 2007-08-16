package org.ourproject.kune.docs.server;

import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.ContentDescriptorManager;
import org.ourproject.kune.platf.server.manager.FolderManager;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.tool.ServerTool;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.Inject;

public class DocumentServerTool implements ServerTool {
    public static final String NAME = "docs";
    private final ContentDescriptorManager contentDescriptorManager;
    private final ToolConfigurationManager configurationManager;
    private final FolderManager folderManager;

    @Inject
    public DocumentServerTool(final ContentDescriptorManager contentDescriptorManager,
	    final FolderManager folderManager, final ToolConfigurationManager configurationManager) {
	this.contentDescriptorManager = contentDescriptorManager;
	this.folderManager = folderManager;
	this.configurationManager = configurationManager;
    }

    @Inject
    public void register(final ToolRegistry registry) {
	registry.register(this);
    }

    public String getName() {
	return NAME;
    }

    public Group initGroup(final User user, final Group group) {
	ToolConfiguration config = group.getToolConfiguration(NAME);
	if (config == null) {
	    config = new ToolConfiguration();
	    // i18n
	    Folder folder = folderManager.createRootFolder(group, NAME, "docs");
	    config.setRoot(folder);
	    group.setToolConfig(NAME, config);
	    configurationManager.persist(config);
	    Content descriptor = contentDescriptorManager.createContent("Kune docs!", user, folder);
	    group.setDefaultContent(descriptor);
	}
	return group;
    }

}
