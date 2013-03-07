package net.auroris.ColorPicker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ColorPickerSample implements EntryPoint, ClickHandler
{
    private static class ColorPickerDialog extends DialogBox
    {
        private ColorPicker picker;

        public ColorPickerDialog()
        {
            setText("Choose a color");

            setWidth("435px");
            setHeight("350px");

            // Define the panels
            VerticalPanel panel = new VerticalPanel();
            FlowPanel okcancel = new FlowPanel();
            picker = new ColorPicker();

            // Define the buttons
            Button ok = new Button("Ok");   // ok button
            ok.addClickHandler(new ClickHandler()
            {
                public void onClick(ClickEvent event)
                {
                    Window.alert("You chose " + picker.getHexColor());
                    ColorPickerDialog.this.hide();
                }
            });

            Button cancel = new Button("Cancel");   // cancel button
            cancel.addClickHandler(new ClickHandler()
            {
                public void onClick(ClickEvent event)
                {
                    ColorPickerDialog.this.hide();
                }
            });
            okcancel.add(ok);
            okcancel.add(cancel);

            // Put it together
            panel.add(picker);
            panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
            panel.add(okcancel);

            setWidget(panel);
        }
    }

    private ColorPickerDialog pickerDialog;

    public void onClick(ClickEvent event) {
        // Show the dialog box.
        pickerDialog.show();
    }

    public void onModuleLoad()
    {
        pickerDialog = new ColorPickerDialog();

        Button b = new Button("Click me");
        b.addClickHandler(this);

        RootPanel.get().add(b);
    }
}
