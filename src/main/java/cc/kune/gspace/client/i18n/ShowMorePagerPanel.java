/*
 * Copyright 2010, 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cc.kune.gspace.client.i18n;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasRows;

// TODO: Auto-generated Javadoc
/**
 * A scrolling pager that automatically increases the range every time the
 * scroll bar reaches the bottom.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ShowMorePagerPanel extends AbstractPager {

  /**
   * The default increment size.
   */
  private static final int DEFAULT_INCREMENT = 20;

  /**
   * The increment size.
   */
  private int incrementSize = DEFAULT_INCREMENT;

  /**
   * The last scroll position.
   */
  private int lastScrollPos = 0;

  /**
   * The scrollable panel.
   */
  private final ScrollPanel scrollable = new ScrollPanel();

  /**
   * Construct a new {@link ShowMorePagerPanel}.
   */
  public ShowMorePagerPanel() {
    initWidget(scrollable);

    // Do not let the scrollable take tab focus.
    scrollable.getElement().setTabIndex(-1);

    // Handle scroll events.
    scrollable.addScrollHandler(new ScrollHandler() {
      @Override
      public void onScroll(final ScrollEvent event) {
        // If scrolling up, ignore the event.
        final int oldScrollPos = lastScrollPos;
        lastScrollPos = scrollable.getScrollPosition();
        if (oldScrollPos >= lastScrollPos) {
          return;
        }

        final HasRows display = getDisplay();
        if (display == null) {
          return;
        }
        final int maxScrollTop = scrollable.getWidget().getOffsetHeight() - scrollable.getOffsetHeight();
        if (lastScrollPos >= maxScrollTop) {
          // We are near the end, so increase the page size.
          final int newPageSize = Math.min(display.getVisibleRange().getLength() + incrementSize,
              display.getRowCount());
          display.setVisibleRange(0, newPageSize);
        }
      }
    });
  }

  /**
   * Ensure visible.
   * 
   * @param object
   *          the object
   */
  public void ensureVisible(final UIObject object) {
    scrollable.ensureVisible(object);
  }

  /**
   * Get the number of rows by which the range is increased when the scrollbar
   * reaches the bottom.
   * 
   * @return the increment size
   */
  public int getIncrementSize() {
    return incrementSize;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.user.cellview.client.AbstractPager#onRangeOrRowCountChanged
   * ()
   */
  @Override
  protected void onRangeOrRowCountChanged() {
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.google.gwt.user.cellview.client.AbstractPager#setDisplay(com.google
   * .gwt.view.client.HasRows)
   */
  @Override
  public void setDisplay(final HasRows display) {
    assert display instanceof Widget : "display must extend Widget";
    scrollable.setWidget((Widget) display);
    super.setDisplay(display);
  }

  /**
   * Set the number of rows by which the range is increased when the scrollbar
   * reaches the bottom.
   * 
   * @param incrementSize
   *          the incremental number of rows
   */
  public void setIncrementSize(final int incrementSize) {
    this.incrementSize = incrementSize;
  }
}
