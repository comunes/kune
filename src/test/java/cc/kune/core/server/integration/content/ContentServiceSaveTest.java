/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.integration.content;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import cc.kune.core.server.TestDomainHelper;
import cc.kune.core.server.integration.IntegrationTestHelper;
import cc.kune.core.shared.dto.StateContentDTO;

public class ContentServiceSaveTest extends ContentServiceIntegrationTest {

  private StateContentDTO defaultContent;

  @Before
  public void init() throws Exception {
    new IntegrationTestHelper(true, this);
    defaultContent = getSiteDefaultContent();
    doLogin();
  }

  @Ignore
  @Test
  public void testSaveAndRetrieve() throws Exception {
    final String text = "Some content";
    // final int version = defaultContent.getVersion();
    // contentService.save(getHash(), defaultContent.getStateToken(), text);
    // final StateContentDTO again = (StateContentDTO)
    // contentService.getContent(getHash(),
    // defaultContent.getStateToken());
    // assertEquals(defaultContent.getWaveRef(), again.getWaveRef());
    // assertEquals(version + 2, again.getVersion());
    // assertEquals(0, again.getRateByUsers().intValue());
    // Here we fetch the wave content (no the db content)
    // assertEquals(text, again.getContent());
    // assertEquals(new Double(0), again.getRate());
  }

  @Ignore
  @Test
  public void testSaveAndRetrieveBig() throws Exception {
    final String text = TestDomainHelper.createBigText();
    // final int version = defaultContent.getVersion();
    // contentService.save(getHash(), defaultContent.getStateToken(), text);
    // final StateContentDTO again = (StateContentDTO)
    // contentService.getContent(getHash(),
    // defaultContent.getStateToken());
    // assertEquals(defaultContent.getWaveRef(), again.getWaveRef());
    // assertEquals(version + 2, again.getVersion());
    // Here we fetch the wave content (no the db content)
    // assertEquals(text, again.getContent());
  }

}
