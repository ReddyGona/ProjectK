package com.ReddyGona.projectk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    FragmentAdapter adapter;
    ViewPager2 pager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.viewPager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("Certificate"));
        tabLayout.addTab(tabLayout.newTab().setText("Profile"));
        tabLayout.addTab(tabLayout.newTab().setText("Chat"));
        tabLayout.addTab(tabLayout.newTab().setText("Score"));

        String check = getIntent().getStringExtra("profile");

        if (check != null) {
          if(check.equals("1")){
            tabLayout.setScrollPosition(1, 0f, true);
            pager2.setCurrentItem(1);
         }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }
}