package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.adapter.BaseAdapter;
import cn.wonders.pos_qdg.adapter.TradeAdapter;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.ScreenManager;
/**
 * 交易查询页面
 * @author n_n
 *
 */
public class QueryTradeActivity extends BaseActivity implements OnClickListener {
	private Activity mActivity;
	private ListView lv_trade;
	private BaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_trade);
		mActivity = QueryTradeActivity.this;
		
		initView();
	}

	private void initView() {
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("交易查询");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
		lv_trade=(ListView) findViewById(R.id.lv_trade);
		adapter=new TradeAdapter(this);
		lv_trade.setAdapter(adapter);
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
		case R.id.activity_title_btn_back:
			myBack();
			break;

		default:
			break;
		}
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
