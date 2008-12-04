package org.ourproject.kune.workspace.client.options.tools;

import java.util.Collection;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.ContentSimpleDTO;
import org.ourproject.kune.platf.client.dto.StateAbstractDTO;
import org.ourproject.kune.platf.client.dto.ToolSimpleDTO;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.workspace.client.newgroup.SiteErrorType;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.listener.Listener2;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EntityOptionsToolsConfPresenter implements EntityOptionsToolsConf {

    private EntityOptionsToolsConfView view;
    private final Session session;
    private final EntityOptions entityOptions;
    private final Provider<GroupServiceAsync> groupServiceProvider;
    private final I18nTranslationService i18n;
    private final StateManager stateManager;

    public EntityOptionsToolsConfPresenter(StateManager stateManager, Session session, I18nTranslationService i18n,
            EntityOptions entityOptions, Provider<GroupServiceAsync> groupServiceProvider) {
        this.stateManager = stateManager;
        this.session = session;
        this.i18n = i18n;
        this.entityOptions = entityOptions;
        this.groupServiceProvider = groupServiceProvider;
        stateManager.onGroupChanged(new Listener2<String, String>() {
            public void onEvent(String group1, String group2) {
                setState();
            }
        });
    }

    public View getView() {
        return view;
    }

    public void init(EntityOptionsToolsConfView view) {
        this.view = view;
        setState();
        entityOptions.addOptionTab(view);
    }

    public void onCheck(ToolSimpleDTO tool, boolean checked) {
        StateAbstractDTO state = session.getCurrentState();
        String toolName = tool.getName();
        if (checked) {
            if (!(state.getEnabledTools().contains(toolName))) {
                // Log.info("Tool " + tool.getName() + " checked: " + checked +
                // " enabled tools: "
                // + state.getEnabledTools().toString());
                setToolCheckedInServer(checked, toolName);
            } else {
                // do nothing
            }
        } else {
            if (state.getEnabledTools().contains(tool.getName())) {
                // Log.info("Tool " + tool.getName() + " checked: " + checked);
                setToolCheckedInServer(checked, toolName);
                if (session.getCurrentStateToken().getTool().equals(toolName)) {
                    stateManager.gotoToken(session.getCurrentState().getGroup().getDefaultContent().getStateToken());
                }
            }
        }
    }

    private void reset() {
        view.clear();
        entityOptions.hideMessages();
    }

    private void setState() {
        StateAbstractDTO state = session.getCurrentState();
        reset();
        Collection<ToolSimpleDTO> toolCollection;
        if (state.getGroup().isPersonal()) {
            toolCollection = session.getUserTools();
        } else {
            toolCollection = session.getGroupTools();
        }
        for (ToolSimpleDTO tool : toolCollection) {
            view.add(tool);
            view.setEnabled(tool.getName(), true);
        }
        for (String tool : state.getEnabledTools()) {
            view.setChecked(tool, true);
        }
        ContentSimpleDTO defaultContent = session.getCurrentState().getGroup().getDefaultContent();
        if (defaultContent != null) {
            String defContentTool = defaultContent.getStateToken().getTool();
            view.setEnabled(defContentTool, false);
            view.setTooltip(
                    defContentTool,
                    i18n.t("You cannot disable this tool because it's where the current group home page is located. To do that you have to select other content as the default group home page but in another tool."));
        }
    }

    private void setToolCheckedInServer(final boolean checked, final String toolName) {
        groupServiceProvider.get().setToolEnabled(session.getUserHash(), session.getCurrentStateToken(), toolName,
                checked, new AsyncCallback<Object>() {
                    public void onFailure(Throwable caught) {
                        view.setChecked(toolName, !checked);
                        entityOptions.setErrorMessage(i18n.t("Error configuring the tool"), SiteErrorType.error);
                    }

                    public void onSuccess(Object result) {
                        stateManager.reload();
                    }
                });
    }
}
