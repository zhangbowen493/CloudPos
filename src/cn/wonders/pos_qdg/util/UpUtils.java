package cn.wonders.pos_qdg.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import cn.wonders.pos_qdg.activity.MainActivity;
import cn.wonders.pos_qdg.activity.ManageActivity;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.TradingHistoryBean;
import cn.wonders.pos_qdg.bean.UpLiushuiToSBean;
import cn.wonders.pos_qdg.db.TradingAbnormalDao;
import cn.wonders.pos_qdg.db.TradingDao;
import cn.wonders.pos_qdg.db.TradingHistoryDao;
import cn.wonders.pos_qdg.net.CPHttpClientUtil;
import cn.wonders.pos_qdg.net.JSONParsing;
import cn.wonders.pos_qdg.net.Result;
import cn.wonders.pos_qdg.tool8583.CodeUtils;
import cn.wonders.pos_qdg.tool8583.Connect;

public class UpUtils {

	private String respCode;
	private int delnum;
	private UpLiushuiAsyncTask updateBLPramsAsyncTask;
	private UpAbNormalLiushuiAsyncTask updateAbnormalPramsAsyncTask;
	private List<TradingBean> queryNoYLUpdateBean;
	private List<TradingAbnormalBean> queryNoYLUpdateAbNormalBean;
	private TradingDao dao;
	private TradingAbnormalDao abnDao;
	private TradingHistoryDao hisDao;
	private CloudposApplication application;

	private Context mContext;
	private Handler handler;
	private Message msg;
	public static int tag=1;

	public UpUtils(Context mContext,Handler handler,CloudposApplication application){
		this.mContext=mContext;
		this.handler=handler;
		this.application=application;
	}
	public UpUtils(Context mContext,Handler handler){
		this.mContext=mContext;
		this.handler=handler;
	}

	/**
	 * 上送流水
	 */
	public void uploadLiushui(int tag) {
		this.tag=tag;
		LogUtil.e("TIME","调用方法TAG："+tag);
		application.setIsServerUp(true);
		String tpdu = SPUtils.getString(mContext, SPUtils.TPDU, "");
		if (tpdu.equals("")) {
			if (handler != null)
				handler.sendEmptyMessage(ManageActivity.notTPDU);
		} else if (CloudposApplication.getInstance().errNum>5) {
			LogUtil.e("TIME", "for循环执行完成");
			ManageActivity.isUpdating=false;
			if (handler != null){
				handler.sendEmptyMessage(19);
			}
			updateBLPramsAsyncTask = new UpLiushuiAsyncTask();
			updateBLPramsAsyncTask.execute();
		} else{
			TradingBean liushui;
			List<TradingBean> queryAllLiushuiList;
			String type, field2, field3, field4, field11, field14, field22, field23, field25, field26, field35, field36, field41, field42, field49, field52, field53, field55, field60, field63, field64;
			String tag9F26, tag9F27, tag9F10, tag9F37, tag9F36, tag95, tag9A, tag9C, tag9F02, tag5F2A, tag82, tag9F1A, tag9F03, tag9F33, tag9F74, tag8A;
			String shanghu = SPUtils.getString(mContext, SPUtils.ShanghuNo, "");
			long time;
			Date d;
			dao = TradingDao.getInstance(mContext);
			abnDao = TradingAbnormalDao.getInstance(mContext);
			hisDao = TradingHistoryDao.getInstance(mContext);
			queryAllLiushuiList = dao.queryNoYLUpdateBean();
			if (queryAllLiushuiList!=null) {
				if (queryAllLiushuiList.size() <1) {
					if (handler != null)
						handler.sendEmptyMessage(2);
				} else {
					if (handler != null)
						handler.sendEmptyMessage(3);
				}
				int errorNum=0,normalLiushui=0,errorLiushui=0;
				LogUtil.e("TIME",queryAllLiushuiList.size()+"");
				if (queryAllLiushuiList.size()<=0) {
					ManageActivity.isUpdating=false;
				}
				for (int i = 0; i < queryAllLiushuiList.size(); i++) {
					if (errorNum>=4) {
						errorNum=0;
						if (handler != null)
							handler.sendEmptyMessage(14);
						break;
					}	
					liushui = queryAllLiushuiList.get(i);
					int tranMoney = (int) (Float.parseFloat(liushui
							.getTranMoney()) * 100);
					if (tranMoney == 0) {
						LogUtil.e("手动执行金额为0上送过程");
						liushui.setIsUpdate(1);
						liushui.setIsAbnormalUP(1);
						liushui.setBatchNumber(CloudposApplication.pici);
						liushui.setAbnormalCode("00");
						SimpleDateFormat df = new SimpleDateFormat("MMdd");
						liushui.setSendUPTime(df.format(System
								.currentTimeMillis()));
						dao.updateLiushui(liushui);
					} else {
						type = "0200";
						field2 = liushui.getCardNumber();
						field3 = "000000";
						field11 = CodeUtils.leftZeroFill(liushui.getTranList(),
								6);
						field14 = null;
						field22 = "072";
						field23 = "001";
						field25 = "00";
						field26 = null;
						field35 = null;
						field36 = null;
						field41 = SPUtils.getString(mContext,
								SPUtils.zhongduanID, "");
						field42 = shanghu;
						field49 = "156";
						field52 = null;
						field53 = null;
						field60 = "36" + CloudposApplication.pici + "00060";
						field63 = "CUP";
						field64 = SPUtils.getString(mContext, SPUtils.UPK, "");
						// 55域数据
						tag9F26 = liushui.getCard_TC_V();
						// 代表TC的类型
						tag9F27 = "40";
						tag9F10 = liushui.getCard_IDD_V();
						tag9F37 = "00000000";
						tag9F36 = liushui.getCard_ATC_V();
						tag95 = "0000000000";
						tag9A = liushui.getTranTime();
						tag9C = "00";
						field4 = CodeUtils.leftZeroFill(
								Integer.toString(tranMoney), 12);
						tag9F02 = CodeUtils.leftZeroFill(
								Integer.toString(tranMoney), 12);
						tag5F2A = "0156";
						tag82 = liushui.getCard_AIP_V();
						tag9F1A = "0156";
						tag9F03 = "000000000000";
						tag9F33 = "000000";
						tag9F74 = "454343303031";
						tag8A = "5931";
						time = Long.parseLong(tag9A);
						d = new Date(time);
						SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
						tag9A = sdf.format(d);
						field55 = CodeUtils.getHexStr(new Connect()
						.pack55Field(tag9F26, tag9F27, tag9F10,
								tag9F37, tag9F36, tag95, tag9A, tag9C,
								tag9F02, tag5F2A, tag82, tag9F1A,
								tag9F03, tag9F33, tag9F74, tag8A));
						new Connect().consumeOffline(type, field2, field3,
								field4, field11, field14, field22, field23,
								field25, field26, field35, field36, field41,
								field42, field49, field52, field53, field55,
								field60, field63, field64, mContext, tpdu);
						respCode = CloudposApplication.respCode;
						if(respCode.equals("99")){
							errorNum++;
							CloudposApplication.getInstance().addErrNum();
							LogUtil.e("流水上送返回99，socket建链失败");
							if (handler!=null) {
								msg = new Message();
								msg.what=7;
								msg.obj="99";
								handler.sendMessage(msg);
							}
						}else{
							liushui.setBatchNumber(CloudposApplication.pici);
							liushui.setAbnormalCode(respCode);
							dao.updateLiushui(liushui);
							if (respCode.equals("00")
									||respCode.equals("Y1")
									||respCode.equals("Y3")) {
								errorNum=0;
								CloudposApplication.getInstance().removeErrNum();
								normalLiushui++;
								liushui.setIsUpdate(1);
								liushui.setIsAbnormalUP(1);
								liushui.setSendUPTime(CloudposApplication.dateQingSuan);
								dao.updateLiushui(liushui);
							}  else if (respCode.equals("A0")) {
								CloudposApplication.getInstance().addErrNum();
								//执行签到
								signIn();
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}else if (respCode.equals("14")
									||respCode.equals("15")
									||respCode.equals("21")
									||respCode.equals("30")
									||respCode.equals("58")
									||respCode.equals("94")){
								SimpleDateFormat df = new SimpleDateFormat("MMdd");
								liushui.setSendUPTime(df.format(System
										.currentTimeMillis()));
								errorNum=0;
								CloudposApplication.getInstance().removeErrNum();
								errorLiushui++;
								if (abnDao == null) {
									abnDao = TradingAbnormalDao
											.getInstance(mContext);
								}
								int num = abnDao
										.saveAbnormalTrading(new TradingAbnormalBean(
												liushui));
								if (num >0) { 
									// 异常流水存成功了，在流水表中删除本条流水
									delnum = dao.deleteTrading(liushui);
									if (delnum <0) { 
										// 已转表异常流水删除失败
										LogUtil.e("流水表：已上送异常表流水，删除失败");
										LogUtil.e("异常流水(已转异常表未删除)："
												+ liushui.toString());
										liushui.setIsAbnormalUP(10);
										dao.updateLiushui(liushui);
									}
								} else {
									// 异常流水转表失败
									liushui.setIsUpdate(1);
									LogUtil.e("流水表：上送异常表失败，返回码：" + num);
									LogUtil.e("异常流水(未转异常表)：" + liushui.toString());
									liushui.setIsAbnormalUP(11);
									dao.updateLiushui(liushui);
								}
							}
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				Long queryLiushuiNum = dao.queryNoYLUpdateNum();
				if (queryLiushuiNum>0) {
					if (tag<2) {
						updateBLPramsAsyncTask = new UpLiushuiAsyncTask();
						updateBLPramsAsyncTask.execute();
					}else{
						LogUtil.e("TIME", "150条已完成，流水未全部送出，继续for循环");
						ManageActivity.isUpdating=true;
					}
				}else{
					LogUtil.e("TIME", "for循环执行完成");
					ManageActivity.isUpdating=false;
					if (handler != null){
						msg = new Message();
						msg.what=15;
						msg.arg1=normalLiushui;
						msg.arg2=errorLiushui;
						handler.sendMessage(msg);
					}
					updateBLPramsAsyncTask = new UpLiushuiAsyncTask();
					updateBLPramsAsyncTask.execute();
				}
			}
		}
	}

	/**
	 * 上送流水
	 * 
	 */
	class UpLiushuiAsyncTask extends AsyncTask<String, Void, Boolean> {
		String strError;
		Exception exception;
		private Result queryResult;
		private boolean BLPrams;

		@Override
		protected Boolean doInBackground(String... prams) {
			try {
				// payURL =
				// "http://10.2.163.179:8080/myagent/control/report/list.jhtml?";
				String payURL = CPHttpClientUtil.getBillUpLiushuiURL(mContext);
				if (StringUtils.isBlank(payURL)) {
					return false;
				}
				Map<String, Object> pramsMap = transUpLSReqMapParams();
				if (pramsMap == null) {
					LogUtil.e("上送后台数据为空");
					return false;
				}
				HttpResponse response = CPHttpClientUtil.httpPost(payURL, pramsMap);
				queryResult = JSONParsing.NoReturnDataRequestToArray(response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
			}
			return queryResult != null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			// CustomProgressDialog.hideProgressDialog();
			if (exception != null) {
				application.setIsServerUp(false);
				if (handler != null)
					handler.sendEmptyMessage(1);
				LogUtil.e("上送服务器流水exception:" + exception.toString());
				return;
			}
			if (result) {
				if (CPHttpClientUtil.Success.equals(queryResult.coder)) {
					// 查询成功
					BLPrams = JSONParsing.parsBLPrams(mContext,
							queryResult.jsonBodyArray);
					if (BLPrams) {
						// 解析成功并保存到数据库
						LogUtil.e("流水上传成功！");
						updateLiushui(hisDao, dao);
						if (handler != null) {
							handler.sendEmptyMessage(16);
						}
					} else {
						LogUtil.e("流水上传后台失败，BLPrams问题");
						if (handler != null)
							handler.sendEmptyMessage(17);
					}
				}else {
					LogUtil.e("流水上传后台失败，queryResult.coder问题");
					LogUtil.e("返回码:"+queryResult.coder);
					if (handler != null)
						handler.sendEmptyMessage(17);
				}
			} else {
				LogUtil.e("流水上传后台失败，result问题");
				if (handler != null){
					LogUtil.e("取消dialog");
					handler.sendEmptyMessage(1);
				}
			}
			application.setIsServerUp(false);
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			application.setIsServerUp(false);
			if (handler != null)
				handler.sendEmptyMessage(9);
			updateBLPramsAsyncTask = null;
			super.onCancelled();
		}
	}

	/**
	 * ManageAvtivity上送流水至管理台
	 * 
	 * serial_num NUMBER(20) not null, 流水编号 card_num VARCHAR2(30), 卡号 device_id
	 * NUMBER(20), 终端号 tran_amount NUMBER(20,2), 消费金额 tran_time VARCHAR2(20),
	 * 消费时间 upload_union_pay_time VARCHAR2(20), 流水上送银联时间 synctime VARCHAR2(20),
	 * 同步时间 reserved VARCHAR2(50), 预留 meal_type CHAR(2), 餐别 早中晚夜 is_quatata
	 * NUMBER 定额标志
	 * 
	 * @return
	 */
	public Map<String, Object> transUpLSReqMapParams() {
		// TODO Auto-generated method stub

		dao = TradingDao.getInstance(mContext);
		queryNoYLUpdateBean = dao.queryupYLnoUPS(true);
		if (queryNoYLUpdateBean == null || queryNoYLUpdateBean.size() < 1) {
			return null;
		}
		List<UpLiushuiToSBean> list = new ArrayList<UpLiushuiToSBean>();
		list.clear();
		String device_id = SPUtils.getString(mContext, SPUtils.TerminalNo, "");
		long upTime = System.currentTimeMillis();
		for (int i = 0; i < queryNoYLUpdateBean.size(); i++) {
			TradingBean liushui = queryNoYLUpdateBean.get(i);
			UpLiushuiToSBean bean = new UpLiushuiToSBean();
			bean.setCard_num(liushui.getCardNumber());
			bean.setDevice_id(device_id);
			bean.setIs_quatata(liushui.getisQuota() + "");
			bean.setMeal_type(liushui.getStyle());
			bean.setReserved("");
			bean.setSerial_num(liushui.getTranList());
			bean.setSynctime(upTime + "");
			bean.setTran_amount(liushui.getTranMoney());
			bean.setTran_time(liushui.getTranTime());
			bean.setUpload_union_pay_time(liushui.getSendUPTime());
			bean.setBatch_num(liushui.getBatchNumber());
			bean.setAbnormalCode(liushui.getAbnormalCode());
			bean.setisAbnormalUP(liushui.getIsAbnormalUP() + "");
			bean.setworkerId(liushui.getWorkerID());
			list.add(bean);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("protocolType", "1163"); // 接口类别
		params.put("data", list); // 当前版本号
		return params;
	}
	/**
	 * 转表
	 */
	protected void updateLiushui(TradingHistoryDao hisDao, TradingDao dao) {// 没有必要传递DAO ？？？？？？？？？？
		// TODO Auto-generated method stub
		TradingBean liushui;
		if (hisDao == null) {
			hisDao = TradingHistoryDao.getInstance(mContext);
		}
		for (int i = 0; i < queryNoYLUpdateBean.size(); i++) {
			liushui = queryNoYLUpdateBean.get(i);
			int num = hisDao
					.saveHistoryTrading(new TradingHistoryBean(liushui));
			if (num >0) {
				// 存成功了,流水表中删除此条流水   
				delnum = dao.deleteTrading(liushui);
				if (delnum <0) {
					// TODO 正常流水上送，转历史表成功，流水删除失败
					LogUtil.e("流水表：已上送历史表流水，删除失败");
					LogUtil.e("异常流水(已转历史表未删除)：" + liushui.toString());
					liushui.setIsAbnormalUP(10);
					dao.updateLiushui(liushui); 
				}
			} else {
				// TODO 历史表转表失败
				LogUtil.e("流水表：上送历史表失败，返回码：" + num);
				LogUtil.e("正常流水(未转表)：" + liushui.toString());
				liushui.setIsAbnormalUP(12);
				dao.updateLiushui(liushui); 
			}
		}
	}
	//***********************************************************************************
	/**
	 * 上送异常流水
	 */
	public void uploadAbnormalLiushui() {
		updateAbnormalPramsAsyncTask=new UpAbNormalLiushuiAsyncTask();
		updateAbnormalPramsAsyncTask.execute();
	}
	/**
	 * 上送异常流水
	 * 
	 */
	class UpAbNormalLiushuiAsyncTask extends AsyncTask<String, Void, Boolean> {
		String strError;
		Exception exception;
		private Result queryResult;
		private boolean BLPrams;

		@Override
		protected Boolean doInBackground(String... prams) {
			// TODO Auto-generated method stub
			Map<String, Object> pramsMap = transUpAbNormalLSReqMapParams();
			if (pramsMap == null) {
				LogUtil.e("上送后台异常流水数据为空");
				return false;
			}
			String upAbnormalURL = CPHttpClientUtil.getBillUpAbNormalLiushuiURL(mContext);
			// payURL =
			// http://[ip]:[port]/myagent/control/report/abnormalSerialList;
			if (StringUtils.isBlank(upAbnormalURL)) {
				LogUtil.e("上送后台异常流水URL为空");
				return false;
			}
			HttpResponse response = CPHttpClientUtil.httpPost(upAbnormalURL, pramsMap);
			try {
				queryResult = JSONParsing.NoReturnDataRequestToArray(response);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
			}
			return queryResult != null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			// CustomProgressDialog.hideProgressDialog();
			if (exception != null) {
				if (handler != null)
					handler.sendEmptyMessage(1);
				LogUtil.e("上送服务器异常流水exception:" + exception.toString());
				return;
			}
			if (result) {
				if (CPHttpClientUtil.Success.equals(queryResult.coder)) {
					// 查询成功
					BLPrams = JSONParsing.parsBLPrams(mContext,
							queryResult.jsonBodyArray);
					if (BLPrams) {
						// 解析成功并保存到数据库
						LogUtil.e("异常流水上传成功！");
						if (handler != null) {
							handler.sendEmptyMessage(16);
						}
						//						updateAbnormalLiushui(abnDao);
						abnDao.clearAll();
					} else {
						LogUtil.e("异常流水上传后台失败，BLPrams问题");
						if (handler != null)
							handler.sendEmptyMessage(17);
					}
				}else if (CPHttpClientUtil.Timeout.equals(queryResult.coder)) {
					LogUtil.e("异常流水上传后台失败，TimeOut!");
				}else {
					LogUtil.e("异常流水上传后台失败，queryResult.coder问题");
					if (handler != null)
						handler.sendEmptyMessage(17);
				}
			} else {
				LogUtil.e("异常流水上传后台失败，result问题");
				if (handler != null)
					handler.sendEmptyMessage(1);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			if (handler != null)
				handler.sendEmptyMessage(9);
			updateAbnormalPramsAsyncTask = null;
			super.onCancelled();
		}
	}

	/**
	 * ManageAvtivity上送异常流水至管理台
	 * 
	 * serial_num NUMBER(20) not null, 流水编号 card_num VARCHAR2(30), 卡号 device_id
	 * NUMBER(20), 终端号 tran_amount NUMBER(20,2), 消费金额 tran_time VARCHAR2(20),
	 * 消费时间 upload_union_pay_time VARCHAR2(20), 流水上送银联时间 synctime VARCHAR2(20),
	 * 同步时间 reserved VARCHAR2(50), 预留 meal_type CHAR(2), 餐别 早中晚夜 is_quatata
	 * NUMBER 定额标志
	 * 
	 * @return
	 */
	public Map<String, Object> transUpAbNormalLSReqMapParams() {
		// TODO Auto-generated method stub

		abnDao = TradingAbnormalDao.getInstance(mContext);
		queryNoYLUpdateAbNormalBean = abnDao.queryAllLiushuiList();
		if (queryNoYLUpdateAbNormalBean == null || queryNoYLUpdateAbNormalBean.size() < 1) {
			return null;
		}
		List<UpLiushuiToSBean> list = new ArrayList<UpLiushuiToSBean>();
		list.clear();
		String device_id = SPUtils.getString(mContext, SPUtils.TerminalNo, "");
		long upTime = System.currentTimeMillis();
		for (int i = 0; i < queryNoYLUpdateAbNormalBean.size(); i++) {
			TradingAbnormalBean liushui = queryNoYLUpdateAbNormalBean.get(i);
			UpLiushuiToSBean bean = new UpLiushuiToSBean();
			bean.setCard_num(liushui.getCardNumber());
			bean.setDevice_id(device_id);
			bean.setIs_quatata(liushui.getisQuota() + "");
			bean.setMeal_type(liushui.getStyle());
			bean.setReserved("");
			bean.setSerial_num(liushui.getTranList());
			bean.setSynctime(upTime + "");
			bean.setTran_amount(liushui.getTranMoney());
			bean.setTran_time(liushui.getTranTime());
			bean.setUpload_union_pay_time(liushui.getSendUPTime());
			bean.setBatch_num(liushui.getBatchNumber());
			bean.setAbnormalCode(liushui.getAbnormalCode());
			bean.setisAbnormalUP(liushui.getIsAbnormalUP() + "");
			bean.setworkerId(liushui.getWorkerID());
			list.add(bean);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("protocolType", "1163"); // 接口类别
		params.put("abnormaldata", list); // 当前版本号
		return params;
	}
	protected void updateAbnormalLiushui(TradingAbnormalDao abDao) {
		// TODO Auto-generated method stub
		TradingAbnormalBean abnormalLiushui;
		if (abDao == null) {
			abDao = TradingAbnormalDao.getInstance(mContext);
		}
		for (int i = 0; i < queryNoYLUpdateAbNormalBean.size(); i++) {
			abnormalLiushui = queryNoYLUpdateAbNormalBean.get(i);
			abnormalLiushui.setIsAbnormalUP(20);
			abDao.updateAbnormalLiushui(abnormalLiushui);
		}
		int abNum=abDao.updateLiushuiList(queryNoYLUpdateAbNormalBean);
		if (abNum<0) {
			LogUtil.e("异常流水更新isQuta字段失败："+abNum);
		}
	}

	//***********************************************************************************
	public void signIn() {
		CloudposApplication.getInstance().setIsSignIn(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				String tpdu = SPUtils.getString(mContext, SPUtils.TPDU, "");
				if (tpdu.equals("")) {
					LogUtil.d("未设置TPDU！");
					if (handler!=null) {
						handler.sendEmptyMessage(MainActivity.UNSginFild);
					}
				} else {
					boolean signIn = new Connect().signIn("0800", SPUtils
							.getSerialData(mContext), SPUtils.getString(
									mContext, SPUtils.zhongduanID, ""), SPUtils
									.getString(mContext, SPUtils.ShanghuNo, ""), "00"
											+ CloudposApplication.pici + "003", null, "123",
											mContext, tpdu);
					if (signIn) {
						if (handler!=null)
							handler.sendEmptyMessage(MainActivity.UNSginOK);
					} else {
						if (handler!=null)
							handler.sendEmptyMessage(MainActivity.UNSginFild);
					}
				}
			}
		}).start();
	}
	//*****************************************************************************************
	/**
	 * 每当开机的时候，自动检查数据库内为上送流水数量，如果超过阀值开始报警不允许进行消费
	 * @param mContext
	 * @param handler
	 */
	public void checkTradingLiushui(){
		TradingDao dao = TradingDao.getInstance(mContext);
		Long nUpUnionpayAmount = dao.getNoYLUpdateCount();
		String mThresholdLevel = SPUtils.getString(mContext,SPUtils.ThresholdLevel, "2000");
		if (nUpUnionpayAmount>=Long.parseLong(mThresholdLevel)) {
			handler.sendEmptyMessage(BuzzerTools.STOP_SERVICE);
		}
	}

	public void onDestroy(){
		if (updateBLPramsAsyncTask!=null) {
			updateBLPramsAsyncTask.cancel(true);
		}
		updateBLPramsAsyncTask = null;
	}


}
