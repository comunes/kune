package org.ourproject.kune.platf.client.workspace.ui;

import com.google.gwt.user.client.Timer;

public class SiteMessagePresenter implements SiteMessage {

    private static final int TIMEVISIBLE = 4000;

    private static final int ERROR = 1;
    private static final int VERYIMP = 2;
    private static final int IMP = 3;
    private static final int INFO = 4;

    private SiteMessageView view;
    private boolean isVisible;
    private String message;
    private int currentMessageType;

    public SiteMessagePresenter() {
        // backGroundColor = new HashMap();
        // borderColor = new HashMap();
        //
        // backGroundColor.put(ERROR, "#FFB380");
        // backGroundColor.put(VERYIMP, "#FFD4AA");
        // backGroundColor.put(IMP, "#FFE6D5");
        // backGroundColor.put(INFO, "#E5FF80");
        //
        // borderColor.put(ERROR, "red");
        // borderColor.put(ERROR, "red");
        // borderColor.put(ERROR, "red");
        // borderColor.put(INFO, "#CF0");
    }

    public void init(SiteMessageView siteMessageView) {
        this.view = siteMessageView;
    }

    Timer timer = new Timer() {
        public void run() {
            reset();
        }
    };

    private void reset() {
        view.hide();
        view.reset();
        this.message = "";
        this.isVisible = false;
    }

    public void setMessage(String message, int type) {
        if (isVisible) {
            this.message = this.message + "\n" + message;
            if (currentMessageType != type) {
                // TODO: set new type
                currentMessageType = type;
            }
        } else {
            this.message = message;
            isVisible = true;
        }
        timer.schedule(TIMEVISIBLE);
    }

    public void info(String message) {
        setMessage(message, INFO);
        view.setMessageInfo(message);
    }

    public void important(String message) {
        setMessage(message, IMP);
        view.setMessageImp(message);
    }

    public void veryImportant(String message) {
        setMessage(message, VERYIMP);
        view.setMessageVeryImp(message);
    }

    public void error(String message) {
        setMessage(message, ERROR);
        view.setMessageError(message);
    }

    public void onClose() {
        timer.run();
    }
}
