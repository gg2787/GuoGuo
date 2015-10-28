package com.guoguo.logic.shortcut;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * [快捷方式实现类]<BR>
 * [1.页面需要继承 ShortCutImpl接口 2.界面调用showShortCutDialog方法即可]
 * 
 * @author Owen Xie
 */
public class ShortCutManager {
	private static final String ANDROID_LAUNCHER_PACKAGE = "com.google.android.googlequicksearchbox";
    protected static final String TAG = ShortCutManager.class.getSimpleName();
    
    private static ShortCutManager mInstance = null;
/*    public static final String CREATE_CUTSHOT = "cutshortstate";
    private static final String SHORTCUT_INFO = "cutshort";
    
    public static final String HUAWEI_U8860 = "U8860";
    public static final String GT5100 = "GT-N5100";
    public static final String OPPO_X909 = "X909";
    
    public int addtime = 0;
    private int mUpgradeNum = 0;
    private int mChangeNum = 0;
    private boolean isFromSetting = false;
    private static String[] arrayOfString;
    private SharedPreferences ShortCutPreferences = null;
    
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
    
    *//**
     * 获取当前launcher的包名
     * @param context
     * @return
     *//*
    public String getLauncherPackageName(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            return "";
        }
        return res.activityInfo.packageName;
    }

    *//**
     * 本类实例
     *//*
    private static ShortCutManager mInstance = null;

    *//**
     * 页面是否进入pause状态
     *//*
    protected boolean isPaused = false;

    public ShortCutManager(Context application) {
        ShortCutPreferences = application.getSharedPreferences(SHORTCUT_INFO, 0);
    }

    *//**
     * [判断是否需要谈框让用户选择是否添加快捷方式]<BR>
     * [功能详细描述]
     * 
     * @param activity 启动页面（通常）
     * @param observe 实现接口类
     * @param title dialog标题
     * @param message dialog内容
     * @param confirm 确定按钮文字
     * @param cancel 取消按钮文字
     *//*
    public void doShortCutDialog(final Activity activity, boolean isonCreate) {
        int ishasShortcut = ShortCutPreferences.getInt(CREATE_CUTSHOT, 0);
        Log.debug(TAG, "android.os.Build.MODEL:" + android.os.Build.MODEL);
        if (!hasShortCut(activity, activity == null ? "" : activity.getString(R.string.app_name)) && ishasShortcut == 0) {
            createShortCut(activity);
            Log.debug(TAG, "android.os.Build.MODEL createShortCut!");
        } else {
            if (!isonCreate) {
                Toast.makeText(activity, activity.getResources().getString(R.string.addcutshotmessage),
                        Toast.LENGTH_SHORT).show();
            }

            if (android.os.Build.MODEL.contains("V880") && ishasShortcut == 0) {
                createShortCut(activity);
                Log.debug(TAG, "android.os.Build.MODEL android.os.Build.MODEL.contains!");
            }
        }
    }

    public void addShortCutDialog(final Activity activity) {
        if (!hasShortCut(activity, activity == null? "" : activity.getString(R.string.app_name)) && addtime == 0) {
            if (createShortCut(activity)) {
                addtime++;
                    Toast.makeText(activity, activity.getResources().getString(R.string.addcutshotmessage),
                            Toast.LENGTH_SHORT).show();
            }

        } else {
            if (android.os.Build.MODEL.contains("V880") && addtime == 0) {
                if (createShortCut(activity)) {
                    addtime++;
                }
            }
        }
    }
    
    *//**
     * 是否已经有快捷方式按钮
     * 
     * @param context 上下文
     * @return true or false
     *//*
    private boolean hasShortCut(Context context, String shortcutName) {
        Cursor cursor = null;
        try {
            if (context == null)
                return false;
            String url = "";
            String str = "";
            str = getAuthority(context); 
            Log.debug(TAG, " str = " + str);
            if (str == null || str.length() < 0) {
                String name = getLauncherPackageName(MainTabActivity.mainTabActivity);
                if (str == null || str.length() < 0) {
                    for (int i = 0; i < arrayOfString.length; i++) {
                        if (name.contains(arrayOfString[i])) {
                            str = arrayOfString[i];
                            break;
                        }
                    }
                }
            }
            if (str != null && str.trim().length() > 0) {
                url = "content://" + str + "/favorites?notify=true";
            } else {
                if (android.os.Build.VERSION.SDK_INT < 8) {
                    url = "content://com.android.launcher.settings/favorites?notify=true";
                } else {
                    url = "content://com.android.launcher2.settings/favorites?notify=true";
                }
            }
            Log.debug(TAG, " url = " + url);
            ContentResolver resolver = context.getContentResolver();
            cursor = resolver.query(Uri.parse(url), null, "title=?",
                    new String[] { shortcutName }, null);
            if (cursor != null && cursor.moveToFirst()) {
                Log.debug(TAG, "android.os.Build.MODEL hasShortCut");
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            Log.printStackTrace(TAG, e);
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }
        return false;
    }
    
    private String getAuthority(Context context) {
        if (context == null) {
            return null;
        }
        
        try {
            String authority = getLauncherPackageName(context);
            Log.debug(TAG, "authority = " + authority);
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            for (PackageInfo pack : packs) {
                if (authority.equals(pack.packageName)) {
                    ProviderInfo[] providers = pack.providers;
                    for (ProviderInfo provider : providers) {
                        if (provider.readPermission.contains("READ_SETTINGS") || provider.writePermission.contains("WRITE_SETTINGS")) {
                            return provider.authority;
                        } else {
                            continue;
                        }
                    }
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            Log.printStackTrace(TAG, e);
        }
        
        return null;
    }
    
    *//**
     * 创建快捷方式
     * 
     * @param activity
     *//*
    private boolean createShortCut(Activity activity) {
        try {
            // 创建快捷方式的Intent
            Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            // 不允许重复创建
            shortcutintent.putExtra("duplicate", false);
            // 需要现实的名称
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));
            // 快捷图片
            Parcelable icon = Intent.ShortcutIconResource.fromContext(activity, R.drawable.logo);
            Bitmap iconBitmap = null;
            if (mUpgradeNum == 0 || UIutil.getScreen_height() <= 320 || UIutil.getScreen_height() == 782
                    || SPhoneHelper.getPhoneModel().equals(HUAWEI_U8860) || SPhoneHelper.getPhoneModel().equals(GT5100)
                    || SPhoneHelper.getPhoneModel().equals(OPPO_X909) || (!AppMarketSharePreferences.IsDisplayShortCutNum())) {
                shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            } else {
                iconBitmap = generatorContactCountIcon(activity,
                        ((BitmapDrawable) (activity.getResources().getDrawable(R.drawable.logo))).getBitmap());
                shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON, iconBitmap);
            }
            // 点击快捷图片，运行的程序主入口
            ComponentName comp = new ComponentName(activity.getPackageName(), activity.getPackageName() + "."  + activity.getLocalClassName());
            Intent intent = new Intent(Intent.ACTION_MAIN).setComponent(comp);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            //TODO 小米添加此句，可以创建成功
            //intent.putExtra("extra.from", 2);
            shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
            // 发送广播。OK
            activity.sendBroadcast(shortcutintent);
            if (iconBitmap != null && !iconBitmap.isRecycled()) {
                iconBitmap.isRecycled();
            }
            setFromSetting(false);
            ShortCutPreferences.edit().putInt(CREATE_CUTSHOT, 1).commit();
        } catch (Exception ee) {   
            setFromSetting(false);
            ShortCutPreferences.edit().putInt(CREATE_CUTSHOT, -1).commit();
            return false;
        }
        return true;
    }
    
    *//**
     * 创建oppo find5快捷方式
     * @param activity
     * @param count
     * @return
     *//*
    private Bitmap mkOPPOShotcut(Activity activity, int count) {
        Bitmap bmpShrtcut = Bitmap.createBitmap(160, 160, Config.ARGB_8888);
        Canvas mCnvs = new Canvas(bmpShrtcut);
        Paint mPnt = new Paint();
        mPnt.setDither(true);
        mPnt.setFilterBitmap(true);
        
        // Logo
        Bitmap bmpLogo = BitmapFactory.decodeResource(activity.getResources(), R.drawable.logo);
        Matrix matrix = new Matrix();
        matrix.postScale(bmpShrtcut.getWidth(), bmpShrtcut.getHeight());
        
        bmpLogo = Bitmap.createScaledBitmap(bmpLogo, (int)(bmpShrtcut.getWidth() * 0.9f), (int)(bmpShrtcut.getHeight() * 0.9f), true);
        mCnvs.drawBitmap(bmpLogo, (bmpShrtcut.getWidth() - bmpLogo.getWidth()) / 2, (bmpShrtcut.getHeight() - bmpLogo.getHeight()) / 2, mPnt);
        if (!bmpLogo.isRecycled()) bmpLogo.recycle();
        
        // 红色圈
        Bitmap bmpRedBg = BitmapFactory.decodeResource(activity.getResources(), R.drawable.red_background);
        mCnvs.drawBitmap(bmpRedBg, bmpShrtcut.getWidth() - bmpRedBg.getWidth(), 0, mPnt);
        if (!bmpRedBg.isRecycled()) bmpRedBg.recycle();
        
        // 数字
        Paint txtPnt = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        txtPnt.setColor(Color.WHITE);
        txtPnt.setTextSize(30);
        txtPnt.setTypeface(Typeface.DEFAULT_BOLD);
        float txtLength = txtPnt.measureText(String.valueOf(count));
        float txtLeft = bmpShrtcut.getWidth() - bmpRedBg.getWidth();
        mCnvs.drawText(String.valueOf(count), (bmpRedBg.getWidth() - txtLength) / 2 + txtLeft , 30, txtPnt);
        
        return bmpShrtcut;
    }
    

    private Bitmap generatorContactCountIcon(Activity activity, Bitmap icon) {
    	
    	if (SPhoneHelper.getPhoneModel().equals(OPPO_X909)) {
    		int contacyCount = 0;
            if (isFromSetting) {
               contacyCount = getmChangeNum();
            } else {
               contacyCount = AppMarketSharePreferences.getInt(AppMarketSharePreferences.shortCutUpgradeNum);
            }
    		return mkOPPOShotcut(activity, contacyCount);
    	}
    	
        // 初始化画布
        int iconSize = (int) activity.getResources().getDimension(android.R.dimen.app_icon_size);
        Bitmap contactIcon = Bitmap.createBitmap(iconSize, iconSize, Config.ARGB_8888);
        Canvas canvas = new Canvas(contactIcon);

        // 拷贝图片
        Paint iconPaint = new Paint();
        iconPaint.setDither(true);// 防抖动
        iconPaint.setFilterBitmap(true);// 用来对Bitmap进行滤波处理，这样，当你选择Drawable时，会有抗锯齿的效果

        Bitmap bitmap = scaleBitmap(icon);
        canvas.drawBitmap(bitmap, 0, iconSize - bitmap.getHeight(), iconPaint);

        int Screen_height = UIutil.getScreen_height();
        float textSize = 0;
        int size_x = 0;
        int size_y = 0;
        int size_top = 0;
        int size_left = 0;
        int size_right = 0 ;
        int redBgHight = 0;
        
        int contacyCount = 0;
        if (isFromSetting) {
           contacyCount = getmChangeNum();
        } else {
           contacyCount = AppMarketSharePreferences.getInt(AppMarketSharePreferences.shortCutUpgradeNum);
        }
        Log.debug(TAG, "isFromSetting = " + isFromSetting + " contacyCount = " + contacyCount);
        if (Screen_height >= 1776) {
            textSize = 32f;
            size_y = 40;
            size_right = 2;
            redBgHight = 85;
            size_top = 2;
            if (contacyCount >= 10) {
                size_x = 50;
            } else {
                size_x = 39;
            }
        } else if (Screen_height >= 1000) {
            textSize = 21f;
            size_y = 24;
            size_left = 3;
            size_right = 2;
            redBgHight = 65;
            size_top = 2;
            if (contacyCount >= 10) {
                size_x = 35;
            } else {
                size_x = 28;
            }
        } else if (Screen_height >= 800) {
            textSize = 16f;
            size_y = 20;
            redBgHight = 45;
            size_top = 1;
            if (contacyCount >= 10) {
                size_x = 25;
            } else {
                size_x = 20;
            }
        } else if (Screen_height >= 480) {
            textSize = 10f;
            size_y = 12;
            if (contacyCount >= 10) {
                size_x = 16;
            } else {
                size_x = 12;
            }
            redBgHight = 30;
        }
        Drawable drawable = activity.getResources().getDrawable(R.drawable.red_background);
        int dw = drawable.getIntrinsicWidth();
        drawable.setBounds(icon.getWidth() - dw - size_left, size_top, icon.getWidth() - size_right, icon.getHeight() - redBgHight);
        drawable.draw(canvas);

        // 启用抗锯齿和使用设备的文本字距
        Paint countPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
        countPaint.setColor(Color.WHITE);
        countPaint.setTextSize(textSize);
        countPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(String.valueOf(contacyCount), iconSize - size_x, size_y, countPaint);

        return contactIcon;
    }

    public Bitmap scaleBitmap(Bitmap bm) {
        int newWidth = 0;
        int newHeight = 0;

        int Screen_height = UIutil.getScreen_height();

        Log.debug(TAG, " Screen_height = " + Screen_height);
        if (Screen_height >= 1776) {
            newWidth = 124;
            newHeight = 124;
        } else if (Screen_height >= 1000) {
            newWidth = 84;
            newHeight = 84;
        } else if (Screen_height >= 800) {
            newWidth = 64;
            newHeight = 64;
        } else if (Screen_height >= 480) {
            newWidth = 44;
            newHeight = 44;
        }

        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        if (newbm != bm) {
            if (bm != null && !bm.isRecycled()) {
                bm.isRecycled();
            }
        }

        return newbm;
    }

    *//**
     * 删除快捷方式
     *//*
    public void delShortcut(Activity activity) {
        if (activity == null)
            return;
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));
        *//** 改成以下方式能够成功删除，估计是删除和创建需要对应才能找到快捷方式并成功删除 **//*
        Intent intent = new Intent();
        intent.setClass(activity, activity.getClass());
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory("android.intent.category.LAUNCHER");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        activity.sendBroadcast(shortcut);
    }
      
    *//**
     * 更新快捷方式
     *//*
    public void updateShortCutByNum() {
        if (!AppMarketSharePreferences.isSomeDay()) {
            if (UiInstance.getInstance().backToHome()) {
                int upgradeCount = AppMarketSharePreferences.getInt(AppMarketSharePreferences.shortCutUpgradeNum);
                Log.debug(TAG, "upgradeCount = " + upgradeCount + " mUpgradeNum = " + mUpgradeNum);
                if (!AppMarketSharePreferences.getBoolean(AppMarketSharePreferences.firstShortCutStart)) {
                    if (upgradeCount == 0 && mUpgradeNum == 0) {
                        AppMarketSharePreferences.setBoolean(AppMarketSharePreferences.firstShortCutStart, true);
                        AppMarketSharePreferences.setShortCutUpdateTime();
                        createShortCutByPhoneModel();
                    }
                    if ((upgradeCount == 0 && mUpgradeNum != 0) || (upgradeCount != 0 && mUpgradeNum == 0)) {
                        AppMarketSharePreferences.setBoolean(AppMarketSharePreferences.firstShortCutStart, true);
                        AppMarketSharePreferences.putInt(AppMarketSharePreferences.shortCutUpgradeNum, mUpgradeNum);
                        AppMarketSharePreferences.setShortCutUpdateTime();
                        createShortCutByPhoneModel();
                    }
                } else {
                    if ((upgradeCount == 0 && mUpgradeNum != 0) || (upgradeCount != 0 && mUpgradeNum == 0)) {
                        AppMarketSharePreferences.putInt(AppMarketSharePreferences.shortCutUpgradeNum, mUpgradeNum);
                        AppMarketSharePreferences.setShortCutUpdateTime();
                        createShortCutByPhoneModel();
                    }        
                }
            } 
        }
    }    
    
    *//**
     * 排除重复创建快捷方式的机型
     *//*
    private void createShortCutByPhoneModel() {
        if (HUAWEI_U8860.equals(SPhoneHelper.getPhoneModel())) {
            if (!AppMarketSharePreferences.getBoolean(AppMarketSharePreferences.duplicatePhoneModel)) {
                AppMarketSharePreferences.setBoolean(AppMarketSharePreferences.duplicatePhoneModel, true);
                createShortCutByNum();
            }
        } else {
            createShortCutByNum();
        }
    }
    
    *//**
     * 升级数改变更新数目
     *//*
    public void updateNumFromGetUpgradeChange(int num) {
        mUpgradeNum = num;
        setmChangeNum(num);
    }

    
    public int getmChangeNum() {
        return mChangeNum;
    }

    public void setmChangeNum(int mChangeNum) {
        this.mChangeNum = mChangeNum;
    }
    
    public boolean isFromSetting() {
        return isFromSetting;
    }

    public void setFromSetting(boolean isFromSetting) {
        this.isFromSetting = isFromSetting;
    }

    *//**
     * 创建快捷方式
     *//*
    public void createShortCutByNum() {
        delShortcut(MainTabActivity.mainTabActivity);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Log.printStackTrace(TAG, e);
        }
        addtime = 0;
        addShortCutDialog(MainTabActivity.mainTabActivity);
    }*/
    
    public static ShortCutManager getInstance() {
        if (null == mInstance) {
            synchronized (ShortCutManager.class) {
                if (null == mInstance) {
                    mInstance = new ShortCutManager();
                }
            }
        }
        return mInstance;
    }
    
    private ShortCutManager() {
    	
    }
    
    public static String getDefaultLauncherPackage(Context context) {
		if (context == null) {
			return null;
		}
        try {//这里捕捉异常是有用户上报的崩溃显示这里在某些手机上报android.permission.SET_PREFERRED_APPLICATIONS权限异常
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			ResolveInfo res = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
			if (res == null || res.activityInfo == null) {
				return "";
			}
			return res.activityInfo.packageName;
		} catch (Exception e) {
		}
        return "";
    }
    
    public void installAppShortcutFromSettings(final Context context) {
/*     暂时屏蔽快捷方式
    	final String launcherPackage = getDefaultLauncherPackage(context);
    	new AppShortcutInstaller().installShortcut(context, launcherPackage, true);
    	UiInstance.getInstance().postRunnableDelayedToHandler(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, R.string.addcutshotmessage,
		                Toast.LENGTH_SHORT).show();
			}
		}, 1000); */
    }
    
    public void updateAppShortcutWhenNecessary(Context context) {
    	//只在之前没有创建过的情况下才创建，不再考虑红点展示
    	boolean shouldUpdateShortcut = false;
    	//if (!AppMarketSharePreferences.hasCreatedAppShortcut()) {
    	//	shouldUpdateShortcut = true;
    	//}
//    	else {
//			int shortcutNumber = AppMarketSharePreferences
//					.getInt(AppMarketSharePreferences.shortCutUpgradeNum);
//			if (shortcutNumber < 0) {
//				shortcutNumber = 0;
//			}
//			if (shortcutNumber > 0 && !AppMarketSharePreferences.IsDisplayShortCutNum()) {
//				shouldUpdateShortcut = true;
//			} else {
//				final int updatalbeAppCount = AppLoader.getInstance()
//						.getUpgradableAppCount();
//				int diff = Math.abs(shortcutNumber - updatalbeAppCount);
//				boolean upgradeCountChanged = diff != 0
//						&& (diff == Math.max(shortcutNumber, updatalbeAppCount));
//				if (upgradeCountChanged) {
//					if (shortcutNumber <= 0) {
//						shouldUpdateShortcut = InstallShortcutChainHandler.showUpgradeNumber(context);	//修复在showUpgradeNumber里面返回false的情况会重复创建快捷方式的bug
//					} else {
//						shouldUpdateShortcut = true;
//					}
//				}
//			}
//    	}
    	/*屏蔽快捷方式
    	 * if (shouldUpdateShortcut) {
    		final String launcherPackage = getDefaultLauncherPackage(context);
    		new AppShortcutInstaller().installShortcut(context, launcherPackage, false);
    	}*/
    }
    
    /**
     * 创建桌面搜索快捷方式
     */
    public void installSearchShortcut(Context context) {
    	/* 屏蔽快捷方式
    	final String launcherPackage = getDefaultLauncherPackage(context);
    	new SearchShortcutInstaller().installShortcut(context, launcherPackage, false); */
    }
		
	public void setShortCutInfo(Map<String, String> datamap, Context context) {
		/*final String launcherPackage = ShortCutManager.getDefaultLauncherPackage(context);
		boolean isHas = ShortcutDetector.Builder.newInstance(context, launcherPackage).hasShortcut(context, launcherPackage,
                context.getString(R.string.app_search), "");
		datamap.put("dtsearch", isHas ? "1" : "2");
		isHas = ShortcutDetector.Builder.newInstance(context, launcherPackage).hasShortcut(context, launcherPackage,
				context.getString(R.string.app_name), "");
		datamap.put("dtsjzs", isHas ? "1" : "2");
        datamap.put("dtgamebox", "2");*/
	}

}