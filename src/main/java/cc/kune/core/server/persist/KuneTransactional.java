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
package cc.kune.core.server.persist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface KuneTransactional.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface KuneTransactional {

  /**
   * A list of exceptions to <b>not<b> rollback on. A caveat to the rollbackOn
   * clause. The disjunction of rollbackOn and ignore represents the list of
   * exceptions that will trigger a rollback. The complement of rollbackOn and
   * the universal set plus any exceptions in the ignore set represents the list
   * of exceptions that will trigger a commit. Note that ignore exceptions take
   * precedence over rollbackOn, but with subtype granularity.
   * 
   * @return the class<? extends exception>[]
   */
  Class<? extends Exception>[] ignore() default {};

  /**
   * A list of exceptions to rollback on, if thrown by the transactional method.
   * These exceptions are propagated correctly after a rollback.
   * 
   * @return the class<? extends exception>[]
   */
  Class<? extends Exception>[] rollbackOn() default java.lang.RuntimeException.class;
}
