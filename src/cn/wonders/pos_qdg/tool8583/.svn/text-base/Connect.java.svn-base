package cn.wonders.pos_qdg.tool8583;

import org.apache.commons.collections.map.ListOrderedMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.Constant;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.SPUtils;

public class Connect {
	protected static final int PIJIESUCCESS = 0;
	protected static final int SIGNINSUCCESS = 2;
	TcpClient client = new TcpClient();
	RequestDataPacket request = new RequestDataPacket();
	ResponseDataPacket resp = new ResponseDataPacket();
	byte[] content = {};
	Context context;
	private void init(Context context,String tpdu) {
		CloudposApplication.getInstance().setrespCode("99");
		
		client.setHost(SPUtils.getString(context, SPUtils.CUP_IP, ""));
		client.setPort(SPUtils.getString(context, SPUtils.CUP_POR, ""));
		client.setReadTimeout(Constant.READTIMEOUT);
		client.setConnetTimeout(Constant.CONNETTIMEOUT);
		content = ProcessHelper.addTPDU(content,
				CodeUtils.str2CompressBcd(tpdu));// 增加TPDU
		content = ProcessHelper.addHead(content,
				CodeUtils.str2CompressBcd(Constant.HEADER));// 增加报文头
	}

	/**
	 * 9.4.1 签到操作
	 * 
	 * @param type
	 * @param field11
	 * @param field41
	 * @param field42
	 * @param field60
	 * @param field63
	 * @return 
	 */
	public boolean signIn(String type, String field11, String field41,
			String field42, String field60, String field62, String field63,
			Context context,String tpdu) {
		try {
			init(context,tpdu);
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);// 消息类型
			lmap.put(Constant.FIELD11, field11);// 受卡方系统跟踪号 11域
			lmap.put(Constant.FIELD41, field41);// 受卡机终端标识码 41
			lmap.put(Constant.FIELD42, field42);// 受卡方标识码 42
			lmap.put(Constant.FIELD60, field60);// 自定义域60
			if (field62 != null) {
				lmap.put(Constant.FIELD62, field62);// 自定义域62
			}
			lmap.put(Constant.FIELD63, field63);// 自定义域63
			request.setData(lmap);
			resp = client.sendData(request, content);
			String k1, k2, data, macKey, pici;
			byte[] data2, data3;
			data = resp.getValue(Constant.FIELD62).toString().substring(40, 56);
			pici = resp.getValue(Constant.FIELD60).toString().substring(2, 8);

			String initKey = SPUtils.getString(context, SPUtils.UPK, "");

			k1 = initKey.substring(0, 16);
			k2 = initKey.substring(16);

			data2 = DesECBencrypt.decryptDES(CodeUtils.str2CompressBcd(data),
					CodeUtils.str2CompressBcd(k1));
			data3 = DesECBencrypt.encryptDES(data2,
					CodeUtils.str2CompressBcd(k2));
			macKey = CodeUtils.getHexStr(DesECBencrypt.decryptDES(data3,
					CodeUtils.str2CompressBcd(k1)));
			CloudposApplication.getInstance().setmacKey(macKey);
			CloudposApplication.getInstance().setpici(pici);
			String respCode = resp.getValue(Constant.FIELD39).toString();
			CloudposApplication.getInstance().setrespCode(respCode);
			LogUtil.e("签到应答码：" + respCode);
			if("00".equals(respCode)){
				return true;
			}
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO Auto-generated catch block
		}
		return false;
	}

	/**
	 * 9.4.3 签退操作
	 * 
	 * @param type
	 * @param field11
	 * @param field41
	 * @param field42
	 * @param field60
	 */
	public void signOut(String type, String field11, String field41,
			String field42, String field60,	Context context,String tpdu) {
		String respCode;
		try {
			this.context=context;
			init(context,tpdu);
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);// 消息类型
			lmap.put(Constant.FIELD11, field11);// 受卡方系统跟踪号 11域
			lmap.put(Constant.FIELD41, field41);// 受卡机终端标识码 41
			lmap.put(Constant.FIELD42, field42);// 受卡方标识码 42
			lmap.put(Constant.FIELD60, field60);// 自定义域60
			request.setData(lmap);
			resp = client.sendData(request, content);
			respCode = resp.getValue(Constant.FIELD39).toString();
			CloudposApplication.getInstance().setrespCode(respCode);
			LogUtil.e("签退应答码：" + respCode);
			
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 9.4.4 批结算
	 * 
	 * @param type
	 * @param field11
	 * @param field41
	 * @param field42
	 * @param field48
	 * @param field49
	 * @param field60
	 * @param field63
	 * @param dialog 
	 * @param handler 
	 */
	public void BalanceAccounts(String type, String field11, String field41,
			String field42, String field48, String field49, String field60,
			String field63,	Context context,String tpdu, Handler handler, ProgressDialog dialog) {
		try {
			this.context=context;
			init(context,tpdu);
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);
			lmap.put(Constant.FIELD11, field11);
			lmap.put(Constant.FIELD41, field41);
			lmap.put(Constant.FIELD42, field42);
			lmap.put(Constant.FIELD48, field48);
			lmap.put(Constant.FIELD49, field49);
			lmap.put(Constant.FIELD60, field60);
			lmap.put(Constant.FIELD63, field63);
			request.setData(lmap);
			resp = client.sendData(request, content);
			if (resp!=null) {
				handler.sendEmptyMessage(5);
			}else{
				handler.sendEmptyMessage(6);
			}
			String pici = resp.getValue(Constant.FIELD60).toString();
			LogUtil.e("更新后批次号：" + pici);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 9.4.5 批上送金融交易/批上送结束
	 * 
	 * @param type
	 * @param field11
	 * @param field41
	 * @param field42
	 * @param field48
	 * @param field60
	 */
	public void upLoading(String type, String field11, String field41,
			String field42, String field48, String field60) {
		try {
			// init();
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);
			lmap.put(Constant.FIELD11, field11);
			lmap.put(Constant.FIELD41, field41);
			lmap.put(Constant.FIELD42, field42);
			lmap.put(Constant.FIELD48, field48);
			lmap.put(Constant.FIELD60, field60);
			request.setData(lmap);
			resp = client.sendData(request, content);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 9.4.7 基于PBOC借/贷记IC卡批上送通知交易
	 * 
	 * @param type
	 * @param field2
	 * @param field4
	 * @param field11
	 * @param field22
	 * @param field23
	 * @param field41
	 * @param field42
	 * @param field55
	 * @param field60
	 * @param field62
	 */
	public void upLoadNotification(String type, String field2, String field4,
			String field11, String field22, String field23, String field41,
			String field42, String field55, String field60, String field62) {
		try {
			// init();
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);
			lmap.put(Constant.FIELD2, field2);
			lmap.put(Constant.FIELD4, field4);
			lmap.put(Constant.FIELD11, field11);
			lmap.put(Constant.FIELD22, field22);
			if (field23 != null) {
				lmap.put(Constant.FIELD23, field23);
			}
			lmap.put(Constant.FIELD41, field41);
			lmap.put(Constant.FIELD42, field42);
			lmap.put(Constant.FIELD55, field55);
			lmap.put(Constant.FIELD60, field60);
			lmap.put(Constant.FIELD62, field62);
			request.setData(lmap);
			resp = client.sendData(request, content);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 9.4.9 POS参数传递
	 * 
	 * @param type
	 * @param field41
	 * @param field42
	 * @param field60
	 * @param field62
	 */
	public void parameterTransmit(String type, String field41, String field42,
			String field60, String field62) {
		try {
			// init();
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);
			lmap.put(Constant.FIELD41, field41);
			lmap.put(Constant.FIELD42, field42);
			lmap.put(Constant.FIELD60, field60);
			if (field62 != null) {
				lmap.put(Constant.FIELD62, field62);
			}
			request.setData(lmap);
			resp = client.sendData(request, content);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 9.4.10 回响测试
	 * 
	 * @param type
	 * @param field41
	 * @param field42
	 * @param field60
	 */
	public void echoTest(String type, String field41, String field42,
			String field60) {
		try {
			// init();
			ListOrderedMap lmap = new ListOrderedMap();
			String respCode;
			lmap.put(Constant.MSG_TYPE, type);
			lmap.put(Constant.FIELD41, field41);
			lmap.put(Constant.FIELD42, field42);
			lmap.put(Constant.FIELD60, field60);
			request.setData(lmap);
			resp = client.sendData(request, content);
			respCode = resp.getValue(Constant.FIELD39).toString();
			CloudposApplication.getInstance().setrespCode(respCode);
			LogUtil.e("回响应答码：" + respCode);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 9.4.11 POS状态上送
	 * 
	 * @param type
	 * @param field41
	 * @param field42
	 * @param field60
	 * @param field62
	 */
	public void upLoadState(String type, String field41, String field42,
			String field60, String field62) {
		try {
			// init();
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);
			lmap.put(Constant.FIELD41, field41);
			lmap.put(Constant.FIELD42, field42);
			lmap.put(Constant.FIELD60, field60);
			lmap.put(Constant.FIELD62, field62);
			request.setData(lmap);
			resp = client.sendData(request, content);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 9.4.12 IC卡公钥/参数/TMS参数/卡BIN黑名单下载结束
	 * 
	 * @param type
	 * @param field41
	 * @param field42
	 * @param field60
	 */
	public void downloadTerminate(String type, String field41, String field42,
			String field60) {
		try {
			// init();
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);
			lmap.put(Constant.FIELD41, field41);
			lmap.put(Constant.FIELD42, field42);
			lmap.put(Constant.FIELD60, field60);
			request.setData(lmap);
			resp = client.sendData(request, content);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 9.3.4 基于PBOC借/贷记标准的IC卡离线交易（含小额支付应用）
	 * 
	 * @param type
	 * @param field2
	 * @param field3
	 * @param field4
	 * @param field11
	 * @param field14
	 * @param field22
	 * @param field23
	 * @param field25
	 * @param field26
	 * @param field35
	 * @param field36
	 * @param field41
	 * @param field42
	 * @param field49
	 * @param field52
	 * @param field53
	 * @param field55
	 * @param field60
	 * @param field63
	 * @param field64
	 */
	public void consumeOffline(String type, String field2, String field3,
			String field4, String field11, String field14, String field22,
			String field23, String field25, String field26, String field35,
			String field36, String field41, String field42, String field49,
			String field52, String field53, String field55, String field60,
			String field63, String field64, Context context,String tpdu) {
		
		CloudposApplication.getInstance().setrespCode("99");
		try {
			client.setHost(SPUtils.getString(context, SPUtils.CUP_IP, ""));
			client.setPort(SPUtils.getString(context, SPUtils.CUP_POR, ""));
			client.setReadTimeout(Constant.READTIMEOUT);
			client.setConnetTimeout(Constant.CONNETTIMEOUT);
			content = ProcessHelper.addTPDU(content,
					CodeUtils.str2CompressBcd(tpdu));// 增加TPDU
			content = ProcessHelper.addHead(content,
					CodeUtils.str2CompressBcd(Constant.HEADER));// 增加报文头
			String respCode;
			String dateQingSuan;
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.MSG_TYPE, type);
			lmap.put(Constant.FIELD2, field2);
			lmap.put(Constant.FIELD3, field3);
			lmap.put(Constant.FIELD4, field4);
			lmap.put(Constant.FIELD11, field11);
			if (field14 != null) {
				lmap.put(Constant.FIELD14, field14);
			}
			lmap.put(Constant.FIELD22, field22);
			if (field23 != null) {
				lmap.put(Constant.FIELD23, field23);
			}
			lmap.put(Constant.FIELD25, field25);
			if (field26 != null) {
				lmap.put(Constant.FIELD26, field26);
			}
			if (field35 != null) {
				lmap.put(Constant.FIELD35, field35);
			}
			if (field36 != null) {
				lmap.put(Constant.FIELD36, field36);
			}
			lmap.put(Constant.FIELD41, field41);
			lmap.put(Constant.FIELD42, field42);
			lmap.put(Constant.FIELD49, field49);
			if (field52 != null) {
				lmap.put(Constant.FIELD52, field52);
			}
			if (field53 != null) {
				lmap.put(Constant.FIELD53, field53);
			}
			lmap.put(Constant.FIELD55, field55);
			lmap.put(Constant.FIELD60, field60);
			lmap.put(Constant.FIELD63, field63);
			lmap.put(Constant.FIELD64, field64);
			request.setData(lmap);
			resp = client.sendData(request, content);
			respCode = resp.getValue(Constant.FIELD39).toString();
			dateQingSuan = resp.getValue(Constant.FIELD15).toString();
			CloudposApplication.getInstance().setrespCode(respCode);
			CloudposApplication.getInstance().setdateQingSuan(dateQingSuan);
			LogUtil.e("离线上送应答码：" + respCode);
			LogUtil.e("离线上送清算日期：" + dateQingSuan);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] pack55Field(String tag9F26, String tag9F27, String tag9F10,
			String tag9F37, String tag9F36, String tag95, String tag9A,
			String tag9C, String tag9F02, String tag5F2A, String tag82,
			String tag9F1A, String tag9F03, String tag9F33, String tag9F74,
			String tag8A) {
		byte[] sendByte = null;
		try {
			ListOrderedMap lmap = new ListOrderedMap();
			lmap.put(Constant.TAG_9F26, tag9F26);
			lmap.put(Constant.TAG_9F27, tag9F27);
			lmap.put(Constant.TAG_9F10, tag9F10);
			lmap.put(Constant.TAG_9F37, tag9F37);
			lmap.put(Constant.TAG_9F36, tag9F36);
			lmap.put(Constant.TAG_95, tag95);
			lmap.put(Constant.TAG_9A, tag9A);
			lmap.put(Constant.TAG_9C, tag9C);
			lmap.put(Constant.TAG_9F02, tag9F02);
			lmap.put(Constant.TAG_5F2A, tag5F2A);
			lmap.put(Constant.TAG_82, tag82);
			lmap.put(Constant.TAG_9F1A, tag9F1A);
			lmap.put(Constant.TAG_9F03, tag9F03);
			lmap.put(Constant.TAG_9F33, tag9F33);
			if (tag9F74 != null) {
				lmap.put(Constant.TAG_9F74, tag9F74);
			}
			lmap.put(Constant.TAG_8A, tag8A);

			request.setData(lmap);
			Process8583Packet process = new Process8583Packet();
			sendByte = process.pack55field(request);
		} catch (ProcessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sendByte;
	}
}
