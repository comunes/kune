package org.ourproject.kune.docs.client.rpc;

import org.ourproject.kune.platf.client.dto.ContentTreeDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface DocumentService extends RemoteService {
    public ContentTreeDTO getContentTree(String userHash);
    
    public class App {
        private static DocumentServiceAsync ourInstance = null;

        public static synchronized DocumentServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (DocumentServiceAsync) GWT.create(DocumentService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KuneDocumentService");
            }
            return ourInstance;
        }

        public static void setMock(DocumentServiceAsync mock) {
            ourInstance = mock;
        }
    }
}
