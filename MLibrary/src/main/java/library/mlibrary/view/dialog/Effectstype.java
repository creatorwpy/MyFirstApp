package library.mlibrary.view.dialog;

import library.mlibrary.view.dialog.effects.BaseEffects;
import library.mlibrary.view.dialog.effects.FadeIn;
import library.mlibrary.view.dialog.effects.FlipH;
import library.mlibrary.view.dialog.effects.FlipV;
import library.mlibrary.view.dialog.effects.FlipVDismiss;
import library.mlibrary.view.dialog.effects.NewsPaper;
import library.mlibrary.view.dialog.effects.NewsPaperDismiss;
import library.mlibrary.view.dialog.effects.RotateBottom;
import library.mlibrary.view.dialog.effects.RotateLeft;
import library.mlibrary.view.dialog.effects.RotateLeftDismiss;
import library.mlibrary.view.dialog.effects.SideFall;
import library.mlibrary.view.dialog.effects.SlideBottom;
import library.mlibrary.view.dialog.effects.SlideLeft;
import library.mlibrary.view.dialog.effects.SlideRight;
import library.mlibrary.view.dialog.effects.SlideTop;

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
public enum  Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    SlideBottomDismiss(library.mlibrary.view.dialog.effects.SlideBottomDismiss.class),
    Slideright(SlideRight.class),
    Fall(library.mlibrary.view.dialog.effects.Fall.class),
    Newspaper(NewsPaper.class),
    NewsPaperDismiss(NewsPaperDismiss.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    FlipvDismiss(FlipVDismiss.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    RotateLeftDismiss(RotateLeftDismiss.class),
    Slit(library.mlibrary.view.dialog.effects.Slit.class),
    Shake(library.mlibrary.view.dialog.effects.Shake.class),
    ShakeDismiss(library.mlibrary.view.dialog.effects.ShakeDismiss.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects=null;
	try {
		bEffects = effectsClazz.newInstance();
	} catch (ClassCastException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (InstantiationException e) {
		throw new Error("Can not init animatorClazz instance");
	} catch (IllegalAccessException e) {
		throw new Error("Can not init animatorClazz instance");
	}
	return bEffects;
    }
}
