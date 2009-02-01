/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.chat.server.managers;

import java.util.Iterator;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;

class RoomConfigurationDumper {

    public static void showConfiguration(final Form form) {
        for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
            FormField formField = fields.next();
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
