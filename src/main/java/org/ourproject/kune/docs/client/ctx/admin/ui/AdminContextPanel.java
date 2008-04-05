
package org.ourproject.kune.docs.client.ctx.admin.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.docs.client.actions.DocsEvents;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextView;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.IconLabel;
import org.ourproject.kune.platf.client.ui.stacks.IndexedStackPanelWithSubItems;
import org.ourproject.kune.platf.client.ui.stacks.StackSubItemAction;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.i18n.LanguageSelectorComponent;
import org.ourproject.kune.workspace.client.i18n.ui.LanguageSelectorPanel;
import org.ourproject.kune.workspace.client.ui.ctx.admin.AccessListsPanel;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.data.Record;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class AdminContextPanel extends VerticalPanel implements AdminContextView {
    private static final Images IMG = Images.App.getInstance();
    private static final String TAGS_ITEM = Kune.I18N.t("Tags");
    private static final String LANGUAGE_ITEM = Kune.I18N.t("Language");
    private static final String PERMS_ITEM = Kune.I18N.t("Permissions");
    private static final String AUTHORS_ITEM = Kune.I18N.t("Authors");
    private static final String PUBLICATION_ITEM = Kune.I18N.t("Publication");

    private static final int FORMS_WIDTH = 145;
    private final IndexedStackPanelWithSubItems options;
    private final AdminContextPresenter presenter;

    private AccessListsPanel accessListsPanel;
    private DateField publishedOnField;
    private TextArea tagsField;
    private VerticalPanel tagsComponent;
    private VerticalPanel publishedOnComponent;
    private LanguageSelectorComponent langPresenter;
    private VerticalPanel authorsComponent;
    private VerticalPanel langComponent;
    private IconLabel addAuthorLabel;

    public AdminContextPanel(final AdminContextPresenter presenter) {
        this.presenter = presenter;
        options = new IndexedStackPanelWithSubItems();
        options.addStyleName("kune-AdminContextPanel");

        add(options);
        setCellWidth(options, "100%");
        setWidth("100%");
    }

    public void reset() {
        options.clear();
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
        if (accessListsPanel == null) {
            accessListsPanel = new AccessListsPanel();
        }
        if (!options.containsItem(PERMS_ITEM)) {
            addComponent(PERMS_ITEM, Kune.I18N.t("Who can admin/edit/view this work"), accessListsPanel);
        }
        accessListsPanel.setAccessLists(accessLists);
    }

    public void setAuthors(final List<UserSimpleDTO> authors) {
        if (authorsComponent == null) {
            authorsComponent = new VerticalPanel();
            addAuthorLabel = new IconLabel(IMG.addGreen(), Kune.I18N.t("Add author"));
            addAuthorLabel.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    presenter.doAction(DocsEvents.ADD_AUTHOR, null);
                }
            });
        } else {
            if (options.containsItem(AUTHORS_ITEM)) {
                options.removeStackItem(AUTHORS_ITEM);
            }
            authorsComponent.clear();
        }
        if (!options.containsItem(AUTHORS_ITEM)) {
            addComponent(AUTHORS_ITEM, Kune.I18N.t("Authors of this work"), authorsComponent);
        }
        for (Iterator<UserSimpleDTO> iterator = authors.iterator(); iterator.hasNext();) {
            UserSimpleDTO author = iterator.next();
            StackSubItemAction[] authorActions = { new StackSubItemAction(IMG.del(), Kune.I18N.t("Remove author"),
                    DocsEvents.REMOVE_AUTHOR) };
            options.addStackSubItem(AUTHORS_ITEM, IMG.personDef(), author.getShortName(), author.getName(),
                    authorActions, presenter);
        }
        authorsComponent.add(addAuthorLabel);
    }

    public void setLanguage(final I18nLanguageDTO language) {
        if (langComponent == null) {
            langPresenter = WorkspaceFactory.createLanguageSelectorComponent();
            langComponent = new VerticalPanel();
            LanguageSelectorPanel view = (LanguageSelectorPanel) langPresenter.getView();
            view.setWidth("" + FORMS_WIDTH);
            langComponent.add(view);
            view.addChangeListener(new ComboBoxListenerAdapter() {
                public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                    presenter.doChangeLanguage(record.getAsString(LanguageSelectorPanel.LANG_ID));
                }
            });
        }
        if (!options.containsItem(LANGUAGE_ITEM)) {
            addComponent(LANGUAGE_ITEM, Kune.I18N.t("The language of this work"), langComponent);
        }
        langPresenter.setLanguage(language);
    }

    public void setPublishedOn(final Date publishedOn) {
        if (publishedOnField == null) {
            publishedOnComponent = createPublicationComponent();
        }
        if (!options.containsItem(PUBLICATION_ITEM)) {
            addComponent(PUBLICATION_ITEM, Kune.I18N.t("Date of publication of this work"), publishedOnComponent);
        }
        publishedOnField.setValue(publishedOn);
    }

    public void setTags(final String tags) {
        if (tagsComponent == null) {
            tagsComponent = createTagsComponent();
        }
        if (!options.containsItem(TAGS_ITEM)) {
            addComponent(TAGS_ITEM, Kune.I18N.t("Keywords or terms associated with this work"), tagsComponent);
        }
        tagsField.setValue(tags);
    }

    public void removeAccessListComponent() {
        if (options.containsItem(PERMS_ITEM)) {
            removeComponent(PERMS_ITEM);
        }
    }

    public void removeAuthorsComponent() {
        if (options.containsItem(AUTHORS_ITEM)) {
            removeComponent(AUTHORS_ITEM);
        }
    }

    public void removeLangComponent() {
        if (options.containsItem(LANGUAGE_ITEM)) {
            removeComponent(LANGUAGE_ITEM);
        }
    }

    public void removePublishedOnComponent() {
        if (options.containsItem(PUBLICATION_ITEM)) {
            removeComponent(PUBLICATION_ITEM);
        }
    }

    public void removeTagsComponent() {
        if (options.containsItem(TAGS_ITEM)) {
            removeComponent(TAGS_ITEM);
        }
    }

    private void addComponent(final String header, final String headerTitle, final VerticalPanel panel) {
        panel.addStyleName("kune-AdminContextPanel-inner-wrap");
        VerticalPanel vp = options.addStackItem(header, headerTitle, false);
        vp.setStyleName("kune-AdminContextPanel-inner");
        vp.add(panel);
        vp.setCellWidth(panel, "100%");
        vp.setWidth("100%");
    }

    private void removeComponent(final String header) {
        options.removeStackItem(header);
    }

    private VerticalPanel createPublicationComponent() {
        FormPanel form = createDefaultForm();

        publishedOnField = new DateField();
        publishedOnField.setWidth("140");
        publishedOnField.setFormat("Y-m-d");

        publishedOnField.addListener(new FieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                presenter.setPublishedOn((Date) newVal);
            }
        });

        form.add(publishedOnField);

        VerticalPanel vp = new VerticalPanel();
        vp.add(form);
        return vp;
    }

    private FormPanel createDefaultForm() {
        FormPanel form = new FormPanel();
        form.setHideLabels(true);
        form.setWidth(FORMS_WIDTH);
        form.setBorder(false);
        return form;
    }

    private VerticalPanel createTagsComponent() {
        FormPanel form = createDefaultForm();
        tagsField = new TextArea();
        tagsField.setWidth("" + FORMS_WIDTH);
        tagsField.setHeight("3em");
        tagsField.addListener(new FieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                presenter.setTags((String) newVal);
            }
        });

        form.add(tagsField);
        VerticalPanel vp = new VerticalPanel();
        vp.add(form);
        return vp;
    }

}
