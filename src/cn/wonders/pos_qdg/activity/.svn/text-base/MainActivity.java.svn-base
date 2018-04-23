package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.R.color;
import cn.wonders.pos_qdg.R.id;
import cn.wonders.pos_qdg.R.layout;
import cn.wonders.pos_qdg.R.menu;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.paramBean;
import cn.wonders.pos_qdg.net.CPHttpClientUtil;
import cn.wonders.pos_qdg.net.JSONParsing;
import cn.wonders.pos_qdg.net.Result;
import cn.wonders.pos_qdg.tool8583.Connect;
import cn.wonders.pos_qdg.util.BuzzerTools;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.StringUtils;
import cn.wonders.pos_qdg.util.ToastUtil;
import cn.wonders.pos_qdg.util.UpUtils;

public class MainActivity extends BaseActivity {

	Activity mActivity;

	/** 银联定额下载 */
	/** handler 参数 成功 */
	public final static int upYLQPInt = 100;
	/** handler 参数 失败 */
	public final static int upYLQHandlerFild = 999;
	/** 设备自检成功 */
	public final static int DriversOK = 101;
	/** 设备自检失败 */
	public final static int DriversFild = 102;
	/** 银联签到 */
	public final static int UNSgin = 110;
	/** 银联签到成功 */
	public final static int UNSginOK = 103;
	/** 银联签到失败 */
	public final static int UNSginFild = 104;
	/** 网络链接成功 */
	public final static int netOK = 105;
	/** 网络链接失败 */
	public final static int netFild = 106;
	public final static int toHome = 108;

	protected static final int IpRouteAdd = 107;
	/** 双网并存设置是否成功*/
	private boolean ipRouteOK = false;

	Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case DriversOK:// 设备检测
				tv_main_ic_info.setText("正常");
				tv_main_ic_info.setTextColor(mActivity.getResources().getColor(
						R.color.check_green));
				handler.sendEmptyMessageDelayed(IpRouteAdd, 7000);
				break;
			case IpRouteAdd:// 双网并存
				/**
				 * 设置双网并存
				 */
				String ip = SPUtils.getString(mActivity, SPUtils.CUP_IP, "");
				if (ip == "") {
					ip = "210.22.91.77";
				}
				ipRouteAdd(ip);
				break;
			case DriversFild:// 设备检测
				tv_main_ic_info.setText("失败");
				tv_main_ic_info.setTextColor(mActivity.getResources().getColor(
						R.color.check_rad));
				tv_main_info.setText("设备硬件故障，请联系维护人员！点击停止音效");
				tv_main_info.setTextColor(mActivity.getResources().getColor(
						R.color.check_rad));
				tv_main_info.setVisibility(View.VISIBLE);
				buzzerTools.playError(mActivity, true);
				break;
			case netOK:// 网络共存设置
				
				ipRouteOK = true;
				handler.sendEmptyMessageDelayed(UNSgin, 5000);
				break;
			case UNSgin:// 执行签到操作
				UpUtils up=new UpUtils(mActivity, handler);
				up.signIn();
				break;
			case netFild:// 网络共存设置
				tv_main_net_info.setText("失败");
				tv_main_net_info.setTextColor(mActivity.getResources()
						.getColor(R.color.check_rad));
				tv_main_info.setText("设备硬件故障，请联系维护人员！点击停止音效");
				tv_main_info.setTextColor(mActivity.getResources().getColor(
						R.color.check_rad));
				tv_main_info.setVisibility(View.VISIBLE);
				buzzerTools.playError(mActivity, true);
				break;
			case UNSginOK:// 银联签到结果
				tv_main_un_info.setText("正常");
				tv_main_un_info.setTextColor(mActivity.getResources().getColor(
						R.color.check_green));
				initDate();
				break;
			case UNSginFild:// 银联签到结果
				tv_main_un_info.setText("失败");
				tv_main_un_info.setTextColor(mActivity.getResources().getColor(
						R.color.check_rad));
				initDate();
				break;

			case upYLQPInt:
				CloudposApplication.getInstance().setIsUpdate(true);
				ToastUtil.showShort(mActivity, "参数下载成功！");
				if(ipRouteOK){
					tv_main_net_info.setText("正常");
					tv_main_net_info.setTextColor(mActivity.getResources()
							.getColor(R.color.check_green));
				}
				
				handler.sendEmptyMessageDelayed(toHome, 3000);
				break;
			case upYLQHandlerFild:
				CloudposApplication.getInstance().setIsUpdate(false);
				String showInfo = (String) msg.obj;
				ToastUtil.showShort(mActivity, showInfo);

				handler.sendEmptyMessageDelayed(toHome, 3000);
				break;
			case 10000:
				String showInfo1 = (String) msg.obj;
				ToastUtil.showShort(mActivity, showInfo1);

				handler.sendEmptyMessageDelayed(toHome, 3000);
				break;
			case toHome:
				Intent intent = new Intent(mActivity, MainHomeActivity.class);
				startActivity(intent);
				CloudposApplication.getInstance().mScreenManager
						.popActivity(mActivity);
				break;
			default:
				break;
			}

		};
	};

	private UpdatePramsAsyncTask updateYLPramsAsyncTask;

	private TextView tv_main_ic_info, tv_main_un_info, tv_main_net_info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/***
		 * 注册广播屏蔽状态通知栏下拉操作
		 */
		Intent intent = new Intent();
		intent.setAction("com.landicorp.android.ACTION.STATUBARDISABLE");
		startService(intent);
		setContentView(R.layout.activity_main);
		mActivity = MainActivity.this;

		initView();

		checkDrivers();
		
	}

	private void initView() {
		tv_main_info = (TextView) findViewById(R.id.tv_main_loading);
		tv_main_ic_info = (TextView) findViewById(R.id.tv_main_ic_state);
		tv_main_un_info = (TextView) findViewById(R.id.tv_main_un_state);
		tv_main_net_info = (TextView) findViewById(R.id.tv_main_net_state);

		buzzerTools = BuzzerTools.getInstance();

		tv_main_info.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				buzzerTools.playError(mActivity, false);
			}
		});
	}
	
	/**
	 * 检测硬件设备
	 */
	private void checkDrivers() {
		// TODO Auto-generated method stub
		handler.sendEmptyMessage(DriversOK);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (updateYLPramsAsyncTask != null) {
			updateYLPramsAsyncTask.cancel(true);
		}
		buzzerTools.playError(mActivity, false);
	}

	private void initDate() {

		if (updateYLPramsAsyncTask!=null) {
			updateYLPramsAsyncTask.cancel(true);
		}
		String url = (String) SPUtils.get(mActivity, SPUtils.IP, "");
    	if(StringUtils.isBlank(url)){
    		showMsg(10000, "后台服务器参数未设置！");
    	}else{
			updateYLPramsAsyncTask = new UpdatePramsAsyncTask();
			updateYLPramsAsyncTask.execute();
    	}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// myBack();
		}
		return true;
		// return super.onKeyDown(keyCode, event);
	}

	/**
	 * 屏蔽home键 无法放到BaseActivity 每个页面单独处理
	 */
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window win = getWindow();
		try {
			Class<?> cls = win.getClass();
			final Class<?>[] PARAM_TYPES = new Class[] { int.class };
			Method method = cls.getMethod("addCustomFlags", PARAM_TYPES);
			method.setAccessible(true);
			method.invoke(win, new Object[] { 0x00000001 });
		} catch (Exception e) {
			// handle the error here.
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 后台下载配置参数
	 * 
	 * @author Administrator
	 * 
	 */
	private class UpdatePramsAsyncTask extends AsyncTask<String, Void, Boolean> {
		String strError;
		Exception exception;
		private Result queryResult;
		private boolean YLQPrams;
		private int handlWhat = upYLQHandlerFild;
		private String handlerMsg;

		@Override
		protected Boolean doInBackground(String... prams) {
			// TODO Auto-generated method stub
			Map<String, Object> pramsMap = transUpdateReqMapParams();
			String payURL = CPHttpClientUtil
					.getBillUpdateYLQPURL(MainActivity.this);
			if (StringUtils.isBlank(payURL)) {
				handlWhat = 10000;
				handlerMsg = "未设置IP！";
				return false;
			}
			HttpResponse response = CPHttpClientUtil.httpPost(payURL, pramsMap);
			queryResult = JSONParsing.parsingtoString(response);
			return queryResult != null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			// CustomProgressDialog.hideProgressDialog();
			if (exception != null) {
				LogUtil.e(exception.toString());
				showMsg(upYLQHandlerFild, "参数下载失败！");
				return;
			}
			if (result) {
				if (CPHttpClientUtil.Success.equals(queryResult.coder)) {
					// 查询成功
					YLQPrams = JSONParsing.parsPrams(mActivity, queryResult);
					if (YLQPrams) {
						// 解析成功并保存到SP
						handler.sendMessageDelayed(
								handler.obtainMessage(upYLQPInt), 3000);
					} else {
						showMsg(upYLQHandlerFild, "参数下载失败！");
					}
				} else {
					strError = queryResult.msg;
					LogUtil.i("银联定额参数：" + strError);
					if (handlWhat == 10000) {
						showMsg(handlWhat, handlerMsg);
					} else
						showMsg(upYLQHandlerFild, "参数下载失败！");
				}
			} else {
				if (handlWhat == 10000) {
					showMsg(handlWhat, handlerMsg);
				} else
					showMsg(upYLQHandlerFild, "参数下载失败！");
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			updateYLPramsAsyncTask = null;
			super.onCancelled();
		}
	}

	private void showMsg(int what, String info) {

		Message msg = handler.obtainMessage();
		msg.what = what;
		msg.obj = info;
		handler.sendMessageDelayed(msg, 3000);

	}

	public Map<String, Object> transUpdateReqMapParams() {
		// 设置商户号与TPDU
		Map<String, Object> params = new HashMap<String, Object>();

		List<paramBean> beans = new ArrayList<paramBean>();

		String yl_Version = SPUtils.getString(mActivity, SPUtils.YL_VERSION,
				"000000000000");
		if (StringUtils.isBlank(yl_Version)) {
			yl_Version = "000000000000";
		}

		paramBean YLbean1 = new paramBean(yl_Version, 1153 + "", null, null,
				null);

		String st_posNO = SPUtils.getString(mActivity, SPUtils.TerminalNo, "")
				.trim();
		if (StringUtils.isBlank(st_posNO)) {
			// 没设置
			showMsg(10000, "参数未设置！");
		}
		String st_Version = SPUtils.getString(mActivity, SPUtils.ST_VERSION,
				"000000000000");
		if (StringUtils.isBlank(st_Version)) {
			st_Version = "000000000000";
		}
		paramBean STWbean1 = new paramBean(st_Version, "1113", st_posNO, null,
				null);

		
		String rate_Version = SPUtils.getString(mActivity,
				SPUtils.Rate_VERSION, "000000000000");
		if (StringUtils.isBlank(rate_Version)) {
			rate_Version = "000000000000";
		}
		paramBean RATbean1 = new paramBean(rate_Version, "1123", st_posNO,
				null, null);
		String BL_Version = SPUtils.getString(mActivity, SPUtils.BL_VERSION,
				"000000000000");
		if (StringUtils.isBlank(BL_Version)) {
			BL_Version = "000000000000";
		}
		paramBean BLbean1 = new paramBean(BL_Version, "1133", null, null, null);

		paramBean CDbean1 = new paramBean(BL_Version, "1173", null, null,
				"mealType");
		beans.add(CDbean1);
		beans.add(BLbean1);
		beans.add(RATbean1);
		beans.add(STWbean1);
		beans.add(YLbean1);

		params.put("data", beans);

		return params;
	}

	private final String TAG = MainActivity.class.getSimpleName();
	private IBinder mBinder = null;
	private int CMD = 0;

	private TextView tv_main_info;

	private BuzzerTools buzzerTools;

	// 服务相关
	private void initBinder() {
		try {
			Class spCls = Class.forName("android.os.ServiceManager");
			Class[] typeArgs = new Class[1];
			typeArgs[0] = String.class;
			Constructor spcs = spCls.getConstructor(null);
			Object[] valueArgs = new Object[1];
			valueArgs[0] = "epay.iprouteadd";
			Object sp = spcs.newInstance(null);
			Method method = spCls.getMethod("getService", typeArgs);
			mBinder = (IBinder) method.invoke(sp, valueArgs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}

		if (mBinder == null) {
			Toast.makeText(MainActivity.this, "iprouteadd服务绑定失败",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 设置网络共存
	 * 
	 * @param cmd
	 */
	private void ipRouteAdd(String cmd) {

		Parcel data = null;
		Parcel reply = null;
		try {
			if (mBinder == null) {
				initBinder();
			}

			if (mBinder == null) {
				Toast.makeText(MainActivity.this, "iprouteadd服务绑定失败",
						Toast.LENGTH_LONG).show();
				handler.sendEmptyMessage(netFild);
				return;
			}
			if (cmd == null || cmd.isEmpty()) {
				Toast.makeText(MainActivity.this, "请输入正确的命令", Toast.LENGTH_LONG)
						.show();
				handler.sendEmptyMessage(netFild);
				return;
			}

			data = Parcel.obtain();
			reply = Parcel.obtain();
			data.writeString(cmd);
			mBinder.transact(CMD, data, reply, 0);// 执行远程调用
			int result = reply.readInt();
			LogUtil.d(TAG, "网络共存返回结果result:" + result);
			handler.sendEmptyMessage(netOK);
		} catch (Exception e) {
			LogUtil.e(TAG, e.toString());
			handler.sendEmptyMessage(netFild);
		} finally {
			if (data != null) {
				data.recycle();
			}
			if (reply != null) {
				reply.recycle();
			}
		}
	}

	
}