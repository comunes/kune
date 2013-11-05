/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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

/**
 * 
 */
package cc.kune.kunecli;

import java.net.MalformedURLException;

import org.junit.Test;
import org.naturalcli.ExecutionException;
import org.naturalcli.InvalidSyntaxException;

// TODO: Auto-generated Javadoc
/**
 * The Class KuneCliMainTest.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class KuneCliMainTest {

  /**
   * Test basic auth.
   *
   * @throws InvalidSyntaxException the invalid syntax exception
   * @throws ExecutionException the execution exception
   * @throws MalformedURLException the malformed url exception
   */
  @Test
  public void testBasicAuth() throws InvalidSyntaxException, ExecutionException, MalformedURLException {
    final KuneCliMain cli = new KuneCliMain();
    final String[] args = new String[] { "auth", "admin", "easyeasy" };
    KuneCliMain.main(args);
  }

  /**
   * Test basic hello.
   *
   * @throws InvalidSyntaxException the invalid syntax exception
   * @throws ExecutionException the execution exception
   * @throws MalformedURLException the malformed url exception
   */
  @Test
  public void testBasicHello() throws InvalidSyntaxException, ExecutionException, MalformedURLException {
    final KuneCliMain cli = new KuneCliMain();
    final String[] args = new String[] { "hello", "world", "admin" };
    KuneCliMain.main(args);
  }

  /**
   * Test basic init.
   *
   * @throws InvalidSyntaxException the invalid syntax exception
   * @throws ExecutionException the execution exception
   * @throws MalformedURLException the malformed url exception
   */
  @Test
  public void testBasicInit() throws InvalidSyntaxException, ExecutionException, MalformedURLException {
    final KuneCliMain cli = new KuneCliMain();
    final String[] args = new String[] { "siteGetInitData" };
    KuneCliMain.main(args);
  }

  /**
   * Test basic init lang.
   *
   * @throws InvalidSyntaxException the invalid syntax exception
   * @throws ExecutionException the execution exception
   * @throws MalformedURLException the malformed url exception
   */
  @Test
  public void testBasicInitLang() throws InvalidSyntaxException, ExecutionException,
      MalformedURLException {
    final KuneCliMain cli = new KuneCliMain();
    final String[] args = new String[] { "i18nGetInitLang" };
    KuneCliMain.main(args);
  }

}
