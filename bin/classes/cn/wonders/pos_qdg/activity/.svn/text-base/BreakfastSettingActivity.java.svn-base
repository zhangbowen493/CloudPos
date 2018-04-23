package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
 * 早餐金额设置页面
 * 
 * @author n_n
 *
 */
public class BreakfastSettingActivity extends BaseActivity implements
		OnClickListener {
	private Activity mActivity;
	private EditText et_1,et_2,et_3,et_4,et_5,et_6,et_7,et_8,et_9,et_10,et_11;
	private String money_1,money_2,money_3,money_4,money_5,money_6,money_7,money_8,money_9,money_10,money_11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breakfast_setting);
		mActivity = BreakfastSettingActivity.this;
		initView();
		initData();
	}

	private void initData() {
		money_1 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_1_MONEY, "0.1");
		money_2 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_2_MONEY, "0.2");
		money_3 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_3_MONEY, "0.3");
		money_4 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_4_MONEY, "0.4");
		money_5 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_5_MONEY, "0.5");
		money_6 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_6_MONEY, "0.6");
		money_7 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_7_MONEY, "0.7");
		money_8 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_8_MONEY, "0.8");
		money_9 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_9_MONEY, "0.9");
		money_10 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_10_MONEY, "1");
		money_11 = SPUtils.getString(mActivity,SPUtils.BREAKFAST_11_MONEY, "2");

		et_1.setText(money_1);
		et_2.setText(money_2);
		et_3.setText(money_3);
		et_4.setText(money_4);
		et_5.setText(money_5);
		et_6.setText(money_6);
		et_7.setText(money_7);
		et_8.setText(money_8);
		et_9.setText(money_9);
		et_10.setText(money_10);
		et_11.setText(money_11);
	}

	private void initView() {
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("消费金额设置");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);

		et_1 = (EditText) findViewById(R.id.et_1);
		et_2 = (EditText) findViewById(R.id.et_2);
		et_3 = (EditText) findViewById(R.id.et_3);
		et_4 = (EditText) findViewById(R.id.et_4);
		et_5 = (EditText) findViewById(R.id.et_5);
		et_6 = (EditText) findViewById(R.id.et_6);
		et_7 = (EditText) findViewById(R.id.et_7);
		et_8 = (EditText) findViewById(R.id.et_8);
		et_9 = (EditText) findViewById(R.id.et_9);
		et_10 = (EditText) findViewById(R.id.et_10);
		et_11 = (EditText) findViewById(R.id.et_11);

		findViewById(R.id.btn_package12).setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_title_btn_back:
			myBack();
			break;
		case R.id.btn_package12:
			saveSetting();
			break;

		default:
			break;
		}
	}

	private void saveSetting() {
		// TODO Auto-generated method stub
		money_1 = et_1.getText().toString().trim();
		money_2 = et_2.getText().toString().trim();
		money_3 = et_3.getText().toString().trim();
		money_4 = et_4.getText().toString().trim();
		money_5 = et_5.getText().toString().trim();
		money_6 = et_6.getText().toString().trim();
		money_7 = et_7.getText().toString().trim();
		money_8 = et_8.getText().toString().trim();
		money_9 = et_9.getText().toString().trim();
		money_10 = et_10.getText().toString().trim();
		money_11 = et_11.getText().toString().trim();

		if (APPTools.isEmpty(money_1)||APPTools.isEmpty(money_2)||APPTools.isEmpty(money_3)
				||APPTools.isEmpty(money_4)||APPTools.isEmpty(money_5)||APPTools.isEmpty(money_6)
				||APPTools.isEmpty(money_7)||APPTools.isEmpty(money_8)||APPTools.isEmpty(money_9)
				||APPTools.isEmpty(money_10)||APPTools.isEmpty(money_11)) {
			ToastUtil.showLong(mActivity, "金额不能为空！");
		}else{
			if (APPTools.isMoney(money_1)&&APPTools.isMoney(money_2)&&APPTools.isMoney(money_3)
					&&APPTools.isMoney(money_4)&&APPTools.isMoney(money_5)&&APPTools.isMoney(money_6)
					&&APPTools.isMoney(money_7)&&APPTools.isMoney(money_8)&&APPTools.isMoney(money_9)
					&&APPTools.isMoney(money_10)&&APPTools.isMoney(money_11)) {
				
				SPUtils.put(mActivity, SPUtils.BREAKFAST_1_MONEY,money_1);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_2_MONEY,money_2);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_3_MONEY,money_3);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_4_MONEY,money_4);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_5_MONEY,money_5);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_6_MONEY,money_6);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_7_MONEY,money_7);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_8_MONEY,money_8);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_9_MONEY,money_9);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_10_MONEY,money_10);
				SPUtils.put(mActivity, SPUtils.BREAKFAST_11_MONEY,money_11);
				ToastUtil.showLong(mActivity, "金额设置成功！");
				startActivity(new Intent(mActivity, MainHomeActivity.class));
			} else {
				ToastUtil.showLong(mActivity, "请设置正确金额！");
			}
		}
	}

}
