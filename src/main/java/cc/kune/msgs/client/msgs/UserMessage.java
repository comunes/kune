package cc.kune.msgs.client.msgs;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.examples.Fade;
import org.adamtacy.client.ui.effects.examples.Show;

import cc.kune.msgs.client.resources.UserMessageImages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class UserMessage extends Composite implements HasText {

    private static int fadeMills = 5000;
    private static MessageUiBinder uiBinder = GWT.create(MessageUiBinder.class);
    private static String closeTitle = "Close";

    @UiField
    Image icon;
    @UiField
    InlineHTML label;
    @UiField
    PushButton close;

    interface MessageUiBinder extends UiBinder<Widget, UserMessage> {
    }

    public static void setCloseTitle(String title) {
        closeTitle = title;
    }

    public static void setFadeMills(int mills) {
        fadeMills = mills;
    }

    public UserMessage(UserMessageLevel level, String message, boolean closeable) {
        initWidget(uiBinder.createAndBindUi(this));
        setStyleName("k-msg");
        label.setHTML(message);
        close.setVisible(closeable);
        close.setTitle(closeTitle);
        if (!closeable) {
            Timer time = new Timer() {
                @Override
                public void run() {
                    close();
                }
            };
            time.schedule(fadeMills);
        }
        switch (level) {
        case error:
            icon.setResource(UserMessageImages.INST.error());
            break;
        case important:
            icon.setResource(UserMessageImages.INST.important());
            break;
        case info:
            icon.setResource(UserMessageImages.INST.info());
            break;
        case veryImportant:
            icon.setResource(UserMessageImages.INST.warning());
            break;
        default:
            break;
        }
        Show anim = new Show(this.getElement());
        anim.setDuration(0.5);
        anim.play();
    }

    public UserMessage(String message) {
        this(message, false);
    }

    public UserMessage(String message, boolean closeable) {
        this(UserMessageLevel.info, message, closeable);
    }

    @Override
    public void setText(String text) {
        label.setText(text);
    }

    @Override
    public String getText() {
        return label.getText();
    }

    @UiHandler("close")
    void handleClick(ClickEvent e) {
        close();
    }

    private void close() {
        Fade fade = new Fade(this.getElement());
        fade.setDuration(0.7);
        fade.play();
        fade.addEffectCompletedHandler(new EffectCompletedHandler() {

            @Override
            public void onEffectCompleted(EffectCompletedEvent event) {
                removeFromParent();
            }
        });
    }

}
