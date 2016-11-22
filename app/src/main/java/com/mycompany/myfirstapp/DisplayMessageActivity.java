package com.mycompany.myfirstapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //接收传过来的值
        Intent intent = getIntent();
        String v = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = (TextView) findViewById(R.id.tv_intent);
        textView.setText(v);

        ImageView iv_size = (ImageView) findViewById(R.id.iv_size);
        iv_size.setImageBitmap(decodeImg(getResources(),R.drawable.login_bg,50,100));
        //iv_size.setImageBitmap(R.drawable.login_bg);
//        iv_size.setImageResource(R.drawable.login_bg);

    }
    //根据新宽高，算出缩放倍数
    private static int calculateInSampleSize(BitmapFactory.Options options,int w,int h){
        final  int height = options.outHeight;
        final  int widht = options.outWidth;
        int inSampleSize = 1;
        if(height>h || widht>w){
            final int halfH = height/2;
            final int halfW = widht/2;
            while ((halfH/inSampleSize>h && (halfW/inSampleSize)>w)){
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    //缩小图片
    public static Bitmap decodeImg(Resources res,int resId,int w,int h){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId,options);

        options.inSampleSize = calculateInSampleSize(options,w,h);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

}
