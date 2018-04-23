package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.R.layout;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.ScreenManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class SettingUpActivity extends Activity implements OnClickListener{

	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_up);
		this.mActivity=SettingUpActivity.this;
		initView();
	}

	private void initView() {
		findViewById(R.id.btn_setting).setOnClickListener(this);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("设置");	
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_setting:
			startActivity(new Intent(this, ParameterSettingActivity.class));
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
		ScreenManager screenManager = CloudposApplication.getInstance().getScreenManager();
		screenManager.popActivity(mActivity);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
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
