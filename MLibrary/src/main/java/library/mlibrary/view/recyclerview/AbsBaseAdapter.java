package library.mlibrary.view.recyclerview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import library.mlibrary.util.inject.InjectUtil;


/**
 * Created by Harmy on 2015/8/17 0017.
 */
public abstract class AbsBaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private int mLayoutId;
    private Context mContext;

    public AbsBaseAdapter(Context context, int layoutId) {
        mLayoutId = layoutId;
        mContext = context;
    }

    public AbsBaseAdapter(Context context) {
        mContext = context;
        mLayoutId = -1;
    }

    protected abstract VH createViewHolder(ViewGroup parent, View itemView, int viewType);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
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

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        holder.itemView.setLongClickable(canLongClick);
        holder.itemView.setClickable(canClick);
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (canLongClick) {
                        mOnItemLongClickListener.onItemLongClick(v, position);
                    }
                    return true;
                }
            });
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (canClick) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });
        }
        afterBindViewHolder(holder, position);
        if (mOnFinishInitListener != null) {
            mOnFinishInitListener.onFinishInit(position);
        }
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

    private boolean canLongClick = true;

    public void setItemCanLongClick(boolean can) {
        canLongClick = can;
    }

    private boolean canClick = true;

    public void setItemCanClick(boolean can) {
        canClick = can;
    }

    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    private OnFinishInitListener mOnFinishInitListener;

    public void setOnFinishInitListener(OnFinishInitListener listener) {
        mOnFinishInitListener = listener;
    }

    public void notifyDataSetChangedHandler() {
        mHandler.sendEmptyMessage(0);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    notifyDataSetChanged();
                    break;
            }
        }
    };
}
