package cn.wonders.pos_qdg.util;

import java.util.Stack;

import android.app.Activity;
import android.util.Log;
/**
 * 堆栈处理
 * @author a1
 *
 */
public class ScreenManager {
	
	private static final String TAG = "ScreenManager";
	private static Stack<Activity> activityStack;
	private static ScreenManager instance;
	
	private ScreenManager(){}
	
	public static ScreenManager getScreenManager(){
		if(instance == null){
			instance = new ScreenManager();
		}
		return instance;
	}
	/**
	 * 销毁堆栈中最上面一个Activity
	 * */
	public void popActivity(){
		Activity activity = activityStack.lastElement();
		if(activity != null){
			activity.finish();
			activity = null;
		}
	}
	
	/**
	 * 根据Activity内置上下文销毁
	 * 
	 * */
	
	public void popActivity(Activity activity){
		if(activity != null){
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}
	
	/**
	 * 获取当前Activity
	 * */
	public Activity currentActivity(){
		Activity activity = null;
		if(activityStack != null && !activityStack.isEmpty()){
			activity = activityStack.lastElement();
		}
		return activity;
	}
	
	/**
	 * 压栈
	 * 
	 * */
	public void pushActivity(Activity activity){
		if(activityStack == null){
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	/**
	 * 只保留一个Activity其他出栈
	 * 
	 * */
	
	public void popAllActivityExceptOne(Class cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (cls != null && activity.getClass().equals(cls)) {
				continue;
			}
			popActivity(activity);
		}
	}
	
	
	/**
	 * 根据Activity的SimpleName销毁
	 * 
	 * */
	public void popAllActivitys(String... clsStr){
		int count = 0;
		if(!activityStack.isEmpty()){
			int stockLen = activityStack.size();
			for(int i = stockLen; i > 0; i --){
				Activity activity = activityStack.get(i-1);
				int len = clsStr.length;
				for(int j =0;j<len;j++){
					if(clsStr[j].equals(activity.getClass().getSimpleName())){
						popActivity(activity);
						count ++;
					}
				}
				if(count == len){
					break;
				}
			}
		}
	}
	/**
	 * 销毁所有
	 */
	public void popAlls(){
		if(!activityStack.isEmpty()){
			int stockLen = activityStack.size() ;
			for(int i = stockLen; i > 0; i --){
				Activity activity = activityStack.get(i-1);
				popActivity(activity);
			}
		}
	}
	/**
	 * 销毁所有
	 */
	public void logsAlls(){
		if(!activityStack.isEmpty()){
			int stockLen = activityStack.size() ;
			for(int i = stockLen; i > 0; i --){
				Activity activity = activityStack.get(i-1);
			}
		}
	}
	
}
