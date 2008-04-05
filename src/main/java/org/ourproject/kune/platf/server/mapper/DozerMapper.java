
package org.ourproject.kune.platf.server.mapper;

import java.util.ArrayList;
import java.util.List;

import net.sf.dozer.util.mapping.DozerBeanMapperSingletonWrapper;
import net.sf.dozer.util.mapping.MapperIF;

import org.ourproject.kune.platf.client.dto.SearchResultDTO;
import org.ourproject.kune.platf.server.manager.impl.SearchResult;

import com.google.inject.Singleton;

@Singleton
public class DozerMapper implements Mapper {
    private final MapperIF mapper;

    public DozerMapper() {
        mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    public <T> T map(final Object source, final Class<T> type) {
        return (T) mapper.map(source, type);
    }

    public <T> List<T> mapList(final List<?> list, final Class<T> type) {
        ArrayList<T> dest = new ArrayList<T>(list.size());
        for (Object o : list) {
            dest.add((T) mapper.map(o, type));
        }
        return dest;
    }

    public <K, T> SearchResultDTO<T> mapSearchResult(final SearchResult<K> result, final Class<T> type) {
        SearchResultDTO<T> resultDTO = new SearchResultDTO<T>();
        List<K> list = result.getList();
        ArrayList<T> dest = new ArrayList<T>(list.size());
        for (Object o : list) {
            dest.add((T) mapper.map(o, type));
        }
        resultDTO.setList(dest);
        resultDTO.setSize(result.getSize());
        return resultDTO;
    }

}
