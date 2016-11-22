package library.mlibrary.view.recyclerview.grouprecyclerview;

import android.content.Context;

import java.util.ArrayList;

import library.mlibrary.view.recyclerview.RecyclerView;

/**
 * Created by Harmy on 2016/7/20 0020.
 */
public abstract class AbsBaseDataGroupAdapter<GH extends RecyclerView.ViewHolder, VH extends RecyclerView.ViewHolder, T> extends AbsBaseGroupAdapter<GH, VH> {
    private ArrayList<T> mDatas;

    public AbsBaseDataGroupAdapter(Context context, int layoutId, ArrayList<T> datas) {
        super(context, layoutId);
        mDatas = datas;
    }

    public AbsBaseDataGroupAdapter(Context context, int layoutId, int grouplayoutid, ArrayList<T> datas) {
        super(context, layoutId, grouplayoutid);
        mDatas = datas;
    }

    public AbsBaseDataGroupAdapter(Context context, ArrayList<T> datas) {
        super(context);
        mDatas = datas;
    }

    public ArrayList<T> getDatas() {
        return mDatas;
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
