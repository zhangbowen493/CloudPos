package cn.wonders.pos_qdg.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import cn.wonders.pos_qdg.R;
/** 
 * Toast统一管理类 
 */ 
public class ToastUtil {
	private ToastUtil()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isShow = true;

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message)
	{
		if (isShow){
			if (isShow){
				LayoutInflater inflater = LayoutInflater.from(context);  
				View toastRoot = inflater.inflate(R.layout.toast_item, null);
				Toast toast=new Toast(context);
				toast.setView(toastRoot);
				TextView tv=(TextView)toastRoot.findViewById(R.id.text);
				tv.setText(message);
				toast.setDuration(Toast.LENGTH_SHORT);
				toast.show();
			}
		}
	}

	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message)
	{
		if (isShow){
			LayoutInflater inflater = LayoutInflater.from(context);  
			View toastRoot = inflater.inflate(R.layout.toast_item, null);
			Toast toast=new Toast(context);
			toast.setView(toastRoot);
			TextView tv=(TextView)toastRoot.findViewById(R.id.text);
			tv.setText(message);
			toast.setDuration(Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	/**
	 * 长时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, int message)
	{
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, int message, int duration)
	{
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

}
