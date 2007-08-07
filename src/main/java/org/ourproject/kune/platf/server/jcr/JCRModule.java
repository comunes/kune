package org.ourproject.kune.platf.server.jcr;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class JCRModule extends AbstractModule {

    @Override
    protected void configure() {
	bindInterceptor(Matchers.any(), Matchers.annotatedWith(JcrTransactional.class), new PersistenceInterceptor());
    }

}
