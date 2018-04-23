package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.adapter.BaseAdapter;
import cn.wonders.pos_qdg.adapter.PeopleAdapter;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.User;
import cn.wonders.pos_qdg.db.UserDao;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.ToastUtil;
/**
 * 人员维护 页面
 * @author Administrator
 *
 */
public class PeopleManagementActivity extends BaseActivity implements OnClickListener,OnItemClickListener{
	private ListView lv_people;
	private RelativeLayout rl_add_people;
	private TextView tv_add_operator;
	private BaseAdapter adapter;
	private Activity mActivity;
	private ArrayList<User> users;
	private UserDao userDao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_people_management);
		this.mActivity=PeopleManagementActivity.this;
		initView();
		
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		userDao = new UserDao(mActivity);
		ArrayList allUser = (ArrayList) userDao.getAllUser();
		adapter.setData(allUser);
		adapter.notifyDataSetChanged();
	}

	private void initView() {
		findViewById(R.id.rl_add_people).setOnClickListener(this);
		lv_people=(ListView) findViewById(R.id.lv_people);
		lv_people.setOnItemClickListener(this);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("人员维护");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
		adapter=new PeopleAdapter(this,users);
		lv_people.setAdapter(adapter);
		
		rl_add_people=(RelativeLayout) findViewById(R.id.rl_add_people);
		tv_add_operator=(TextView) findViewById(R.id.tv_add_operator);
		rl_add_people.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_add_people:
			setParamDialog();
			break;
		case R.id.activity_title_btn_back:
			myBack();
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
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
		mDialog.setContentView(R.layout.dialog_people_manage);
		ImageView sure = (ImageView) mDialog
				.findViewById(R.id.iv_dialog_people_ok);
		ImageView back = (ImageView) mDialog
				.findViewById(R.id.iv_dialog_people_cancel);
		final EditText param_account = (EditText) mDialog
				.findViewById(R.id.edt_dialog_people_account);
		final EditText param_password = (EditText) mDialog
				.findViewById(R.id.edt_dialog_people_password);
		mDialog.setCanceledOnTouchOutside(false);
		
		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String mAccount = param_account.getText().toString().trim();
				String mPassword = param_password.getText().toString().trim();
				
				List<User> users = userDao.getUser("account", mAccount);
				if(users!=null && users.size()>0){
					ToastUtil.showLong(mActivity,"用户已存在，请勿重复添加！");
				}else{
					ArrayList<User> list = new ArrayList<User>() ;
					User user = new User();
					user.setAccount(mAccount);
					user.setPassword(mPassword);
					user.setLevel(0);
					list.add(user);
					Integer addUserList = userDao.addUserList(list);
					list.clear();
					if(addUserList!=1){
						ToastUtil.showShort(mActivity, "操作员添加失败，请重新添加！");
					}else{
						
						ToastUtil.showShort(mActivity, "操作员添加成功！");
						ArrayList allUser = (ArrayList) userDao.getAllUser();
						adapter.setData(allUser);
						adapter.notifyDataSetChanged();
						
					}
					if (mDialog.isShowing())
						mDialog.dismiss();
				}
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
