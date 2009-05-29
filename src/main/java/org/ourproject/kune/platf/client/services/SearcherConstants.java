package org.ourproject.kune.platf.client.services;

public final class SearcherConstants {

    public final static String START_PARAM = "start";
    public final static String LIMIT_PARAM = "limit";
    public final static String QUERY_PARAM = "query";
    public final static String MIMETYPE_PARAM = "mimetype";
    public final static String MIMETYPE2_PARAM = "mimetype2";
    public final static String GROUP_PARAM = "group";

    public final static String GROUP_DATA_PROXY_URL = "/ws/json/GroupJSONService/search";
    public final static String USER_DATA_PROXY_URL = "/ws/json/UserJSONService/search";
    public final static String CONTENT_DATA_PROXY_URL = "/ws/json/ContentJSONService/search";
    public final static String I18N_JSON_SERVICE = "/ws/json/I18nTranslationJSONService/search";
    public final static String I18N_JSON_SERVICE_TRANSLATED = "/ws/json/I18nTranslationJSONService/searchtranslated";

    public final static String CONTENT_TEMPLATE_TEXT_PREFIX = "<div class=\"search-item\"><span class=\"kune-IconHyperlink\"><img src=\"";
    public final static String CONTENT_TEMPLATE_TEXT_SUFFIX = "\" style=\"height: 16px; width: 16px;\" />{shortName}:&nbsp;{longName}</span></div>";

    private SearcherConstants() {
    }
}
