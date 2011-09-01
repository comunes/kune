package cc.kune.common.client.ui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.UIObject;

/**
 * Source:
 * http://stackoverflow.com/questions/2316590/blink-flash-effect-or-animation
 * -in-gwt
 */
/**
 * @author vjrj
 *
 */
/**
 * @author vjrj
 * 
 */
public class BlinkAnimation {

  private boolean blink;

  private final int interval;

  private int iteration;

  private int stopIter;

  private final Timer timer;

  public BlinkAnimation(final UIObject obj) {
    this(obj, 200);
  }

  /**
   * @param obj
   *          the object to animate
   * @param interval
   *          between blinks
   */
  public BlinkAnimation(final UIObject obj, final int interval) {
    this.interval = interval;

    timer = new Timer() {
      @Override
      public void run() {
        if (!blink) {
          obj.addStyleDependentName("blink");
          iteration++;
        } else {
          obj.removeStyleDependentName("blink");
          if (iteration == stopIter) {
            timer.cancel();
          }
        }
        blink = !blink;
      }
    };
  }

  /**
   * Animate till {@link BlinkAnimation#stop() }
   */
  public void animate() {
    animate(-1);
  }

  /**
   * @param numTimes
   *          to blink (3, 4, ... etc)
   */
  public void animate(final int numTimes) {
    iteration = 0;
    stopIter = numTimes;
    blink = false;
    timer.scheduleRepeating(interval);
  }

  /**
   * Stop the animation now
   */
  public void stop() {
    timer.cancel();
  }
}