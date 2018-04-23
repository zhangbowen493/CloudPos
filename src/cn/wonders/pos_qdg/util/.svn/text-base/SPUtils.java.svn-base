package cn.wonders.pos_qdg.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import cn.wonders.pos_qdg.tool8583.CodeUtils;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences管理类
 * 
 * @author kevin
 *
 */
public class SPUtils {
	/**
	 * 保存在手机里面的文件名
	 */
	public static final String FILE_NAME = "cloud_pos_share_data";
	/** 当前操作员 */
	public static final String CURRENT_OPERATOR = "current_operator";
	/** 当前操作员 级别 等级 1 管理员 0 操作员 */
	public static final String CURRENT_OPERATOR_LV = "current_operator_lv";

	/**
	 * 2.1 canteenId 食堂id 2.2 mealType 餐别 2.3 startTime 营业开始时间 2.4 endTime
	 * 营业结束时间 3 syncTime 同步时间
	 */
	/** 食堂配置 更新版本号 **/
	public static final String ST_VERSION = "st_version";

	/** 卡种费率 更新版本号 **/
	public static final String Rate_VERSION = "rate_version";

	/** 黑名单 更新版本号 **/
	public static final String BL_VERSION = "bl_version";
	
	/** 更新版本号 **/
	public static final String YL_VERSION = "yl_version";

	/**
	 * transCurrencyCode 交易货币代码 Char 3 sendOrgnFlag 发送机构标识码 Char 11 merType 商户类型
	 * Char 4 acceptFlag 受卡方标识码 Char 15 acceptAddress 受卡方名称地址 Char 40
	 * origTransInfo 原始交易信息 Char 23 msgReasonCode 报文原因代码 Char 4 sglAndDblFlag
	 * 单双信息标志 Char 1 cupsSerialNum cups流水号 Char 9 serverInputCode 服务点输入方式码 Char
	 * 3 版本号
	 */
	/** 交易货币代码 **/
	public static final String YL_TRANSCURRENCYCODE = "yl_trans_currency_code";
	/** 发送机构标识码 **/
	public static final String YL_SENDORGNFLAG = "yl_send_orgn_flag";
	/** 商户类型 **/
	public static final String YL_MERTYPE = "yl_mer_type";
	/** 受卡方标识码 **/
	public static final String YL_ACCEPTFLAG = "yl_acceptFlag";
	/** 受卡方名称地址 **/
	public static final String YL_ACCEPTADDRESS = "yl_accept_address";
	/** 原始交易信息 **/
	public static final String YL_ORIGTRANSINFO = "yl_orig_trans_info";
	/** 报文原因代码 **/
	public static final String YL_MSGREASONCODE = "yl_msg_reason_code";
	/** 单双信息标志 **/
	public static final String YL_SGLANDDBLFLAG = "yl_sgl_and_dbl_flag";
	/** cups流水号 **/
	public static final String YL_CUPSSERIALNUM = "yl_cups_serial_num";
	/** 服务点输入方式码 **/
	public static final String YL_SERVERINPUTCODE = "yl_server_input_code";
	
	/**
	 * 早餐消费配置金额
	 */
	public static final String BREAKFAST_1_MONEY="breakfast_1_money";
	public static final String BREAKFAST_2_MONEY="breakfast_2_money";
	public static final String BREAKFAST_3_MONEY="breakfast_3_money";
	public static final String BREAKFAST_4_MONEY="breakfast_4_money";
	public static final String BREAKFAST_5_MONEY="breakfast_5_money";
	public static final String BREAKFAST_6_MONEY="breakfast_6_money";
	public static final String BREAKFAST_7_MONEY="breakfast_7_money";
	public static final String BREAKFAST_8_MONEY="breakfast_8_money";
	public static final String BREAKFAST_9_MONEY="breakfast_9_money";
	public static final String BREAKFAST_10_MONEY="breakfast_10_money";
	public static final String BREAKFAST_11_MONEY="breakfast_11_money";
	
	/**
	 * pos 参数配置
	 */
	/** 套餐消费 套餐一 价格 **/
	public static final String PACKAGE_1_MONEY = "package_1_money";
	/** 套餐消费 套餐二 价格 **/
	public static final String PACKAGE_2_MONEY = "package_2_money";
	/** 套餐消费 套餐三 价格 **/
	public static final String PACKAGE_3_MONEY = "package_3_money";
	/** 套餐消费 套餐四 价格 **/
	public static final String PACKAGE_4_MONEY = "package_4_money";
	/** 套餐消费 套餐五 价格 **/
	public static final String PACKAGE_5_MONEY = "package_5_money";
	/** 套餐消费 套餐六 价格 **/
	public static final String PACKAGE_6_MONEY = "package_6_money";
	/** 套餐消费 套餐七 价格 **/
	public static final String PACKAGE_7_MONEY = "package_7_money";
	/** 消费页面退出密码 */
	public static final String ConsumptionModelExitPassword = "consumption_model_exit_password";
	/** 终端号 */
	public static final String TerminalNo = "terminal_no";
	/** TPDU头 */
	public static final String TPDU = "tpdu";
	/** 商户号 */
	public static final String ShanghuNo = "shanghu_no";
	/** 消费最大限额 */
	public static final String MaxMoney = "max_money";
	/** 离线上送服务间隔时间 */
	public static final String Service_ShangSong = "600000";
	/** 网络请求地址 */
	public static final String IP = "ip";
	/** 端口 */
	public static final String port = "port";
	/** 银联对应 pos 秘钥 */
	public static final String UPK = "upk";
	/** 银联对应 服务器IP */
	public static final String CUP_IP = "cup_ip";
	/** 银联对应 服务器 端口 */
	public static final String CUP_POR = "cup_por";
	/** 银联对应  终端号 */
	public static final String zhongduanID = "zhongduanID";
	/** 异常交易警报阀值 */
	public static final String ThresholdLevel = "thresholdLevel";

	/** 是否登录 */
	public static final String IS_LOGIN = "isLogin";
	/** 版本名称 */
	public static final String Version_name = "version_name";
	/** 手机imei号 */
	public static final String Phone_imei = "phone_imei";

	/** 网络请求地址 */
	public static final String Account = "account";
	/**
	 * 流水号
	 */
	public static final String serialData="serialData";
	/**
	 * 流水号自加
	 * @param context
	 * @param Data
	 */
	public static void addSerialData(Context context ){
		int s = (Integer) get(context, serialData, 0);
		s = s+1;
		put(context, serialData, s);
	}
	/**
	 * 获取流水号
	 * @param context
	 * @return
	 */
	public static String getSerialData(Context context ){
		int s = (Integer) get(context, serialData, 0);
		
		String data = CodeUtils.leftZeroFill(s+"", 6);
		
		return data;
	}
	/**
	 * 定额消费笔数
	 */
	public static final String CONSUME_TIMES="consumetimes";
	/**
	 * 定额消费次数
	 */
	public static final String CONSUME_TYPE="consumetype";
	/**
	 * 清理限时
	 */
	public static final String CLEAR_TIME = "clear_time";
	/**
	 * 定额消费笔数自加
	 * @param context
	 * @param Data
	 */
	public static void addConsume_Times(Context context ){
		int s = (Integer) get(context, CONSUME_TIMES, 0);
		s = s+1;
		put(context, CONSUME_TIMES, s);
	}
	/**
	 * 获取定额消费笔数
	 * @param context
	 * @return
	 */
	public static String getConsumeTimes(Context context ){
		int s = (Integer) get(context, CONSUME_TIMES, 0);
		
		String data = CodeUtils.leftZeroFill(s+"", 4);
		
		return data;
	}
	
	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 * 
	 * @param context
	 * @param key
	 * @param object
	 */
	public static void put(Context context, String key, Object object) {

		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 * 
	 * @param context
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public static Object get(Context context, String key, Object defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);

		if (defaultObject instanceof String) {
			return sp.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	/*
	 * 获取字符串
	 */
	public static String getString(Context context, String key,
			String defaultObject) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getString(key, defaultObject);
	}

	/**
	 * 移除某个key值已经对应的值
	 * 
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.remove(key);
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 清除所有数据
	 * 
	 * @param context
	 */
	public static void clear(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.clear();
		SharedPreferencesCompat.apply(editor);
	}

	/**
	 * 查询某个key是否已经存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean contains(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.contains(key);
	}

	/**
	 * 返回所有的键值对
	 * 
	 * @param context
	 * @return
	 */
	public static Map<String, ?> getAll(Context context) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
				Context.MODE_PRIVATE);
		return sp.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 * 
	 * @author zhy
	 * 
	 */
	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		/**
		 * 反射查找apply的方法
		 * 
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		/**
		 * 如果找到则使用apply执行，否则使用commit
		 * 
		 * @param editor
		 */
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}

}
