package com.mycompany.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mycompany.model.XHMainListModel;
import com.mycompany.myfirstapp.R;

import java.util.List;

/**
 * Created by X230 on 2016/9/20.
 */
public class XHMainAdapter extends BaseAdapter {
    private List<XHMainListModel> mList;
    private LayoutInflater mInflater;
    public XHMainAdapter(Context content, List<XHMainListModel> data) {
        mList = data;
        mInflater = LayoutInflater.from(content);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            view=mInflater.inflate(R.layout.xh_main_list_item,null);

            viewHolder.tvTitle = (TextView) view.findViewById(R.id.xh_main_title);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.xh_main_content);
            viewHolder.tvDate = (TextView) view.findViewById(R.id.xh_main_date);

            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tvTitle.setText(mList.get(i).newsTitle);
        viewHolder.tvContent.setText(mList.get(i).newsContent);
        viewHolder.tvDate.setText(mList.get(i).newsDate);
        return view;
    }

    class ViewHolder{
        public TextView tvTitle,tvContent,tvDate;
    }
}
