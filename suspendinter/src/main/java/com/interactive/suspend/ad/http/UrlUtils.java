package com.interactive.suspend.ad.http;

import android.content.Context;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.interactive.suspend.ad.BuildConfig;
import com.interactive.suspend.ad.util.DeviceUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by drason on 2018/7/20.
 */

public class UrlUtils {

    public static final String HOST = "http://api.c.avazunativeads.com";
    public static String GET_APP_CONFIG = "/anative/appcfg?";

    public static String buildGetAdConfigUrl(Context context, String configUrl, String configInfo) {
        try {
            configUrl = configUrl + "&pkg_ver=" + DeviceUtil.getAppVersion(context) +
                    "&deviceid=" + DeviceUtil.getDeviceId(context) +
                    //TODO
                    "&pkg_name=" + DeviceUtil.getAppPackName(context.getApplicationContext()) +
                    "&android_id=" + Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) +
                    "&osv=" + android.os.Build.VERSION.SDK_INT +
                    //用户是否为新用户
                    "&new_user=" + DeviceUtil.getIsNewUser(context.getApplicationContext()) +
                    //广告sdk的版本号
                    "&ad_sdk_version=" + "1.4.9" +
                    "&sdk_version=100" +
                    //TODO
                    "&first_time=" + DeviceUtil.getFristInstallTime(context.getApplicationContext()) / 1000 +
                    "&has_sim=" + true +
                    "&sdk_vercode=" + BuildConfig.VERSION_CODE +
                    "&sdk_vername=" + BuildConfig.VERSION_NAME
                    + "&func=openad";
            configUrl += getFileVersion(configInfo);
        } catch (Exception e) {
//            MyLog.e(MyLog.TAG, "build config failed");
        }
        return configUrl;
    }

    public static String buildGetApxConfigURL(Context context, String appId) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HOST);
        stringBuilder.append(GET_APP_CONFIG)
                .append("appid=").append(appId)
//                .append("&version=").append(PreferenceUtils.getAppConfigVersion(context))
                .append("&sdkversion=").append(BuildConfig.VERSION_NAME)
                .append("&pkg=").append(context.getPackageName());
        stringBuilder.append("&androidid=").append(DeviceUtil.getAndroidId(context));
        return stringBuilder.toString();
    }


    public static String getFileVersion(String adConfig) {
        String fileVersion = "0";
        if (!TextUtils.isEmpty(adConfig)) {
            try {
                JSONObject json = new JSONObject(adConfig);
                String version = json.getString("version");
                if (!TextUtils.isEmpty(version)) {
                    fileVersion = version;
                }
            } catch (Exception ignore) {
            }
        }
        return "&file_ver=" + fileVersion;
    }


    public static String appendReportParams(String url, @NonNull HashMap<String, String> params) {
        if (url == null) throw new IllegalArgumentException("url incorrect!!");
        if (params == null || params.size() == 0) return url;
        StringBuilder sb = new StringBuilder(url);
        Set<Map.Entry<String, String>> entries = params.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
                sb.append(String.format("%s=%s",key,value));
            }
        }
        return sb.toString();
    }


}
