package library.mlibrary.view.pulltorefresh.pullview;

import android.content.Context;
import android.view.View;

/**
 * Created by Harmy on 2016/5/31 0031.
 */
public abstract class AbsBaseHeader {
    public abstract View onCreateHeaderView(Context context);

    public abstract View getHeaderView();

    public abstract void onInit();

    public abstract void onRelease();

    public abstract void onHeading();

    public abstract void onSuccess();

    public abstract void onFailure();

    public abstract void onDone();

    public void onPulling(float dist) {

    }
}
