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
package cc.kune.common.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface CommonResources extends ClientBundle {

  public static final CommonResources INSTANCE = GWT.create(CommonResources.class);

  @Source("alert.png")
  ImageResource alert();

  @Source("checked.png")
  ImageResource checked();

  @Source("clear.png")
  ImageResource clear();

  @Source("error.png")
  ImageResource error();

  @Source("important.png")
  ImageResource important();

  @Source("important-32.png")
  ImageResource important32();

  @Source("info.png")
  ImageResource info();

  @Source("kune-anim.gif")
  ImageResource kuneAnim();

  @Source("kune-anim-g.gif")
  ImageResource kuneAnimG();

  @Source("kune-close.png")
  ImageResource kuneClose();

  @Source("kune-close-grey.png")
  ImageResource kuneCloseGrey();

  @Source("kune-close-greylight.png")
  ImageResource kuneCloseGreylight();

  @Source("radiochecked.png")
  ImageResource radioChecked();

  @Source("radiounchecked.png")
  ImageResource radioUnChecked();

  @Source("cross.png")
  ImageResource redCross();
  
  @Source("icons/chain-close-black.png")
  ImageResource chainCloseBlack();

  @Source("icons/chain-close-white.png")
  ImageResource chainCloseWhite();

  @Source("icons/chain-closed-grey.png")
  ImageResource chainClosedGrey();

  @Source("icons/chain-open-black.png")
  ImageResource chainOpenBlack();

  @Source("icons/chain-open-grey.png")
  ImageResource chainOpenGrey();

  @Source("icons/chain-open-white.png")
  ImageResource chainOpenWhite();

  @Source("icons/picture-black.png")
  ImageResource pictureBlack();

  @Source("icons/picture-grey.png")
  ImageResource pictureGrey();

  @Source("icons/picture-white.png")
  ImageResource pictureWhite();
  
  @Source("unchecked.png")
  ImageResource unChecked();
}
