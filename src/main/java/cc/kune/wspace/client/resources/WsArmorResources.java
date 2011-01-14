package cc.kune.wspace.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface WsArmorResources extends ClientBundle {
    public interface Style extends CssResource {

        String docEditor();

        String docEditorMargin();

        String docFooter();

        String docHeader();

        String docHeaderArrow();

        String docSubheader();

        String docSubheaderLeft();

        String entityFooter();

        String entityHeader();

        String entityTools();

        String entityToolsCenter();

        String entityToolsNorth();

        String entityToolsSouth();

        String floatLeft();

        String floatRight();

        String mainPanel();

        String siteBar();
    }

    @Source("def-theme-doc-arrow-down.png")
    ImageResource defThemeDocArrowDown();

    @Source("def-theme-tools-arrow-left.png")
    ImageResource defThemeToolsArrowLeft();

    @Source("wsArmor.css")
    Style style();

    ImageResource publicSpaceEnabled();

    ImageResource publicSpaceDisabled();

    ImageResource userSpaceEnabled();

    ImageResource userSpaceDisabled();

    ImageResource homeSpaceEnabled();

    ImageResource homeSpaceDisabled();

    ImageResource groupSpaceEnabled();

    ImageResource groupSpaceDisabled();
}