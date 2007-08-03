package org.ourproject.kune.app.server;

import org.ourproject.kune.platf.server.properties.PropertiesFileName;

import com.google.inject.AbstractModule;

public class KuneAppModule extends AbstractModule {
    @Override
    protected void configure() {
	bindConstant().annotatedWith(PropertiesFileName.class).to("");
    }
}
