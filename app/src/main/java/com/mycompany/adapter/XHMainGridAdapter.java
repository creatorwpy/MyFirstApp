package com.mycompany.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mycompany.myfirstapp.R;
import com.mycompany.util.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by X230 on 2016/9/20.
 */
public class XHMainGridAdapter extends BaseAdapter {
    private List<Map<String, String>> mList;
    private LayoutInflater mInflater;

    public XHMainGridAdapter(Context content, List<Map<String, String>> data) {
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
        if (view == null) {
            viewHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.xh_gridview_list_item, null);
            viewHolder.name = (TextView) view.findViewById(R.id.xh_yiren_name);
            viewHolder.piaoshu2 = (TextView) view.findViewById(R.id.xh_yiren_piaoshu);
            viewHolder.number = (TextView) view.findViewById(R.id.xh_yiren_number);
            viewHolder.img = (ImageView) view.findViewById(R.id.xh_yiren_img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(mList.get(i).get("yiming"));
        viewHolder.piaoshu2.setText(mList.get(i).get("piaoshu2"));
        String url = "http://www.258star.com/static_data/showcover/"+mList.get(i).get("yirenpic");
        //String url = "http://www.258star.com/static_data/showcover/thumb800x800_" + mList.get(i).get("yirenpic");
        Log.v("url", url);
        Utils.showImage(view.getContext(), viewHolder.img, url);

        return view;
    }

    class ViewHolder {
        public TextView number, piaoshu2, name;
        public ImageView img;
    }
}
