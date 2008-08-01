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

package org.ourproject.kune.docs.client.ctx.admin.ui;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.docs.client.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextView;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.form.FormPanel;

public class AdminContextPanel extends VerticalPanel implements AdminContextView {
    private static final Images IMG = Images.App.getInstance();

    private static final int FORMS_WIDTH = 145;

    // private final IndexedStackPanelWithSubItems options;
    // private final AdminContextPresenter presenter;
    //
    // private AccessListsPanel accessListsPanel;
    // private DateField publishedOnField;
    // private TextArea tagsField;
    // private VerticalPanel tagsComponent;
    // private VerticalPanel publishedOnComponent;
    // private LanguageSelectorComponent langPresenter;
    // private VerticalPanel authorsComponent;
    // private VerticalPanel langComponent;
    // private IconLabel addAuthorLabel;
    // private final I18nTranslationService i18n;
    // private final String authors_item;
    // private final String publication_item;
    // private final String perms_item;
    // private final String language_item;
    // private final String tags_item;

    public AdminContextPanel(final AdminContextPresenter presenter, final I18nTranslationService i18n) {
	// this.presenter = presenter;
	// this.i18n = i18n;
	// //options = new IndexedStackPanelWithSubItems();
	// //options.addStyleName("kune-AdminContextPanel");
	//
	// //add(options);
	// //setCellWidth(options, "100%");
	// setWidth("100%");
	// authors_item = i18n.t("Authors");
	// publication_item = i18n.t("Publication");
	// perms_item = i18n.t("Permissions");
	// language_item = i18n.t("Language");
	// tags_item = i18n.t("Tags");
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
	// if (options.containsItem(language_item)) {
	// removeComponent(language_item);
	// }
    }

    public void removePublishedOnComponent() {
	// if (options.containsItem(publication_item)) {
	// removeComponent(publication_item);
	// }
    }

    public void removeTagsComponent() {
	// if (options.containsItem(tags_item)) {
	// removeComponent(tags_item);
	// }
    }

    public void reset() {
	// options.clear();
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
	// if (langComponent == null) {
	// langPresenter = WorkspaceFactory.createLanguageSelectorComponent();
	// langComponent = new VerticalPanel();
	// final LanguageSelectorPanel view = (LanguageSelectorPanel)
	// langPresenter.getView();
	// view.setWidth("" + FORMS_WIDTH);
	// langComponent.add(view);
	// view.addChangeListener(new ComboBoxListenerAdapter() {
	// public void onSelect(final ComboBox comboBox, final Record record,
	// final int index) {
	// presenter.doChangeLanguage(record.getAsString(LanguageSelectorPanel.LANG_ID));
	// }
	// });
	// }
	// if (!options.containsItem(language_item)) {
	// addComponent(language_item, i18n.t("The language of this work"),
	// langComponent);
	// }
	// langPresenter.setLanguage(language);
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
	// if (tagsComponent == null) {
	// tagsComponent = createTagsComponent();
	// }
	// if (!options.containsItem(tags_item)) {
	// addComponent(tags_item, i18n.t("Keywords or terms associated with
	// this work"), tagsComponent);
	// }
	// tagsField.setValue(tags);
    }

    private void addComponent(final String header, final String headerTitle, final VerticalPanel panel) {
	// panel.addStyleName("kune-AdminContextPanel-inner-wrap");
	// final VerticalPanel vp = options.addStackItem(header, headerTitle,
	// false);
	// vp.setStyleName("kune-AdminContextPanel-inner");
	// vp.add(panel);
	// vp.setCellWidth(panel, "100%");
	// vp.setWidth("100%");
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
    // private VerticalPanel createTagsComponent() {
    // final FormPanel form = createDefaultForm();
    // tagsField = new TextArea();
    // tagsField.setWidth("" + FORMS_WIDTH);
    // tagsField.setHeight("3em");
    // tagsField.addListener(new FieldListenerAdapter() {
    // public void onChange(final Field field, final Object newVal, final
    // Object oldVal) {
    // presenter.setTags((String) newVal);
    // }
    // });
    //
    // form.add(tagsField);
    // final VerticalPanel vp = new VerticalPanel();
    // vp.add(form);
    // return vp;
    // }

    private void removeComponent(final String header) {
	// options.removeStackItem(header);
    }

}
