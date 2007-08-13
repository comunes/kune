package org.ourproject.kune.platf.server.manager;

import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.User;

public interface ContentDescriptorManager {
    public ContentDescriptor get(Long id);

    public ContentDescriptor createContent(User user);
}
