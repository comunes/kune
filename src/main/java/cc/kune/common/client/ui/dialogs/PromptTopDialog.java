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
package cc.kune.common.client.ui.dialogs;

import com.extjs.gxt.ui.client.widget.form.TextField;

public class PromptTopDialog extends BasicTopDialog {

  public static class Builder extends BasicTopDialog.Builder {

    private boolean allowBlank;
    private int maxLength;
    private String maxLengthText;
    private int minLength;
    private String minLengthText;
    private String regex;
    private String regexText;
    private String textboxId;

    public Builder(final String dialogId, final String promptText, final boolean autohide,
        final boolean modal) {
      super(dialogId, autohide, modal);
      super.title(promptText);
    }

    public Builder allowBlank(final boolean allowBlank) {
      this.allowBlank = allowBlank;
      return this;
    }

    @Override
    public PromptTopDialog build() {
      final PromptTopDialog dialog = new PromptTopDialog(this);
      return dialog;
    }

    public Builder maxLength(final int maxLength) {
      this.maxLength = maxLength;
      return this;
    }

    public Builder maxLengthText(final String maxLengthText) {
      this.maxLengthText = maxLengthText;
      return this;
    }

    public Builder minLength(final int minLength) {
      this.minLength = minLength;
      return this;
    }

    public Builder minLengthText(final String minLengthText) {
      this.minLengthText = minLengthText;
      return this;
    }

    public Builder regex(final String regex) {
      this.regex = regex;
      return this;
    }

    public Builder regexText(final String regexText) {
      this.regexText = regexText;
      return this;
    }

    public Builder textboxId(final String textboxId) {
      this.textboxId = textboxId;
      return this;
    }
  }

  private final TextField<String> textField;

  protected PromptTopDialog(final Builder builder) {
    super(builder);
    textField = new TextField<String>();
    textField.setRegex(builder.regex);
    textField.getMessages().setRegexText(builder.regexText);
    textField.getMessages().setMinLengthText(builder.minLengthText);
    textField.getMessages().setMaxLengthText(builder.maxLengthText);
    textField.setId(builder.textboxId);
    textField.setMinLength(builder.minLength);
    textField.setMaxLength(builder.maxLength);
    textField.setAllowBlank(builder.allowBlank);
    super.getInnerPanel().add(textField);
  }

  public void focusOnTextBox() {
    textField.focus();
  }

  public String getText() {
    return textField.getValue();
  }

  public boolean isValid() {
    return textField.isValid();
  }

  public void setText(final String text) {
    textField.setValue(text);
  }

}
