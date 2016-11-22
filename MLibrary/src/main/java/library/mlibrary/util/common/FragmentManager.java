package library.mlibrary.util.common;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class FragmentManager {
    private Context mContext;
    private Fragment mParent;
    private int resId;
    private Fragment mCurrentFragment = null;

    public FragmentManager(Context context, int layoutid) {
        mContext = context;
        resId = layoutid;
    }

    public void showFragment(Fragment fragment) {
        if (fragment == null) {
//            Toast.makeText(mContext, "你逗我啊o(╯□╰)o", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mCurrentFragment == fragment) {
//            Toast.makeText(mContext, "哎呀，不要再点啦/(ㄒoㄒ)/~~", Toast.LENGTH_SHORT)
//                    .show();
            return;
        }
        android.support.v4.app.FragmentManager fm = null;
        fm = ((FragmentActivity) mContext).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.in_fragment, R.anim.out_fragment);
        if (!fragment.isAdded()) {
            ft.add(resId, fragment);
        }
        if (mCurrentFragment != null)
            ft.hide(mCurrentFragment);
        ft.show(fragment);
        ft.commit();
        mCurrentFragment = fragment;
    }


    public FragmentManager(Fragment fragment, int layoutid) {
        mParent = fragment;
        resId = layoutid;
    }

    public void showChildFragment(Fragment fragment) {
        if (fragment == null) {
//            Toast.makeText(mParent.getContext(), "你逗我啊o(╯□╰)o", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mCurrentFragment == fragment) {
//            Toast.makeText(mParent.getContext(), "哎呀，不要再点啦/(ㄒoㄒ)/~~", Toast.LENGTH_SHORT)
//                    .show();
            return;
        }
        android.support.v4.app.FragmentManager fm = null;
        fm = mParent.getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.in_fragment, R.anim.out_fragment);
        if (!fragment.isAdded()) {
            ft.add(resId, fragment);
        }
        if (mCurrentFragment != null)
            ft.hide(mCurrentFragment);
        ft.show(fragment);
        ft.commit();
        mCurrentFragment = fragment;
    }

    public void hideFragment(Fragment fragment) {
        if (fragment == null) {
//            Toast.makeText(mContext, "你逗我啊o(╯□╰)o", Toast.LENGTH_SHORT).show();
            return;
        }
        android.support.v4.app.FragmentManager fm = null;
        fm = ((FragmentActivity) mContext).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.in_fragment, R.anim.out_fragment);
        if (!fragment.isAdded()) {
            return;
        }
        ft.hide(fragment);
        ft.commit();
        if (mCurrentFragment == fragment) {
            mCurrentFragment = null;
        }
    }

    public void hideChildFragment(Fragment fragment) {
        if (fragment == null) {
//            Toast.makeText(mParent.getContext(), "你逗我啊o(╯□╰)o", Toast.LENGTH_SHORT).show();
            return;
        }
        android.support.v4.app.FragmentManager fm = null;
        fm = mParent.getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.setCustomAnimations(R.anim.in_fragment, R.anim.out_fragment);
        if (!fragment.isAdded()) {
            return;
        }
        ft.hide(fragment);
        ft.commit();
        if (mCurrentFragment == fragment) {
            mCurrentFragment = null;
        }
    }
}
