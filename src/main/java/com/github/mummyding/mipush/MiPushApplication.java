package com.github.mummyding.mipush;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by dingqinying on 16/11/28.
 */
public class MiPushApplication extends Application {

    public static final String APP_ID = "2882303761517528628";
    public static final String APP_KEY = "5971752876628";
    private static final String TAG = "MiPushApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Log.d(TAG, "~~~~~~~~~~~~~~ pid: " + Process.myPid() + getPidName(Process.myPid()) + "~~~~~~~~~~~~~~");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化push推送服务
        if(shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
            MiPushClient.setUserAccount(getApplicationContext(), "MichelleMeng", "-1");
        }
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
            }
            @Override
            public void log(String content, Throwable t) {
                Log.d(TAG, content, t);
            }
            @Override
            public void log(String content) {
                Log.d(TAG, content);
            }
        };
        Logger.setLogger(this, newLogger);
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onTerminate() {
        MiPushClient.unregisterPush(getApplicationContext());
        super.onTerminate();
    }

    private String getPidName(int pid) throws IOException {
        String processName = "";
        File file = new File("/proc/"+pid+"/cmdline");
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            processName =bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        }
        return  processName;
    }
}
