package com.yinsel.checkproxy;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.provider.Telephony;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

public class DetectUtils {
    public static boolean isProxy(Context context) {


        // 检测VPN接口
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                for (NetworkInterface networkInterface : Collections.list(interfaces)) {
                    String interfaceName = networkInterface.getName().toLowerCase();
                    if (interfaceName.contains("tun") || interfaceName.contains("tap") || interfaceName.contains("vpn")) {
                        MainActivity.showMessage(context,"检测到VPN网络接口");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

         // 通过系统API判断是否使用VPN
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            Network activeNetwork = cm.getActiveNetwork();
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(activeNetwork);
            if (capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                MainActivity.showMessage(context,"检测到VPN");
                return true;
            }
        }


        // 检测系统代理属性值
        String proxyAddress;
        int proxyPort;
        String portStr = System.getProperty("http.proxyPort");
        proxyAddress = System.getProperty("http.proxyHost");
        if (proxyAddress != null && !proxyAddress.isEmpty() && portStr != null && !portStr.isEmpty()) {
            try {
                proxyPort = Integer.parseInt(portStr);
                if (proxyPort > 0) {
                    MainActivity.showMessage(context,"检测到系统代理属性 (存在VPN/WIFI代理/APN代理)");
                    return true;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
