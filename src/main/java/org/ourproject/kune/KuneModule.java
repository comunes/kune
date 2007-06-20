package org.ourproject.kune;

import org.ourproject.kune.server.dao.DocumentDao;
import org.ourproject.kune.server.dao.DocumentDaoJCR;

import com.google.inject.AbstractModule;

public class KuneModule extends AbstractModule {

    @Override
    protected void configure() {
	bind(DocumentDao.class).to(DocumentDaoJCR.class);
    }

}
