package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.gwtext.client.widgets.QuickTip;
import com.gwtext.client.widgets.QuickTips;

public class QuickTipsHelper {

    public QuickTipsHelper() {
	DeferredCommand.addCommand(new Command() {
	    public void execute() {
		if (!QuickTips.isEnabled()) {
		    // If not enabled before by another UI component
		    QuickTips.init();
		    final QuickTip quickTipInstance = QuickTips.getQuickTip();
		    quickTipInstance.setInterceptTitles(true);
		    quickTipInstance.setDismissDelay(7000);
		    quickTipInstance.setHideDelay(400);
		    quickTipInstance.setMinWidth(100);
		}
	    }
	});
    }
}