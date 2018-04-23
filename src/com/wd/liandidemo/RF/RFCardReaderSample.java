package com.wd.liandidemo.RF;


import android.content.Context;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.RFCpuCardDriver;
import com.landicorp.android.eptapi.card.RFDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesUtil;

/**
 * 关于射频卡操作
 * @author chenwei
 *
 */
public abstract class RFCardReaderSample extends AbstractSample {
	
	
	
	public RFCardReaderSample(Context context) {
		super(context);
	}
	
	/**
	 * 开始搜索。这张卡你想搜索可以是一个非接触式读卡器卡或CPU卡(TypeB卡).
	 */
	public void searchCard(RFCardReader.OnSearchListener listener) {
		try {
			RFCardReader.getInstance().searchCard(listener);
		} catch (RequestException e) {
			e.printStackTrace();
			onDeviceServiceCrash();
		}
	}
	
	/**
	 * 停止搜索如果卡开始搜索
	 */
	public void stopSearch() {
		try {
			RFCardReader.getInstance().stopSearch();
		} catch (RequestException e) {
			e.printStackTrace();
			onDeviceServiceCrash();
		}
	}
	
	/**
	 * 显示射频卡信息 
	 * @param cardInfo
	 */
	public abstract void displayRFCardInfo(String cardInfo);
}
