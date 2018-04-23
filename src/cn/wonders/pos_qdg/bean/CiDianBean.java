package cn.wonders.pos_qdg.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
	/*
	 * 词典bean
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-26 下午11:32:41
 *{"hvalue":"早餐",
 *"hkey":"00"}
 */
@DatabaseTable(tableName = "tb_cidian")
public class CiDianBean {
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = "hvalue")
	private String hvalue;
	@DatabaseField(columnName = "hkey")
	private String hkey;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHvalue() {
		return hvalue;
	}
	public void setHvalue(String hvalue) {
		this.hvalue = hvalue;
	}
	public String getHkey() {
		return hkey;
	}
	public void setHkey(String hkey) {
		this.hkey = hkey;
	}
	@Override
	public String toString() {
		return "CiDianBean [id=" + id + ", hvalue=" + hvalue + ", hkey=" + hkey
				+ "]";
	}
	
	

}


