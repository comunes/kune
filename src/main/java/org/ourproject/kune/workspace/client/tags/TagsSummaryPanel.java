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

package org.ourproject.kune.workspace.client.tags;

import java.util.List;

import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TagsSummaryPanel extends DropDownPanel implements TagsSummaryView {

    private final FlowPanel flowPanel;
    private final TagsSummaryPresenter presenter;
    private final Label noTagsLabel;
    private final I18nTranslationService i18n;

    public TagsSummaryPanel(final TagsSummaryPresenter presenter, final I18nTranslationService i18n,
            final WorkspaceSkeleton ws) {
        super(i18n.t("Tags"), true);
        this.i18n = i18n;
        setHeaderTitle(i18n.t("Keywords or terms associated with this group"));
        this.presenter = presenter;
        flowPanel = new FlowPanel();
        final VerticalPanel vp = new VerticalPanel();
        noTagsLabel = new Label(i18n.t("The contents of this group don't have any tag"));
        vp.add(flowPanel);
        vp.setWidth("100%");
        vp.setCellWidth(flowPanel, "100%");
        super.setContent(vp);
        super.setBorderStylePrimaryName("k-dropdownouter-tags");
        addStyleName("kune-Margin-Medium-t");
        flowPanel.addStyleName("kune-Margin-Small-trbl");
        ws.addInSummary(this);
    }

    public void setTags(final List<TagResultDTO> groupTags) {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                flowPanel.clear();
                if (groupTags.size() == 0) {
                    flowPanel.add(noTagsLabel);
                } else {
                    for (final TagResultDTO tagResult : groupTags) {
                        final Label label = new Label(tagResult.getName());
                        // i18n pluralization
                        if (tagResult.getCount().intValue() > 1) {
                            KuneUiUtils.setQuickTip(label, i18n.t("[%d] items with this tag", tagResult.getCount()));
                        } else {
                            KuneUiUtils.setQuickTip(label, i18n.t("[%d] item with this tag", tagResult.getCount()));
                        }
                        label.addClickListener(new ClickListener() {
                            public void onClick(final Widget sender) {
                                presenter.doSearchTag(tagResult.getName());
                            }
                        });
                        label.addStyleName("kune-TagsPanel-tag");
                        flowPanel.add(label);
                    }
                }
            }
        });
    }
}
