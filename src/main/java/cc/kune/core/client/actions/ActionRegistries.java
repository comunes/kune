package cc.kune.core.client.actions;


public class ActionRegistries {

    ActionRegistryByType contentFooterActions;
    ActionRegistryByType contentHeaderActions;
    ActionRegistryByType contentItemMenu;
    ActionRegistryByType contentSubHeaderActions;

    public ActionRegistries() {
        contentSubHeaderActions = new ActionRegistryByType();
        contentHeaderActions = new ActionRegistryByType();
        contentFooterActions = new ActionRegistryByType();
        contentItemMenu = new ActionRegistryByType();
    }

}
