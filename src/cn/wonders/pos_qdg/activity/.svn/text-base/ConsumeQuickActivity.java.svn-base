package cn.wonders.pos_qdg.activity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.R.color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import cn.wonders.pos_qdg.R;
import cn.wonders.pos_qdg.MOne.M1Listener;
import cn.wonders.pos_qdg.MOne.MifareOneCardReader;
import cn.wonders.pos_qdg.app.BaseActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.WorkTimeParma;
import cn.wonders.pos_qdg.db.TradingAbnormalDao;
import cn.wonders.pos_qdg.db.TradingDao;
import cn.wonders.pos_qdg.db.WorkTimeParmaDao;
import cn.wonders.pos_qdg.util.APPTools;
import cn.wonders.pos_qdg.util.Arith;
import cn.wonders.pos_qdg.util.BuzzerTools;
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
 * 快捷早餐消费模式界面
 * 
 * @author Luckydog
 * 
 */
public class ConsumeQuickActivity extends BaseActivity implements
		OnClickListener, OnLongClickListener {
	private static final int NOT_ENOUGH = 2;
	/**
	 * 食堂当前营业规则
	 */
	private WorkTimeParma mWorkTimebean;
	private Activity mActivity;
	private TextView tv_id, tv_consume, tv_name, tv_balance, _BCTV_Tv_Numb;
	private TextView tv_total, tv_details;
	private TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9,
			tv_10, tv_11, tv_12;
	private Button btn_affirm;
	private String mQuotaMoneyStr;;
	private RFCardReaderSample rfCardSample;
	private TradingDao liushuiDao;
	public static String QMoney = "000000000000";
	// 消费金额
	private double dQuotaMoney;
	/** M1 区信息 */
	private Map<String, String> m1Info;
	private String mCardNumber;
	private String mCashReqData;
	private String mCardLiushui;
	private String mMoneyOnce;
	private double mMoneyTotal = 0;
	InputMethodManager imm;
	private StringBuilder sb;
	/**
	 * 消费余额
	 */
	private String balance;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {
			case 1:
				// imm.showSoftInput(et_money_subitem, 0);
				initUI();
				break;
			case NOT_ENOUGH:
				ToastUtil.showLong(mActivity, "已超过消费总额");
				break;
			case BuzzerTools.STOP_SERVICE:
				unbindDeviceService();
				showStopService();
				break;
			default:
				break;
			}
		};
	};
	private TradingAbnormalDao abnormalDao;
	private int numb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_breakfast);
		this.mActivity = ConsumeQuickActivity.this;
		abnormalDao = TradingAbnormalDao.getInstance(mActivity);
		initView();
		initData();
		UpUtils up=new UpUtils(mActivity, handler);
		up.checkTradingLiushui();
	}

	private void initView() {
		tv_id = (TextView) findViewById(R.id.tv_id);
		tv_consume = (TextView) findViewById(R.id.tv_consume);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_balance = (TextView) findViewById(R.id.tv_balance);
		tv_total = (TextView) findViewById(R.id.tv_qca_money);
		tv_details = (TextView) findViewById(R.id.tv_details);
		btn_affirm = (Button) findViewById(R.id.btn_affirm);
		findViewById(R.id.btn_affirm).setOnClickListener(this);
		// 快捷消费按钮
		findViewById(R.id.btn_package1).setOnClickListener(this);
		findViewById(R.id.btn_package2).setOnClickListener(this);
		findViewById(R.id.btn_package3).setOnClickListener(this);
		findViewById(R.id.btn_package4).setOnClickListener(this);
		findViewById(R.id.btn_package5).setOnClickListener(this);
		findViewById(R.id.btn_package6).setOnClickListener(this);
		findViewById(R.id.btn_package7).setOnClickListener(this);
		findViewById(R.id.btn_package8).setOnClickListener(this);
		findViewById(R.id.btn_package9).setOnClickListener(this);
		findViewById(R.id.btn_package10).setOnClickListener(this);
		findViewById(R.id.btn_package11).setOnClickListener(this);
		findViewById(R.id.btn_package12).setOnClickListener(this);
		findViewById(R.id.btn_package1).setOnLongClickListener(this);
		findViewById(R.id.btn_package2).setOnLongClickListener(this);
		findViewById(R.id.btn_package3).setOnLongClickListener(this);
		findViewById(R.id.btn_package4).setOnLongClickListener(this);
		findViewById(R.id.btn_package5).setOnLongClickListener(this);
		findViewById(R.id.btn_package6).setOnLongClickListener(this);
		findViewById(R.id.btn_package7).setOnLongClickListener(this);
		findViewById(R.id.btn_package8).setOnLongClickListener(this);
		findViewById(R.id.btn_package9).setOnLongClickListener(this);
		findViewById(R.id.btn_package10).setOnLongClickListener(this);
		findViewById(R.id.btn_package11).setOnLongClickListener(this);
		findViewById(R.id.btn_package12).setOnLongClickListener(this);
		tv_1 = (TextView) findViewById(R.id.tv_1);
		tv_2 = (TextView) findViewById(R.id.tv_2);
		tv_3 = (TextView) findViewById(R.id.tv_3);
		tv_4 = (TextView) findViewById(R.id.tv_4);
		tv_5 = (TextView) findViewById(R.id.tv_5);
		tv_6 = (TextView) findViewById(R.id.tv_6);
		tv_7 = (TextView) findViewById(R.id.tv_7);
		tv_8 = (TextView) findViewById(R.id.tv_8);
		tv_9 = (TextView) findViewById(R.id.tv_9);
		tv_10 = (TextView) findViewById(R.id.tv_10);
		tv_11 = (TextView) findViewById(R.id.tv_11);
		tv_12 = (TextView) findViewById(R.id.tv_12);
		TextView _BCTV_Title = (TextView) findViewById(R.id.activity_title_tv_name);
		_BCTV_Title.setText("快捷早餐消费模式");
		_BCTV_Tv_Numb = (TextView) findViewById(R.id.tv_numb);
		numb=0;
		_BCTV_Tv_Numb.setText(numb+"");
		findViewById(R.id.activity_title_btn_back).setOnClickListener(this);
		sb = new StringBuilder("");
		initMoney();
		initUI();
	}

	private void initMoney() {
		tv_1.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_1_MONEY,
				"0.1"));
		tv_2.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_2_MONEY,
				"0.2"));
		tv_3.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_3_MONEY,
				"0.3"));
		tv_4.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_4_MONEY,
				"0.4"));
		tv_5.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_5_MONEY,
				"0.5"));
		tv_6.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_6_MONEY,
				"0.6"));
		tv_7.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_7_MONEY,
				"0.7"));
		tv_8.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_8_MONEY,
				"0.8"));
		tv_9.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_9_MONEY,
				"0.9"));
		tv_10.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_10_MONEY,
				"1"));
		tv_11.setText(SPUtils.getString(mActivity, SPUtils.BREAKFAST_11_MONEY,
				"2"));
	}

	private void initData() {
		liushuiDao = TradingDao.getInstance(mActivity);
		rfCardSample = new RFCardReaderSample(this) {

			public void onDeviceServiceCrash() {
				// Handle in 'RFCardActivity'
				ConsumeQuickActivity.this.onDeviceServiceCrash();
			}

			@Override
			public void displayRFCardInfo(String cardInfo) {
				// Handle in 'RFCardActivity'
				LogUtil.i("卡信息123：" + cardInfo);
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_title_btn_back:
			myBack();
			break;
		case R.id.btn_affirm:
			mQuotaMoneyStr = tv_total.getText().toString();
			String[] arrays = mQuotaMoneyStr.split(" ");
			mQuotaMoneyStr = arrays[0];
			LogUtil.e("消费金额为：" + mQuotaMoneyStr);
			if (APPTools.isMoney(mQuotaMoneyStr)) {
				dQuotaMoney = Double.parseDouble(mQuotaMoneyStr);
				if (dQuotaMoney < 0.01) {
					ToastUtil.showLong(mActivity, "请输入消费金额！");
				} else {
					rfCardSample.searchCard(M1SearchDriversListener);
					btnChange(false);
				}
			} else {
				ToastUtil.showLong(mActivity, "请输入消费金额！");
			}
			break;
		case R.id.btn_package1:
			addMoney(tv_1);
			break;
		case R.id.btn_package2:
			addMoney(tv_2);
			break;
		case R.id.btn_package3:
			addMoney(tv_3);
			break;
		case R.id.btn_package4:
			addMoney(tv_4);
			break;
		case R.id.btn_package5:
			addMoney(tv_5);
			break;
		case R.id.btn_package6:
			addMoney(tv_6);
			break;
		case R.id.btn_package7:
			addMoney(tv_7);
			break;
		case R.id.btn_package8:
			addMoney(tv_8);
			break;
		case R.id.btn_package9:
			addMoney(tv_9);
			break;
		case R.id.btn_package10:
			addMoney(tv_10);
			break;
		case R.id.btn_package11:
			addMoney(tv_11);
			break;
		case R.id.btn_package12:
			initUI();
			btnChange(true);
			rfCardSample.stopSearch();
			break;
		default:
			break;
		}
	}

	private void btnChange(boolean b) {
		if (b) {
			btn_affirm.setBackgroundColor(Color.parseColor("#00A9A6"));
			btn_affirm.setClickable(b);
		} else {
			btn_affirm.setBackgroundColor(Color.parseColor("#FF0000"));
			btn_affirm.setClickable(b);
		}
	}

	/**
	 * 添加金额显示，叠加总金额
	 * 
	 * @param tv
	 */
	private void addMoney(TextView tv) {
		mMoneyOnce = tv.getText().toString();
		if (sb.toString().equals("")) {
			sb.append(mMoneyOnce);
		} else {
			sb.append("+" + mMoneyOnce);
		}
		mMoneyTotal = Arith.add(mMoneyOnce, Double.toString(mMoneyTotal));
		tv_total.setText(mMoneyTotal + " 元");
		tv_details.setText(sb.toString());
	}

	@Override
	public boolean onLongClick(View v) {
		switch (v.getId()) {
		case R.id.btn_package1:
			deleteMoney(tv_1);
			break;
		case R.id.btn_package2:
			deleteMoney(tv_2);
			break;
		case R.id.btn_package3:
			deleteMoney(tv_3);
			break;
		case R.id.btn_package4:
			deleteMoney(tv_4);
			break;
		case R.id.btn_package5:
			deleteMoney(tv_5);
			break;
		case R.id.btn_package6:
			deleteMoney(tv_6);
			break;
		case R.id.btn_package7:
			deleteMoney(tv_7);
			break;
		case R.id.btn_package8:
			deleteMoney(tv_8);
			break;
		case R.id.btn_package9:
			deleteMoney(tv_9);
			break;
		case R.id.btn_package10:
			deleteMoney(tv_10);
			break;
		case R.id.btn_package11:
			deleteMoney(tv_11);
			break;
		case R.id.btn_package12:
			startActivity(new Intent(mActivity, BreakfastSettingActivity.class));
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 减去金额
	 * 
	 * @param tv
	 */
	private void deleteMoney(TextView tv) {
		mMoneyOnce = tv.getText().toString();
		if (Arith.compareTo(Double.toString(mMoneyTotal), mMoneyOnce) != -1) {
			sb.append("-" + mMoneyOnce);
			mMoneyTotal = Arith.sub(Double.toString(mMoneyTotal), mMoneyOnce);
			tv_total.setText(mMoneyTotal + " 元");
			tv_details.setText(sb.toString());
		} else {
			handler.sendEmptyMessage(NOT_ENOUGH);
		}
	}

	private void myBack() {
		// TODO Auto-generated method stub
		ScreenManager screenManager = CloudposApplication.getInstance()
				.getScreenManager();
		screenManager.popActivity(mActivity);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			myBack();
		}
		if (keyCode == KeyEvent.KEYCODE_0) {
			addMoney(tv_11);
		}
		if (keyCode == KeyEvent.KEYCODE_1) {
			addMoney(tv_1);
		}
		if (keyCode == KeyEvent.KEYCODE_2) {
			addMoney(tv_2);
		}
		if (keyCode == KeyEvent.KEYCODE_3) {
			addMoney(tv_3);
		}
		if (keyCode == KeyEvent.KEYCODE_4) {
			addMoney(tv_4);
		}
		if (keyCode == KeyEvent.KEYCODE_5) {
			addMoney(tv_5);
		}
		if (keyCode == KeyEvent.KEYCODE_6) {
			addMoney(tv_6);
		}
		if (keyCode == KeyEvent.KEYCODE_7) {
			addMoney(tv_7);
		}
		if (keyCode == KeyEvent.KEYCODE_8) {
			addMoney(tv_8);
		}
		if (keyCode == KeyEvent.KEYCODE_9) {
			addMoney(tv_9);
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 设备服务崩溃
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
			String cashReqData, String cardNumber, String tranList) {
		LogUtil.e("流水号：" + tranList);
		TradingBean mLiushuiBean = APPTools.Data2Liushui(cashReqData);
		long currentTimeMillis = System.currentTimeMillis();
		if (mLiushuiBean != null) {
			mLiushuiBean.setWorkerID(workerid);
			mLiushuiBean.setCardNumber(cardNumber);
			mLiushuiBean.setTranMoney(money);
			mLiushuiBean.setTranList(tranList);
			mLiushuiBean.setTranTime(currentTimeMillis + "");
			mLiushuiBean.setStyle("0");
			// mLiushuiBean.setSendUPTime(workerID);
			mLiushuiBean.setSyncTime("0");
			mLiushuiBean.setIsUpdate(0);
			Integer addLiushui = liushuiDao.addLiushui(mLiushuiBean);
			if (addLiushui == 1) {
				return mLiushuiBean;
			} else {
				mLiushuiBean
						.setAbnormalCode(TradingBean.abnormal_save_trading_error);
				abnormalDao.saveAbnormalTrading(new TradingAbnormalBean(mLiushuiBean));
			}
		} else {
			mLiushuiBean = new TradingBean();
			mLiushuiBean.setWorkerID(workerid);
			mLiushuiBean.setCardNumber(cardNumber);
			mLiushuiBean.setTranMoney(money);
			mLiushuiBean.setTranList(tranList);
			mLiushuiBean.setTranTime(currentTimeMillis + "");
			mLiushuiBean.setStyle("0");
			// mLiushuiBean.setSendUPTime(workerID);
			mLiushuiBean.setSyncTime("0");
			mLiushuiBean.setIsUpdate(0);
			mLiushuiBean
					.setAbnormalCode(TradingBean.abnormal_parsing_trading_error);
			abnormalDao.saveAbnormalTrading(new TradingAbnormalBean(mLiushuiBean));
		}
		return null;

	}

	/**
	 * 显示卡消费信息
	 * 
	 * @param mQuotaMoneyStr
	 *            消费金额
	 * @param eC_BALANCE2
	 *            消费后余额
	 * @param cashReqData
	 *            电子现金刷卡消费返回参数
	 * @param cardNumber
	 *            卡号
	 */
	private boolean showConsumeInfo(String mQuotaMoneyStr, String cashReqData,
			String cardNumber, String tranList, int balance) {
		// TODO Auto-generated method stub

		TradingBean liushui = SaveTranLiushui(m1Info.get(CodeUtils.M1_STAFFID),
				mQuotaMoneyStr, cashReqData, cardNumber, tranList);

		if (liushui == null) {
			// 停止服务
			LogUtil.e("流水入库异常，停止服务！");
			unbindDeviceService();
			showStopService();
			return false;
		} else {
			double ec_Blance_d = Arith.div(balance, 100, 2);
			LogUtil.e("消费流水信息：" + liushui.toString());
			LogUtil.e("消费后余额：" + ec_Blance_d + "元");
			LogUtil.e("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			tv_id.setText(m1Info.get(CodeUtils.M1_STAFFID));
			tv_consume.setText(mQuotaMoneyStr + "元");
			tv_name.setText(m1Info.get(CodeUtils.M1_STAFFNAME));
			tv_balance.setText(ec_Blance_d + "元");
			// et_money_subitem.setText("");
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						boolean isWhile = true;
						while (isWhile) {
							try {
								if (!RFCardReader.getInstance().exists()) {
									isWhile = false;
									handler.sendEmptyMessageDelayed(1, 500);
								}
							} catch (RequestException e) {
								// TODO Auto-generated catch block
								LogUtil.e("QuotaConsumeActivity 判断是否有卡存在异常！");
								isWhile = false;
								// 刷卡等待时间
								handler.sendEmptyMessageDelayed(1, 1000);
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		return true;
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

	/*** 卡消费 ***/
	/*
	 * 重启消费服务
	 */
	private void stopSearch(int state) {
		switch (state) {
		case BuzzerTools.STATE_FAIL:
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_FAIL);
			btnChange(true);
			break;
		case BuzzerTools.STATE_ILLGALCARD:
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_ILLGALCARD);
			btnChange(true);
			break;
		case BuzzerTools.STATE_ILLGALTIME:
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_ILLGALTIME);
			break;
		case BuzzerTools.STATE_NOBALANCE:
			if (balance != null) {
				tv_balance.setText(balance + "元");
				balance = null;
			}
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_NOBALANCE);
			handler.sendEmptyMessageDelayed(1, 1000);
			btnChange(true);
			break;
		case BuzzerTools.STATE_OVERRUN:
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_OVERRUN);
			btnChange(true);
			break;
		case BuzzerTools.STATE_SUCCESS:
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_SUCCESS);
			btnChange(true);
			break;
		case BuzzerTools.STATE_QINGCHONGSHUA:
			BuzzerTools.getInstance().startOKV(mActivity,
					BuzzerTools.STATE_QINGCHONGSHUA);
			btnChange(true);
			break;
		default:
			break;
		}
		rfCardSample.stopSearch();
	}

	/**
	 * 卡操作消费流程
	 * 
	 * @param cardDriver
	 */
	private void CardConsume() {

		rfCardSample.stopSearch();
		rfCardSample.searchCard(PROonSearchListener);
	}

	/**
	 * 初始化显示参数
	 */
	private void initUI() {
		// TODO Auto-generated method stub
		tv_id.setText("");
		tv_consume.setText("元");
		tv_name.setText("");
		tv_balance.setText("元");

		tv_total.setText("0.0元");
		tv_details.setText("");
		sb.delete(0, sb.length());
		mMoneyTotal = 0;
	}

	/**
	 * Create a listener to listen the result of search card. M1 区读卡监听
	 */
	private String driverName;
	private RFCardReader.OnSearchListener M1SearchDriversListener = new RFCardReader.OnSearchListener() {
		@Override
		public void onCrash() {
			// 失败
			onDeviceServiceCrash();
			// StartServce();
		}

		@Override
		public void onFail(int error) {
			// 失败
			stopSearch(BuzzerTools.STATE_FAIL);
			Log.e("tag", "SEARCH ERROR - " + getErrorDescription(error));
		}

		@Override
		public void onCardPass(int cardType) {
			// 选择正确的卡片设备
			switch (cardType) {
			case S50_CARD:
				driverName = "S50";
				break;
			case S70_CARD:
				driverName = "S70";
				break;
			case CPU_CARD:
			case PRO_CARD:
			case S50_PRO_CARD: // 这种类型的卡片也可以使用S50的驱动程序。
			case S70_PRO_CARD: // 这种类型的卡片也可以使用S70的驱动程序。
				driverName = "PRO";
				break;
			default:
				// 失败
				LogUtil.e("搜索卡失败,未知的卡片类型!");
				return;
			}

			driverName = "S50";

			LogUtil.d("射频卡检测,并使用" + driverName + "司机阅读它!");

			try {
				RFCardReader.getInstance().activate(driverName,
						M1ReadCardListener);
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
	private RFCardReader.OnActiveListener M1ReadCardListener = new RFCardReader.OnActiveListener() {

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

			Log.e("tag", "card activated!" + BytesUtil.bytesToInt(serial));
			/** M1 区读卡 ***/
			final MifareOneCardReader M1card = new MifareOneCardReader(
					(MifareDriver) cardDriver) {
			};

			final Map<String, String> reqMap = new HashMap<String, String>();
			M1card.startRead(8, new M1Listener() {
				@Override
				public void onSuccess(String string) {
					// TODO Auto-generated method stub
					LogUtil.d("第八：" + string);
					reqMap.put(8 + "", string);
					M1card.startRead(9, new M1Listener() {
						@Override
						public void onSuccess(String string) {
							// TODO Auto-generated method stub
							reqMap.put(9 + "", string);
							LogUtil.d("第9：" + string);
							M1card.startRead(10, new M1Listener() {

								@Override
								public void onSuccess(String string) {
									// TODO Auto-generated method stub
									reqMap.put(10 + "", string);
									LogUtil.d("第10：" + string);
									m1Info = CodeUtils.getM1Info(reqMap);
									M1card.halt();
									String dateNow = TimerTools.getDateFormat(
											System.currentTimeMillis() + "",
											"MMdd");
									if ("0001".equals(m1Info
											.get(CodeUtils.M1_APPID))) {
										CardConsume();
									} else {
										// 不让吃
										ToastUtil
												.showLong(mActivity, "卡类型不正确！");
										stopSearch(BuzzerTools.STATE_ILLGALCARD);
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
									WorkTimeParmaDao workTimeParmaDao = new WorkTimeParmaDao(
											mActivity);
									mWorkTimebean = workTimeParmaDao.getCB();
									if (mWorkTimebean == null) {
										return false;
									}
									String cb = mWorkTimebean.getMealType();
									if (string.equals(cb)) {
										return false;
									}
									return true;
								}

								@Override
								public void onFild(String s) {
									// TODO Auto-generated method stub
									// 失败
									LogUtil.e("M1区 读取错误！");
									ToastUtil.showLong(mActivity, "请重刷！");
									stopSearch(BuzzerTools.STATE_QINGCHONGSHUA);
								}

								@Override
								public void onCrash(String s) {
									// TODO Auto-generated method stub
								}
							});
						}

						@Override
						public void onFild(String s) {
							// TODO Auto-generated method stub
							LogUtil.e("M1区 读取错误！");
							ToastUtil.showLong(mActivity, "请重刷！");
							stopSearch(BuzzerTools.STATE_QINGCHONGSHUA);
						}

						@Override
						public void onCrash(String s) {
							// TODO Auto-generated method stub
						}
					});

				}

				@Override
				public void onFild(String s) {
					// TODO Auto-generated method stub
					// 失败
					LogUtil.e("M1区 读取错误！");
					ToastUtil.showLong(mActivity, "请重刷！");
					stopSearch(BuzzerTools.STATE_QINGCHONGSHUA);
				}

				@Override
				public void onCrash(String s) {
					// TODO Auto-generated method stub
				}
			});
		}

		@Override
		public void onActivateError(int code) {
			stopSearch(BuzzerTools.STATE_FAIL);
			Log.e("tag", "ACTIVATE ERROR - " + getErrorDescription(code));
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
			stopSearch(BuzzerTools.STATE_FAIL);
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

			LogUtil.e("射频卡检测,并使用" + driverName + "司机阅读它!");
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

			Log.e("tag", "card activated!" + BytesUtil.bytesToInt(serial));
			if (cardDriver instanceof RFCpuCardDriver) {
				// It is assumed to be UP card
				Log.e("tag", "if里面的");
				final UPCardReader reader = new UPCardReader(mActivity,
						(RFCpuCardDriver) cardDriver) {
					@Override
					protected void showErrorMessage(String msg) {
						Log.e("tag", "showErrorMessage里面的" + msg);
						stopSearch(BuzzerTools.STATE_FAIL);
						rfCardSample.displayRFCardInfo(msg);
					}

					@Override
					protected void onServiceCrash() {
						Log.e("tag", "onServiceCrash里面的");
						rfCardSample.onDeviceServiceCrash();
					}

					@Override
					protected void onDataRead(String pan, String track2,
							String track3, String expiredDate, byte[] serialNo,
							String readTime) {
						Log.e("tag", "onDataRead里面的");
						StringBuilder infoBuilder = new StringBuilder();
						infoBuilder.append("PAN [");
						infoBuilder.append(pan);
						infoBuilder.append("]\n");

						infoBuilder.append("TRACK2 [");
						infoBuilder.append(track2);
						infoBuilder.append("]\n");

						infoBuilder.append("TRACK3 [");
						infoBuilder.append(track3);
						infoBuilder.append("]\n");

						infoBuilder.append("EXPIRED DATE [");
						infoBuilder.append(expiredDate);
						infoBuilder.append("]\n");
						rfCardSample.displayRFCardInfo(infoBuilder.toString());
					}
				};
				String maxMoney = SPUtils.getString(mActivity,
						SPUtils.MaxMoney, "100");

				if (APPTools.isNumeric(mQuotaMoneyStr)
						&& APPTools.isNumeric(maxMoney)) {
					// 得到消费金额
					final int dQuotaMoneyInt = (int) Arith.round(
							Arith.mul(Double.parseDouble(mQuotaMoneyStr), 100),
							0);
					int dMaxMoneyStr = (int) Arith.round(
							Arith.mul(Double.parseDouble(maxMoney), 100), 0);
					if (Arith.compareTo(dQuotaMoneyInt, dMaxMoneyStr) > 0) {
						ToastUtil.showLong(mActivity, "超过消费限额！");
						reader.halt();
						stopSearch(BuzzerTools.STATE_OVERRUN);
					} else {
						reader.startRead(dQuotaMoneyInt, new PBOClistener() {
							@Override
							public void onFild(int tag, int parseInt) {
								// TODO Auto-generated method stub
								if (tag == PBOClistener.BLANCE_FILD) {
									// 余额不足失败
									balance = APPTools.moneyFenToYuan(parseInt
											+ "");
									reader.halt();
									stopSearch(BuzzerTools.STATE_NOBALANCE);
									ToastUtil.showLong(mActivity, "余额不足！");
								} else {
									reader.halt();
									stopSearch(BuzzerTools.STATE_ILLGALCARD);
								}
							}

							@Override
							public void onSuccess(String CashReqData,
									String cardNumber) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(String resData,
									String cardNumber, int blace) {
								LogUtil.e("消费完成！！！！！！！");
								numb++;
								_BCTV_Tv_Numb.setText(numb+"");
								mCardNumber = cardNumber;
								mCashReqData = resData;

								reader.halt();
								SPUtils.addSerialData(mActivity);
								mCardLiushui = CodeUtils.leftZeroFill(SPUtils
										.get(mActivity, SPUtils.serialData, 0)
										.toString(), 6);
								// 将消费金额转为显示金额
								double MoneyConsume = Arith.div(dQuotaMoneyInt,
										100, 2);
								LogUtil.e("流水号：" + mCardLiushui + " \n实际消费金额="
										+ MoneyConsume);
								
								if(showConsumeInfo(MoneyConsume + "",
										mCashReqData, mCardNumber,
										mCardLiushui, blace)){
									
									stopSearch(BuzzerTools.STATE_SUCCESS);
									ToastUtil.showLong(mActivity, "消费成功！");
								}else{
									stopSearch(BuzzerTools.STATE_FAIL);
								}


							}

						});
					}
				} else {
					ToastUtil.showLong(mActivity, "请设置正确消费金额！");
					stopSearch(BuzzerTools.STATE_FAIL);
				}

			} else {
				// It's never gonna happen.
				Log.e("tag", "都没有");
			}
		}

		@Override
		public void onActivateError(int code) {
			stopSearch(BuzzerTools.STATE_FAIL);
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		rfCardSample.stopSearch();
	}

	@Override
	protected void onResume() {
		super.onResume();

		bindDeviceService();

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
}
