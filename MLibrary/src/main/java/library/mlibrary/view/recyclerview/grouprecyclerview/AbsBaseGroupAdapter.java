package library.mlibrary.view.recyclerview.grouprecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.util.inject.InjectUtil;
import library.mlibrary.view.recyclerview.AbsBaseAdapter;
import library.mlibrary.view.recyclerview.RecyclerView;

/**
 * Created by Harmy on 2016/5/16 0016.
 */
public abstract class AbsBaseGroupAdapter<GH extends RecyclerView.ViewHolder, VH extends RecyclerView.ViewHolder> extends AbsBaseAdapter<VH> implements GroupRecyclerImp<GH> {
    private int mGroupLayoutId;

    public AbsBaseGroupAdapter(Context context, int layoutId) {
        super(context, layoutId);
        mGroupLayoutId = -1;
    }

    public AbsBaseGroupAdapter(Context context, int layoutId, int grouplayoutid) {
        super(context, layoutId);
        mGroupLayoutId = grouplayoutid;
    }

    public AbsBaseGroupAdapter(Context context) {
        super(context);
        mGroupLayoutId = -1;
    }


    @Override
    public GH onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (mGroupLayoutId != -1) {
            itemView = LayoutInflater.from(getContext()).inflate(mGroupLayoutId, parent, false);
        } else {
            itemView = createGroupItemView(parent, viewType);
        }
        GH holder = createGroupHolder(parent, itemView, viewType);
        injectView(holder, itemView);
        return holder;
    }

    protected abstract GH createGroupHolder(ViewGroup parent, View itemView, int viewType);

    public View createGroupItemView(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * 通过注解自动绑定资源ID
     */
    private void injectView(GH holder, View itemView) {
        InjectUtil.injectObjectFields(holder, itemView);
    }

    @Override
    public int getGroupItemType(int position) {
        return 0;
    }

    @Override
    public long getHeaderId(int position) {
        String string = getHeaderString(position);
        if (CommonUtil.isEmpty(string)) {
            return 0;
        }
        int len = string.length();
        String result = "";
        for (int i = 0; i < len; i++) {
            result += (int)(string.charAt(i));
        }
        return Long.parseLong(result);
    }

    public String getHeaderString(int position) {
        return null;
    }

    @Override
    public boolean showHeader(int position) {
        return true;
    }
}
