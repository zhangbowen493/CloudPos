package cn.wonders.pos_qdg.util;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import org.apache.commons.collections.map.ListOrderedMap;

import com.google.gson.Gson;
import com.wd.liandidemo.RF.PBOC_TLV;

import cn.wonders.pos_qdg.bean.BlackList;
import cn.wonders.pos_qdg.bean.CiDianBean;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.QuotaRate;
import cn.wonders.pos_qdg.bean.UpLiushuiToSBean;
import cn.wonders.pos_qdg.bean.User;
import cn.wonders.pos_qdg.bean.WorkTimeParma;
import cn.wonders.pos_qdg.db.BlackListDao;
import cn.wonders.pos_qdg.db.CiDianDao;
import cn.wonders.pos_qdg.db.TradingDao;
import cn.wonders.pos_qdg.db.QuotaRateDao;
import cn.wonders.pos_qdg.db.UserDao;
import cn.wonders.pos_qdg.db.WorkTimeParmaDao;
import cn.wonders.pos_qdg.tool8583.CodeUtils;
import cn.wonders.pos_qdg.tool8583.ProcessException;
import cn.wonders.pos_qdg.tool8583.ProcessHelper;
import cn.wonders.pos_qdg.tool8583.RequestDataPacket;
import cn.wonders.pos_qdg.tool8583.ResponseDataPacket;
import cn.wonders.pos_qdg.tool8583.TcpClient;
import android.content.Context;
import android.os.Environment;
import android.test.AndroidTestCase;

public class TestCase extends AndroidTestCase {
	
	public void sdd(){
		String PATH_LOGCAT="";
//		if (Environment.getExternalStorageState().equals(
//				Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
			PATH_LOGCAT = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "miniGPS";
			System.out.println("ccccc:" + PATH_LOGCAT);
//		} else {// 如果SD卡不存在，就保存到本应用的目录下
//			PATH_LOGCAT = context.getFilesDir().getAbsolutePath()
//					+ File.separator + "miniGPS";
			PATH_LOGCAT = Environment.getDownloadCacheDirectory().toString();

			System.out.println( "aaaa:" + PATH_LOGCAT);
//		}
		
		
	}

	private TradingDao liushuiDao;
	public void ttt(){
		
		System.out.println(getSDPath());
	}
	/**
	   * 
	   *TODO：保存文件 根目录
	   *Author：Andy.Liu
	   *Create Time：2012-7-10 上午08:54:14
	   *TAG：@return
	   *Return：String
	   */
	  public static String getSDPath(){
	   boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	   if(hasSDCard){
	    return Environment.getExternalStorageDirectory().toString();
	   }else
	    return Environment.getDownloadCacheDirectory().toString();
	  }
	
	public void query1() {
		TradingDao dao = TradingDao.getInstance(mContext);
		List<TradingBean> queryAllLiushuiList = dao.queryAllLiushuiList();
		System.out.println(queryAllLiushuiList.size());
//		for (int i = 0; i < queryAllLiushuiList.size(); i++) {
//			System.out.println("流水：" + queryAllLiushuiList.get(i).toString());
//		}
//		dao.deleteAll();
		

//		List<CiDianBean> queryAllList = new CiDianDao(mContext).queryAllList();
//		for (int i = 0; i < queryAllList.size(); i++) {
//			System.out.println("词典:" + queryAllList.get(i).toString());
//		}
//
//		List<WorkTimeParma> queryAllWorkTime = new WorkTimeParmaDao(mContext)
//				.queryAllWorkTime();
//		for (int i = 0; i < queryAllWorkTime.size(); i++) {
//			System.out.println("营业时间：" + queryAllWorkTime.get(i).toString());
//		}
//
//		List<QuotaRate> quotaAllList = new QuotaRateDao(mContext)
//				.queryAllList();
//		for (int i = 0; i < quotaAllList.size(); i++) {
//			System.out.println("税率：" + quotaAllList.get(i).toString());
//		}
//
//		List<BlackList> queryAllBlackList = new BlackListDao(mContext)
//				.queryAllBlackList();
//
//		for (int i = 0; i < queryAllBlackList.size(); i++) {
//			System.out.println("黑名单：" + queryAllBlackList.get(i).toString());
//		}
	}

	public void t3() {
		liushuiDao = TradingDao.getInstance(mContext);
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				for (int i = 0; i < 5000; i++) {
//					
//				liushuiDao.queryAllLiushuiList();
//				try {
//					Thread.sleep(200);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				}
//			}
//		}).start();
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				System.out.println("开始时间："+System.currentTimeMillis());
//				for (int i = 0; i < 5000; i++) {
//					
//					SaveTranLiushui(
//							"001"+i,
//							"1",
//							"775682027C00940C0803030018010601200202009F3602015757136231700180007160672D25072200000000670F9F101307010103900000010A010000000326DD52804F9F2608AEF3E23BE546BF825F3401019F6C0200009000",
//							"99999999999999");
//					System.out.println("jjjjjj"+i);
//					
//					
////					try {
////						Thread.sleep(200);
////					} catch (InterruptedException e) {
////						// TODO Auto-generated catch block
////						e.printStackTrace();
////					}
//				}
//				
//				System.out.println("结束时间："+System.currentTimeMillis());
//			}
//		}).start();

		for (int i = 0; i < 11000; i++) {

			SaveTranLiushui(
					"2135"+i,
					"0.01",
					"775682027C00940C0803030018010601200202009F3602015757136231700180007160672D25072200000000670F9F101307010103900000010A010000000326DD52804F9F2608AEF3E23BE546BF825F3401019F6C0200009000",
					"123456788896");
			System.out.println("adsa"+i);
		}
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
		// TODO Auto-generated method stub
		String mCardLiushui = CodeUtils.leftZeroFill(
				SPUtils.get(mContext, SPUtils.serialData, 0).toString(), 6);
		LogUtil.d("流水号：" + mCardLiushui);
		TradingBean mLiushuiBean = APPTools.Data2Liushui(cashReqData);

		long currentTimeMillis = System.currentTimeMillis();
		ArrayList<TradingBean> list = new ArrayList<TradingBean>();
		if (mLiushuiBean != null) {
			mLiushuiBean.setWorkerID(workerid);
			mLiushuiBean.setCardNumber(cardNumber);
			mLiushuiBean.setTranMoney(money);
			mLiushuiBean.setTranList(mCardLiushui);
			mLiushuiBean.setTranTime(currentTimeMillis + "");
			mLiushuiBean.setStyle("01");
			// mLiushuiBean.setSendUPTime(workerID);
			mLiushuiBean.setSyncTime("0");
			mLiushuiBean.setIsUpdate(0);
			mLiushuiBean.setisQuota(1);
			list.add(mLiushuiBean);
			liushuiDao.addLiushuiList(list);
		}
		return mLiushuiBean;
	}

	
	int times=0;
	public void T2(){
		int wTimes = 2;
		String s = "07211400";
		String startTime = "22:00";
		String endTime = "2:00";

		int year = TimerTools.getYear(); // 当前年
		int month = TimerTools.getMonth(); // 当前月
		int day = TimerTools.getDay(); // 当前日
		int monthDays = TimerTools.monthDays();// 多少天

		String cardTimeMark = s.substring(0, 6);

		times = Integer.parseInt(s.substring(6, 8));

		// yyyyMMDDHH
		String cardTime = year + cardTimeMark;

		long cardTimeL = TimerTools.date2long(cardTime, "yyyyMMddHH"); // M1区读取出的时间

		StringBuffer startbuf = new StringBuffer();
		startbuf.append(year).append(month).append(day).append(startTime);
		String startTimeStr = startbuf.toString();
		long startTimeL = TimerTools.date2long(startTimeStr, "yyyyMMddHH:mm");

		StringBuffer endbuf = new StringBuffer();
		endbuf.append(year).append(month).append(day).append(endTime);
		String endTimeStr = endbuf.toString();
		long endTimeL = TimerTools.date2long(endTimeStr, "yyyyMMddHH:mm");

		if (endTimeL > startTimeL) {
			day++;
			if (day > monthDays) {
				month += 1;
				day = 1;
				if (month > 12) {
					year += 1;
					month = 1;
				}
			}
			endbuf = new StringBuffer();
			endbuf.append(year).append(month).append(day).append(endTime);
			endTimeStr = endbuf.toString();
			endTimeL = TimerTools.date2long(endTimeStr, "yyyyMMddHH:mm");
		}

		if (startTimeL < cardTimeL && cardTimeL < endTimeL) {
			if (times < wTimes) {
				System.out.println("可以就餐！");
			} else {
				System.out.println("超过就餐次数！");
			}
		} else {
			System.out.println("不在就餐时间！");
		}
	}

	public void t1() {
		BlackListDao dao = new BlackListDao(mContext);
		List<BlackList> blackList = dao.getBlackList("card_number",
				"6231700180007160672");

		for (int i = 0; i < blackList.size(); i++) {
			System.out.println(blackList.toString());
		}
	}

	public void cleanBanBen() {

	}

	public void updateliushui() {
		TradingDao dao = TradingDao.getInstance(mContext);
		List<TradingBean> queryAllLiushuiList = dao.queryAllLiushuiList();
		TradingBean liushui = queryAllLiushuiList.get(0);

		liushui.setIsUpdate(1);

		dao.updateLiushui(liushui);

		dadsa();
	}

	public void dadsa() {
		TradingDao dao = TradingDao.getInstance(mContext);
		List<TradingBean> queryNoYLUpdateBean1 = dao.queryNoYLUpdateBean();

		for (int i = 0; i < queryNoYLUpdateBean1.size(); i++) {
			System.out.println("未清算：" + queryNoYLUpdateBean1.get(i).toString());
		}
		List<TradingBean> queryNoYLUpdateBean = dao.queryupYLnoUPS(true);

		for (int i = 0; i < queryNoYLUpdateBean.size(); i++) {
			System.out.println("未上送：" + queryNoYLUpdateBean.get(i).toString());
		}
		List<TradingBean> queryAllLiushuiList = dao.queryAllLiushuiList();

		for (int i = 0; i < queryAllLiushuiList.size(); i++) {
			System.out.println("全部：" + queryAllLiushuiList.get(i).toString());
		}

	}

	public void query() {
		TradingDao dao = TradingDao.getInstance(mContext);
		List<TradingBean> queryAllLiushuiList = dao.queryAllLiushuiList();
		for (int i = 0; i < queryAllLiushuiList.size(); i++) {
			System.out.println("流水：" + queryAllLiushuiList.get(i).toString());
		}

		List<CiDianBean> queryAllList = new CiDianDao(mContext).queryAllList();
		for (int i = 0; i < queryAllList.size(); i++) {
			System.out.println("词典:" + queryAllList.get(i).toString());
		}

		List<WorkTimeParma> queryAllWorkTime = new WorkTimeParmaDao(mContext)
				.queryAllWorkTime();
		for (int i = 0; i < queryAllWorkTime.size(); i++) {
			System.out.println("营业时间：" + queryAllWorkTime.get(i).toString());
		}

		List<QuotaRate> quotaAllList = new QuotaRateDao(mContext)
				.queryAllList();
		for (int i = 0; i < quotaAllList.size(); i++) {
			System.out.println("税率：" + quotaAllList.get(i).toString());
		}

		List<BlackList> queryAllBlackList = new BlackListDao(mContext)
				.queryAllBlackList();

		for (int i = 0; i < queryAllBlackList.size(); i++) {
			System.out.println("黑名单：" + queryAllBlackList.get(i).toString());
		}
	}

	public void sss() {
		Gson gson = new Gson();

		TradingDao dao = TradingDao.getInstance(mContext);
		// 稍后修改为已清算的列表
		List<TradingBean> queryNoYLUpdateBean = dao.queryNoYLUpdateBean();
		List<UpLiushuiToSBean> list = new ArrayList<UpLiushuiToSBean>();

		/**
		 * serial_num NUMBER(20) not null, 流水编号 card_num VARCHAR2(30), 卡号
		 * device_id NUMBER(20), 终端号 tran_amount NUMBER(20,2), 消费金额 tran_time
		 * VARCHAR2(20), 消费时间 upload_union_pay_time VARCHAR2(20), 流水上送银联时间
		 * synctime VARCHAR2(20), 同步时间 reserved VARCHAR2(50), 预留 meal_type
		 * CHAR(2), 餐别 早中晚夜 is_quatata NUMBER 定额标志
		 * 
		 * @return
		 */
		list.clear();
		String device_id = SPUtils.getString(mContext, SPUtils.TerminalNo, "");
		long upTime = System.currentTimeMillis();
		for (int i = 0; i < queryNoYLUpdateBean.size(); i++) {
			TradingBean liushui = queryNoYLUpdateBean.get(i);
			UpLiushuiToSBean bean = new UpLiushuiToSBean();
			bean.setCard_num(liushui.getTranList());
			bean.setDevice_id(device_id);
			bean.setIs_quatata(liushui.getisQuota() + "");
			bean.setReserved("");
			bean.setSerial_num(liushui.getTranList());
			bean.setSynctime(upTime + "");
			bean.setTran_amount(liushui.getTranMoney());
			bean.setTran_time(liushui.getTranTime());
			bean.setUpload_union_pay_time(liushui.getSendUPTime());
			list.add(bean);
		}

		String json = gson.toJson(list);

		String test = json.replaceAll("\\x0a|\\x0d", "");

	}

	public void dbsk() {
		String data = "00000000000000000000000000000000";
		String cb_info = "00";
		WorkTimeParmaDao workTimeParmaDao = new WorkTimeParmaDao(mContext);
		WorkTimeParma workTimeParma = workTimeParmaDao.getCB();
		String cb = workTimeParma.getMealType();
		if (StringUtils.isEmpty(cb) || cb.length() > 2) {
			cb = "00";
		}
		String c_temp_str = cb_info.substring(0, 2 - cb.length());
		String CB_str = c_temp_str + cb;
		String Md_str = TimerTools.getDateFormatMd();
		String str = Md_str + CB_str;
		String substring = data.substring(str.length(), data.length());
		data = str + substring;

		System.out.println(data);
	}

	public void aaaaa() {
		TimerTools.isEate("02:30", "04:45");
		//
		// TimerTools.date2long("", "");

	}

	public void test1() {
		TradingDao dao = TradingDao.getInstance(mContext);
		List<TradingBean> queryNoYLUpdateBean = dao.queryAllLiushuiList();

		for (int i = 0; i < queryNoYLUpdateBean.size(); i++) {
			TradingBean liushui = queryNoYLUpdateBean.get(0);

			liushui.setIsUpdate(1);

			dao.updateLiushui(liushui);
			System.out.println("流水：  " + queryNoYLUpdateBean.get(i).toString());
		}

	}

	public void test2() {
		String a = "5F24032412315A0A6231700170200007515F9F0702FF008E0C000000000000000002031F009F0D05D86004A8009F0E0500109800009F0F05D86804F8005F280201568C1B9F02069F03069F1A0295055F2A029A039C019F37049F21039F4E148D1A8A029F02069F03069F1A0295055F2A029A039C019F37049F21039F080200305F2503141211";
		String a1 = "7081865F24032412315A0A6231700170200007515F9F0702FF008E0C000000000000000002031F009F0D05D86004A8009F0E0500109800009F0F05D86804F8005F280201568C1B9F02069F03069F1A0295055F2A029A039C019F37049F21039F4E148D1A8A029F02069F03069F1A0295055F2A029A039C019F37049F21039F080200305F25031412119000";

		List decodingTLV = PBOC_TLV.decodingTLV(a1);
		if (decodingTLV != null && decodingTLV.size() > 0) {
			for (int i = 0; i < decodingTLV.size(); i++) {
				String[] tlv = (String[]) decodingTLV.get(i);
				if ("70".equals(tlv[0])) {
					String CardNumber_str = tlv[2];
					List cardTLV = PBOC_TLV.decodingTLV(CardNumber_str);
					for (int j = 0; j < cardTLV.size(); j++) {
						String[] tlv_card = (String[]) cardTLV.get(j);
						if ("5A".equals(tlv_card[0])) {
							String CardNumber = tlv_card[2];
							System.out.println("卡号是："
									+ CardNumber.replaceAll("F", ""));
							break;
						}
					}
				}
			}
		}
	}

	public void aa() {
		BlackListDao dao = new BlackListDao(mContext);
		List<BlackList> list = dao.queryAllBlackList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("黑名单" + list.get(i).toString());
		}
		QuotaRateDao quotaRateDao = new QuotaRateDao(mContext);
		List<QuotaRate> allBlackList = quotaRateDao.queryAllList();
		for (int i = 0; i < allBlackList.size(); i++) {
			System.out.println("费率" + allBlackList.get(i).toString());
		}

		WorkTimeParmaDao timeParmaDao = new WorkTimeParmaDao(mContext);
		List<WorkTimeParma> workTime = timeParmaDao.queryAllWorkTime();
		for (int i = 0; i < workTime.size(); i++) {
			System.out.println("营业时间：" + workTime.get(i).toString());
		}

	}

	static String str = "775682027C00940C0803030018010601200202009F3602005D57136231700170200007515D24122200000000906F9F101307010103900000010A01000000011695D83AC99F260854495025C35617385F3401019F6C0200009000";

	public void test() {
		TradingBean data2Liushui = APPTools.Data2Liushui(str);

		System.out.println(data2Liushui.toString());
	}

	public void getLiushui() {
		TradingDao dao = TradingDao.getInstance(mContext);

		List<TradingBean> liushuiList = dao.queryAllLiushuiList();
		if (liushuiList != null)
			for (int i = 0; i < liushuiList.size(); i++) {
				System.out.println(liushuiList.get(i));
			}
	}

	public void addLiushui() {
		ArrayList<TradingBean> list = new ArrayList<TradingBean>();

		TradingBean liushui = new TradingBean();
		liushui.setCard_2MMSD_L("1");
		liushui.setCard_2MMSD_T("1");
		liushui.setCard_2MMSD_V("1");

		liushui.setCard_AFL_L("WE");
		liushui.setCard_AFL_T("W");
		liushui.setCard_AFL_V("E");

		liushui.setCard_AIP_L("R");
		liushui.setCard_AIP_T("23");
		liushui.setCard_AIP_V("AD");

		liushui.setCard_ATC_V("GS");
		liushui.setCard_ATC_L("EW");
		liushui.setCard_ATC_T("GE");

		liushui.setCard_IDD_L("WE");
		liushui.setCard_IDD_V("E");
		liushui.setCard_IDD_T("GA");

		liushui.setCard_ATC_T("SD");
		liushui.setCard_ATC_L("EE");
		liushui.setCard_ATC_V("GW");

		liushui.setCard_Number_T("DSS");
		liushui.setCard_Number_L("DSD");
		liushui.setCard_Number_V("SDSD");
		liushui.setCard_Status_T("SFE");
		liushui.setCard_Status_L("SDF");
		liushui.setCard_TC_T("KKK");
		liushui.setCard_TC_L("GG");
		liushui.setCard_TC_V("RR");
		liushui.setCard_Tran_Prama_T("E");
		liushui.setCard_Tran_Prama_L("44");
		liushui.setCard_Tran_Prama_V("555");

		liushui.setCardNumber("4");
		liushui.setIsUpdate(0);
		liushui.setSendUPTime("4");
		liushui.setStyle("4");
		liushui.setWorkerID("4");
		liushui.setTranMoney("4");

		list.add(liushui);

		TradingDao dao = TradingDao.getInstance(mContext);
		dao.addLiushuiList(list);
	}

	public void TLV() {
		String ss = "82027C00940C1801060130010100200202009F360200FB9F260884727BA346DB7C439F101307010103900000010A01000002129948D3E53857136231700190000013539D26012200000000724F5F3401019F6C0200009000";
		// Map mapTLV = PBOC_TLV.decodingTLV(listTLV);
		// // String encodingList = PBOC_TLV.encodingTLV(listTLV);
		// String encodingMap = PBOC_TLV.encodingTLV(mapTLV);
		// // System.out.println(listTLV);
		// System.out.println("+++===="+mapTLV);

		List listTLV = PBOC_TLV.decodingTLV(ss);
		String[] result = new String[listTLV.size()];
		for (int i = 0; i < listTLV.size(); i++) {
			String[] tlv = (String[]) listTLV.get(i);
			result[i] = tlv[0] + "=" + tlv[1] + "=" + tlv[2];
			System.out.println("---=" + result[i]);
		}
	}

	public void sendVPN(Context context) {
		RequestDataPacket request = new RequestDataPacket();
		ResponseDataPacket resp = new ResponseDataPacket();
		TcpClient client = new TcpClient();
		String host = "11.188.247.3";// 地址
		int port = 4000;// 端口
		int connetTimeout = 30000;// 连接超时设置
		int readTimeout = 3000;// 读超时设置
		client.setHost(SPUtils.getString(context, SPUtils.CUP_IP, ""));
		client.setPort(SPUtils.getString(context, SPUtils.CUP_POR, ""));
		client.setReadTimeout(readTimeout);
		client.setConnetTimeout(connetTimeout);

		byte[] content = {};
		String TPDU = "6007730000";
		String header = "601000301505";
		content = ProcessHelper.addTPDU(content,
				CodeUtils.str2CompressBcd(TPDU));// 增加TPDU
		content = ProcessHelper.addHead(content,
				CodeUtils.str2CompressBcd(header));// 增加报文头
		System.out.println("head:" + CodeUtils.bcd2Str(content));
		ListOrderedMap lmap = new ListOrderedMap();
		lmap.put("msgType", "0800");// 消息类型
		lmap.put("posSerial", "123456");// 受卡方系统跟踪号 11域
		lmap.put("cardAcceptorTerminalCode", "58120002");// 受卡机终端标识码 41
		lmap.put("cardAcceptorCode", "450035258120001");// 受卡方标识码 42
		lmap.put("reservedPrivate", "00123456001");// 自定义域60
		lmap.put("reservedPrivate3", "123");// 自定义域63
		// lmap.put("recCardDate", "0621");// 受卡方所在日期
		// lmap.put("respCode", "11");// 应答码 39域
		// lmap.put("cardAcceptorTerminalCode", "999000000000");// 受卡机终端标识码 41域
		// lmap.put("cardAcceptorCode", "111111000000001");// 受卡方标识码 42域
		// lmap.put("ICCardInfo", "111111000000001");// IC卡数据域 55域
		// // 子域信息
		// lmap.put("9F26",
		// "111111000000001111111000000001111111000000001111111000000001");//
		// 55域
		// lmap.put("9F27", "111100000");// 55域 子域
		// lmap.put("9F10", "11111");// 55域 子域
		// lmap.put("5F2A", "1234567890");// 55域 子域
		//
		// lmap.put("reservedPrivate", "01160518");// 自定义域 60域

		request.setData(lmap);

		try {
			resp = client.sendData(request, content);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void badd() {
		ArrayList<BlackList> nlackLists = new ArrayList<BlackList>();

		for (int i = 0; i < 200; i++) {
			BlackList list = new BlackList();
			nlackLists.add(list);
		}

		Integer addBlackList = new BlackListDao(mContext)
				.addBlackList(nlackLists);

		System.out.println("结果显示" + addBlackList);
	}

	public void bqueryAll() {
		List<BlackList> queryAllBlackList = new BlackListDao(mContext)
				.queryAllBlackList();
		System.out.println("" + queryAllBlackList.size());
		for (int i = 0; i < queryAllBlackList.size(); i++) {
			System.out.println(queryAllBlackList.get(i).toString());
		}
	}

	public void bquery() {
		List<BlackList> blackList = new BlackListDao(mContext).getBlackList(
				"worker_number", "1013");
		for (int i = 0; i < blackList.size(); i++) {
			System.out.println(blackList.get(i).toString());
		}
	}

	public void bDle() {
		Long deleteAll = new BlackListDao(mContext).clearAll();
		System.out.println("删除:" + deleteAll);
	}

	public void DleLiuShui() {
		Long deleteAll = TradingDao.getInstance(mContext).clearAll();
		System.out.println("删除:" + deleteAll);
	}

	public void queryAll() {
		List<User> queryAllUser = new UserDao(mContext).queryAllUser();
		for (int i = 0; i < queryAllUser.size(); i++) {
			System.out.println(queryAllUser.get(i).toString());
		}
	}

	public void deleteAllUser() {
		Long deleteAll = new UserDao(mContext).clearAll();

		if (-1 == deleteAll) {
			System.out.println("删除失败");
		} else
			System.out.println(deleteAll + "  删除返回吗");
	}

	public void add() {
		ArrayList<User> users = new ArrayList<User>();
		users.clear();
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setAccount("账号" + i);
			user.setPassword("密码" + i);
			user.setLevel(0);
			users.add(user);
		}
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println(currentTimeMillis);
		System.out.println(users.size());

		Integer addi = new UserDao(mContext).addUserList(users);

		long currentTimeMillis2 = System.currentTimeMillis();

		System.out.println(currentTimeMillis2);
		System.out.println(addi + "执行完毕，用时："
				+ ((currentTimeMillis2 - currentTimeMillis) / 1000) + "S");

	}

	public void getuse1r() {
		UserDao userDao = new UserDao(mContext);
		List<User> users = userDao.getUser("id", "33");
		for (User user : users) {
			System.out.println(user.toString());
		}
	}

	public void te() {
		List<User> users = new UserDao(mContext).getUser("account",
				User.adminAccount);
		if (users == null || users.size() <= 0) {
			users = new ArrayList<User>();
			User user = new User();
			user.setAccount(user.adminAccount);
			user.setPassword(user.adminPassword);
			user.setLevel(1);
			users.add(user);
			new UserDao(mContext).addUserList((ArrayList<User>) users);
		}
	}

	public void addAll() {

	}

	public void getall() {
		List<User> allUser = new UserDao(mContext).getAllUser();
		// for (int i = 0; i < allUser.size(); i++) {
		// System.out.println(allUser.get(i).toString());
		// }

		System.out.println(allUser.size());
	}

	public void getLatelyOrder() {

		List<User> users = new UserDao(mContext).getUser("account",
				User.adminAccount);
		if (users == null || users.size() <= 0) {
			users = new ArrayList<User>();
			User user = new User();
			user.setAccount(user.adminAccount);
			user.setPassword(user.adminPassword);
			user.setLevel(1);
			users.add(user);
			new UserDao(mContext).addUserList((ArrayList<User>) users);
		}
	}

	public void update() {
	}

	public void getuser() {
		List<User> user = new UserDao(mContext).getUser("account", "操作员3422");
		if (user != null) {
			for (User user2 : user) {
				System.out.println(user2.toString());
			}
		}
	}
}
