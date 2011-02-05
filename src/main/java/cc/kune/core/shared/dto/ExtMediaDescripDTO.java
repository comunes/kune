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
package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ExtMediaDescripDTO implements IsSerializable {

    public static final String URL = "###URL###";
    public static final String HEIGHT = "###HEIGHT###";
    public static final String WIDTH = "###WIDTH###";

    private String name;
    private String siteurl;
    private String detectRegex;
    private String idRegex;
    private String embedTemplate;
    private int width;
    private int height;

    public ExtMediaDescripDTO() {
        this(null, null, null, null, null, 0, 0);
    }

    public ExtMediaDescripDTO(final String name, final String siteurl, final String detectRegex, final String idRegex,
            final String embedTemplate, final int defWidth, final int defHeight) {
        this.name = name;
        this.siteurl = siteurl;
        this.detectRegex = detectRegex;
        this.idRegex = idRegex;
        this.embedTemplate = embedTemplate;
        width = defWidth;
        height = defHeight;
    }

    public String getDetectRegex() {
        return detectRegex;
    }

    public String getEmbed(final String url) {
        final String id = getId(url);
        String result = embedTemplate.replaceAll(URL, id);
        result = result.replaceAll(HEIGHT, "" + height);
        result = result.replaceAll(WIDTH, "" + width);
        return result;
    }

    public String getEmbedTemplate() {
        return embedTemplate;
    }

    public int getHeight() {
        return height;
    }

    public String getId(final String url) {
        final String id = url.replaceFirst(idRegex, "$1");
        return id;
    }

    public String getIdRegex() {
        return idRegex;
    }

    public String getName() {
        return name;
    }

    public String getSiteurl() {
        return siteurl;
    }

    public int getWidth() {
        return width;
    }

    public boolean is(final String url) {
        return url.matches(detectRegex);
    }

    public void setDetectRegex(final String detectRegex) {
        this.detectRegex = detectRegex;
    }

    public void setEmbedTemplate(final String embedTemplate) {
        this.embedTemplate = embedTemplate;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setIdRegex(final String idRegex) {
        this.idRegex = idRegex;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSiteurl(final String siteurl) {
        this.siteurl = siteurl;
    }

    public void setWidth(final int width) {
        this.width = width;
    }
}
