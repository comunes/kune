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

package org.ourproject.kune.server.jcr.util;

import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.core.TransientRepository;

public class CreateRepository {
	public static void main(String[] args) throws Exception {
		Repository repository = new TransientRepository();
		Session session = repository.login(new SimpleCredentials("username",
				"password".toCharArray()));
		try {
			Node rootNode = session.getRootNode();
			Node docsNode = createDocumentsNode(rootNode);
			initRootDocument(docsNode);
			session.save();
		} finally {
			session.logout();
		}
	}

	private static Node createDocumentsNode(Node rootNode)
			throws RepositoryException, ItemExistsException,
			PathNotFoundException, VersionException,
			ConstraintViolationException, LockException {
		Node docsNode;
		if (!rootNode.hasNode("documents")) {
			System.out.println("create documents node!");
			docsNode = rootNode.addNode("documents");
		} else {
			System.out.println("repository already has documents!");
			docsNode = rootNode.getNode("documents");
		}
		return docsNode;
	}

	private static final String content = "<p>Esto no es latín, pero es la página por defecto guardada en el repositorio</p><p><table style='text-align: left; width: 100%;' border='1' cellpadding='1' cellspacing='1'> <tbody> <tr> <td align='right' valign='middle'>(0,0)</td> <td align='right' valign='middle'>(0,1)</td> <td align='right' valign='middle'>(0,2)</td> </tr> <tr> <td align='right' valign='middle'>(1,0)</td> <td align='right' valign='middle'>(1,1)</td> <td align='right' valign='middle'>(1,2)</td> </tr> <tr> <td align='right' valign='middle'>(2,0)</td> <td align='right' valign='middle'>(2,1)</td> <td align='right' valign='middle'>(2,2)</td> </tr> </tbody> </table></p>";
	private static void initRootDocument(Node docsNode)
			throws RepositoryException {
		Node rootDocument;
		if (docsNode.hasNode("root")) {
			System.out.println("repository alread has root document");
			rootDocument = docsNode.getNode("root");
		} else {
			System.out.println("creating root document");
			rootDocument = docsNode.addNode("root");
			rootDocument.addMixin("mix:referenceable");
			rootDocument.addMixin("mix:versionable");
		}
		
		System.out.println("initializing root node");
		rootDocument.setProperty("title", "this is the root document");
		rootDocument.setProperty("content", content);
		rootDocument.setProperty("license_name", "this is the default licence");
	}
}
