package library.mlibrary.view.banner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import library.mlibrary.R;
import library.mlibrary.util.inject.InjectUtil;

/**
 * Created by Harmy on 2016/4/22 0022.
 */
public abstract class AbsBannerPageAdapter<VH extends RecyclerView.ViewHolder, T> extends AbsBasePageAdapter<T> {
    public AbsBannerPageAdapter(Context Context, List<T> datas, int layoutid) {
        super(Context, datas);
        mResId = layoutid;
    }

    public AbsBannerPageAdapter(Context Context, List<T> datas) {
        super(Context, datas);
        mResId = -1;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = toRealPosition(position);

        View view = getView(realPosition, null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private Context mContent;
    private int mResId;

    public View createItemView(ViewGroup parent, int viewType) {
        return null;
    }

    public abstract VH createViewHolder(ViewGroup parent, View itemView, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    public int getViewType(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder = null;
        if (convertView == null) {
            if (mResId != -1) {
                convertView = LayoutInflater.from(getContext()).inflate(mResId, parent, false);
            } else {
                convertView = createItemView(parent, getViewType(position));
            }
            holder = createViewHolder(parent, convertView, getViewType(position));
            injectView(holder, convertView);
            convertView.setTag(R.id.cb_item_tag, holder);
        } else {
            holder = (VH) convertView.getTag(R.id.cb_item_tag);
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    /**
     * 通过注解自动绑定资源ID
     */
    private void injectView(RecyclerView.ViewHolder holder, View itemView) {
        InjectUtil.injectObjectFields(holder, itemView);
    }

    protected T getItem(int position) {
        return getDatas().get(position);
    }
}
