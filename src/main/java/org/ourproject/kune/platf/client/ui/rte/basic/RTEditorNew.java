package org.ourproject.kune.platf.client.ui.rte.basic;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescCollection;
import org.ourproject.kune.platf.client.actions.ui.GuiActionDescrip;
import org.ourproject.kune.platf.client.actions.ui.GuiAddCondition;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;

import com.calclab.suco.client.events.Listener0;

public interface RTEditorNew {

    String TOPBAR = "rte-topbar";
    String SNDBAR = "rte-sndbar";
    String LINKCTX = "rte-linkctx";

    void addAction(GuiActionDescrip action);

    void addActions(GuiActionDescCollection actions);

    void addOnEditListener(Listener0 listener);

    void attach();

    void detach();

    GuiAddCondition getBasicAddCondition();

    MenuDescriptor getEditMenu();

    View getEditorArea();

    GuiAddCondition getExtendedAddCondition();

    MenuDescriptor getFileMenu();

    MenuDescriptor getFormatMenu();

    String getHtml();

    MenuDescriptor getInsertMenu();

    MenuDescriptor getLinkCtxMenu();

    View getSndBar();

    String getText();

    View getTopBar();

    void reset();

    void setActionShortcut(KeyStroke key, AbstractAction action);

    void setActionShortcut(KeyStroke key, AbstractAction mainAction, AbstractAction... actions);

    void setExtended(boolean extended);

    void setFocus(boolean focus);

    void setHtml(String html);

    void setText(String text);
}
