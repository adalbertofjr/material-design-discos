package com.adalbertofjr.discos.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.adalbertofjr.discos.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AdalbertoF on 30/01/2016.
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
    }
}
