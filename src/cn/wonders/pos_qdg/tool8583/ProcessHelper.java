package cn.wonders.pos_qdg.tool8583;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.util.LogUtil;

/**
 * 
 * 
 * 类名称：ProcessHelper 类描述： 创建人：mzw 创建时间：2016年4月20日 下午2:20:14 修改人：mzw
 * 修改时间：2016年4月20日 下午2:20:14 Modification History:
 * =============================================================================
 * Author Date Description
 * ---------------------------------------------------------------------------
 * =============================================================================
 * 帮助类
 */
public class ProcessHelper {

	/**
	 * 
	 * convertLitterLen 转化小字节序长度
	 * 
	 * @param lenByte
	 * @return int
	 * @exception
	 * @since 1.0.0
	 */
	public static int convertLitterLen(byte[] lenByte) {
		lenByte = reverseArray(lenByte);
		return CodeUtils.hexToDeci(lenByte);
	}

	/**
	 * 
	 * reverseArray 数组反转
	 * 
	 * @param lenByte
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] reverseArray(byte[] lenByte) {
		byte[] newByte = new byte[lenByte.length];
		int index = lenByte.length;
		for (byte b : lenByte) {
			newByte[index - 1] = b;
			index--;
		}
		return newByte;
	}

	/**
	 * 
	 * cutHead 将无用的头信息去除掉
	 * 
	 * @param content
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] cutHeadAndTail(byte[] content) {
		int head = (6 + 5);
		// int tail = 1+1;
		byte[] newByte = new byte[content.length - head];
		System.arraycopy(content, head, newByte, 0, newByte.length);
		return newByte;
	}

	/**
	 * 
	 * unpack8583 处理8583包 解包
	 * 
	 * @param content
	 * @param table
	 * @return ResponseDataPacket
	 * @throws UnsupportedEncodingException
	 * @exception
	 * @since 1.0.0
	 */
	public static ResponseDataPacket unpack8583(byte[] content,
			Packet8583ConvertTable table) {
		if (CloudposApplication.isSignIn) {
			table = Packet8583ConfigSignIn.table;
		}
		ResponseDataPacket packet = new ResponseDataPacket();
		ListOrderedMap meta = table.getTable();
		byte[] msgType = new byte[4];
		System.arraycopy(content, 0, msgType, 0, msgType.length);

		try {
			packet.setValue("msgType", new String(msgType,
					ConstentVar.DEFAULT_CHARSET));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] bitMap = new byte[8];
		System.arraycopy(content, 2, bitMap, 0, 8);
		// 转化为二进制的字符串
		String bit = CodeUtils.byteArrayToBitStr(bitMap);

		byte[] filddata = new byte[content.length - 10];
		System.arraycopy(content, 10, filddata, 0, filddata.length);
		content = filddata;
		LogUtil.d("最终处理域消息：" + CodeUtils.getHexStr(content));
		char bitHead = 0;
		Packet8583Field field = null;
		Field8583Helper helper = new Field8583Helper(null, 0);
		for (int index = 1, len = bit.length() - 1; index < len; index++) {
			bitHead = bit.charAt(index);

			if (bitHead == '1') {// 说明有元素
				for (int i = 0; i < table.getTable().size(); i++) {
					int fildindex = (Integer) table.getTable().get(i);
					if ((index + 1) == fildindex) {
						field = (Packet8583Field) table.getTable().getValue(i);
						helper = getValue(field, content, helper);
						String ret = null;
						switch (field.getFieldCodeType()) {
						case BCD_COMPRESS:
							ret = CodeUtils.bcd2Str((byte[]) helper
									.getRetByteArray());
							LogUtil.d("第" + fildindex + "域" + ret);
							break;
						case BCD_COMPRESS_RIGHT:
							ret = CodeUtils.bcd2Str((byte[]) helper
									.getRetByteArray());
							LogUtil.d("第" + fildindex + "域" + ret);
							break;
						case BCD_UNCOMPRESS:
							ret = CodeUtils.unCompressBcd2Str((byte[]) helper
									.getRetByteArray());
							LogUtil.d("第" + fildindex + "域" + ret);
							break;
						case B:
							ret = CodeUtils.getHexStr(helper.getRetByteArray());
							LogUtil.d("第" + fildindex + "域" + ret);
							break;
						case ASCll:
							try {
								ret = new String(
										(byte[]) helper.getRetByteArray(),
										ConstentVar.DEFAULT_CHARSET);
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							LogUtil.d("第" + fildindex + "域" + ret);
							break;
						default:
							break;
						}
						LogUtil.d("FieldValue:" + ret);
						packet.setValue(field.getFieldKey(), ret);
						break;
					}
				}

			}
		}

		return packet;
	}

	/**
	 * 
	 * getValue 从报文中获取特定的值
	 * 
	 * @param field
	 * @param content
	 * @return Object
	 * @exception
	 * @since 1.0.0
	 */
	public static Field8583Helper getValue(Packet8583Field field,
			byte[] content, Field8583Helper helper) {

		int pos = helper.getPosIncValue();
		byte[] retByteArray = null;
		switch (field.getFieldType()) {
		case A_PADDING_SPACE_RIGHT:
			helper = commonProcess(field, content, helper);
			break;
		case N_PADDING_ZERO_LEFT:
			if (field.getFieldCodeType() == FieldCodeType.BCD_COMPRESS||field.getFieldCodeType() == FieldCodeType.BCD_COMPRESS_RIGHT) {
				helper = commonProcessBCD(field, content, helper);
				break;
			}
			helper = commonProcess(field, content, helper);
			break;
		case S_PADDING_SPACE_RIGHT:
			helper = commonProcess(field, content, helper);
			break;
		case AN_PADDING_SPACE_RIGHT:
			helper = commonProcess(field, content, helper);
			break;
		case ANS_PADDING_SPACE_RIGHT:
			helper = commonProcess(field, content, helper);
			break;
		case AS_PADDING_SPACE_RIGHT:
			helper = commonProcess(field, content, helper);
			break;
		case LL:
			helper = commonProcessVar(field, content, helper, 1);
			break;
		case LLL:
			helper = commonProcessVar(field, content, helper, 2);
			break;

		default:
			break;
		}

		return helper;

	}

	/**
	 * 
	 * commonProcess 固定长度报文处理
	 * 
	 * @param field
	 * @param content
	 * @param helper
	 * @return Field8583Helper
	 * @exception
	 * @since 1.0.0
	 */
	private static Field8583Helper commonProcess(Packet8583Field field,
			byte[] content, Field8583Helper helper) {
		int pos = helper.getPosIncValue();
		byte[] retByteArray = new byte[field.getFieldMaxSize()];

		System.arraycopy(content, pos, retByteArray, 0, retByteArray.length);

		//

		return new Field8583Helper(retByteArray, pos + field.getFieldMaxSize());
	}

	/**
	 * 
	 * commonProcess 固定长度报文处理（BCD）
	 * 
	 * @param field
	 * @param content
	 * @param helper
	 * @return Field8583Helper
	 * @exception
	 * @since 1.0.0
	 */
	private static Field8583Helper commonProcessBCD(Packet8583Field field,
			byte[] content, Field8583Helper helper) {
		int pos = helper.getPosIncValue();
		int length = field.getFieldMaxSize() / 2;
		if (field.getFieldMaxSize() % 2 != 0) {
			length += 1;
		}
		byte[] retByteArray = new byte[length];

		System.arraycopy(content, pos, retByteArray, 0, retByteArray.length);

		//

		return new Field8583Helper(retByteArray, pos + length);
	}

	/**
	 * 
	 * commonProcessVar 处理变长报文域
	 * 
	 * @param field
	 * @param content
	 * @param helper
	 * @return Field8583Helper
	 * @exception
	 * @since 1.0.0
	 */
	private static Field8583Helper commonProcessVar(Packet8583Field field,
			byte[] content, Field8583Helper helper, int len) {
		int pos = helper.getPosIncValue();

		// 先获取长度
		byte[] lenByte = new byte[len];
		System.arraycopy(content, pos, lenByte, 0, lenByte.length);

		pos += len;
		LogUtil.d("fieldlen=" + CodeUtils.bcd2Str(lenByte));
		int fieldLen = Integer.parseInt(CodeUtils.bcd2Str(lenByte));
		if (field.getFieldCodeType() == FieldCodeType.BCD_COMPRESS
				&& fieldLen >= 2) {
			if (fieldLen % 2 != 0) {
				fieldLen = fieldLen / 2 + 1;
			} else {

				fieldLen /= 2;
			}
		}

		byte[] retByteArray = new byte[fieldLen];

		//

		System.arraycopy(content, pos, retByteArray, 0, retByteArray.length);
		return new Field8583Helper(retByteArray, pos + fieldLen);
	}

	/**
	 * 
	 * addHead 增加固定的报文头
	 * 
	 * @param content
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] addHead(byte[] content, byte[] header) {
		content = CodeUtils.concatArray(content, header);
		return content;
	}

	/**
	 * 
	 * addHead 增加TPDU
	 * 
	 * @param content
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] addTPDU(byte[] content, byte[] TPDU) {
		content = CodeUtils.concatArray(content, TPDU);
		return content;
	}

	/**
	 * 
	 * addField 打8583包 替换长度
	 * 
	 * @param request
	 *            发送报文信息对象
	 * @param table
	 *            8583域
	 * @param subfieldtable
	 *            8583子域
	 * @return byte[] 报文头
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] addField(RequestDataPacket request,
			Packet8583ConvertTable table, Packet8583ConvertTable subfieldtable,
			byte[] head) throws IOException {
		if (CloudposApplication.isSignIn) {
			table = Packet8583ConfigSignIn.table;
		}
		byte[] bodybyte = null;
		String msgType = (String) request.getData().get("msgType");
		if (msgType != null && !"".equals(msgType) && msgType.length() == 4) {
			// 消息类型
			bodybyte = CodeUtils.str2CompressBcd(request.getData()
					.get("msgType").toString());
			LogUtil.d("消息类型" + CodeUtils.bcd2Str(bodybyte));

		} else {
			throw new RuntimeException("报文组包异常！--没有消息类型或消息类型长度错误！");
		}
		// 8583报文初始位图:128位01字符串
		String initBitMap = CodeUtils.getInit64BitMap();
		byte[] bitmap = null;
		byte[] fildbyte = {};
		byte[] codelength = {};
		String temp1, temp2, temp4;
		byte[] newByte = new byte[3];
		int temp3;
		for (int i = 0; i < request.getData().size(); i++) {
			for (int j = 0; j < table.getTable().size(); j++) {
				temp1 = (String) request.getData().get(i);
				temp2 = ((Packet8583Field) table.getTable().getValue(j))
						.getFieldKey();
				temp3 = ((Packet8583Field) table.getTable().getValue(j))
						.getFieldIndex();
				if (temp2.equals(temp1)) {
					// 域有值的修改相应的位图
					initBitMap = CodeUtils
							.change16bitMapFlag(temp3, initBitMap);
					// 处理变长域
					if (((Packet8583Field) table.getTable().getValue(j))
							.getFieldType().equals(FieldType.LL)
							|| ((Packet8583Field) table.getTable().getValue(j))
									.getFieldType().equals(FieldType.LLL)) {
						int length = request.getData().getValue(i).toString()
								.length();
						if (length <= ((Packet8583Field) table.getTable()
								.getValue(j)).getFieldMaxSize()) {
							switch (((Packet8583Field) table.getTable()
									.getValue(j)).getFieldType()) {
							case LL:
								temp4 = Integer.toString(length);
								temp4 = CodeUtils.leftZeroFill(temp4, 2);
								codelength = CodeUtils.str2hex(temp4);
								fildbyte = CodeUtils.concatArray(fildbyte,
										codelength);
								break;
							case LLL:
								//55域特殊处理
								if (temp3==55) {
									length/=2;
								}
								temp4 = Integer.toString(length);
								temp4 = CodeUtils.leftZeroFill(temp4, 4);
								codelength = CodeUtils.str2hex(temp4);
								fildbyte = CodeUtils.concatArray(fildbyte,
										codelength);
								
								break;
							default:
								break;
							}

							switch (((Packet8583Field) table.getTable()
									.getValue(j)).getFieldCodeType()) {
							case BCD_UNCOMPRESS:
								fildbyte = CodeUtils.concatArray(
										fildbyte,
										CodeUtils.str2UnCompressBcd(request
												.getData().getValue(i)
												.toString()));
								LogUtil.d("第" + temp3 + "域"
										+ CodeUtils.getHexStr(fildbyte));
								break;
							case ASCll:
								try {
									fildbyte = CodeUtils
											.concatArray(
													fildbyte,
													request.getData()
															.getValue(i)
															.toString()
															.getBytes(
																	ConstentVar.DEFAULT_CHARSET));
									LogUtil.d("第" + temp3 + "域"
											+ CodeUtils.getHexStr(fildbyte));
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							case SUBFIELD:
								fildbyte = CodeUtils.concatArray(
										fildbyte,
										CodeUtils.str2CompressBcd(request
												.getData().getValue(i)
												.toString()));
								LogUtil.d("第" + temp3 + "域"
										+ CodeUtils.getHexStr(fildbyte));
								break;
							case BCD_COMPRESS:
								fildbyte = CodeUtils.concatArray(
										fildbyte,
										CodeUtils.str2CompressBcd(request
												.getData().getValue(i)
												.toString()));
								byte[] a = CodeUtils.str2CompressBcd(request
										.getData().getValue(i).toString());
								LogUtil.d("第" + temp3 + "域"
										+ CodeUtils.getHexStr(fildbyte));
								break;
							case B:
								fildbyte = CodeUtils
										.concatArray(fildbyte, CodeUtils
												.hexStringToByte(CodeUtils
														.binary2hex(request
																.getData()
																.getValue(i)
																.toString())));
								break;
							default:
								break;
							}
						}
						// 处理定长域
					} else {
						int length = request.getData().getValue(i).toString()
								.length();
						if (length == ((Packet8583Field) table.getTable()
								.getValue(j)).getFieldMaxSize()) {
							switch (((Packet8583Field) table.getTable()
									.getValue(j)).getFieldCodeType()) {
							case BCD_COMPRESS:
								fildbyte = CodeUtils.concatArray(
										fildbyte,
										CodeUtils.str2CompressBcd(request
												.getData().getValue(i)
												.toString()));
								LogUtil.d("第" + temp3 + "域"
										+ CodeUtils.getHexStr(fildbyte));
								break;
							case BCD_COMPRESS_RIGHT:
								fildbyte = CodeUtils.concatArray(
										fildbyte,
										CodeUtils.str2CompressBcdRight(request
												.getData().getValue(i)
												.toString()));
								LogUtil.d("第" + temp3 + "域"
										+ CodeUtils.getHexStr(fildbyte));
								break;
							case BCD_UNCOMPRESS:
								fildbyte = CodeUtils.concatArray(
										fildbyte,
										CodeUtils.str2UnCompressBcd(request
												.getData().getValue(i)
												.toString()));
								LogUtil.d("第" + temp3 + "域"
										+ CodeUtils.getHexStr(fildbyte));
								break;
							case ASCll:
								try {
									fildbyte = CodeUtils
											.concatArray(
													fildbyte,
													request.getData()
															.getValue(i)
															.toString()
															.getBytes(
																	ConstentVar.DEFAULT_CHARSET));
									LogUtil.d("第" + temp3 + "域"
											+ CodeUtils.getHexStr(fildbyte));
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
							default:
								break;
							}
						}
					}
				}
			}
		}

		// 转换位图为byte数组
		String bitmap2 = CodeUtils.binary2hex(initBitMap);
		bitmap = CodeUtils.toByteArray(bitmap2);
		LogUtil.d("转换后位图：" + bitmap2);
		// 添加位图
		bodybyte = CodeUtils.concatArray(bodybyte, bitmap);
		// 添加剩余2-63域
		bodybyte = CodeUtils.concatArray(bodybyte, fildbyte);
		if (request.getData().containsKey("mac")) {
			byte[] macContent = new byte[8];
			try {
				// 计算安全报文校验mac
				String mac;
				mac = MacUtils.generateMacUP(bodybyte,
						CodeUtils.str2CompressBcd(CloudposApplication.macKey));
				macContent = mac.getBytes();
				LogUtil.d("64域："+CodeUtils.getHexStr(macContent));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 添加mac
			bodybyte = CodeUtils.concatArray(bodybyte, macContent);
		}
		// 添加报文头
		if (head != null) {
			bodybyte = CodeUtils.concatArray(head, bodybyte);
		}
		String length = Integer.toHexString(bodybyte.length);
		LogUtil.d("长度" + bodybyte.length);
		length = CodeUtils.leftZeroFill(length, 4);
		byte[] bodylength = CodeUtils.toByteArray(length);

		bodybyte = CodeUtils.concatArray(bodylength, bodybyte);
		LogUtil.e("发出的完整8583报文："+CodeUtils.getHexStr(bodybyte));
		return bodybyte;
	}

	/**
	 * 
	 * addField 打55域包
	 * 
	 * @param request
	 *            发送报文信息对象
	 * @param table
	 *            8583域
	 * @param subfieldtable
	 *            8583子域
	 * @return byte[] 报文头
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] add55Field(RequestDataPacket request,
			Packet8583ConvertTable table) throws IOException {
		Map<String, byte[]> field55 = ListOrderedMap.decorate(new HashMap());
		byte[] fildbyte = {};

		for (int i = 0; i < request.getData().size(); i++) {

			if (request.getData().get(i).equals("9F26")) {
				field55.put(
						"9F26",
						CodeUtils.str2CompressBcd(request.getData().get("9F26")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F27")) {
				field55.put(
						"9F27",
						CodeUtils.str2CompressBcd(request.getData().get("9F27")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F10")) {
				field55.put(
						"9F10",
						CodeUtils.str2CompressBcd(request.getData().get("9F10")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F37")) {
				field55.put(
						"9F37",
						CodeUtils.str2CompressBcd(request.getData().get("9F37")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F36")) {
				field55.put(
						"9F36",
						CodeUtils.str2CompressBcd(request.getData().get("9F36")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("95")) {
				field55.put(
						"95",
						CodeUtils.str2CompressBcd(request.getData().get("95")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9A")) {
				field55.put(
						"9A",
						CodeUtils.str2CompressBcd(request.getData().get("9A")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9C")) {
				field55.put(
						"9C",
						CodeUtils.str2CompressBcd(request.getData().get("9C")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F02")) {
				field55.put(
						"9F02",
						CodeUtils.str2CompressBcd(request.getData().get("9F02")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("5F2A")) {
				field55.put(
						"5F2A",
						CodeUtils.str2CompressBcd(request.getData().get("5F2A")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("82")) {
				field55.put(
						"82",
						CodeUtils.str2CompressBcd(request.getData().get("82")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F1A")) {
				field55.put(
						"9F1A",
						CodeUtils.str2CompressBcd(request.getData().get("9F1A")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F03")) {
				field55.put(
						"9F03",
						CodeUtils.str2CompressBcd(request.getData().get("9F03")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F33")) {
				field55.put(
						"9F33",
						CodeUtils.str2CompressBcd(request.getData().get("9F33")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F1E")) {
				field55.put(
						"9F1E",
						CodeUtils.str2CompressBcd(request.getData().get("9F1E")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("84")) {
				field55.put(
						"84",
						CodeUtils.str2CompressBcd(request.getData().get("84")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F09")) {
				field55.put(
						"9F09",
						CodeUtils.str2CompressBcd(request.getData().get("9F09")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F41")) {
				field55.put(
						"9F41",
						CodeUtils.str2CompressBcd(request.getData().get("9F41")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F34")) {
				field55.put(
						"9F34",
						CodeUtils.str2CompressBcd(request.getData().get("9F34")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F35")) {
				field55.put(
						"9F35",
						CodeUtils.str2CompressBcd(request.getData().get("9F35")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F63")) {
				field55.put(
						"9F63",
						CodeUtils.str2CompressBcd(request.getData().get("9F63")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("9F74")) {
				field55.put(
						"9F74",
						CodeUtils.str2CompressBcd(request.getData().get("9F74")
								.toString()));
				continue;
			}
			if (request.getData().get(i).equals("8A")) {
				field55.put(
						"8A",
						CodeUtils.str2CompressBcd(request.getData().get("8A")
								.toString()));
				continue;
			}
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		try {
			for (Map.Entry<String, byte[]> entry : field55.entrySet()) {
				byte[] tb = CodeUtils.hexStringToByte(entry.getKey());
				byte[] vb = entry.getValue();
				if (vb == null || vb.length == 0) {
					continue;
				}
				byte[] lb = new byte[] {};
				int fieldlength = vb.length;
				if (fieldlength > 127) {
					lb = new byte[] { (byte) 0x82, (byte) fieldlength };
				} else {
					lb = new byte[] { (byte) fieldlength };
				}
				os.write(tb);
				os.write(lb);
				os.write(vb);
			}
			fildbyte = CodeUtils.concatArray(fildbyte, os.toByteArray());
		} finally {
			if (os != null) {
				os.close();
			}
		}
		LogUtil.d(CodeUtils.getHexStr(fildbyte));
		return fildbyte;
	}

	/**
	 * 
	 * addTail 增加尾部
	 * 
	 * @param content
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] addTail(byte[] content) {
		byte[] endpacket = new byte[] { 0x03 };
		content = CodeUtils.concatArray(content, endpacket);

		byte[] lrc = CodeUtils.lrcComputer(content);

		return CodeUtils.concatArray(content, lrc);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {

		RequestDataPacket request = new RequestDataPacket();
		ResponseDataPacket resp = new ResponseDataPacket();
		byte[] content = {};
		byte[] TPDU = { 0x12, 0x34, 0x56, 0x78, 0x23 };
		byte[] header = { 0x12, 0x34, 0x56, 0x78, 0x23, 0x45 };
		content = ProcessHelper.addTPDU(content, TPDU);// 增加TPDU
		content = ProcessHelper.addHead(content, header);// 增加报文头
		System.out.print("head:" + CodeUtils.bcd2Str(content));

		ListOrderedMap lmap = new ListOrderedMap();
		lmap.put("msgType", "0200");// 消息类型
		lmap.put("mainAccount", "1234567890123456789");// 主账号 2域
		lmap.put("tradeNum", "311000");// 交易处理码
		lmap.put("tradeAmount", "000000001000");// 交易金额（分）
		lmap.put("posSerial", "123456");// 受卡方系统跟踪号
		lmap.put("recCardTime", "155045");// 受卡方所在地时间
		lmap.put("recCardDate", "0621");// 受卡方所在日期
		lmap.put("respCode", "11");// 应答码 39域
		lmap.put("cardAcceptorTerminalCode", "999000000000");// 受卡机终端标识码 41域
		lmap.put("cardAcceptorCode", "111111000000001");// 受卡方标识码 42域
		lmap.put("ICCardInfo", "111111000000001");// IC卡数据域 55域
		// 子域信息
		lmap.put("9F26",
				"111111000000001111111000000001111111000000001111111000000001");// 55域
		lmap.put("9F27", "111100000");// 55域 子域
		lmap.put("9F10", "11111");// 55域 子域
		lmap.put("5F2A", "1234567890");// 55域 子域

		lmap.put("reservedPrivate", "01160518");// 自定义域 60域

		request.setData(lmap);

		try {
			content = addField(request, Packet8583Config.table,
					Packet8583SubfieldConfig.subfieldtable, content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for (int i = 0; i < content.length; i++) {
		// System.out.print(content[i]);
		// }
	}

}
