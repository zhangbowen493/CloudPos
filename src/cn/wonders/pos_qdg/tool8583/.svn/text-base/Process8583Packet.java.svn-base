package cn.wonders.pos_qdg.tool8583;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import cn.wonders.pos_qdg.util.LogUtil;

/**
 *
 *
 * 类名称：Process8583Packet 类描述： 创建人：mzw 创建时间：2016年4月20日 下午2:18:53 修改人：mzw
 * 修改时间：2016年4月20日 下午2:18:53 Modification History:
 * =============================================================================
 * Author Date Description
 * ---------------------------------------------------------------------------
 * =============================================================================
 * 
 * 具体处理8583报文
 */
public class Process8583Packet implements ProcessPacket {
	/**
	 * 打包报文
	 */
	public byte[] pack(RequestDataPacket request,byte[] header) throws ProcessException {

		byte[] content = null;
		// 增加8583报文
		try {
			content = ProcessHelper.addField(request, Packet8583Config.table,
					Packet8583SubfieldConfig.subfieldtable, header);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	
 
	
	
	/**
	 * 打包55域报文
	 */
	public byte[] pack55field(RequestDataPacket request) throws ProcessException {
		
		byte[] content = null;
		// 增加8583报文
		try {
			content = ProcessHelper.add55Field(request, Packet8583SubfieldConfig.subfieldtable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 解包报文
	 */
	public ResponseDataPacket unpack(InputStream reader)
			throws ProcessException {
		// 先读长度 两个字节
		byte[] lenByte = new byte[2];
		int readLen = 0;
		ResponseDataPacket packet = null;
		try {
			reader.read(lenByte);
			int packLen = CodeUtils.hexToDeci(lenByte);// 字符串长度

			byte[] content = new byte[packLen];
			readLen = reader.read(content);
			LogUtil.e("返回的完整8583报文："+CodeUtils.getHexStr(content));

			content = ProcessHelper.cutHeadAndTail(content);
			packet = ProcessHelper.unpack8583(content, Packet8583Config.table);

		} catch (IOException e) {
			throw new ProcessException(ConstentVar.UNKNOWN_ERR, "读发生异常");
		}

		return packet;
	}


}
