package org.ourproject.massmob.client;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Inject;

public class CookiesUserSelfPreferences implements UserSelfPreferences {

    private static final String COOKIE_NAME = "massmobuserpref";
    private final WaveUtils waveUtils;

    @Inject
    public CookiesUserSelfPreferences(final WaveUtils waveUtils) {
        this.waveUtils = waveUtils;
    }

    @Override
    public String get(final String key, final String defValue) {
        final JSONValue jsonValue = getCookieJson();
        JSONObject obj = jsonValue.isObject();
        if (obj != null) {
            final JSONValue wavePrefObj = obj.get(getWaveId());
            if (wavePrefObj != null) {
                obj = wavePrefObj.isObject();
                if (obj != null) {
                    final JSONValue keyObject = obj.get(key);
                    if (keyObject != null) {
                        final String name = keyObject.isString().stringValue();
                        if (name != null) {
                            return name;
                        }
                    }
                }
            }
        }
        return defValue;
    }

    @Override
    public void set(final String key, final String value) {
        final JSONValue jsonValue = getCookieJson();
        JSONObject obj = jsonValue.isObject();
        if (obj == null) {
            obj = initCookie();
        }
        JSONValue wavePrefObj = obj.get(getWaveId());
        if (wavePrefObj == null) {
            obj = initCookie();
            wavePrefObj = obj.get(getWaveId());
        }
        JSONObject wavePrefs = wavePrefObj.isObject();
        if (wavePrefs == null) {
            obj = initCookie();
            wavePrefs = wavePrefObj.isObject();
        }
        wavePrefs.put(key, new JSONString(value));
        setCookie(obj);
    }

    private JSONValue getCookieJson() {
        final String cookieValue = Cookies.getCookie(COOKIE_NAME);
        // Log.info(cookieValue);
        JSONValue jsonValue;
        if (cookieValue == null) {
            jsonValue = initCookie();
        } else {
            jsonValue = JSONParser.parse(cookieValue);
        }
        return jsonValue;
    }

    private String getWaveId() {
        return waveUtils.getWaveId();
    }

    private JSONObject initCookie() {
        final JSONObject parent = new JSONObject();
        parent.put(getWaveId(), new JSONObject());
        setCookie(parent);
        return parent;
    }

    private void setCookie(final JSONObject parent) {
        Cookies.setCookie(COOKIE_NAME, parent.toString());
    }

}
