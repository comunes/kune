package org.ourproject.kune.platf.client.ui.rte.insertmedia;

public class ExternalMediaDescriptor {

    public static final String URL = "###URL###";
    public static final String HEIGHT = "###HEIGHT###";
    public static final String WIDTH = "###WIDTH###";

    private final String name;
    private final String siteurl;
    private final String detectRegex;
    private final String idRegex;
    private final String embedTemplate;
    private int width;
    private int height;

    public ExternalMediaDescriptor(final String name, final String siteurl, final String detectRegex,
            final String idRegex, final String embedTemplate, final int defWidth, final int defHeight) {
        this.name = name;
        this.siteurl = siteurl;
        this.detectRegex = detectRegex;
        this.idRegex = idRegex;
        this.embedTemplate = embedTemplate;
        width = defWidth;
        height = defHeight;
    }

    public String getEmbed(final String url) {
        String id = getId(url);
        String result = embedTemplate.replaceAll(URL, id);
        result = result.replaceAll(HEIGHT, "" + height);
        result = result.replaceAll(WIDTH, "" + width);
        return result;
    }

    public int getHeight() {
        return height;
    }

    public String getId(final String url) {
        String id = url.replaceFirst(idRegex, "$1");
        return id;
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

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setWidth(final int width) {
        this.width = width;
    }
}
