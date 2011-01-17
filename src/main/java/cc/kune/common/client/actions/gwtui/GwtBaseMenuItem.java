package cc.kune.common.client.actions.gwtui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;

public class GwtBaseMenuItem extends MenuItem {

    private static Command dummyCommand() {
        return new Command() {
            @Override
            public void execute() {
            }
        };
    }

    public GwtBaseMenuItem() {
        super("", dummyCommand());
    }

    public GwtBaseMenuItem(final String text) {
        super(text, dummyCommand());
    }

    public GwtBaseMenuItem(final String text, final boolean asHtml) {
        super(text, asHtml, dummyCommand());
    }

}
