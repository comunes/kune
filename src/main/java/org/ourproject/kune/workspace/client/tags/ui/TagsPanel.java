
package org.ourproject.kune.workspace.client.tags.ui;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.TagResultDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.DropDownPanel;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.workspace.client.tags.TagsPresenter;
import org.ourproject.kune.workspace.client.tags.TagsView;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class TagsPanel extends DropDownPanel implements TagsView {

    private final FlowPanel flowPanel;
    private final TagsPresenter presenter;
    private final Label noTagsLabel;

    public TagsPanel(final TagsPresenter presenter) {
        super(Kune.I18N.t("Tags"), true);
        setHeaderTitle(Kune.I18N.t("Keywords or terms associated with this group"));
        this.presenter = presenter;
        addStyleName("kune-Margin-Medium-t");
        flowPanel = new FlowPanel();
        VerticalPanel vp = new VerticalPanel();
        vp.add(flowPanel);
        vp.setWidth("100%");
        vp.setCellWidth(flowPanel, "100%");
        super.setContent(vp);
        noTagsLabel = new Label(Kune.I18N.t("The contents of this group don't have any tag"));
    }

    public void setTags(final List<TagResultDTO> groupTags) {
        flowPanel.clear();
        if (groupTags.size() == 0) {
            flowPanel.add(noTagsLabel);
        } else {
            for (Iterator<TagResultDTO> iterator = groupTags.iterator(); iterator.hasNext();) {
                final TagResultDTO tagResult = iterator.next();
                Label label = new Label(tagResult.getName());
                // i18n pluralization
                if (tagResult.getCount().intValue() > 1) {
                    KuneUiUtils.setQuickTip(label, Kune.I18N.t("[%d] items with this tag", tagResult.getCount()));
                } else {
                    KuneUiUtils.setQuickTip(label, Kune.I18N.t("[%d] item with this tag", tagResult.getCount()));
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
}
