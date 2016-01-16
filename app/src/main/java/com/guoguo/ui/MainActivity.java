package com.guoguo.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.guoguo.R;
import com.guoguo.demos.dynamicProxy.ProxyTest;
import com.guoguo.logic.log.Log;
import com.guoguo.logic.prefs.AppPrefs;
import com.guoguo.logic.service.AppService;
import com.guoguo.logic.service.MyBinder;
import com.guoguo.logic.shortcut.AppShortCut;
import com.guoguo.logic.watchUs.OpenWeiChat;
import com.guoguo.ui.anmi.TweenedAnimation;
import com.guoguo.ui.toast.ShowToast;
import com.guoguo.ui.view.customListView.CustomListActivity;
import com.guoguo.ui.viewpager.MyViewPager;
import com.guoguo.utils.UIutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

public class MainActivity extends Activity {

    AppShortCut appShortCut = new AppShortCut();
    private Button btnClickHere = null;
    private MyBinder mBinder = null;
    private GridView mGridView = null;

    private static final int GRID_ITEM_START_SERVICE = 0;
    private static final int GRID_ITEM_STOP_SERVICE = 1;
    private static final int GRID_ITEM_MY_LIST = 2;
    private static final int GRID_ITEM_OPEN_WEICHAT = 3;
    private static final int GRID_ITEM_PROXY_TEST = 4;
    private static final int GRID_ITEM_VIEW_PAGER = 5;
    private static final int GRID_ITEM_ANIM = 6;
    private static final int GRID_ITEM_BANNER = 7;

    private static final int BANNER_MARGIN_DP = 10;
    private static final int BANNER_HEIGHT_DP = 160;

    LinearLayout mBannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

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
//        findViewById(R.id.button_clickhere).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        initBannerView();
        initGridView();
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

    private ServiceConnection mSrvConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MyBinder)service;
            mBinder.doSomething();
        }


        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private void initGridView() {
        mGridView = (GridView)findViewById(R.id.main_grid_view);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));//选中时高亮

        ArrayList<HashMap<String, Object>> lstItem = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> mapService = new HashMap<>();
        mapService.put("ItemText", "启动服务");
        lstItem.add(mapService);

        HashMap<String, Object> mapStopService = new HashMap<>();
        mapStopService.put("ItemText", "停止服务");
        lstItem.add(mapStopService);

        HashMap<String, Object> mapList = new HashMap<>();
        mapList.put("ItemText", "自定义list界面");
        lstItem.add(mapList);

        HashMap<String, Object> mapOpenWeiChat = new HashMap<>();
        mapOpenWeiChat.put("ItemText", "打开微信");
        lstItem.add(mapOpenWeiChat);

        HashMap<String, Object> mapTestProxy = new HashMap<>();
        mapTestProxy.put("ItemText", "动态代理测试");
        lstItem.add(mapTestProxy);

        HashMap<String, Object> mapViewPager = new HashMap<>();
        mapViewPager.put("ItemText", "ViewPager");
        lstItem.add(mapViewPager);

        HashMap<String, Object> mapAnim = new HashMap<>();
        mapAnim.put("ItemText", "tweened动画");
        lstItem.add(mapAnim);

        MainGrideAdapter manageGrideAdapter = new MainGrideAdapter(lstItem, this);
        mGridView.setAdapter(manageGrideAdapter);
        mGridView.setOnItemClickListener(mGridViewItemClick);
    }

    //GridView 是 AdapterView的子类
    private AdapterView.OnItemClickListener mGridViewItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case GRID_ITEM_START_SERVICE:
                    AppService.startService();
//                AppService.bindMyService(mSrvConn);
                    break;
                case GRID_ITEM_STOP_SERVICE:
                    AppService.stopService();
//                AppService.unbindMyService(mSrvConn);
                    break;
                case GRID_ITEM_MY_LIST:
                    startActivity(new Intent(MainActivity.this, CustomListActivity.class));//显式启动
                    break;
                case GRID_ITEM_OPEN_WEICHAT:
                    OpenWeiChat.openWeiChatApp(MainActivity.this);
                    break;
                case GRID_ITEM_PROXY_TEST:
                    ProxyTest.doProxyTest();
                    break;
                case GRID_ITEM_VIEW_PAGER:
                    startActivity(new Intent(MainActivity.this, MyViewPager.class));
                    //viewpager
                    break;
                case GRID_ITEM_BANNER:
                    //使用fragment
                    break;
                case GRID_ITEM_ANIM:
                    startActivity(new Intent(MainActivity.this, TweenedAnimation.class));
                    break;
                default:
                    break;
            }
        }
    };

    private void initBannerView() {
        mBannerView = (LinearLayout)findViewById(R.id.main_banner_view);

        int nMargin = UIutils.dip2px(this, BANNER_MARGIN_DP);
        int nSeparate = UIutils.dip2px(this, BANNER_MARGIN_DP);
        int nScreenSize[] = UIutils.getScreenSize((Activity) this);
        int nWidth = (nScreenSize[0] - nMargin * 3) / 2;
        int nHeight = BANNER_HEIGHT_DP - nMargin * 2;

        ImageView imgView1 = initImagView(nMargin, nMargin, nSeparate/2, 0, nWidth, nHeight);
        ImageView imgView2 = initImagView(nSeparate/2, nMargin, nMargin, 0, nWidth, nHeight);
        ImageView imgView3 = initImagView(nMargin, nMargin, nSeparate/2, nMargin, nWidth, nHeight);
        ImageView imgView4 = initImagView(nSeparate/2, nMargin, nMargin, nMargin, nWidth, nHeight);

        mBannerView.addView(imgView1);
        mBannerView.addView(imgView2);
        mBannerView.addView(imgView3);
        mBannerView.addView(imgView4);

    }

    private ImageView initImagView(int nLeftMargin, int nTopMargin, int nRightMargin, int nBottomMargin, int nWidth, int nHeight) {
        ImageView img = new ImageView(this);
        LinearLayout.LayoutParams para;
        para = new LinearLayout.LayoutParams(0, 0);
        para.width = nWidth;
        para.height = nHeight;
        para.setMargins(nLeftMargin, nTopMargin, nRightMargin, nBottomMargin);
        img.setLayoutParams(para);
        return img;
    }
}

