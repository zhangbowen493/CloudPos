package cn.wonders.pos_qdg.tool8583;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Random;

/**
 *进制转化
 */

public class CodeUtils {
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

	public static void main(String[] args) throws IOException, ParseException {
		String ret = "10\n"
				+ "123000000000001,123000000001,160603,0000000038,000000000000,000000000000,000000000038,000001000287,ALL\n"
				+ "B000300050025,66000300050025,9200,631002,1821049,160603,242,5,,null,20160603,140932,20160603,0       ,alipay,SXCC20160603140444550040\n"
				+ "B000300050025,66000300050025,9200,631002,1820994,160603,226,8,,null,20160603,100046,20160603,0       ,alipay,SXCC20160603095745361143\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821013,160603,236,1,,null,20160603,114056,20160603,0       ,alipay,SXCC20160603113627485313\n"
				+ "A000300139652,65000300139652,9200,631002,1821052,160603,248,2,,null,20160603,141505,20160603,0       ,alipay,SXCC20160603141300827770\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821003,160603,230,1,,null,20160603,110820,20160603,0       ,alipay,SXCC20160603110136615408\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821004,160603,231,1,,null,20160603,110820,20160603,0       ,alipay,SXCC20160603110355202021\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821005,160603,233,1,,null,20160603,111847,20160603,0       ,alipay,SXCC20160603111102207174\n"
				+ "6217566200010619605,3121800000117132,9200,631002,1821063,160603,256,8,,null,20160603,151546,20160603,0       ,alipay,SXCC20160603150938337572\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821064,160603,258,1,,null,20160603,151627,20160603,0       ,alipay,SXCC20160603151604375220\n"
				+ "B000300050025,66000300050025,9200,631002,1821065,160603,259,1,,null,20160603,151627,20160603,0       ,alipay,SXCC20160603151621085504\n"
				+ "B000300050025,66000300050025,9200,631002,1821066,160603,260,1,,null,20160603,151628,20160603,0       ,alipay,SXCC20160603151635422460\n"
				+ "B000300050025,66000300050025,9200,631002,1821067,160603,261,2,,null,20160603,151628,20160603,0       ,alipay,SXCC20160603151654465072\n"
				+ "B000300050025,66000300050025,9200,631002,1821068,160603,262,3,,null,20160603,151629,20160603,0       ,alipay,SXCC20160603151703664157\n"
				+ "B000300050025,66000300050025,9200,631002,1821069,160603,265,5,,null,20160603,151629,20160603,0       ,alipay,SXCC20160603151908374235\n"
				+ "B000300050025,66000300050025,9200,631002,1821070,160603,264,4,,null,20160603,152029,20160603,0       ,alipay,SXCC20160603151857061351\n"
				+ "B000300050025,66000300050025,9200,631002,1821079,160603,267,100,,null,20160603,152917,20160603,0       ,alipay,SXCC20160603152702284076\n"
				+ "B000300050025,66000300050025,9200,631002,1821080,160603,268,9,,null,20160603,152918,20160603,0       ,alipay,SXCC20160603152718866364\n"
				+ "B000300050025,66000300050025,9200,631002,1821081,160603,269,4,,null,20160603,152918,20160603,0       ,alipay,SXCC20160603152730162818\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821082,160603,270,1,,null,20160603,152918,20160603,0       ,alipay,SXCC20160603152732800068\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821083,160603,271,1,,null,20160603,152919,20160603,0       ,alipay,SXCC20160603152826765561\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821084,160603,273,1,,null,20160603,152919,20160603,0       ,alipay,SXCC20160603152902183267\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821085,160603,275,1,,null,20160603,152919,20160603,0       ,alipay,SXCC20160603152932502012\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821104,160603,278,1,,null,20160603,161421,20160603,0       ,alipay,SXCC20160603153359886576\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821105,160603,279,1,,null,20160603,161432,20160603,0       ,alipay,SXCC20160603153428180830\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821106,160603,281,1,,null,20160603,161534,20160603,0       ,alipay,SXCC20160603153542454443\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821107,160603,283,1,,null,20160603,161535,20160603,0       ,alipay,SXCC20160603153647383504\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821108,160603,284,1,,null,20160603,161535,20160603,0       ,alipay,SXCC20160603153719640538\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821109,160603,286,1,,null,20160603,161536,20160603,0       ,alipay,SXCC20160603153819728230\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821110,160603,287,1,,null,20160603,161536,20160603,0       ,alipay,SXCC20160603153937648727\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821111,160603,288,1,,null,20160603,161537,20160603,0       ,alipay,SXCC20160603154013654152\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821112,160603,291,1,,null,20160603,161538,20160603,0       ,alipay,SXCC20160603154217312825\n"
				+ "A000300139652,65000300139652,9200,631002,1821228,160603,340,4,,null,20160603,173600,20160603,0       ,alipay,SXCC20160603173144125701\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821231,160603,349,1,,null,20160603,173749,20160603,0       ,alipay,SXCC20160603173907417841\n"
				+ "6230865774004357,3121800000115430,9200,631002,1821203,160603,321,1,,null,20160603,172725,20160603,0       ,alipay,SXCC20160603172620102228\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821204,160603,322,1,,null,20160603,172726,20160603,0       ,alipay,SXCC20160603172630616114\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1820991,160603,221,10,,null,20160603,94840,20160603,0       ,alipay,SXCC20160603094551514565\n"
				+ "B000300050025,66000300050025,9200,631002,1821142,160603,293,1000099,,null,20160603,162602,20160603,0       ,alipay,SXCC20160603162215235325\n"
				+ "6217973300002446327,3121800000282963,9200,631002,1821143,160603,309,1,,null,20160603,162842,20160603,0       ,alipay,SXCC20160603162848400735\n";
		File file = new File("d:\\test\\writefile1.txt");
		if (!file.exists())
			file.createNewFile();
		FileOutputStream out = new FileOutputStream(file, true);
		// for(int i=0;i<10000;i++){
		StringBuffer sb = new StringBuffer();
		sb.append(ret);
		out.write(sb.toString().getBytes("utf-8"));
		// }
		out.close();
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
	 * 
	 * @param lenByte
	 * @return int
	 * @exception
	 * @since 1.0.0
	 */
	public static int hexToDeci(byte[] lenByte) {
		String msg = CodeUtils.bytesToHexString(lenByte);
		int len = Integer.parseInt(msg, 16);
		return len;
	}

	/**
	 *
	 * concatArray 拼接数组
	 * 
	 * @param one
	 * @param two
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] concatArray(byte[] one, byte[] two) {
		byte[] newByte = new byte[one.length + two.length];

		System.arraycopy(one, 0, newByte, 0, one.length);
		System.arraycopy(two, 0, newByte, one.length, two.length);
		return newByte;
	}
	/**
	 *
	 * concatArray 拼接tag数组
	 * 
	 * @param one
	 * @param two
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] concatTagArray(byte[] one, byte[] tag,byte[] two) {
		byte[] newByte = new byte[one.length + tag.length+two.length];
		
		System.arraycopy(one, 0, newByte, 0, one.length);
		System.arraycopy(tag, 0, newByte, one.length, tag.length);
		System.arraycopy(two, 0, newByte, one.length+tag.length, two.length);
		return newByte;
	}

	/**
	 *
	 * lrcComputer 除了报文头部每个字节做异或操作
	 * 
	 * @param content
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] lrcComputer(byte[] content) {
		byte temp = 0;
		for (int i = 2, len = content.length; i < len; i++) {
			if (i == 2)
				temp = (byte) (content[1] ^ content[2]);
			else
				temp = (byte) (temp ^ content[i]);

		}

		return new byte[] { temp };
	}

	/**
	 *
	 * unCompressBcd2Str 将无压缩的bcd码转化为字符串
	 * 
	 * @param bytes
	 * @return String
	 * @exception
	 * @since 1.0.0
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
		return temp.toString();
	}
	/**
	 *
	 * bcd2Str 将压缩的bcd码转化为字符串(左补零)
	 *
	 * @param bytes
	 * @return String
	 * @exception
	 * @since 1.0.0
	 */
	public static String bcd2Str_right(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString();
	}

	/**
	 *
	 * str2UnCompressBcd 将一个字符串转化为无压缩的bcd码
	 * 
	 * @param asc
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] str2UnCompressBcd(String asc) {
		int len = asc.length();

		byte abt[] = new byte[len];

		byte bbt[] = new byte[len];
		try {
			abt = asc.getBytes(ConstentVar.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	 * 
	 * @param asc
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] str2CompressBcd(String asc) {
		int len = asc.length();
		int mod = len % 2;

		if (mod != 0) {
			asc =  asc+"0";
			len = asc.length();
		}

		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		byte bbt[] = new byte[len];
		try {
			abt = asc.getBytes(ConstentVar.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	/***
	 *
	 * str2CompressBcd 将字符串转化为压缩的bcd码(右补零)
	 * 
	 * @param asc
	 * @return byte[]
	 * @exception
	 * @since 1.0.0
	 */
	public static byte[] str2CompressBcdRight(String asc) {
		int len = asc.length();
		int mod = len % 2;
		
		if (mod != 0) {
			asc =  "0"+asc;
			len = asc.length();
		}
		
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		
		byte bbt[] = new byte[len];
		try {
			abt = asc.getBytes(ConstentVar.DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	// 转换成十六进制字符串
	public static String byte2Hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	public static byte[] GetSPSecretKeyBytes(String key) {
		int length = key.length() / 4;// 即为32个字符的16进制数
		byte[] keyArray = new byte[length];
		for (int i = 0; i < length; i++) {
			keyArray[i] = BitToByte(key.substring(i * 4, (i + 1) * 4));
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
	// 8583报文初始位图:64位01字符串
	public static String getInit64BitMap() {
		String initBitMap = "00000000" + "00000000" + "00000000" + "00000000"
				+ "00000000" + "00000000" + "00000000" + "00000000";
		return initBitMap;
	}

	/**
	 * 改变位图中的标志为1
	 * 
	 * @param fieldNo
	 * @param res
	 * @return
	 */
	public static String change16bitMapFlag(int fieldNo, String res) {
		int indexNo = fieldNo;
		res = res.substring(0, indexNo - 1) + "1" + res.substring(indexNo);
		return res;
	}

	/**
	 * 复制字符
	 * 
	 * @param str
	 * @param count
	 * @return
	 */
	public static String strCopy(String str, int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 位图操作
	 *
	 * 把16位图的字节数组转化成128位01字符串
	 * 
	 * @param packet_header_map
	 * @return
	 */
	public static String get16BitMapStr(byte[] bitMap16) {
		String bitMap128 = "";
		// 16位图转2进制位图128位字符串
		for (int i = 0; i < bitMap16.length; i++) {
			int bc = bitMap16[i];
			bc = (bc < 0) ? (bc + 256) : bc;
			String bitnaryStr = Integer.toBinaryString(bc);// 二进制字符串
			// 左补零，保证是8位
			String rightBitnaryStr = strCopy("0",
					Math.abs(8 - bitnaryStr.length()))
					+ bitnaryStr;// 位图二进制字符串
			// 先去除多余的零，然后组装128域二进制字符串
			bitMap128 += rightBitnaryStr;
		}
		return bitMap128;
	}

	/**
	 * 位图操作
	 *
	 * 把128位01字符串转化成16位图的字节数组
	 * 
	 * @param packet_header_map
	 * @return
	 */
	public static byte[] get16BitByteFromStr(String str_128) {
		byte[] bit16 = new byte[8];
		try {
			if (str_128 == null ||  str_128.length() != 64) {
				return null;
			}
			// 128域位图二进制字符串转16位16进制
			byte[] tmp = str_128.getBytes(ConstentVar.DEFAULT_CHARSET);
			int weight;// 权重
			byte[] strout = new byte[64];
			int i, j, w = 0;
			for (i = 0; i < 8; i++) {
				weight = 0x0040;
				for (j = 0; j < 8; j++) {
					strout[i] += ((tmp[w]) - '0') * weight;
					weight /= 2;
					w++;
				}
				bit16[i] = strout[i];
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bit16;
	}

	/**
	 * 字符串左补零方法
	 * 
	 * @param str
	 *            待处理字符串
	 * @param maxlen
	 *            最大长度
	 * @return
	 */
	public static String leftZeroFill(String str, int maxlen) {
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
	 * 
	 * @param str
	 *            待处理字符串
	 * @param maxlen
	 *            最大长度
	 * @return
	 */
	public static String rightZeroFill(String str, int maxlen) {
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
	 * 
	 * @param str
	 *            待处理字符串
	 * @param maxlen
	 *            最大长度
	 * @return
	 */
	public static String rightSpaceFill(String str, int maxlen) {
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
	 * 
	 * @param length
	 *            [生成随机数的长度]
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
	/**
	 * 16进制字符串转byte数组
	 * @param hexString
	 * @return
	 */
	public static byte[] toByteArray(String hexString){
		hexString=hexString.toLowerCase();
		final byte[] byteArray=new byte[hexString.length()/2];
		int k=0;
		for (int i = 0; i < byteArray.length; i++) {
			byte high=(byte)(Character.digit(hexString.charAt(k), 16)&0xff);
			byte low=(byte)(Character.digit(hexString.charAt(k+1), 16)&0xff);
			byteArray[i]=(byte)(high<<4|low);
			k+=2;
		}
		return byteArray;
	}
	/**
	 * 字符串转byte数组
	 * @param hexString
	 * @return
	 */
	public static byte[] strtoByteArray(String String){
		//String=String.toLowerCase();
		final byte[] byteArray=new byte[String.length()];
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i]=(byte)(String.charAt(i)-'0');
		}
		return byteArray;
	}
	/**
	 * string字符串转16进制数组
	 * @param inputStr
	 * @return
	 */
	public static byte[] str2hex(String inputStr){
		byte[] result = new byte[inputStr.length()/2];
		for (int i = 0; i < inputStr.length()/2; i++) {
			result[i]=(byte)(Integer.parseInt(inputStr.substring(i*2,i*2+2), 16)&0xff);
		}
		return result;
	}
	/**
	 * 二进制转16进制
	 * @param binary
	 * @return
	 */
	public static String binary2hex(String binary){
		int length=binary.length();
		int temp=length%4;
		//每四位2进制数字对应一位16进制数字
		if(temp!=0){
			for (int i = 0; i < 4-temp; i++) {
				binary="0"+binary;
			}
		}
		//重新计算长度
		length=binary.length();
		StringBuilder sb=new StringBuilder();
		//每4个二进制数为一组进行计算
		for (int i = 0; i < length/4; i++) {
			int num=0;
			//将4个二进制数转成整数
			for (int j = i*4; j < i*4+4; j++) {
				num<<=1;//左移
				num|=(binary.charAt(j)-'0');//或运算
			}
			switch (num) {
			case 0:
				sb.append('0');
				break;
			case 1:
				sb.append('1');
				break;
			case 2:
				sb.append('2');
				break;
			case 3:
				sb.append('3');
				break;
			case 4:
				sb.append('4');
				break;
			case 5:
				sb.append('5');
				break;
			case 6:
				sb.append('6');
				break;
			case 7:
				sb.append('7');
				break;
			case 8:
				sb.append('8');
				break;
			case 9:
				sb.append('9');
				break;
			case 10:
				sb.append('a');
				break;
			case 11:
				sb.append('b');
				break;
			case 12:
				sb.append('c');
				break;
			case 13:
				sb.append('d');
				break;
			case 14:
				sb.append('e');
				break;
			case 15:
				sb.append('f');
				break;
			}
		}
		return sb.toString();
	}
}
