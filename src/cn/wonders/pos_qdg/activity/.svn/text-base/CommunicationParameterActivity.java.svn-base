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
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.ToastUtil;

/**
 * 商户设置页面
 * 
 * @author n_n
 *
 */
public class CommunicationParameterActivity extends BaseActivity implements
OnClickListener {
	private Activity mActivity;
	private EditText edt_tpdu, edt_shanghu;
	private String Tpdu, Shanghu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_communication_parameter);
		mActivity = CommunicationParameterActivity.this;
		// 设置软键盘在进入界面时不弹出
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		initView();
	}

	private void initView() {
		Tpdu = SPUtils.getString(mActivity, SPUtils.TPDU, "");
		Shanghu = SPUtils.getString(mActivity, SPUtils.ShanghuNo, "");
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("商户设置");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);

		findViewById(R.id.btn_communication_save).setOnClickListener(this);
		edt_tpdu = (EditText) findViewById(R.id.edt_communication_tpdu);
		edt_shanghu = (EditText) findViewById(R.id.edt_communication_shanghu);
		edt_tpdu.setText(Tpdu);
		edt_shanghu.setText(Shanghu);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_communication_save:
			Tpdu = edt_tpdu.getText().toString().trim();
			Shanghu = edt_shanghu.getText().toString().trim();
			if (APPTools.isNumeric(Tpdu) && APPTools.isNumeric(Shanghu)
					&& Tpdu.length() == 10 && Shanghu.length() == 15) {

				SPUtils.put(mActivity, SPUtils.TPDU, Tpdu);
				SPUtils.put(mActivity, SPUtils.ShanghuNo, Shanghu);

				ToastUtil.showLong(mActivity, "设置成功！");
				startActivity(new Intent(mActivity, MainHomeActivity.class));
				ScreenManager.getScreenManager().popAlls();
			} else {
				ToastUtil.showLong(mActivity, "请设置正确参数！");
			}
			break;
		case R.id.activity_title_btn_back:
			myBack();
			break;

		default:
			break;
		}
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

}
