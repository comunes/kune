package cc.kune.core.server.mail;

public class MailManagerDefault implements MailManager {

  public static class FormatedString {

    private final String string;

    public FormatedString(final String template, final Object... args) {
      string = String.format(template, args);
    }

    public String getString() {
      return string;
    }
  }

  public MailManagerDefault(final String mailServer, final String otherConfigurableMailServerParams) {
  }

  @Override
  public boolean sendHtml(final FormatedString subject, final FormatedString body, final String from,
      final String... to) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean sendPlain(final FormatedString subject, final FormatedString body, final String from,
      final String... to) {
    // TODO Auto-generated method stub
    return false;
  }
}
