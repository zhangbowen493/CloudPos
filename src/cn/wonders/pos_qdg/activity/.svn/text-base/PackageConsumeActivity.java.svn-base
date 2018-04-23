package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.StringUtils;
import cn.wonders.pos_qdg.util.ToastUtil;
/**
 * 套餐消费
 */
public class PackageConsumeActivity extends BaseActivity implements OnClickListener {

	private TextView tv_id,tv_consume,tv_name,tv_balance;
	private TextView tv_pac_money;
	private Activity mActivity;
	/**套餐价格*/
	private String mPackage1MoneyStr, mPackage2MoneyStr, mPackage3MoneyStr, mPackage4MoneyStr, mPackage5MoneyStr, mPackage6MoneyStr, mPackage7MoneyStr,mPackageOtherMoneyStr;
	private int PackageIndex = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_consume);
		this.mActivity=PackageConsumeActivity.this;
		initView();
		initData();
	}

	private void initData() {
		mPackage1MoneyStr =  SPUtils.getString(mActivity, SPUtils.PACKAGE_1_MONEY, "0.0");
		mPackage2MoneyStr =  SPUtils.getString(mActivity, SPUtils.PACKAGE_2_MONEY, "0.0");
		mPackage3MoneyStr =  SPUtils.getString(mActivity, SPUtils.PACKAGE_3_MONEY, "0.0");
		mPackage4MoneyStr =  SPUtils.getString(mActivity, SPUtils.PACKAGE_4_MONEY, "0.0");
		mPackage5MoneyStr =  SPUtils.getString(mActivity, SPUtils.PACKAGE_5_MONEY, "0.0");
		mPackage6MoneyStr =  SPUtils.getString(mActivity, SPUtils.PACKAGE_6_MONEY, "0.0");
		mPackage7MoneyStr =  SPUtils.getString(mActivity, SPUtils.PACKAGE_7_MONEY, "0.0");
		
		tv_pac_money.setText(mPackage1MoneyStr +"元");
	}

	private void initView() {
		tv_pac_money = (TextView) findViewById(R.id.tv_qca_money);
		tv_id=(TextView) findViewById(R.id.tv_id);
		tv_consume=(TextView) findViewById(R.id.tv_consume);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_balance=(TextView) findViewById(R.id.tv_balance);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("套餐选择消费模式");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
		
		findViewById(R.id.btn_affirm).setOnClickListener(this);
		findViewById(R.id.btn_package1).setOnClickListener(this);
		findViewById(R.id.btn_package2).setOnClickListener(this);
		findViewById(R.id.btn_package3).setOnClickListener(this);
		findViewById(R.id.btn_package4).setOnClickListener(this);
		findViewById(R.id.btn_package5).setOnClickListener(this);
		findViewById(R.id.btn_package6).setOnClickListener(this);
		findViewById(R.id.btn_package7).setOnClickListener(this);
		findViewById(R.id.btn_package8).setOnClickListener(this);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_affirm:
			//确认消费
			
			break;
		case R.id.btn_package1:
			PackageIndex = 1;
			tv_pac_money.setText(mPackage1MoneyStr +"元");
			break;
		case R.id.btn_package2:
			PackageIndex = 2;
			tv_pac_money.setText(mPackage2MoneyStr +"元");
			
			break;
		case R.id.btn_package3:
			PackageIndex = 3;
			tv_pac_money.setText(mPackage3MoneyStr +"元");
			
			break;
		case R.id.btn_package4:
			PackageIndex = 4;
			tv_pac_money.setText(mPackage4MoneyStr +"元");
			
			break;
		case R.id.btn_package5:
			PackageIndex = 5;
			tv_pac_money.setText(mPackage5MoneyStr +"元");
			
			break;
		case R.id.btn_package6:
			
			PackageIndex = 6;
			tv_pac_money.setText(mPackage6MoneyStr +"元");
			break;
		case R.id.btn_package7:
			PackageIndex = 7;
			tv_pac_money.setText(mPackage7MoneyStr +"元");
			
			break;
		case R.id.btn_package8:
			setParamDialog();
			break;
		case R.id.activity_title_btn_back:
			myBack();
			break;

		default:
			break;
		}
	}
	
	private void setParamDialog(){
		
		final Dialog mDialog = new Dialog(this, R.style.windowDilog);
		mDialog.setContentView(R.layout.dialog_package_other);
		Button sure = (Button) mDialog
				.findViewById(R.id.other_dialog_sure);
		Button back = (Button) mDialog
				.findViewById(R.id.other_dialog_back);
		final EditText param_new_value = (EditText) mDialog.findViewById(R.id.edt_dialog_new_value);
		mDialog.setCanceledOnTouchOutside(false);
		
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String paramNewValue = param_new_value.getText().toString().trim();
				if(paramNewValue!=null && !StringUtils.isBlank(paramNewValue)){
					PackageIndex = 8;
					mPackageOtherMoneyStr = paramNewValue;
					tv_pac_money.setText(paramNewValue +"元");
				}else{
					ToastUtil.showLong(mActivity, "金额输入有误!");
				}
				
				if (mDialog.isShowing())
					mDialog.dismiss();
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
