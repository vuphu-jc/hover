/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mattcarroll.hover.hoverdemo.helloworld;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;

import org.codecanon.hover.hoverdemo.helloworld.R;

import java.util.Collections;
import java.util.List;

import io.mattcarroll.hover.Content;
import io.mattcarroll.hover.HoverMenu;
import io.mattcarroll.hover.HoverView;
import io.mattcarroll.hover.window.HoverMenuService;

/**
 * Extend {@link HoverMenuService} to get a Hover menu that displays the tabs and content
 * in your custom {@link HoverMenu}.
 *
 * This menu presents the simplest possible Hover Menu, a menu with a single Section.  This menu
 * also runs as a foreground Service by providing a notification.
 */
public class SingleSectionNotificationHoverMenuService extends HoverMenuService {

    private static final String TAG = "SingleSectionNotificationHoverMenuService";

    @Override
    protected int getForegroundNotificationId() {
        return 1000;
    }

    @Nullable
    @Override
    protected Notification getForegroundNotification() {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.tab_background)
                .setContentTitle("Hover Menu")
                .setContentText("Hover is running in a foreground Service.")
                .build();
    }

    @Override
    protected void onHoverMenuLaunched(@NonNull Intent intent, @NonNull HoverView hoverView) {
        hoverView.setMenu(createHoverMenu());
        hoverView.collapse();
    }

    @NonNull
    private HoverMenu createHoverMenu() {
        return new SingleSectionHoverMenu(getApplicationContext());
    }

    private static class SingleSectionHoverMenu extends HoverMenu {

        private Context mContext;
        private Section mSection;

        private SingleSectionHoverMenu(@NonNull Context context) {
            mContext = context;

            mSection = new Section(
                    new SectionId("1"),
                    createTabView(),
                    createScreen()
            );
        }

        private View createTabView() {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.tab_background);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            return imageView;
        }

        private Content createScreen() {
            return new HoverMenuScreen(mContext, "Notice the foreground Service notification?");
        }

        @Override
        public String getId() {
            return "singlesectionmenu_foreground";
        }

        @Override
        public int getSectionCount() {
            return 1;
        }

        @Nullable
        @Override
        public Section getSection(int index) {
            if (0 == index) {
                return mSection;
            } else {
                return null;
            }
        }

        @Nullable
        @Override
        public Section getSection(@NonNull SectionId sectionId) {
            if (sectionId.equals(mSection.getId())) {
                return mSection;
            } else {
                return null;
            }
        }

        @NonNull
        @Override
        public List<Section> getSections() {
            return Collections.singletonList(mSection);
        }

        @NonNull
        @Override
        public void removeAt(int index) {

        }
    }
}
