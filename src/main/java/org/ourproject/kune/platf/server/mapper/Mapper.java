
package org.ourproject.kune.platf.server.mapper;

import java.util.List;

import org.ourproject.kune.platf.client.dto.SearchResultDTO;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

public interface Mapper {
    <T> T map(Object source, Class<T> type);

    <T> List<T> mapList(List<?> list, Class<T> type);

    <K, T> SearchResultDTO<T> mapSearchResult(SearchResult<K> result, Class<T> type);
}
