package cn.wonders.pos_qdg.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
	/*
	 * 定额费率    不同卡种对应不同费率
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-20 下午6:49:20
 *
 *{"canteenId":"103",
 *"cardType":"00",
 *"version":"201606262214",
 *"rate":"6"}
 */
@DatabaseTable(tableName = "tb_quota_rate")
public class QuotaRate {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = "canteenid")
	private String CanteenId;
	@DatabaseField(columnName = "cardtype")
	private String CardType; //卡类型
	@DatabaseField(columnName = "version")
	private String Version;	//版本
	@DatabaseField(columnName = "quotarate")
	private double QuotaRate;	//费率
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCanteenId() {
		return CanteenId;
	}
	public void setCanteenId(String canteenId) {
		CanteenId = canteenId;
	}
	public String getCardType() {
		return CardType;
	}
	public void setCardType(String cardType) {
		CardType = cardType;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public double getQuotaRate() {
		return QuotaRate;
	}
	public void setQuotaRate(double quotaRate) {
		QuotaRate = quotaRate;
	}
	@Override
	public String toString() {
		return "QuotaRate [id=" + id + ", CanteenId=" + CanteenId
				+ ", CardType=" + CardType + ", Version=" + Version
				+ ", QuotaRate=" + QuotaRate + "]";
	}
	
	
	

}


