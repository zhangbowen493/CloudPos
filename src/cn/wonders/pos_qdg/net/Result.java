package cn.wonders.pos_qdg.net;

import org.json.JSONArray;
import org.json.JSONObject;

public class Result {
	/**
	 * {"message":"good",	
	"canteenBusiConfReq":{
		"data":[
		,{"startTime":"00:00","quataAmount":0,"isQuata":0,"endTime":"00:00","canteenId":"121","version":"201606272220","mealType":"05"}]
		,"message":"success","result":"99"},	
	"result":"99"
	,"dicTypeDataReq":{
		"data":[
		,{"hvalue":"宵夜3","hkey":"05"}]	
		,"message":"success","result":"99"},
	"unionPayDealUploadReq":{
		"data":[
		{"cupsSerialNum":"2        ","acceptFlag":"2              ","transCurrencyCode":"2  ","msgReasonCode":"2   ","merType":"2   ","sendOrgnFlag":"2          ","serverInputCode":"2  ","sglAndDblFlag":"2","acceptAddress":"2                                       ","version":"201606252240","origTransInfo":"2                      "}]
		,"message":"success","result":"99"}			
	,"canteenCardTypeRateReq":{
		"data":[
		,{"canteenId":"121","cardType":"02","version":"201606272220","rate":"29"}]
		,"message":"success","result":"99"},				
	"blacklistDownloadReq":{
		"data":[
		{"version":"201606271852","cardNum":"6231700180000518298"}]
		,"message":"success","result":"99"}}
	 * @param coder
	 * @param msg
	 */
	
	public Result(String coder, String msg) {
		super();
		this.coder = coder;
		this.msg = msg;
	}
	public String coder ;
	public String msg ;
	public String token;
	public JSONObject jsonBodyObject;
	public JSONArray jsonBodyArray;
	
	JSONObject canteenBusiConfReq;
	JSONObject dicTypeDataReq;
	JSONObject unionPayDealUploadReq;
	JSONObject canteenCardTypeRateReq;
	JSONObject blacklistDownloadReq;
	
}
