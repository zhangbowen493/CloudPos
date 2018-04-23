package cn.wonders.pos_qdg.tool8583;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
/**
 *
 * 2013-8-29
*下午4:57:52
* by mzw
*ECB模式  DES加密、解密算法
 */
public class DesECBencrypt {
    /**
     * 加密数据
     * @param encryptbyte  注意：这里的数据长度只能为8的倍数
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptDES(byte[] encryptbyte, byte[] encryptKey) throws Exception {
    	if(encryptbyte.length%8!=0)
    		encryptbyte = getData(encryptbyte);
        SecretKeySpec key = new SecretKeySpec(getKey(encryptKey), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(encryptbyte);
        return encryptedData;
    }

    /**
     * 自定义一个key
     * @param string
     */
    public static byte[] getKey(byte[] keyRule) {
        Key key = null;
        byte[] keyByte = keyRule;
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        key = new SecretKeySpec(byteTemp, "DES");
        return key.getEncoded();
    }
    /**
     * 数据补齐
     * @param srcDate
     * @return
     */
    public static byte[] getData(byte[] srcDate){
    	int len = srcDate.length/8 + 1;
    	byte[] retData = new byte[len*8];
    	for(int i =0,size= retData.length;i<size;i++){
    	       retData[i] = 0x20;
    	}
    	System.arraycopy(srcDate, 0, retData, 0, srcDate.length);

    	return retData;
    }
    /***
     * 解密数据
     * @param decryptString
     * @param decryptKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptDES(byte[] decryptString, byte[] decryptKey) throws Exception {
        SecretKeySpec key = new SecretKeySpec(getKey(decryptKey), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte decryptedData[] = cipher.doFinal(decryptString);

        return decryptedData;
    }

}
