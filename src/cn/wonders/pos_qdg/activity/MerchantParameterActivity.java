package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;

import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.R.layout;
import cn.wonders.pos_qdg.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
/**
 * 商户参数设置页面
 * @author n_n
 *
 */
public class MerchantParameterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_merchant_parameter);
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
