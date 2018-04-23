package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.APPTools;
import cn.wonders.pos_qdg.util.Arith;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.StringUtils;
import cn.wonders.pos_qdg.util.ToastUtil;

/**
 * 系统参数设置界面
 *
 */
public class SystemParameterActivity extends BaseActivity implements
		OnClickListener {
	private Activity mActivity;
	private EditText edt_ip, edt_port;
	private EditText edt_terminal;
	private String mTerminalNo;
	private EditText edt_upk;
	private EditText edt_cup_ip;
	private EditText edt_parameter_port;
	private String mUPK;
	private String mCUP_IP;
	private String mCUP_POR;
	private String ip;
	private String port;
	private boolean ipIsOk = false;
	private boolean serviceIpIsOk = false;
	private boolean parameterIsOK=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent();
		intent.setAction("com.landicorp.android.ACTION.STATUBARDISABLE");
		stopService(intent);
		setContentView(R.layout.activity_system_parameter);
		mActivity = SystemParameterActivity.this;
		// 设置软键盘在进入界面时不弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initView();
		initData();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setAction("com.landicorp.android.ACTION.STATUBARDISABLE");
		startService(intent);

		super.onDestroy();
	}

	private void initData() {
		// TODO Auto-generated method stub
		ip = SPUtils.getString(mActivity, SPUtils.IP, "");
		port = SPUtils.getString(mActivity, SPUtils.port, "");
		mTerminalNo = SPUtils.getString(mActivity, SPUtils.TerminalNo, "");
		mUPK = SPUtils.getString(mActivity, SPUtils.UPK, "");
		if (!mUPK.equals("")) {
			edt_upk.setText("*****");
		}
		mCUP_IP = SPUtils.getString(mActivity, SPUtils.CUP_IP, "");
		mCUP_POR = SPUtils.getString(mActivity, SPUtils.CUP_POR, "");

		edt_ip.setText(ip);
		edt_port.setText(port);
		edt_terminal.setText(mTerminalNo);
		edt_cup_ip.setText(mCUP_IP);
		edt_parameter_port.setText(mCUP_POR);
	}

	private void initView() {
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("系统参数设置");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);

		edt_ip = (EditText) findViewById(R.id.edt_system_parameter_ip);
		edt_port = (EditText) findViewById(R.id.edt_system_parameter_port);
		edt_terminal = (EditText) findViewById(R.id.edt_sys_parameter_terminal);
		edt_upk = (EditText) findViewById(R.id.edt_sys_upk);
		edt_cup_ip = (EditText) findViewById(R.id.edt_sys_cup_ip);
		edt_parameter_port = (EditText) findViewById(R.id.edt_sys_cup_parameter_port);
		// edt_shebei=(EditText) findViewById(R.id.edt_zhongduan);

		findViewById(R.id.btn_system_parameter_save).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_title_btn_back:
			myBack();
			break;
		case R.id.btn_system_parameter_save:
			saveData();
			break;

		default:
			break;
		}
	}

	private void saveData() {
		String ipStr = edt_ip.getText().toString().trim();
		String portStr = edt_port.getText().toString().trim();
		mTerminalNo = edt_terminal.getText().toString().trim();
		mUPK = edt_upk.getText().toString().trim();
		if (mUPK.equals("*****")) {
			mUPK = SPUtils.getString(mActivity, SPUtils.UPK, "");
		}
		mCUP_IP = edt_cup_ip.getText().toString().trim();
		mCUP_POR = edt_parameter_port.getText().toString().trim();

		// 判断是否是正确的服务器IP地址
		String[] serviceIpStr = ipStr.split("\\.");
		for (int i = 0; i < serviceIpStr.length; i++) {
			if (!APPTools.isNumeric(serviceIpStr[i])
					|| Arith.compareTo(serviceIpStr[i], "255") == 1
					|| Arith.compareTo(serviceIpStr[i], "0") == -1) {
				serviceIpIsOk = false;
			} else {
				serviceIpIsOk = true;
			}
		}
		if (StringUtils.isBlank(ipStr)) {
			ToastUtil.showShort(mActivity, "请填写IP地址！");
			parameterIsOK=false;
		} else {
			if (serviceIpStr.length != 4 || !serviceIpIsOk) {
				ToastUtil.showShort(mActivity, "服务器IP地址格式错误，请重新输入！");
				parameterIsOK=false;
			} else {
				SPUtils.put(mActivity, SPUtils.IP, ipStr);
			}
		}
		// 判断是否是正确的服务器端口号
		if (StringUtils.isBlank(portStr)) {
			ToastUtil.showShort(mActivity, "请输入端口号！");
			parameterIsOK=false;
		} else {
			if (!APPTools.isNumeric(portStr)
					|| Arith.compareTo(portStr, "0") == -1
					|| Arith.compareTo(portStr, "65535") == 1) {
				ToastUtil.showShort(mActivity, "服务器端口号格式错误，请重新输入！");
				parameterIsOK=false;
			} else {
				SPUtils.put(mActivity, SPUtils.port, portStr);
			}
		}
		// 判断是否是正确的终端号
		if (StringUtils.isBlank(mTerminalNo)) {
			ToastUtil.showShort(mActivity, "请输入终端号！");
			parameterIsOK=false;
		} else {
			if (mTerminalNo.length() != 8 || !APPTools.isNumeric(mTerminalNo)) {
				ToastUtil.showShort(mActivity, "终端号格式错误，请重新输入！");
				parameterIsOK=false;
			} else {
				SPUtils.put(mActivity, SPUtils.TerminalNo, mTerminalNo);
				SPUtils.put(mActivity, SPUtils.zhongduanID, mTerminalNo);
			}
		}
		// 判断是否是正确的密钥
		if (StringUtils.isBlank(mUPK)) {
			ToastUtil.showShort(mActivity, "请输入密钥！");
			parameterIsOK=false;
		} else {
			if (mUPK.length() != 32 || !APPTools.checkHexNum(mUPK)) {
				ToastUtil.showShort(mActivity, "密钥格式错误，请重新输入！");
				parameterIsOK=false;
				SPUtils.put(mActivity, SPUtils.UPK, "");
			} else {
				SPUtils.put(mActivity, SPUtils.UPK, mUPK);
			}
		}
		// 判断是否是正确的银联端口号
		if (StringUtils.isBlank(mCUP_POR)) {
			ToastUtil.showShort(mActivity, "请输入端口号！");
			parameterIsOK=false;
		} else {
			if (!APPTools.isNumeric(mCUP_POR)
					|| Arith.compareTo(mCUP_POR, "0") == -1
					|| Arith.compareTo(mCUP_POR, "65535") == 1) {
				ToastUtil.showShort(mActivity, "银联端口号格式错误，请重新输入！");
				parameterIsOK=false;
			} else {
				SPUtils.put(mActivity, SPUtils.CUP_POR, mCUP_POR);
			}
		}
		// 判断是否是正确的银联IP地址
		String[] mCUPIpStr = mCUP_IP.split("\\.");
		for (int i = 0; i < mCUPIpStr.length; i++) {
			if (!APPTools.isNumeric(mCUPIpStr[i])
					|| Arith.compareTo(mCUPIpStr[i], "255") == 1
					|| Arith.compareTo(mCUPIpStr[i], "0") == -1) {
				ipIsOk = false;
			} else {
				ipIsOk = true;
			}
		}
		if (StringUtils.isBlank(mCUP_IP)) {
			ToastUtil.showShort(mActivity, "请填写银联IP地址！");
			parameterIsOK=false;
		} else {
			if (mCUPIpStr.length != 4 || !ipIsOk) {
				ToastUtil.showShort(mActivity, "银联IP地址格式错误，请重新输入！");
				parameterIsOK=false;
			} else {
				SPUtils.put(mActivity, SPUtils.CUP_IP, mCUP_IP);
			}

		}
		if (parameterIsOK) {
			ToastUtil.showShort(mActivity, "参数保存成功！");
			Intent intent = new Intent(mActivity, MainHomeActivity.class);
			startActivity(intent);
			ScreenManager.getScreenManager().popAlls();
		}
		parameterIsOK=true;
	}

	private void myBack() {
		// TODO Auto-generated method stub
		ScreenManager screenManager = CloudposApplication.getInstance()
				.getScreenManager();
		screenManager.popActivity(mActivity);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			myBack();
		}

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 屏蔽home键 无法放到BaseActivity 每个页面单独处理
	 */
//	@Override
//	public void onAttachedToWindow() {
//		super.onAttachedToWindow();
//		Window win = getWindow();
//		try {
//			Class<?> cls = win.getClass();
//			final Class<?>[] PARAM_TYPES = new Class[] { int.class };
//			Method method = cls.getMethod("addCustomFlags", PARAM_TYPES);
//			method.setAccessible(true);
//			method.invoke(win, new Object[] { 0x00000001 });
//		} catch (Exception e) {
//		}
//	}
}
