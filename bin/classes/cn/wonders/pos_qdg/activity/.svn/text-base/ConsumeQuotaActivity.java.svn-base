package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.MOne.M1Listener;
import cn.wonders.pos_qdg.MOne.MifareOneCardReader;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.IfCanResult;
import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.QuotaRate;
import cn.wonders.pos_qdg.bean.WorkTimeParma;
import cn.wonders.pos_qdg.db.TradingAbnormalDao;
import cn.wonders.pos_qdg.db.TradingDao;
import cn.wonders.pos_qdg.db.QuotaRateDao;
import cn.wonders.pos_qdg.db.WorkTimeParmaDao;
import cn.wonders.pos_qdg.util.APPTools;
import cn.wonders.pos_qdg.util.Arith;
import cn.wonders.pos_qdg.util.BuzzerTools;
import cn.wonders.pos_qdg.util.CustomDialog;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.ScreenManager;
import cn.wonders.pos_qdg.util.TimerTools;
import cn.wonders.pos_qdg.util.ToastUtil;
import cn.wonders.pos_qdg.util.UpUtils;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFCpuCardDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesUtil;
import com.wd.liandidemo.RF.CodeUtils;
import com.wd.liandidemo.RF.PBOClistener;
import com.wd.liandidemo.RF.RFCardReaderSample;
import com.wd.liandidemo.RF.UPCardReader;

/**
 * 定额消费模式
 * 
 * @author Administrator
 * 
 */
public class ConsumeQuotaActivity extends BaseActivity implements
OnClickListener,OnLongClickListener {

	Map<String, Double> mQutaMap = new HashMap<String, Double>();
	long firstFlipTime = 0;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				initViewInfo();
				break;
			case 2:
				initViewInfo();
				if (rfCardSample != null) {
					rfCardSample.searchCard(M1onSearchListener);
				} else {
					initDervicesSlience();
				}
				break;
			case 3:
				ToastUtil.showShort(mActivity, "当前不在营业时间！");
				tv_qca_money.setText("0.0" + " 元");
				SPUtils.put(mActivity, SPUtils.CONSUME_TIMES, 0);
				tv_consume_times.setText(SPUtils.getConsumeTimes(mActivity));
				break;
			case 4:
				ToastUtil.showShort(mActivity, "请下载或更新员工优惠配置信息！");
				break;
			case 5:
				ToastUtil.showLong(mActivity, "请下载配置参数");
				break;
			case 6:
				if (isConsumeAvilable) {
					double dj = (Double) msg.obj;
					tv_qca_money.setText(dj + " 元");
					tv_consume_times
					.setText(SPUtils.getConsumeTimes(mActivity));
				}
				break;
			case 7:
				SPUtils.put(mActivity, SPUtils.CONSUME_TIMES, 0);
				tv_consume_times.setText(SPUtils.getConsumeTimes(mActivity));
				CloudposApplication.getInstance().setmQuotaMealType(mActivity,
						mQuotaMealType);
				break;
			case BuzzerTools.STOP_SERVICE:
				// 停止服务
				unbindDeviceService();
				showStopService();
				break;
			default:
				break;
			}
		};
	};
	private WorkTimeParma workTimeParma;
	/** 餐别 */
	private String mQuotaMealType;
	public static ConsumeQuotaActivity quoActivity;
	private TextView tv_id, tv_consume, tv_name, tv_balance;
	private TextView tv_qca_money;
	private Activity mActivity;
	// 当前餐别 基本消费金额
	private double dQuotaMoney;
	// 消费后读取余额数据
	private int afterBalance;

	private RFCardReaderSample rfCardSample;
	private String mQuotaMoneyStr;
	/** M1 区信息 */
	private Map<String, String> m1Info;
	private String mCardNumber;
	private String mCashReqData;
	private Thread thread;
	private boolean isCheckRunning = true;

	private TradingDao liushuiDao;

	private String mCardLiushui;
	/**
	 * 消费次数
	 */
	private TextView tv_consume_times;
	private int consume_times;
	/** 实际扣款金额 */
	private double shijimoney;
	/**
	 * 判定是否允许消费
	 */
	private boolean isConsumeAvilable = true;
	/**
	 * 是否可以用餐结果
	 */
	IfCanResult ifCanResult;
	/***
	 * 是否向下兼容
	 */
	private boolean isDown = false;
	/**
	 * 卡余额
	 */
	private String balance;
	/** 双击操作记录时间 **/
	private long firstTime = 0;
	/** 刷卡消费流程驱动器名称 */
	private String driverName;
	/** 营业配置参数 */
	private WorkTimeParmaDao workTimeParmaDao;
	/** 卡类型定额消费折扣数 */
	private QuotaRateDao quotaRateDao;
	/** 电子现金消费完成 */
	private boolean isPBOCTradingSuccess = false;
	/** 是否停止服务 */
	private boolean isStopService = false;

	private TradingAbnormalDao abnormalDao;
	/**
	 * 本餐次结束时间
	 */
	private String mQuotaEndTime;
	private TradingBean liushui;

	/**
	 * 检查当前是否为营业时间
	 */
	private void CheckTime() {
		isCheckRunning = true;
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (isCheckRunning) {
					try {
						Thread.sleep(180000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (isCheckRunning&&mQuotaEndTime!=null) {
						int a = TimerTools.getConpareTime(TimerTools.getDateFormatHM().toString(), mQuotaEndTime);
						if (a<0) {
							LogUtil.e("正在就餐中，不触发餐次清零");
						}else if (a>0) {
							LogUtil.e("就餐时间已过，开启餐次判断");
							initData();
							String mType = CloudposApplication.getInstance()
									.getmQuotaMealType(mActivity);
							LogUtil.e("餐别：" + mType);
							if (mType != null) {
								if (mType == "99") {
									// 保存餐别信息
									CloudposApplication.getInstance()
									.setmQuotaMealType(mActivity,
											mQuotaMealType);
									LogUtil.e("当前餐次："
											+ CloudposApplication.getInstance()
											.getmQuotaMealType(mActivity));
								} else if (!(mType.equals(mQuotaMealType))) {
									handler.sendEmptyMessage(7);
									LogUtil.e("定额流水清零，执行位置07");
								}
							}
						}
					}
				}
			}
		});
		thread.start();
	}

	protected void showStopService() {
		// TODO Auto-generated method stub
		ProgressDialog dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
		dialog.setCancelable(false);// 设置是否可以通过点击Back键取消
		dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
		dialog.setIcon(R.drawable.icon_tiaoshu);//
		// 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
		dialog.setTitle("提示");
		dialog.setMessage("设备异常，停止服务。请联系管理员！");
		dialog.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quota_consume);
		this.mActivity = ConsumeQuotaActivity.this;
		quoActivity = ConsumeQuotaActivity.this;
		liushuiDao = TradingDao.getInstance(mActivity);
		abnormalDao = TradingAbnormalDao
				.getInstance(mActivity);
		workTimeParmaDao = new WorkTimeParmaDao(mActivity);
		quotaRateDao = new QuotaRateDao(mActivity);
		initView();
		initData();
		initDervices();
		CheckTime();
		UpUtils up=new UpUtils(mActivity, handler);
		up.checkTradingLiushui();
	}

	/**
	 * 初始化设备参数
	 */
	private void initDervices() {
		// TODO Auto-generated method stub
		rfCardSample = new RFCardReaderSample(this) {
			public void onDeviceServiceCrash() {
				// Handle in 'RFCardActivity'
				ConsumeQuotaActivity.this.onDeviceServiceCrash();
			}

			@Override
			public void displayRFCardInfo(String cardInfo) {
				// Handle in 'RFCardActivity'
				LogUtil.i(cardInfo);
			}
		};
		StartServce(BuzzerTools.STATE_INITSUCCESS);
	}

	/**
	 * 初始化设备参数(静音)
	 */
	private void initDervicesSlience() {
		// TODO Auto-generated method stub
		unbindDeviceService();

		bindDeviceService();
		rfCardSample = new RFCardReaderSample(this) {

			public void onDeviceServiceCrash() {
				// Handle in 'RFCardActivity'
				ConsumeQuotaActivity.this.onDeviceServiceCrash();
			}

			@Override
			public void displayRFCardInfo(String cardInfo) {
				// Handle in 'RFCardActivity'
				LogUtil.i(cardInfo);
			}
		};
		StartServce(88);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		isCheckRunning = false;
		if (rfCardSample != null)
			rfCardSample.stopSearch();
		rfCardSample = null;
	}

	private void initData() {
		workTimeParma = workTimeParmaDao.getCB();
		if (workTimeParma != null) {
			isConsumeAvilable = true;
			mQuotaMoneyStr = workTimeParma.getQuataAmount();
			mQuotaMealType = workTimeParma.getMealType();
			mQuotaEndTime = workTimeParma.getEndTime();
			double parseDouble = Double.parseDouble(mQuotaMoneyStr);
			dQuotaMoney = Arith.div(parseDouble, 100, 2);
		} else {
			isConsumeAvilable = false;
			handler.sendEmptyMessage(3);
			LogUtil.e("流水清零，执行位置03");
		}

		mQutaMap.clear();
		List<QuotaRate> quotaAllList = quotaRateDao.queryAllList();
		if (quotaAllList == null || quotaAllList.size() < 1) {
			isConsumeAvilable = false;
			handler.sendEmptyMessage(4);
		} else {
			for (int i = 0; i < quotaAllList.size(); i++) {
				QuotaRate quotaRate = quotaAllList.get(i);
				String cardType = quotaRate.getCardType();
				double Rate = Arith.div(quotaRate.getQuotaRate(), 100, 2);
				mQutaMap.put(cardType, Rate);
			}
		}

		/** 基本员工优惠费率 */
		if (mQutaMap != null && mQutaMap.size() > 0) {
			double Rate = mQutaMap.get("00");
			double djbQuotaMoney = Arith.round(Arith.mul(dQuotaMoney, Rate), 2);
			Message msg = new Message();
			msg.what = 6;
			msg.obj = djbQuotaMoney;
			handler.sendMessage(msg);
		} else {
			isConsumeAvilable = false;
			handler.sendEmptyMessage(5);
		}

	}

	private void initView() {
		tv_consume_times = (TextView) findViewById(R.id.tv_consume_time);
		tv_consume_times.setText(SPUtils.getConsumeTimes(mActivity));
		tv_consume_times.setOnLongClickListener(this);;
		tv_qca_money = (TextView) findViewById(R.id.tv_qca_money);
		tv_id = (TextView) findViewById(R.id.tv_id);
		tv_consume = (TextView) findViewById(R.id.tv_consume);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_balance = (TextView) findViewById(R.id.tv_balance);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("定额餐次消费模式");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
		AnimationDrawable anima = (AnimationDrawable) findViewById(
				R.id.quota_load).getBackground();
		anima.start();
		initViewInfo();
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindDeviceService();
	}

	// Sometimes you need to release the right of using device before other
	// application 'onStart'.
	@Override
	protected void onPause() {
		super.onPause();
		unbindDeviceService();
	}

	/**
	 * If device service crashed, quit application or try to relogin service
	 * again.
	 */
	public void onDeviceServiceCrash() {
		bindDeviceService();
	}

	/**
	 * 数据库存消费流水
	 * 
	 * @param workerid
	 *            员工id
	 * @param money
	 *            消费金额
	 * @param cashReqData
	 *            电子现金消费数据
	 * @param cardNumber
	 *            卡号
	 * @return
	 */
	private TradingBean SaveTranLiushui(String workerid, String money,
			String cashReqData, String cardNumber) {
		mCardLiushui = CodeUtils.leftZeroFill(
				SPUtils.get(mActivity, SPUtils.serialData, 0).toString(), 6);
		LogUtil.e("流水号：" + mCardLiushui);
		TradingBean mLiushuiBean = APPTools.Data2Liushui(cashReqData);
		long currentTimeMillis = System.currentTimeMillis();
		if (mLiushuiBean != null) {
			mLiushuiBean.setWorkerID(workerid);
			mLiushuiBean.setCardNumber(cardNumber);
			mLiushuiBean.setTranMoney(money);
			mLiushuiBean.setTranList(mCardLiushui);
			mLiushuiBean.setTranTime(currentTimeMillis + "");
			mLiushuiBean.setStyle(mQuotaMealType);
			mLiushuiBean.setSyncTime("0");
			mLiushuiBean.setIsUpdate(0);
			mLiushuiBean.setisQuota(1);
			Integer addLiushui = liushuiDao.addLiushui(mLiushuiBean);

			if (addLiushui == 1) {
				return mLiushuiBean;
			} else {
				mLiushuiBean
				.setAbnormalCode(TradingBean.abnormal_save_trading_error);
				abnormalDao.saveAbnormalTrading(new TradingAbnormalBean(mLiushuiBean));
			}
		}else{
			mLiushuiBean = new TradingBean();
			mLiushuiBean.setWorkerID(workerid);
			mLiushuiBean.setCardNumber(cardNumber);
			mLiushuiBean.setTranMoney(money);
			mLiushuiBean.setTranList(mCardLiushui);
			mLiushuiBean.setTranTime(currentTimeMillis + "");
			mLiushuiBean.setStyle(mQuotaMealType);
			mLiushuiBean.setSyncTime("0");
			mLiushuiBean.setIsUpdate(0);
			mLiushuiBean.setisQuota(1);
			mLiushuiBean
			.setAbnormalCode(TradingBean.abnormal_parsing_trading_error);
			abnormalDao.saveAbnormalTrading(new TradingAbnormalBean(mLiushuiBean));
		}
		return null;

	}

	/**
	 * 初始化界面显示效果
	 */
	private void initViewInfo() {
		// TODO Auto-generated method stub
		tv_id.setText("");
		tv_consume.setText("元");
		tv_name.setText("");
		tv_balance.setText("元");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quota_consume, menu);
		return true;
	}

	private void myBack() {
		// TODO Auto-generated method stub
		startActivity(new Intent(mActivity, MainHomeActivity.class));
		ScreenManager screenManager = CloudposApplication.getInstance()
				.getScreenManager();
		screenManager.popActivity(mActivity);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// setParamDialog();
			LogUtil.d("123");
		}

		// return super.onKeyDown(keyCode, event);
		return false;
	}

	/**
	 * 消费退出密码输入界面
	 */
	private void setParamDialog() {

		final Dialog mDialog = new Dialog(this, R.style.windowDilog);
		mDialog.setContentView(R.layout.dialog_quota_quit);
		mDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				}
				return false;
			}
		});
		Button sure = (Button) mDialog.findViewById(R.id.btn_dialog_quota_quit);
		Button back = (Button) mDialog
				.findViewById(R.id.btn_dialog_quota_cancel);
		final EditText param_password = (EditText) mDialog
				.findViewById(R.id.edt_dialog_quota_password);
		mDialog.setCanceledOnTouchOutside(false);

		sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String exitPassword = param_password.getText().toString()
						.trim();
				String spExitPaString = SPUtils.getString(mActivity,
						SPUtils.ConsumptionModelExitPassword, "0");
				if (spExitPaString.equals(exitPassword)) {
					myBack();
					if (mDialog.isShowing())
						mDialog.dismiss();
				} else {
					ToastUtil.showLong(mActivity, "退出密码错误！");
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_title_btn_back:
			byDoubleClick();
			break;
		default:
			break;
		}
	}

	/**
	 * 双击操作
	 */
	private void byDoubleClick() {
		long secondTime = System.currentTimeMillis();
		if (secondTime - firstTime > 1000) {
			firstTime = secondTime;
		} else {
			setParamDialog();
		}
	}

	/**
	 * 显示卡消费信息
	 * 
	 * @param mQuotaMoneyStr
	 *            消费金额
	 * @param cashReqData
	 *            电子现金刷卡消费返回参数
	 * @param cardNumber
	 *            卡号
	 */
	private boolean showConsumeInfo(String mQuotaMoneyStr, String cashReqData,
			String cardNumber) {
		// TODO Auto-generated method stub

		isPBOCTradingSuccess = true;

		liushui = SaveTranLiushui(m1Info.get(CodeUtils.M1_STAFFID),
				mQuotaMoneyStr, cashReqData, cardNumber);
		if (liushui == null) {
			// 停止服务
			LogUtil.e("流水入库异常，停止服务！");
			StartServce(BuzzerTools.STOP_SERVICE);
			return false;
		} else {
//			double ec_After_Blance_d = Arith.div(afterBalance, 100, 2);
//			LogUtil.e("消费流水信息：" + liushui.toString());
//			LogUtil.e("消费后余额：" + ec_After_Blance_d + "元");
//			LogUtil.e("------------------------------");
//			tv_id.setText(m1Info.get(CodeUtils.M1_STAFFID));
//			tv_consume.setText(mQuotaMoneyStr + "元");
//			tv_name.setText(m1Info.get(CodeUtils.M1_STAFFNAME));
//			tv_balance.setText(ec_After_Blance_d + "元");
			return true;
		}

	}

	/*** 卡消费 ***/
	/*
	 * 重启消费服务
	 */
	private void StartServce(final int state) {
		switch (state) {
		case BuzzerTools.STATE_FAIL:
			rfCardSample.stopSearch();
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_FAIL);
			break;
		case BuzzerTools.STATE_ILLGALCARD:
			rfCardSample.stopSearch();
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_ILLGALCARD);
			break;
		case BuzzerTools.STATE_ILLGALTIME:
			rfCardSample.stopSearch();
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_ILLGALTIME);
			break;
		case BuzzerTools.STATE_NOBALANCE:
			if (balance != null) {
				tv_balance.setText(balance + "元");
				balance = null;
			}
			rfCardSample.stopSearch();
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_NOBALANCE);
			break;
		case BuzzerTools.STATE_OVERRUN:
			rfCardSample.stopSearch();
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_OVERRUN);
			break;
		case BuzzerTools.STATE_SUCCESS:
			showInfo();
			rfCardSample.stopSearch();
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_SUCCESS);
			break;
		case BuzzerTools.STATE_INITSUCCESS:
			rfCardSample.stopSearch();
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_INITSUCCESS);
			break;
		case BuzzerTools.STATE_QINGCHONGSHUA:
			rfCardSample.stopSearch();
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_QINGCHONGSHUA);
			break;
		case BuzzerTools.STOP_SERVICE:
			// 停止服务
			isStopService = true;
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_FAIL);
			break;
		default:
			break;
		}
		if (isStopService) {
			handler.sendEmptyMessageDelayed(BuzzerTools.STOP_SERVICE, 500);
		} else {
			isCardExist();
			//			handler.sendEmptyMessageDelayed(2, 500);
		}
	}

	private void showInfo() {
		double ec_After_Blance_d = Arith.div(afterBalance, 100, 2);
		LogUtil.e("消费流水信息：" + liushui.toString());
		LogUtil.e("消费后余额：" + ec_After_Blance_d + "元");
		LogUtil.e("------------------------------");
		tv_id.setText(m1Info.get(CodeUtils.M1_STAFFID));
//		tv_consume.setText(mQuotaMoneyStr + "元");
		tv_consume.setText(liushui.getTranMoney() + "元");
		tv_name.setText(m1Info.get(CodeUtils.M1_STAFFNAME));
		tv_balance.setText(ec_After_Blance_d + "元");
		TimerTools.timeDuration(firstFlipTime, "消费完成");
	}

	private void isCardExist() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean isWhile = true;
				while (isWhile) {
					try {
						if (!RFCardReader.getInstance().exists()) {
							isWhile = false;
							handler.sendEmptyMessageDelayed(2, 500);
						}
					} catch (RequestException e) {
						// TODO Auto-generated catch block
						LogUtil.e("QuotaConsumeActivity 判断是否有卡存在异常！");
						isWhile = false;
						// 刷卡等待时间
						handler.sendEmptyMessageDelayed(2, 1000);
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	/**
	 * 卡操作流程
	 * 
	 * @param cardDriver
	 */
	private void CardConsume() {
		if (isConsumeAvilable) {
			rfCardSample.stopSearch();
			rfCardSample.searchCard(PROonSearchListener);
		} else {
			ToastUtil.showShort(mActivity, "不可消费！");
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_FAIL);
		}
	}

	/**
	 * M1 区写卡操作
	 */
	private void MOneWrite() {
		rfCardSample.stopSearch();
		try {
			LogUtil.e("开启写卡操作");
			if (RFCardReader.getInstance().exists()) {
				rfCardSample.searchCard(M1WRonSearchListener);
			}else{
				LogUtil.e("卡不存在，未执行写卡操作");
				if (isPBOCTradingSuccess) {
					StartServce(BuzzerTools.STATE_SUCCESS);
				} else {
					StartServce(BuzzerTools.STATE_FAIL);
				}
			}
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Create a listener to listen the result of search card. M1 区读卡监听
	 */

	private RFCardReader.OnSearchListener M1onSearchListener = new RFCardReader.OnSearchListener() {
		@Override
		public void onCrash() {
			// 失败
			onDeviceServiceCrash();
		}

		@Override
		public void onFail(int error) {
			// 失败
			if (error == 139) {
				StartServce(BuzzerTools.STATE_QINGCHONGSHUA);
			} else {
				StartServce(BuzzerTools.STATE_FAIL);
			}
			Log.e("tag", "SEARCH ERROR - " + getErrorDescription(error));
		}

		@Override
		public void onCardPass(int cardType) {
			// Choose the right card driver .
			firstFlipTime = TimerTools.getTime("开始寻卡");
			switch (cardType) {
			case S50_CARD:
				driverName = "S50";
				break;
			case S70_CARD:
				driverName = "S70";
				break;
			case CPU_CARD:
			case PRO_CARD:
			case S50_PRO_CARD: // The card of this type can use 'S50' driver
				// too.
			case S70_PRO_CARD: // The card of this type can use 'S70' driver
				// too.
				driverName = "PRO";
				break;
			default:
				// 失败
				Log.e("tag", "Search card fail, unknown card type!");
				return;
			}

			driverName = "S50";

			Log.e("tag", "rf card detected, and use " + driverName
					+ " driver to read it!");
			try {
				RFCardReader.getInstance().activate(driverName,
						M1onActiveListener);
			} catch (RequestException e) {
				e.printStackTrace();
				onDeviceServiceCrash();
			}
		}

		public String getErrorDescription(int code) {
			switch (code) {
			case ERROR_CARDNOACT:
				return "Pro card or TypeB card is not activated";
			case ERROR_CARDTIMEOUT:
				return "No response";
			case ERROR_PROTERR:
				return "The card return data does not meet the requirements of the protocal";
			case ERROR_TRANSERR:
				return "Communication error";
			}
			return "unknown error [" + code + "]";
		}
	};

	/**
	 * Create a listener to listen the result of activate card. M1区读卡监听
	 */
	private RFCardReader.OnActiveListener M1onActiveListener = new RFCardReader.OnActiveListener() {

		@Override
		public void onCrash() {
			onDeviceServiceCrash();
		}

		@Override
		public void onUnsupport(String driverName) {
			// All driver names using in this example have already support.
		}

		@Override
		public void onCardActivate(final RFDriver cardDriver) {
			byte[] serial = getLastCardSerialNo();

			/** M1 区读卡 ***/

			final MifareOneCardReader M1card = new MifareOneCardReader(
					(MifareDriver) cardDriver) {
			};

			final Map<String, String> reqMap = new HashMap<String, String>();
			M1card.startRead(8, new M1Listener() {
				@Override
				public void onSuccess(String string) {
					// TODO Auto-generated method stub
					LogUtil.e("第八：" + string);
					reqMap.put(8 + "", string);
					M1card.startRead(9, new M1Listener() {
						@Override
						public void onSuccess(String string) {
							// TODO Auto-generated method stub
							reqMap.put(9 + "", string);
							LogUtil.e("第9：" + string);
							M1card.startRead(10, new M1Listener() {
								@Override
								public void onSuccess(String string) {
									// TODO Auto-generated method stub
									reqMap.put(10 + "", string);
									LogUtil.e("第10：" + string);

									M1card.halt();
									if (string.length() == 6) {
										// 老模式 第十区标记位是六位 MMDDCB
										isDown = true;
										m1Info = CodeUtils.getM1Info(reqMap);
										String dateNow = TimerTools.getDateFormat(
												System.currentTimeMillis() + "",
												"MMdd");

										// 定额消费一餐次只能消费一次
										if ("0001".equals(m1Info
												.get(CodeUtils.M1_APPID))
												&& "01".equals(m1Info
														.get(CodeUtils.M1_STATUS))) {

											if (dateNow.equals(m1Info
													.get(CodeUtils.M1_DATE))) {

												if (rangburangchi(m1Info
														.get(CodeUtils.M1_CB))) {
													CardConsume();
												} else {
													// 不让吃
													ToastUtil.showLong(
															mActivity, "已就餐！");
													StartServce(BuzzerTools.STATE_OVERRUN);
												}
											} else {
												CardConsume();
											}
										} else {
											// 不让吃
											ToastUtil.showLong(mActivity,
													"卡类型不正确！");
											StartServce(BuzzerTools.STATE_ILLGALCARD);
										}
									} else {
										// 新模式 M1第十区 标志位 MMddHHmmxx XX 为优惠次数
										isDown = false;
										m1Info = CodeUtils.getM1InfoNew(reqMap);

										ifCanResult = APPTools.IfCan(m1Info,
												workTimeParma);

										if (ifCanResult.getIsCan()) {
											// 可以用餐
											CardConsume();
										} else {

											switch (ifCanResult.getCode()) {
											case IfCanResult.EXCESS:
												ToastUtil.showLong(mActivity,
														"超过消费次数！");
												StartServce(BuzzerTools.STATE_FAIL);
												break;
											case IfCanResult.ILLEGAL:
												ToastUtil.showLong(mActivity,
														"卡类型不正确！");
												StartServce(BuzzerTools.STATE_ILLGALCARD);
												break;
											case IfCanResult.Fild:

												ToastUtil.showLong(mActivity,
														"不可就餐！");
												StartServce(BuzzerTools.STATE_FAIL);

											default:
												break;
											}
										}
									}
								}

								/**
								 * 判断当前时间餐别
								 * 
								 * @param string
								 *            卡餐别
								 * @return
								 */
								private boolean rangburangchi(String string) {
									// TODO Auto-generated method stub
									if (workTimeParma == null) {
										return false;
									}
									String cb = workTimeParma.getMealType();
									if (string.equals(cb)) {
										return false;
									}
									return true;
								}

								@Override
								public void onFild(String s) {
									// TODO Auto-generated method stub
									// 失败
									LogUtil.e("M1区 读取错误！—8:" + s);
									ToastUtil.showLong(mActivity, "请重刷！");
									StartServce(BuzzerTools.STATE_QINGCHONGSHUA);
								}

								@Override
								public void onCrash(String s) {
									LogUtil.e("8区OnCrash:" + s);
								}
							});
						}

						@Override
						public void onFild(String s) {
							// TODO Auto-generated method stub
							LogUtil.e("M1区 读取错误！—9:" + s);
							ToastUtil.showLong(mActivity, "请重刷！");
							StartServce(BuzzerTools.STATE_QINGCHONGSHUA);
						}

						@Override
						public void onCrash(String s) {
							LogUtil.e("9区OnCrash:" + s);
						}
					});

				}

				@Override
				public void onFild(String s) {
					// TODO Auto-generated method stub
					// 失败
					LogUtil.e("M1区 读取错误！—10:" + s);
					ToastUtil.showLong(mActivity, "请重刷！");
					StartServce(BuzzerTools.STATE_QINGCHONGSHUA);
				}

				@Override
				public void onCrash(String s) {
					LogUtil.e("10区OnCrash:" + s);
				}
			});
		}

		@Override
		public void onActivateError(int code) {
			if (code == 139) {
				LogUtil.e("139错误");

				initDervicesSlience();
				ToastUtil.showLong(mActivity, "请重刷！");
				BuzzerTools.getInstance().startOKV(mActivity,
						BuzzerTools.STATE_QINGCHONGSHUA);

			} else {
				StartServce(BuzzerTools.STATE_FAIL);
				Log.e("tag", "ACTIVATE ERROR - " + getErrorDescription(code));
			}
		}

		public String getErrorDescription(int code) {
			switch (code) {
			case ERROR_TRANSERR:
				return "Communication error";
			case ERROR_PROTERR:
				return "The card return data does not meet the requirements of the protocal";
			case ERROR_CARDTIMEOUT:
				return "No response";
			}
			return "unknown error [" + code + "]";
		}
	};

	/**
	 * Create a listener to listen the result of search card. 银行卡 CPU
	 */
	private RFCardReader.OnSearchListener PROonSearchListener = new RFCardReader.OnSearchListener() {
		@Override
		public void onCrash() {
			// 失败
			onDeviceServiceCrash();
			// StartServce();
		}

		@Override
		public void onFail(int error) {
			// 失败
			if (error == 139) {
				StartServce(BuzzerTools.STATE_QINGCHONGSHUA);
			} else {
				StartServce(BuzzerTools.STATE_FAIL);
			}
			Log.e("tag", "SEARCH ERROR - " + getErrorDescription(error));
		}

		@Override
		public void onCardPass(int cardType) {
			// Choose the right card driver .
			switch (cardType) {
			case S50_CARD:
				driverName = "S50";
				break;
			case S70_CARD:
				driverName = "S70";
				break;
			case CPU_CARD:
			case PRO_CARD:
			case S50_PRO_CARD: // The card of this type can use 'S50' driver
				// too.
			case S70_PRO_CARD: // The card of this type can use 'S70' driver
				// too.
				driverName = "PRO";
				break;
			default:
				// 失败
				Log.e("tag", "Search card fail, unknown card type!");
				return;
			}

			Log.d("tag", "rf card detected, and use " + driverName
					+ " driver to read it!");
			try {
				RFCardReader.getInstance().activate(driverName,
						PROActiveListener);
			} catch (RequestException e) {
				e.printStackTrace();
				onDeviceServiceCrash();
			}
		}

		public String getErrorDescription(int code) {
			switch (code) {
			case ERROR_CARDNOACT:
				return "Pro card or TypeB card is not activated";
			case ERROR_CARDTIMEOUT:
				return "No response";
			case ERROR_PROTERR:
				return "The card return data does not meet the requirements of the protocal";
			case ERROR_TRANSERR:
				return "Communication error";
			}
			return "unknown error [" + code + "]";
		}
	};

	/**
	 * Create a listener to listen the result of activate card. 银行卡 CPU
	 */
	private RFCardReader.OnActiveListener PROActiveListener = new RFCardReader.OnActiveListener() {

		@Override
		public void onCrash() {
			onDeviceServiceCrash();
		}

		@Override
		public void onUnsupport(String driverName) {
			// All driver names using in this example have already support.
		}

		@Override
		public void onCardActivate(final RFDriver cardDriver) {
			byte[] serial = getLastCardSerialNo();

			if (cardDriver instanceof RFCpuCardDriver) {
				// It is assumed to be UP card
				final UPCardReader reader = new UPCardReader(mActivity,
						(RFCpuCardDriver) cardDriver) {
					@Override
					protected void showErrorMessage(String msg) {
						StartServce(BuzzerTools.STATE_FAIL);
						rfCardSample.displayRFCardInfo(msg);
					}

					@Override
					protected void onServiceCrash() {
						rfCardSample.onDeviceServiceCrash();
					}

					@Override
					protected void onDataRead(String pan, String track2,
							String track3, String expiredDate, byte[] serialNo,
							String readTime) {
						// StringBuilder infoBuilder = new StringBuilder();
						// infoBuilder.append("PAN [");
						// infoBuilder.append(pan);
						// infoBuilder.append("]\n");
						//
						// infoBuilder.append("TRACK2 [");
						// infoBuilder.append(track2);
						// infoBuilder.append("]\n");
						//
						// infoBuilder.append("TRACK3 [");
						// infoBuilder.append(track3);
						// infoBuilder.append("]\n");
						//
						// infoBuilder.append("EXPIRED DATE [");
						// infoBuilder.append(expiredDate);
						// infoBuilder.append("]\n");
						// rfCardSample.displayRFCardInfo(infoBuilder.toString());
					}
				};

				String maxMoney = SPUtils.getString(mActivity,
						SPUtils.MaxMoney, "100");

				String canbie = m1Info.get(CodeUtils.M1_CARDTYPE);
				if (mQutaMap.get(canbie)==null) {
					LogUtil.e("mQutaMap.get(canbie)为空");
				}else{
					Double cbRuta = mQutaMap.get(canbie);
					shijimoney = Arith.round(Arith.mul(dQuotaMoney, cbRuta), 2);
				}

				if (APPTools.isMoney(maxMoney)) {
					double dmaxMoney = (int) ((Double.parseDouble(maxMoney)) * 100);

					if (0 == dmaxMoney) {
						dmaxMoney = 100.0;
					}

					if (shijimoney > dmaxMoney) {
						ToastUtil.showLong(mActivity, "超过消费限额！");
						StartServce(BuzzerTools.STATE_OVERRUN);
					} else {

						int sjmoeny = ((int) Arith.round(
								Arith.mul(shijimoney, 100), 0));
						reader.startRead(sjmoeny, new PBOClistener() {
							@Override
							public void onFild(int tag, int parseInt) {
								// TODO Auto-generated method stub
								if (tag == PBOClistener.BLANCE_FILD) {
									// 余额不足 消费失败
									Toast.makeText(getApplicationContext(),
											"余额不足", 0).show();
									balance = APPTools.moneyFenToYuan(parseInt
											+ "");
									StartServce(BuzzerTools.STATE_NOBALANCE);
								} else {
									StartServce(BuzzerTools.STATE_ILLGALCARD);
								}
							}

							@Override
							public void onSuccess(String resData,
									String cardNumber) {
							}

							@Override
							public void onSuccess(String resData,
									String cardNumber, int blace) {
								// TODO Auto-generated method stub
								// 流水号自增 餐次统计自增
								SPUtils.addSerialData(mActivity);
								SPUtils.addConsume_Times(mActivity);
								tv_consume_times.setText(SPUtils
										.getConsumeTimes(mActivity));

								mCardNumber = cardNumber;
								mCashReqData = resData;
								afterBalance = blace;
								reader.halt();
								if (showConsumeInfo(shijimoney + "",
										mCashReqData, mCardNumber)) {
									LogUtil.e("消费完成！！！！！！！");
									MOneWrite();
								} else {
									LogUtil.e("停止服务！！！！！！！");
									StartServce(BuzzerTools.STOP_SERVICE);
								}
							}

						});
					}
				} else {
					ToastUtil.showLong(mActivity, "请设置正确消费金额！");
					StartServce(BuzzerTools.STATE_FAIL);
				}

			} else {
				// It's never gonna happen.
				Log.e("tag", "都没有");
			}
		}

		@Override
		public void onActivateError(int code) {
			StartServce(BuzzerTools.STATE_FAIL);
			Log.e("tag", "PRO ACTIVATE ERROR - " + getErrorDescription(code));
		}

		public String getErrorDescription(int code) {
			switch (code) {
			case ERROR_TRANSERR:
				return "Communication error";
			case ERROR_PROTERR:
				return "The card return data does not meet the requirements of the protocal";
			case ERROR_CARDTIMEOUT:
				return "No response";
			}
			return "unknown error [" + code + "]";
		}
	};

	/**
	 * Create a listener to listen the result of search card.
	 */
	private RFCardReader.OnSearchListener M1WRonSearchListener = new RFCardReader.OnSearchListener() {
		@Override
		public void onCrash() {
			// 失败
			if (isPBOCTradingSuccess) {
				StartServce(BuzzerTools.STATE_SUCCESS);
			}
			onDeviceServiceCrash();
		}

		@Override
		public void onFail(int error) {
			// 失败
			if (isPBOCTradingSuccess) {
				StartServce(BuzzerTools.STATE_SUCCESS);
			} else {
				if (error == 139) {
					StartServce(BuzzerTools.STATE_QINGCHONGSHUA);
				} else {
					StartServce(BuzzerTools.STATE_FAIL);
				}
				Log.e("tag", "M1W SEARCH ERROR - " + getErrorDescription(error));
			}
		}

		@Override
		public void onCardPass(int cardType) {
			// Choose the right card driver .
			switch (cardType) {
			case S50_CARD:
				driverName = "S50";
				break;
			case S70_CARD:
				driverName = "S70";
				break;
			case CPU_CARD:
			case PRO_CARD:
			case S50_PRO_CARD: // The card of this type can use 'S50' driver
				// too.
			case S70_PRO_CARD: // The card of this type can use 'S70' driver
				// too.
				driverName = "PRO";
				break;
			default:
				// 失败
				Log.e("tag", "Search card fail, unknown card type!");
				return;
			}

			driverName = "S50";

			Log.i("tag", "rf card detected, and use " + driverName
					+ " driver to read it!");
			try {
				RFCardReader.getInstance().activate(driverName,
						M1WRonActiveListener);
			} catch (RequestException e) {
				if (isPBOCTradingSuccess) {
					StartServce(BuzzerTools.STATE_SUCCESS);
				}
				e.printStackTrace();
				onDeviceServiceCrash();
			}
		}

		public String getErrorDescription(int code) {
			switch (code) {
			case ERROR_CARDNOACT:
				return "Pro card or TypeB card is not activated";
			case ERROR_CARDTIMEOUT:
				return "No response";
			case ERROR_PROTERR:
				return "The card return data does not meet the requirements of the protocal";
			case ERROR_TRANSERR:
				return "Communication error";
			}
			return "unknown error [" + code + "]";
		}
	};

	/**
	 * Create a listener to listen the result of activate card.
	 */
	private RFCardReader.OnActiveListener M1WRonActiveListener = new RFCardReader.OnActiveListener() {

		@Override
		public void onCrash() {
			if (isPBOCTradingSuccess) {
				StartServce(BuzzerTools.STATE_SUCCESS);
			}
			onDeviceServiceCrash();
		}

		@Override
		public void onUnsupport(String driverName) {
			// All driver names using in this example have already support.
			if (isPBOCTradingSuccess) {
				StartServce(BuzzerTools.STATE_SUCCESS);
			}
		}

		@Override
		public void onCardActivate(final RFDriver cardDriver) {
			byte[] serial = getLastCardSerialNo();

			Log.i("tag", "card activated!" + BytesUtil.bytesToInt(serial));
			/** M1 区读卡 ***/
			final MifareOneCardReader M1card = new MifareOneCardReader(
					(MifareDriver) cardDriver) {
			};

			String writeStr = "";
			if (isDown) {
				writeStr = APPTools.getM1WriteDataNew(null);
			} else {
				writeStr = APPTools.getM1WriteDataNew(ifCanResult);
			}
			//			LogUtil.e("TIME","写入数据："+writeStr);
			M1card.startWrite(10, writeStr, new M1Listener() {

				@Override
				public void onSuccess(String string) {
					LogUtil.e("写完成功！！！！！");
					// showConsumeInfo(shijimoney + "", mCashReqData,
					// mCardNumber);
					StartServce(BuzzerTools.STATE_SUCCESS);
				}

				@Override
				public void onFild(String s) {
					if (isPBOCTradingSuccess) {
						StartServce(BuzzerTools.STATE_SUCCESS);
					} else
						StartServce(BuzzerTools.STATE_FAIL);
				}

				@Override
				public void onCrash(String s) {
					if (isPBOCTradingSuccess) {
						StartServce(BuzzerTools.STATE_SUCCESS);
					} else
						StartServce(BuzzerTools.STATE_FAIL);
				}
			});
		}

		@Override
		public void onActivateError(int code) {
			if (isPBOCTradingSuccess) {
				StartServce(BuzzerTools.STATE_SUCCESS);
			} else {
				StartServce(BuzzerTools.STATE_FAIL);
				Log.e("tag", "ACTIVATE ERROR - " + getErrorDescription(code));
			}
		}

		public String getErrorDescription(int code) {
			switch (code) {
			case ERROR_TRANSERR:
				return "Communication error";
			case ERROR_PROTERR:
				return "The card return data does not meet the requirements of the protocal";
			case ERROR_CARDTIMEOUT:
				return "No response";
			}
			return "unknown error [" + code + "]";
		}
	};


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
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.tv_consume_time:
			clearTime();
			break;

		default:
			break;
		}
		return true;
	}

	private void clearTime() {
		CustomDialog.Builder builder = new CustomDialog.Builder(this); 
		builder.setMessage("是否清理流水计数？");  
		builder.setTitle("提示");  
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();  
				SPUtils.put(mActivity, SPUtils.CONSUME_TIMES, 0);
				tv_consume_times.setText(SPUtils.getConsumeTimes(mActivity));
			}  
		});  

		builder.setNegativeButton("取消",  
				new android.content.DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {  
				dialog.dismiss();  
			}  
		});  

		builder.create().show();
	}
}
