package com.wd.liandidemo.RF;

import java.util.List;

import android.content.Context;
import cn.wonders.pos_qdg.bean.BlackList;
import cn.wonders.pos_qdg.bean.Constant;
import cn.wonders.pos_qdg.db.BlackListDao;
import cn.wonders.pos_qdg.util.LogUtil;

import com.landicorp.android.eptapi.card.RFCpuCardDriver;
import com.landicorp.android.eptapi.card.RFCpuCardDriver.OnExchangeListener;
import com.landicorp.android.eptapi.exception.RequestException;
import com.landicorp.android.eptapi.utils.BytesBuffer;
import com.landicorp.android.eptapi.utils.BytesUtil;

/*
 * 
 * 电子现金操作工具类
 * 
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-7-20 上午9:49:47
 */
public class RFUtils extends RFCardDriversUtil {

	Context mContext;
	RFCpuCardDriver mDriver;
	private int CARD_SW1;
	private int CARD_SW2;
	/** 卡号 */
	String CardNumber; 
	/**传入参数 -- 消费金额*/
	private int IMoney; 
	/** 卡消费 返回接收数据 */
	private String resData;
	boolean tag = false;

	public RFUtils(Context context, RFDriversListener listener,
			RFCpuCardDriver driver) {
		super(listener);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mDriver = driver;
	}

	/**
	 * 开始执行电子现金消费流程 
	 * @param qMoney	int值 消费金额
	 */
	public void startRead(int qMoney) {
		this.IMoney = qMoney;

		Select_PSE(new NextStep() {
			@Override
			public void invoke() {
				readECBalance(new NextStep() {
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
										Consume1(new NextStep() {
											@Override
											public void invoke() {
												Consume2(new NextStep() {
													@Override
													public void invoke() {
														Consume3(new NextStep() {
															@Override
															public void invoke() {
																// TODO
																mListener
																.onSuccess(RFDriversListener.EXECHANGEAPDU,
																		CardNumber,
																		resData,
																		IMoney
																		);
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

	/**
	 * 选择PSE
	 * @param next
	 */
	protected void Select_PSE(final NextStep next) {
		exechangeApdu(
				BytesUtil
				.hexString2Bytes("00A404000e325041592E5359532E444446303100"),
				new ResponseHandler() {
					@Override
					public void onResponse(byte[] responseData) {
						if ((CARD_SW1 == 97) || (CARD_SW1 == 159)
								|| ((CARD_SW1 == 144) && (CARD_SW2 == 0))) {
							next.invoke();
						} else {
							mListener.onFild(RFDriversListener.EXECHANGEAPDU,
									0, "卡通讯错误！");
						}
					}
				});
	}

	/***
	 * 查询卡号第一步
	 * 
	 * @param next
	 */
	private void checkCardNo1(final NextStep next) {
		exechangeApdu(
				BytesUtil.hexString2Bytes("00A4040008A00000033301010100"),
				new ResponseHandler() {
					@Override
					public void onResponse(byte[] responseData) {
						next.invoke();
					}
				});
	}

	/**
	 * 查询卡号第二步
	 * 
	 * @param next
	 */
	private void checkCardNo2(final NextStep next) {

		exechangeApdu(BytesUtil.hexString2Bytes("00B2011400"),
				new ResponseHandler() {

			@Override
			public void onResponse(byte[] responseData) {
				// TODO Auto-generated method stub
				String hexString = toHexString(responseData, 0,
						responseData.length, true);
				LogUtil.i("卡号信息数据是：" + hexString);
				List decodingTLV = PBOC_TLV.decodingTLV(hexString);
				if (decodingTLV != null && decodingTLV.size() > 0) {
					for (int i = 0; i < decodingTLV.size(); i++) {
						String[] tlv = (String[]) decodingTLV.get(i);
						if ("70".equals(tlv[0])) {
							String CardNumber_str = tlv[2];
							List cardTLV = PBOC_TLV
									.decodingTLV(CardNumber_str);
							for (int j = 0; j < cardTLV.size(); j++) {
								String[] tlv_card = (String[]) cardTLV
										.get(j);
								if ("5A".equals(tlv_card[0])) {
									String info = tlv_card[2];
									CardNumber = info.replace("F", "");
									System.out.println("卡号是："
											+ CardNumber);
									break;
								}
							}
						}
					}
				}

				BlackListDao dao = new BlackListDao(mContext);
				List<BlackList> blackList = dao.getBlackList(
						"card_number", CardNumber);
				if (blackList != null && blackList.size() > 0) {
					mListener.onFild(RFDriversListener.EXECHANGEAPDU,
							-1, "卡号在黑名单中");
				} else {
					next.invoke();
				}
			}
		});
	}

	/**
	 * 电子现金消费第一步
	 * 
	 * @param next
	 */
	String tag_9f38;

	protected void Consume1(final NextStep next) {
		exechangeApdu(
				BytesUtil.hexString2Bytes("00A4040008A00000033301010100"),
				new ResponseHandler() {
					@Override
					public void onResponse(byte[] responseData) {
						if (responseData.length < 10) {
							mListener.onFild(RFDriversListener.EXECHANGEAPDU,
									-1, "卡信息读取出错！");
							return;
						}
						// byte[] serialNo = BytesUtil.subBytes(responseData, 0,
						// 10);
						tag_9f38 = toHexString(responseData, 0,
								responseData.length, true);
						LogUtil.i("tag_9f38 = " + tag_9f38);
						next.invoke();
					}
				});
	}

	/**
	 * 电子现金消费第二步
	 * 
	 * @param next
	 */
	protected void Consume2(final NextStep next) {
		String mQMoney = CodeUtils.leftZeroFill(IMoney+"", 12);
		LogUtil.i("999999997667654444444444======" + mQMoney);

		String sgpo = CodeUtils.test2(tag_9f38, mQMoney);
		int length = sgpo.length() / 2;
		String hexStr = CodeUtils.getHexLen(length);
		String hexStr1 = CodeUtils.getHexLen(length + 2);
		LogUtil.i("+++++++ = "
				+ ("80A80000" + hexStr1 + "83" + hexStr + sgpo + "00"));
		exechangeApdu(
				BytesUtil.hexString2Bytes("80A80000" + hexStr1 + "83" + hexStr
						+ sgpo + "00"), new ResponseHandler() {

					@Override
					public void onResponse(byte[] responseData) {
						resData = toHexString(responseData, 0,
								responseData.length, true);
						LogUtil.i("80A80000 ===" + resData);
						next.invoke();
					}
				});
	}

	/**
	 * 电子现金消费第三步
	 * @param next
	 */
	private void Consume3(final NextStep next) {
		// TODO Auto-generated method stub
		String[] rs = CodeUtils.String2L(resData);
		if ("99".equals(rs[0])) {
			// /同步返回 空参数处理
			for (int i = 1; i < rs.length; i++) {
				byte[] hexString2Bytes = BytesUtil.hexString2Bytes(rs[i]);
				// BytesBuffer bytesBuffer = new BytesBuffer();

				exechangeApdu(hexString2Bytes, new ResponseHandler() {
					@Override
					public void onResponse(byte[] responseData) {
						// TODO Auto-generated method stub

						List decodingTLV = PBOC_TLV.decodingTLV(toHexString(
								responseData, 0, responseData.length, true));
						if (decodingTLV != null && decodingTLV.size() > 0) {
							for (int k = 0; k < decodingTLV.size(); k++) {
								String[] tlv = (String[]) decodingTLV.get(k);
								if ("70".equals(tlv[0])) {
									String CardNumber_str = tlv[2];
									LogUtil.i("CardNumber_str=="
											+ CardNumber_str);
									List cardTLV = PBOC_TLV
											.decodingTLV(CardNumber_str);
									for (int j = 0; j < cardTLV.size(); j++) {
										String[] tlv_card = (String[]) cardTLV
												.get(j);
										if ("8F".equals(tlv_card[0])) {
											String CAIndex = tlv_card[2];
											LogUtil.i("CA索引：" + CAIndex);
											// 生产
											if ("02".equals(CAIndex)
													|| "03".equals(CAIndex)
													|| "04".equals(CAIndex)) {
												tag = true;
											}
											if (Constant.ISDEBUG) {
												// 测试
												if ( "08".equals(CAIndex)
														|| "09".equals(CAIndex)
														|| "0b".equals(CAIndex)
														|| "0B".equals(CAIndex)) {
													tag = true;
												}
											}
										}
									}
								}
							}
						}

					}
				});

				// try {
				// int exchangeApdu = driver.exchangeApdu(hexString2Bytes,
				// bytesBuffer);
				// LogUtil.i("trc", rs[i] + "-exchangeApdu=" + exchangeApdu
				// + "---" + bytesBuffer.toHexString());
				// List decodingTLV = PBOC_TLV.decodingTLV(bytesBuffer
				// .toHexString());
				// if (decodingTLV != null && decodingTLV.size() > 0) {
				// for (int k = 0; k < decodingTLV.size(); k++) {
				// String[] tlv = (String[]) decodingTLV.get(k);
				// if ("70".equals(tlv[0])) {
				// String CardNumber_str = tlv[2];
				// System.out.println("CardNumber_str=="
				// + CardNumber_str);
				// List cardTLV = PBOC_TLV
				// .decodingTLV(CardNumber_str);
				// for (int j = 0; j < cardTLV.size(); j++) {
				// String[] tlv_card = (String[]) cardTLV
				// .get(j);
				// if ("8F".equals(tlv_card[0])) {
				// CAIndex = tlv_card[2];
				// System.out.println("CA索引：" + CAIndex);
				// // 测试
				// // if ( "08".equals(CAIndex)
				// // || "09".equals(CAIndex)
				// // || "0b".equals(CAIndex)
				// // || "0B".equals(CAIndex)) {
				// // tag = true;
				// // }
				// // 生产
				// if ("02".equals(CAIndex)
				// || "03".equals(CAIndex)
				// || "04".equals(CAIndex)) {
				// tag = true;
				// }
				// }
				// }
				// }
				// }
				// }
				// } catch (RequestException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// mClistener.onFild();
				// return;
				// }
			}

			if (tag) {
				next.invoke();
			} else {
				mListener.onFild(RFDriversListener.EXECHANGEAPDU, -1,
						"消费记录读取失败！");
			}
		} else {
			mListener
			.onFild(RFDriversListener.EXECHANGEAPDU, -1, "消费记录数据读取失败！");
		}
	}

	/**
	 * 读取电子现金余额
	 */
	private void readECBalance(final NextStep next) {
		// TODO Auto-generated method stub

		exechangeApdu(BytesUtil.hexString2Bytes("80CA9F7900"),
				new ResponseHandler() {

			@Override
			public void onResponse(byte[] responseData) {
				// TODO Auto-generated method stub
				LogUtil.i("eeeeeee"
						+ toHexString(responseData, 0,
								responseData.length, true));
				String string = toHexString(responseData, 0,
						responseData.length, true);
				String balance = string.substring(6, 18);
				int parseInt = Integer.parseInt(balance);

				if (IMoney > parseInt) {
					mListener.onFild(RFDriversListener.EXECHANGEAPDU,
							-1, "电子现金余额不足！");
				} else {
					next.invoke();
				}
			}
		});
	}

	/**
	 * 设备关闭1
	 */
	public void halt() {

		try {
			mDriver.halt();
		} catch (RequestException e) {
			e.printStackTrace();
			mListener.onFild(RFDriversListener.M1_HAIT, 0, "设备关闭异常");
		}

	}

	/**
	 * apdu操作卡
	 * 
	 * @param apdu
	 */
	private void exechangeApdu(byte[] apdu, final ResponseHandler respHandler) {
		try {
			mDriver.exchangeApdu(apdu, new OnExchangeListener() {

				@Override
				public void onCrash() {
					// TODO Auto-generated method stub
					mListener.onCrash(RFDriversListener.EXECHANGEAPDU);
				}

				/**
				 * 在成功交互数据时触发的方法 注意： 如果传输出错，返回的数据可能会为null。
				 */
				@Override
				public void onSuccess(byte[] responseData) {
					// TODO Auto-generated method stub
					if (respHandler != null) {
						CARD_SW1 = responseData[responseData.length - 2] & 0xff;
						CARD_SW2 = responseData[responseData.length - 1] & 0xff;
						respHandler.onResponse(responseData);
					}
				}

				/**
				 * 激活失败时触发，一般是一些严重的错误，如坏卡。
				 */
				@Override
				public void onFail(int arg0) {
					// TODO Auto-generated method stub
					mListener.onFild(RFDriversListener.EXECHANGEAPDU, arg0,
							getErrorDescription(arg0));
				}
			});
		} catch (RequestException e) {

		}
	}

	/**
	 * 处理一次交换的反回
	 */
	interface ResponseHandler {
		void onResponse(byte[] responseData);
	}

	/**
	 * 操作下一步
	 */
	interface NextStep {
		void invoke();
	}
}
