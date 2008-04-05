package org.ourproject.kune.workspace.client.newgroup;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.client.dto.LicenseDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.errors.GroupNameInUseException;
import org.ourproject.kune.platf.client.rpc.ParamCallback;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.sitebar.msg.SiteMessage;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NewGroupPresenter implements NewGroup {
    private final NewGroupListener listener;
    private NewGroupView view;

    public NewGroupPresenter(final NewGroupListener listener) {
        this.listener = listener;
    }

    public void init(final NewGroupView view) {
        this.view = view;
    }

    public void onFinish() {
        view.maskProcessing();
        String shortName = view.getShortName();
        String longName = view.getLongName();
        String publicDesc = view.getPublicDesc();
        LicenseDTO license = view.getLicense();
        GroupDTO group = new GroupDTO(shortName, longName, publicDesc, getTypeOfGroup());
        group.setDefaultLicense(license);

        AsyncCallback<StateToken> callback = new AsyncCallback<StateToken>() {
            public void onFailure(final Throwable caught) {
                try {
                    throw caught;
                } catch (final GroupNameInUseException e) {
                    onBack();
                    view.unMask();
                    setMessage(Kune.I18N.t("This name in already in use, try with a different name."),
                            SiteMessage.ERROR);
                } catch (final Throwable e) {
                    onBack(); // The messageP is in first page of wizard :-/
                    view.unMask();
                    setMessage(Kune.I18N.t("Error creating group"), SiteMessage.ERROR);
                    GWT.log("Other kind of exception in group registration", null);
                    throw new RuntimeException();
                }
            }

            public void onSuccess(final StateToken token) {
                listener.onNewGroupCreated(token);
                view.hide();
                reset();
                view.unMask();
            }
        };

        DefaultDispatcher.getInstance().fire(WorkspaceEvents.CREATE_NEW_GROUP,
                new ParamCallback<GroupDTO, StateToken>(group, callback));
    }

    public View getView() {
        return view;
    }

    private String getTypeOfGroup() {
        if (view.isProject()) {
            return GroupDTO.PROJECT;
        } else if (view.isOrphanedProject()) {
            return GroupDTO.ORPHANED_PROJECT;
        } else if (view.isOrganization()) {
            return GroupDTO.ORGANIZATION;
        } else {
            return GroupDTO.COMMUNITY;
        }
    }

    private void reset() {
        view.clearData();
    }

    public void onBack() {
        view.setEnabledBackButton(false);
        view.setEnabledFinishButton(false);
        view.setEnabledNextButton(true);
        view.showNewGroupInitialDataForm();
    }

    public void onCancel() {
        listener.onNewGroupCancel();
        view.hide();
        reset();
    }

    public void onClose() {
        listener.onNewGroupClose();
        reset();
    }

    public void onNext() {
        if (view.isFormValid()) {
            view.setEnabledBackButton(true);
            view.setEnabledFinishButton(true);
            view.setEnabledNextButton(false);
            view.showLicenseForm();
        }
    }

    public void onChange() {
        // This doesn't work perfect (don't use now):
        // if (view.isFormValid()) {
        // view.setEnabledNextButton(true);
        // } else {
        // view.setEnabledNextButton(false);
        // }
    }

    public void setMessage(final String message, final int type) {
        view.setMessage(message, type);
    }
}
