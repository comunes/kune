/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.utils;

import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.Image;

public class PrefetchUtilities {

    public static void doTasksDeferred() {

        DeferredCommand.addCommand(new IncrementalCommand() {
            int i = 0;
            int j = 0;

            String[] lic = { "gnu-fdl.gif", "bynd80x15.png", "byncsa80x15.png", "byncnd80x15.png", "bync80x15.png",
                    "by80x15.png", "fal-license.gif" };

            String[] ext = { "default/form/text-bg.gif", "default/form/trigger.gif", "default/form/date-trigger.gif",
                    "gray/window/left-corners.png", "gray/button/btn-sprite.gif", "gray/window/top-bottom.png",
                    "gray/window/right-corners.png", "gray/window/left-right.png", "default/sizer/s-handle.gif",
                    "default/sizer/e-handle.gif", "default/sizer/ne-handle.gif", "default/sizer/se-handle.gif",
                    "default/sizer/sw-handle.gif", "gray/panel/tool-sprites.gif", "default/sizer/nw-handle.gif",
                    "gray/tabs/tabs-sprite.gif", "gray/tabs/tab-strip-bg.gif", "default/shadow.png",
                    "default/shadow-lr.png", "default/shadow-c.png", "default/grid/invalid_line.gif",
                    "default/form/exclamation.gif", "default/box/tb-blue.gif", "default/grid/loading.gif",
                    "gray/toolbar/bg.gif", "default/grid/grid3-hrow.gif", "default/dd/drop-no.gif",
                    "default/grid/col-move-top.gif", "default/grid/col-move-bottom.gif", "default/grid/row-over.gif",
                    "default/grid/grid-split.gif", "default/grid/page-first-disabled.gif",
                    "default/grid/page-last-disabled.gif", "default/grid/done.gif",
                    "default/grid/page-prev-disabled.gif", "default/grid/done.gif",
                    "default/grid/page-next-disabled.gif", "default/qtip/tip-sprite.gif",
                    "default/grid/grid3-hrow-over.gif", "default/grid/grid3-hd-btn.gif",
                    "gray/panel/white-top-bottom.gif", "gray/tabs/tab-close.gif", "gray/toolbar/btn-arrow.gif",
                    "gray/toolbar/tb-btn-sprite.gif", "gray/panel/light-hd.gif" };

            public boolean execute() {

                while (i < lic.length) {
                    final String licImg = lic[i];
                    Image.prefetch("images/lic/" + licImg);
                    i++;
                }

                while (j < ext.length) {
                    final String extImg = ext[j];
                    Image.prefetch("js/ext/resources/images/" + extImg);
                    j++;
                }

                boolean notFinished = i + j < lic.length + ext.length;

                final boolean finished = !notFinished;

                if (finished) {
                    // In the future maybe: workspace.getLoginComponent()
                }

                return notFinished;
            }
        });
    }

    public static void preFetchImpImages() {
        final String[] imgs = { "images/spin-kune-thund-green.gif", "css/img/button-bg-hard.gif",
                "css/img/button-bg-soft.gif", "css/img/arrow-down-white.gif", "css/img/arrow-right-white.gif",
                "images/lic/bysa80x15.png", "images/nav/blog.png", "images/nav/download.png", "images/nav/film.png",
                "images/nav/folder.png", "images/nav/gallery.png", "images/nav/go.png", "images/nav/page_pdf.png",
                "images/nav/page.png", "images/nav/page_pps.png", "images/nav/page_text.png",
                "images/nav/page_word.png", "images/nav/page_zip.png", "images/nav/picture.png", "images/nav/post.png",
                "images/nav/refresh.png", "images/nav/room_add.png", "images/nav/room.png", "images/nav/upload.png",
                "images/nav/wikipage.png", "images/nav/wiki.png" };
        for (final String img : imgs) {
            Image.prefetch(img);
        }
    }
}
