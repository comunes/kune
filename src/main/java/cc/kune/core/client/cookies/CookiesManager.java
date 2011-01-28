package cc.kune.core.client.cookies;

public interface CookiesManager {
    String getCurrentCookie();

    void removeCookie();

    void setCookie(String userHash);
}
