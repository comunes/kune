/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 *
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.client.ui.chat;

import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.client.Session;
import org.ourproject.kune.client.model.Event;
import org.ourproject.kune.client.rpc.XmppService;
import org.ourproject.kune.client.ui.desktop.SiteMessageDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

public class ConferenceRoomImpl implements ConferenceRoom {
    private ConferenceRoomDialog room = null;
    private int pollErrorCount;
    private Timer retryTimer;

    public void init(ConferenceRoomDialog room) {
        this.room = room;
        pollErrorCount = 0;
        retryTimer = new Timer() {
            public void run() {
                // FIXME: Memory problems
                doPoll();
            }
        };
        doPoll();

        // FIXME: Only for tests:
        XmppService.App.getInstance().testRemoteEvents(new AsyncCallback() {
            public void onSuccess(Object result) {
                //SiteMessageDialog.get().setMessageInfo("Success calling test events");
            }
            public void onFailure(Throwable exception) {
                if (!exception.toString().startsWith("com.google.gwt.user.client.rpc.InvocationException: {OK}", 0))
                    SiteMessageDialog.get().setMessageError("Error testing events: " + exception.toString());
            }});
    }

    public void doPoll() {
        XmppService.App.getInstance().getEvents(new AsyncCallback() {
            public void onSuccess(Object result) {
                processEvents(result);
            }
            public void onFailure(Throwable exception) {
                if (exception.toString().startsWith("com.google.gwt.user.client.rpc.InvocationException: {OK}", 0)) {
                    // http://blogs.webtide.com/gregw/2006/12/07/1165517549286.html
                    // «Because GWT RPC uses POSTS, the body of the request is consumed
                    // when the request is first handled and is not available when the
                    // request is retried. To handle this, the parsed contents of the
                    // POST are stored as a request attribute so they are available to
                    // retried requests without reparsing»
                    String result = exception.toString().substring(56);
                    processEvents(result);
                }
                else {
                    pollErrorCount++;
                    if (pollErrorCount > 5) {
                        String message = "Error getting events: " + exception.toString();
                        GWT.log(message, null);
                        SiteMessageDialog.get().setMessageError("Network error trying to contact server. " + message);
                        pollErrorCount = 0;
                        retryTimer.schedule(20000);
                    }
                    else {
                        retryTimer.schedule(3000);
                    }
                }
            }});
    }

    private void processEvents(Object result) {
        pollErrorCount = 0;
        //FIXME: move this down
        retryTimer.schedule(2000);
        SiteMessageDialog.get().setMessage("List: " + result);
        List events = (List) result;
        for (Iterator it = events.iterator(); it.hasNext();) {
            Event currentEvent = ((Event) it.next());
            String message = "Success in getting event: " +
            currentEvent.getType() +
            " time: " + currentEvent.getTimestamp().toLocaleString() +
            " of " + events.size() + " events";
            GWT.log(message, null);
            SiteMessageDialog.get().setMessageInfo(message);
        }
    }

    public void onSend(String sentence) {
        room.enableSendButton(false);
        room.clearInputArea();
        sentence = sentence.replaceAll("&", "&amp;");
        sentence = sentence.replaceAll("\"", "&quot;");
        sentence = sentence.replaceAll("<", "&lt;");
        sentence = sentence.replaceAll(">", "&gt;");
        sentence = sentence.replaceAll("\n", "<br>\n");
        room.addToConversation(Session.get().currentUser.getNickName(), new HTML(sentence));
    }

    public void onMessage(String nick, String sentence) {
        room.addToConversation(nick, new HTML(sentence));
    }

    public void onClose() {
        retryTimer.cancel();
    }

}
