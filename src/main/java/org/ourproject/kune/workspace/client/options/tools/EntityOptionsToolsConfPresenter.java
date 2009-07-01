package org.ourproject.kune.workspace.client.options.tools;

import java.util.Collection;
import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.dto.ToolSimpleDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.rpc.GroupServiceAsync;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class EntityOptionsToolsConfPresenter {

    private EntityOptionsToolsConfView view;
    private final EntityOptions entityOptions;
    protected final Session session;
    private final Provider<GroupServiceAsync> groupService;
    protected final I18nTranslationService i18n;
    protected final StateManager stateManager;

    public EntityOptionsToolsConfPresenter(final Session session, final StateManager stateManager,
            final I18nTranslationService i18n, final EntityOptions entityOptions,
            final Provider<GroupServiceAsync> groupService) {
        this.session = session;
        this.stateManager = stateManager;
        this.i18n = i18n;
        this.entityOptions = entityOptions;
        this.groupService = groupService;
    }

    public View getView() {
        return view;
    }

    public void init(final EntityOptionsToolsConfView view) {
        this.view = view;
        setState();
        entityOptions.addTab(view);
    }

    public void onCheck(final ToolSimpleDTO tool, final boolean checked) {
        final List<String> enabledTools = getEnabledTools();
        final String toolName = tool.getName();
        if (checked) {
            if (!(enabledTools.contains(toolName))) {
                setToolCheckedInServer(checked, toolName);
            }
        } else {
            if (enabledTools.contains(toolName)) {
                setToolCheckedInServer(checked, toolName);
                gotoDifLocationIfNecessary(toolName);
            }
        }
    }

    protected abstract boolean applicable();

    protected abstract Collection<ToolSimpleDTO> getAllTools();

    protected abstract StateToken getDefContentToken();

    protected abstract String getDefContentTooltip();

    protected abstract List<String> getEnabledTools();

    protected abstract StateToken getOperationToken();

    protected abstract void gotoDifLocationIfNecessary(String toolName);

    protected void reset() {
        view.clear();
        entityOptions.hideMessages();
    }

    protected void setState() {
        reset();
        final Collection<ToolSimpleDTO> toolCollection = getAllTools();
        for (final ToolSimpleDTO tool : toolCollection) {
            view.add(tool);
            view.setEnabled(tool.getName(), true);
        }
        for (final String tool : getEnabledTools()) {
            view.setChecked(tool, true);
        }

        final StateToken token = getDefContentToken();
        if (token != null) {
            final String defContentTool = token.getTool();
            if (defContentTool != null) {
                view.setEnabled(defContentTool, false);
                view.setTooltip(defContentTool, getDefContentTooltip());
            }
        }
    }

    protected void setToolCheckedInServer(final boolean checked, final String toolName) {
        groupService.get().setToolEnabled(session.getUserHash(), getOperationToken(), toolName, checked,
                new AsyncCallback<Object>() {
                    public void onFailure(final Throwable caught) {
                        view.setChecked(toolName, !checked);
                        entityOptions.setErrorMessage(i18n.t("Error configuring the tool"), Level.error);
                    }

                    public void onSuccess(final Object result) {
                        stateManager.reload();
                    }
                });
    }
}
