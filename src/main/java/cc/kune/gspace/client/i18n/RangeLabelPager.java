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

import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;

// TODO: Auto-generated Javadoc
/**
 * A pager that displays the current range without any controls to change the
 * range.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class RangeLabelPager extends AbstractPager {

  /**
   * The label that shows the current range.
   */
  private final HTML label = new HTML();

  /**
   * Construct a new {@link RangeLabelPager}.
   */
  public RangeLabelPager() {
    initWidget(label);
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
    final HasRows display = getDisplay();
    final Range range = display.getVisibleRange();
    final int start = range.getStart();
    final int end = start + range.getLength();
    label.setText(start + " - " + end + " : " + display.getRowCount(), HasDirection.Direction.LTR);
  }
}
