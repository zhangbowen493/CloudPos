package com.wd.liandidemo.RF;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PbocUtils {
	
	/**
	 * DDA认证
	 * @param map
	 * @return
	 */
	public static String checkDDa(Map<String,String> map){
		String n = "EB374DFC5A96B71D2863875EDA2EAFB96B1B439D3ECE0B1826A2672EEEFA7990286776F8BD989A15141A75C384DFC14FEF9243AAB32707659BE9E4797A247C2F0B6D99372F384AF62FE23BC54BCDC57A9ACD1D5585C303F201EF4E8B806AFB809DB1A3DB1CD112AC884F164A67B99C7D6E5A8A6DF1D3CAE6D7ED3D5BE725B2DE4ADE23FA679BF4EB15A93D8A6E29C7FFA1A70DE2E54F593D908A3BF9EBBD760BBFDC8DB8B54497E6C5BE0E4A4DAC29E5";
		String d = "3";
		String tag_90 = map.get("tag_90");
		String tag_92 = map.get("tag_92");
		String tag_82 = map.get("tag_82");
		String tag_5F24 = map.get("tag_5F24");//AFL组合的数据
		String tag_9F46 = map.get("tag_9F46");
		String tag_9F48 = map.get("tag_9F48");
		String tag_80 = map.get("tag_80");
		String Rsa_random = map.get("Rsa_random");

		RSAPublicKey pubKey = RSAUtils.getPublicKey(n, d);
		String ming = "";
		try {
			ming = RSAUtils.decryptByPublicKey(tag_90, pubKey);//发卡行公钥解90 恢复发卡行公钥证书原数据
			System.out.println(ming);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bank_head = ming.substring(0, 4);
		String bank_tail = ming.substring(ming.length()-2, ming.length());
		String bank_flag = ming.substring(4, 12);
		String bank_abateDate = ming.substring(12, 16);
		String bank_serial = ming.substring(16, 22);
		String bank_hashFlag = ming.substring(22, 24);
		String bank_sfFlag = ming.substring(24, 26);
		String bank_Ni = ming.substring(26, 28);
		String bank_Ei = ming.substring(28, 30);
		String bank_hash = ming.substring(ming.length()-42, ming.length()-2);//IC卡公钥的哈希值
		String bank_lPart = "";
		if("".equals(tag_92)||tag_92==null){
			bank_lPart = ming.substring(30, ming.length()-42);
		}else{
			bank_lPart = ming.substring(30, ming.length()-42) + tag_92;
		}
		if(!"6A02".equals(bank_head.toUpperCase())||!"BC".equals(bank_tail.toUpperCase())){
			return "false";
		}

		//用于计算哈希的所有数据
		String hadata = "04"+bank_flag+bank_abateDate+bank_serial+bank_hashFlag+bank_Ni+bank_Ei+bank_lPart+bank_tail+"0"+d+tag_5F24+tag_82;
		System.out.println("hadata=="+hadata);
		String ha = RSAUtils.sha1(hadata).toUpperCase();//计算上述数据的哈希值
		System.out.println("ha=="+ha);
		if(bank_hash.toUpperCase().equals(ha))
			System.out.println("ok");
		else
			return "false";

		//发卡行公钥解9F46 恢复发卡行公钥证书原数据
		RSAPublicKey pubKeyBank = RSAUtils.getPublicKey(bank_lPart, d);
		String mingIC = "";
		try {
			mingIC = RSAUtils.decryptByPublicKey(tag_9F46, pubKeyBank);
			System.out.println(mingIC);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//IC卡公钥证书数据解析
		String IC_head = mingIC.substring(0, 2);
		String IC_cipherType = mingIC.substring(2, 4);
		String IC_tail = mingIC.substring(mingIC.length()-2, mingIC.length());
		String IC_acctNum = mingIC.substring(4, 24);
		String IC_abateDate = mingIC.substring(24, 28);
		String IC_serial = mingIC.substring(28, 34);
		String IC_hashFlag = mingIC.substring(34, 36);
		String IC_sfFlag = mingIC.substring(36, 38);
		String IC_Nic = mingIC.substring(38, 40);
		String IC_Eic = mingIC.substring(40, 42);
		String IC_hash = mingIC.substring(mingIC.length()-42, mingIC.length()-2);//IC卡公钥的哈希值
		String IC_lPart = "";
		if("".equals(tag_9F48)||tag_9F48==null){
			IC_lPart = mingIC.substring(42, mingIC.length()-42);
		}else{
			IC_lPart = mingIC.substring(42, mingIC.length()-42) + tag_9F48;
		}
		if(!"6A02".equals((IC_head+IC_cipherType).toUpperCase())||!"BC".equals(IC_tail.toUpperCase())){
			return "false";
		}

		// IC卡公钥解80 恢复IC卡公钥证书原数据
		RSAPublicKey pubKeyIc = RSAUtils.getPublicKey(IC_lPart, d);
		String mingSign = "";
		try {
			mingSign = RSAUtils.decryptByPublicKey(tag_80, pubKeyIc);
			System.out.println(mingSign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String sign_head = mingSign.substring(0, 2);
		String sign_tail = mingSign.substring(mingSign.length()-2, mingSign.length());
		String sign_data = mingSign.substring(2,mingSign.length()-2);
		String sign_ha = sign_data.substring(sign_data.length()-40, sign_data.length());

		// 用于计算哈希的所有数据
		System.out.println("sign_data==" + sign_data);
		String haB = RSAUtils.sha1(sign_data+Rsa_random).toUpperCase();// 计算上述数据的哈希值
		System.out.println("haB==" + haB);
		if (sign_ha.toUpperCase().equals(haB))
			System.out.println("ok");
		else
			return "false";

		return "success";
	}

	/**
	 * 交易日志记录文件
	 * @param map（key:transInfo 需要处理的数据；key:data_9F4F 需要处理的数据的格式）
	 * @return（tag_9A：交易日期 tag_9F21：交易时间 tag_9F02：授权金额 tag_9F03：其它金额 tag_9F1A：终端国家代码
	 * 			tag_5F2A：交易货币代码 tag_9F4E：商户名称 tag_9C：交易类型 tag_9F36：应用交易计数器（ATC））
	 */
	public static Map<String,String> transLogs(Map<String,String> map){
		Map<String,String> retMap = new HashMap<String, String>();
		String transInfo = map.get("transInfo");
		String data_9F4F = map.get("data_9F4F");
		String tag_9F4F = "";
		//解析数据格式数据得tag_9f4f
		List tlvListData = PBOC_TLV.decodingTLV(data_9F4F);
        for(int i = 0; i < tlvListData.size(); i++) {
            String[] tlvData = (String[]) tlvListData.get(i);
            if("9F4F".equals(tlvData[0])){
            	tag_9F4F = tlvData[2];
            }
        }
        //解析tag_9f4f数据得数据格式并将解析的数据放入map中
        List tlvListTag = PBOC_TL.decodingTLV(tag_9F4F);
        String tag_9A = "";
        String tag_9F21 = "";
        String tag_9F02 = "";
        String tag_9F03 = "";
        String tag_9F1A = "";
        String tag_5F2A = "";
        String tag_9F4E = "";
        String tag_9C = "";
        String tag_9F36 = "";
        for(int i = 0; i < tlvListTag.size(); i++) {
            String[] tlvTag = (String[]) tlvListTag.get(i);
            if("9A".equals(tlvTag[0])){
            	//截取tag值
            	tag_9A = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	//截取剩下的数据
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
            if("9F21".equals(tlvTag[0])){
            	tag_9F21 = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
            if("9F02".equals(tlvTag[0])){
            	tag_9F02 = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
            if("9F03".equals(tlvTag[0])){
            	tag_9F03 = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
            if("9F1A".equals(tlvTag[0])){
            	tag_9F1A = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
            if("5F2A".equals(tlvTag[0])){
            	tag_5F2A = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
            if("9F4E".equals(tlvTag[0])){
            	tag_9F4E = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
            if("9C".equals(tlvTag[0])){
            	tag_9C = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
            if("9F36".equals(tlvTag[0])){
            	tag_9F36 = transInfo.substring(0, CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2);
            	transInfo = transInfo.substring(CodeUtils.hexToDeci(CodeUtils.hexStringToByte(tlvTag[1]))*2, transInfo.length());
            }
        }
        retMap.put("tag_9A", tag_9A);
        retMap.put("tag_9F21", tag_9F21);
        retMap.put("tag_9F02", tag_9F02);
        retMap.put("tag_9F03", tag_9F03);
        retMap.put("tag_9F1A", tag_9F1A);
        retMap.put("tag_5F2A", tag_5F2A);
        retMap.put("tag_9F4E", tag_9F4E);
        retMap.put("tag_9C", tag_9C);
        retMap.put("tag_9F36", tag_9F36);
		return retMap;
	}

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
//		String data = "046230511510000006376F12190B484A01018001DB1F5551D7F5500B7574E9F46CE000EA2B4D499B8946FEFBC793498912E94E661A2492519CAAF65A43275F0D84CC89E48D06B1E9880E0AF9E795A400515C5BA2600854D69419EBD1D411F0F57394B0142627714ED0F170CF516933E3772894756C3B0DC9C42B246793743687E9E90A16BD6AD4E191F5691FA7B6E105EC28A257BBBBBBBBBBBB035F24032308315A0A6230511510000006376F9F0702FF008E100000000000000000420341031E031F009F0D05D8609CA8009F0E0500100000009F0F05D8689CF8005F280201567C00";
//		String sign = "11DB304472C318DEDEAE94DC6A3E7B754F94A537";
		/*Map<String,String> map = new HashMap<String, String>();
		String tag_90 = "70008DF97CD5BDE4520E7DFBAEC8420F621D60DC2BF97B7E04129A3FD5825E0D56105943B7E24D05C8FED3D89B7BFAC663B8F1F951876CA312B869A9D64954F49AEEC4D6AF9FFE6F880434174649725FF025587793AB94F4CBE809E1A344931F6DF0ACD4B2A50CF61682731485DE0494F94085B48723D8C6C5C6538E1CF0A7BBB1C4CE21C695C503CC0B9F33807D2A27888516805C53FF3FA916C6507C315B0ED0AA9B27C9AAC08213447130C2EEE23C";
		String tag_5F24 = "5F24032308315A0A6230511510000006368F9F0702FF008E100000000000000000420341031E031F009F0D05D8609CA8009F0E0500100000009F0F05D8689CF8005F28020156";
		String tag_82 = "7C00";
		String tag_92 = "350B58A9D0659628B02864744A9DB28D97F020C1CBBEEE31FAAD9ACF4E9D4C8AF5DAC20D";
		String tag_9F46 = "41E658D4E4197703E1C583D4BE100AAB3615277608E1F6B22630FA3D18E59592BCF836AD6096A54A11F57234E7BD8BE7807E65EC0B97636CD5FA5FC0A51C8D76CC318F190DD62981E6FD0251A7F8F4A44CAFDD2FAA67CF595FE1D6E2C05C059387B8D3424366ECE9A7402A158322A5FF16B3088263E379282F5E705CDE2298805D2087ACD8BA10B1DC7660528A6D64AE358D64F06D07DD48CCB4F45AE2C5F4228C2A957674D11CEB4B6F1F9F554D9B30";
		String tag_9F48 = "";
		String tag_80 = "";
		map.put("tag_90", tag_90);
		map.put("tag_5F24", tag_5F24);
		map.put("tag_82", tag_82);
		map.put("tag_92", tag_92);
		map.put("tag_9F46", tag_9F46);
		map.put("tag_9F48", tag_9F48);
		map.put("tag_80", tag_80);

		checkDDa(map);*/
		Map<String,String> data = new HashMap<String, String>();
		Map<String,String> result = new HashMap<String, String>();
		data.put("transInfo", "16052419122200000000000000000000000001560156000000000000000000000000000000000000000001003F9000");
		data.put("data_9F4F", "9F4F199A039F21039F02069F03069F1A025F2A029F4E149C019F36029000");
		result = transLogs(data);
		System.out.println(result.get("tag_9F4E"));
	}
}
