package library.mlibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import library.mlibrary.util.common.FragmentManager;
import library.mlibrary.util.inject.InjectUtil;

/**
 * Created by Harmy on 2016/4/7 0007.
 */
public abstract class AbsBaseFragment extends Fragment {
    private View mLayoutView;
    private int mLayoutId = -1;
    protected AbsBaseFragment mThis;
    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThis = this;
        Bundle bundle=getArguments();
        if(bundle!=null){
            onGetArguments(bundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        beforeSetContentView();
        onSetContentView();
        afterSetContentView();
        if (mLayoutId != -1) {
            mLayoutView = inflater.inflate(mLayoutId, container, false);
        }
        return mLayoutView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectView();
        onFindView(mLayoutView);
        initViews();
        afterOnCreate(savedInstanceState);
        onSetListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public AbsBaseFragment getThis() {
        return mThis;
    }

    public View getLayoutView() {
        return mLayoutView;
    }

    /**
     * 通过注解自动绑定资源ID
     */
    private void injectView() {
        InjectUtil.injectFragmentFields(this, mLayoutView);
    }

    protected void beforeSetContentView() {

    }

    protected abstract void onSetContentView();

    protected void afterSetContentView() {

    }

    protected void onFindView(View view) {
    }

    protected abstract void initViews();

    protected abstract void onSetListener();

    protected abstract void afterOnCreate(Bundle savedInstanceState);

    public void setContentView(int id) {
        mLayoutId = id;
    }

    public void setContentView(View view) {
        mLayoutView = view;
    }

    protected void onBackPressed() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    protected void onGetArguments(Bundle bundle){}

    /**
     * 配合viewpager使用时，如果要完全切换过来时才需要执行的方法在onVisible里面写
     */
    public void onVisible() {
    }

    public void onInVisible() {
    }

    public void setNeedBackHandle() {
        if (getActivity() instanceof AbsBaseActivity) {
            ((AbsBaseActivity) getActivity()).setCurrentFragment(getThis());
        }
    }

    public void cancelNeedBackHandle() {
        if (getActivity() instanceof AbsBaseActivity) {
            if (((AbsBaseActivity) getActivity()).getCurrentFragment() == getThis()) {
                ((AbsBaseActivity) getActivity()).setCurrentFragment(null);
            }
        }
    }

    public FragmentManager initFragmentManager(int framlayout) {
        mFragmentManager = new FragmentManager(getThis(), framlayout);
        return mFragmentManager;
    }
}
