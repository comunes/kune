package org.ourproject.kune.platf.server.rest;

import java.util.List;

import org.ourproject.kune.platf.client.dto.I18nTranslationDTO;
import org.ourproject.kune.platf.server.domain.I18nTranslation;
import org.ourproject.kune.platf.server.manager.I18nTranslationManager;
import org.ourproject.kune.platf.server.mapper.Mapper;
import org.ourproject.rack.filters.rest.REST;

import com.google.inject.Inject;

public class I18nTranslationJSONService {
    private final I18nTranslationManager manager;
    private final Mapper mapper;

    @Inject
    public I18nTranslationJSONService(final I18nTranslationManager manager, final Mapper mapper) {
        this.manager = manager;
        this.mapper = mapper;
    }

    @REST(params = { "language" })
    public List<I18nTranslationDTO> search(final String language) {
        List<I18nTranslation> results = manager.getUntranslatedLexicon(language);
        return mapper.mapList(results, I18nTranslationDTO.class);
    }

}