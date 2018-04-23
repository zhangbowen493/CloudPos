package cn.wonders.pos_qdg.bean;

/*
 * 用于封装 M1 第十区 是否符合就餐标准
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-7-22 上午11:34:53
 */
public class IfCanResult {
	/**非法卡*/
	public static final int ILLEGAL = 10010;
	/**超过优惠次数*/
	public static final int EXCESS = 10000;
	/**可以用餐*/
	public static final int OK = 10001;
	/**不可用餐*/
	public static final int Fild = 10002;
	
	public IfCanResult(Boolean isCan, Boolean upTimes, int code, String msg,
			int times) {
		super();
		this.isCan = isCan;
		this.upTimes = upTimes;
		this.code = code;
		this.msg = msg;
		this.times = times;
	}

	/** 是否可以用餐 **/
	private Boolean isCan;
	/** 是否重置就餐次数 true 为 +1* false 为 =1 */
	private Boolean upTimes;
	/** 不能吃原因 code **/
	private int code;
	/** 不能吃原因描述 **/
	private String msg;
	/**卡内标记次数*/
	private int times;
	
	

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public Boolean getIsCan() {
		return isCan;
	}

	public void setIsCan(Boolean isCan) {
		this.isCan = isCan;
	}

	public Boolean getUpTimes() {
		return upTimes;
	}

	public void setUpTimes(Boolean upTimes) {
		this.upTimes = upTimes;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static int getExcess() {
		return EXCESS;
	}

	@Override
	public String toString() {
		return "IfCanResult [isCan=" + isCan + ", upTimes=" + upTimes
				+ ", code=" + code + ", msg=" + msg + "]";
	}

}
