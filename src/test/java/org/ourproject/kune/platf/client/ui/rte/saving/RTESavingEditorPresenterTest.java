/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui.rte.saving;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import cc.kune.common.client.utils.SchedulerManager;
import cc.kune.common.client.utils.TimerWrapper;
import cc.kune.core.client.i18n.I18nTranslationServiceMocked;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.testing.events.MockedListener;
import com.calclab.suco.testing.events.MockedListener0;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.resources.client.ImageResource;

public class RTESavingEditorPresenterTest {

    private MockedListener0 cancelListener;
    private SchedulerManager deferredCommandWrapper;
    private RTESavingEditorPresenter presenter;
    private MockedListener<String> saveListener;
    private StateManager stateManager;
    private TimerWrapper timer;
    private RTESavingEditorView view;

    private void checkSaveBtnDisabled() {
        assertFalse(presenter.saveAction.isEnabled());
    }

    @Before
    public void createObjects() {
        final I18nTranslationService i18n = new I18nTranslationServiceMocked();
        new Resources(i18n);
        stateManager = Mockito.mock(StateManager.class);
        deferredCommandWrapper = Mockito.mock(SchedulerManager.class);

        final RTEImgResources imgResources = Mockito.mock(RTEImgResources.class);
        final ImageResource img = Mockito.mock(ImageResource.class);
        Mockito.when(img.getName()).thenReturn("save");
        Mockito.when(imgResources.save()).thenReturn(img);
        Mockito.when(imgResources.fontheight()).thenReturn(img);
        Mockito.when(imgResources.charfontname()).thenReturn(img);
        timer = Mockito.mock(TimerWrapper.class);
        view = Mockito.mock(RTESavingEditorView.class);

        presenter = new RTESavingEditorPresenter(i18n, null, imgResources, null, null, null, null, null, null, null,
                deferredCommandWrapper, true, stateManager, timer);
        presenter.init(view);
        saveListener = new MockedListener<String>();
        cancelListener = new MockedListener0();
    }

    private String editAndChangeHistoryToken() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        final String newToken = "somegroup";
        final boolean change = presenter.beforeTokenChange();
        assertFalse(change);
        verifyAskConfirmationCalled(Mockito.times(1));
        return newToken;
    }

    @Test
    public void historyChangeWithoutPendingMustAccept() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        final boolean change = presenter.beforeTokenChange();
        assertTrue(change);
        verifyAskConfirmationCalled(Mockito.never());
        Mockito.verify(deferredCommandWrapper, Mockito.times(1)).addCommand((ScheduledCommand) Mockito.anyObject());
    }

    @Test
    public void historyChangeWithPendingSaveAndCancelMustPosponeIt() {
        editAndChangeHistoryToken();
        presenter.onCancelConfirmed();
        assertTrue(saveListener.isNotCalled());
        Mockito.verify(stateManager, Mockito.times(1)).resumeTokenChange();
    }

    @Test
    public void historyChangeWithPendingSaveMustPosponeIt() {
        editAndChangeHistoryToken();
        presenter.onDoSaveAndClose();
        presenter.onSavedSuccessful();
        assertTrue(saveListener.isCalledOnce());
        Mockito.verify(stateManager, Mockito.times(1)).resumeTokenChange();
    }

    @Test
    public void initialEdit() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        checkSaveBtnDisabled();
        assertTrue(cancelListener.isNotCalled());
    }

    @Test
    public void initialEditWithCancel() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onCancel();
        assertTrue(saveListener.isNotCalled());
        assertTrue(cancelListener.isCalledOnce());
    }

    @Test
    public void initialEditWithEditionAndSave() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        presenter.onDoSave();
        presenter.onSavedSuccessful();
        checkSaveBtnDisabled();
        assertTrue(cancelListener.isNotCalled());
    }

    @Test
    public void testSavePendingAndCancel() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        presenter.onCancel();
        presenter.onCancelConfirmed();
        assertTrue(saveListener.isNotCalled());
        assertTrue(cancelListener.isCalledOnce());
    }

    @Test
    public void testSavePendingAndSaveFails() {
        presenter.edit("Text to edit", saveListener, cancelListener);
        presenter.onEdit();
        presenter.onDoSave();
        presenter.onSaveFailed();
        presenter.onDoSave();
        Mockito.verify(timer, Mockito.times(1)).schedule(RTESavingEditorPresenter.AUTOSAVE_IN_MILLIS);
        Mockito.verify(timer, Mockito.times(1)).schedule(RTESavingEditorPresenter.AUTOSAVE_AFTER_FAIL_MILLS);
        assertTrue(saveListener.isCalled(2));
    }

    private void verifyAskConfirmationCalled(final VerificationMode mode) {
        Mockito.verify(view, mode).askConfirmation(Mockito.anyString(), Mockito.anyString(),
                (Listener0) Mockito.anyObject(), (Listener0) Mockito.anyObject());
    }

}
