package org.ourproject.kune.app.server;

import org.ourproject.kune.platf.server.properties.PropertiesFileName;

import com.google.inject.AbstractModule;
import com.wideplay.warp.jpa.JpaUnit;

public class KuneAppModule extends AbstractModule {
    @Override
    protected void configure() {
	bindConstant().annotatedWith(JpaUnit.class).to("development");
	bindConstant().annotatedWith(PropertiesFileName.class).to("");
    }
}
