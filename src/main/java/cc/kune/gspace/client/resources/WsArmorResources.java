package cc.kune.gspace.client.resources;

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

    ImageResource groupSpaceDisabled();

    ImageResource groupSpaceEnabled();

    ImageResource homeSpaceDisabled();

    ImageResource homeSpaceEnabled();

    ImageResource publicSpaceDisabled();

    ImageResource publicSpaceEnabled();

    @Source("wsArmor.css")
    Style style();

    ImageResource userSpaceDisabled();

    ImageResource userSpaceEnabled();
}