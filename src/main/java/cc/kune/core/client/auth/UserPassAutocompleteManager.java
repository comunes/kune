package cc.kune.core.client.auth;

public interface UserPassAutocompleteManager {

    void clickFormLogin();

    String getNickOrEmail();

    String getPassword();

    void setNickOrEmail(String username);

    void setPassword(String password);

}
