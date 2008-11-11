/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.docs.client.ctx.admin;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.ContextPropertyPanel;
import org.ourproject.kune.workspace.client.i18n.LanguageSelector;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorPanel;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class DocContextPropEditorPanel extends ScrollPanel implements DocContextPropEditorView {

    private static final int FORMS_WIDTH = 150;
    public static final String TAGS_PROP = "k-dcpep-tagsp-dis";
    public static final String LANG_PROP = "k-dcpep-tagsp-dis";

    private final DocContextPropEditorPresenter presenter;
    private final WorkspaceSkeleton ws;
    private final I18nTranslationService i18n;
    private TextArea tagsField;
    private ContextPropertyPanel tagsComponent;
    private final Provider<LanguageSelector> langSelectorProv;
    private ContextPropertyPanel langComponent;
    private final VerticalPanel vp;

    public DocContextPropEditorPanel(final DocContextPropEditorPresenter presenter, final I18nTranslationService i18n,
            WorkspaceSkeleton ws, Provider<LanguageSelector> langSelectorProv) {
        this.i18n = i18n;
        this.ws = ws;
        this.presenter = presenter;
        this.langSelectorProv = langSelectorProv;
        super.addStyleName("kune-Margin-Medium-trbl");
        vp = new VerticalPanel();
        super.add(vp);
        // authors_item = i18n.t("Authors");
        // publication_item = i18n.t("Publication");
        // perms_item = i18n.t("Permissions");
        // language_item = i18n.t("Language");
        // tags_item = i18n.t("Tags");
    }

    public void attach() {
        if (!super.isAttached()) {
            ws.getEntityWorkspace().setContext(this);
        }
    }

    public void detach() {
        if (super.isAttached()) {
            super.removeFromParent();
        }
    }

    public void removeAccessListComponent() {
        // if (options.containsItem(perms_item)) {
        // removeComponent(perms_item);
        // }
    }

    public void removeAuthorsComponent() {
        // if (options.containsItem(authors_item)) {
        // removeComponent(authors_item);
        // }
    }

    public void removeLangComponent() {
        if (langComponent != null) {
            langComponent.removeFromParent();
        }
    }

    public void removePublishedOnComponent() {
        // if (options.containsItem(publication_item)) {
        // removeComponent(publication_item);
        // }
    }

    public void removeTagsComponent() {
        if (tagsComponent != null) {
            tagsComponent.removeFromParent();
        }
    }

    public void reset() {
        tagsField.reset();
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
        // if (accessListsPanel == null) {
        // accessListsPanel = new AccessListsPanel(i18n);
        // }
        // if (!options.containsItem(perms_item)) {
        // addComponent(perms_item, i18n.t("Who can admin/edit/view this work"),
        // accessListsPanel);
        // }
        // accessListsPanel.setAccessLists(accessLists);
    }

    public void setAuthors(final List<UserSimpleDTO> authors) {
        // if (authorsComponent == null) {
        // authorsComponent = new VerticalPanel();
        // addAuthorLabel = new IconLabel(IMG.addGreen(), i18n.t("Add author"));
        // addAuthorLabel.addClickListener(new ClickListener() {
        // public void onClick(final Widget sender) {
        // presenter.doAction(DocsEvents.ADD_AUTHOR, null);
        // }
        // });
        // } else {
        // if (options.containsItem(authors_item)) {
        // options.removeStackItem(authors_item);
        // }
        // authorsComponent.clear();
        // }
        // if (!options.containsItem(authors_item)) {
        // addComponent(authors_item, i18n.t("Authors of this work"),
        // authorsComponent);
        // }
        // for (final Iterator<UserSimpleDTO> iterator = authors.iterator();
        // iterator.hasNext();) {
        // final UserSimpleDTO author = iterator.next();
        // final StackSubItemAction[] authorActions = { new
        // StackSubItemAction(IMG.del(), i18n.t("Remove author"),
        // DocsEvents.REMOVE_AUTHOR) };
        // options.addStackSubItem(authors_item, IMG.personDef(),
        // author.getShortName(), author.getName(),
        // authorActions, presenter);
        // }
        // authorsComponent.add(addAuthorLabel);
    }

    public void setLanguage(final I18nLanguageDTO language) {
        if (langComponent == null) {
            final LanguageSelectorPanel view = (LanguageSelectorPanel) langSelectorProv.get().getView();
            view.setWidth(FORMS_WIDTH);
            langComponent = new ContextPropertyPanel(i18n.t("What language is this in?"), null, true, LANG_PROP, view);
            view.addChangeListener(new ComboBoxListenerAdapter() {
                @Override
                public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                    presenter.doChangeLanguage(record.getAsString(LanguageSelectorPanel.LANG_ID));
                }
            });
            // angComponent.addStyleName("kune-Margin-Medium-t");
            addComponent(langComponent);
        }
        langSelectorProv.get().setLanguage(language);
    }

    public void setPublishedOn(final Date publishedOn) {
        // if (publishedOnField == null) {
        // publishedOnComponent = createPublicationComponent();
        // }
        // if (!options.containsItem(publication_item)) {
        // addComponent(publication_item, i18n.t("Date of publication of this
        // work"), publishedOnComponent);
        // }
        // // publishedOnField.setValue(publishedOn);
    }

    public void setTags(final String tags) {
        if (tagsComponent == null) {
            tagsComponent = createTagsComponent();
            addComponent(tagsComponent);
        }
        tagsField.setValue(tags);
    }

    private void addComponent(ContextPropertyPanel prop) {
        vp.add(prop);
    }

    private FormPanel createDefaultForm() {
        final FormPanel form = new FormPanel();
        form.setHideLabels(true);
        form.setWidth(FORMS_WIDTH);
        form.setBorder(false);
        return form;
    }

    // private VerticalPanel createPublicationComponent() {
    // // final FormPanel form = createDefaultForm();
    // //
    // // publishedOnField = new DateField();
    // // publishedOnField.setWidth("140");
    // // publishedOnField.setFormat("Y-m-d");
    // //
    // // publishedOnField.addListener(new FieldListenerAdapter() {
    // // public void onChange(final Field field, final Object newVal, final
    // // Object oldVal) {
    // // presenter.setPublishedOn((Date) newVal);
    // // }
    // // });
    // //
    // // form.add(publishedOnField);
    // //
    // // final VerticalPanel vp = new VerticalPanel();
    // // vp.add(form);
    // // return vp;
    // //// }
    //
    private ContextPropertyPanel createTagsComponent() {
        final FormPanel form = createDefaultForm();
        tagsField = new TextArea();
        tagsField.setWidth(FORMS_WIDTH - 10);
        tagsField.setHeight("3em");
        tagsField.addListener(new FieldListenerAdapter() {
            @Override
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                presenter.setTags((String) newVal);
            }
        });
        form.add(tagsField);
        ContextPropertyPanel propertyPanel = new ContextPropertyPanel(i18n.t("Tags"),
                i18n.t("Keywords or terms associated with this work"), true, TAGS_PROP, form);
        return propertyPanel;
    }

    // private void removeComponent(final String header) {
    // options.removeStackItem(header);
    // }

}
