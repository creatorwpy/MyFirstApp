package library.mlibrary.view.dialog.effects;

import android.animation.ObjectAnimator;
import android.view.View;

/*
 * Copyright 2014 litao
 * https://github.com/sd6352051/NiftyDialogEffects
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
public class NewsPaperDismiss extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "rotation", 1080, 720, 360, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(mDuration * 3 / 2),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 0.5f, 0.1f).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 0.5f, 0.1f).setDuration(mDuration)

        );
    }
}
