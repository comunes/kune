package org.ourproject.kune.platf.server;

import org.ourproject.kune.platf.server.mapper.DozerMapper;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.kune.platf.server.properties.KuneProperties;
import org.ourproject.kune.platf.server.properties.KunePropertiesDefault;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.wideplay.warp.persist.PersistenceService;

public class KunePlatformModule extends AbstractModule {
    @Override
    protected void configure() {
	install(PersistenceService.usingJpa().buildModule());
	bind(KuneProperties.class).to(KunePropertiesDefault.class);
	bind(Mapper.class).to(DozerMapper.class).in(Scopes.SINGLETON);
    }
}
