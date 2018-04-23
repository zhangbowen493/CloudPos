package cn.wonders.pos_qdg.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.wonders.pos_qdg.net.CPHttpClientUtil;
import cn.wonders.pos_qdg.util.LogUtil;
import android.app.Activity;

public class UpdateInfoService {
	Activity context;
	public UpdateInfoService(Activity context) {
		this.context=context;
	}

	public UpdateInfo getUpDateInfo() throws Exception {
		
		//"http://host:port/myagent/posUpdate/update.txt";
		String path = CPHttpClientUtil.getUpdateUrl(context);
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			// 创建一个url对象
			URL url = new URL(path);
			// 通过url对象，创建一个HttpURLConnection对象（连接）
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			// 通过HttpURLConnection对象，得到InputStream
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			// 使用io流读取文件
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String info = sb.toString();
		UpdateInfo updateInfo = new UpdateInfo();
		updateInfo.setVersion(info.split("&")[1]);
		LogUtil.i("版本信息："+info.split("&")[1]);
		updateInfo.setDescription(info.split("&")[2]);
		LogUtil.i("详细介绍："+info.split("&")[2]);
		updateInfo.setUrl(info.split("&")[3]);
		LogUtil.i("下载地址："+info.split("&")[3]);
		return updateInfo;
	}

}
