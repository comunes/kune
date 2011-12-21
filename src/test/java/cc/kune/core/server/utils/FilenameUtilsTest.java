package cc.kune.core.server.utils;

import org.junit.Test;

import cc.kune.core.client.errors.NameNotPermittedException;

public class FilenameUtilsTest {

  @Test(expected = NameNotPermittedException.class)
  public void testNoDot() {
    FilenameUtils.checkBasicFilename(".");
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoDoubleDot() {
    FilenameUtils.checkBasicFilename("..");
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoEmpty() {
    FilenameUtils.checkBasicFilename("");
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoNull() {
    FilenameUtils.checkBasicFilename(null);
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoReturn() {
    FilenameUtils.checkBasicFilename("\n");
  }

  @Test(expected = NameNotPermittedException.class)
  public void testNoSpace() {
    FilenameUtils.checkBasicFilename(" ");
  }
}
