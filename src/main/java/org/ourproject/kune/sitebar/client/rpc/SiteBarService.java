package org.ourproject.kune.sitebar.client.rpc;

import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface SiteBarService extends RemoteService {

    String login(String nick, String pass) throws SerializableException;

    void logout() throws SerializableException;

    void createNewGroup(GroupDTO group) throws SerializableException;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    List getAllLicenses() throws SerializableException;

    public class App {
        private static SiteBarServiceAsync ourInstance = null;

        public static synchronized SiteBarServiceAsync getInstance() {
            if (ourInstance == null) {
                ourInstance = (SiteBarServiceAsync) GWT.create(SiteBarService.class);
                ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "SiteBarService");
            }
            return ourInstance;
        }

        public static void setMock(final SiteBarServiceAsync mock) {
            ourInstance = mock;
        }
    }
}
