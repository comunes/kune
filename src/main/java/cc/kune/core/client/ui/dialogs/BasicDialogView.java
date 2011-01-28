package cc.kune.core.client.ui.dialogs;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasDirectionalText;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;

public interface BasicDialogView {

    ForIsWidget getBottomPanel();

    HasClickHandlers getFirstBtn();

    HasText getFirstBtnText();

    ForIsWidget getInnerPanel();

    HasClickHandlers getSecondBtn();

    HasText getSecondBtnText();

    HasDirectionalText getTitleText();

}