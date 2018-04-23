package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.ScreenManager;
/**
 * 下传参数设置页面
 * @author n_n
 *
 */
public class DownloadParameterActivity extends BaseActivity implements OnClickListener {
	private Activity mActivity;
	private EditText edt_discount,edt_black,edt_white,edt_control,edt_unionpay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_parameter);
		mActivity=DownloadParameterActivity.this;
		initView();
	}

	private void initView() {
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("下传参数设置");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
	
		findViewById(R.id.btn_download_parameter_update).setOnClickListener(this);
		edt_discount=(EditText) findViewById(R.id.edt_download_parameter_discount);
		edt_black=(EditText) findViewById(R.id.edt_download_parameter_black);
		edt_white=(EditText) findViewById(R.id.edt_download_parameter_white);
		edt_control=(EditText) findViewById(R.id.edt_download_parameter_control);
		edt_unionpay=(EditText) findViewById(R.id.edt_download_parameter_unionpay);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_title_btn_back:
			myBack();
			break;
		case R.id.btn_download_parameter_update:
			
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
