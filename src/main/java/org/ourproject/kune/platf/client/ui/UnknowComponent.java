package org.ourproject.kune.platf.client.ui;

import org.ourproject.kune.platf.client.Component;
import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Label;

public class UnknowComponent extends Label implements View, Component {

    public static final UnknowComponent instance = new UnknowComponent();

    public UnknowComponent() {
	super("programming error: unknown component!! please contact kune_dev@ourproject.org");
    }

    public View getView() {
	return this;
    }

}
