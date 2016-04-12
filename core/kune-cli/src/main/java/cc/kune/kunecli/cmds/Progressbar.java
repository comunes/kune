package cc.kune.kunecli.cmds;

/**
 * From: https://gist.github.com/nacmartin/703416
 *
 **/

public class Progressbar {
  private int current;
  private long lastUpdate;
  private final int max;
  private final String name;
  private final long start;
  private String stats = "";
  private String status = "";

  public Progressbar(final int max, final String name) {
    this.start = System.currentTimeMillis();
    this.name = name;
    this.max = max;
    System.out.println(this.name + ":");
    this.printBar(false);
  }

  public void finish() {
    this.current = this.max;
    this.printBar(true);
  }

  private void printBar(final boolean finished) {
    final double numbar = Math.floor(50 * (double) current / max);
    String strbar = "";
    int ii = 0;
    for (ii = 0; ii < numbar; ii++) {
      strbar += "=";
    }
    for (ii = (int) numbar; ii < 50; ii++) {
      strbar += " ";
    }
    final long elapsed = (System.currentTimeMillis() - this.start);
    final int seconds = (int) (elapsed / 1000) % 60;
    final int minutes = (int) (elapsed / 1000) / 60;
    String strend = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);

    String strETA = "";
    if (elapsed < 2000) {
      strETA = "--:--";
    } else {
      final long timeETA = elapsed * (long) ((double) max / (double) current);
      final int ETAseconds = (int) (timeETA / 1000) % 60;
      final int ETAminutes = (int) (timeETA / 1000) / 60;
      strETA = String.format("%02d", ETAminutes) + ":" + String.format("%02d", ETAseconds);
    }
    if (finished) {
      strend = "Finished: " + strend + "               ";
    } else {
      strend = "Elapsed: " + strend + " ETA: " + strETA + ", " + status + " " + current + " (" + stats
          + ")                        ";
    }
    System.out.print("|" + strbar + "| " + strend);
    if (finished) {
      System.out.print("\n");
    } else {
      System.out.print("\r");
    }
  }

  public void setStatus(final String status, final String stats) {
    this.status = status;
    this.stats = stats;
  }

  public void setVal(final int i) {
    this.current = i;
    if ((System.currentTimeMillis() - this.lastUpdate) > 1000) {
      this.lastUpdate = System.currentTimeMillis();
      this.printBar(false);
    }
  }
}
