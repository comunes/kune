package org.ourproject.kune.platf.client.ui.rte.insertimg;

public class ImageInfo {

    public static String[][] positions = { new String[] { "left" }, new String[] { "center" }, new String[] { "right" } };

    public static String[][] sizes = { new String[] { "original", "Original image size", "" },
            new String[] { "fit", "Fit page width", "100%" },
            new String[] { "xs", "Thumbnail, up to 160 pixels wide", "160px" },
            new String[] { "s", "Small, up to 320 pixels wide", "320px" },
            new String[] { "m", "Medium, up to 640 pixels wide", "640px" },
            new String[] { "l", "Large, up to 1024 pixels wide", "1024px" },
            new String[] { "xl", "Extra large, up to 1600 pixels wide", "1600px" } };

    private String href;
    private boolean wraptext;
    private String position;
    private String size;

    public ImageInfo(String href, boolean wraptext, String position, String size) {
        this.href = href;
        this.wraptext = wraptext;
        this.setPosition(position);
        this.setSize(size);
    }

    public String getHref() {
        return href;
    }

    public String getPosition() {
        return position;
    }

    public String getSize() {
        return size;
    }

    public boolean isWraptext() {
        return wraptext;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setWraptext(boolean wraptext) {
        this.wraptext = wraptext;
    }

}
