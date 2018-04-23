package cn.wonders.pos_qdg.MOne;

import android.util.Log;
import cn.wonders.pos_qdg.util.LogUtil;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.MifareDriver.OnReadListener;
import com.landicorp.android.eptapi.card.MifareDriver.OnResultListener;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesUtil;
import com.wd.liandidemo.RF.RFCardDriversUtil;
import com.wd.liandidemo.RF.RFDriversListener;

/*
 * 卡M1区操作工具类
 * M1区块操作性质导致  认证只对单独块有作用   需要读之前要先验证本区块
 * RFCardDriversUtil.SearchCardDervices() >> activate() >> auth() >> read()/write() >> halt()
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-7-19 上午11:09:20
 */
public class MOneUtil extends RFCardDriversUtil {
	MifareDriver mDriver;
	public MOneUtil(RFDriversListener listener,final MifareDriver driver) {
		super(listener);
		if(driver instanceof MifareDriver){
			mListener.onFild(RFDriversListener.ERROR, 0, "非M1读取设备驱动！");
		}
		this.mDriver = driver;
	}

	/**
	 * M1区认证
	 */
	public void auth(final Integer index) {
		Log.i("tag", "----M1 startRead()  ");
		// The driver method can be used only once in the 'onStart' method.
		byte[] keyA = BytesUtil.hexString2Bytes("FFFFFFFFFFFF");
		try {
			mDriver.authBlock(index, MifareDriver.KEY_A, keyA,
					new OnResultListener() {
						@Override
						public void onCrash() {
							// TODO Auto-generated method stub
							Log.i("tag", "M1认证设备服务崩溃-------------------：");
							mListener.onCrash(RFDriversListener.M1_Auth);
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							Log.i("tag", "M1认证成功-------------------：");
							mListener.onSuccess(RFDriversListener.M1_Auth,
									mDriver);
						}

						@Override
						public void onFail(int arg0) {
							// TODO Auto-generated method stub
							Log.i("tag", "M1区认证：" + getErrorDescription(arg0));
							mListener.onFild(RFDriversListener.M1_Auth, arg0,
									getErrorDescription(arg0));
						}
					});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mListener.onFild(RFDriversListener.M1_Auth, 0, "认证异常！");
		}
	}

	/***
	 * m1区读取 区域
	 */
	public void readM1(final Integer index) {
		try {
			mDriver.readBlock(index, new OnReadListener() {
				@Override
				public void onCrash() {
					// TODO Auto-generated method stub
					LogUtil.i("2读取onCrash");
					mListener.onCrash(RFDriversListener.M1_READ);
				}

				@Override
				public void onSuccess(byte[] arg0) {
					// TODO Auto-generated method stub
					Log.i("tag",
							"读取成功-------------------："
									+ BytesUtil.bytes2HexString(arg0));
					mListener.onSuccess(RFDriversListener.M1_READ, mDriver,
							BytesUtil.bytes2HexString(arg0));
				}

				@Override
				public void onFail(int arg0) {
					// TODO Auto-generated method stub
					LogUtil.i("tag", "3读取错误信息：" + getErrorDescription(arg0));
					mListener.onFild(RFDriversListener.M1_READ, arg0,
							getErrorDescription(arg0));
				}
			});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mListener.onFild(RFDriversListener.M1_READ, 0, "读取异常！");
		}
	}

	/**
	 * M1区写入操作
	 * 
	 * @param index
	 * @param driver
	 * @param WriteData
	 */
	public void Write(final Integer index,
			final String WriteData) {
		try {
			mDriver.writeBlock(index, BytesUtil.hexString2Bytes(WriteData),
					new OnResultListener() {

						@Override
						public void onCrash() {
							// TODO Auto-generated method
							LogUtil.i("写入 onCrash()-------------------：");
							mListener.onCrash(RFDriversListener.M1_WRITE);
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method
							LogUtil.i("写入成功-------------------：");
							mListener.onSuccess(RFDriversListener.M1_WRITE,
									"写入成功");
						}

						@Override
						public void onFail(int arg0) {
							// TODO Auto-generated method
							LogUtil.i("写入错误信息：" + getErrorDescription(arg0));
							mListener.onFild(RFDriversListener.M1_WRITE, arg0,
									getErrorDescription(arg0));
						}
					});
		} catch (RequestException e) {
			// TODO Auto-generated catch
			e.printStackTrace();
			mListener.onFild(RFDriversListener.M1_WRITE, 0, "写入异常！");
		}
	}

	/**
	 * 放弃正在执行的卡操作，如果需要再次操作卡，需要从寻卡再次开始
	 * 
	 * @param driver
	 */
	public void halt() {
		// TODO Auto-generated method stub
		try {
			mDriver.halt();
		} catch (RequestException e) {
			// TODO Auto-generated catch
			e.printStackTrace();
			mListener.onFild(RFDriversListener.M1_HAIT, 0, "设备关闭异常");
		}
	}

	
}
