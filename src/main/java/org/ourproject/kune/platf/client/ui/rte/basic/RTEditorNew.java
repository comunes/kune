package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.toolbar.ActionToolbar;
import org.ourproject.kune.platf.client.actions.ui.AbstractGuiActionDescrip;
import org.ourproject.kune.platf.client.actions.ui.GuiActionCollection;
import org.ourproject.kune.platf.client.actions.ui.GuiAddCondition;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;

import com.calclab.suco.client.events.Listener0;

public interface RTEditorNew {

    String TOPBAR = "rte-topbar";
    String SNDBAR = "rte-sndbar";
    String LINKCTX = "rte-linkctx";

    void addAction(AbstractGuiActionDescrip action);

    void addActions(GuiActionCollection actions);

    void addOnEditListener(Listener0 listener);

    void attach();

    void detach();

    GuiAddCondition getBasicAddCondition();

    MenuDescriptor getEditMenu();

    View getEditorArea();

    GuiAddCondition getExtendedAddCondition();

    MenuDescriptor getFormatMenu();

    String getHtml();

    MenuDescriptor getInsertMenu();

    MenuDescriptor getLinkCtxMenu();

    ActionToolbar<Object> getSndBar();

    String getText();

    ActionToolbar<Object> getTopBar();

    void reset();

    void setExtended(boolean extended);

    void setFocus(boolean focus);

    void setHtml(String html);

    void setText(String text);
}
