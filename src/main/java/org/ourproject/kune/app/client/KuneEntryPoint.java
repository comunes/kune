package org.ourproject.kune.app.client;

import java.util.HashMap;

import org.ourproject.kune.platf.client.app.ApplicationBuilder;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class KuneEntryPoint implements EntryPoint {

    public KuneEntryPoint() {
    }

    public void onModuleLoad() {
        /*
         * Install an UncaughtExceptionHandler which will produce <code>FATAL</code>
         * log messages
         */

        /*
         * Currently we let firebug to catch the error:
         * Log.setUncaughtExceptionHandler();
         */

        // At the moment, in runtime:
        Log.setCurrentLogLevel(Log.LOG_LEVEL_DEBUG);

        /*
         * Use a deferred command so that the UncaughtExceptionHandler catches
         * any exceptions in onModuleLoadCont()
         */
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                onModuleLoadCont();
            }
        });
    }

    public void onModuleLoadCont() {
        final String userHash = Cookies.getCookie("userHash");
        Kune.I18N.getInitialLanguage(new AsyncCallback<I18nLanguageDTO>() {
            public void onFailure(final Throwable caught) {
                Log.debug("Workspace adaptation to your language failed");
            }

            public void onSuccess(final I18nLanguageDTO result) {
                final I18nLanguageDTO initialLang = result;
                Kune.I18N.getInitialLexicon(initialLang.getCode(), new AsyncCallback<HashMap<String, String>>() {
                    public void onFailure(final Throwable caught) {
                        Log.debug("Workspace adaptation to your language failed");
                    }

                    public void onSuccess(final HashMap<String, String> result) {
                        I18nUITranslationService.getInstance().setLexicon(result);
                        new ApplicationBuilder().build(userHash, initialLang);
                    }
                });
            }
        });
    }
}
