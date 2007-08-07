package org.ourproject.kune.platf.server.mapper;

public interface Mapper {
    <T> T map(Object source, Class<T> type);
}
