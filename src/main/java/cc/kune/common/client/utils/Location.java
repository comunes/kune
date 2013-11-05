/*
 * Copyright 2006, 2012 Robert Hanson <iamroberthanson AT gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 \*/
package cc.kune.common.client.utils;

import java.util.HashMap;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class Location.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class Location {
  
  /** The hash. */
  private String hash;
  
  /** The host. */
  private String host;
  
  /** The host name. */
  private String hostName;
  
  /** The href. */
  private String href;
  
  /** The path. */
  private String path;
  
  /** The port. */
  private String port;
  
  /** The protocol. */
  private String protocol;
  
  /** The query string. */
  private String queryString;
  
  /** The param map. */
  private HashMap<String, String> paramMap;

  /**
   * sample: #site.docs.3.1
   *
   * @return the hash
   */
  public String getHash() {
    return hash;
  }

  /**
   * sample: locahost:8080.
   *
   * @return the host
   */
  public String getHost() {
    return host;
  }

  /**
   * sample: locahost.
   *
   * @return the host name
   */
  public String getHostName() {
    return hostName;
  }

  /**
   * sample: http://localhost:8080/ws/#site.docs.3.1?locale=en&log_level=INFO
   *
   * @return the href
   */
  public String getHref() {
    return href;
  }

  /**
   * Gets the parameter.
   *
   * @param name the name
   * @return the parameter
   */
  public String getParameter(final String name) {
    return paramMap.get(name);
  }

  /**
   * Gets the parameter map.
   *
   * @return the parameter map
   */
  public Map<String, String> getParameterMap() {
    return paramMap;
  }

  /**
   * sample: /ws/.
   *
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * Gets the port.
   *
   * @return the port
   */
  public String getPort() {
    return port;
  }

  /**
   * Gets the protocol.
   *
   * @return the protocol
   */
  public String getProtocol() {
    return protocol;
  }

  /**
   * sample: ?locale=en&log_level=INFO.
   *
   * @return the query string
   */
  public String getQueryString() {
    return queryString;
  }

  /**
   * Sets the hash.
   *
   * @param hash the new hash
   */
  protected void setHash(final String hash) {
    this.hash = hash;
  }

  /**
   * Sets the host.
   *
   * @param host the new host
   */
  protected void setHost(final String host) {
    this.host = host;
  }

  /**
   * Sets the host name.
   *
   * @param hostName the new host name
   */
  protected void setHostName(final String hostName) {
    this.hostName = hostName;
  }

  /**
   * Sets the href.
   *
   * @param href the new href
   */
  protected void setHref(final String href) {
    this.href = href;
  }

  /**
   * Sets the path.
   *
   * @param path the new path
   */
  protected void setPath(final String path) {
    this.path = path;
  }

  /**
   * Sets the port.
   *
   * @param port the new port
   */
  protected void setPort(final String port) {
    this.port = port;
  }

  /**
   * Sets the protocol.
   *
   * @param protocol the new protocol
   */
  protected void setProtocol(final String protocol) {
    this.protocol = protocol;
  }

  /**
   * Sets the query string.
   *
   * @param queryString the new query string
   */
  protected void setQueryString(final String queryString) {
    this.queryString = queryString;
    paramMap = new HashMap<String, String>();

    if (queryString != null && queryString.length() > 1) {
      String qs = queryString.substring(1);
      String[] kvPairs = qs.split("&");
      for (String kvPair : kvPairs) {
        String[] kv = kvPair.split("=");
        if (kv.length > 1) {
          paramMap.put(kv[0], unescape(kv[1]));
        } else {
          paramMap.put(kv[0], "");
        }
      }
    }
  }

  /**
   * Unescape.
   *
   * @param val the val
   * @return the string
   */
  private native String unescape(String val) /*-{
                                                return unescape(val);
                                             }-*/;

}
