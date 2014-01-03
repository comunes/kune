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

package cc.kune.core.client.actions;

import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.ui.dialogs.PromptTopDialog.Builder;

// TODO: Auto-generated Javadoc
/**
 * The Class FieldValidationUtil.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class FieldValidationUtil {

  /**
   * Restrict to email list.
   * 
   * @param builder
   *          the builder
   * @param id
   *          the id
   */
  public static void restrictToEmailList(final Builder builder, final String id) {
    builder.regex(TextUtils.EMAIL_REGEXP_LIST).regexText(
        I18n.t("Please provide here a comma-separated list of emails")).textboxId(id);
    builder.allowBlank(false);
  }

  /**
   * Restrict to unix name.
   * 
   * @param builder
   *          the builder
   * @param id
   *          the id
   */
  public static void restrictToUnixName(final Builder builder, final String id) {
    // For the future: In Google Groups, the max group shorta name is 63 chars
    // (with dashes)
    builder.regex(TextUtils.UNIX_NAME).regexText(
        I18n.t("The name must contain only lowercase characters, numbers and dashes")).textboxId(id);
    builder.minLength(3).maxLength(15).allowBlank(false).minLengthText(
        I18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15_NO_CHARS)).maxLengthText(
        I18n.t(CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15_NO_CHARS));
  }
}
