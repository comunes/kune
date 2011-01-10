package cc.kune.common.client.noti;

public interface SimpleCallback {
    /**
     * Notifies this callback of an accept response.
     */
    void onSuccess();

    /**
     * Notifies this callback of a cancel.
     */
    void onCancel();
}
