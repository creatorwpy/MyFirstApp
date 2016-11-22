package com.mycompany.myfirstapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;

public class ViewAnimActivity extends AppCompatActivity {
    private ScrollView content;
    private ProgressBar loading;
    private Button btn;
    private Boolean isshow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_anim);
        content = (ScrollView) findViewById(R.id.view_anim_content);
        loading = (ProgressBar) findViewById(R.id.view_anim_loading);
        btn = (Button) findViewById(R.id.view_anim_btn);

        loading.setVisibility(View.GONE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isshow = !isshow;
                if (isshow) {
                    content.setAlpha(0f);
                    content.setVisibility(View.VISIBLE);
                    content.animate().alpha(1f).setDuration(500).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            Log.e("view", "view ok");
                        }
                    });

                    loading.setVisibility(View.GONE);
                } else {
                    content.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
