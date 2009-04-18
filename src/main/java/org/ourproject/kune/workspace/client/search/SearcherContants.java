package org.ourproject.kune.workspace.client.search;

public interface SearcherContants {

    String START_PARAM = "start";
    String LIMIT_PARAM = "limit";
    String QUERY_PARAM = "query";
    String MIMETYPE_PARAM = "mimetype";
    String MIMETYPE2_PARAM = "mimetype2";

    String CONTENT_DATA_PROXY_URL = "/kune/json/ContentJSONService/search";
    String CONTENT_TEMPLATE_TEXT_PREFIX = "<div class=\"search-item\"><span class=\"kune-IconHyperlink\"><img src=\"";
    String CONTENT_TEMPLATE_TEXT_SUFFIX = "\" style=\"height: 16px; width: 16px;\" />{shortName}:&nbsp;{longName}</span></div>";

}
