package cn.wonders.pos_qdg.bean;

import java.util.Map;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 交易记录历史流水bean
 * 
 * @author dingle
 * 
 */
@DatabaseTable(tableName = "tb_tran_history_list")
public class TradingHistoryBean {

	public TradingHistoryBean() {
		// TODO Auto-generated constructor stub
	}

	public TradingHistoryBean(TradingBean liushui) {
		// TODO Auto-generated constructor stub
		setTradingBean(liushui);
	}

	// id
	@DatabaseField(generatedId = true)
	private int id;
	// 员工id
	@DatabaseField(columnName = "workerid")
	private String WorkerID;
	// 卡号
	@DatabaseField(columnName = "cardnumber")
	private String CardNumber;
	// 交易金额 分为单位
	@DatabaseField(columnName = "tranmoney")
	private String TranMoney;
	// 交易流水 设备号+时间 （时分秒）
	@DatabaseField(columnName = "tranlist")
	private String TranList;
	// 交易时间
	@DatabaseField(columnName = "trantime")
	private String TranTime;
	// 餐别
	@DatabaseField(columnName = "style")
	private String Style;
	// 上送银联时间
	@DatabaseField(columnName = "senduptime")
	private String SendUPTime;
	// 同步时间
	@DatabaseField(columnName = "synctime")
	private String syncTime;
	// 是否清结算 0 未结算 1 结算
	@DatabaseField(columnName = "isupdate")
	private int isUpdate;
	// 是否是定额模式
	@DatabaseField(columnName = "isQuota")
	private int isQuota;
	// 批次号
	@DatabaseField(columnName = "batch_number")
	private String BatchNumber;
	// 异常错误码
	@DatabaseField(columnName = "abnormal_code")
	private String AbnormalCode;
	// 是否上送到后台
	@DatabaseField(columnName = "is_abnormal_up")
	private int isAbnormalUP;

	public int getIsQuota() {
		return isQuota;
	}

	public void setIsQuota(int isQuota) {
		this.isQuota = isQuota;
	}

	public String getBatchNumber() {
		return BatchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		BatchNumber = batchNumber;
	}

	public String getAbnormalCode() {
		return AbnormalCode;
	}

	public void setAbnormalCode(String abnormalCode) {
		AbnormalCode = abnormalCode;
	}

	public int getIsAbnormalUP() {
		return isAbnormalUP;
	}

	public void setIsAbnormalUP(int isAbnormalUP) {
		this.isAbnormalUP = isAbnormalUP;
	}

	// 蠢到数据库 存V 卡片数据
	//
	// ---=82=02=7C00 应用交互特征 AIP
	@DatabaseField(columnName = "card_aip_t")
	private String Card_AIP_T;
	@DatabaseField(columnName = "card_aip_l")
	private String Card_AIP_L;
	@DatabaseField(columnName = "card_aip_v")
	private String Card_AIP_V;
	// ---=94=0C=180106013001010020020200 AFL 卡消费commit 请求参数
	@DatabaseField(columnName = "card_afl_t")
	private String Card_AFL_T;
	@DatabaseField(columnName = "card_afl_l")
	private String Card_AFL_L;
	@DatabaseField(columnName = "card_afl_v")
	private String Card_AFL_V;
	// ---=9F36=02=00FB 交易计数器 ATC
	@DatabaseField(columnName = "card_atc_t")
	private String Card_ATC_T;
	@DatabaseField(columnName = "card_atc_l")
	private String Card_ATC_L;
	@DatabaseField(columnName = "card_atc_v")
	private String Card_ATC_V;
	// ---=9F26=08=84727BA346DB7C43 应用密文 TC
	@DatabaseField(columnName = "card_tc_t")
	private String Card_TC_T;
	@DatabaseField(columnName = "card_tc_l")
	private String Card_TC_L;
	@DatabaseField(columnName = "card_tc_v")
	private String Card_TC_V;
	// ---=9F10=13=07010103900000010A01000002129948D3E538 发卡行应用数据 IDD
	@DatabaseField(columnName = "card_idd_t")
	private String Card_IDD_T;
	@DatabaseField(columnName = "card_idd_l")
	private String Card_IDD_L;
	@DatabaseField(columnName = "card_idd_v")
	private String Card_IDD_V;
	// ---=57=13=6231700190000013539D26012200000000724F 二磁数据 MMSD
	@DatabaseField(columnName = "card_2mmsd_t")
	private String Card_2MMSD_T;
	@DatabaseField(columnName = "card_2mmsd_l")
	private String Card_2MMSD_L;
	@DatabaseField(columnName = "card_2mmsd_v")
	private String Card_2MMSD_V;
	// ---=5F34=01=01 卡片序列号
	@DatabaseField(columnName = "card_number_t")
	private String Card_Number_T;
	@DatabaseField(columnName = "card_number_l")
	private String Card_Number_L;
	@DatabaseField(columnName = "card_number_v")
	private String Card_Number_V;
	// ---=9F6C=02=0000 卡片交易属性
	@DatabaseField(columnName = "card_tran_prama_t")
	private String Card_Tran_Prama_T;
	@DatabaseField(columnName = "card_tran_prama_l")
	private String Card_Tran_Prama_L;
	@DatabaseField(columnName = "card_tran_prama_v")
	private String Card_Tran_Prama_V;
	// ---=90=00= 状态位
	@DatabaseField(columnName = "card_status_t")
	private String Card_Status_T;
	@DatabaseField(columnName = "card_status_l")
	private String Card_Status_L;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getisQuota() {
		return isQuota;
	}

	public void setisQuota(int isQuota) {
		this.isQuota = isQuota;
	}

	public String getWorkerID() {
		return WorkerID;
	}

	public void setWorkerID(String workerID) {
		WorkerID = workerID;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}

	public String getTranMoney() {
		return TranMoney;
	}

	public void setTranMoney(String tranMoney) {
		TranMoney = tranMoney;
	}

	public String getTranList() {
		return TranList;
	}

	public void setTranList(String tranList) {
		TranList = tranList;
	}

	public String getTranTime() {
		return TranTime;
	}

	public void setTranTime(String tranTime) {
		TranTime = tranTime;
	}

	public String getStyle() {
		return Style;
	}

	public void setStyle(String style) {
		Style = style;
	}

	public String getSendUPTime() {
		return SendUPTime;
	}

	public void setSendUPTime(String sendUPTime) {
		SendUPTime = sendUPTime;
	}

	public String getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
	}

	public int getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getCard_AIP_T() {
		return Card_AIP_T;
	}

	public void setCard_AIP_T(String card_AIP_T) {
		Card_AIP_T = card_AIP_T;
	}

	public String getCard_AIP_L() {
		return Card_AIP_L;
	}

	public void setCard_AIP_L(String card_AIP_L) {
		Card_AIP_L = card_AIP_L;
	}

	public String getCard_AIP_V() {
		return Card_AIP_V;
	}

	public void setCard_AIP_V(String card_AIP_V) {
		Card_AIP_V = card_AIP_V;
	}

	public String getCard_AFL_T() {
		return Card_AFL_T;
	}

	public void setCard_AFL_T(String card_AFL_T) {
		Card_AFL_T = card_AFL_T;
	}

	public String getCard_AFL_L() {
		return Card_AFL_L;
	}

	public void setCard_AFL_L(String card_AFL_L) {
		Card_AFL_L = card_AFL_L;
	}

	public String getCard_AFL_V() {
		return Card_AFL_V;
	}

	public void setCard_AFL_V(String card_AFL_V) {
		Card_AFL_V = card_AFL_V;
	}

	public String getCard_ATC_T() {
		return Card_ATC_T;
	}

	public void setCard_ATC_T(String card_ATC_T) {
		Card_ATC_T = card_ATC_T;
	}

	public String getCard_ATC_L() {
		return Card_ATC_L;
	}

	public void setCard_ATC_L(String card_ATC_L) {
		Card_ATC_L = card_ATC_L;
	}

	public String getCard_ATC_V() {
		return Card_ATC_V;
	}

	public void setCard_ATC_V(String card_ATC_V) {
		Card_ATC_V = card_ATC_V;
	}

	public String getCard_TC_T() {
		return Card_TC_T;
	}

	public void setCard_TC_T(String card_TC_T) {
		Card_TC_T = card_TC_T;
	}

	public String getCard_TC_L() {
		return Card_TC_L;
	}

	public void setCard_TC_L(String card_TC_L) {
		Card_TC_L = card_TC_L;
	}

	public String getCard_TC_V() {
		return Card_TC_V;
	}

	public void setCard_TC_V(String card_TC_V) {
		Card_TC_V = card_TC_V;
	}

	public String getCard_IDD_T() {
		return Card_IDD_T;
	}

	public void setCard_IDD_T(String card_IDD_T) {
		Card_IDD_T = card_IDD_T;
	}

	public String getCard_IDD_L() {
		return Card_IDD_L;
	}

	public void setCard_IDD_L(String card_IDD_L) {
		Card_IDD_L = card_IDD_L;
	}

	public String getCard_IDD_V() {
		return Card_IDD_V;
	}

	public void setCard_IDD_V(String card_IDD_V) {
		Card_IDD_V = card_IDD_V;
	}

	public String getCard_2MMSD_T() {
		return Card_2MMSD_T;
	}

	public void setCard_2MMSD_T(String card_2mmsd_T) {
		Card_2MMSD_T = card_2mmsd_T;
	}

	public String getCard_2MMSD_L() {
		return Card_2MMSD_L;
	}

	public void setCard_2MMSD_L(String card_2mmsd_L) {
		Card_2MMSD_L = card_2mmsd_L;
	}

	public String getCard_2MMSD_V() {
		return Card_2MMSD_V;
	}

	public void setCard_2MMSD_V(String card_2mmsd_V) {
		Card_2MMSD_V = card_2mmsd_V;
	}

	public String getCard_Number_T() {
		return Card_Number_T;
	}

	public void setCard_Number_T(String card_Number_T) {
		Card_Number_T = card_Number_T;
	}

	public String getCard_Number_L() {
		return Card_Number_L;
	}

	public void setCard_Number_L(String card_Number_L) {
		Card_Number_L = card_Number_L;
	}

	public String getCard_Number_V() {
		return Card_Number_V;
	}

	public void setCard_Number_V(String card_Number_V) {
		Card_Number_V = card_Number_V;
	}

	public String getCard_Tran_Prama_T() {
		return Card_Tran_Prama_T;
	}

	public void setCard_Tran_Prama_T(String card_Tran_Prama_T) {
		Card_Tran_Prama_T = card_Tran_Prama_T;
	}

	public String getCard_Tran_Prama_L() {
		return Card_Tran_Prama_L;
	}

	public void setCard_Tran_Prama_L(String card_Tran_Prama_L) {
		Card_Tran_Prama_L = card_Tran_Prama_L;
	}

	public String getCard_Tran_Prama_V() {
		return Card_Tran_Prama_V;
	}

	public void setCard_Tran_Prama_V(String card_Tran_Prama_V) {
		Card_Tran_Prama_V = card_Tran_Prama_V;
	}

	public String getCard_Status_T() {
		return Card_Status_T;
	}

	public void setCard_Status_T(String card_Status_T) {
		Card_Status_T = card_Status_T;
	}

	public String getCard_Status_L() {
		return Card_Status_L;
	}

	public void setCard_Status_L(String card_Status_L) {
		Card_Status_L = card_Status_L;
	}

	@Override
	public String toString() {
		return "TradingHistoryBean [id=" + id + ", WorkerID=" + WorkerID
				+ ", CardNumber=" + CardNumber + ", TranMoney=" + TranMoney
				+ ", TranList=" + TranList + ", TranTime=" + TranTime
				+ ", Style=" + Style + ", SendUPTime=" + SendUPTime
				+ ", syncTime=" + syncTime + ", isUpdate=" + isUpdate
				+ ", isQuota=" + isQuota + ", BatchNumber=" + BatchNumber
				+ ", AbnormalCode=" + AbnormalCode + ", isAbnormalUP="
				+ isAbnormalUP + "]";
	}

	/**
	 * 流水bean 设置为 历史流水bean
	 * 
	 * @param bean
	 * @return
	 */
	public boolean setTradingBean(TradingBean bean) {
		if (bean == null) {
			return false;
		}
		this.AbnormalCode = bean.getAbnormalCode();
		this.BatchNumber = bean.getBatchNumber();
		this.Card_2MMSD_L = bean.getCard_2MMSD_L();
		this.Card_2MMSD_T = bean.getCard_2MMSD_T();
		this.Card_2MMSD_V = bean.getCard_2MMSD_V();
		this.Card_AFL_L = bean.getCard_AFL_L();
		this.Card_AFL_T = bean.getCard_AFL_T();
		this.Card_AFL_V = bean.getCard_AFL_V();
		this.Card_AIP_L = bean.getCard_AIP_L();
		this.Card_AIP_T = bean.getCard_AIP_T();
		this.Card_AIP_V = bean.getCard_AIP_V();
		this.Card_ATC_L = bean.getCard_ATC_L();
		this.Card_ATC_T = bean.getCard_ATC_T();
		this.Card_ATC_V = bean.getCard_ATC_V();
		this.Card_IDD_L = bean.getCard_IDD_L();
		this.Card_IDD_T = bean.getCard_IDD_T();
		this.Card_IDD_V = bean.getCard_IDD_V();
		this.Card_Number_L = bean.getCard_Number_L();
		this.Card_Number_T = bean.getCard_Number_T();
		this.Card_Number_V = bean.getCard_Status_L();
		this.Card_Status_L = bean.getCard_Status_L();
		this.Card_Status_T = bean.getCard_Status_T();
		this.Card_TC_L = bean.getCard_TC_L();
		this.Card_TC_T = bean.getCard_TC_T();
		this.Card_TC_V = bean.getCard_TC_V();
		this.Card_Tran_Prama_L = bean.getCard_Tran_Prama_L();
		this.Card_Tran_Prama_T = bean.getCard_Tran_Prama_T();
		this.Card_Tran_Prama_V = bean.getCard_Tran_Prama_V();
		this.CardNumber = bean.getCardNumber();
		this.id = bean.getId();
		this.isAbnormalUP = bean.getIsAbnormalUP();
		this.isQuota = bean.getisQuota();
		this.isUpdate = bean.getIsUpdate();
		this.SendUPTime = bean.getSendUPTime();
		this.Style = bean.getStyle();
		this.syncTime = bean.getSyncTime();
		this.TranList = bean.getTranList();
		this.TranMoney = bean.getTranMoney();
		this.TranTime = bean.getTranTime();
		this.WorkerID = bean.getWorkerID();

		return true;
	}

}