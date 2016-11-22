package com.mycompany.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mycompany.adapter.XHMainAdapter;
import com.mycompany.model.XHMainListModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class XHMain extends Activity {

    private ListView mListView;
    public View moreView;
    public Button bt;
    public ProgressBar pg;
    public XHMainAdapter adapter;
    public List<XHMainListModel> newBeanList = new ArrayList<>();
    public int page=0;
    private static String http_url="http://ce.sysu.edu.cn/hope/hopedairyjson/Index.aspx";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xh_activity_main);

        moreView = getLayoutInflater().inflate(R.layout.xh_main_list_moredata,null);
        bt = (Button) moreView.findViewById(R.id.xh_bt_more);
        pg = (ProgressBar) moreView.findViewById(R.id.xh_pg);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg.setVisibility(View.VISIBLE);// 将进度条可见
                bt.setVisibility(View.GONE);// 按钮不可见
                getData();

            }
        });

        mListView = (ListView)findViewById(R.id.xh_main_list);
        getData();
//        new NewsAsyncTask().execute(http_url);
    }

    private void getData(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = ("http://www.258star.com/iumobile/apis/?action=xingxiu_list&limit=10&p="+(++page));
        Log.e("http:xingxiu_list",url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,byte[] response) {
                // called when response HTTP status is "200 OK"
                Log.e("http:xingxiu_list","onSuccess");
                Log.e("http:xingxiu_list",new String(response));
                List<XHMainListModel> tmplist = new ArrayList<>();
                XHMainListModel newBean;
                try {
                    JSONObject obj = new JSONObject(new String(response));
                    String str = obj.getJSONArray("response").get(0).toString();
                    obj = new JSONObject(str);
                    JSONArray arr = obj.getJSONArray("data");
                    for(int i=0;i<arr.length();i++){
                        JSONObject tmp = arr.getJSONObject(i);
                        newBean = new XHMainListModel();
                        newBean.newsTitle = tmp.getString("nickname");
                        newBean.newsContent = tmp.getString("piaoshu2");
                        newBean.newsDate = tmp.getString("current");
                        tmplist.add(newBean);
                        Log.v("json:xingxiu_list",tmp.getString("nickname"));
                    }
                    Message msg = new Message();
                    msg.what=1;
                    msg.obj=tmplist;
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
                    //newBeanList
                    List<XHMainListModel> tmp = (List<XHMainListModel>) msg.obj;
                    for (int i=0;i<tmp.size();i++){
                        newBeanList.add(tmp.get(i));
                    }
                    if(page==1){
                        adapter = new XHMainAdapter(XHMain.this,newBeanList);
                        mListView.addFooterView(moreView);
                        mListView.setAdapter(adapter);
                    }
                    bt.setVisibility(View.VISIBLE);
                    pg.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    /*
    class NewsAsyncTask extends AsyncTask<String,Void,List<XHMainListModel>>{
        @Override
        protected List<XHMainListModel> doInBackground(String... strings) {
            try{
                return getJsonData(strings[0]);
            }catch (IOException e){
    class NewsAsyncTask extends AsyncTask<String,Void,List<XHMainListModel>>{
        @Override
        protected List<XHMainListModel> doInBackground(String... strings) {
            try{
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<XHMainListModel> xhMainListModels) {
            super.onPostExecute(xhMainListModels);
            XHMainAdapter adapter = new XHMainAdapter(XHMain.this,xhMainListModels);
            mListView.setAdapter(adapter);
        }
    }

    private List<XHMainListModel> getJsonData(String url)throws IOException{
        List<XHMainListModel> newBeanList = new ArrayList<>();
        String jsonString;
        try{
            jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            XHMainListModel newBean;
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i=0;i<jsonArray.length();i++){
                jsonObject = jsonArray.getJSONObject(i);
                newBean = new XHMainListModel();
                newBean.newsTitle = jsonObject.getString("name");
                newBean.newsContent = jsonObject.getString("content");
                newBean.newsDate = jsonObject.getString("date");
                newBeanList.add(newBean);
            }


        }catch (IOException e){            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }        return newBeanList;

    }    private String readStream(InputStream is){


        InputStreamReader isr;
        String result="";
        try{
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while((line = br.readLine()) != null) {
                result += line;
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }*/
}
