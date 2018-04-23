package cn.wonders.pos_qdg.tool8583;
import java.io.Serializable;

import org.apache.commons.collections.map.ListOrderedMap;

/**
 * 响应报文信息对象
 */
public class ResponseDataPacket implements Serializable {

	public ListOrderedMap data = new ListOrderedMap();

	public Object getValue(String fieldKey) {
		return data.get(fieldKey);
	}

	public void setValue(String fieldKey, Object fieldValue) {
		data.put(fieldKey, fieldValue);
	}

	public ListOrderedMap getData() {
		return data;
	}

	public void setData(ListOrderedMap data) {
		this.data = data;
	}

}
