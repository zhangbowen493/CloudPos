package cn.wonders.pos_qdg.net;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import cn.wonders.pos_qdg.app.CloudposApplication;
import cn.wonders.pos_qdg.bean.BlackList;
import cn.wonders.pos_qdg.bean.CiDianBean;
import cn.wonders.pos_qdg.bean.QuotaRate;
import cn.wonders.pos_qdg.bean.WorkTimeParma;
import cn.wonders.pos_qdg.db.BlackListDao;
import cn.wonders.pos_qdg.db.CiDianDao;
import cn.wonders.pos_qdg.db.QuotaRateDao;
import cn.wonders.pos_qdg.db.WorkTimeParmaDao;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.StringUtils;
import cn.wonders.pos_qdg.util.ToastUtil;

/**
 * JSON 解析类
 * 
 * @author
 */
public class JSONParsing {

	/**
	 * 网络请求解析第一部
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public static Result parsingtoString(HttpResponse response) {
		Result result = null;
		if (null == response) {
			// 网络请求失败
			return result;
		}
		try {
			if (response.getStatusLine().getStatusCode() == 200) {
				String ret;
				ret = EntityUtils.toString(response.getEntity(), "UTF-8");
				LogUtil.i("APP_TAG", "返回数据：" + ret);
				String json = ret.replaceAll("\\x0a|\\x0d", "");
				JSONObject jsonData = null;
				if (!StringUtils.isEmpty(json)) {
					jsonData = new JSONObject(json);
				}
				if (jsonData == null) {
					return result;
				}
				// JSONArray jsonArray = (JSONArray)
				// jsonData.optJSONArray("data");
				String respCode = (String) jsonData.opt("result");
				String msg = (String) jsonData.opt("message");
				result = new Result(respCode, msg);
				result.canteenBusiConfReq = jsonData
						.optJSONObject("canteenBusiConfReq");
				result.dicTypeDataReq = jsonData
						.optJSONObject("dicTypeDataReq");
				result.unionPayDealUploadReq = jsonData
						.optJSONObject("unionPayDealUploadReq");
				result.canteenCardTypeRateReq = jsonData
						.optJSONObject("canteenCardTypeRateReq");
				result.blacklistDownloadReq = jsonData
						.optJSONObject("blacklistDownloadReq");

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return result;
	}

	public static Result NoReturnDataRequestToArray(HttpResponse response)
			throws ParseException, IOException, JSONException {
		Result result = null;
		if (null == response) {
			// 网络请求失败
			return result;
		}
		if (response.getStatusLine().getStatusCode() == 200) {
			String ret;
			ret = EntityUtils.toString(response.getEntity(), "UTF-8");
			LogUtil.e("APP_TAG", "返回数据：" + ret);
			String json = ret.replaceAll("\\x0a|\\x0d", "");
			JSONObject jsonData = null;
			if (!StringUtils.isEmpty(json)) {
				jsonData = new JSONObject(json);
			}
			if (jsonData == null) {
				return result;
			}
			// JSONObject jsonObject = (JSONObject)
			// jsonData.optJSONObject("head");
			// if (jsonObject == null) {
			// return result;
			// }
			JSONArray jsonArray = (JSONArray) jsonData.optJSONArray("data");
			String respCode = (String) jsonData.opt("result");
			String msg = (String) jsonData.opt("message");
			result = new Result(respCode, msg);
			result.jsonBodyArray = jsonArray;
		}
		return result;
	}

	/**
	 * 返回状态数据 携带body 下JSONObject 数据
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws JSONException
	 */
	public static Result NoReturnDataRequest(HttpResponse response)
			throws ParseException, IOException, JSONException {
		// TODO Auto-generated method stub
		Result result = null;
		if (null == response) {
			// 网络请求失败
			return result;
		}
		if (response.getStatusLine().getStatusCode() == 200) {
			String ret;
			ret = EntityUtils.toString(response.getEntity(), "UTF-8");
			LogUtil.e("APP_TAG", "返回数据：" + ret);
			String json = ret.replaceAll("\\x0a|\\x0d", "");
			JSONObject jsonObject = null;
			if (!StringUtils.isEmpty(json)) {
				jsonObject = new JSONObject(json);
			}
			// //判断后台返回结果
			String resultCode = jsonObject.optString("result");
			String resultStrmsg = jsonObject.optString("message");
			JSONObject jsonObjectbody = jsonObject.optJSONObject("data");
			result = new Result(resultCode, resultStrmsg);
			result.jsonBodyObject = jsonObjectbody;
			return result;
		}
		return result;
	}
	
	/**
	 * 解析返回参数
	 * @param mActivity
	 * @param queryResult
	 * @return
	 */
	public static boolean parsPrams(Activity mActivity, Result queryResult) {
		// TODO Auto-generated method stub
		
		JSONObject blacklistDownloadReq = queryResult.blacklistDownloadReq;
		JSONObject canteenBusiConfReq = queryResult.canteenBusiConfReq;
		JSONObject canteenCardTypeRateReq = queryResult.canteenCardTypeRateReq;
		JSONObject dicTypeDataReq = queryResult.dicTypeDataReq;
		JSONObject unionPayDealUploadReq = queryResult.unionPayDealUploadReq;
		if(CPHttpClientUtil.Success.equals(blacklistDownloadReq.opt("result"))
			&&	CPHttpClientUtil.Success.equals(canteenBusiConfReq.opt("result"))
			&&	CPHttpClientUtil.Success.equals(canteenCardTypeRateReq.opt("result"))
			&&	CPHttpClientUtil.Success.equals(dicTypeDataReq.opt("result"))
			&&	CPHttpClientUtil.Success.equals(unionPayDealUploadReq.opt("result"))
				){
			
			boolean parsBLPrams = parsBLPrams(mActivity, blacklistDownloadReq.optJSONArray("data"));
			boolean parsCDPrams = parsCDPrams(mActivity, dicTypeDataReq.optJSONArray("data"));
			boolean parsRatePrams = parsRatePrams(mActivity, canteenCardTypeRateReq.optJSONArray("data"));
			boolean parsSTWTPrams = parsSTWTPrams(mActivity, canteenBusiConfReq.optJSONArray("data"));
			boolean parsYLQPrams = parsYLQPrams(mActivity, unionPayDealUploadReq.optJSONArray("data"));
			
			LogUtil.e("参数下载情况：黑名单："+parsBLPrams+"；词典配置："+parsCDPrams+"；消费卡种费率："+parsRatePrams+"；食堂营业时间配置："+parsSTWTPrams+"；银联定额参数："+parsYLQPrams);
			
			if(parsBLPrams&& parsCDPrams && parsRatePrams && parsSTWTPrams )
				CloudposApplication.getInstance().setIsUpdate(true);
				return true;
		}
			
			
		
		return false;
	}
	

	/**
	 * 解析银联定额参数
	 * 
	 * @param jsonBodyArray
	 * @return
	 */
	public static boolean parsYLQPrams(Activity activity,
			JSONArray jsonBodyArray) {
		// TODO Auto-generated method stub
		if (jsonBodyArray == null) {
			return false;
		}
		JSONObject obj = null;
		if (jsonBodyArray.length() == 1) {
			obj = jsonBodyArray.optJSONObject(0);
		}
		if (obj != null) {
			SPUtils.put(activity, SPUtils.YL_ACCEPTADDRESS,
					obj.optString("acceptAddress").trim());
			SPUtils.put(activity, SPUtils.YL_ACCEPTFLAG,
					obj.optString("acceptFlag").trim());
			SPUtils.put(activity, SPUtils.YL_CUPSSERIALNUM,
					obj.optString("cupsSerialNum").trim());
			SPUtils.put(activity, SPUtils.YL_MERTYPE, obj.optString("merType")
					.trim());
			SPUtils.put(activity, SPUtils.YL_MSGREASONCODE,
					obj.optString("msgReasonCode").trim());
			SPUtils.put(activity, SPUtils.YL_ORIGTRANSINFO,
					obj.optString("origTransInfo").trim());
			SPUtils.put(activity, SPUtils.YL_SENDORGNFLAG,
					obj.optString("sendOrgnFlag").trim());
			SPUtils.put(activity, SPUtils.YL_SERVERINPUTCODE,
					obj.optString("serverInputCode").trim());
			SPUtils.put(activity, SPUtils.YL_SGLANDDBLFLAG,
					obj.optString("sglAndDblFlag").trim());
			SPUtils.put(activity, SPUtils.YL_TRANSCURRENCYCODE,
					obj.optString("transCurrencyCode").trim());
			SPUtils.put(activity, SPUtils.YL_VERSION, obj.optString("version")
					.trim());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 食堂营业时间配置解析
	 * 
	 * @param mActivity
	 * @param jsonBodyArray
	 * @return
	 */
	public static boolean parsSTWTPrams(Activity activity,
			JSONArray jsonBodyArray) {
		// TODO Auto-generated method stub
		if (jsonBodyArray == null) {
			return false;
		}
		ArrayList<WorkTimeParma> list = new ArrayList<WorkTimeParma>();
		for (int i = 0; i < jsonBodyArray.length(); i++) {
			JSONObject json = jsonBodyArray.optJSONObject(i);
			if (json == null)
				return false;
			WorkTimeParma bean = new WorkTimeParma();
			bean.setIsQuata(json.optString("isQuata"));
			bean.setQuataAmount(json.optString("quataAmount"));
			bean.setVersion(json.optString("version"));
			bean.setCanteenID(json.optString("canteenId"));
			bean.setEndTime(json.optString("endTime"));
			bean.setMealType(json.optString("mealType"));
			bean.setStartTime(json.optString("startTime"));
			bean.setTimes(json.optString("mealNum"));
			SPUtils.put(activity, SPUtils.ST_VERSION, json.optString("version"));
			list.add(bean);
		}
		Integer addWorkTimeList = new WorkTimeParmaDao(activity)
				.addWorkTimeList(list);
		if (addWorkTimeList != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 消费卡种费率对应配置解析
	 * 
	 * @param mActivity
	 * @param jsonBodyArray
	 * @return
	 */
	public static boolean parsRatePrams(Activity mActivity,
			JSONArray jsonBodyArray) {
		// TODO Auto-generated method stub
		if (jsonBodyArray == null) {
			return false;
		}
		ArrayList<QuotaRate> list = new ArrayList<QuotaRate>();
		for (int i = 0; i < jsonBodyArray.length(); i++) {
			JSONObject json = jsonBodyArray.optJSONObject(i);
			if (json == null)
				return false;
			QuotaRate bean = new QuotaRate();
			bean.setCanteenId(json.optString("canteenId"));
			bean.setCardType(json.optString("cardType"));
			String rate = json.optString("rate");
			double parseDouble = Double.parseDouble(rate);
			SPUtils.put(mActivity, SPUtils.Rate_VERSION,
					json.optString("version"));
			bean.setQuotaRate(parseDouble);
			bean.setVersion(json.optString("version"));
			list.add(bean);
		}
		Integer addWorkTimeList = new QuotaRateDao(mActivity)
				.addQuotaRate(list);
		if (addWorkTimeList != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 黑名单下载存储
	 * 
	 * @param mActivity
	 * @param jsonBodyArray
	 * @return
	 */
	public static boolean parsBLPrams(Context mActivity, JSONArray jsonBodyArray) {
		// TODO Auto-generated method stub
		
		if (jsonBodyArray == null) {
			new BlackListDao(mActivity).clearAll();
			return false;
		}
		ArrayList<BlackList> list = new ArrayList<BlackList>();
		for (int i = 0; i < jsonBodyArray.length(); i++) {
			JSONObject json = jsonBodyArray.optJSONObject(i);
			if (json == null)
				return false;
			BlackList bean = new BlackList();
			bean.setCard_Number(json.optString("cardNum"));
			SPUtils.put(mActivity, SPUtils.BL_VERSION,
					json.optString("version"));
			list.add(bean);
		}

		Integer addWorkTimeList = new BlackListDao(mActivity)
				.addBlackList(list);
		if (addWorkTimeList != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 词典配置 解析
	 * 
	 * @param mActivity
	 * @param jsonBodyArray
	 * @return
	 */
	public static boolean parsCDPrams(Activity mActivity,
			JSONArray jsonBodyArray) {
		// TODO Auto-generated method stub
		if (jsonBodyArray == null) {
			return false;
		}
		ArrayList<CiDianBean> list = new ArrayList<CiDianBean>();
		for (int i = 0; i < jsonBodyArray.length(); i++) {
			JSONObject json = jsonBodyArray.optJSONObject(i);
			if (json == null)
				return false;
			CiDianBean bean = new CiDianBean();

			bean.setHvalue(json.optString("hvalue"));
			bean.setHkey(json.optString("hkey"));

			list.add(bean);
		}

		Integer addWorkTimeList = new CiDianDao(mActivity).addList(list);
		if (addWorkTimeList != 1) {
			return false;
		}
		return true;
	}

}
