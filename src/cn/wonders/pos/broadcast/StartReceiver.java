package cn.wonders.pos.broadcast;

import cn.wonders.pos_qdg.activity.MainActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
	/*
	 * 为了启动程序
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-7-28 上午11:09:20
 */
public class StartReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 if (intent.getAction().equalsIgnoreCase("install_and_start")) {
	            Intent intent2 = new Intent(context, MainActivity.class);
	            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//注意,不能少
	            context.startActivity(intent2);
	        }
	}

}


