package cc.kune.core.client.sitebar.spaces;

import com.gwtplatform.mvp.client.UiHandlers;

public interface SpaceSelectorUiHandlers extends UiHandlers {
    void onHomeSpaceSelect();

    void onUserSpaceSelect();

    void onGroupSpaceSelect();

    void onPublicSpaceClick();
}