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
package cc.kune.core.client.ui.dialogs;

import cc.kune.common.client.ui.dialogs.BasicTopDialog;
import cc.kune.common.shared.utils.TextUtils;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.user.client.ui.Label;

// TODO: Auto-generated Javadoc
/**
 * The Class PromptTopDialog shows a top dialog with some textfield and two
 * buttons.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class PromptTopDialog extends BasicTopDialog {

  /**
   * The Class Builder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class Builder extends BasicTopDialog.Builder {

    /** The allow blank. */
    private boolean allowBlank = false;

    /** The empty text. */
    private String emptyText;

    /** The max length. */
    private int maxLength = 0;

    /** The max length text. */
    private String maxLengthText;

    /** The min length. */
    private int minLength = 0;

    /** The min length text. */
    private String minLengthText;

    /** The on enter. */
    private final OnEnter onEnter;

    /** The prompt lines. */
    private int promptLines = 1;

    /** The prompt text. */
    private String promptText;
    /** The field width. */
    private int promptWidth;
    /** The regex. */
    private String regex;

    /** The regex text. */
    private String regexText;

    /** The textbox id. */
    private String textboxId;

    /** The width. */
    private int width = 0;

    /**
     * Instantiates a new builder.
     * 
     * @param dialogId
     *          the dialog id (used for debuggin)
     * @param title
     *          the title
     * @param autohide
     *          the autohide
     * @param modal
     *          the modal
     * @param direction
     *          the direction
     * @param onEnter
     *          the on enter
     */
    public Builder(final String dialogId, final String title, final boolean autohide,
        final boolean modal, final Direction direction, final OnEnter onEnter) {
      super(dialogId, autohide, modal, direction);
      this.onEnter = onEnter;
      super.title(title);
      super.tabIndexStart(1);
    }

    /**
     * Allow blank.
     * 
     * @param allowBlank
     *          the allow blank
     * @return the builder
     */
    public Builder allowBlank(final boolean allowBlank) {
      this.allowBlank = allowBlank;
      return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder#build()
     */
    @Override
    public PromptTopDialog build() {
      final PromptTopDialog dialog = new PromptTopDialog(this);
      return dialog;
    }

    /**
     * Empty text showed when the prompt field is empty.
     * 
     * @param emptyText
     *          the empty text
     * @return the builder
     */
    public Builder emptyTextField(final String emptyText) {
      this.emptyText = emptyText;
      return this;
    }

    /**
     * Max length.
     * 
     * @param maxLength
     *          the max length
     * @return the builder
     */
    public Builder maxLength(final int maxLength) {
      this.maxLength = maxLength;
      return this;
    }

    /**
     * Max length text.
     * 
     * @param maxLengthText
     *          the max length text
     * @return the builder
     */
    public Builder maxLengthText(final String maxLengthText) {
      this.maxLengthText = maxLengthText;
      return this;
    }

    /**
     * Min length.
     * 
     * @param minLength
     *          the min length
     * @return the builder
     */
    public Builder minLength(final int minLength) {
      this.minLength = minLength;
      return this;
    }

    /**
     * Min length text.
     * 
     * @param minLengthText
     *          the min length text
     * @return the builder
     */
    public Builder minLengthText(final String minLengthText) {
      this.minLengthText = minLengthText;
      return this;
    }

    /**
     * The number of lines that the prompt has. If > 1 will be a textarea, if
     * not a textfield
     * 
     * @param promptLines
     *          the prompt lines
     * @return the builder
     */
    public Builder promptLines(final int promptLines) {
      this.promptLines = promptLines;
      return this;
    }

    /**
     * Prompt text showed just before the text field.
     * 
     * @param promptText
     *          the prompt text
     * @return the builder
     */
    public Builder promptText(final String promptText) {
      this.promptText = promptText;
      return this;
    }

    /**
     * Field width.
     * 
     * @param promptWidth
     *          the field width
     * @return the builder
     */
    public Builder promptWidth(final int promptWidth) {
      this.promptWidth = promptWidth;
      return this;
    }

    /**
     * Regex.
     * 
     * @param regex
     *          the regex
     * @return the builder
     */
    public Builder regex(final String regex) {
      this.regex = regex;
      return this;
    }

    /**
     * Regex text.
     * 
     * @param regexText
     *          the regex text
     * @return the builder
     */
    public Builder regexText(final String regexText) {
      this.regexText = regexText;
      return this;
    }

    /**
     * Textbox id.
     * 
     * @param textboxId
     *          the textbox id
     * @return the builder
     */
    public Builder textboxId(final String textboxId) {
      this.textboxId = textboxId;
      return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cc.kune.common.client.ui.dialogs.BasicTopDialog.Builder#width(int)
     */
    @Override
    public Builder width(final int width) {
      this.width = width;
      return this;
    }
  }

  /**
   * The Interface OnEnter.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public interface OnEnter {

    /**
     * On enter what to do.
     */
    void onEnter();
  }

  /** The prompt label. */
  private final Label promptLabel;

  /** The text field of the Prompt. */
  private TextField<String> textField;

  /**
   * Instantiates a new prompt top dialog.
   * 
   * @param builder
   *          the builder
   */
  protected PromptTopDialog(final Builder builder) {
    super(builder);
    promptLabel = new Label();
    promptLabel.addStyleName("kune-Margin-Medium-b");
    if (builder.promptLines > 1) {
      textField = new TextArea();
      textField.setHeight(20 * builder.promptLines);
    } else {
      textField = new TextField<String>();
    }
    textField.setRegex(builder.regex);
    textField.getMessages().setRegexText(builder.regexText);
    textField.getMessages().setMinLengthText(builder.minLengthText);
    textField.getMessages().setMaxLengthText(builder.maxLengthText);
    textField.setTabIndex(1);
    textField.setId(builder.textboxId);
    if (TextUtils.notEmpty(builder.emptyText)) {
      textField.setEmptyText(builder.emptyText);
    }
    if (builder.width != 0) {
      textField.setWidth(builder.width);
    }
    if (builder.minLength != 0) {
      textField.setMinLength(builder.minLength);
    }
    if (builder.maxLength != 0) {
      textField.setMaxLength(builder.maxLength);
    }
    textField.setWidth(builder.promptWidth);
    textField.setAllowBlank(builder.allowBlank);
    textField.addListener(Events.OnKeyPress, new Listener<FieldEvent>() {
      @Override
      public void handleEvent(final FieldEvent fe) {
        if (fe.getEvent().getKeyCode() == 13) {
          builder.onEnter.onEnter();
        }
      }
    });
    if (TextUtils.notEmpty(builder.promptText)) {
      promptLabel.setText(builder.promptText);
    }
    super.getInnerPanel().add(promptLabel);
    super.getInnerPanel().add(textField);
  }

  public void addStyleToTextField(final String style) {
    textField.addStyleName(style);
  }

  /**
   * Clear text field value.
   */
  public void clearTextFieldValue() {
    textField.reset();
  }

  /**
   * Focus on text box.
   */
  public void focusOnTextBox() {
    textField.focus();
  }

  /**
   * Gets the text field value.
   * 
   * @return the text field value
   */
  public String getTextFieldValue() {
    return textField.getValue();
  }

  /**
   * Checks if is valid.
   * 
   * @return true, if is valid
   */
  public boolean isValid() {
    return textField.isValid();
  }

  /**
   * Sets the prompt label.
   * 
   * @param promptText
   *          the new prompt label
   */
  public void setPromptLabel(final String promptText) {
    promptLabel.setText(promptText);
  }

  /**
   * Sets the text field focus.
   * 
   */
  public void setTextFieldFocus() {
    textField.focus();
  }

  public void setTextFieldFocusOnClick() {
    textField.addListener(Events.OnClick, new Listener<FieldEvent>() {
      @Override
      public void handleEvent(final FieldEvent fe) {
        textField.focus();
      }
    });

  }

  public void setTextFieldReadOnly(final boolean readonly) {
    textField.setReadOnly(readonly);
  }

  /**
   * Sets the text field select on focus.
   * 
   * @param selectOnFocus
   *          the new text field select on focus
   */
  public void setTextFieldSelectOnFocus(final boolean selectOnFocus) {
    textField.setSelectOnFocus(selectOnFocus);
  }

  /**
   * Sets the text field value.
   * 
   * @param text
   *          the new text field value
   */
  public void setTextFieldValue(final String text) {
    textField.setRawValue(text);
  }

}
