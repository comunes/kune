package cc.kune.common.client.actions.ui.descrip;

public class MenuSeparatorDescriptor extends AbstractSeparatorDescriptor {

    public MenuSeparatorDescriptor(final MenuDescriptor parent) {
        super();
        setParent(parent);
    }

    @Override
    public Class<?> getType() {
        return MenuSeparatorDescriptor.class;
    }
}