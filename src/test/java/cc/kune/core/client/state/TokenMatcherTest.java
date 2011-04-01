package cc.kune.core.client.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.waveprotocol.wave.util.escapers.jvm.JavaWaverefEncoder;

public class TokenMatcherTest {

    private static final String DEF_SITE_TOKEN = "";
    private static final String GROUP_TOKEN = "site.docs.1";
    private static final String GROUP_TOKEN_ONLY_PROJECT = "site";
    private static final String GROUP_TOKEN_ONLY_PROJECT_AND_TOOL = "site.docs";
    private static final String REDIRECT_LINK = "example.com/w+jsdKixyHhZA";
    private static final String SIGNIN_TOKEN = "signin";
    private static final String SIGNIN_TOKEN_WITH_REDIRECT = SIGNIN_TOKEN + "(" + REDIRECT_LINK + ")";
    private static final String WAVE_TOKEN_SAMPLE1 = "example.com/w+abcd";
    private static final String WAVE_TOKEN_SAMPLE2 = "example.com/w+abcd/~/conv+root";
    private static final String WAVE_TOKEN_SAMPLE3 = "example.com/w+abcd/~/conv+root/b+45kg";
    private TokenMatcher tokenMatcher;

    @Before
    public void before() {
        final ReservedWordsRegistry reservedWords = new ReservedWordsRegistry();
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
        dontMatchGroupToken(SIGNIN_TOKEN_WITH_REDIRECT);
        assertFalse(tokenMatcher.hasRedirect(SIGNIN_TOKEN));
    }

    private void dontMatchWaveToken(final String token) {
        assertFalse("Expected '" + token + "' dont match isWaveToken", tokenMatcher.isWaveToken(token));
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
        dontMatchGroupToken(WAVE_TOKEN_SAMPLE2);
    }

    @Test
    public void matchWaveToken3() {
        matchWaveToken(WAVE_TOKEN_SAMPLE3);
        dontMatchGroupToken(WAVE_TOKEN_SAMPLE3);
    }

    @Test
    public void shouldDontMatchNull() {
        dontMatchWaveToken(null);
        dontMatchWaveToken("");
        dontMatchGroupToken(null);
        dontMatchGroupToken("");
    }

    @Test
    public void shouldExtractRedirect() {
        assertTrue(tokenMatcher.hasRedirect(SIGNIN_TOKEN_WITH_REDIRECT));
        assertEquals(SIGNIN_TOKEN, tokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT).getLeft());
        assertEquals(REDIRECT_LINK, tokenMatcher.getRedirect(SIGNIN_TOKEN_WITH_REDIRECT).getRight());
    }

    @Test
    public void shoulMatchCompleteToken() {
        matchGroupToken(GROUP_TOKEN);
        dontMatchWaveToken(GROUP_TOKEN);
    }

    @Test
    public void shoulMatchGroupToken() {
        matchGroupToken(GROUP_TOKEN_ONLY_PROJECT);
        dontMatchWaveToken(GROUP_TOKEN_ONLY_PROJECT);
    }

    @Test
    public void shoulMatchGroupToolToken() {
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
