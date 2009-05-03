package org.ourproject.kune.platf.client.services;

public interface SearcherConstants {

    String START_PARAM = "start";
    String LIMIT_PARAM = "limit";
    String QUERY_PARAM = "query";
    String MIMETYPE_PARAM = "mimetype";
    String MIMETYPE2_PARAM = "mimetype2";
    String GROUP_PARAM = "group";

    String GROUP_DATA_PROXY_URL = "/ws/json/GroupJSONService/search";
    String USER_DATA_PROXY_URL = "/ws/json/UserJSONService/search";
    String CONTENT_DATA_PROXY_URL = "/ws/json/ContentJSONService/search";
    String I18N_JSON_SERVICE = "/ws/json/I18nTranslationJSONService/search";
    String I18N_JSON_SERVICE_TRANSLATED = "/ws/json/I18nTranslationJSONService/searchtranslated";

    String CONTENT_TEMPLATE_TEXT_PREFIX = "<div class=\"search-item\"><span class=\"kune-IconHyperlink\"><img src=\"";
    String CONTENT_TEMPLATE_TEXT_SUFFIX = "\" style=\"height: 16px; width: 16px;\" />{shortName}:&nbsp;{longName}</span></div>";

}
