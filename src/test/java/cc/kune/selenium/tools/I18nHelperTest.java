package cc.kune.selenium.tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.calclab.hablar.login.client.LoginMessages;
import com.calclab.hablar.search.client.SearchMessages;

public class I18nHelperTest {

    @Test
    public void testOnePlural() {
	assertEquals("Results for «test1»: One user found.", I18nHelper.get(SearchMessages.class, "searchResultsFor",
		"test1", 1));
    }

    @Test
    public void testSimpleArg() {
	assertEquals("Connected as test1", I18nHelper.get(LoginMessages.class, "connectedAs", "test1"));
    }

    @Test
    public void testSimpleSeveralArgs() {
	assertEquals("Results for «test1»: 2 users found.", I18nHelper.get(SearchMessages.class, "searchResultsFor",
		"test1", 2));
    }

}
