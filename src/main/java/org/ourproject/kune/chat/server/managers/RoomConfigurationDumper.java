package org.ourproject.kune.chat.server.managers;

import java.util.Iterator;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;

class RoomConfigurationDumper {

    public static void showConfiguration(final Form form) {
	for (Iterator fields = form.getFields(); fields.hasNext();) {
	    FormField formField = (FormField) fields.next();
	    log("Field label: " + formField.getLabel());
	    log("Field variable: " + formField.getVariable());
	    log("Field type: " + formField.getType());
	    log("Field desc: " + formField.getDescription());
	    log("Field options: " + log(formField.getOptions()));
	    log("Field values: " + log(formField.getValues()));
	    log("Field end -------------------");
	}
    }

    private static String log(final Iterator<?> options) {
	String s = "";
	while (options.hasNext()) {
	    s += "| " + options.next().toString();
	}
	return s;
    }

    private static void log(final String string) {
	System.out.println(string);
    }

    void configure2(final MultiUserChat muc) throws XMPPException {
	Form form = muc.getConfigurationForm().createAnswerForm();
	form.setAnswer("muc#roomconfig_passwordprotectedroom", false);
	// form.setAnswer("muc#roomconfig_roomname",
	// mucRoomDialog.getRoomName());

	form.setAnswer("muc#roomconfig_persistentroom", true);

	// List owners = new ArrayList();
	// owners.add(SparkManager.getSessionManager().getBareAddress());
	// form.setAnswer("muc#roomconfig_roomowners", owners);

	// new DataFormDialog(groupChat, form);
	muc.sendConfigurationForm(form);

    }
}
