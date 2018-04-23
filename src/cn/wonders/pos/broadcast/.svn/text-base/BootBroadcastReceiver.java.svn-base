package cn.wonders.pos.broadcast;

import cn.wonders.pos_qdg.activity.MainActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-5-24 下午4:54:25
 */
public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String action_boot = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(action_boot)) {
			Intent StartIntent = new Intent(context, MainActivity.class); // 接收到广播后，跳转到MainActivity
			StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			try {
				new Thread().sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			context.startActivity(StartIntent);
		}
	}

}
