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
package cc.kune.core.client.state.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.TokenMatcher;
import cc.kune.core.shared.dto.ReservedWordsRegistryDTO;

public class TokenMatcherTest {

  private static final String DEF_SITE_TOKEN = "";
  private static final String GROUP_TOKEN = "site.docs.1";
  private static final String GROUP_TOKEN_ONLY_PROJECT = "site";
  private static final String GROUP_TOKEN_ONLY_PROJECT_AND_TOOL = "site.docs";
  private static final String REDIRECT_LINK = "example.com/w+jsdKixyHhZA";
  private static final String SIGNIN_TOKEN = "signin";
  private static final String SIGNIN_TOKEN_WITH_REDIRECT = SIGNIN_TOKEN + "(" + REDIRECT_LINK + ")";
  private static final String SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW = SIGNIN_TOKEN + "("
      + SiteTokens.PREVIEW + "(" + REDIRECT_LINK + "))";
  private static final String WAVE_TOKEN_SAMPLE1 = "example.com/w+abcd";
  private static final String WAVE_TOKEN_SAMPLE2 = "example.com/w+abcd/~/conv+root";
  private static final String WAVE_TOKEN_SAMPLE3 = "example.com/w+abcd/~/conv+root/b+45kg";
  private TokenMatcher tokenMatcher;

  @Before
  public void before() {
    final ReservedWordsRegistryDTO reservedWords = new ReservedWordsRegistryDTO();
    reservedWords.add(SIGNIN_TOKEN);
    tokenMatcher = new TokenMatcher(reservedWords);
    tokenMatcher.init(JavaWaverefEncoder.INSTANCE);
  }

  private void dontMatchGroupToken(final String token) {
    assertFalse("Expected '" + token + "' dont match isGroup", tokenMatcher.isGroupToken(token));
  }

  @Test
  public void dontMatchRedirect() {
    dontMatchWaveToken(SIGNIN_TOKEN_WITH_REDIRECT);
    dontMatchWaveToken("!" + SIGNIN_TOKEN_WITH_REDIRECT);
    dontMatchGroupToken(SIGNIN_TOKEN_WITH_REDIRECT);
    dontMatchGroupToken("!" + SIGNIN_TOKEN_WITH_REDIRECT);
    assertFalse(tokenMatcher.hasRedirect(SIGNIN_TOKEN));
    assertFalse(tokenMatcher.hasRedirect("!" + SIGNIN_TOKEN));
  }

  private void dontMatchWaveToken(final String token) {
    assertFalse("Expected '" + token + "' dont match isWaveToken", tokenMatcher.isWaveToken(token));
  }

  @Test
  public void testInbox() {
    assertTrue(tokenMatcher.isInboxToken(SiteTokens.WAVE_INBOX));
    assertTrue(tokenMatcher.isInboxToken("!" + SiteTokens.WAVE_INBOX));
  }

  private void matchGroupToken(final String token) {
    assertTrue("Expected '" + token + "' match isGroup", tokenMatcher.isGroupToken(token));
  }

  private void matchWaveToken(final String token) {
    assertTrue("Expected '" + token + "' match isWaveToken", tokenMatcher.isWaveToken(token));
  }

  @Test
  public void matchWaveToken2() {
    matchWaveToken(WAVE_TOKEN_SAMPLE2);
    matchWaveToken("!" + WAVE_TOKEN_SAMPLE2);
    dontMatchGroupToken(WAVE_TOKEN_SAMPLE2);
    dontMatchGroupToken("!" + WAVE_TOKEN_SAMPLE2);
  }

  @Test
  public void matchWaveToken3() {
    matchWaveToken(WAVE_TOKEN_SAMPLE3);
    matchWaveToken("!" + WAVE_TOKEN_SAMPLE3);
    dontMatchGroupToken(WAVE_TOKEN_SAMPLE3);
    dontMatchGroupToken("!" + WAVE_TOKEN_SAMPLE3);
  }

  @Test
  public void shouldDontMatchNull() {
    dontMatchWaveToken(null);
    dontMatchWaveToken("");
    dontMatchWaveToken("!");
    dontMatchGroupToken(null);
    dontMatchGroupToken("");
    dontMatchGroupToken("!");
  }

  @Test
  public void shouldExtractRedirect() {
    assertTrue(tokenMatcher.hasRedirect(SIGNIN_TOKEN_WITH_REDIRECT));
    assertEquals(SIGNIN_TOKEN, tokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT).getLeft());
    assertEquals(SIGNIN_TOKEN, tokenMatcher.getRedirect("!" + SIGNIN_TOKEN_WITH_REDIRECT).getLeft());
    assertEquals(REDIRECT_LINK, tokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT).getRight());
    assertEquals(REDIRECT_LINK, tokenMatcher.getRedirect("!" + SIGNIN_TOKEN_WITH_REDIRECT).getRight());
  }

  @Test
  public void shouldExtractRedirectInSignPreview() {
    assertTrue(tokenMatcher.hasRedirect(SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW));
    assertEquals(
        "Expected " + SIGNIN_TOKEN + " but: "
            + tokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW).getLeft(), SIGNIN_TOKEN,
        tokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW).getLeft());
    // assertEquals(REDIRECT_LINK,
    // tokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT).getRight());
  }

  @Test
  public void shoulMatchCompleteToken() {
    matchGroupToken(GROUP_TOKEN);
    matchGroupToken("!" + GROUP_TOKEN);
    dontMatchWaveToken(GROUP_TOKEN);
    dontMatchWaveToken("!" + GROUP_TOKEN);
  }

  @Test
  public void shoulMatchGroupToken() {
    matchGroupToken(GROUP_TOKEN_ONLY_PROJECT);
    dontMatchWaveToken(GROUP_TOKEN_ONLY_PROJECT);
  }

  @Test
  public void shoulMatchGroupToolToken() {
    matchGroupToken("site.docs");
    matchGroupToken(GROUP_TOKEN_ONLY_PROJECT_AND_TOOL);
    dontMatchWaveToken(GROUP_TOKEN_ONLY_PROJECT_AND_TOOL);
  }

  @Test
  public void testDefSiteTokenDontMatch() {
    dontMatchWaveToken(DEF_SITE_TOKEN);
    dontMatchGroupToken(DEF_SITE_TOKEN);
  }

  @Test
  public void testMatchWaveToken1() {
    matchWaveToken(WAVE_TOKEN_SAMPLE1);
    dontMatchGroupToken(WAVE_TOKEN_SAMPLE1);
  }

  @Test
  public void testOtherSiteTokensDontMatch() {
    dontMatchWaveToken(SIGNIN_TOKEN);
    dontMatchGroupToken(SIGNIN_TOKEN);
  }
}
