package library.mlibrary.view.expandlistview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import library.mlibrary.util.inject.InjectUtil;

/**
 * Created by Harmy on 2016/1/6 0006.
 */
public abstract class AbsBaseExpandListAdapter<VHParent extends RecyclerView.ViewHolder, VHChild extends RecyclerView.ViewHolder> extends BaseExpandableListAdapter {

    private Context mContent;
    private int mParentRes;
    private int mChildRes;

    public AbsBaseExpandListAdapter(Context Context, int parentRes, int childRes) {
        mContent = Context;
        mParentRes = parentRes;
        mChildRes = childRes;
    }

    public AbsBaseExpandListAdapter(Context Context) {
        mContent = Context;
        mParentRes = -1;
        mChildRes = -1;
    }

    public Context getContext() {
        return mContent;
    }

    public View createParentView(ViewGroup parent, int viewType) {
        return null;
    }

    public View createChildView(ViewGroup parent, int viewType) {
        return null;
    }

    public abstract VHParent createParentHolder(ViewGroup parent, View itemView, int viewType);

    public abstract void onBindParentHolder(VHParent parentHolder, int groupPosition, boolean isExpanded);

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        VHParent parentholder = null;
        if (convertView == null) {
            if (mParentRes != -1) {
                convertView = LayoutInflater.from(getContext()).inflate(mParentRes, parent, false);
            } else {
                convertView = createParentView(parent, getGroupType(groupPosition));
            }
            parentholder = createParentHolder(parent, convertView, getGroupType(groupPosition));
            injectView(parentholder, convertView);
            convertView.setTag(parentholder);
        } else {
            parentholder = (VHParent) convertView.getTag();
        }
        onBindParentHolder(parentholder, groupPosition, isExpanded);
        return convertView;
    }

    /**
     * 通过注解自动绑定资源ID
     */
    private void injectView(RecyclerView.ViewHolder holder, View itemView) {
        InjectUtil.injectObjectFields(holder, itemView);
    }

    public abstract VHChild createChildHolder(ViewGroup parent, View itemView, int viewType);

    public abstract void onBindChildHolder(VHChild childHolder, int groupPosition, int childPosition, boolean isLastChild);

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        VHChild childholder = null;
        if (convertView == null) {
            if (mChildRes != -1) {
                convertView = LayoutInflater.from(getContext()).inflate(mChildRes, parent, false);
            } else {
                convertView = createChildView(parent, getChildType(groupPosition, childPosition));
            }
            childholder = createChildHolder(parent, convertView, getChildType(groupPosition, childPosition));
            injectView(childholder, convertView);
            convertView.setTag(childholder);
        } else {
            childholder = (VHChild) convertView.getTag();
        }
        onBindChildHolder(childholder, groupPosition, childPosition, isLastChild);
        return convertView;
    }
}
