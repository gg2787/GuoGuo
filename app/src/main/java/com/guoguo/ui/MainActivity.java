package com.guoguo.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.guoguo.R;
import com.guoguo.app.GuoGuoApplication;
import com.guoguo.prefs.AppPrefs;
import com.guoguo.shortcut.AppShortCut;
import com.guoguo.ui.toast.ShowToast;
import com.guoguo.utils.AbTest;

public class MainActivity extends AppCompatActivity {

    AppShortCut appShortCut = new AppShortCut();
    private Button btnClickHere = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guoguo_main_activity);

        AppPrefs.init(this);

        appShortCut.addShortCut(this);

        initView();

    }

    private void initView() {
        findViewById(R.id.button_clickhere).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AbTest.getAbTest()) {
                    ShowToast.showShortToast(v.getContext(), v.getContext().getString(R.string.toast_short));
                }
                else {
                    ShowToast.showLongToast(v.getContext(), v.getContext().getString(R.string.toast_long));
                }
            }
        });

        findViewById(R.id.btn_another_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));//显式启动
                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://10.20.240.57/123/marketcloudcfg.json")));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

