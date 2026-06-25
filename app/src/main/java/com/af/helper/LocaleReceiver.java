package com.af.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.LocaleList;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LocaleReceiver extends BroadcastReceiver {
    private static final String TAG = "AfHelper";

    @Override
    public void onReceive(Context context, Intent intent) {
        String locale = intent != null ? intent.getStringExtra("locale") : null;
        if (locale == null || locale.isBlank()) {
            Log.w(TAG, "missing locale extra");
            return;
        }
        try {
            setSystemLocale(locale.trim());
            Log.i(TAG, "locale applied: " + locale);
        } catch (Exception e) {
            Log.e(TAG, "setSystemLocale failed", e);
        }
    }

    /**
     * LocaleManager.setSystemLocales — @hide/@TestApi, недоступен через getMethod на API 33+.
     * Повторяем его реализацию: IActivityManager.updatePersistentConfiguration.
     */
    private static void setSystemLocale(String localeTag) throws Exception {
        LocaleList list = LocaleList.forLanguageTags(localeTag);

        Class<?> amClass = Class.forName("android.app.ActivityManager");
        Method getService = amClass.getDeclaredMethod("getService");
        getService.setAccessible(true);
        Object iam = getService.invoke(null);

        Method getConfig = iam.getClass().getMethod("getConfiguration");
        Configuration config = (Configuration) getConfig.invoke(iam);

        try {
            Field userSetLocale = Configuration.class.getField("userSetLocale");
            userSetLocale.setBoolean(config, true);
        } catch (NoSuchFieldException ignored) {
            // поле убрали на новых сборках — не критично
        }

        config.setLocales(list);

        Method update = iam.getClass().getMethod("updatePersistentConfiguration", Configuration.class);
        update.invoke(iam, config);
    }
}
