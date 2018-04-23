package com.wd.liandidemo.RF;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;

import javax.crypto.Cipher;

public class RSAUtils {

	/**
	 * 生成公钥和私钥
	 * @throws NoSuchAlgorithmException
	 *
	 */
	public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        map.put("public", publicKey);
        map.put("private", privateKey);
        return map;
	}
	/**
	 * 使用模和指数生成RSA公钥
	 * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 *
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus,16);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * 注意：【此代码用了默认补位方式，为RSA/NONE/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
	 * /None/NoPadding】
	 *
	 * @param modulus
	 *            模
	 * @param exponent
	 *            指数
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus,16);
			BigInteger b2 = new BigInteger(exponent,16);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 公钥加密
	 *
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		// 模长
		int key_len = publicKey.getModulus().bitLength() / 8;
		// 加密数据长度 <= 模长-11
		String[] datas = splitString(data, key_len - 11);
		String mi = "";
		//如果明文长度大于模长-11则要分组加密
		for (String s : datas) {
			mi += bcd2Str(cipher.doFinal(s.getBytes()));
		}
		return mi;
	}

	/**
	 * 私钥解密
	 *
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		//模长
		int key_len = privateKey.getModulus().bitLength() / 8;
		byte[] bytes = data.getBytes();
		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
		System.err.println(bcd.length);
		//如果密文长度大于模长则要分组解密
		String ming = "";
		byte[][] arrays = splitArray(bcd, key_len);
		for(byte[] arr : arrays){
			ming += new String(cipher.doFinal(arr));
		}
		return ming;
	}
	/**
	 * 公钥解密
	 *
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String data, RSAPublicKey publicKey)
			throws Exception {
//		Cipher cipher = Cipher.getInstance("RSA");
		Cipher cipher = Cipher.getInstance("RSA/ECB/NOPADDING");
//		Cipher.getInstance(transformation, provider)
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		//模长
		int key_len = publicKey.getModulus().bitLength() / 8;
		byte[] bytes = data.getBytes();
		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
		System.err.println(bcd.length);
		//如果密文长度大于模长则要分组解密
		String ming = "";
		byte[][] arrays = splitArray(bcd, key_len);
		for(byte[] arr : arrays){
			ming += CodeUtils.getHexStr(cipher.doFinal(arr));
		}
		return ming;
	}
	public static boolean checkSign(byte[] data,byte[] sign,RSAPublicKey publicKey) throws
				InvalidKeyException, NoSuchAlgorithmException, SignatureException{

		Signature signature = Signature.getInstance("SHA1WithRSA");
		signature.initVerify(publicKey);
		signature.update( data );
		boolean bverify = signature.verify(sign);
		return bverify;
	}
	/**
	 *
	 * @param data 编码方式“GBK”
	 * @param privateKey
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 */
	public static byte[] Sign(byte[] data,RSAPrivateKey privateKey) throws
	InvalidKeyException, NoSuchAlgorithmException, SignatureException{

		Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(privateKey);
        signature.update(data);

		return signature.sign();
	}

	public static String sha1(String text) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(CodeUtils.hexStringToByte(text));
			outStr = byteToString(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return outStr;
	}

	private static String byteToString(byte[] digest) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < digest.length; i++) {
			String tempStr = Integer.toHexString(digest[i] & 0xff);
			if (tempStr.length() == 1) {
				buf.append("0").append(tempStr);
			} else {
				buf.append(tempStr);
			}
		}
		return buf.toString().toLowerCase();
	}
	/**
	 * ASCII码转BCD码
	 *
	 */
	public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}
	public static byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}
	/**
	 * BCD转字符串
	 */
	public static String bcd2Str(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}
	/**
	 * 拆分字符串
	 */
	public static String[] splitString(String string, int len) {
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str = "";
		for (int i=0; i<x+z; i++) {
			if (i==x+z-1 && y!=0) {
				str = string.substring(i*len, i*len+y);
			}else{
				str = string.substring(i*len, i*len+len);
			}
			strings[i] = str;
		}
		return strings;
	}
	/**
	 *拆分数组
	 */
	public static byte[][] splitArray(byte[] data,int len){
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if(y!=0){
			z = 1;
		}
		byte[][] arrays = new byte[x+z][];
		byte[] arr;
		for(int i=0; i<x+z; i++){
			arr = new byte[len];
			if(i==x+z-1 && y!=0){
				System.arraycopy(data, i*len, arr, 0, y);
			}else{
				System.arraycopy(data, i*len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}

	/**
	 *
	 * @param data 原系统信息
	 * @param sign 签名信息
	 * @return
	 */
	public static boolean licenseCheck(String data,String signstr){
		String modulus = "C028F83386AFAB898F9ADCD35FBEA51067E74A45C5CC2BB40D652CC8F110501F687A7782D342328CA04AB5D38BD6848450EC4BF33D28275E1AE49E42854C3F9420C30F088EC96C61F4E04286107908AE646DCFE85AEC222CBE7791266660948DC55387C5BF4F911FB8740025E000ABFD1F29B79D63E549C62D836CA0052E9833";
//		System.out.println("n==="+modulus);
		String public_exponent = "3";
//		System.out.println("e==="+public_exponent);
		RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
		boolean flag = false;
		try {
			flag = checkSign(data.getBytes("GBK"), CodeUtils.str2CompressBcd(signstr), pubKey);

		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag){
			System.out.println("ok!");
		}else{
			System.out.println("no ok!");
		}
		return flag;
	}
	/**
	 *
	 * @param data 原系统信息
	 * @param modulus 模
	 * @param private_exponent 私钥指数
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws SignatureException
	 */
	public static byte[] licenseSign(String data) throws
	InvalidKeyException, NoSuchAlgorithmException, SignatureException{
		String modulus = "C028F83386AFAB898F9ADCD35FBEA51067E74A45C5CC2BB40D652CC8F110501F687A7782D342328CA04AB5D38BD6848450EC4BF33D28275E1AE49E42854C3F9420C30F088EC96C61F4E04286107908AE646DCFE85AEC222CBE7791266660948DC55387C5BF4F911FB8740025E000ABFD1F29B79D63E549C62D836CA0052E9833";
		System.out.println("n==="+modulus);
		String private_exponent = "801B502259CA725BB511E88CEA7F18B59A9A3183D932C7CD5E437330A0B58ABF9AFC4FAC8CD6CC5DC031CE8D07E4585835F2DD4CD3701A3EBC986981AE32D50C429F7DCA532BFF657207D552F67AAF530358083D365834FAE95CDCDBE3B4AD5E89D7162A0D1756F2783793877F195501B8DB61911CBC84C31FCB00354B4F41EB";
		System.out.println("d==="+private_exponent);
		RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);
		Signature signature = Signature.getInstance("SHA1WithRSA");
        signature.initSign(priKey);
        try {
			signature.update(data.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return signature.sign();
	}

	/*public static void main(String[] args) throws Exception {
		byte[] sign = licenseSign("30-10-B3-92-A2-D8");
		String signstr = CodeUtils.getHexStr(sign);
		System.out.println("signstr=="+signstr);
//		boolean flag = licenseCheck("123456", signstr);
	}*/
	public static void main(String[] args) throws Exception {
		HashMap<String, Object> map = RSAUtils.getKeys();
		//生成公钥和私钥
		RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
		RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
		System.out.println("publicKey:"+publicKey);
		System.out.println("privateKey:"+privateKey);

		/*String modulus = "C028F83386AFAB898F9ADCD35FBEA51067E74A45C5CC2BB40D652CC8F110501F687A7782D342328CA04AB5D38BD6848450EC4BF33D28275E1AE49E42854C3F9420C30F088EC96C61F4E04286107908AE646DCFE85AEC222CBE7791266660948DC55387C5BF4F911FB8740025E000ABFD1F29B79D63E549C62D836CA0052E9833";
		System.out.println("n==="+modulus);

		String public_exponent = "3";
		System.out.println("e==="+public_exponent);

		String private_exponent = "801B502259CA725BB511E88CEA7F18B59A9A3183D932C7CD5E437330A0B58ABF9AFC4FAC8CD6CC5DC031CE8D07E4585835F2DD4CD3701A3EBC986981AE32D50C429F7DCA532BFF657207D552F67AAF530358083D365834FAE95CDCDBE3B4AD5E89D7162A0D1756F2783793877F195501B8DB61911CBC84C31FCB00354B4F41EB";
		System.out.println("d==="+private_exponent);

		RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
		RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);

//		String ming = "70008DF97CD5BDE4520E7DFBAEC8420F621D60DC2BF97B7E04129A3FD5825E0D56105943B7E24D05C8FED3D89B7BFAC663B8F1F951876CA312B869A9D64954F49AEEC4D6AF9FFE6F880434174649725FF025587793AB94F4CBE809E1A344931F6DF0ACD4B2A50CF61682731485DE0494F94085B48723D8C6C5C6538E1CF0A7BBB1C4CE21C695C503CC0B9F33807D2A27888516805C53FF3FA916C6507C315B0ED0AA9B27C9AAC08213447130C2EEE23C";
//		String mi = RSAUtils.encryptByPublicKey(ming, pubKey);
//		System.err.println(mi);

		System.out.println("sign=="+CodeUtils.getHexStr(Sign("123456".getBytes(), priKey)));
		boolean flag = checkSign("123456".getBytes("GBK"), Sign("123456".getBytes("GBK"), priKey), pubKey);
		if(flag){
			System.out.println("ok!");
		}else{
			System.out.println("no ok!");
		}*/
	}
}
