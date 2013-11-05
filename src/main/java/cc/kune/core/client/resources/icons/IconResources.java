/*
 *
 * Copyright (C) 2007-2013 Licensed to the Comunes Association (CA) under
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
package cc.kune.core.client.resources.icons;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

@Deprecated
public interface IconResources extends ClientBundle {

  @Source("add-green.png")
  ImageResource addGreen();

  @Source("bug.png")
  ImageResource bug();

  @Source("checked.png")
  ImageResource checked();

  @Source("kicon.css")
  IconCssResource css();

  @Source("del-green.png")
  ImageResource delGreen();

  @Source("group-home.png")
  ImageResource groupHome();

  @Source("info.png")
  ImageResource info();

  @Source("kune-icon16.png")
  ImageResource kuneIcon16();

  @Source("language.png")
  ImageResource language();

  @Source("arrow_out.png")
  ImageResource maximize();

  @Source("arrow_in.png")
  ImageResource minimize();

  @Source("prefs.png")
  ImageResource prefs();

  @Source("radiochecked.png")
  ImageResource radiochecked();

  @Source("radiounchecked.png")
  ImageResource radioUnChecked();

  @Source("unchecked.png")
  ImageResource unChecked();
}
