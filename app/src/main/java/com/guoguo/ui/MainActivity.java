package com.guoguo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.guoguo.R;
import com.guoguo.logic.log.Log;
import com.guoguo.logic.prefs.AppPrefs;
import com.guoguo.logic.shortcut.AppShortCut;
import com.guoguo.ui.notification.NotificationUtil;
import com.guoguo.ui.toast.ShowToast;

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

        setStatus(getIntent());
    }

    private void setStatus(Intent intent) {
        if (null == intent) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (null == bundle) {
            return;
        }
        ShowToast.showShortToast(this, bundle.getString("from"));
        Log.error("setStatus", bundle.getString("from"));
    }

    private void initView() {
        findViewById(R.id.button_clickhere).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //finish();
            }
        });

        findViewById(R.id.btn_another_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtil.sendSayHelloNormalNotification(v.getContext());
                //startActivity(new Intent(MainActivity.this, SecondActivity.class));//显式启动
               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com")));
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel::10086")));
            }
        });
    }

    /**
     * 创建menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * menu的点击响应函数
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.add_item:
                ShowToast.showShortToast(this,"menu_add_item");
                break;
            case R.id.remove_item:
                ShowToast.showShortToast(this,"menu_remove_item");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

