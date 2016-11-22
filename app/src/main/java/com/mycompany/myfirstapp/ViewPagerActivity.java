package com.mycompany.myfirstapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mycompany.fragment.ViewPagerFragment;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private TextView view1, view2, view3, view4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        InitTextView();
        InitViewPager();
    }
    public void InitTextView() {
        view1 = (TextView) findViewById(R.id.tv_guid1);
        view2 = (TextView) findViewById(R.id.tv_guid2);
        view3 = (TextView) findViewById(R.id.tv_guid3);
        view4 = (TextView) findViewById(R.id.tv_guid4);
        view1.setOnClickListener(new txListener(0));
        view2.setOnClickListener(new txListener(1));
        view3.setOnClickListener(new txListener(2));
        view4.setOnClickListener(new txListener(3));
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;
        public txListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }
    public void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();
        Fragment btFragment =  new ViewPagerFragment();
        Fragment secondFragment =  new ViewPagerFragment();
        Fragment thirdFragment =  new ViewPagerFragment();
        Fragment fourthFragment =  new ViewPagerFragment();
        fragmentList.add(btFragment);
        fragmentList.add(secondFragment);
        fragmentList.add(thirdFragment);
        fragmentList.add(fourthFragment);

        //给ViewPager设置适配器,getFragmentManager,getSupportFragmentManager
        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
    }
    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;
        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }
    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int arg0) {
            TextView tmp;
            tmp=view1;
            if(arg0==0){
                tmp=view1;
            }else if(arg0==1){
                tmp=view2;
            }else if(arg0==2){
                tmp=view3;
            }else if(arg0==3){
                tmp=view4;
            }
            view1.setTextColor(0xff000000);
            view2.setTextColor(0xff000000);
            view3.setTextColor(0xff000000);
            view4.setTextColor(0xff000000);
            tmp.setTextColor(0xffff0000);
        }
    }
}