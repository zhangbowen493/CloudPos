package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.ToastUtil;

/**
 * 参数设置界面
 * 
 * @author Luckydog
 *
 */
public class ParameterSettingActivity extends Activity implements
		OnClickListener {

	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parameter_setting);
		this.mActivity = ParameterSettingActivity.this;
		initView();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initView() {
		findViewById(R.id.btn_application).setOnClickListener(this);
		findViewById(R.id.btn_system).setOnClickListener(this);
		findViewById(R.id.btn_download).setOnClickListener(this);
		findViewById(R.id.btn_money_setting).setOnClickListener(this);
		findViewById(R.id.btn_shanghu).setOnClickListener(this);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("参数设置");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_application:
			startActivity(new Intent(mActivity, AppParameterActivity.class));
			break;
		case R.id.btn_system:
			startActivity(new Intent(mActivity, SystemParameterActivity.class));
			break;
		case R.id.btn_download:
			ToastUtil.showLong(this, "正在更新参数，请稍后……");
			findViewById(R.id.btn_download).setEnabled(false);
			ScreenManager.getScreenManager().popAlls();
			startActivity(new Intent(ParameterSettingActivity.this,
					MainActivity.class));
			break;
		case R.id.btn_money_setting:
			startActivity(new Intent(mActivity,
					BreakfastSettingActivity.class));
			break;
		case R.id.btn_shanghu:
			startActivity(new Intent(mActivity,
					CommunicationParameterActivity.class));
			break;
		case R.id.activity_title_btn_back:
			myBack();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
	 * 屏蔽home键
	 * 无法放到BaseActivity
	 * 每个页面单独处理
	 */
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    Window win = getWindow();
	    try {
	        Class<?> cls = win.getClass();
	        final Class<?>[] PARAM_TYPES = new Class[] {int.class};
	        Method method = cls.getMethod("addCustomFlags", PARAM_TYPES);
	        method.setAccessible(true);
	        method.invoke(win, new Object[] {0x00000001});
	    } catch (Exception e) {
	        // handle the error here.
	    }
	}
}
