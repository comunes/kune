package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.View;

public interface WizardDialogView extends View {

    void add(View view);

    void center();

    void clear();

    void hide();

    boolean isCurrentPage(View view);

    void mask(final String message);

    void maskProcessing();

    void remove(View view);

    void setEnabled(boolean back, boolean next, boolean cancel, boolean finish);

    void setEnabledBackButton(final boolean enabled);

    void setEnabledCancelButton(final boolean enabled);

    void setEnabledFinishButton(final boolean enabled);

    void setEnabledNextButton(final boolean enabled);

    void setFinishText(final String text);

    void setVisible(boolean back, boolean next, boolean cancel, boolean finish);

    void setVisibleBackButton(final boolean visible);

    void setVisibleCancelButton(final boolean visible);

    void setVisibleFinishButton(final boolean visible);

    void setVisibleNextButton(final boolean visible);

    void show();

    void show(View view);

    void unMask();

}
