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
import android.widget.RadioButton;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.service.UpLiushuiService;
import cn.wonders.pos_qdg.util.APPTools;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.StringUtils;
import cn.wonders.pos_qdg.util.ToastUtil;

/**
 * 应用参数设置页面
 * 
 * @author n_n
 *
 */
public class AppParameterActivity extends BaseActivity implements
OnClickListener {
	private Activity mActivity;
	private EditText edt_quit_password, edt_max_money, edt_service_shangsong,edt_threshold_level;
	private RadioButton rb_one,rb_two;
	private String mModelExitPassword;
	private String mMaxMoney;
	private String mServiceShangsong;
	private String mThresholdLevel;
	private long clearTime;
	private boolean isOK=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_parameter);
		mActivity = AppParameterActivity.this;
		// 设置软键盘在进入界面时不弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initView();
		initData();
	}

	private void initData() {
		mModelExitPassword = SPUtils.getString(mActivity,
				SPUtils.ConsumptionModelExitPassword, "");
		mMaxMoney = SPUtils.getString(mActivity, SPUtils.MaxMoney, "0");
		mServiceShangsong = SPUtils.getString(mActivity,
				SPUtils.Service_ShangSong, "300000");
		mThresholdLevel = SPUtils.getString(mActivity,
				SPUtils.ThresholdLevel, "2000");
		clearTime = (Long) SPUtils.get(mActivity,
				SPUtils.CLEAR_TIME, 604800000L);
		if (clearTime<=604800000L) {
			rb_one.setChecked(true);
		}else{
			rb_two.setChecked(true);
		}
		edt_quit_password.setText(mModelExitPassword);
		edt_max_money.setText(mMaxMoney);
		edt_service_shangsong.setText(mServiceShangsong);
		edt_threshold_level.setText(mThresholdLevel);
	}

	private void initView() {
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("应用参数设置");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);

		edt_quit_password = (EditText) findViewById(R.id.edt_app_parameter_quit_password);

		edt_max_money = (EditText) findViewById(R.id.edt_app_parameter_max_money);
		edt_service_shangsong = (EditText) findViewById(R.id.edt_service_shangsong);
		edt_threshold_level=(EditText) findViewById(R.id.edt_threshold_level);

		rb_one=(RadioButton) findViewById(R.id.radiobtn_oneweek);
		rb_two=(RadioButton) findViewById(R.id.radiobtn_twoweek);

		findViewById(R.id.btn_app_parameter_save).setOnClickListener(this);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_title_btn_back:
			myBack();
			break;
		case R.id.btn_app_parameter_save:
			saveSetting();
			break;

		default:
			break;
		}
	}

	private void saveSetting() {
		// TODO Auto-generated method stub
		mModelExitPassword = edt_quit_password.getText().toString().trim();
		mMaxMoney = edt_max_money.getText().toString().trim();
		mServiceShangsong = edt_service_shangsong.getText().toString().trim();
		mThresholdLevel=edt_threshold_level.getText().toString().trim();
		//设置定额退出密码
		if (StringUtils.isBlank(mModelExitPassword)) {
			ToastUtil.showLong(mActivity, "请设置消费界面退出密码！");
			isOK=false;
		}else{
			if (APPTools.isNumeric(mModelExitPassword)) {
				SPUtils.put(mActivity, SPUtils.ConsumptionModelExitPassword,
						mModelExitPassword);
			}else{
				ToastUtil.showLong(mActivity, "消费界面退出密码只可为数字，请重新设置！");
				edt_quit_password.setText("");
				isOK=false;
			}
		}
		//设置最大金额
		if (StringUtils.isBlank(mMaxMoney)) {
			ToastUtil.showLong(mActivity, "请设置最大金额！");
			isOK=false;
		} else {
			if (!APPTools.isNumeric(mMaxMoney)) {
				ToastUtil.showLong(mActivity, "最大金额格式错误，请重新设置！");
				isOK=false;
			} else {
				if (APPTools.isMoney(mMaxMoney)) {
					SPUtils.put(mActivity, SPUtils.MaxMoney, mMaxMoney);
				} else {
					ToastUtil.showLong(mActivity, "请设置正确金额！");
					isOK=false;
				}
			}
		}
		//设置上送时间
		if (StringUtils.isBlank(mServiceShangsong)) {
			ToastUtil.showLong(mActivity, "请设置离线上送时间！");
			isOK=false;
		} else {
			if (!APPTools.isNumeric(mServiceShangsong)) {
				ToastUtil.showLong(mActivity, "离线上送时间格式错误，请重新设置！");
				isOK=false;
			} else {
				SPUtils.put(mActivity, SPUtils.Service_ShangSong,
						mServiceShangsong);
			}
		}
		//设置异常流水阀值
		if (StringUtils.isBlank(mThresholdLevel)) {
			ToastUtil.showLong(mActivity, "请设置异常流水阀值！");
			isOK=false;
		}else{
			if (APPTools.isNumeric(mThresholdLevel)&&Integer.parseInt(mThresholdLevel)>700) {
				SPUtils.put(mActivity, SPUtils.ThresholdLevel,
						mThresholdLevel);
			}else{
				ToastUtil.showLong(mActivity, "异常流水阀值只可为数字且阀值不低于700，请重新设置！");
				edt_threshold_level.setText("");
				isOK=false;
			}
		}
		//设置清理历史流水周期
		if (rb_one.isChecked()) {
			SPUtils.put(mActivity, SPUtils.CLEAR_TIME,604800000L);
		}else if (rb_two.isChecked()) {
			SPUtils.put(mActivity, SPUtils.CLEAR_TIME,1209600000L);
		}

		if (isOK) {
			ToastUtil.showLong(mActivity, "参数设置成功！");
			// 启动批上送服务
			Intent intent = new Intent(this, UpLiushuiService.class);
			stopService(intent);
			//			Intent intent2 = new Intent(this, UpTrandToServer.class);
			//			stopService(intent2);
			startActivity(new Intent(mActivity, MainActivity.class));
			ScreenManager.getScreenManager().popAlls();
		}
		isOK=true;
	}

}
