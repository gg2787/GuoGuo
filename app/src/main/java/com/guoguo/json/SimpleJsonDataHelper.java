package com.guoguo.json;

/**
 * Created by Administrator on 2015/9/29.
 */
public class SimpleJsonDataHelper {
    /*private static final String TAG = "SimpleJsonDataHelper";
    public String makeJsonString(SimpleJsonDataBean bean) {
        return JsonUtil.toString(bean);
    }

    public SimpleJsonDataBean praseJsonString(String strJson) {
        return JsonUtil.parseObject(strJson, SimpleJsonDataBean.class);
    }

    public void jsonMakeAndParse() {
        SimpleJsonDataBean beanOld = new SimpleJsonDataBean();
        SimpleJsonDataBean.MemClean memClean = beanOld.getMemClean();
        memClean.setOpen(1);
        memClean.setNotifyBottom(60);
        memClean.setPopcnt(5);

        String strJson = makeJsonString(beanOld);

        SimpleJsonDataBean beanNew = praseJsonString(strJson);

        SimpleJsonDataBean beanNew2 = beanNew.clone();

        Log.error(TAG, "open" ,Integer.toString(beanNew2.getMemClean().getOpen()));
        Log.error(TAG, "bottom", Integer.toString(beanNew2.getMemClean().getOpen()));
        Log.error(TAG, "popcnt", Integer.toString(beanNew2.getMemClean().getOpen()));

        return;
    }*/
}
