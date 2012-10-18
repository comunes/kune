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
package cc.kune.core.client.i18n;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class I18nUtilsTest {

  @Test
  public void basicLongMessage() {
    assertEquals(
        "_012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678",
        I18nUtils.convertMethodName("0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789 012345678 "));
  }

  @Test
  public void basicMultipleReservedChars() {
    assertEquals("yesOrNoMaybe", I18nUtils.convertMethodName("Yes, or; & no? maybe"));
    assertEquals("yesOrNoMaybe", I18nUtils.convertMethodName("Yes, or; & no? (maybe)"));
    assertEquals("yesOrNoMaybeGiveSomeAnyPerCent",
        I18nUtils.convertMethodName("Yes, or; & no?! (maybe): 'give' \"some/any\" % per-cent"));
  }

  @Test
  public void basicTests() {
    assertEquals("yesOrNo", I18nUtils.convertMethodName("yes, or & no?"));
    assertEquals("yesOrNo", I18nUtils.convertMethodName("yes or no"));
    assertEquals("yesOrNo", I18nUtils.convertMethodName("yes   or   no"));
    assertEquals("newYesNoMaybeVoting", I18nUtils.convertMethodName("New Yes/No/Maybe/+ Voting"));
  }

  @Test
  public void carriageReturn() {
    assertEquals("yesNo", I18nUtils.convertMethodName("yes\nno"));
  }

  @Test
  public void testNumbers() {
    assertEquals("_2day3", I18nUtils.convertMethodName("2day3"));
  }

  @Test
  public void testParams() {
    assertEquals("nOfParamItems", I18nUtils.convertMethodName("[%d] of [%s] items"));
    assertEquals("nOfNItems", I18nUtils.convertMethodName("[%d] of [%d] items"));
  }

  @Test
  public void testParent() {
    assertEquals("ofItems", I18nUtils.convertMethodName("() of [] {} items"));
  }

}
