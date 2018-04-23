package com.wd.liandidemo.RF;

import java.util.Map;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFDriver;
	/*
	 * 获取卡可用设备监听
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-7-19 上午11:28:33
 */
public interface RFDriversListener {
	final int SEARCH = 0x00;//寻卡
	final int ACTIVATE = 0x00+1; //激活
	final int M1_Auth = 0x00+2;//M1认证
	final int M1_READ = 0x00+3;//M1读取
	final int M1_WRITE = 0x00+4;//M1写入
	final int M1_HAIT = 0x00+5;//M1停止
	final int STOP_SEARCH = 0x00+6;//寻卡停止
	final int ERROR = 0x00+7;//错误
	final int EXECHANGEAPDU = 0x00+8;//APDU通讯
	
	/**
	 * 失败
	 * @param tag
	 * @param errorCoder
	 * @param s
	 */
	void onFild(int tag ,int errorCoder,String s);
	/**
	 * 成功
	 * @param tag
	 * @param cardDriver
	 */
	void onSuccess(int tag ,RFDriver cardDriver);
	/**
	 * 读取设备名称获取成功
	 * @param string
	 */
	void onSuccess(int tag ,Map<Integer,String> map);
	/**
	 * M1区读取成功
	 * @param tag
	 * @param cardDriver
	 * @param readInfo
	 */
	void onSuccess(int tag ,MifareDriver cardDriver,String readInfo);
	/**
	 * M1区写入成功
	 * @param cardDriver 卡驱动
	 * @param readInfo	信息
	 */
	void onSuccess(int tag ,String readInfo);

	/**
	 * 电子现金消费成功
	 * @param tag
	 * @param cardNumber	卡号
	 * @param resData		电子现金消费第二步返回数据
	 * @param iMoney		传入消费金额
	 */
	void onSuccess(int tag, String cardNumber, String resData,
			int iMoney);
	/**
	 * 设备出现问题
	 * @param tag
	 */
	void onCrash(int tag );
}


