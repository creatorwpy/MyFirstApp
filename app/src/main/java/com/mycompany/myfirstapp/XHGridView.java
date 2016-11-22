package com.mycompany.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mycompany.adapter.XHMainGridAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class XHGridView extends Activity implements OnScrollListener  {

    // 设置一个最大的数据条数，超过即不再加载
    private int MaxDateNum;
    // 最后可见条目的索引
    private int lastVisibleIndex;
    private GridView gv;
    public int page=0;
    private XHMainGridAdapter adapter;
    private List<Map<String,String>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xh_activity_girdview);

        data = new ArrayList<Map<String, String>>();
        int w_width = getAndroiodScreenProperty("width");
        Log.v("width",w_width+"");
        gv= (GridView) findViewById(R.id.xh_girdlist);
        gv.setColumnWidth(w_width/2);
        adapter = new XHMainGridAdapter(this,data);
        gv.setAdapter(adapter);
        gv.setOnScrollListener(this);
        getData();
    }

    private void getData(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = ("http://www.258star.com/iumobile/apis/?action=xingxiu_list&limit=10&p="+(++page));
        Log.e("http:xingxiu_list",url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                Log.e("http:xingxiu_list","onSuccess");
                Log.e("http:xingxiu_list",new String(response));
                Map row;
                try {
                    JSONObject obj = new JSONObject(new String(response));
                    String str = obj.getJSONArray("response").get(0).toString();
                    obj = new JSONObject(str);
                    JSONArray arr = obj.getJSONArray("data");
                    for(int i=0;i<arr.length();i++){
                        JSONObject tmp = arr.getJSONObject(i);
                        row = new HashMap<String, String>();
                        row.put("yiming",tmp.getString("nickname"));
                        row.put("piaoshu2",tmp.getString("piaoshu2"));
                        row.put("yirenpic",tmp.getString("yirenpic"));
                        data.add(row);
                        Log.v("json:xingxiu_list",tmp.getString("nickname"));
                    }
                    Message msg = new Message();
                    msg.what=1;
                    msg.obj=data;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
    android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Log.e("handler_1:","来了");
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 计算最后可见条目的索引
        lastVisibleIndex = firstVisibleItem + visibleItemCount - 1;
/*Log.v("onScroll,firstVisibleItem:",firstVisibleItem+"");
Log.v("onScroll,visibleItemCount:",visibleItemCount+"");
Log.v("onScroll,totalItemCount:",totalItemCount+"");
Log.v("onScroll,lastVisibleIndex:",lastVisibleIndex+"");
Log.v("onScroll,data.size:",data.size()+"");*/
        // 所有的条目已经和最大条数相等，则移除底部的View
        if (totalItemCount == data.size()) {
            //Toast.makeText(this, "数据全部加载完成，没有更多数据！", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        // 滑到底部后自动加载，判断listview已经停止滚动并且最后可视的条目等于adapter的条目
        Log.v("onScrollStateChanged,i:",i+"");
        Log.v("onScrollStateChanged,lastVisibleIndex:",lastVisibleIndex+"");
        Log.v("onScrollStateChanged, adapter.getCount():", (adapter.getCount()+""));
        if (i == OnScrollListener.SCROLL_STATE_IDLE
                && lastVisibleIndex == (adapter.getCount()-1)) {
            getData();
        }
    }
    public int getAndroiodScreenProperty(String v){
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度（像素）
        int height= dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width/density);//屏幕宽度(dp)
        int screenHeight = (int)(height/density);//屏幕高度(dp)
        if(v.equals(width)){
            return screenWidth;
        }else{
            return screenHeight;
        }
    }
}
