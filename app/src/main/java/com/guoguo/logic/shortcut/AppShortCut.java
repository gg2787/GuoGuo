package com.guoguo.logic.shortcut;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;

import com.guoguo.ui.MainActivity;
import com.guoguo.R;
import com.guoguo.logic.prefs.AppPrefs;

import java.util.List;



/**
 * Created by Administrator on 2015/9/14.
 */
public class AppShortCut {
    private static String TAG = "AppShortCut";

    private static String[] arrayOfString;

    public static final int ACTION_ADD = 1;

    static
    {
        arrayOfString = new String[9];
        arrayOfString[0] = "com.sec.android.app.launcher";
        arrayOfString[1] = "org.adwfreak.launcher";
        arrayOfString[2] = "org.adw.launcher";
        arrayOfString[3] = "com.fede.launcher";
        arrayOfString[4] = "com.qihoo360.launcher";
        arrayOfString[5] = "net.qihoo.launcher";
        arrayOfString[6] = "com.lge.launcher";
        arrayOfString[7] = "com.huawei.android.launcher";
        arrayOfString[8] = "com.miui.home";
    }

    public boolean addShortCut(Context context) {
        if (!isNeedAddShortCut(context)) {
            return false;
        }

        boolean bCreate = createShortCut(context);
        if (bCreate) {
            setShortCutFlag(true);
            //KInfocHelper.reportShortCut(ACTION_ADD);
        }
        return bCreate;
    }

    private boolean isNeedAddShortCut(Context context) {
        boolean bShortCutAdd = getShortCutFlag();

        if (!bShortCutAdd) {
            bShortCutAdd = hasShortCut(context, context.getString(R.string.app_name));

            if (bShortCutAdd) {
                setShortCutFlag(true);
            }
        }
        return !bShortCutAdd;
    }

    private boolean createShortCut(Context context) {
        Intent addShortCutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        //图标
        Parcelable icon = Intent.ShortcutIconResource.fromContext(context, R.drawable.logo);
        addShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);

        //名字
        addShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));

        //启动时关联
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.setClass(context, MainActivity.class);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        startIntent.putExtra("fromShortCut", true);

        addShortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, startIntent);

        //不可重复
        addShortCutIntent.putExtra("duplicate", false);

        context.sendBroadcast(addShortCutIntent);

        return true;
    }

    private void setShortCutFlag(boolean bCreate) {
        if (!bCreate) {
            return;
        }
        AppPrefs.setBoolean(AppPrefs.SHORT_CUT_ADD, bCreate);
    }

    private boolean getShortCutFlag() {
        return AppPrefs.getBoolean(AppPrefs.SHORT_CUT_ADD);
    }

    private boolean hasShortCut(Context context, String strShortcutName) {
        boolean bHasShortCut = false;
        String strUrl = getUrlString(context);
        int nShortCutCnt = getShortCutCnt(context, strShortcutName, strUrl);
        if (nShortCutCnt > 0) {
            bHasShortCut = true;
        }
        return bHasShortCut;
    }

    private int getShortCutCnt(Context context, String strShortcutName, String strUrl) {
        Cursor cursor = null;
        int nShortCutCnt = 0;
        try {
            ContentResolver resolver = context.getContentResolver();
            cursor = resolver.query(Uri.parse(strUrl), null, "title=?",
                    new String[]{strShortcutName}, null);

            if (cursor == null) {
                nShortCutCnt = 0;
            } else {
                nShortCutCnt = cursor.getCount();
            }
        } catch (RuntimeException e) {
            String strErr = e.toString();
            strErr = e.toString();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return nShortCutCnt;
    }

    private String getUrlString(Context context) {
        String strUrl = "";
        String strAuthority = getAuthority(context);
        String strPkgName = getLauncherPackageName(context);

        if (strAuthority == null || strAuthority.length() < 0) {
            for (int i = 0; i < arrayOfString.length; i++) {
                if (strPkgName.contains(arrayOfString[i])) {
                    strAuthority = arrayOfString[i];
                    break;
                }
            }
        }

        if (strAuthority != null && strAuthority.trim().length() > 0) {
            strUrl = "content://" + strAuthority + "/favorites?notify=true";
        } else {
            if (android.os.Build.VERSION.SDK_INT < 8) {
                strUrl = "content://com.android.launcher.settings/favorites?notify=true";
            } else {
                strUrl = "content://com.android.launcher2.settings/favorites?notify=true";
            }
        }
        return strUrl;
    }

    //获取用于组成uri的authority（主机名）
    private String getAuthority(Context context) {
        try {
            String strPkgName = getLauncherPackageName(context);
            //获取到手机上已安装的包的信息，这些信息是从它们的AndroidManifest.xml中获取到的
            //GET_PROVIDERS表示，获取xml中 <provider 标签内的内容
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            boolean bPermission = false;

            for (PackageInfo pack : packs) {
                if (!strPkgName.equals(pack.packageName)) {
                    continue;
                }

                ProviderInfo[] providers = pack.providers;
                for (ProviderInfo provider : providers) {
                    bPermission = provider.readPermission != null && provider.readPermission.contains("READ_SETTINGS");
                    bPermission = bPermission || (provider.writePermission!= null && provider.writePermission.contains("WRITE_SETTINGS"));
                    if (bPermission) {
                        return provider.authority;
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            //Log.error(TAG, e.toString());
        }
        return null;
    }

    private String getLauncherPackageName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);//从所有已安装的应用中，获取到与intent相符合的activity
        if (res.activityInfo == null) {
            return "";
        }
        return res.activityInfo.packageName;//获取到这个activity所在的packagename,这里获取的是launcher特有的activity
    }
}


