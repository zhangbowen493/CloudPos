package com.wd.liandidemo.RF;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.wonders.pos_qdg.util.APPTools;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.StringUtils;
import cn.wonders.pos_qdg.util.TimerTools;




/**
 *
 *
 * 类名称：CodeUtils 类描述： 创建人：mzw 创建时间：2016年4月20日 上午11:29:23 修改人：mzw 修改时间：2016年4月20日
 * 上午11:29:23 Modification History:
 * =============================================================================
 * Author Date Description
 * ---------------------------------------------------------------------------
 * =============================================================================
 *
 * @version 1.0.0
 *
 *          进制转化
 */

public class CodeUtils {
	/**应用ID*/
	public static final String M1_APPID = "appId";
	/**状态*/
	public static final String M1_STATUS = "status";
	/**卡种*/
	public static final String M1_CARDTYPE = "cardType";
	/**员工ID*/
	public static final String M1_STAFFID = "staffId";
	/**员工姓名*/
	public static final String M1_STAFFNAME = "staffName";
	/**上次刷卡时间*/
	public static final String M1_DATE = "date";
	/**餐别*/
	public static final String M1_CB = "cbName";
	/**优惠次数*/
	public static final String M1_TIMES = "times";
	
	/**
	 * 解析M1区信息  
	 * 其中第十区 格式为  MMddHHmmxx    XX 为优惠次数
	 */
	public static Map<String,String> getM1InfoNew(Map<String,String> map){
		Map<String,String> retMap = new HashMap<String, String>();
		String str_8 = map.get("8");
		String str_9 = map.get("9");
		String str_10 = map.get("10");
		String appId = str_8.substring(0, 4);
		String status = str_8.substring(4, 6);
		String cardType = str_8.substring(6, 8);
		String staffId = str_8.substring(8, 14);
		
		str_9=str_9.replace("2020", "");
		String staffName = "";
		for (int i = 0; i < str_9.length()/4; i++) {
			staffName = staffName + (char)Integer.parseInt(str_9.substring(i*4, (i+1)*4), 16);
		}
		
		String data_10 = str_10.substring(0, 8);
		String times_10 = str_10.substring(8, 10);
		
		
		LogUtil.i("M1_APPID="+appId
				+"M1_STATUS="+status
				+"M1_CARDTYPE="+cardType
				+"M1_STAFFID="+staffId
				+"M1_STAFFNAME="+staffName
				+"M1_DATE="+data_10
				+"M1_TIMES="+times_10
				);
		
		retMap.put(M1_APPID, appId); //应用ID
		retMap.put(M1_STATUS, status); //状态
		retMap.put(M1_CARDTYPE, cardType);//卡种
		retMap.put(M1_STAFFID, staffId);//员工ID
		retMap.put(M1_STAFFNAME, staffName);//员工姓名
		retMap.put(M1_DATE, data_10);//上次就餐时间
		retMap.put(M1_TIMES, times_10);//优惠次数
		return retMap;
	}
	
	/**
	 * 解析M1区信息  
	 * 其中第十区 格式为  MMDDCB
	 * @param map
	 * @return
	 */
	public static Map<String,String> getM1Info(Map<String,String> map){
		Map<String,String> retMap = new HashMap<String, String>();
		String str_8 = map.get("8");
		String str_9 = map.get("9");
		String str_10 = map.get("10");
		String appId = str_8.substring(0, 4);
		String status = str_8.substring(4, 6);
		String cardType = str_8.substring(6, 8);
		String staffId = str_8.substring(8, 14);
		//abcdef12 202020202020202020202020
		str_9=str_9.replace("2020", "");
		String staffName = "";
		for (int i = 0; i < str_9.length()/4; i++) {
			staffName = staffName + (char)Integer.parseInt(str_9.substring(i*4, (i+1)*4), 16);
		}
		
		String data_10 = str_10.substring(0, 4);
		String cb_10 = str_10.substring(4, 6);
		
		retMap.put(M1_APPID, appId); //应用ID
		retMap.put(M1_STATUS, status); //状态
		retMap.put(M1_CARDTYPE, cardType);//卡种
		retMap.put(M1_STAFFID, staffId);//员工ID
		retMap.put(M1_STAFFNAME, staffName);//员工姓名
		retMap.put(M1_DATE, data_10);//上次就餐时间
		retMap.put(M1_CB, cb_10);//餐别
		return retMap;
	}
	
	public static String getHexLen(int num){
		String numstr = Integer.toHexString(num);
		if(numstr.length()==1){
			numstr = "0" + numstr;
		}
		return numstr;
	}
	public static String test2(String temp,String stag){
		String tag_9F66 = "21000000";
		String tag_9F02 = stag;
		String tag_9F03 = "000000000000";
		String tag_9F1A = "0156";
		String tag_95 = "0000000000";
		String tag_5F2A = "0156";
		String tag_9A = TimerTools.getDateFormatyyMMdd();
		//9C是交易类型，00代表商品和服务
		String tag_9C = "00";
		//9f37是随机数
		String tag_9F37 = "00000000";
		String tag_DF60 = "00";
		String tag_DF69 = "00";
		Map<String, String> tempmap = new HashMap<String, String>();
		tempmap.put("9F66", tag_9F66);
		tempmap.put("9F02", tag_9F02);
		tempmap.put("9F03", tag_9F03);
		tempmap.put("9F1A", tag_9F1A);
		tempmap.put("95", tag_95);
		tempmap.put("5F2A", tag_5F2A);
		tempmap.put("9A", tag_9A);
		tempmap.put("9C", tag_9C);
		tempmap.put("9F37", tag_9F37);
		tempmap.put("DF60", tag_DF60);
		tempmap.put("DF69", tag_DF69);

		String[] str = temp.split("9F38");

		String paramstrlen = str[1].substring(0, 2);
		String paramstr = str[1].substring(2, 2+Integer.valueOf(paramstrlen,16)*2);
		List tlvList = PBOC_TL.decodingTLV(paramstr);
        String[] result = new String[tlvList.size()];
        String resultstr = "";
        for(int i = 0; i < tlvList.size(); i++) {
            String[] tlv = (String[]) tlvList.get(i);
            resultstr = resultstr + tempmap.get(tlv[0]);
        }
        return resultstr;
	}

	
	
	/**
	 * 将byte数组转化为 十六进制字符串
	 *
	 * @param data
	 * @return String
	 */
	public static String getHexStr(byte[] arrB) {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将ASCII码转换为16进制 字符串
	 *
	 * @param str
	 * @return String
	 */
	public static String convertStringToHex(String str) {
		char[] chars = str.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			hex.append(Integer.toHexString((int) chars[i]));
		}
		return hex.toString();
	}

	/**
	 * 将16进制转换为ASCII
	 *
	 * @param hex
	 * @return String
	 */
	public static String convertHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < hex.length() - 1; i += 2) {
			String output = hex.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}

	/**
	 * 把16进制字符串转换成字节数组
	 *
	 * @param hex
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 把字节数组转换成16进制字符串
	 *
	 * @param bArray
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {
		if (bArray == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * Byte转Bit
	 */
	public static String byteToBit(byte b) {
		return "" + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
				+ (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
				+ (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
				+ (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
	}

	/**
	 * Bit转Byte
	 */
	public static byte BitToByte(String byteStr) {
		int re, len;
		if (null == byteStr) {
			return 0;
		}
		len = byteStr.length();
		if (len != 4 && len != 8) {
			return 0;
		}
		if (len == 8) {// 8 bit处理
			if (byteStr.charAt(0) == '0') {// 正数
				re = Integer.parseInt(byteStr, 2);
			} else {// 负数
				re = Integer.parseInt(byteStr, 2) - 256;
			}
		} else {// 4 bit处理
			re = Integer.parseInt(byteStr, 2);
		}
		return (byte) re;
	}
public static void main(String[] args) {
	String[] string2l = String2L("");
}

	/**
	 * 电子现金消费返回数据获取电子现金余额
	 * @param temp
	 * @return
	 */
	public static int getEC_Blance(String temp){
		List tlvList_77 = PBOC_TLV.decodingTLV(temp);
        String tag_77 = "";
        for(int i = 0; i < tlvList_77.size(); i++) {
            String[] tlv = (String[]) tlvList_77.get(i);
            if("77".equals(tlv[0])){
            	tag_77 = tlv[2];
            }
        }
        System.out.println(tag_77);
        List tlvList_94 = PBOC_TLV.decodingTLV(tag_77);
        String tag_9F10 = "";
        String len_9F10 = "";
        for(int i = 0; i < tlvList_94.size(); i++) {
            String[] tlv_9F10 = (String[]) tlvList_94.get(i);
            if("9F10".equals(tlv_9F10[0])){
            	len_9F10 = tlv_9F10[1];
            	tag_9F10 = tlv_9F10[2];
            }
        }
        
        String HexEC_Blance = tag_9F10.subSequence(20, 30).toString();
        LogUtil.i("电子现金消费后余额="+HexEC_Blance);
        if(!StringUtils.isEmpty(HexEC_Blance)){
        	return Integer.parseInt(HexEC_Blance);
        }
        return -1;
	}

	public static String[] String2L(String temp){
		List tlvList_77 = PBOC_TLV.decodingTLV(temp);
        String tag_77 = "";
        for(int i = 0; i < tlvList_77.size(); i++) {
            String[] tlv = (String[]) tlvList_77.get(i);
            if("77".equals(tlv[0])){
            	tag_77 = tlv[2];
            }
        }
        System.out.println(tag_77);
        List tlvList_94 = PBOC_TLV.decodingTLV(tag_77);
        String tag_94 = "";
        String len_94 = "";
        for(int i = 0; i < tlvList_94.size(); i++) {
            String[] tlv_94 = (String[]) tlvList_94.get(i);
            if("94".equals(tlv_94[0])){
            	len_94 = tlv_94[1];
            	tag_94 = tlv_94[2];
            }
        }
        System.out.println(len_94);
        System.out.println(tag_94);

		String paramstrlen = len_94;
		if("".equals(paramstrlen)||paramstrlen==null){
			String result[] = new String[]{"00"};
			return result;
		}else{
			int length = Integer.valueOf(paramstrlen, 16);
			String paramstr = tag_94;
			int len = 0;
			for (int i = 0; i < length * 2 / 8; i++) {
				int lengthp = test(paramstr.substring(i * 8, (i + 1) * 8)).length;
				len = len + lengthp;
			}
			String result[] = new String[len + 1];
			result[0] = "99";
			int m = 1;
			for (int i = 0; i < length * 2 / 8; i++) {
				int lengthp = test(paramstr.substring(i * 8, (i + 1) * 8)).length;
				for (int j = 0; j < lengthp; j++) {

					result[m] = test(paramstr.substring(i * 8, (i + 1) * 8))[j];
					m++;
				}
			}
			for (int i = 0; i < result.length; i++) {
				System.out.println(result[i]);
			}
			return result;
		}
	}
	public static String[] test(String str){

		String startstr = str.substring(2, 4);
		int startnum = Integer.parseInt(CodeUtils.getHexStr(str2CompressBcd(startstr)));
		String endstr = str.substring(4, 6);
		int endnum = Integer.parseInt(CodeUtils.getHexStr(str2CompressBcd(endstr)));
		String[] ret = new String[endnum-startnum+1];
		byte[] b = str2CompressBcd(str.substring(0,2));
		b[0]|=(byte)0x04;
		String temp = "00b2";
		for (int i = startnum,j=0; i <= endnum; i++,j++) {
			ret[j] = temp + getHexLen(i) + CodeUtils.getHexStr(b) +"00";
		}
		return ret;
	}
	/**
	 *
	 * byteArrayToBitStr
	 *
	 * @param bitMap
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public static String byteArrayToBitStr(byte[] bitMap) {
		StringBuffer buffer = new StringBuffer();
		for (byte b : bitMap) {
			buffer.append(byteToBit(b));
		}
		return buffer.toString();
	}


	/**
	 *
	 * hexToDeci 十六进制转化为十进制
	 * @param lenByte
	 * @return
	 * int
	 * @exception
	 * @since  1.0.0
	 */
	public static int hexToDeci(byte[] lenByte){
		    String msg = CodeUtils.bytesToHexString(lenByte);
			int len = Integer.parseInt(msg,16);
			return len;
	}

	/**
	 *
	 * concatArray 拼接数组
	 * @param one
	 * @param two
	 * @return
	 * byte[]
	 * @exception
	 * @since  1.0.0
	 */
	public static byte[] concatArray(byte[] one,byte[] two){
		byte[] newByte = new byte[one.length+two.length];

		System.arraycopy(one, 0, newByte, 0, one.length);
		System.arraycopy(two, 0, newByte, one.length, two.length);
		return newByte;
	}
	/**
	 *
	 * lrcComputer 除了报文头部每个字节做异或操作
	 * @param content
	 * @return
	 * byte[]
	 * @exception
	 * @since  1.0.0
	 */
	public static byte[] lrcComputer(byte[] content){
		byte temp = 0;
		for(int i=2,len=content.length;i<len;i++){
			if(i == 2)
			temp = (byte)(content[1]^content[2]);
			else
				temp = (byte)(temp^content[i]);

		}

		return new byte[]{temp};
	}


	/**
	 *
	 * unCompressBcd2Str 将无压缩的bcd码转化为字符串
	 * @param bytes
	 * @return
	 * String
	 * @exception
	 * @since  1.0.0
	 */
	public static String unCompressBcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}


	/**
	 *
	 * bcd2Str 将压缩的bcd码转化为字符串
	 *
	 * @param bytes
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	/**
	 *
	 * str2UnCompressBcd 将一个字符串转化为无压缩的bcd码
	 * @param asc
	 * @return
	 * byte[]
	 * @exception
	 * @since  1.0.0
	 */
	public static byte[] str2UnCompressBcd(String asc) {
		int len = asc.length();

		byte abt[] = new byte[len];

		byte bbt[] = new byte[len];
			abt = asc.getBytes();

		int j;

		for (int p = 0; p < asc.length(); p++) {
			if ((abt[p] >= '0') && (abt[p] <= '9')) {
				j = abt[p] - '0';
			} else if ((abt[p] >= 'a') && (abt[p] <= 'z')) {
				j = abt[p] - 'a' + 0x0a;
			} else {
				j = abt[p] - 'A' + 0x0a;
			}


			int a = j;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/***
	 *
	 * str2CompressBcd 将字符串转化为压缩的bcd码
	 * @param asc
	 * @return
	 * byte[]
	 * @exception
	 * @since  1.0.0
	 */
	public static byte[] str2CompressBcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
			abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}

			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	//转换成十六进制字符串
    public static String byte2Hex(byte[] b){
        String hs="";
        String stmp="";
        for(int n=0; n<b.length; n++){
            stmp = (java.lang.Integer.toHexString(b[n]& 0XFF));
            if(stmp.length()==1){
                hs = hs + "0" + stmp;
            }else{
                hs = hs + stmp;
            }
            if(n<b.length-1)hs=hs+"";
        }
        return hs.toUpperCase();
    }

	public static byte[] GetSPSecretKeyBytes(String key) {
		int length = key.length() / 4;// 即为32个字符的16进制数
		byte[] keyArray = new byte[length];
		for (int i = 0; i < length; i++) {
			keyArray[i] = BitToByte(key.substring(i * 4, (i+1)*4));
		}

		return keyArray;
	}

	// 8583报文初始位图:128位01字符串
	public static String getInitBitMap() {
		String initBitMap = "00000000" + "00000000" + "00000000" + "00000000"
				+ "00000000" + "00000000" + "00000000" + "00000000"
				+ "00000000" + "00000000" + "00000000" + "00000000"
				+ "00000000" + "00000000" + "00000000" + "00000000";
		return initBitMap;
	}
	/**
	 * 改变128位图中的标志为1
	 * @param fieldNo
	 * @param res
	 * @return
	 */
	public static String change16bitMapFlag(int fieldNo, String res) {
		int indexNo=fieldNo;
		res = res.substring(0, indexNo-1) + "1" + res.substring(indexNo);
		return res;
	}
	/**
	 * 复制字符
	 * @param str
	 * @param count
	 * @return
	 */
	public static String strCopy(String str,int count){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i < count;i++){
			sb.append(str);
		}
		return sb.toString();
	}
	/**
	 * 位图操作
	 *
	 * 把16位图的字节数组转化成128位01字符串
	 * @param packet_header_map
	 * @return
	 */
	public static String get16BitMapStr(byte[] bitMap16){
		String bitMap128 = "";
		// 16位图转2进制位图128位字符串
		for (int i = 0; i < bitMap16.length; i++) {
			int bc = bitMap16[i];
			bc=(bc<0)?(bc+256):bc;
			String bitnaryStr=Integer.toBinaryString(bc);//二进制字符串
			// 左补零，保证是8位
			String rightBitnaryStr = strCopy("0",Math.abs(8-bitnaryStr.length())) + bitnaryStr;//位图二进制字符串
			// 先去除多余的零，然后组装128域二进制字符串
			bitMap128+=rightBitnaryStr;
		}
		return bitMap128;
	}
	/**
	 *  位图操作
	 *
	 * 把128位01字符串转化成16位图的字节数组
	 * @param packet_header_map
	 * @return
	 */
	public static byte[] get16BitByteFromStr(String str_128){
		byte[]  bit16=new byte[16];
			if(str_128==null||str_128.length()!=128){
				return null;
			}
			// 128域位图二进制字符串转16位16进制
			byte[]  tmp=str_128.getBytes();
			int weight;//权重
			byte[] strout = new byte[128];
			int i, j, w = 0;
			for (i = 0; i < 16; i++) {
				weight = 0x0080;
				for (j = 0; j < 8; j++) {
					strout[i] += ((tmp[w]) - '0') * weight;
					weight /= 2;
					w++;
				}
				bit16[i] = strout[i];
			}
		return bit16;
	}
	/**
	 * 字符串左补零方法
	 * @param str 待处理字符串
	 * @param maxlen 最大长度
	 * @return
	 */
	public static String leftZeroFill(String str,int maxlen){
		String zeroDtr = "";
		String ret = "";
		int len = maxlen - str.length();
		for (int i = 0; i < len; i++) {
			zeroDtr = zeroDtr + "0";
		}
		ret = zeroDtr + str;
		return ret;
	}
	/**
	 * 字符串右补零方法
	 * @param str 待处理字符串
	 * @param maxlen 最大长度
	 * @return
	 */
	public static String rightZeroFill(String str,int maxlen){
		String zeroDtr = "";
		String ret = "";
		int len = maxlen - str.length();
		for (int i = 0; i < len; i++) {
			zeroDtr = zeroDtr + "0";
		}
		ret = str + zeroDtr;
		return ret;
	}
	/**
	 * 字符串右补空格方法
	 * @param str 待处理字符串
	 * @param maxlen 最大长度
	 * @return
	 */
	public static String rightSpaceFill(String str,int maxlen){
		String zeroDtr = "";
		String ret = "";
		int len = maxlen - str.length();
		for (int i = 0; i < len; i++) {
			zeroDtr = zeroDtr + " ";
		}
		ret = str + zeroDtr;
		return ret;
	}

	 /**
	  * java生成随机数字和字母组合
	  * @param length[生成随机数的长度]
	  * @return
	  */
	public static String getCharAndNumr(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}


}
