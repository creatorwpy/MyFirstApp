package com.mycompany.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mycompany.adapter.DividerGridItemDecoration;
import com.mycompany.adapter.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
public class XHRecycleView extends Activity {
//public class XHRecycleView extends AbsBase {
    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private  HomeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xh_recyclerview);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.xh_recyclerview);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        mRecyclerView.setAdapter(mAdapter=new HomeAdapter());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
    }
    protected  void initData(){
        mDatas = new ArrayList<String>();
        for(int i='A';i<'z';i++){
            mDatas.add(""+(char)i);
        }
    }
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{
        private View.OnClickListener mOnClickListener;
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(XHRecycleView.this).inflate(R.layout.xh_recyclerview_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                this.tv = (TextView) itemView.findViewById(R.id.id_num);
            }
        }
    }
}
