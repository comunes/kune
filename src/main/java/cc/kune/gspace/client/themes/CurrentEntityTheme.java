package cc.kune.gspace.client.themes;

public class CurrentEntityTheme {
  private static String[] backColors = new String[8];
  private static String[] colors = new String[8];

  private static String filter(final String color) {
    return color == null ? "#FFF" : color;
  }

  public static String getBackColor(final int number) {
    return filter(backColors[number]);
  }

  public static String getColor(final int number) {
    return filter(colors[number]);
  }

  public static void setColors(final String[] colors, final String[] backColors) {
    CurrentEntityTheme.colors = colors;
    CurrentEntityTheme.backColors = backColors;
  }

  public CurrentEntityTheme() {
  }
}
