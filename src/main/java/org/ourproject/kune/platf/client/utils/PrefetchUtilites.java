package org.ourproject.kune.platf.client.utils;

import java.util.List;

import org.ourproject.kune.platf.client.rpc.KuneService;
import org.ourproject.kune.platf.client.state.State;
import org.ourproject.kune.sitebar.client.Site;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

public class PrefetchUtilites {

    public static void preFetchImpImages() {
	Image.prefetch("images/spin-kune-thund-green.gif");

	Image.prefetch("css/img/button15cdark.png");
	Image.prefetch("css/img/button15clight.png");
	Image.prefetch("css/img/button15cxlight.png");
	Image.prefetch("css/img/button17cdark.png");
	Image.prefetch("css/img/button17clight.png");
	Image.prefetch("css/img/button17cxlight.png");
	Image.prefetch("css/img/button20cdark.png");
	Image.prefetch("css/img/button20clight.png");
	Image.prefetch("css/img/button20cxlight.png");
	Image.prefetch("css/img/button-bg-soft.gif");
	Image.prefetch("css/img/button-bg-hard.gif");

	Image.prefetch("gwm/themes/alphacubecustom/b.gif");
	Image.prefetch("gwm/themes/alphacubecustom/bl.gif");
	Image.prefetch("gwm/themes/alphacubecustom/br.gif");
	Image.prefetch("gwm/themes/alphacubecustom/close-btn.gif");
	Image.prefetch("gwm/themes/alphacubecustom/close-btn-on.gif");
	Image.prefetch("gwm/themes/alphacubecustom/l.gif");
	Image.prefetch("gwm/themes/alphacubecustom/max-btn.gif");
	Image.prefetch("gwm/themes/alphacubecustom/max-btn-on.gif");
	Image.prefetch("gwm/themes/alphacubecustom/min-btn.gif");
	Image.prefetch("gwm/themes/alphacubecustom/min-btn-on.gif");
	Image.prefetch("gwm/themes/alphacubecustom/resize-btn.gif");
	Image.prefetch("gwm/themes/alphacubecustom/restore-btn.gif");
	Image.prefetch("gwm/themes/alphacubecustom/r.gif");
	Image.prefetch("gwm/themes/alphacubecustom/t.gif");
	Image.prefetch("gwm/themes/alphacubecustom/tr.gif");
	Image.prefetch("gwm/themes/alphacubecustom/tl.gif");
	Image.prefetch("gwm/themes/alphacubecustom/t-off.gif");
	Image.prefetch("gwm/themes/alphacubecustom/tr-off.gif");
	Image.prefetch("gwm/themes/alphacubecustom/tl-off.gif");
    }

    public static void preFetchLicenses(final State state) {
	org.ourproject.kune.platf.client.rpc.KuneServiceAsync kuneService = KuneService.App.getInstance();
	kuneService.getAllLicenses(new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		// TODO
		Site.error("Error fetching initial data");
	    }

	    public void onSuccess(final Object result) {
		state.setLicenses((List) result);
	    }
	});

	kuneService.getNotCCLicenses(new AsyncCallback() {
	    public void onFailure(final Throwable arg0) {
		// TODO
		Site.error("Error fetching initial data");
	    }

	    public void onSuccess(final Object result) {
		state.setLicensesNotCC((List) result);
	    }
	});

    }
}
