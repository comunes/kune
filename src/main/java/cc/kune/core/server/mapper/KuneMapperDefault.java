/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.mapper;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;

import cc.kune.core.server.manager.SearchResult;
import cc.kune.core.shared.dto.SearchResultDTO;

import com.google.inject.Singleton;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneMapperDefault.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Singleton
public class KuneMapperDefault implements KuneMapper {

  /** The mapper. */
  private final Mapper mapper;

  /**
   * Instantiates a new kune mapper default.
   */
  public KuneMapperDefault() {
    mapper = DozerBeanMapperSingletonWrapper.getInstance();
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.mapper.KuneMapper#map(java.lang.Object,
   * java.lang.Class)
   */
  @Override
  public <T> T map(final Object source, final Class<T> type) {
    return mapper.map(source, type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see cc.kune.core.server.mapper.KuneMapper#mapList(java.util.List,
   * java.lang.Class)
   */
  @Override
  public <T> List<T> mapList(final List<?> list, final Class<T> type) {
    final ArrayList<T> dest = new ArrayList<T>(list.size());
    for (final Object o : list) {
      dest.add(mapper.map(o, type));
    }
    return dest;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.server.mapper.KuneMapper#mapSearchResult(cc.kune.core.server
   * .manager.SearchResult, java.lang.Class)
   */
  @Override
  public <K, T> SearchResultDTO<T> mapSearchResult(final SearchResult<K> result, final Class<T> type) {
    final SearchResultDTO<T> resultDTO = new SearchResultDTO<T>();
    final List<K> list = result.getList();
    final ArrayList<T> dest = new ArrayList<T>(list.size());
    for (final Object o : list) {
      dest.add(mapper.map(o, type));
    }
    resultDTO.setList(dest);
    resultDTO.setSize(result.getSize());
    return resultDTO;
  }

}
