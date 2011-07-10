package cc.kune.chat.client.actions.conditions;

import cc.kune.chat.client.ChatClient;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.core.client.state.Session;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class IsNotBuddieCondition extends IsBuddieCondition {

  @Inject
  public IsNotBuddieCondition(final Session session, final ChatClient chatClient) {
    super(session, chatClient);
  }

  @Override
  public boolean mustBeAdded(final GuiActionDescrip descr) {
    return !super.mustBeAdded(descr);
  }

}
