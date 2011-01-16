package cc.kune.wspace.client;

import cc.kune.wspace.client.resources.WsArmorResources;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.RootLayoutPanel;

@Deprecated
public class WsArmorEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
        final WsArmorResources resources = GWT.create(WsArmorResources.class);
        resources.style().ensureInjected();

        final RootLayoutPanel rootPanel = RootLayoutPanel.get();
        final WsArmorImpl armor = new WsArmorImpl(null);
        final InlineLabel icons = new InlineLabel("Icons");
        final InlineLabel login = new InlineLabel("Login");
        final InlineLabel logo = new InlineLabel("Logo");
        icons.setStyleName(resources.style().floatLeft());
        login.setStyleName(resources.style().floatRight());
        logo.setStyleName(resources.style().floatRight());
        armor.getSitebar().add(icons);
        armor.getSitebar().add(logo);
        armor.getSitebar().add(login);
        armor.getEntityToolsNorth().add(new InlineLabel("Group members"));
        armor.getEntityToolsCenter().add(new InlineLabel("Documents"));
        armor.getEntityToolsSouth().add(new InlineLabel("Group tags"));
        armor.getEntityHeader().add(new InlineLabel("Name of the Initiative"));
        armor.getDocHeader().add(new InlineLabel("Some doc title"));
        final InlineLabel editors = new InlineLabel("Editors:");
        editors.addStyleName(resources.style().docSubheaderLeft());
        armor.getDocSubheader().add(editors);
        armor.getDocFooter().add(new InlineLabel("Tags:"));
        armor.getEntityFooter().add(new InlineLabel("Rate it:"));
        rootPanel.add(armor);

    }

}
