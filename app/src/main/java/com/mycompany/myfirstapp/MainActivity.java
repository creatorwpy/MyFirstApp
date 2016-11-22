package com.mycompany.myfirstapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mycompany.base.BaseActivity;
import com.mycompany.model.BaseResponse;
import com.mycompany.util.HttpUtils;
import com.mycompany.util.LogDebug;
import com.mycompany.util.SP;

import java.lang.reflect.Type;

public class MainActivity extends BaseActivity {

    public Button btn, btn_camera, btn_luxiang, btn_print,bt_test;
    public SP sp;

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v("activity生命周期", "onCreate");

        sp = new SP(this);
        //按下设备的音量键都能够影响我们指定的音频流
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //去拍照
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 123);
                }
            }
        });

        btn_luxiang = (Button) findViewById(R.id.btn_luxiang);
        btn_luxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //去录像
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 2);
                }
            }
        });
/*
        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });*/
        bt_test = (Button) findViewById(R.id.btn_test);
        bt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("a","开始请求");
                Toast.makeText(MainActivity.this,"点击了",Toast.LENGTH_SHORT).show();
                HttpUtils.RequestParam params = new HttpUtils.RequestParam();
                params.add("action","test");
                params.add("username","wpy");
                showProgress("正在加载中...");
                HttpUtils.getInstance().url("http://192.168.10.57/function/temp.php?").params(params).executeGet(new HttpUtils.HttpListener(){
                    @Override
                    public void onException(Throwable e) {
                        super.onException(e);
                        dismissProgress();
                        showToastShort("网络错误");
                    }

                    @Override
                    public void onHttpSuccess(String string) {
                        super.onHttpSuccess(string);
                        dismissProgress();
                        Gson gson = new Gson();
                        TypeToken<BaseResponse> typeToken = new TypeToken<BaseResponse>(){};
                        Type type = typeToken.getType();
                        BaseResponse baseResponse = gson.fromJson(string,type);
                        LogDebug.d(baseResponse.getApi_code());
                        LogDebug.d(baseResponse.getApi_msg());
                    }
                });


            }
        });
    }

    //拍照/录象后会回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 123) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ImageView iv_camera = (ImageView) findViewById(R.id.iv_camera);
                iv_camera.setImageBitmap(imageBitmap);
            } else if (requestCode == 2) {
                Uri videoUri = data.getData();
                VideoView vv_luxiang = (VideoView) findViewById(R.id.vv_luxiang);
                vv_luxiang.setVideoURI(videoUri);
            }
        }
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText edittext = (EditText) findViewById(R.id.edit_message);
        String v = edittext.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, v);
        startActivity(intent);
        //finish();//将此activity销毁，就不能返回了
    }

    //添加菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //绑定菜单事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Log.v("menu", "search");
                break;
            case R.id.action_settings:
                Log.v("menu", "settings");
                break;
            case R.id.menu_fragment:
                Log.v("menu", "fragment");
                //动态添加fragment
                if (findViewById(R.id.article_fragment2) != null) {
                    BlankFragment blankfragment = new BlankFragment();
                    blankfragment.setArguments(getIntent().getExtras());
                    getSupportFragmentManager().beginTransaction().add(R.id.article_fragment2, blankfragment).commit();
                }
                break;
            case R.id.menu_fragment_del:
                break;
            case R.id.menu_set_sp:

                sp.set("test", "test123");
                Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_get_sp:
                String value = "取到的值是:";
                String value2 = value.concat(sp.get("test", ""));
                Toast.makeText(this, value2, Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_share:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "这里是分享的内容");
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.menu_tell:
                Uri number = Uri.parse("tel:762");
                //Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
                break;
            case R.id.menu_xinghuilogin:
                Intent intent1 = new Intent(this, XinghuiLogin.class);
                startActivity(intent1);
                break;
            case R.id.menu_view_anim:
                startActivity(new Intent(this, ViewAnimActivity.class));
                break;
            case R.id.menu_viewpager:
                startActivity(new Intent(this,ViewPagerActivity.class));
                break;
            case R.id.menu_xh:
                startActivity(new Intent(this,XHMain.class));
                break;
            case R.id.menu_xh2:
                startActivity(new Intent(this,XHGridView.class));
                break;
            case R.id.menu_xh_recyc:
                startActivity(new Intent(this,XHRecycleView.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //隐藏到后台，或开了其它activty之后调用
    @Override
    protected void onPause() {
        super.onPause();
        Log.v("activity生命周期", "onPause");
    }

    //从后台开启时调用1
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("activity生命周期", "onRestart");
    }

    //从后台开启时调用2
    @Override
    protected void onStart() {
        super.onStart();
        Log.v("activity生命周期", "onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("activity生命周期", "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("activity生命周期", "onStop");
    }
}
