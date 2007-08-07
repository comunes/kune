package org.ourproject.kune.docs.client.folder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
 * 
 * http://code.google.com/p/google-web-toolkit/wiki/ImageBundleDesign
 * 
 */
interface NavImages extends ImageBundle {

    public static class App {
        private static NavImages ourInstance = null;

        public static synchronized NavImages getInstance() {
            if (ourInstance == null) {
                ourInstance = (NavImages) GWT.create(NavImages.class);
            }
            return ourInstance;
        }
    }

    /**
     * @gwt.resource bullet_arrow_right.png
     */
    AbstractImagePrototype bulletArrowRight();

    /**
     * @gwt.resource folder.png
     */
    AbstractImagePrototype folder();

    /**
     * @gwt.resource folder_add.png
     */
    AbstractImagePrototype folderAdd();

    /**
     * @gwt.resource folderpathmenu.png
     */
    AbstractImagePrototype folderpathmenu();

    /**
     * @gwt.resource go-up.png
     */
    AbstractImagePrototype goUp();

    /**
     * @gwt.resource go-up-light.png
     */
    AbstractImagePrototype goUpLight();

    /**
     * @gwt.resource page.png
     */
    AbstractImagePrototype page();

    /**
     * @gwt.resource page_add.png
     */
    AbstractImagePrototype pageAdd();

    /**
     * @gwt.resource page_white.png
     */
    AbstractImagePrototype pageWhite();

    /**
     * @gwt.resource page_white_add.png
     */
    AbstractImagePrototype pageWhiteAdd();

}