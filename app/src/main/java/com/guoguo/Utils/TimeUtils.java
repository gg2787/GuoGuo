package com.guoguo.Utils;

import java.util.Calendar;

/**
 * Created by Administrator on 2015/9/23.
 */
public class TimeUtils {

    public static void timeTest() {

        Calendar calNow = Calendar.getInstance();
        int nYear = calNow.get(Calendar.YEAR);
        int nMonth = calNow.get(Calendar.MONTH) + 1;
        int nDate = calNow.get(Calendar.DATE);
        int nHour = calNow.get(Calendar.HOUR);
        int nMin = calNow.get(Calendar.MINUTE);
        int nSec = calNow.get(Calendar.SECOND);

        Calendar calNow2 = Calendar.getInstance();
        calNow2.setTimeInMillis(System.currentTimeMillis());
        int nYear2 = calNow2.get(Calendar.YEAR);
        int nMonth2 = calNow2.get(Calendar.MONTH) + 1;
        int nDate2 = calNow2.get(Calendar.DATE);
        int nHour2 = calNow2.get(Calendar.HOUR);
        int nMin2 = calNow2.get(Calendar.MINUTE);
        int nSec2 = calNow.get(Calendar.SECOND);




    }
}
