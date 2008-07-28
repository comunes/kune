package org.ourproject.kune.platf.client.ui;

import com.gwtext.client.widgets.QuickTip;
import com.gwtext.client.widgets.QuickTips;

public class QuickTipHelper {

    public QuickTipHelper() {
	QuickTips.init();
	final QuickTip quickTipInstance = QuickTips.getQuickTip();
	quickTipInstance.setDismissDelay(8000);
	quickTipInstance.setHideDelay(500);
	quickTipInstance.setInterceptTitles(true);
    }
}
