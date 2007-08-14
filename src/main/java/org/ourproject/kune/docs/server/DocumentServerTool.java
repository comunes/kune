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
    public static final String NAME = "docs";
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
	    Folder folder = config.setRoot(new Folder("/", NAME));
	    group.setToolConfig(NAME, config);
	    configurationManager.persist(config);
	    ContentDescriptor descriptor = contentDescriptorManager.createContent(user, folder);
	    group.setDefaultContent(descriptor);
	}
	return group;
    }

}
