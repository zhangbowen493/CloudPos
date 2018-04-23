package cn.wonders.pos_qdg.tool8583;
/**
 * 
 * 
 * 类名称：ProcessException
 * 类描述：
 * 创建人：mzw
 * 创建时间：2016年4月20日 下午2:04:06
 * 修改人：mzw
 * 修改时间：2016年4月20日 下午2:04:06
 * Modification History:
 * =============================================================================
 *   Author         Date           Description
 *  ---------------------------------------------------------------------------
 * =============================================================================
 * @version 1.0.0
 *
 *异常信息
 */
public class ProcessException extends Exception{

	private String errCode;
	private String errMsg;
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public ProcessException(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
}
