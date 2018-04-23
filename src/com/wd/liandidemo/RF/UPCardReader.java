package com.wd.liandidemo.RF;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import cn.wonders.pos_qdg.bean.BlackList;
import cn.wonders.pos_qdg.bean.Constant;
import cn.wonders.pos_qdg.db.BlackListDao;
import cn.wonders.pos_qdg.util.LogUtil;

import com.landicorp.android.eptapi.card.RFCpuCardDriver;
import com.landicorp.android.eptapi.device.RFCardReader;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesBuffer;
import com.landicorp.android.eptapi.utils.BytesUtil;

/**
 * UP card is a kind of mobile phone chip card. The sample code is that we use
 * in real project.
 * 
 * @author chenwei
 * 
 *         3、刷卡交易日志 3.1、读取M1日志————》保留读取3个扇区数据的日志 3.2、读取卡号5A————》保留读取记录文件，不再保留
 *         解析每个tag的日志 3.3、读取电子现余额————》只显示解析后的余额
 *         3.4、脱机消费交易————》1、整理格式：在发送和返回数据前加注释；2、其他解析处理不再打日志。
 *         3.5、写卡操作————》只打印写入卡片的数据。
 * 
 */
public abstract class UPCardReader extends RFCpuCardDriver.OnExchangeListener {
	private RFCpuCardDriver driver;
	private ResponseHandler respHandler;
	private int CARD_SW1;
	private int CARD_SW2;
	private byte[] serialNo;
	@SuppressWarnings("unused")
	private String cardName;
	private String pan;
	private String track2;
	private String track3;
	private String expiredDate;
	private Context mContext;

	public UPCardReader(Context context, RFCpuCardDriver driver) {
		this.mContext = context;
		this.driver = driver;
	}

	@Override
	public void onFail(int errorCode) {
		showErrorMessage("UP CARD READ FAIL - "
				+ getErrorDescription(errorCode));
	}

	@Override
	protected boolean checkResult(int result) {
		return super.checkResult(result);
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

	@Override
	public void onSuccess(byte[] responseData) {
		// Save the sw1 and sw2 after current exchange.
		if (respHandler != null) {
			CARD_SW1 = responseData[responseData.length - 2] & 0xff;
			CARD_SW2 = responseData[responseData.length - 1] & 0xff;
			respHandler.onResponse(responseData);
		}
	}

	@Override
	public void onCrash() {
		onServiceCrash();
	}

	private PBOClistener mClistener;

	public void startRead(int qMoney, PBOClistener clistener) {
		this.mClistener = clistener;
		this.IMoney = qMoney;
		detect(new NextStep() {
			@Override
			public void invoke() {
				checkCardNo1(new NextStep() {
					@Override
					public void invoke() {
						// TODO Auto-generated method stub
						checkCardNo2(new NextStep() {
							@Override
							public void invoke() {
								// TODO Auto-generated method stub
								readBeforeECBalance(new NextStep() {
									@Override
									public void invoke() {
										xiaofei1(new NextStep() {
											@Override
											public void invoke() {
												xiaofei2(new NextStep() {
													@Override
													public void invoke() {
														xiaofei3(new NextStep() {
															@Override
															public void invoke() {
																// TODO
																// Auto-generated
																readAfterECBalance(new NextStep() {
																	@Override
																	public void invoke() {
																		// TODO
																		// Auto-generated
																		// method
																		// stub
																		mClistener
																				.onSuccess(
																						resData,
																						CardNumber,
																						AfterBlance);
																	}
																});
															}
														});
													}
												});
											}
										});
									}
								});
							}
						});
					}
				});
			}
		});
	}

	private String cardPassNumb = "00A4040008A00000033301010100";
	String resp;

	/**
	 * 检测是否是UP card
	 * Detect if the card is UP card or not.
	 * 
	 * @param next
	 */
	protected void detect(final NextStep next) {
		exechangeApdu("00A404000e325041592E5359532E444446303100",
				new ResponseHandler() {
					@Override
					public void onResponse(byte[] responseData) {
						LogUtil.e("银行卡信息："
								+ toHexString(responseData, 0,
										responseData.length, true));
						resp = getAIDTag(toHexString(responseData, 0,
								responseData.length, true));
						if (resp == null) {
							showErrorMessage("未识别的卡");
							mClistener.onFild(PBOClistener.FILD, 0);
						} else {
							cardPassNumb = "00A4040008" + resp + "00";

							LogUtil.i("ucr", "异型卡CARD_SW1 = " + CARD_SW1
									+ "CARD_SW2 = " + CARD_SW2);
							if ((UPCardReader.this.CARD_SW1 == 97)
									|| (UPCardReader.this.CARD_SW1 == 159)
									|| ((UPCardReader.this.CARD_SW1 == 144) && (UPCardReader.this.CARD_SW2 == 0))) {
								next.invoke();
							} else {
								showErrorMessage("The card you insert is not a UP card!");
							}
						}
					}
				});
	}

	public static String getAIDTag(String str) {
		List decodingTLV = PBOC_TLV.decodingTLV(str);
		for (int i = 0; i < decodingTLV.size(); i++) {
			String[] tlv = (String[]) decodingTLV.get(i);
			if (tlv[0].equals("6F")) {
				String tag6F_str = tlv[2];
				List card6FTLV = PBOC_TLV.decodingTLV(tag6F_str);
				for (int j = 0; j < card6FTLV.size(); j++) {
					String[] tag6F_tlv = (String[]) card6FTLV.get(j);
					if (tag6F_tlv[0].equals("A5")) {
						String tagA5_str = tag6F_tlv[2];
						List cardA5TLV = PBOC_TLV.decodingTLV(tagA5_str);
						for (int k = 0; k < cardA5TLV.size(); k++) {
							String[] tagA5_tlv = (String[]) cardA5TLV.get(k);
							if (tagA5_tlv[0].equals("BF0C")) {
								String tagBF0C_str = tagA5_tlv[2];
								List cardBF0CTLV = PBOC_TLV
										.decodingTLV(tagBF0C_str);
								for (int l = 0; l < cardBF0CTLV.size(); l++) {
									String[] tagBF0C_tlv = (String[]) cardBF0CTLV
											.get(l);
									if ("61".equals(tagBF0C_tlv[0])) {
										String tag61_str = tagBF0C_tlv[2];
										List card61TLV = PBOC_TLV
												.decodingTLV(tag61_str);
										for (int m = 0; m < card61TLV.size(); m++) {
											String[] tag61_tlv = (String[]) card61TLV
													.get(m);
											if ("4F".equals(tag61_tlv[0])) {
												return tag61_tlv[2];
											} else {
												continue;
											}
										}
									} else {
										continue;
									}
								}
							} else {
								continue;
							}
						}
					} else {
						continue;
					}
				}
			} else {
				continue;
			}
		}
		return null;
	}

	private String extendString(String str) {
		try {
			byte[] gbk = str.getBytes("GBK");
			return BytesUtil.bytes2HexString(gbk);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	/**
	 * Read serial no from card.
	 * 
	 * @param next
	 */
	private String tag_9f38;

	protected void xiaofei1(final NextStep next) {
		LogUtil.d("消费一发送数据：" + cardPassNumb);
		exechangeApdu(cardPassNumb, new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				if (responseData.length < 10) {
					showErrorMessage("card sn read fail!");
					return;
				}
				serialNo = BytesUtil.subBytes(responseData, 0, 10);
				tag_9f38 = toHexString(responseData, 0, responseData.length,
						true);
				LogUtil.d("消费一返回数据：" + tag_9f38);
				next.invoke();
			}
		});
	}

	/**
	 * Read card id from card.
	 * 
	 * @param next
	 */
	private int IMoney;
	private String[] rs;

	protected void xiaofei2(final NextStep next) {

		String mQMoney = CodeUtils.leftZeroFill(IMoney + "", 12);

		String sgpo = CodeUtils.test2(tag_9f38, mQMoney);
		int length = sgpo.length() / 2;
		String hexStr = CodeUtils.getHexLen(length);
		String hexStr1 = CodeUtils.getHexLen(length + 2);

		LogUtil.e("消费二发送数据："
				+ ("80A80000" + hexStr1 + "83" + hexStr + sgpo + "00"));
		exechangeApdu("80A80000" + hexStr1 + "83" + hexStr + sgpo + "00",
				new ResponseHandler() {

					@Override
					public void onResponse(byte[] responseData) {

						resData = toHexString(responseData, 0,
								responseData.length, true);
						LogUtil.e("消费二接收数据：" + resData);

						rs = CodeUtils.String2L(resData);

						next.invoke();
					}
				});
	}

	/***
	 * 查询卡号第一步
	 * 
	 * @param next
	 */
	private void checkCardNo1(final NextStep next) {

		exechangeApdu(cardPassNumb, new ResponseHandler() {

			@Override
			public void onResponse(byte[] responseData) {
				// TODO Auto-generated method stub
				LogUtil.e("checkCardNo1:"
						+ toHexString(responseData, 0, responseData.length,
								true));
				next.invoke();
			}
		});
	}

	String CardNumber;

	/**
	 * 查询卡号第二步
	 * 
	 * @param next
	 */
	private void checkCardNo2(final NextStep next) {
		LogUtil.d("查询卡号发送数据：00B2011400");
		exechangeApdu("00B2011400", new ResponseHandler() {

			@Override
			public void onResponse(byte[] responseData) {
				// TODO Auto-generated method stub
				String hexString = toHexString(responseData, 0,
						responseData.length, true);
				LogUtil.e("查询卡号接收数据：" + hexString);
				List decodingTLV = PBOC_TLV.decodingTLV(hexString);
				if (decodingTLV != null && decodingTLV.size() > 0) {
					for (int i = 0; i < decodingTLV.size(); i++) {
						String[] tlv = (String[]) decodingTLV.get(i);
						if ("70".equals(tlv[0])) {
							String CardNumber_str = tlv[2];
							List cardTLV = PBOC_TLV.decodingTLV(CardNumber_str);
							for (int j = 0; j < cardTLV.size(); j++) {
								String[] tlv_card = (String[]) cardTLV.get(j);
								if ("5A".equals(tlv_card[0])) {
									String info = tlv_card[2];
									CardNumber = info.replace("F", "");
									LogUtil.i("卡号是：" + CardNumber);
									break;
								}
							}
						}
					}
				}

				BlackListDao dao = new BlackListDao(mContext);
				List<BlackList> blackList = dao.getBlackList("card_number",
						CardNumber);
				if (blackList != null && blackList.size() > 0) {
					mClistener.onFild(PBOClistener.FILD, BeforeBlance);

				} else {
					next.invoke();
				}
			}
		});
	}

	/** 卡消费 返回接收数据 */
	private String resData;
	int indxe = 0;
	String CAIndex;
	private boolean isTradingSuccess = false;

	private void xiaofei3(final NextStep next) {
		// TODO Auto-generated method stub
		if ("99".equals(rs[0])) {
			// /同步返回 空参数处理
			for (int i = 1; i < rs.length; i++) {
				byte[] hexString2Bytes = BytesUtil.hexString2Bytes(rs[i]);
				BytesBuffer bytesBuffer = new BytesBuffer();
				try {
					int exchangeApdu = driver.exchangeApdu(hexString2Bytes,
							bytesBuffer);
					LogUtil.d(exchangeApdu + "---读取记录发送数据：" + rs[i]
							+ "读取记录接收数据：" + bytesBuffer.toHexString());
					List decodingTLV = PBOC_TLV.decodingTLV(bytesBuffer
							.toHexString());
					if (decodingTLV != null && decodingTLV.size() > 0) {
						for (int k = 0; k < decodingTLV.size(); k++) {
							String[] tlv = (String[]) decodingTLV.get(k);
							if ("70".equals(tlv[0])) {
								String CardNumber_str = tlv[2];
								LogUtil.i("CardNumber_str==" + CardNumber_str);
								List cardTLV = PBOC_TLV
										.decodingTLV(CardNumber_str);
								for (int j = 0; j < cardTLV.size(); j++) {
									String[] tlv_card = (String[]) cardTLV
											.get(j);
									if ("8F".equals(tlv_card[0])) {
										CAIndex = tlv_card[2];
										// 生产
										if ("02".equals(CAIndex)
												|| "03".equals(CAIndex)
												|| "04".equals(CAIndex)) {
											isTradingSuccess = true;
										}
										if (Constant.ISDEBUG) {
											// 测试
											if ("08".equals(CAIndex)
													|| "09".equals(CAIndex)
													|| "0b".equals(CAIndex)
													|| "0B".equals(CAIndex)) {
												isTradingSuccess = true;
											}
										}
									}
								}
							}
						}
					}
				} catch (RequestException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					mClistener.onFild(PBOClistener.FILD, BeforeBlance);
					return;
				}
			}
			if (isTradingSuccess) {
				next.invoke();
			} else {
				mClistener.onFild(PBOClistener.FILD, BeforeBlance);
			}
		} else {
			if (isTradingSuccess) {
				next.invoke();
			} else {
				mClistener.onFild(PBOClistener.FILD, BeforeBlance);
			}
		}
	}

	private int BeforeBlance = 0;

	/**
	 * 读取消费前电子现金余额
	 */
	private void readBeforeECBalance(final NextStep next) {
		// TODO Auto-generated method stub
		try {
			driver.exchangeApdu(BytesUtil.hexString2Bytes("80CA9F7900"),
					new RFCpuCardDriver.OnExchangeListener() {
						@Override
						public void onCrash() {
							// TODO Auto-generated method stub
						}

						@Override
						public void onSuccess(byte[] responseData) {
							// TODO Auto-generated method stub
							String string = toHexString(responseData, 0,
									responseData.length, true);
							String balance = string.substring(6, 18);
							BeforeBlance = Integer.parseInt(balance);
							LogUtil.e("电子现金余额：" + BeforeBlance + " 本次消费金额："
									+ IMoney);
							if (BeforeBlance < IMoney) {
								mClistener.onFild(PBOClistener.BLANCE_FILD,
										BeforeBlance);
							} else {
								next.invoke();
							}
						}

						@Override
						public void onFail(int arg0) {
							// TODO Auto-generated method stub
							if (isTradingSuccess) {
								next.invoke();
							} else {
								mClistener.onFild(PBOClistener.FILD,
										BeforeBlance);
							}
						}
					});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (isTradingSuccess)
				next.invoke();
		}
	}

	private int AfterBlance = 0;

	/**
	 * 读取消费后电子现金余额
	 */
	private void readAfterECBalance(final NextStep next) {
		// TODO Auto-generated method stub
		try {
			driver.exchangeApdu(BytesUtil.hexString2Bytes("80CA9F7900"),
					new RFCpuCardDriver.OnExchangeListener() {
						@Override
						public void onCrash() {
							// TODO Auto-generated method stub
						}

						@Override
						public void onSuccess(byte[] responseData) {
							// TODO Auto-generated method stub
							String string = toHexString(responseData, 0,
									responseData.length, true);
							String balance = string.substring(6, 18);
							AfterBlance = Integer.parseInt(balance);
							LogUtil.e("消费后电子现金余额：" + AfterBlance + " 本次消费金额："
									+ IMoney);

							next.invoke();

						}

						@Override
						public void onFail(int arg0) {
							// TODO Auto-generated method stub
							LogUtil.i("余额读取：" + getErrorDescription(arg0));
							mClistener.onFild(PBOClistener.FILD, AfterBlance);
						}
					});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 设备关闭1
	 */
	public void halt() {

		try {
			driver.halt();
		} catch (RequestException e) {
			onServiceCrash();
		}

	}

	/**
	 * Read card info and notify.
	 * 
	 * @param next
	 */
	protected void readCardInfo(final NextStep next) {
		final String readTime = new SimpleDateFormat("yyyyMMddHHmmss")
				.format(new Date());
		exechangeApdu("80F8020008" + readTime + "80", new ResponseHandler() {
			@Override
			public void onResponse(byte[] responseData) {
				if (responseData.length < 116) {
					showErrorMessage("card info read fail!");
					return;
				}
				cardName = gbk(responseData, 0, 20);
				pan = toHexString(responseData, 20, 10, true);

				int track2Len;
				int track3Len;
				try {
					track2Len = Integer.parseInt(BytesUtil
							.bytes2HexString(new byte[] { responseData[30] }));
					track3Len = Integer.parseInt(BytesUtil
							.bytes2HexString(new byte[] { responseData[50],
									responseData[51] }));
				} catch (NumberFormatException e) {
					showErrorMessage("card info read fail!");
					return;
				}
				track2 = toHexString(responseData, 31, 20, false).substring(0,
						track2Len);
				track3 = toHexString(responseData, 52, 54, false).substring(0,
						track3Len);
				expiredDate = toHexString(responseData, 104, 2, false);

				onDataRead(pan, track2, track3, expiredDate, serialNo, readTime);

				LogUtil.i("卡信息：cardName=" + cardName + "\n pan=" + pan
						+ "\n pan=" + pan + "\n track2=" + track2
						+ "\n track3=" + track3 + "\n expiredDate="
						+ expiredDate + "\n serialNo=" + serialNo
						+ "\n readTime=" + readTime);

				// BlackListDao dao = new BlackListDao(mContext);
				// List<BlackList> blackList = dao.getBlackList("card_number",
				// pan);
				// if(blackList!=null && blackList.size()>0){
				// mClistener.onFild();
				// }else{
				next.invoke();
				// }
			}
		});
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
	private String toHexString(byte[] data, int offset, int len,
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
	 * Convert byte[] to GBK String
	 * 
	 * @param b
	 * @param offset
	 * @param len
	 * @return
	 */
	private String gbk(byte[] b, int offset, int len) {
		try {
			return new String(BytesUtil.subBytes(b, offset, len), "GBK");
		} catch (UnsupportedEncodingException e) {
		}
		;
		return null;
	}

	/**
	 * To operate the card through the exchange of apdu.
	 * 
	 * @param apdu
	 */
	private void exechangeApdu(String apdu, ResponseHandler h) {
		this.respHandler = h;
		exechangeApdu(BytesUtil.hexString2Bytes(apdu));
	}

	/**
	 * To operate the card through the exchange of apdu.
	 * 
	 * @param apdu
	 */
	private void exechangeApdu(byte[] apdu) {
		try {
			driver.exchangeApdu(apdu, this);
		} catch (RequestException e) {
			onServiceCrash();
		}
	}

	/**
	 * Show error message in reading process.
	 * 
	 * @param msg
	 */
	protected abstract void showErrorMessage(String msg);

	protected abstract void onServiceCrash();

	/**
	 * Handle final result include all data readed.
	 * 
	 * @param pan
	 * @param track2
	 * @param track3
	 * @param expiredDate
	 * @param serialNo
	 * @param readTime
	 */
	protected abstract void onDataRead(String pan, String track2,
			String track3, String expiredDate, byte[] serialNo, String readTime);

	/**
	 * Handle the response of once exchanging.
	 * 
	 * @author chenwei
	 * 
	 */
	interface ResponseHandler {
		void onResponse(byte[] responseData);
	}

	/**
	 * Each card operation means a step.
	 * 
	 * @author chenwei
	 * 
	 */
	interface NextStep {
		void invoke();
	}
}