package cn.wonders.pos_qdg.tool8583;

import java.io.Serializable;

import org.apache.commons.collections.map.ListOrderedMap;
/**
 * 发送报文信息对象
 */
public class RequestDataPacket implements Serializable {
    //存贮数据信息
	private ListOrderedMap data = new ListOrderedMap();
    
    public void setValue(String key,String value){
    	data.put(key, value);
    }

	public ListOrderedMap getData() {
		return data;
	}

	public void setData(ListOrderedMap data) {
		this.data = data;
	}
    
    
}
