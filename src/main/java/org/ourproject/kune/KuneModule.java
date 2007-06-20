package org.ourproject.kune;

import org.ourproject.kune.log.LoggerMethodInterceptor;
import org.ourproject.kune.server.dao.DocumentDao;
import org.ourproject.kune.server.dao.DocumentDaoJCR;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class KuneModule extends AbstractModule {

    @Override
    protected void configure() {
	bind(DocumentDao.class).to(DocumentDaoJCR.class);
	bindInterceptor(Matchers.any(), Matchers.any(), new LoggerMethodInterceptor());
    }

}
