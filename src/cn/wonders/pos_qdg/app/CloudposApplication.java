package cn.wonders.pos_qdg.app;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import cn.wonders.pos_qdg.bean.User;
import cn.wonders.pos_qdg.db.UserDao;
import cn.wonders.pos_qdg.util.LogcatHelper;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ScreenManager;

public class CloudposApplication extends Application {
	/** 后台上送服务开关 */
	public boolean isServerUp;

	private static CloudposApplication instance;
	public ScreenManager mScreenManager;//堆栈管理工具
	
	/**
	 * 是否正在更新
	 */
	public static boolean isUpgrade = false;
	/**
	 * 升级地址
	 */
	public static String upgradePath = "";

	private User mUser;
	public static boolean isSignIn=false;
	public static String iniKey="86E6EF13A7B30BCB5EDAD9CDE6D53775";
	public static String macKey="";
	public static String pici="000001";	
	public static String respCode="99";	
	public static boolean isUpdate=false;
	public static String dateQingSuan="";	
	public static String mQuotaMealType="";	
	public int errNum=0;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		x.Ext.init(this); 
		if (instance == null) {
			instance = CloudposApplication.this;
		}
		if(mScreenManager==null){
			mScreenManager = ScreenManager.getScreenManager();
		}

		String imei = (String) SPUtils.get(this, SPUtils.Phone_imei, "");
		if("".equals(imei)){
			TelephonyManager tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
			String phoneimei = tm.getDeviceId();
			if(phoneimei!=null){
				SPUtils.put(getApplicationContext(), SPUtils.Phone_imei, phoneimei);
			}
		}
		LogcatHelper.getInstance(this).uploadLogFile();;  
	}
	/**
	 * 预存管理员
	 */
	private void saveAdmin() {
		// TODO Auto-generated method stub
		List<User> users = new UserDao(getApplicationContext()).getUser("account", User.adminAccount);
		if(users==null || users.size()<=0){
			users = new ArrayList<User>();
			User user = new User();
			user.setAccount(user.adminAccount);
			user.setPassword(user.adminPassword);
			user.setLevel(1);
			users.add(user);
			new UserDao(getApplicationContext()).addUserList((ArrayList<User>) users);
		}
	}

	public static CloudposApplication getInstance() {

		return instance;
	}
	public ScreenManager getScreenManager() {

		return mScreenManager;
	}
	public User getmUser() {
		return mUser;
	}
	public void setmUser(User mUser) {
		this.mUser = mUser;
	}
	public void setIsSignIn(boolean isSignIn){
		this.isSignIn=isSignIn;
	}
	public void setIsServerUp(boolean isServerUp){
		this.isServerUp=isServerUp;
	}
	public void setinitKey(String initKey){
		this.iniKey=initKey;
	}
	public void setmacKey(String macKey){
		this.macKey=macKey;
	}
	public void setpici(String pici){
		this.pici=pici;
	}
	public void setIsUpdate(boolean isUpdate){
		this.isUpdate=isUpdate;
	}
	public void setrespCode(String respCode){
		this.respCode=respCode;
	}
	public void setdateQingSuan(String dateQingSuan){
		this.dateQingSuan=dateQingSuan;
	}
	public void setmQuotaMealType(Context context,String mQuotaMealType){
		if (mQuotaMealType!=null) {
			SPUtils.put(context, SPUtils.CONSUME_TYPE, mQuotaMealType);
		}
	}
	public String getmQuotaMealType(Context context){
		
		return (String) SPUtils.get(context, SPUtils.CONSUME_TYPE, "99");
	}
	public void addErrNum(){
		errNum++;
	}
	public void removeErrNum(){
		errNum=0;
	}

}


