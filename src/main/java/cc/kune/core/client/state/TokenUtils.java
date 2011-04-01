package cc.kune.core.client.state;

public class TokenUtils {

    public static String addRedirect(final String token, final String redirect) {
        return new StringBuffer().append(token).append("(").append(redirect).append(")").toString();
    }

    public static String preview(final String token) {
        return addRedirect(SiteTokens.PREVIEW, token);
    }
}
