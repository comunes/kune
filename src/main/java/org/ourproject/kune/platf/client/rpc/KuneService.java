package org.ourproject.kune.platf.client.rpc;

import java.util.List;

import org.ourproject.kune.platf.client.dto.GroupDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializableException;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface KuneService extends RemoteService {

    public GroupDTO getDefaultGroup(String userHash);

    void createNewGroup(GroupDTO group) throws SerializableException;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    List getAllLicenses() throws SerializableException;

    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    List getNotCCLicenses() throws SerializableException;

    public class App {
	private static KuneServiceAsync ourInstance = null;

	public static synchronized KuneServiceAsync getInstance() {
	    if (ourInstance == null) {
		ourInstance = (KuneServiceAsync) GWT.create(KuneService.class);
		((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KuneService");
	    }
	    return ourInstance;
	}

	public static void setMock(final KuneServiceAsync mocked) {
	    ourInstance = mocked;
	}
    }

}
