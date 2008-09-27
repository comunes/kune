package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.app.ApplicationComponentGroup;
import org.ourproject.kune.platf.client.app.ToolGroup;

import com.calclab.suco.client.ioc.module.AbstractModule;

public class KuneCoreModule extends AbstractModule {

    @Override
    public void onLoad() {
	registerDecorator(ApplicationComponentGroup.class, new ApplicationComponentGroup(container));
	registerDecorator(ToolGroup.class, new ToolGroup(container));
    }
}
