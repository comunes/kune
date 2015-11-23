package org.ourproject.massmob.client.ui;

import org.ourproject.massmob.client.actions.OptionsActions;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import cc.kune.common.client.actions.ui.ActionFlowPanel;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.shared.i18n.HasRTL;

public class OptionsPanel extends Composite {

  interface OptionsPanelUiBinder extends UiBinder<Widget, OptionsPanel> {
  }

  private static OptionsPanelUiBinder uiBinder = GWT.create(OptionsPanelUiBinder.class);

  private final GuiProvider bindReg;

  private final OptionsActions optActions;

  @UiField
  ActionFlowPanel toolbar;

  @Inject
  public OptionsPanel(final GuiProvider bindReg, final OptionsActions optActions) {
    this.bindReg = bindReg;
    this.optActions = optActions;
    initWidget(uiBinder.createAndBindUi(this));
    // toolbar.add(new ToolbarSeparatorDescriptor(Type.spacer, toolbar));
    toolbar.addAll(optActions.getActions());
    toolbar.addStyleName("toolbar");
  }

  @UiFactory
  ActionFlowPanel createGui() {
    // new BasicGuiBindings(bindReg);
    return new ActionFlowPanel(bindReg, new HasRTL() {
      @Override
      public boolean isRTL() {
        return false;
      }
    });
  }

  public void setEnabled(final boolean enabled) {
    optActions.getMenu().setEnabled(enabled);
  }
}
