package cn.wonders.pos_qdg.MOne;

import android.util.Log;

import cn.wonders.pos_qdg.util.LogUtil;

import com.landicorp.android.eptapi.card.MifareDriver;
import com.landicorp.android.eptapi.card.MifareDriver.OnReadListener;
import com.landicorp.android.eptapi.card.MifareDriver.OnResultListener;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesUtil;

/**
 * 
 * This card reader can do some data operations.
 * 
 * @author chenwei
 * 
 */
public abstract class MifareOneCardReader {
	private MifareDriver driver;

	public MifareOneCardReader(MifareDriver cardDriver) {
		this.driver = cardDriver;
	}

	// protected abstract void onDeviceServiceException();
	//
	// protected abstract void showErrorMessage(String msg);
	//
	// protected abstract void onDataRead(String info);

	/**
	 * Start read card 开始读卡
	 */
	public void startRead(final Integer index, final M1Listener listener) {
		// The driver method can be used only once in the 'onStart' method.
		byte[] keyA = BytesUtil.hexString2Bytes("FFFFFFFFFFFF");
		try {
			driver.authBlock(index, MifareDriver.KEY_A, keyA,
					new OnResultListener() {
						@Override
						public void onCrash() {
							// TODO Auto-generated method stub
							listener.onCrash("authBlock");
						}
						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							try {
								driver.readBlock(index, new OnReadListener() {
									@Override
									public void onCrash() {
										// TODO Auto-generated method stub
										listener.onCrash("readBlock");
									}
									@Override
									public void onSuccess(byte[] arg0) {
										// TODO Auto-generated method stub
										listener.onSuccess(BytesUtil
												.bytes2HexString(arg0));
									}
									@Override
									public void onFail(int arg0) {
										// TODO Auto-generated method stub
										LogUtil.i("tag", "3读取错误信息："
												+ getErrorDescription(arg0));
										listener.onFild(getErrorDescription(arg0));
									}
								});
							} catch (RequestException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								listener.onFild("");
							}
						}

						@Override
						public void onFail(int arg0) {
							// TODO Auto-generated method stub
							Log.i("tag", "4错误信息：" + getErrorDescription(arg0));
							listener.onFild(getErrorDescription(arg0));
						}

						public String getErrorDescription(int code) {
							switch (code) {
							case ERROR_ERRPARAM:
								return "Parameter error";
							case ERROR_FAILED:
								return "Other error(OS error,etc)";
							case ERROR_NOTAGERR:
								return "Operating range without card or card is not responding";
							case ERROR_CRCERR:
								return "The data CRC parity error";
							case ERROR_AUTHERR:
								return "Authentication failed";
							case ERROR_PARITYERR:
								return "Data parity error";
							case ERROR_CODEERR:
								return "The wrong card response data content";
							case ERROR_SERNRERR:
								return "Data in the process of conflict protection error";
							case ERROR_NOTAUTHERR:
								return "Card not authentication";
							case ERROR_BITCOUNTERR:
								return "The length of data bits card return is wrong";
							case ERROR_BYTECOUNTERR:
								return "The length of data bytes card return is wrong";
							case ERROR_OVFLERR:
								return "The card return data overflow";
							case ERROR_FRAMINGERR:
								return "Data frame error";
							case ERROR_UNKNOWN_COMMAND:
								return "The terminal sends illegal command";
							case ERROR_COLLERR:
								return "Multiple cards conflict";
							case ERROR_RESETERR:
								return "RF card module reset failed";
							case ERROR_INTERFACEERR:
								return "RF card module interface error";
							case ERROR_RECBUF_OVERFLOW:
								return "Receive buffer overflow";
							case ERROR_VALERR:
								return "Numerical block operation on the Mifare card, block error";
							case ERROR_ERRTYPE:
								return "Card type of error";
							case ERROR_SWDIFF:
								return "Data exchange with MifarePro card or TypeB card, card loopback status byte SW1! = 0x90, =0x00 SW2.";
							case ERROR_TRANSERR:
								return "Communication error";
							case ERROR_PROTERR:
								return "The card return data does not meet the requirements of the protocal";
							case ERROR_MULTIERR:
								return "There are multiple cards in the induction zone";
							case ERROR_NOCARD:
								return "There is no card in the induction zone";
							case ERROR_CARDEXIST:
								return "The card is still in the induction zone";
							case ERROR_CARDTIMEOUT:
								return "Response timeout";
							case ERROR_CARDNOACT:
								return "Pro card or TypeB card is not activated";
							}
							return "unknown error [" + code + "]";
						}

					});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			listener.onFild("");
		}
	}

	/**
	 * Start read card 开始写卡
	 */
	/**
	 * M1区写卡操作
	 * @param index	 	写卡位置
	 * @param WriteData	写入数据 十六位   十六进制字符串
	 * @param listener
	 */
	public void startWrite(final Integer index,final String WriteData , final M1Listener listener) {
		LogUtil.e("M1区写入数据:"+WriteData);
		// The driver method can be used only once in the 'onStart' method.
		byte[] keyA = BytesUtil.hexString2Bytes("FFFFFFFFFFFF");
		try {
			driver.authBlock(index, MifareDriver.KEY_A, keyA,
					new OnResultListener() {
						@Override
						public void onCrash() {
							// TODO Auto-generated method stub
							listener.onCrash("authBlock");
						}

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub
							try {
								driver.writeBlock(index, BytesUtil
										.hexString2Bytes(WriteData),
										new OnResultListener() {

											@Override
											public void onCrash() {
												// TODO Auto-generated method
												listener.onFild("设备服务停止");
											}

											@Override
											public void onSuccess() {
												// TODO Auto-generated method
												listener.onSuccess("写入成功");
												try {
													driver.halt();
												} catch (RequestException e) {
													// TODO Auto-generated catch
													e.printStackTrace();
												}
											}

											@Override
											public void onFail(int arg0) {
												// TODO Auto-generated method
												listener.onFild("-------"+getErrorDescription(arg0));
											}
										});
							} catch (RequestException e) {
								// TODO Auto-generated catch
								listener.onFild("写入失败!");
								e.printStackTrace();
							}

						}

						@Override
						public void onFail(int arg0) {
							// TODO Auto-generated method stub
							Log.i("tag", "4错误信息：" + getErrorDescription(arg0));
							listener.onFild(getErrorDescription(arg0));
						}

						public String getErrorDescription(int code) {
							switch (code) {
							case ERROR_ERRPARAM:
								return "Parameter error";
							case ERROR_FAILED:
								return "Other error(OS error,etc)";
							case ERROR_NOTAGERR:
								return "Operating range without card or card is not responding";
							case ERROR_CRCERR:
								return "The data CRC parity error";
							case ERROR_AUTHERR:
								return "Authentication failed";
							case ERROR_PARITYERR:
								return "Data parity error";
							case ERROR_CODEERR:
								return "The wrong card response data content";
							case ERROR_SERNRERR:
								return "Data in the process of conflict protection error";
							case ERROR_NOTAUTHERR:
								return "Card not authentication";
							case ERROR_BITCOUNTERR:
								return "The length of data bits card return is wrong";
							case ERROR_BYTECOUNTERR:
								return "The length of data bytes card return is wrong";
							case ERROR_OVFLERR:
								return "The card return data overflow";
							case ERROR_FRAMINGERR:
								return "Data frame error";
							case ERROR_UNKNOWN_COMMAND:
								return "The terminal sends illegal command";
							case ERROR_COLLERR:
								return "Multiple cards conflict";
							case ERROR_RESETERR:
								return "RF card module reset failed";
							case ERROR_INTERFACEERR:
								return "RF card module interface error";
							case ERROR_RECBUF_OVERFLOW:
								return "Receive buffer overflow";
							case ERROR_VALERR:
								return "Numerical block operation on the Mifare card, block error";
							case ERROR_ERRTYPE:
								return "Card type of error";
							case ERROR_SWDIFF:
								return "Data exchange with MifarePro card or TypeB card, card loopback status byte SW1! = 0x90, =0x00 SW2.";
							case ERROR_TRANSERR:
								return "Communication error";
							case ERROR_PROTERR:
								return "The card return data does not meet the requirements of the protocal";
							case ERROR_MULTIERR:
								return "There are multiple cards in the induction zone";
							case ERROR_NOCARD:
								return "There is no card in the induction zone";
							case ERROR_CARDEXIST:
								return "The card is still in the induction zone";
							case ERROR_CARDTIMEOUT:
								return "Response timeout";
							case ERROR_CARDNOACT:
								return "Pro card or TypeB card is not activated";
							}
							return "unknown error [" + code + "]";
						}

					});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			listener.onFild("");
		}
	}


	public void halt() {
		// TODO Auto-generated method stub
		try {
			driver.halt();
		} catch (RequestException e) {
			// TODO Auto-generated catch
			e.printStackTrace();
		}
	}

}
