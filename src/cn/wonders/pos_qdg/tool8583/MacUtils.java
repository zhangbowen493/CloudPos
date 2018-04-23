package cn.wonders.pos_qdg.tool8583;

import java.util.Properties;

import cn.wonders.pos_qdg.util.StringUtils;


/**
 * Mac工具类
 *
 * @author add by why 20160513
 *
 */
public class MacUtils {
    public static byte[] IV = new byte[8];

    public static byte byteXOR(byte src, byte src1) {
        return (byte) ((src & 0xFF) ^ (src1 & 0xFF));
    }

    public static byte[] bytesXOR(byte[] src, byte[] src1) {
        int length = src.length;
        if (length != src1.length) {
            return null;
        }
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = byteXOR(src[i], src1[i]);
        }
        return result;
    }
    /**
     *	生成报文异或数据
     * @param content
     * @return
     */
    public static byte[] getXorData(byte[] content){
    	byte[] data = content;
    	int  arrayCount = data.length/8;
    	int replenishCount = data.length%8;

    	Object[] obj = new Object[arrayCount+1];
    	for (int i = 0; i < obj.length; i++) {
    		if(i==obj.length-1){
    			obj[i] = new byte[replenishCount];
    			System.arraycopy(data, i*8, obj[i], 0,replenishCount);
    		}else{
    			obj[i] = new byte[8];
    			System.arraycopy(data, i*8, obj[i], 0, 8);
    		}
    	}

    	byte temp[] = new byte[8];
    	for(int i=1,len=obj.length;i<len;i++){
    		if(i == 1){
    			temp = bytesXOR((byte[])obj[0],(byte[])obj[1]);
    		}else{
    			if(i==len-1)
    				//如果最后不满8个字节则用“0x00”补足8字节
    				temp = bytesXOR(temp,CodeUtils.concatArray((byte[])obj[i], new byte[8-replenishCount]));
    			else
    				temp = bytesXOR(temp,(byte[])obj[i]);
    		}

    	}

    	return temp;
    }

    /**
     *
     * @param body xml报文body中参数拼接的字符串
     * @return
     * @throws Exception
     */
    public static String generateMacUP(byte[] body,byte[] workKey) throws Exception {
    	byte[] xorHex = getXorData(body);
    	String RESULT_BLOCK = CodeUtils.byte2Hex(xorHex);
    	String RESULT_BLOCK1 = RESULT_BLOCK.substring(0, 8);
    	String RESULT_BLOCK2 = RESULT_BLOCK.substring(8, 16);

    	byte[] ENC_BLOCK1 = DesECBencrypt.encryptDES(RESULT_BLOCK1.getBytes(), workKey);
    	byte[] TEMP_BLOCK = bytesXOR(ENC_BLOCK1,RESULT_BLOCK2.getBytes());
    	byte[] ENC_BLOCK2 = DesECBencrypt.encryptDES(TEMP_BLOCK, workKey);
    	String ENC_RESULT = CodeUtils.byte2Hex(ENC_BLOCK2);

    	return ENC_RESULT.substring(0, 8);
    }
}
