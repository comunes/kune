package org.ourproject.kune.docs.server;

import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.ToolConfiguration;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.manager.ContentDescriptorManager;
import org.ourproject.kune.platf.server.manager.ToolConfigurationManager;
import org.ourproject.kune.platf.server.tool.ServerTool;

import com.google.inject.Inject;

public class DocumentServerTool implements ServerTool {
    private static final String NAME = "docs";
    private final ContentDescriptorManager contentDescriptorManager;
    private final ToolConfigurationManager configurationManager;

    @Inject
    public DocumentServerTool(final ContentDescriptorManager contentDescriptorManager,
	    final ToolConfigurationManager configurationManager) {
	this.contentDescriptorManager = contentDescriptorManager;
	this.configurationManager = configurationManager;
    }

    public String getName() {
	return NAME;
    }

    public Group initGroup(final User user, final Group group) {
	ToolConfiguration config = group.getToolConfiguration(NAME);
	if (config == null) {
	    config = new ToolConfiguration();
	    config.setRoot(new Folder("/"));
	    group.setToolConfig(NAME, config);
	    ContentDescriptor descriptor = contentDescriptorManager.createContent(user);
	    config.setWelcome(descriptor);
	    configurationManager.persist(config);
	}
	return group;
    }

}
