package cn.wonders.pos_qdg.tool8583;

import java.io.InputStream;

/**
 * 
 * 
 * 类名称：ProcessPacket
 * 类描述：
 * 创建人：mzw
 * 创建时间：2016年4月20日 上午11:47:00
 * 修改人：mzw
 * 修改时间：2016年4月20日 上午11:47:00
 * Modification History:
 * =============================================================================
 *   Author         Date           Description
 *  ---------------------------------------------------------------------------
 * =============================================================================
 * @version 1.0.0
 *
 *发送报文接受报文一连串处理
 */
public interface ProcessPacket {

	public byte[] pack(RequestDataPacket request,byte[] header)  throws ProcessException;

	public ResponseDataPacket unpack(InputStream reader) throws ProcessException;

	
	
	
}
