package com.github.mummyding.mipush;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by dingqinying on 16/11/30.
 */
public class MIUIUtil {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        Properties prop = new Properties();
        boolean isMIUI;
        try {
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        isMIUI = prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        return isMIUI;
    }
}
