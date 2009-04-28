package org.ourproject.kune.platf.client.dto;

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

    public ExtMediaDescripDTO(final String name, final String siteurl, final String detectRegex,
            final String idRegex, final String embedTemplate, final int defWidth, final int defHeight) {
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
        String id = getId(url);
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
        String id = url.replaceFirst(idRegex, "$1");
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
