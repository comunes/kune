/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
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
 \*/
package cc.kune.common.shared.utils;

import java.util.ArrayList;

public class Url {
  private final String base;
  private final ArrayList<UrlParam> params;

  public Url(final String base) {
    this.base = base;
    params = new ArrayList<UrlParam>();
  }

  public Url(final String base, final UrlParam... iniParams) {
    this(base);
    for (final UrlParam param : iniParams) {
      addImpl(param);
    }
  }

  public void add(final UrlParam param) {
    addImpl(param);
  }

  private void addImpl(final UrlParam param) {
    params.add(param);
  }

  @Override
  public String toString() {
    String paramPart = "";
    boolean first = true;
    for (final UrlParam param : params) {
      if (first) {
        paramPart = "?" + param;
        first = false;
      } else {
        paramPart += "&" + param;
      }
    }
    return base + paramPart;
  }

}
