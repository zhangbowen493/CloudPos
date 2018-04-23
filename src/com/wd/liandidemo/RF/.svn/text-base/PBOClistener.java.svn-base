package com.wd.liandidemo.RF;
	/*
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-23 下午1:50:48
 */
public interface PBOClistener {

	public static final int FILD = 0;
	public static final int BLANCE_FILD = 1;
	
	void onFild(int tag,int balance);
	

	/**
	 * 
	 * @param hexString
	 * @param resData
	 * @param cardNumber 卡号
	 */
	void onSuccess( String resData, String cardNumber);
	/**
	 * 
	 * @param hexString
	 * @param resData
	 * @param cardNumber 卡号
	 * @param blace 消费后余额
	 */
	void onSuccess( String resData, String cardNumber,int blace);
	
}


