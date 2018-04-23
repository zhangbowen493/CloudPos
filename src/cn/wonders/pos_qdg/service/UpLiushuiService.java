package cn.wonders.pos_qdg.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.db.TradingAbnormalDao;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.UpUtils;
/**
 * 开启上送流水到银联及上送流水到管理台服务
 * @author Luckydog
 */
public class UpLiushuiService extends Service {

	private Context mContext;
	int service_Shangsong;
	boolean isRunning = true;
	String shanghu;
	private CloudposApplication cloudposApplication;
	TradingAbnormalDao abnDao;
	private UpUtils up;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
		cloudposApplication = CloudposApplication.getInstance();
		new Thread(new Runnable() {
			@Override
			public void run() {
				service_Shangsong = Integer.parseInt(SPUtils.getString(
						mContext, SPUtils.Service_ShangSong, "5400000"));
				try {
					Thread.sleep(service_Shangsong);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				up=new UpUtils(mContext, null,cloudposApplication);
				while (isRunning) {
					if (cloudposApplication.isServerUp) {
						LogUtil.e("TIME", "正在进行上送，后台服务等待");
					}else{
						up.uploadLiushui(1);
					}
					try {
						Thread.sleep(service_Shangsong);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.i("service停止");
		isRunning=false;
		if (up!=null) {
			up.onDestroy();
		}
	}

}
