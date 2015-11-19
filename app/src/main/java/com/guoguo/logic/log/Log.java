package com.guoguo.logic.log;

import com.guoguo.app.GuoGuoApplication;
import com.guoguo.utils.TimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/9/29.
 */
public class Log {

    public static void error(String tag, String text) {
        android.util.Log.e(tag, " " + text);
        storeLog("e", tag, text);
    }

    public static void error(String tag, String format, Object... args) {
        String text = format;
        if (args.length > 0)
            text = String.format(format, args);
        error(tag, text);
    }

    private static void storeLog(String type, String tag, String text) {
        FileOutputStream fOut = null;
        PrintWriter writer = null;

        try {
            File dir = GuoGuoApplication.mContext.getExternalFilesDir(null);
            if (null == dir) {
                return;
            }

            StringBuilder stringBuilder = new StringBuilder(dir.getPath());
            stringBuilder.append("/Log");
            File logDir = new File(stringBuilder.toString());
            if (null == logDir) {
                return;
            }
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            stringBuilder.append("/Log.txt");
            File logFile = new File(stringBuilder.toString());
            if (null == logFile) {
                return;
            }
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            fOut = new FileOutputStream(logFile, true);
            if (null != fOut) {
                writer = new PrintWriter(fOut);
                String dateNowStr = TimeUtils.formatCurTime();
                if (type.equals("e")) {
                    writer.println(dateNowStr + " Error:>>" + tag + "<<  " + text + '\r');
                } else if (type.equals("d")) {
                    writer.println(dateNowStr + " Debug:>>" + tag + "<<  " + text + '\r');
                } else if (type.equals("i")) {
                    writer.println(dateNowStr + " Info:>>" + tag + "<<   " + text + '\r');
                } else if (type.equals("w")) {
                    writer.println(dateNowStr + " Warning:>>" + tag + "<<   " + text + '\r');
                } else if (type.equals("v")) {
                    writer.println(dateNowStr + " Verbose:>>" + tag + "<<   " + text + '\r');
                } else if (type.equals("f")) {
                    writer.println(dateNowStr + " File:>>" + tag + "<<   " + text + '\r');
                }
            }
            writer.flush();

        } catch (IOException e1) {
            android.util.Log.e(tag, "makeLogFileErr");
            e1.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {
            try {
                if (null != fOut) {
                    fOut.close();
                }
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
