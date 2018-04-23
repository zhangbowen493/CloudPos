package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.BlackList;
import cn.wonders.pos_qdg.bean.QuotaRate;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.WorkTimeParma;
import cn.wonders.pos_qdg.db.BlackListDao;
import cn.wonders.pos_qdg.db.QuotaRateDao;
import cn.wonders.pos_qdg.db.TradingDao;
import cn.wonders.pos_qdg.db.WorkTimeParmaDao;
import cn.wonders.pos_qdg.service.ClearTradingService;
import cn.wonders.pos_qdg.service.UpLiushuiService;
import cn.wonders.pos_qdg.tool8583.CodeUtils;
import cn.wonders.pos_qdg.tool8583.Connect;
import cn.wonders.pos_qdg.util.BuzzerTools;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ToastUtil;
import cn.wonders.pos_qdg.util.UpUtils;

/**
 * 主菜单页面
 * 
 * @author Administrator
 * 
 */
public class MainHomeActivity extends Activity implements OnClickListener {
	// 签到成功
	protected static final int SIGNINSUCCESS = 0;
	// 签到失败
	protected static final int SIGNINFAIL = 1;
	/** 银联定额下载 */
	// handler 参数 成功
	public final static int upYLQPInt = 100;
	// handler 参数 失败
	public final static int upYLQHandlerFild = 999;
	/** 没有参数 tpdu **/
	public final static int notTPDU = 5;
	private Activity mActivity;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case notTPDU:
				ToastUtil.showLong(mActivity, "请配置参数TPDU！");
				break;
			case SIGNINSUCCESS:
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						BuzzerTools.getInstance().startOKV(mActivity,
								BuzzerTools.STATE_QD_SUCCESS);
					}
				}).start();
				ToastUtil.showLong(mActivity, "签到成功！");
				break;
			case SIGNINFAIL:
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						BuzzerTools.getInstance().startOKV(mActivity,
								BuzzerTools.STATE_QD_FAIL);
					}
				}).start();
				ToastUtil.showLong(mActivity, "签到失败，请检查网络是否通畅");
				break;
			case MainActivity.upYLQPInt:
				ToastUtil.showLong(mActivity, "服务器参数已更新····");
				CloudposApplication.getInstance().setIsUpdate(false);
				break;
			case MainActivity.upYLQHandlerFild:
				ToastUtil.showLong(mActivity, "服务器参数更新失败，请检查网络是否通畅");
			case 6:
				ToastUtil.showLong(mActivity, "请配置系统参数！");
				break;
			default:
				break;
			}
		};
	};

	String field48 = "00000000000000000000000000000000000000000000000000000000000000";
	String field4 = "000000000001";
	String field2 = "6231700180007160672";
	EditText et_pici;
	String pici;
	Thread thread;
	private boolean isCanUse = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_home);
		this.mActivity = MainHomeActivity.this;

		initView();
		initData();
		printInfo();
		// 启动批上送服务-银联
		Intent intent = new Intent(MainHomeActivity.this,
				UpLiushuiService.class);
		LogUtil.e("启动上送服务");
		startService(intent);
		// 流水清理
		Intent intent2 = new Intent(MainHomeActivity.this,
				ClearTradingService.class);
		startService(intent2);
		// 上送异常流水
		upDateAbnormalLiushui();
	}

	private void upDateAbnormalLiushui() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String miyao = SPUtils.getString(mActivity, SPUtils.UPK, "");
				String fuwuqiip = SPUtils.getString(mActivity, SPUtils.IP, "");
				String fuwuqiport = SPUtils.getString(mActivity, SPUtils.port,
						"");
				String zhongduanID = SPUtils.getString(mActivity,
						SPUtils.zhongduanID, "");
				if (miyao == "" || fuwuqiip == "" || fuwuqiport == ""
						|| zhongduanID == "") {
					handler.sendEmptyMessage(6);
				} else {
					UpUtils up = new UpUtils(mActivity, null);
					up.uploadAbnormalLiushui();
				}
			}
		}).start();
	}

	/**
	 * 屏蔽home键 无法放到BaseActivity 每个页面单独处理
	 */
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window win = getWindow();
		try {
			Class<?> cls = win.getClass();
			final Class<?>[] PARAM_TYPES = new Class[] { int.class };
			Method method = cls.getMethod("addCustomFlags", PARAM_TYPES);
			method.setAccessible(true);
			method.invoke(win, new Object[] { 0x00000001 });
		} catch (Exception e) {
			// handle the error here.
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
		}
		return true;
	}

	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
		findViewById(R.id.activity_title_btn_back).setVisibility(View.GONE);
		;
		findViewById(R.id.btn_manage).setOnClickListener(this);
		findViewById(R.id.btn_pop_consume).setOnClickListener(this);
		findViewById(R.id.btn_poo_standard).setOnClickListener(this);
		findViewById(R.id.btn_help).setOnClickListener(this);
		findViewById(R.id.btn_breakfast).setOnClickListener(this);
		findViewById(R.id.btn_pay_scan).setOnClickListener(this);
		findViewById(R.id.btn_pay_ftf).setOnClickListener(this);
		findViewById(R.id.btn_expense).setOnClickListener(this);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("主菜单");

		findViewById(R.id.btn_qiandao).setOnClickListener(this);
		findViewById(R.id.btn_qiantui).setOnClickListener(this);
		findViewById(R.id.btn_pijiesuan).setOnClickListener(this);
		findViewById(R.id.btn_pishangsong).setOnClickListener(this);
		findViewById(R.id.btn_liushuitongzhi).setOnClickListener(this);
		findViewById(R.id.btn_POScanshuchuandi).setOnClickListener(this);
		findViewById(R.id.btn_huixiang).setOnClickListener(this);
		findViewById(R.id.btn_POSzhuangtaishangsong).setOnClickListener(this);
		findViewById(R.id.btn_xiazai).setOnClickListener(this);
		findViewById(R.id.btn_lixianjiaoyi).setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub
		String IP = SPUtils.getString(mActivity, SPUtils.CUP_IP, "");
		String PORT = SPUtils.getString(mActivity, SPUtils.CUP_POR, "");
		String miyao = SPUtils.getString(mActivity, SPUtils.UPK, "");
		String fuwuqiip = SPUtils.getString(mActivity, SPUtils.IP, "");
		String fuwuqiport = SPUtils.getString(mActivity, SPUtils.port, "");
		String mTerminalNo = SPUtils.getString(mActivity, SPUtils.TerminalNo,
				"");
		String zhongduanID = SPUtils.getString(mActivity, SPUtils.zhongduanID,
				"");
		if (IP == "" || PORT == "" || miyao == "" || fuwuqiip == ""
				|| fuwuqiport == "" || mTerminalNo == "" || zhongduanID == "") {
			ToastUtil.showLong(mActivity, "请配置系统参数！");
			isCanUse = false;
		} else {
			if (CloudposApplication.respCode.equals("00")) {
				handler.sendEmptyMessage(SIGNINSUCCESS);
			} else {
				handler.sendEmptyMessage(SIGNINFAIL);
			}
			if (CloudposApplication.isUpdate) {
				handler.sendEmptyMessage(upYLQPInt);
			} else {
				handler.sendEmptyMessage(upYLQHandlerFild);
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_pop_consume:
			// 定额消费
			if (isCanUse) {
				startActivity(new Intent(this, ConsumeQuotaActivity.class));
			} else {
				ToastUtil.showLong(mActivity, "请配置参数！");
			}
			break;
		case R.id.btn_poo_standard:
			// 标准消费
			if (isCanUse) {
				startActivity(new Intent(this, ConsumeStandardActivity.class));
			} else {
				ToastUtil.showLong(mActivity, "请配置参数！");
			}
			break;
		case R.id.btn_manage:
			// 管理
			startActivity(new Intent(this, ManageActivity.class));
			break;
		case R.id.btn_breakfast:
			// 快捷早餐
			if (isCanUse) {
				startActivity(new Intent(this, ConsumeQuickActivity.class));
			} else {
				ToastUtil.showLong(mActivity, "请配置参数！");
			}
			// addData();
			// deleteData();
			break;
		default:
			break;
		}
	}

	private void addData() {

		ArrayList<QuotaRate> list = new ArrayList<QuotaRate>();
		QuotaRate bean = new QuotaRate();
		bean.setCanteenId("233");
		bean.setCardType("00");
		bean.setQuotaRate(1);
		bean.setVersion("2016111534");
		list.add(bean);
		QuotaRate bean1 = new QuotaRate();
		bean1.setCanteenId("233");
		bean1.setCardType("01");
		bean1.setQuotaRate(1);
		bean1.setVersion("2016111534");
		list.add(bean1);
		Integer addQuotaRate = new QuotaRateDao(mActivity).addQuotaRate(list);

		ArrayList<WorkTimeParma> list1 = new ArrayList<WorkTimeParma>();
		WorkTimeParma beanl = new WorkTimeParma();
		beanl.setIsQuata("");
		beanl.setQuataAmount("0.0");
		beanl.setVersion("2016111534");
		beanl.setCanteenID("2");
		beanl.setStartTime("19:00");
		beanl.setEndTime("23:00");
		beanl.setMealType("00");
		beanl.setTimes("254");
		list1.add(beanl);
		WorkTimeParma bean2 = new WorkTimeParma();
		bean2.setIsQuata("");
		bean2.setQuataAmount("0.0");
		bean2.setVersion("2016111534");
		bean2.setCanteenID("2");
		bean2.setStartTime("3:00");
		bean2.setEndTime("9:00");
		bean2.setMealType("01");
		bean2.setTimes("254");
		list1.add(bean2);
		Integer addWorkTimeList = new WorkTimeParmaDao(mActivity)
				.addWorkTimeList(list1);

		LogUtil.e("addQuotaRate=" + addQuotaRate + "addWorkTimeList="
				+ addWorkTimeList);
	}

	/**
	 * 打印已下载数据信息
	 */
	private void printInfo() {
		List<WorkTimeParma> queryAllWorkTime = new WorkTimeParmaDao(mActivity)
				.queryAllWorkTime();
		for (int i = 0; i < queryAllWorkTime.size(); i++) {
			LogUtil.e("营业时间：" + queryAllWorkTime.get(i).toString());
		}

		List<QuotaRate> quotaAllList = new QuotaRateDao(mActivity)
				.queryAllList();
		for (int i = 0; i < quotaAllList.size(); i++) {
			LogUtil.e("税率：" + quotaAllList.get(i).toString());
		}

		List<BlackList> queryAllBlackList = new BlackListDao(mActivity)
				.queryAllBlackList();

		for (int i = 0; i < queryAllBlackList.size(); i++) {
			LogUtil.e("黑名单卡号："
					+ queryAllBlackList.get(i).getCard_Number().toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_home, menu);
		return true;
	}

}
