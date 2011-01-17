package cc.kune.common.client.actions;

import com.google.gwt.resources.client.ImageResource;

public class BaseAction extends AbstractAction {
    public BaseAction(final String text, final String tooltip) {
        super();
        super.putValue(Action.NAME, text);
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
    }

    public BaseAction(final String text, final String tooltip, final ImageResource icon) {
        super();
        super.putValue(Action.NAME, text);
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
        super.putValue(Action.SMALL_ICON, icon);
    }

    public BaseAction(final String text, final String tooltip, final String icon) {
        super();
        super.putValue(Action.NAME, text);
        super.putValue(Action.SHORT_DESCRIPTION, tooltip);
        super.putValue(Action.SMALL_ICON, icon);
    }

    public void actionPerformed(final ActionEvent actionEvent) {
        // Nothing to do
    }
}
