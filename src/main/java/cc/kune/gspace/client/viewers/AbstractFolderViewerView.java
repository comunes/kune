package cc.kune.gspace.client.viewers;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.ui.HasEditHandler;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.gspace.client.viewers.items.FolderItemDescriptor;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.gwtplatform.mvp.client.View;

public interface AbstractFolderViewerView extends View {
  void addItem(FolderItemDescriptor item, ClickHandler clickHandler,
      DoubleClickHandler doubleClickHandler);

  void attach();

  void clear();

  void detach();

  void editTitle();

  HasEditHandler getEditTitle();

  void highlightTitle();

  void setContainer(StateContainerDTO state);

  void setEditableTitle(String title);

  void setFooterActions(GuiActionDescCollection actions);

  void setSubheaderActions(GuiActionDescCollection actions);

  void showEmptyMsg(String message);

  void showTutorial(String tool);
}
