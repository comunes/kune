package org.ourproject.kune.platf.server.mapper;

import java.util.List;

public interface Mapper {
    <T> T map(Object source, Class<T> type);

    <T> List<T> mapList(List<?> notCC, Class<T> type);
}
