package org.ourproject.massmob.client;

public interface UserSelfPreferences {
    String get(String key, String defValue);

    void set(String key, String value);
}
