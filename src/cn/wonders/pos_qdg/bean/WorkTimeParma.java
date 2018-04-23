package cn.wonders.pos_qdg.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 食堂营业时间配置类
 * @author Luckydog
 *
 */
@DatabaseTable(tableName = "tb_work_time_parma")
public class WorkTimeParma {
	/**
	 * 2.1	canteenId	食堂id
2.2	mealType	餐别
2.3	startTime	营业开始时间
2.4	endTime	营业结束时间
3	syncTime	同步时间

{"startTime":"00:00",

"quataAmount":22,
"isQuata":0,

"endTime":"9:00",
"canteenId":"103",
"version":"201606262210",
"mealType":"00"}
	 */
	/** */
	@DatabaseField(generatedId = true)
	private int id ;
	@DatabaseField(columnName = "canteen_id" )//"canteenId":"103",
	private String CanteenID ;
	@DatabaseField(columnName = "meal_type" )//"mealType":"00"
	private String mealType ;
	@DatabaseField(columnName = "start_time" ) //"startTime":"00:00",
	private String startTime ;
	@DatabaseField(columnName = "end_time" ) //"endTime":"9:00",
	private String endTime ;
	@DatabaseField(columnName = "version" )
	private String version ;
	@DatabaseField(columnName = "quata_amount" )//"quataAmount":22,
	private String quataAmount ;
	@DatabaseField(columnName = "is_quata" )//"isQuata":0,
	private String isQuata ;
	@DatabaseField(columnName = "times")	//优惠次数
	private String times;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCanteenID() {
		return CanteenID;
	}
	public void setCanteenID(String canteenID) {
		CanteenID = canteenID;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getQuataAmount() {
		return quataAmount;
	}
	public void setQuataAmount(String quataAmount) {
		this.quataAmount = quataAmount;
	}
	public String getIsQuata() {
		return isQuata;
	}
	public void setIsQuata(String isQuata) {
		this.isQuata = isQuata;
	}
	
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	@Override
	public String toString() {
		return "WorkTimeParma [id=" + id + ", CanteenID=" + CanteenID
				+ ", mealType=" + mealType + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", version=" + version
				+ ", quataAmount=" + quataAmount + ", isQuata=" + isQuata
				+ ", times=" + times + "]";
	}

}
