package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.cobogw.gwt.user.client.CSS;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

public class ImageInfo {

    public static final String SIZE_ORIGINAL = "original";
    public static final String SIZE_FIT = "fit";
    public static final String SIZE_XS = "xs";
    public static final String SIZE_S = "s";
    public static final String SIZE_M = "m";
    public static final String SIZE_L = "l";
    public static final String SIZE_XL = "xl";
    public static final String POSITION_LEFT = "left";
    public static final String POSITION_CENTER = "center";
    public static final String POSITION_RIGHT = "right";

    public static String[][] positions = { new String[] { POSITION_LEFT }, new String[] { POSITION_CENTER },
            new String[] { POSITION_RIGHT } };

    public static String[][] sizes = { new String[] { SIZE_ORIGINAL, "Original image size", "" },
            new String[] { SIZE_FIT, "Fit page width", "100%" },
            new String[] { SIZE_XS, "Thumbnail, up to 160 pixels wide", "160px" },
            new String[] { SIZE_S, "Small, up to 320 pixels wide", "320px" },
            new String[] { SIZE_M, "Medium, up to 640 pixels wide", "640px" },
            new String[] { SIZE_L, "Large, up to 1024 pixels wide", "1024px" },
            new String[] { SIZE_XL, "Extra large, up to 1600 pixels wide", "1600px" } };

    private String src;
    private boolean wraptext;
    private String position;
    private String size;

    public ImageInfo(String src, boolean wraptext, String position, String size) {
        this.src = src;
        this.wraptext = wraptext;
        this.setPosition(position);
        this.setSize(size);
    }

    public Element getElement() {
        com.google.gwt.user.client.Element img = DOM.createImg();
        DOM.setElementProperty(img, "src", src);
        CSS.setProperty(img, CSS.A.WIDTH, getStyleSize());
        if (!wraptext || position.equals(POSITION_CENTER)) {
            com.google.gwt.user.client.Element divEl = DOM.createDiv();
            CSS.setProperty(divEl, CSS.A.TEXT_ALIGN, position);
            divEl.setInnerHTML(img.getString());
            return divEl;
        } else {
            CSS.setProperty(img, CSS.A.FLOAT, position);
            return img;
        }
    }

    public String getPosition() {
        return position;
    }

    public String getSize() {
        return size;
    }

    public String getSrc() {
        return src;
    }

    public boolean isWraptext() {
        return wraptext;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setWraptext(boolean wraptext) {
        this.wraptext = wraptext;
    }

    @Override
    public String toString() {
        Element element = getElement();
        return element.getString();
    }

    private String getStyleSize() {
        for (String[] current : sizes) {
            if (current[1].equals(size)) {
                return current[2];
            }
        }
        return "";
    }
}
