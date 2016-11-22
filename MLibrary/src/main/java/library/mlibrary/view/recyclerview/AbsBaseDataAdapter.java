package library.mlibrary.view.recyclerview;


import android.content.Context;

import java.util.ArrayList;

import library.mlibrary.util.common.CommonUtil;

/**
 * Created by Harmy on 2015/8/17 0017.
 */
public abstract class AbsBaseDataAdapter<VH extends RecyclerView.ViewHolder, T> extends AbsBaseAdapter<VH> {
    private ArrayList<T> mDatas;

    public AbsBaseDataAdapter(Context context, ArrayList<T> datas) {
        super(context);
        mDatas = datas;
    }

    public AbsBaseDataAdapter(Context context, int layoutId, ArrayList<T> datas) {
        super(context, layoutId);
        mDatas = datas;
    }

    public ArrayList<T> getDatas() {
        return mDatas;
    }

    public T getItem(int position) {
        int size = getItemCount();
        if (size == 0) {
            return null;
        } else if (position > size - 1) {
            return mDatas.get(size - 1);
        } else
            return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        if (CommonUtil.isEmpty(mDatas)) {
            return 0;
        } else
            return mDatas.size();
    }
}
