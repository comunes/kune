package org.ourproject.kune.platf.server.mapper;

import com.google.inject.Singleton;

import net.sf.dozer.util.mapping.DozerBeanMapperSingletonWrapper;
import net.sf.dozer.util.mapping.MapperIF;

@Singleton
public class DozerMapper implements Mapper {
    private final MapperIF mapper;

    public DozerMapper() {
	mapper = DozerBeanMapperSingletonWrapper.getInstance();
    }

    public <T> T map(final Object source, final Class<T> type) {
	return (T) mapper.map(source, type);
    }

}
