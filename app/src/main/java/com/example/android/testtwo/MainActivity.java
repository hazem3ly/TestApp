package com.example.android.testtwo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.android.testtwo.Adapters.myFragmentPagerAdapter;
import com.example.android.testtwo.Fragments.DoctorFragment;
import com.example.android.testtwo.Fragments.DrugsFragment;
import com.example.android.testtwo.Fragments.HospitalFragment;
import com.example.android.testtwo.Fragments.PharmacyFragment;
import com.example.android.testtwo.Fragments.TimerFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.tabhost;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{

    private ViewPager viewPager;
    private TabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        initViewPager();

        initTabHost();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }

    private void initTabHost() {

        mTabHost = (TabHost) findViewById(tabhost);
        mTabHost.setup();
        mTabHost.setOnTabChangedListener(this);

        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("", ResourcesCompat.getDrawable(getResources(), R.drawable.pharmacy, null)).setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String s) {
                return new View(getApplicationContext());
            }
        }));
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("",  ResourcesCompat.getDrawable(getResources(), R.drawable.house,null)).setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String s) {
                return new View(getApplicationContext());
            }
        }));
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setIndicator("",  ResourcesCompat.getDrawable(getResources(),R.drawable.clc,null)).setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String s) {
                return new View(getApplicationContext());
            }
        }));
        mTabHost.addTab(mTabHost.newTabSpec("tab4").setIndicator("", ResourcesCompat.getDrawable(getResources(),R.drawable.drugs,null)).setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String s) {
                return new View(getApplicationContext());
            }
        }));
        mTabHost.addTab(mTabHost.newTabSpec("tab5").setIndicator("",  ResourcesCompat.getDrawable(getResources(),R.drawable.doctor,null)).setContent(new TabHost.TabContentFactory() {
            @Override
            public View createTabContent(String s) {
                return new View(getApplicationContext());
            }
        }));

    }

    private void initViewPager() {

        viewPager = (ViewPager) findViewById(R.id.my_view_pager);
        viewPager.addOnPageChangeListener(this);

        List<Fragment> myFragmentList = new ArrayList<Fragment>();
        myFragmentList.add(new PharmacyFragment());
        myFragmentList.add(new HospitalFragment());
        myFragmentList.add(new TimerFragment());
        myFragmentList.add(new DrugsFragment());
        myFragmentList.add(new DoctorFragment());


        myFragmentPagerAdapter fragmentPagerAdapter = new myFragmentPagerAdapter
                (getSupportFragmentManager(),myFragmentList);

        viewPager.setAdapter(fragmentPagerAdapter);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        mTabHost.setCurrentTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {

        viewPager.setCurrentItem(mTabHost.getCurrentTab());
        setSelectedTabColor();
    }

    private void setSelectedTabColor() {
        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
        {
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundColor(0);
        }
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab())
                .setBackgroundColor(Color.GRAY);
    }



    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
