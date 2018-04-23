/**
 * BCHttpClientUtil.java
 *
 * Created by xuanzhui on 2015/7/27.
 * Copyright (c) 2015 BeeCloud. All rights reserved.
 */
package cn.wonders.pos_qdg.net;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.content.Context;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.SPUtils;
import cn.wonders.pos_qdg.util.StringUtils;


/**
 * 网络请求工具类
 */
public class CPHttpClientUtil {
	/**
     * 网络请求timeout时间
     * 以毫秒为单位
     */
    public static Integer NetworkTimeout = 58000;
    /**
	 * 网络请求成功
	 */
	public static final String Success ="99";
	/**
	 * 处理超时
	 */
	public static final String Timeout ="00";
	/**
	 * 网络请求失败
	 */
	public static final String Failure ="fail";
	

	//银联定额参数下载
    private static final String updateYLQP = "myagent/control/report/list.jhtml?";
    //食堂营业时间配置下载
    private static final String updateSTP = "myagent/control/report/list.jhtml?";
    //卡种费率配置下载
    private static final String updateRate = "myagent/control/report/list.jhtml?";
    
    private static final String updateLS = "myagent/control/report/serialList.jhtml?";
    //程序更新下载
    private static final String UpDateURL="myagent/posUpdate/update.txt";
    //日志上送
    private static final String UploadFile="myagent/control/report/putLogFile.jhtml?";
    //异常流水上送
    private static final String UpDateAbnormaoLishui="myagent/control/report/abnormalSerialList.jhtml?";
    
    private static String getRandomHost(Context context) {
    	String url = (String) SPUtils.get(context, SPUtils.IP, "");
    	if(StringUtils.isBlank(url)){
    		return null;
    	}
    	String port = SPUtils.getString(context, SPUtils.port, "");
    	if(!StringUtils.isBlank(port)){
    		url = url +":"+port.trim();
    	}
    	return "http://"+ url +"/";
//    		return "http://10.1.92.136:18001/";
    }

    /**
     * @return  银联定额参数下载
     */
    public static String getBillUpdateYLQPURL(Activity context) {
    	if(StringUtils.isBlank(getRandomHost(context))){
    		return null;
    	}
        return getRandomHost(context) + updateYLQP;
    }
    /**
     * 食堂营业时间
     * @param context
     * @return
     */
    public static String getBillUpdateSTPURL(Activity context) {
		// TODO Auto-generated method stub
    	if(StringUtils.isBlank(getRandomHost(context))){
    		return null;
    	}
        return getRandomHost(context) + updateSTP;
	}
    /**
     * 卡种对应费率
     * @param context
     * @return
     */
    public static String getBillUpdateRateURL(Activity context) {
		// TODO Auto-generated method stub
    	 if(StringUtils.isBlank(getRandomHost(context))){
     		return null;
     	}
         return getRandomHost(context) + updateRate;
	}
    /**
     * 黑名单
     * @param mainActivity
     * @return
     */
	public static String getBillUpLiushuiURL(Context mContext) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(getRandomHost(mContext))){
     		return null;
     	}
         return getRandomHost(mContext) + updateLS;
	}
	/**
	 * 上送异常流水
	 * @param mainActivity
	 * @return
	 */
	public static String getBillUpAbNormalLiushuiURL(Context mContext) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(getRandomHost(mContext))){
			return null;
		}
		return getRandomHost(mContext) + UpDateAbnormaoLishui;
	}


    /**
     * @return  HttpClient实例
     */
    public static HttpClient wrapClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf = new WDSSLSocketFactory(trustStore);
            
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(params, NetworkTimeout);
            HttpConnectionParams.setSoTimeout(params, NetworkTimeout);

            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(
                    params, registry);
            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    /**
     * http get 请求
     * @param url   请求uri
     * @return      HttpResponse请求结果实例
     */
    public static HttpResponse httpGet(String url) {
        HttpClient client = wrapClient();

        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * http post 请求
     * @param url       请求url
     * @param entity    post参数
     * @return          HttpResponse请求结果实例
     */
    public static HttpResponse httpPost(String url, StringEntity entity) {
        HttpClient client = wrapClient();
        
//        HttpClient client = new DefaultHttpClient();
        
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(entity);

        try {
            return client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * http post 请求
     * @param url       请求url
     * @param para      post参数
     * @return          HttpResponse请求结果实例
     */
    public static HttpResponse httpPost(String url, Map<String, Object> para) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String param = gson.toJson(para);

        LogUtil.e("BCHttpClientUtil : ",url +":::"+ param);

        StringEntity entity;
        try {
            entity = new StringEntity(param, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        entity.setContentType("application/json");
        return httpPost(url, entity);
    }

	public static String getUpdateUrl(Activity context) {
		if(StringUtils.isBlank(getRandomHost(context))){
    		return null;
    	}
        return getRandomHost(context) + UpDateURL;
	}
	public static String getUploadLogFile(Context context) {
		if(StringUtils.isBlank(getRandomHost(context))){
			return null;
		}
		return getRandomHost(context) + UploadFile;
	}

}
