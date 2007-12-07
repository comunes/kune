package org.ourproject.kune.platf.server.manager;

import java.util.List;

import org.ourproject.kune.platf.server.domain.I18nCountry;

public interface I18nCountryManager extends Manager<I18nCountry, Long> {

    List<I18nCountry> getAll();

    I18nCountry findByCode(String country);

}
