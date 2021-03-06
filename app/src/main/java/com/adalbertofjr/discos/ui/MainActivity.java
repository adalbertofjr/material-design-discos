package com.adalbertofjr.discos.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.adalbertofjr.discos.R;
import com.adalbertofjr.discos.ui.fragments.ListaDiscosFavoritosFragment;
import com.adalbertofjr.discos.ui.fragments.ListaDiscosWebFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AdalbertoF on 30/01/2016.
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mViewPager.setAdapter(new DiscoPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class DiscoPagerAdapter extends FragmentPagerAdapter {
        public DiscoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return position == 0 ? new ListaDiscosWebFragment() :
                    new ListaDiscosFavoritosFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return position == 0 ?
                    getString(R.string.tab_todos) :
                    getString(R.string.tab_favoritos) ;
        }
    }
}
