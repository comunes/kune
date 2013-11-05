/*******************************************************************************
 * Copyright (C) 2007, 2013 The kune development team (see CREDITS for details)
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
 *******************************************************************************/

package cc.kune.core.client.state;

/**
 * The Class LinkInterceptorHelper.
 */
public class LinkInterceptorHelper {

  /**
   * Gets the hash.
   * 
   * @param href
   *          the href like
   *          http://username:password@domain:port/path?query_string#fragment_id
   * @return the hash (aka the fragment_id)
   */
  public static String getHash(final String href) {
    final int hashPos = href.lastIndexOf("#");
    final int hashbagPos = href.lastIndexOf("#!");
    final int pos = hashbagPos > 0 ? hashbagPos + 2 : hashPos > 0 ? hashPos + 1 : 0;
    return (pos > 0) ? href.substring(pos) : href;
  }

  /**
   * Checks if some url is local
   * 
   * @param href
   *          the href like
   *          http://username:password@domain:port/path?query_string#fragment_id
   * @param base
   *          the base like http://username:password@domain:port
   * @return true, if is local
   */
  public static boolean isLocal(final String href, final String base) {
    return href.startsWith(base);
  }

}
