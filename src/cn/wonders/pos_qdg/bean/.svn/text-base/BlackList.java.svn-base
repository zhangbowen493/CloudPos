package cn.wonders.pos_qdg.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
	/*
	 * 黑名单
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-18 下午3:24:31
 */
@DatabaseTable( tableName = "tb_whit_list" )
public class BlackList {
	
	@DatabaseField(generatedId = true)
	private int id ;
	//员工编号
	@DatabaseField(columnName = "card_number")
	private String Card_Number;
	//更新时间
	@DatabaseField(columnName = "update_time")
	private String Update_Time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCard_Number() {
		return Card_Number;
	}
	public void setCard_Number(String card_Number) {
		Card_Number = card_Number;
	}
	public String getUpdate_Time() {
		return Update_Time;
	}
	public void setUpdate_Time(String update_Time) {
		Update_Time = update_Time;
	}
	@Override
	public String toString() {
		return "BlackList [id=" + id + ", Card_Number=" + Card_Number
				+ ", Update_Time=" + Update_Time + "]";
	}
	
	
	
	
	
}


