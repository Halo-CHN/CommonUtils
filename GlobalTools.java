package com.hengyirong.util;

import java.util.ArrayList;
import java.util.Collection;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.view.Display;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 常用工具类
 * 
 * @Description:
 */
public class GlobalTools {

	/**
	 * dip转换成Px
	 * 
	 * @Description:
	 * @param context
	 * @param dipValue
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转换成dp
	 * 
	 * @Description:
	 * @param context
	 * @param pxValue
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param fontScale
	 * @return
	 */
	public static float sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return spValue * fontScale + 0.5f;
	}

	/**
	 * 隐藏软键盘
	 * 
	 * @Description:
	 * @param context
	 */
	public static boolean hideSoftInput(Activity context) {
		try {
			InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (inputMethodManager != null && context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null) {
				return inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 切换输入法显示隐藏状态
	 * 
	 * @Description:
	 * @param context
	 */
	public static void toggleSoftInput(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 隐藏输入法
	 * 
	 * @Description:
	 * @param context
	 * @param binder
	 *            输入法所在控件的token
	 */
	public static void hideSoftInput(Context context, IBinder binder) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(binder, 0);
	}

	/**
	 * 存储屏幕高宽的数据
	 */
	private static int[] screenSize = null;

	/**
	 * 获取屏幕高宽
	 * 
	 * @Description:
	 * @param activity
	 * @return 屏幕宽高的数据[0]宽， [1]高
	 */
	public static int[] getScreenSize(Activity activity) {
		if (screenSize == null) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			screenSize = new int[2];
			screenSize[0] = display.getWidth();
			screenSize[1] = display.getHeight();
		}
		return screenSize;
	}

	/**
	 * 清除List内容，并置为null
	 * 
	 * @Description:
	 * @param list
	 */
	public static void clearList(Collection<?> list) {
		if (list != null) {
			list.clear();
			list = null;
		}
	}

	/**
	 * 关闭cursor
	 * 
	 * @Description:
	 * @param cursor
	 */
	public static void closeCursor(Cursor cursor) {
		if (cursor != null) {
			cursor.close();
		}
	}

	/**
	 * 取两个集合的并集
	 * 
	 * @Description:
	 * @param c1
	 * @param c2
	 */
	public static Collection<String> mixedList(Collection<String> c1, Collection<String> c2) {
		// 定义两个空的集合，分别存放最大和�?��的集合，用来取交�?
		Collection<String> tmpBig = new ArrayList<String>();
		Collection<String> tmpSmall = new ArrayList<String>();
		// 为最大和�?��集合赋�?
		if (c1.size() > c2.size()) {
			tmpBig.addAll(c1);
			tmpSmall.addAll(c2);
		} else {
			tmpBig.addAll(c2);
			tmpSmall.addAll(c1);
		}
		tmpBig.retainAll(tmpSmall);
		tmpSmall = null;
		return tmpBig;
	}

	/**
	 * 获取应用版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		String versionName;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

			// 当前应用的版本名称
			versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return versionName;
	}

	/**
	 * 获取应用版本编号
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

			// 当前应用的版本名称
			versionCode = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}
	
	public static void download(Context context, String url) {
		Toast.makeText(context, "开始下载...", Toast.LENGTH_SHORT).show();
		DownloadManager manager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);

		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		// 设置下载路径和文件名
		request.setDestinationInExternalPublicDir("download", "longyuncai.apk");
		// 设置下载中通知栏提示的标题
		request.setTitle("恒易融理财");
		// 下载中通知栏提示的介绍
		request.setDescription("恒易融理财新版本下载");
		// 下载完成后显示通知栏提示
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setMimeType("application/vnd.android.package-archive");
		// 设置为可被媒体扫描器找到
		request.allowScanningByMediaScanner();
		// 设置为可见和可管理
		request.setVisibleInDownloadsUi(true);
		long refernece = manager.enqueue(request);
		// 把当前下载的ID保存起来
		SharedPreferences sPreferences = context.getSharedPreferences("downloadcomplete", 0);
		sPreferences.edit().putLong("refernece", refernece).commit();
	}

}
