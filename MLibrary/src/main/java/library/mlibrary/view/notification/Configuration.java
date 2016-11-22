package library.mlibrary.view.notification;
/*
 * Copyright 2014 gitonway
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Color;
import android.view.Gravity;

public class Configuration {

    public static final int ANIM_DURATION = 700;

    public static final int ANIM_DISPLAY_DURATION = 1500;

    final long animDuration;

    final long dispalyDuration;

    final int textColor;

    final int backgroundColor;

    final int viewHeight;

    final int iconBackgroundColor;

    final int textGravity;

    final int textLines;

    final int textPadding;

    private Configuration(final Builder builder) {
        this.animDuration = builder.animDuration;
        this.textColor = builder.textColor;
        this.dispalyDuration = builder.dispalyDuration;
        this.backgroundColor = builder.backgroundColor;
        this.textPadding = builder.textPadding;
        this.viewHeight = builder.viewHeight;
        this.iconBackgroundColor = builder.iconBackgroundColor;
        this.textGravity = builder.textGravity;
        this.textLines = builder.textLines;
    }

    public static class Builder {

        private long animDuration;

        private long dispalyDuration;

        private int textColor;

        private int backgroundColor;

        private int textPadding;

        private int viewHeight;

        private int iconBackgroundColor;

        private int textGravity;

        private int textLines;

        public Builder() {
            animDuration = ANIM_DURATION;
            dispalyDuration = ANIM_DISPLAY_DURATION;
            textColor = Color.parseColor("#FF444444");
            backgroundColor = Color.parseColor("#FFBDC3C7");
            textPadding = 5;
            viewHeight = 48;
            iconBackgroundColor = Color.parseColor("#FFFFFFFF");
            textGravity = Gravity.CENTER;
            textLines = 2;
        }

        public Builder(final Configuration baseStyle) {
            animDuration = baseStyle.animDuration;
            textColor = baseStyle.textColor;
            backgroundColor = baseStyle.backgroundColor;
            textPadding = baseStyle.textPadding;
            viewHeight = baseStyle.viewHeight;
            iconBackgroundColor = baseStyle.iconBackgroundColor;
            textGravity = baseStyle.textGravity;
            textLines = baseStyle.textLines;
        }


        public Builder setAnimDuration(long animDuration) {
            this.animDuration = animDuration;
            return this;
        }

        public Builder setDispalyDuration(long dispalyDuration) {
            this.dispalyDuration = dispalyDuration;
            return this;
        }

        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder setTextPadding(int textPadding) {
            this.textPadding = textPadding;
            return this;
        }

        public Builder setViewHeight(int viewHeight) {
            this.viewHeight = viewHeight;
            return this;
        }

        public Builder setIconBackgroundColor(int iconBackgroundColor) {
            this.iconBackgroundColor = iconBackgroundColor;
            return this;
        }

        public Builder setTextGravity(int textGravity) {
            this.textGravity = textGravity;
            return this;
        }

        public Builder setTextLines(int textLines) {
            this.textLines = textLines;
            return this;
        }

        public Configuration build() {
            return new Configuration(this);
        }
    }
}
