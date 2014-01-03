/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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

// TODO: Auto-generated Javadoc
/**
 * The Class TokenMatcherTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class TokenMatcherTest {

  /** The Constant DEF_SITE_TOKEN. */
  private static final String DEF_SITE_TOKEN = "";

  /** The Constant GROUP_TOKEN. */
  private static final String GROUP_TOKEN = "site.docs.1";

  /** The Constant GROUP_TOKEN_ONLY_PROJECT. */
  private static final String GROUP_TOKEN_ONLY_PROJECT = "site";

  /** The Constant GROUP_TOKEN_ONLY_PROJECT_AND_TOOL. */
  private static final String GROUP_TOKEN_ONLY_PROJECT_AND_TOOL = "site.docs";

  /** The Constant REDIRECT_LINK. */
  private static final String REDIRECT_LINK = "example.com/w+jsdKixyHhZA";

  /** The Constant SIGNIN_TOKEN. */
  private static final String SIGNIN_TOKEN = "signin";

  /** The Constant SIGNIN_TOKEN_WITH_REDIRECT. */
  private static final String SIGNIN_TOKEN_WITH_REDIRECT = SIGNIN_TOKEN + "(" + REDIRECT_LINK + ")";

  /** The Constant SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW. */
  private static final String SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW = SIGNIN_TOKEN + "("
      + SiteTokens.PREVIEW + "(" + REDIRECT_LINK + "))";

  /** The Constant WAVE_TOKEN_SAMPLE1. */
  private static final String WAVE_TOKEN_SAMPLE1 = "example.com/w+abcd";

  /** The Constant WAVE_TOKEN_SAMPLE2. */
  private static final String WAVE_TOKEN_SAMPLE2 = "example.com/w+abcd/~/conv+root";

  /** The Constant WAVE_TOKEN_SAMPLE3. */
  private static final String WAVE_TOKEN_SAMPLE3 = "example.com/w+abcd/~/conv+root/b+45kg";

  /**
   * Before.
   */
  @Before
  public void before() {
    TokenMatcher.getReservedWords().add(SIGNIN_TOKEN);
    TokenMatcher.getReservedWords().add("kune");
    TokenMatcher.init(JavaWaverefEncoder.INSTANCE);
  }

  /**
   * Dont match group token.
   * 
   * @param token
   *          the token
   */
  private void dontMatchGroupToken(final String token) {
    assertFalse("Expected '" + token + "' dont match isGroup", TokenMatcher.isGroupToken(token));
  }

  /**
   * Dont match redirect.
   */
  @Test
  public void dontMatchRedirect() {
    dontMatchWaveToken(SIGNIN_TOKEN_WITH_REDIRECT);
    dontMatchWaveToken("!" + SIGNIN_TOKEN_WITH_REDIRECT);
    dontMatchGroupToken(SIGNIN_TOKEN_WITH_REDIRECT);
    dontMatchGroupToken("!" + SIGNIN_TOKEN_WITH_REDIRECT);
    assertFalse(TokenMatcher.hasRedirect(SIGNIN_TOKEN));
    assertFalse(TokenMatcher.hasRedirect("!" + SIGNIN_TOKEN));
  }

  /**
   * Dont match wave token.
   * 
   * @param token
   *          the token
   */
  private void dontMatchWaveToken(final String token) {
    assertFalse("Expected '" + token + "' dont match isWaveToken", TokenMatcher.isWaveToken(token));
  }

  /**
   * Match group token.
   * 
   * @param token
   *          the token
   */
  private void matchGroupToken(final String token) {
    assertTrue("Expected '" + token + "' match isGroup", TokenMatcher.isGroupToken(token));
  }

  /* If we use a reserved group name, will fail the matcher */
  @Test
  public void matchReservedGroups() {
    dontMatchGroupToken("kune");
  }

  /**
   * Match wave token.
   * 
   * @param token
   *          the token
   */
  private void matchWaveToken(final String token) {
    assertTrue("Expected '" + token + "' match isWaveToken", TokenMatcher.isWaveToken(token));
  }

  /**
   * Match wave token2.
   */
  @Test
  public void matchWaveToken2() {
    matchWaveToken(WAVE_TOKEN_SAMPLE2);
    matchWaveToken("!" + WAVE_TOKEN_SAMPLE2);
    dontMatchGroupToken(WAVE_TOKEN_SAMPLE2);
    dontMatchGroupToken("!" + WAVE_TOKEN_SAMPLE2);
  }

  /**
   * Match wave token3.
   */
  @Test
  public void matchWaveToken3() {
    matchWaveToken(WAVE_TOKEN_SAMPLE3);
    matchWaveToken("!" + WAVE_TOKEN_SAMPLE3);
    dontMatchGroupToken(WAVE_TOKEN_SAMPLE3);
    dontMatchGroupToken("!" + WAVE_TOKEN_SAMPLE3);
  }

  /**
   * Should dont match null.
   */
  @Test
  public void shouldDontMatchNull() {
    dontMatchWaveToken(null);
    dontMatchWaveToken("");
    dontMatchWaveToken("!");
    dontMatchGroupToken(null);
    dontMatchGroupToken("");
    dontMatchGroupToken("!");
  }

  /**
   * Should extract redirect.
   */
  @Test
  public void shouldExtractRedirect() {
    assertTrue(TokenMatcher.hasRedirect(SIGNIN_TOKEN_WITH_REDIRECT));
    assertEquals(SIGNIN_TOKEN, TokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT).getLeft());
    assertEquals(SIGNIN_TOKEN, TokenMatcher.getRedirect("!" + SIGNIN_TOKEN_WITH_REDIRECT).getLeft());
    assertEquals(REDIRECT_LINK, TokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT).getRight());
    assertEquals(REDIRECT_LINK, TokenMatcher.getRedirect("!" + SIGNIN_TOKEN_WITH_REDIRECT).getRight());
  }

  /**
   * Should extract redirect in sign preview.
   */
  @Test
  public void shouldExtractRedirectInSignPreview() {
    assertTrue(TokenMatcher.hasRedirect(SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW));
    assertEquals(
        "Expected " + SIGNIN_TOKEN + " but: "
            + TokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW).getLeft(), SIGNIN_TOKEN,
        TokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT_TO_PREVIEW).getLeft());
    // assertEquals(REDIRECT_LINK,
    // TokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT).getRight());
  }

  /**
   * Shoul match complete token.
   */
  @Test
  public void shoulMatchCompleteToken() {
    matchGroupToken(GROUP_TOKEN);
    matchGroupToken("!" + GROUP_TOKEN);
    dontMatchWaveToken(GROUP_TOKEN);
    dontMatchWaveToken("!" + GROUP_TOKEN);
  }

  /**
   * Shoul match group token.
   */
  @Test
  public void shoulMatchGroupToken() {
    matchGroupToken(GROUP_TOKEN_ONLY_PROJECT);
    dontMatchWaveToken(GROUP_TOKEN_ONLY_PROJECT);
  }

  /**
   * Shoul match group tool token.
   */
  @Test
  public void shoulMatchGroupToolToken() {
    matchGroupToken("site.docs");
    matchGroupToken(GROUP_TOKEN_ONLY_PROJECT_AND_TOOL);
    dontMatchWaveToken(GROUP_TOKEN_ONLY_PROJECT_AND_TOOL);
  }

  /**
   * Test def site token dont match.
   */
  @Test
  public void testDefSiteTokenDontMatch() {
    dontMatchWaveToken(DEF_SITE_TOKEN);
    dontMatchGroupToken(DEF_SITE_TOKEN);
  }

  /**
   * Test inbox.
   */
  @Test
  public void testInbox() {
    assertTrue(TokenMatcher.isInboxToken(SiteTokens.WAVE_INBOX));
    assertTrue(TokenMatcher.isInboxToken("!" + SiteTokens.WAVE_INBOX));
  }

  /**
   * Test match wave token1.
   */
  @Test
  public void testMatchWaveToken1() {
    matchWaveToken(WAVE_TOKEN_SAMPLE1);
    dontMatchGroupToken(WAVE_TOKEN_SAMPLE1);
  }

  /**
   * Test other site tokens dont match.
   */
  @Test
  public void testOtherSiteTokensDontMatch() {
    dontMatchWaveToken(SIGNIN_TOKEN);
    dontMatchGroupToken(SIGNIN_TOKEN);
  }
}
