/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.wiki.server;

import static cc.kune.wiki.shared.WikiToolConstants.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.waveprotocol.wave.model.wave.InvalidParticipantAddress;
import org.waveprotocol.wave.model.wave.ParticipantId;

import cc.kune.wave.server.kspecific.ParticipantUtils;

public class WikiServerToolTest { // extends PersistenceTest {

  private WikiServerTool serverTool;

  @Before
  public void before() throws InvalidParticipantAddress {
    final ParticipantUtils partUtils = Mockito.mock(ParticipantUtils.class);
    Mockito.when(partUtils.getPublicParticipantId()).thenReturn(new ParticipantId("example.org"));
    serverTool = new WikiServerTool(null, null, null, null, null, partUtils);
  }

  @Test
  public void testCreateContainerInCorrectContainer() {
    serverTool.checkTypesBeforeContainerCreation(TYPE_ROOT, TYPE_FOLDER);
    serverTool.checkTypesBeforeContainerCreation(TYPE_FOLDER, TYPE_FOLDER);
  }

  @Test
  public void testCreateContentInCorrectContainer() {
    serverTool.checkTypesBeforeContentCreation(TYPE_ROOT, TYPE_WIKIPAGE);
    serverTool.checkTypesBeforeContentCreation(TYPE_ROOT, TYPE_UPLOADEDFILE);
    serverTool.checkTypesBeforeContentCreation(TYPE_FOLDER, TYPE_WIKIPAGE);
    serverTool.checkTypesBeforeContentCreation(TYPE_FOLDER, TYPE_UPLOADEDFILE);
  }

}
