/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
 */
package cc.kune.core.server.rack.filters.rest;

import java.util.HashMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

public class XStreamRESTSerializer implements RESTSerializer {
  private final HashMap<String, XStream> serializers;

  public XStreamRESTSerializer() {
    serializers = new HashMap<String, XStream>(2);
    XStream stream = new XStream(new JettisonMappedXmlDriver());
    serializers.put(RESTMethod.FORMAT_JSON, stream);
    serializers.put(RESTMethod.FORMAT_XML, new XStream());

  }

  public String serialize(Object target, String format) {
    return serializers.get(format).toXML(target);
  }
}
