package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.User;
import cn.wonders.pos_qdg.db.UserDao;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.ToastUtil;

/**
 * 签到页面
 * @author Administrator
 *
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

	private Activity mActivity;
	private EditText mEDT_LA_Account;
	private EditText mEDT_LA_Password;
	private ScreenManager mScreenManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mActivity = LoginActivity.this;
		mScreenManager = CloudposApplication.getInstance().mScreenManager;
		initView();
		
	}

	
	
	private void initView() {
		mEDT_LA_Account = (EditText) findViewById(R.id.edt_la_account);
		mEDT_LA_Password = (EditText) findViewById(R.id.edt_la_password);
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
		findViewById(R.id.activity_title_btn_back).setVisibility(View.GONE);;
		findViewById(R.id.btn_la_login).setOnClickListener(this);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("签到");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.activity_title_btn_back:
			myBack();
			break;
		case R.id.btn_la_login:
			login();
			break;

		default:
			break;
		}
		
	}

	private void login() {
		// TODO Auto-generated method stub
		String mAccount = mEDT_LA_Account.getText().toString().trim();
		String mPassword = mEDT_LA_Password.getText().toString().trim();
		
		List<User> users = new UserDao(mActivity).getUser("account", mAccount);
		
		if(users!=null && users.size()>0 && mPassword.equals(users.get(0).getPassword())){
			SPUtils.put(mActivity, SPUtils.CURRENT_OPERATOR, users.get(0).getAccount());
			SPUtils.put(mActivity, SPUtils.CURRENT_OPERATOR_LV, users.get(0).getLevel());
			CloudposApplication.getInstance().setmUser(users.get(0));
			startActivity(new Intent(mActivity, MainHomeActivity.class));
			mScreenManager.popActivity();
		}else{
			ToastUtil.showLong(mActivity, "编号或者密码不对！");
		}
	}

	private void myBack() {
		// TODO Auto-generated method stub
		mScreenManager.popAlls();
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
