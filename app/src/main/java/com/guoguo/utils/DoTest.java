package com.guoguo.utils;

import com.guoguo.logic.log.Log;

/**
 * Created by Administrator on 2015/10/28.
 */
public class DoTest {

        public   static   void stringTest()
        {
            String text = "" ;

            long beginTime = System.currentTimeMillis();
            for ( int i= 0 ;i< 10000 ;i++)
                text = text + i;
            long endTime = System.currentTimeMillis();
            //System.out.println("Sting执行时间：" +(endTime-beginTime));
            Log.error("Sting执行时间：", String.valueOf(endTime-beginTime));


            StringBuilder sb = new StringBuilder ( "" );
            beginTime = System.currentTimeMillis();
            for ( int i= 0 ;i< 10000 ;i++)
                sb.append(String.valueOf(i));
            endTime = System.currentTimeMillis();
           // System.out.println("StringBuilder执行时间：" +(endTime-beginTime));
            Log.error("StringBuilder执行时间：", String.valueOf(endTime - beginTime));

        }

}
