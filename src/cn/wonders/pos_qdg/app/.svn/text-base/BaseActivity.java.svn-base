package cn.wonders.pos_qdg.app;

import android.app.Activity;
import android.os.Bundle;
import cn.wonders.pos_qdg.util.LogUtil;

import com.landicorp.android.eptapi.DeviceService;
import com.landicorp.android.eptapi.exception.ReloginException;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.exception.ServiceOccupiedException;
import com.landicorp.android.eptapi.exception.UnsupportMultiProcess;
public class BaseActivity extends Activity {
	
	protected static final String ScanResult = "ScanResult";
	protected static final String ScanFormat = "ScanFormat";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);

		CloudposApplication.getInstance().getScreenManager().pushActivity(this);
		
	}
	

	@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			super.onBackPressed();
		}
	
	
	/**
	 * 设备是否登陆
	 */
	private boolean isDeviceServiceLogined = false;
	
	/**
	 * 控制设备的服务,您需要调用这个方法之前,任何设备操作。
	 */
	public void bindDeviceService() {
		LogUtil.e("BaseActicity.bindDeviceService");
		try {
			isDeviceServiceLogined = false;
			DeviceService.login(this);
			isDeviceServiceLogined = true;
		} catch (RequestException e) {
			// Rebind after a few milliseconds, If you want this application keep the right of the device service
//			runOnUiThreadDelayed(new Runnable() {
//				@Override
//				public void run() {
//					bindDeviceService();
//				}
//			}, 300);
			LogUtil.e(e.toString());
		} catch (ServiceOccupiedException e) {
			LogUtil.e(e.toString());
		} catch (ReloginException e) {
			LogUtil.e(e.toString());
		} catch (UnsupportMultiProcess e) {
			LogUtil.e(e.toString());
		}
	}
	
	/**
	 * 释放使用设备的权利
	 */
	public void unbindDeviceService() {
		LogUtil.e("BaseActicity.unbindDeviceService");
		DeviceService.logout();
		isDeviceServiceLogined = false;
	}
	
	
}


