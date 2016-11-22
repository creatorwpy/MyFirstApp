package library.mlibrary.view.flowlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import library.mlibrary.util.inject.InjectUtil;
import library.mlibrary.view.recyclerview.OnItemClickListener;
import library.mlibrary.view.recyclerview.OnItemLongClickListener;
import library.mlibrary.view.recyclerview.RecyclerView;


/**
 * Created by Harmy on 2015/8/17 0017.
 */
public abstract class AbsBaseFlowAdapter<VH extends RecyclerView.ViewHolder> {
    private int mLayoutId;
    private Context mContext;

    public AbsBaseFlowAdapter(Context context, int layoutId) {
        mLayoutId = layoutId;
        mContext = context;
    }

    public AbsBaseFlowAdapter(Context context) {
        mContext = context;
        mLayoutId = -1;
    }

    protected abstract VH createViewHolder(ViewGroup parent, View itemView, int viewType);

    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        mFlowLayout = (FlowLayout) parent;
        View itemView = null;
        if (mLayoutId != -1) {
            itemView = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
        } else {
            itemView = createItemView(parent, viewType);
        }
        VH holder = createViewHolder(parent, itemView, viewType);
        injectView(holder, itemView);
        return holder;
    }

    public View createItemView(ViewGroup parent, int viewType) {
        return null;
    }

    public void onBindViewHolder(VH holder, final int position) {
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
        afterBindViewHolder(holder, position);
    }

    protected abstract void afterBindViewHolder(VH holder, int position);

    /**
     * 通过注解自动绑定资源ID
     */
    private void injectView(VH holder, View itemView) {
        InjectUtil.injectObjectFields(holder, itemView);
    }

    public Context getContext() {
        return mContext;
    }


    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public abstract int getItemCount();

    public int getItemViewType(int position) {
        return 0;
    }

    private FlowLayout mFlowLayout;

    public void notifyDataChanged() {
        mFlowLayout.notifyDataChanged();
    }
}
