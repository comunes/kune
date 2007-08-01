package org.ourproject.kune.sitebar.client.ui;

import org.ourproject.kune.sitebar.client.SiteBar;
import org.ourproject.kune.sitebar.client.Translate;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NewGroupPanel extends Composite {
    private NewGroupPresenter presenter;

    public NewGroupPanel(NewGroupPresenter presenter) {
        // Intialize
        VerticalPanel vp = new VerticalPanel();
        initWidget(vp);
        this.presenter = presenter;
        Translate t = SiteBar.getInstance().t;
        Grid fieldGrid = new Grid(5, 2);
        TextBox shortNameGroup = new TextBox();
        TextBox longNameGroup = new TextBox();
        TextArea publicDesc = new TextArea();
        VerticalPanel typesVP = new VerticalPanel();
        RadioButton typeOrg = new RadioButton("typeGroup", t.Organization());
        RadioButton typeCommunity = new RadioButton("typeGroup", t.Community());
        RadioButton typeProject = new RadioButton("typeGroup", t.Project());
        Button create = new Button(t.Create());
        Button cancel = new Button(t.Cancel());

        // Layout
        vp.add(fieldGrid);
        typesVP.add(typeOrg);
        typesVP.add(typeCommunity);
        typesVP.add(typeProject);
        fieldGrid.setWidget(0, 0, new Label(t.ShortNameGroup()));
        fieldGrid.setWidget(0, 1, new Label(t.LongNameGroup()));
        fieldGrid.setWidget(0, 2, new Label(t.PublicDescription()));
        fieldGrid.setWidget(0, 3, new Label(t.DefaultLicense()));
        fieldGrid.setWidget(0, 4, new Label(t.TypeOfGroup()));
        fieldGrid.setWidget(1, 0, shortNameGroup);
        fieldGrid.setWidget(1, 1, longNameGroup);
        fieldGrid.setWidget(1, 2, publicDesc);
        // fieldGrid.setWidget(1, 3, );
        fieldGrid.setWidget(1, 4, typesVP);

        // Set Properties
        create.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {

            }
        });

        cancel.addClickListener(new ClickListener() {
            public void onClick(Widget arg0) {
            }
        });

    }
}
