package com.guoguo.logic.phone;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

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
}
