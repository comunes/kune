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
