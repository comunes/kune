package cc.kune.core.client.state;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.gwtplatform.mvp.client.HasEventBus;

public class UserSignInEvent extends GwtEvent<UserSignInEvent.UserSignInHandler> {

    public interface HasUserSignInHandlers extends HasHandlers {
        HandlerRegistration addUserSignInHandler(UserSignInHandler handler);
    }

    public interface UserSignInHandler extends EventHandler {
        public void onUserSignIn(UserSignInEvent event);
    }

    private static final Type<UserSignInHandler> TYPE = new Type<UserSignInHandler>();

    public static void fire(final HasEventBus source, final cc.kune.core.shared.dto.UserInfoDTO userInfo) {
        source.fireEvent(new UserSignInEvent(userInfo));
    }

    public static Type<UserSignInHandler> getType() {
        return TYPE;
    }

    private final cc.kune.core.shared.dto.UserInfoDTO userInfo;

    public UserSignInEvent(final cc.kune.core.shared.dto.UserInfoDTO userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    protected void dispatch(final UserSignInHandler handler) {
        handler.onUserSignIn(this);
    }

    @Override
    public boolean equals(final Object other) {
        if (other != null && other.getClass().equals(this.getClass())) {
            final UserSignInEvent o = (UserSignInEvent) other;
            return true && ((o.userInfo == null && this.userInfo == null) || (o.userInfo != null && o.userInfo.equals(this.userInfo)));
        }
        return false;
    }

    @Override
    public Type<UserSignInHandler> getAssociatedType() {
        return TYPE;
    }

    public cc.kune.core.shared.dto.UserInfoDTO getUserInfo() {
        return userInfo;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + getClass().hashCode();
        hashCode = (hashCode * 37) + (userInfo == null ? 1 : userInfo.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "UserSignInEvent[" + userInfo + "]";
    }

}
