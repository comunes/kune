package cc.kune.core.client.sn;

import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuTitleItemDescriptor;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.sn.actions.registry.AbstractSNMembersActionsRegistry;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.proxy.Proxy;

public abstract class AbstractSNPresenter<V extends View, Proxy_ extends Proxy<?>> extends
    Presenter<View, Proxy<?>> {

  protected final Provider<FileDownloadUtils> downloadProvider;

  public AbstractSNPresenter(final EventBus eventBus, final View view, final Proxy<?> proxy,
      final Provider<FileDownloadUtils> downloadProvider) {
    super(eventBus, view, proxy);
    this.downloadProvider = downloadProvider;
  }

  protected GuiActionDescCollection createMenuItems(final Object target,
      final AbstractSNMembersActionsRegistry registry, final String title) {
    final GuiActionDescCollection items = new GuiActionDescCollection();
    items.add(new MenuTitleItemDescriptor(title));
    for (final Provider<MenuItemDescriptor> provider : registry) {
      final MenuItemDescriptor menuItem = provider.get();
      menuItem.setTarget(target);
      items.add(menuItem);
    }
    return items;
  }

  protected String getAvatar(final GroupDTO group) {
    return downloadProvider.get().getGroupLogo(group);
  }

}
