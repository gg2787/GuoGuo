package com.guoguo.ui.view.floatWindow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.guoguo.R;

/**
 * Created by Administrator on 2016/6/1.
 * 弹窗的activity
 */
public class dlgActivity extends Activity{

    private Dialog mUpgradeDialog;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        showDialog();
    }

    public void showDialog(){
        //GameLog.log(Tag, "showDialog");
        mUpgradeDialog = createDialog();
        mUpgradeDialog.show();
    }

    private Dialog createDialog(){
        //GameLog.log(Tag, "createDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setIcon(mContext.getResources().getDrawable(R.drawable.app_hot));
        builder.setTitle("升级");
        builder.setMessage("恭喜你，通关了！后续游戏更精彩，是否需要升级？");
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do upgrade work
//                Toast.makeText(mContext, "后台升级中...", Toast.LENGTH_LONG).show();
                dialog.cancel();
                dlgActivity.this.finish();
            }
        });
        builder.setNegativeButton("暂不升级", new  DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(mContext, "您已取消升级", Toast.LENGTH_LONG).show();
                dialog.cancel();
                dlgActivity.this.finish();
            }
        });
        return builder.create();
    }
}
