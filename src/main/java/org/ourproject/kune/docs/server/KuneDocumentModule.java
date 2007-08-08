package org.ourproject.kune.docs.server;

import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.server.KuneServiceDefault;

import com.google.inject.AbstractModule;

public class KuneDocumentModule extends AbstractModule {

    @Override
    protected void configure() {
	bind(KuneService.class).to(KuneServiceDefault.class);
    }
}
