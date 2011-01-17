package cc.kune.common.client.actions.ui.descrip;


public class ToolbarSeparatorDescriptor extends AbstractSeparatorDescriptor {

    public enum Type {
        spacer, separator, fill
    }

    private final Type type;

    public ToolbarSeparatorDescriptor(final Type type, final ToolbarDescriptor parent) {
        super();
        this.type = type;
        setParent(parent);
    }

    public Type getSeparatorType() {
        return type;
    }

    @Override
    public Class<?> getType() {
        return ToolbarSeparatorDescriptor.class;
    }

}
