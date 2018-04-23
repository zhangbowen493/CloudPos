package com.wd.liandidemo.RF;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import cn.wonders.pos_qdg.util.LogUtil;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.card.MifareDriver.OnResultListener;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesUtil;

/*
 * 射频卡驱动 获取卡客都设备名称
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-7-19 上午11:12:35
 */
public class RFCardDriversUtil {

	public RFDriversListener mListener;
	RFCardReader instance;

	public RFCardDriversUtil(RFDriversListener listener) {
		this.mListener = listener;
		instance = RFCardReader.getInstance();
	}

	public RFCardReader getCardReader(){
		if(instance==null){
			instance = RFCardReader.getInstance();
		}
		return instance;
	}
	
	/***
	 * 读取卡设备列表 返回list 列表
	 * 
	 * @param rfCardSample
	 */

	public void SearchCardDervices() {
		LogUtil.i("---------SearchCardDervices");
		try {
			instance.searchCard(new RFCardReader.OnSearchListener() {
				@Override
				public void onCrash() {
					// 失败
					// onDeviceServiceCrash();
					// StartServce();
					mListener.onCrash(RFDriversListener.SEARCH);
				}

				@Override
				public void onFail(int error) {
					// 失败
					// stopSearch(BuzzerTools.STATE_FAIL);
					LogUtil.e("查询错误 - " + getErrorDescription(error));
					mListener.onFild(RFDriversListener.SEARCH,error, getErrorDescription(error));
				}

				@Override
				public void onCardPass(int cardType) {
					Map<Integer ,String> map = new HashMap<Integer, String>();
					// 选择正确的卡片设备
					switch (cardType) {
					case S50_CARD:
						map.put(S50_CARD, "S50");
					case S70_CARD:
						map.put(S70_CARD, "S70");
					case CPU_CARD:
						map.put(CPU_CARD, "PRO");
					case PRO_CARD:
						map.put(PRO_CARD, "PRO");
					case S50_PRO_CARD: // 这种类型的卡片也可以使用S50的驱动程序。
						map.put(S50_PRO_CARD, "PRO");
					case S70_PRO_CARD: // 这种类型的卡片也可以使用S70的驱动程序。
						map.put(S70_PRO_CARD, "PRO");
						break;
					default:
						// 失败
						LogUtil.e("搜索卡失败,未知的卡片类型!");
						return;
					}
					mListener.onSuccess(RFDriversListener.SEARCH,map);
				}
			});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mListener.onFild(RFDriversListener.SEARCH,0, "寻卡设备激活失败！");
		}
	}
	/**
	 * 使用指定的驱动激活卡，激活成功后通过监听器获取相应的驱动对象。
	 * @param driverName
	 */
	public void activate(String driverName){
		
			try {
				RFCardReader.getInstance().activate(driverName,
						new RFCardReader.OnActiveListener() {
							
							@Override
							public void onCrash() {
								// TODO Auto-generated method stub
								mListener.onCrash(RFDriversListener.ACTIVATE);
							}
							/**
							 * 指定的驱动不支持该卡时触发。 
							 */
							@Override
							public void onUnsupport(String arg0) {
								// TODO Auto-generated method stub
								mListener.onFild(RFDriversListener.ACTIVATE, 0, "指定的驱动不支持该卡");
							}
							/**
							 * 成功激活时触发。
							 */
							@Override
							public void onCardActivate(RFDriver driver) {
								// TODO Auto-generated method stub
								LogUtil.i("卡驱动激活成功!");
								mListener.onSuccess(RFDriversListener.ACTIVATE, driver);
							}
							/**
							 * 激活失败时触发，一般是一些严重的错误，如坏卡
							 */
							@Override
							public void onActivateError(int arg0) {
								// TODO Auto-generated method stub
								mListener.onFild(RFDriversListener.ACTIVATE, arg0, getErrorDescription(arg0));
							}
						});
			} catch (RequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				mListener.onFild(RFDriversListener.ACTIVATE, 0, "卡激活异常！");
			}
	}
	
	
	
	/**
	 * 停止搜索如果卡开始搜索
	 */
	public void stopSearch() {
		try {
			instance.stopSearch();
		} catch (RequestException e) {
			e.printStackTrace();
			mListener.onFild(RFDriversListener.STOP_SEARCH,0, "停止搜索异常！");
		}
	}
	
	/**
	 * 检测读卡器上是否有卡。
	 * 
	 * @param driver
	 * @return
	 */
	public boolean Exists() {
		try {
			return instance.exists();
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * LED开关
	 * 
	 * @param b
	 * @param led
	 * @return
	 */
	public boolean TurnLed(boolean b, int led) {
		if (b) {
			return instance.turnOnLed(led);
		} else {
			return instance.turnOffLed(led);
		}
	}
	
	
	
	/**
	 * Convert byte[] to hex string.
	 * 
	 * @param data
	 * @param offset
	 * @param len
	 * @param detectEnd
	 * @return
	 */
	public String toHexString(byte[] data, int offset, int len,
			boolean detectEnd) {
		byte[] d = BytesUtil.subBytes(data, offset, len);
		String str = BytesUtil.bytes2HexString(d);
		if (detectEnd && str.endsWith("F")) {
			int i = str.length();
			while (--i > 0) {
				if (str.charAt(i) != 'F') {
					return str.substring(0, i + 1);
				}
			}
			return str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	/**
	 * 错误信息
	 * @param code
	 * @return
	 */
	public String getErrorDescription(int code) {
		switch (code) {
		case OnResultListener.ERROR_ERRPARAM:
			return "参数错误";
		case OnResultListener.ERROR_FAILED:
			return "其他错误（系统错误等）";
		case OnResultListener.ERROR_NOTAGERR:
			return "操作范围内 无卡或者卡片未响应 ";
		case OnResultListener.ERROR_CRCERR:
			return "数据CRC校验错误";
		case OnResultListener.ERROR_AUTHERR:
			return "卡片认证失败";
		case OnResultListener.ERROR_PARITYERR:
			return "数据奇偶校验错误 ";
		case OnResultListener.ERROR_CODEERR:
			return "卡片回送数据内容错误";
		case OnResultListener.ERROR_SERNRERR:
			return "防冲突过程中数据错误 ";
		case OnResultListener.ERROR_NOTAUTHERR:
			return "卡片未认证 ";
		case OnResultListener.ERROR_BITCOUNTERR:
			return "卡片回送数据位数错误";
		case OnResultListener.ERROR_BYTECOUNTERR:
			return "卡片回送数据字节数错误 ";
		case OnResultListener.ERROR_OVFLERR:
			return "卡片回送数据溢出";
		case OnResultListener.ERROR_FRAMINGERR:
			return "数据帧 错误";
		case OnResultListener.ERROR_UNKNOWN_COMMAND:
			return "设备发送非法命令";
		case OnResultListener.ERROR_COLLERR:
			return "多张卡片冲突";
		case OnResultListener.ERROR_RESETERR:
			return "射频卡模块复位失败 ";
		case OnResultListener.ERROR_INTERFACEERR:
			return "射频卡模块接口错误";
		case OnResultListener.ERROR_RECBUF_OVERFLOW:
			return "存放回送数据的接收缓冲区溢出 ";
		case OnResultListener.ERROR_VALERR:
			return "对 Mifare 卡进行数值块操作时数值块值错误 ";
		case OnResultListener.ERROR_ERRTYPE:
			return "错误的卡类型";
		case OnResultListener.ERROR_SWDIFF:
			return "与MifarePro 或者 TypeB 进行数据交换时，卡片回送状态字节SW1! = 0x90, =0x00 SW2.";
		case OnResultListener.ERROR_TRANSERR:
			return "通信错误 ";
		case OnResultListener.ERROR_PROTERR:
			return "卡片返回数据不符合规范要求";
		case OnResultListener.ERROR_MULTIERR:
			return "感应区内多卡存在";
		case OnResultListener.ERROR_NOCARD:
			return "感应区内无卡";
		case OnResultListener.ERROR_CARDEXIST:
			return "卡在仍在感应区";
		case OnResultListener.ERROR_CARDTIMEOUT:
			return "超时无响应";
		case OnResultListener.ERROR_CARDNOACT:
			return "Pro 卡或者 TypeB卡未激活";
		}
		return "未知错误 [" + code + "]";
	}
}
