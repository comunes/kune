package org.ourproject.kune.platf.server.init;

import cc.kune.domain.finders.ContainerFinder;
import cc.kune.domain.finders.ContentFinder;
import cc.kune.domain.finders.ExtMediaDescripFinder;
import cc.kune.domain.finders.GroupFinder;
import cc.kune.domain.finders.I18nCountryFinder;
import cc.kune.domain.finders.I18nLanguageFinder;
import cc.kune.domain.finders.I18nTranslationFinder;
import cc.kune.domain.finders.LicenseFinder;
import cc.kune.domain.finders.RateFinder;
import cc.kune.domain.finders.TagFinder;
import cc.kune.domain.finders.TagUserContentFinder;
import cc.kune.domain.finders.UserFinder;

import com.google.inject.Module;
import com.google.inject.persist.jpa.JpaPersistModule;

public class FinderRegistry {

    public static Module init(final JpaPersistModule jpaPersistModule) {
        jpaPersistModule.addFinder(ContainerFinder.class);
        jpaPersistModule.addFinder(ContentFinder.class);
        jpaPersistModule.addFinder(ExtMediaDescripFinder.class);
        jpaPersistModule.addFinder(GroupFinder.class);
        jpaPersistModule.addFinder(I18nCountryFinder.class);
        jpaPersistModule.addFinder(I18nLanguageFinder.class);
        jpaPersistModule.addFinder(I18nTranslationFinder.class);
        jpaPersistModule.addFinder(LicenseFinder.class);
        jpaPersistModule.addFinder(RateFinder.class);
        jpaPersistModule.addFinder(TagFinder.class);
        jpaPersistModule.addFinder(TagUserContentFinder.class);
        jpaPersistModule.addFinder(UserFinder.class);
        return jpaPersistModule;
    }

}
