package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.R.layout;
import cn.wonders.pos_qdg.R.menu;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.ToastUtil;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 密钥管理页面
 * @author n_n
 *
 */
public class KeyManageActivity extends BaseActivity implements OnClickListener {
	private Activity mActivity;
	private EditText edt_number,edt_main;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_key_manage);
		mActivity=KeyManageActivity.this;
		initView();
	}

	private void initView() {
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("密钥管理");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
	
		findViewById(R.id.btn_key_manage_save).setOnClickListener(this);
		edt_number=(EditText) findViewById(R.id.edt_key_manage_number);
		edt_main=(EditText) findViewById(R.id.edt_key_manage_main);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_key_manage_save:
			
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
	
	private void setParamDialog(){
		
		final Dialog mDialog = new Dialog(this, R.style.windowDilog);
		mDialog.setContentView(R.layout.dialog_key_manage);
		Button sure = (Button) mDialog
				.findViewById(R.id.iv_dialog_key_ok);
		Button back = (Button) mDialog
				.findViewById(R.id.iv_dialog_key_cancel);
		final LinearLayout ll1=(LinearLayout) mDialog.findViewById(R.id.ll_key_manage_choose);
		final LinearLayout ll2=(LinearLayout) mDialog.findViewById(R.id.ll_key_manage_success);
		
		mDialog.setCanceledOnTouchOutside(false);
		
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ll1.setVisibility(View.GONE);
				ll2.setVisibility(View.VISIBLE);
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if (mDialog.isShowing())
					mDialog.dismiss();
			}
		});
		mDialog.show();
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
