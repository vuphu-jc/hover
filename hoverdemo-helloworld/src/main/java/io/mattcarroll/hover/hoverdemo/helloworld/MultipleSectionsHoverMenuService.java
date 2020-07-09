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

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import org.codecanon.hover.hoverdemo.helloworld.R;

import java.util.ArrayList;
import java.util.List;

import io.mattcarroll.hover.HoverMenu;
import io.mattcarroll.hover.HoverView;
import io.mattcarroll.hover.window.HoverMenuService;

/**
 * Extend {@link HoverMenuService} to get a Hover menu that displays the tabs and content
 * in your custom {@link HoverMenu}.
 * <p>
 * This demo menu displays multiple sections of content.
 */
public class MultipleSectionsHoverMenuService extends HoverMenuService {

    private static final String TAG = "MultipleSectionsHoverMenuService";
    static HoverView mHoverView;

    @Override
    protected void onHoverMenuLaunched(@NonNull Intent intent, @NonNull HoverView hoverView) {
        hoverView.setMenu(createHoverMenu());
        hoverView.collapse();
        mHoverView = hoverView;
    }

    @NonNull
    private HoverMenu createHoverMenu() {
        return new MultiSectionHoverMenu(getApplicationContext());
    }

    private static class MultiSectionHoverMenu extends HoverMenu {

        private final Context mContext;
        private final ArrayList<Section> mSections;

        public MultiSectionHoverMenu(@NonNull Context context) {
            mContext = context.getApplicationContext();

            mSections = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                mSections.add(new Section(
                        new SectionId(String.valueOf(i)),
                        createTabView(),
                        new HoverMenuScreen(mContext, "Screen 1")
                ));
            }

            notifyMenuChanged();

            (new Handler()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSections.add(new Section(
                            new SectionId(String.valueOf(3)),
                            createTabView(),
                            new HoverMenuScreen(mContext, "Screen 1")
                    ));
                    notifyMenuChanged();
                    mHoverView.setSelectedSectionId(new SectionId(String.valueOf(3)));
                }
            }, 1000);
        }

        int mCounter = 0;

        private View createTabView() {
            mCounter++;
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.tab_background);
            if (mCounter % 2 == 0) {
                imageView.setImageResource(R.drawable.tab_background_blue);
            }
            return imageView;
        }

        @Override
        public String getId() {
            return "multisectionmenu";
        }

        @Override
        public int getSectionCount() {
            return mSections.size();
        }

        @Nullable
        @Override
        public Section getSection(int index) {
            return mSections.get(index);
        }

        @Nullable
        @Override
        public Section getSection(@NonNull SectionId sectionId) {
            for (Section section : mSections) {
                if (section.getId().equals(sectionId)) {
                    return section;
                }
            }
            return null;
        }

        @NonNull
        @Override
        public List<Section> getSections() {
            return new ArrayList<>(mSections);
        }

        @NonNull
        @Override
        public void removeAt(int index) {
            if (mSections.size() > index) {
                mSections.remove(index);
                notifyMenuChanged();
            }
        }

    }

}
