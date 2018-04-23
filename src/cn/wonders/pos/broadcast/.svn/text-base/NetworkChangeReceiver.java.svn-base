package cn.wonders.pos.broadcast;

import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.ToastUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class NetworkChangeReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		State wifiState = null;  
        State mobileState = null;  
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();  
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();  
        if (wifiState != null && mobileState != null  
                && State.CONNECTED != wifiState  
                && State.CONNECTED == mobileState) {  
            // 手机网络连接成功  
        	LogUtil.e("TIME", "网络连接成功");
        	ToastUtil.showShort(context, "网络连接成功");
        } else if (wifiState != null && mobileState != null  
                && State.CONNECTED != wifiState  
                && State.CONNECTED != mobileState) {  
            // 手机没有任何的网络  
        	ToastUtil.showShort(context, "没有网络");
        	LogUtil.e("TIME", "没有任何的网络");
        } else if (wifiState != null && State.CONNECTED == wifiState) {  
            // 无线网络连接成功  
        	ToastUtil.showShort(context, "有无线");
        	LogUtil.e("TIME", "无线网络连接成功 ");
        }  
	}

}
