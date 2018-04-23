package cn.wonders.pos_qdg.activity;

import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.R.layout;
import cn.wonders.pos_qdg.R.menu;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.User;
import cn.wonders.pos_qdg.db.UserDao;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.ToastUtil;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
/**
 * 修改密码
 * @author Administrator
 *
 */
public class ChangePasswordActivity extends BaseActivity implements OnClickListener {
	
	private Activity mActivity;
	private EditText mEDT_CPA_OldPassword;
	private EditText mEDT_CPA_NewPassword;
	private EditText mEDT_CPA_NewPasswordAgain;
	private TextView mTV_CPA_UserNO;
	private User user;
	private UserDao userDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		mActivity = ChangePasswordActivity.this;
		
		initView();
		initData();
	}

	private void initData() {
		userDao = new UserDao(mActivity);
		
		user = CloudposApplication.getInstance().getmUser();
		mTV_CPA_UserNO.setText(user.getAccount());
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		
		mTV_CPA_UserNO = (TextView) findViewById(R.id.tv_cpa_operator_number);
		
		mEDT_CPA_OldPassword = (EditText) findViewById(R.id.edt_cpa_old_password);
		mEDT_CPA_NewPassword = (EditText) findViewById(R.id.edt_cpa_new_password);
		mEDT_CPA_NewPasswordAgain = (EditText) findViewById(R.id.edt_cpa_new_password_again);
		
		findViewById(R.id.btn_cpa_change).setOnClickListener(this);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("修改密码");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_cpa_change:
			change();
			break;
		case R.id.activity_title_btn_back:
			myBack();
			break;

		default:
			break;
		}
	}
	
	private void change() {
		// TODO Auto-generated method stub
		String oldPassword = mEDT_CPA_OldPassword.getText().toString().trim();
		String newPassword = mEDT_CPA_NewPassword.getText().toString().trim();
		String newPasswordAgain = mEDT_CPA_NewPasswordAgain.getText().toString().trim();
		
		if(user.getPassword().equals(oldPassword)){
			if(newPassword.equals(newPasswordAgain)){
				user.setPassword(newPassword);
				int updateUser = userDao.updateUser(user);
				if(updateUser == 1){
					ToastUtil.showShort(mActivity, "密码修改成功！");
					mEDT_CPA_OldPassword.setText("");
					mEDT_CPA_NewPassword.setText("");
					mEDT_CPA_NewPasswordAgain.setText("");
					CloudposApplication.getInstance().setmUser(user);
				}else{
					ToastUtil.showShort(mActivity, "密码修改失败！");
				}
			}else{
				ToastUtil.showShort(mActivity, "两次新密码不一致!");
			}
		}else{
			ToastUtil.showShort(mActivity, "原密码错误!");
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

}
