/*
 * Copyright (C) 2005 Stefan Strigler <steve@zeank.in-berlin.de>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.jabber.JabberHTTPBind;

import java.util.Enumeration;

/**
 * @author Stefan Strigler <steve@zeank.in-berlin.de> 
 */
public class Janitor implements Runnable {
	public static final int SLEEPMILLIS = 1000;

	private boolean keep_running = true;

	/*
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (this.keep_running) {
			for (Enumeration e = Session.getSessions(); e.hasMoreElements();) {
				Session sess = (Session) e.nextElement();

				// stop inactive sessions
				if (System.currentTimeMillis() - sess.getLastActive() > Session.MAX_INACTIVITY * 1000) {
					if (JHBServlet.DEBUG)
						System.err.println("Session timed out: " + sess.getSID());
					sess.terminate();
				}
			}
			try {
				Thread.sleep(SLEEPMILLIS);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	public void stop() {
		this.keep_running = false;
	}

}