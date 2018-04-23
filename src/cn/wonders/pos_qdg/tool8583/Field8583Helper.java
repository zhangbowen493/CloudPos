package cn.wonders.pos_qdg.tool8583;
/**
 *
 *
 * 类名称：Field8583Helper 类描述： 创建人：mzw 创建时间：2016年4月20日 下午3:25:13 修改人：mzw
 * 修改时间：2016年4月20日 下午3:25:13 Modification History:
 * =============================================================================
 * Author Date Description
 * ---------------------------------------------------------------------------
 * =============================================================================
 * 
 * @version 1.0.0 记录读取的位置 以及内容
 */
public class Field8583Helper {
	private byte retByteArray[];
	private int posIncValue;

	public Field8583Helper(byte abyte0[], int i) {
		retByteArray = abyte0;
		posIncValue = i;
	}

	public int getPosIncValue() {
		return posIncValue;
	}

	public byte[] getRetByteArray() {
		return retByteArray;
	}
}
