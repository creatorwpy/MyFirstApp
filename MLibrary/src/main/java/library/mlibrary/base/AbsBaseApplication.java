package library.mlibrary.base;

import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDexApplication;

import java.util.Stack;

/**
 * Created by Harmy on 2016/4/7 0007.
 */
public abstract class AbsBaseApplication extends MultiDexApplication {
    private static Stack<Activity> mActivitys;
    protected static AbsBaseApplication mAbsBaseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mAbsBaseApplication = this;
        newActivityStack();
    }

    private void newActivityStack() {
        if (mActivitys == null) {
            mActivitys = new Stack<Activity>();
        }
    }

    private void finishAllActivity() {
        while (!mActivitys.isEmpty()) {
            mActivitys.pop().finish();
        }
    }

    public static AbsBaseApplication getAbsBaseApp() {
        return mAbsBaseApplication;
    }

    public void addActivity(Activity activity) {
        if (!mActivitys.contains(activity)) {
            mActivitys.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (mActivitys.contains(activity)) {
            mActivitys.remove(activity);
        }
    }

    public Stack<Activity> getActivityStack() {
        return mActivitys;
    }

    public void exitApplication() {
        exitApplication(true);
    }

    public void exitApplication(boolean needbackground) {
        finishAllActivity();
        if (!needbackground) {
            System.exit(0);
        }
    }
    public void finishTop(){
        if (!mActivitys.isEmpty()) {
            mActivitys.pop().finish();
        }
    }
}
