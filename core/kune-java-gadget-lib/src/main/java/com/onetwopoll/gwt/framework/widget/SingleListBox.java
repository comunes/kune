package com.onetwopoll.gwt.framework.widget;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

public class SingleListBox extends ListBox implements HasValue<String> {

	public SingleListBox() {
		super(false);
		setVisibleItemCount(1);
		
		addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				ValueChangeEvent.fire(SingleListBox.this, getValue());
			}
		});
	}

	@Override
	public String getValue() {
		return getValue(getSelectedIndex());
	}

	@Override
	public void setValue(String text) {
		for(int i = 0; i<getItemCount(); i++) {
			if(text.equals(getValue(i))) setSelectedIndex(i);
		}		
	}

	@Override
	public void setValue(String value, boolean fireEvents) {
		if(fireEvents) ValueChangeEvent.fireIfNotEqual(this, getValue(), value);
		setValue(value);		
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String> handler) {
		return addHandler(handler, ValueChangeEvent.getType());
	}
	
}