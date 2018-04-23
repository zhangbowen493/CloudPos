package cn.wonders.pos_qdg.util;

import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import cn.wonders.pos_qdg.bean.IfCanResult;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.WorkTimeParma;

import com.wd.liandidemo.RF.CodeUtils;
import com.wd.liandidemo.RF.PBOC_TLV;

/*
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-5-24 上午10:36:50
 */
public class APPTools {

	public static TradingBean Data2Liushui(String data) {
		TradingBean bean = null;
		if (data != null) {
			bean = new TradingBean();
			String Card_Status_T = null;
			String Card_Status_L = null;
			List tlvList_77 = PBOC_TLV.decodingTLV(data);
			String tag_77 = "";
			for (int i = 0; i < tlvList_77.size(); i++) {
				String[] tlv = (String[]) tlvList_77.get(i);
				LogUtil.i("---=--" + tlv[0]);

				if ("77".equals(tlv[0])) {
					tag_77 = tlv[2];
				} else if ("90".equals(tlv[0])) {
					Card_Status_T = tlv[0];
					Card_Status_L = tlv[1];
				}
			}

			List listTLV = PBOC_TLV.decodingTLV(tag_77);
			String[] result = new String[listTLV.size()];
			for (int i = 0; i < listTLV.size(); i++) {
				String[] tlv = (String[]) listTLV.get(i);
				result[i] = tlv[0] + "=" + tlv[1] + "=" + tlv[2];
				LogUtil.i("---=" + result[i]);
				if ("82".equals(tlv[0])) {
					bean.setCard_AIP_T(tlv[0]);
					bean.setCard_AIP_L(tlv[1]);
					bean.setCard_AIP_V(tlv[2]);
				} else if ("94".equals(tlv[0])) {
					bean.setCard_AFL_T(tlv[0]);
					bean.setCard_AFL_L(tlv[1]);
					bean.setCard_AFL_V(tlv[2]);
				} else if ("9F36".equals(tlv[0])) {
					bean.setCard_ATC_T(tlv[0]);
					bean.setCard_ATC_L(tlv[1]);
					bean.setCard_ATC_V(tlv[2]);
				} else if ("9F26".equals(tlv[0])) {
					bean.setCard_TC_T(tlv[0]);
					bean.setCard_TC_L(tlv[1]);
					bean.setCard_TC_V(tlv[2]);
				} else if ("9F10".equals(tlv[0])) {
					bean.setCard_IDD_T(tlv[0]);
					bean.setCard_IDD_L(tlv[1]);
					bean.setCard_IDD_V(tlv[2]);
				} else if ("57".equals(tlv[0])) {
					bean.setCard_2MMSD_T(tlv[0]);
					bean.setCard_2MMSD_L(tlv[1]);
					bean.setCard_2MMSD_V(tlv[2]);
				} else if ("5F34".equals(tlv[0])) {
					bean.setCard_Number_T(tlv[0]);
					bean.setCard_Number_L(tlv[1]);
					bean.setCard_Number_V(tlv[2]);
				} else if ("9F6C".equals(tlv[0])) {
					bean.setCard_Tran_Prama_T(tlv[0]);
					bean.setCard_Tran_Prama_L(tlv[1]);
					bean.setCard_Tran_Prama_V(tlv[2]);
				}

			}
			bean.setCard_Status_T(Card_Status_T);
			bean.setCard_Status_L(Card_Status_L);
		}
		return bean;
	}

	/**
	 * 上传参数加密串
	 * 
	 * @param amount
	 *            交易金额 （以分为单位）
	 * @param appid
	 *            开发商APPID （统一收银台 签约获取 ）
	 * @param channel
	 *            第三方支付渠道
	 * @param order_no
	 *            开发商订单号
	 * @param trantime
	 *            交易时间
	 * @param appSecret
	 *            开发商APPSecret （统一收银台 签约获取 ）
	 *            |amount|appid|order_no|trantime|签名密钥串|
	 * @return
	 */
	public static String getParamsMD5(String amount, String appid,
			String order_no, String trantime, String appSecret) {
		StringBuffer strB = new StringBuffer("|");
		strB.append(amount).append("|").append(appid).append("|")
		.append(order_no).append("|").append(trantime).append("|")
		.append(appSecret).append("|");
		String digest = getMessageMD5Digest(strB.toString());
		return digest;
	}

	/**
	 * 查询订单参数加密
	 * 
	 * @param strAppid
	 * @param strPos_no
	 * @param string
	 * @param strAppSecret
	 * @return
	 */
	public static String getParamsMD5(String strAppid, String strPos_no,
			String string, String strAppSecret) {
		// TODO Auto-generated method stub
		StringBuffer strB = new StringBuffer("|");
		strB.append(strAppid).append("|").append(strPos_no).append("|")
		.append(string).append("|").append(strAppSecret).append("|");
		String digest = getMessageMD5Digest(strB.toString());
		return digest;
	}

	/**
	 * @param s
	 *            待签名的字符串
	 * @return 签名后的字符串
	 */
	public static String getMessageMD5Digest(String s) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] buffer = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdTemp.update(buffer);
			// 获得密文
			byte[] md = mdTemp.digest();

			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (byte byte0 : md) {
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * @param login
	 *            login name
	 * @param pass
	 *            password
	 * @return Base64 Auth encoded string
	 */
	static String getB64Auth(String login, String pass) {
		String source = login + ":" + pass;
		return "Basic "
		+ Base64.encodeToString(source.getBytes(), Base64.URL_SAFE
				| Base64.NO_WRAP);
	}

	/**
	 * 金额验证
	 * @param str
	 * @return
	 */
	public static boolean isMoney(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern
				.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否是数字判断
	 * 
	 * @param s
	 * @return
	 */
	public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]+(.[0-9]{1,2})?$");
		else
			return false;
	}
	/**
	 * 是否是十六进制数字判断
	 * 
	 * @param s
	 * @return
	 */
	public final static boolean checkHexNum(String s) {
		String regString = "[a-f0-9A-F]{32}";
		if (s != null && !"".equals(s.trim()))
			return Pattern.matches(regString, s);
		else
			return false;
	}

	/**
	 * 获取M1区 第十块标志位写入数据
	 * 
	 * @param mActivity
	 * @return
	 */
	public static String getM1WriteData(WorkTimeParma workTimeParma) {
		// TODO Auto-generated method stub
		String data = "00000000000000000000000000000000";
		String cb_info = "00";
		// WorkTimeParmaDao workTimeParmaDao = new WorkTimeParmaDao(mActivity);
		// WorkTimeParma workTimeParma = workTimeParmaDao.getCB();
		String cb = workTimeParma.getMealType();
		if (StringUtils.isEmpty(cb) || cb.length() > 2) {
			cb = "00";
		}
		String c_temp_str = cb_info.substring(0, 2 - cb.length());
		String CB_str = c_temp_str + cb;
		String Md_str = TimerTools.getDateFormatMd();
		String str = Md_str + CB_str;
		String substring = data.substring(str.length(), data.length());
		// data = str + substring;

		data = CodeUtils.rightZeroFill(str + substring, 32);

		LogUtil.i("写入数据：" + data);
		return data;
	}

	/**
	 * 获取M1区 第十块标志位写入数据 新格式格式为 MMddHHmmxx XX 为优惠次数
	 * 
	 * @param ifCanResult
	 */
	public static String getM1WriteDataNew(IfCanResult ifCanResult) {
		// TODO Auto-generated method stub
		String str = "00000000000000000000000000000000";
		int times = 1;
		String MdHm = TimerTools.getDateFormatMdHm();
		if (ifCanResult != null) {
			if (ifCanResult.getUpTimes()) {
				times = ifCanResult.getTimes() + 1;
			}else{
				times =1;
			}
		}
		String times_str = CodeUtils
				.leftZeroFill(Integer.toHexString(times), 2);
		str = CodeUtils.rightZeroFill(MdHm + times_str, 32);
		return str;
	}

	/**
	 * 金额元转分
	 * 
	 * @see 注意:该方法可处理贰仟万以内的金额,且若有小数位,则不限小数位的长度
	 * @see 注意:如果你的金额达到了贰仟万以上,则不推荐使用该方法,否则计算出来的结果会令人大吃一惊
	 * @param amount
	 *            金额的元进制字符串
	 * @return String 金额的分进制字符串
	 */
	public static String moneyYuanToFen(String amount) {
		if (isEmpty(amount)) {
			return amount;
		}
		// 传入的金额字符串代表的是一个整数
		if (-1 == amount.indexOf(".")) {
			return Integer.parseInt(amount) * 100 + "";
		}
		// 传入的金额字符串里面含小数点-->取小数点前面的字符串,并将之转换成单位为分的整数表示
		int money_fen = Integer.parseInt(amount.substring(0,
				amount.indexOf("."))) * 100;
		// 取到小数点后面的字符串
		String pointBehind = (amount.substring(amount.indexOf(".") + 1));
		// amount=12.3
		if (pointBehind.length() == 1) {
			return money_fen + Integer.parseInt(pointBehind) * 10 + "";
		}
		// 小数点后面的第一位字符串的整数表示
		int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
		// 小数点后面的第二位字符串的整数表示
		int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
		// amount==12.03,amount=12.00,amount=12.30
		if (pointString_1 == 0) {
			return money_fen + pointString_2 + "";
		} else {
			return money_fen + pointString_1 * 10 + pointString_2 + "";
		}
	}

	/**
	 * 金额元转分
	 * 
	 * @see 该方法会将金额中小数点后面的数值,四舍五入后只保留两位....如12.345-->12.35
	 * @see 注意:该方法可处理贰仟万以内的金额
	 * @see 注意:如果你的金额达到了贰仟万以上,则非常不建议使用该方法,否则计算出来的结果会令人大吃一惊
	 * @param amount
	 *            金额的元进制字符串
	 * @return String 金额的分进制字符串
	 */
	public static String moneyYuanToFenByRound(String amount) {
		if (isEmpty(amount)) {
			return amount;
		}
		if (-1 == amount.indexOf(".")) {
			return Integer.parseInt(amount) * 100 + "";
		}
		int money_fen = Integer.parseInt(amount.substring(0,
				amount.indexOf("."))) * 100;
		String pointBehind = (amount.substring(amount.indexOf(".") + 1));
		if (pointBehind.length() == 1) {
			return money_fen + Integer.parseInt(pointBehind) * 10 + "";
		}
		int pointString_1 = Integer.parseInt(pointBehind.substring(0, 1));
		int pointString_2 = Integer.parseInt(pointBehind.substring(1, 2));
		// 下面这种方式用于处理pointBehind=245,286,295,298,995,998等需要四舍五入的情况
		if (pointBehind.length() > 2) {
			int pointString_3 = Integer.parseInt(pointBehind.substring(2, 3));
			if (pointString_3 >= 5) {
				if (pointString_2 == 9) {
					if (pointString_1 == 9) {
						money_fen = money_fen + 100;
						pointString_1 = 0;
						pointString_2 = 0;
					} else {
						pointString_1 = pointString_1 + 1;
						pointString_2 = 0;
					}
				} else {
					pointString_2 = pointString_2 + 1;
				}
			}
		}
		if (pointString_1 == 0) {
			return money_fen + pointString_2 + "";
		} else {
			return money_fen + pointString_1 * 10 + pointString_2 + "";
		}
	}

	/**
	 * 金额分转元
	 * 
	 * @see 注意:如果传入的参数中含小数点,则直接原样返回
	 * @see 该方法返回的金额字符串格式为<code>00.00</code>,其整数位有且至少有一个,小数位有且长度固定为2
	 * @param amount
	 *            金额的分进制字符串
	 * @return String 金额的元进制字符串
	 */
	public static String moneyFenToYuan(String amount) {
		if (isEmpty(amount)) {
			return amount;
		}
		if (amount.indexOf(".") > -1) {
			return amount;
		}
		if (amount.length() == 1) {
			return "0.0" + amount;
		} else if (amount.length() == 2) {
			return "0." + amount;
		} else {
			return amount.substring(0, amount.length() - 2) + "."
					+ amount.substring(amount.length() - 2);
		}

	}

	/**
	 * 判断输入的字符串参数是否为空
	 * 
	 * @return boolean 空则返回true,非空则flase
	 */
	public static boolean isEmpty(String input) {
		return null == input || 0 == input.length()
				|| 0 == input.replaceAll("\\s", "").length();
	}

	/**
	 * 判断是否可以用餐
	 * 
	 * @param m1Info
	 * @param workTimeParma
	 * @return -1 不可以
	 * 
	 *         是否可以吃 次数 是否 +1 或者 赋值1 不可以吃原因 code msg
	 */
	public static IfCanResult IfCan(Map<String, String> m1Info,
			WorkTimeParma workTimeParma) {
		// TODO Auto-generated method stub
		boolean iscan = false;
		boolean isUp = true;
		int errorCoed = IfCanResult.Fild;
		String msg = "不能用餐！";
		int times = 0; // 卡内标记消费次数

		// 定额消费一餐次只能消费一次
		if ("0001".equals(m1Info.get(CodeUtils.M1_APPID))
				&& "01".equals(m1Info.get(CodeUtils.M1_STATUS))) {
			if (workTimeParma != null) {
				int workTimes=0;
				String startTime = workTimeParma.getStartTime();
				String endTime = workTimeParma.getEndTime();
				String workTimesMark = workTimeParma.getTimes();
				if (workTimesMark!=null) {
					workTimes = Integer.parseInt(workTimesMark);// M1标记优惠次数
				}else{
					LogUtil.e("刷卡次数为空");
				}

				/** 24小时毫秒值 */
				long dayLong = 24 * 60 * 60 * 1000;

				int year = TimerTools.getYear(); // 当前年
				int month = TimerTools.getMonth(); // 当前月
				int day = TimerTools.getDay(); // 当前日
				String cardTimeMark = m1Info.get(CodeUtils.M1_DATE);// M1标记日期
				times = Integer.parseInt(m1Info.get(CodeUtils.M1_TIMES),16);// M1标记优惠次数

				// yyyyMMDDHH
				String cardTime = year + cardTimeMark;
				long cardTimeL = TimerTools.date2long(cardTime, "yyyyMMddHHmm"); // M1区读取出的时间

				StringBuffer startbuf = new StringBuffer();
				startbuf.append(year)
				.append(CodeUtils.leftZeroFill(month + "", 2))
				.append(CodeUtils.leftZeroFill(day + "", 2))
				.append(CodeUtils.leftZeroFill(startTime, 5));
				String startTimeStr = startbuf.toString();
				long startTimeL = TimerTools.date2long(startTimeStr,
						"yyyyMMddHH:mm");

				StringBuffer endbuf = new StringBuffer();
				endbuf.append(year)
				.append(CodeUtils.leftZeroFill(month + "", 2))
				.append(CodeUtils.leftZeroFill(day + "", 2))
				.append(CodeUtils.leftZeroFill(endTime, 5));
				String endTimeStr = endbuf.toString();
				long endTimeL = TimerTools.date2long(endTimeStr,
						"yyyyMMddHH:mm");

				StringBuffer endZerobuf = new StringBuffer();
				endZerobuf.append(year)
				.append(CodeUtils.leftZeroFill(month + "", 2))
				.append(CodeUtils.leftZeroFill(day + "", 2));
				String endZeroStr = endZerobuf.toString();
				long endZeroL = TimerTools.date2long(endZeroStr, "yyyyMMdd");

				Long now = TimerTools.getdatatimelong();
				String nowStr = TimerTools.getDateFormat(now + "",
						"yyyyMMddHH:mm");
				if (endTimeL < startTimeL) {

					// if() 大于等于结束时间日期零时零分 并且小于等于结束时间 开始时间减去一天
					if (endZeroL <= now && now <= endTimeL) {
						startTimeL -= dayLong;
					} else {
						endTimeL += dayLong;
					}
				}

				if (startTimeL <= cardTimeL && cardTimeL < endTimeL) {
					LogUtil.e("TIME","times="+times + " : workTimes="+workTimes + " :(times < workTimes)="+(times < workTimes));
					if (times < workTimes&&times<255) {
//						if (times < workTimes) {
						LogUtil.i("可以就餐！");

						iscan = true;
						isUp = true;
						errorCoed = IfCanResult.OK;
						msg = "可以就餐！";

					} else {
						LogUtil.e("超过就餐次数！");

						iscan = false;
						isUp = true;
						errorCoed = IfCanResult.EXCESS;
						msg = "超过就餐次数！";
					}
				} else {

					iscan = true;
					isUp = false;
					errorCoed = IfCanResult.OK;
					msg = "可以就餐！";
				}
			}
		} else {
			// 不让吃
			iscan = false;
			isUp = false;
			errorCoed = IfCanResult.ILLEGAL;
			msg = "非法卡！";
		}
		return new IfCanResult(iscan, isUp, errorCoed, msg, times);
	}

	/**
	 * 判断当前网络类型
	 * 
	 * 0 :当前网络不可用 1: wifi 2: cmwap 3: cmnet
	 * 
	 * @param context
	 * @return
	 * 
	 * */
	public static int inNetWorkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return 0;
		} else {
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
			if (networkInfo != null) {
				for (int i = 0; i < networkInfo.length; i++) {
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						NetworkInfo info = networkInfo[i];
						if (info.getType() == ConnectivityManager.TYPE_WIFI) {
							return 1;
						} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
							if ("cmwap".equals(info.getExtraInfo())
									|| "cmwap:gsm".equals(info.getExtraInfo())) {
								return 2;
							}
							return 3;
						}
					}
				}
			}
		}
		return 0;
	}

}
