package com.guoguo.logic.phone;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.guoguo.R;
import com.guoguo.app.GuoGuoApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2015/10/30.
 */
public class AppsManager {
    public static ArrayList<String> getInstalledPkgs() {
        ArrayList<String> pkgNames = new ArrayList<String>();
        PackageManager pkgMgr = GuoGuoApplication.mContext.getPackageManager();
        List<PackageInfo> appInfoList = pkgMgr.getInstalledPackages(0);
        for (PackageInfo pkgInfo : appInfoList) {
            pkgNames.add(pkgInfo.packageName);
        }
        return pkgNames;
    }

    public static ArrayList<PackageInfo> getInstalledPkgInfos() {
        PackageManager pkgMgr = GuoGuoApplication.mContext.getPackageManager();
        List<PackageInfo> appInfoList = pkgMgr.getInstalledPackages(0);
        ArrayList<PackageInfo> pkgInfos = new ArrayList<PackageInfo>();
        pkgInfos.addAll(appInfoList);
        return pkgInfos;
    }

    public static Drawable getAppIcon(String strPkgName) {
        Drawable icon = null;
        PackageManager pkgMgr = GuoGuoApplication.mContext.getPackageManager();
        try {
            icon = pkgMgr.getApplicationIcon(strPkgName);
        } catch (PackageManager.NameNotFoundException e) {
            icon = GuoGuoApplication.mContext.getDrawable(R.mipmap.logo);
        }
        return icon;
    }
}
