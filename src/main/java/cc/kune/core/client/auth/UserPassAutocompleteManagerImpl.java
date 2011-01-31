package cc.kune.core.client.auth;

import com.google.gwt.user.client.DOM;

/**
 * Implementation <a href=
 * "http://stackoverflow.com/questions/1245174/is-it-possible-to-implement-cross-browser-username-password-autocomplete-in-gxt"
 * >based in this</a>
 */
public class UserPassAutocompleteManagerImpl implements UserPassAutocompleteManager {

    private static final String VALUE = "value";

    public static native String getElementValue(String domId) /*-{
        return $doc.getElementById(domId).value;
    }-*/;

    @Override
    public native void clickFormLogin() /*-{
        $doc.getElementById("login").click();
    }-*/;

    @Override
    public String getNickOrEmail() {
        return getElementValue("username");
    }

    @Override
    public String getPassword() {
        return getElementValue("password");
    }

    @Override
    public void setNickOrEmail(final String username) {
        DOM.getElementById("username").setAttribute(VALUE, username);
    }

    @Override
    public void setPassword(final String password) {
        DOM.getElementById("password").setAttribute(VALUE, password);
    }

}
