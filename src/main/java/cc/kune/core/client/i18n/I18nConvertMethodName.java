package cc.kune.core.client.i18n;

public class I18nConvertMethodName {

  public static void main(final String... args) {
    if (args.length == 0) {
      System.err.print("Syntax: I18nConvertMethodName 'Some string message to convert'\n");
    } else {
      final StringBuffer buf = new StringBuffer();
      for (final String arg : args) {
        buf.append(arg).append(" ");
      }
      System.out.print(I18nUtils.convertMethodName(buf.toString()));
    }
  }
}
