package com.hengyirong.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

/**
 * 网络工具类
 * 
 * @Description:
 */
public class NetUtil {

	public static ConnectivityManager getConnectivityManager(Context c) {
		return (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	private static WifiLock wifilock = null;

	/**
	 * 使用Wifi连接, wifi锁
	 */
	public static void startUseWifi(Context c) {
		WifiManager wifimanager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
		if (wifimanager != null) {
			wifilock = wifimanager.createWifiLock(WifiManager.WIFI_MODE_FULL, "meq");
			if (wifilock != null) {
				wifilock.acquire();
			}
		}
	}

	/**
	 * 释放wifi锁
	 */
	public static void stopUseWifi() {
		if (wifilock != null && wifilock.isHeld()) {
			wifilock.release();
		}
		wifilock = null;
	}

	/**
	 * 是否存在已经连接上的网络, 不论是wifi/cmwap/cmnet还是其他
	 * 
	 * @return
	 */
	public static boolean isConnected(Context c) {
		NetworkInfo ni = getConnectivityManager(c).getActiveNetworkInfo();
		return ni != null && ni.isConnected();
	}

	/**
	 * @description wifi 是否连接或正在连接
	 */
	public static boolean isWifiConnecting(Context context) {
		try {
			ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
			if (wifi == State.CONNECTING || wifi == State.CONNECTED) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 * @description 判断连接的网络是否是2g/3g
	 */
	public static boolean isMobileConnecting(Context context) {
		try {
			ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
			if (wifi != State.CONNECTED) {
				State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
				if (mobile == State.CONNECTED) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;

	}

}
