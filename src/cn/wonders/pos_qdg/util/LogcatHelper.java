package cn.wonders.pos_qdg.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.http.Header;
import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.http.annotation.HttpResponse;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import cn.wonders.pos_qdg.activity.ManageActivity;
import cn.wonders.pos_qdg.bean.HttpParameters;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.db.TradingDao;
import cn.wonders.pos_qdg.net.CPHttpClientUtil;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wd.liandidemo.RF.CodeUtils;

/**
 * log日志统计保存
 * 
 */
public class LogcatHelper {

	protected static final int UPLOAD_END = 0;
	public static final int LOGFILE_UPLOAD_SUCCESS = 11;
	public static final int LOGFILE_UPLOAD_FAIL = 12;
	private static LogcatHelper INSTANCE = null;
	public static String PATH_LOGCAT;
	public static String PATH_ZIP_LOGCAT;
	private static LogDumper mLogDumper = null;
	private static int mPId;
	private static Context context;
	public static String filename = "POS-" + TimerTools.getFileName() + ".log";
	private Vector<String> logFileName;
	private static List<TradingBean> queryUpdateBean;

	static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case UPLOAD_END:
				start();
				break;
			case LOGFILE_UPLOAD_SUCCESS:
				ToastUtil.showShort(context, "日志上传成功");
				if (ManageActivity.getInstance().dialog!=null) {
					ManageActivity.getInstance().dialog.dismiss();
				}else{
					start();
				}
				break;
			case LOGFILE_UPLOAD_FAIL:
				ToastUtil.showShort(context, "日志上传失败，请检查网络是否通畅……");
				if (ManageActivity.getInstance().dialog!=null) {
					ManageActivity.getInstance().dialog.cancel();
				}else{
					start();
				}
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 
	 * 初始化目录
	 * 
	 * */
	public void init(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
			PATH_LOGCAT = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "wonders";
			PATH_ZIP_LOGCAT = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + File.separator + "wonders_tmpzip";
		} else {// 如果SD卡不存在，就保存到本应用的目录下
			PATH_LOGCAT = context.getFilesDir().getAbsolutePath()
					+ File.separator + "wonders";
			PATH_ZIP_LOGCAT = context.getFilesDir().getAbsolutePath()
					+ File.separator + "wonders_tmpzip";
		}
		File file = new File(PATH_LOGCAT);
		if (!file.exists()) {
			file.mkdirs();
		} else {
			LogUtil.d("LOG文件夹已存在，路径为：" + file.getAbsolutePath());
		}
		File zipfile = new File(PATH_ZIP_LOGCAT);
		if (!zipfile.exists()) {
			zipfile.mkdirs();
		} else {
			LogUtil.d("ZipLOG文件夹已存在，路径为：" + zipfile.getAbsolutePath());
		}
	}

	public static LogcatHelper getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new LogcatHelper(context);
		}
		return INSTANCE;
	}

	private LogcatHelper(Context context) {
		this.context = context;
		init(context);
		mPId = android.os.Process.myPid();
	}

	/**
	 * 开始写日志
	 */
	public static void start() {
		if (mLogDumper == null){
			mLogDumper = new LogDumper(String.valueOf(mPId), PATH_LOGCAT);
			mLogDumper.start();
			LogUtil.e("开启日志读写！");
		}else{
			LogUtil.e("mLogDumper已经存在，日志记录无需开启");
		}
	}

	public static void stop() {
		if (mLogDumper != null) {
			mLogDumper.stopLogs();
			mLogDumper = null;
		}
	}

	private static class LogDumper extends Thread {

		private Process logcatProc;
		private BufferedReader mReader = null;
		private boolean mRunning = true;
		String cmds = null;
		private String mPID;
		private FileOutputStream out = null;

		public LogDumper(String pid, String dir) {
			mPID = pid;
			// pid判断，如果不足四位左补零
			mPID = CodeUtils.leftZeroFill(pid, 4);
			try {
				File logFile = new File(PATH_LOGCAT, filename);
				if (logFile.exists()) {
					out = new FileOutputStream(logFile, true);
				} else {
					out = new FileOutputStream(new File(dir, filename));
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/**
			 * 
			 * 显示当前mPID程序等级的日志.
			 * 
			 * */

			cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
			// cmds = "logcat  | grep \"(" + mPID + ")\"";// 打印所有日志信息
			// cmds = "logcat -s way";//打印标签过滤信息
			// cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";

		}

		public void stopLogs() {
			LogUtil.e("停止记录日志");
			mRunning = false;
		}

		@Override
		public void run() {
			try {
				logcatProc = Runtime.getRuntime().exec(cmds);
				mReader = new BufferedReader(new InputStreamReader(
						logcatProc.getInputStream()), 1024);
				String line = null;
				while (mRunning && (line = mReader.readLine()) != null) {
					if (!mRunning) {
						break;
					}
					if (line.length() == 0) {
						continue;
					}
					if (out != null && line.contains(mPID)) {
						out.write((TimerTools.getDateEN() + "  " + line + "\n")
								.getBytes());
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (logcatProc != null) {
					logcatProc.destroy();
					logcatProc = null;
				}
				if (mReader != null) {
					try {
						mReader.close();
						mReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					out = null;
				}
			}

		}

	}

	/**
	 * 执行上传/删除日志操作
	 */
	public void uploadLogFile() {
		logFileName = getUploadLogFileName();
		if (logFileName == null || logFileName.size() == 0) {
			// 本日日志文件已存在，当前无需执行上传及删除操作
			LogUtil.d("当前无需上传日志文件");
			// 开始接着本日日志文件继续写日志
			start();
		} else {
			// 本日的日志文件尚不存在，上传与删除以往文件
			upload();
		}
	}

	/**
	 * 上送日志文件
	 */
	private void upload() {
		new Thread() {
			@Override
			public void run() {
				try {
					super.run();
					File logFile;
					String logName, zipfileName;
					String path;
					for (int j = 0; j < logFileName.size(); j++) {
						logName = logFileName.get(j);
						zipfileName = logName.split("\\.")[0] + ".zip";
						ZipUtil.zip(PATH_LOGCAT + "/" + logName,
								PATH_ZIP_LOGCAT + "/" + zipfileName);
						LogUtil.d("上传的日志文件名为：" + logFileName.get(j));
						LogUtil.d("压缩的日志文件名为：" + zipfileName);
						logFile = new File(PATH_ZIP_LOGCAT, zipfileName);
						path = CPHttpClientUtil.getUploadLogFile(context);
						HttpParameters params = new HttpParameters();
						params.add("fileName", zipfileName);
						params.add("terminalNo", SPUtils.getString(context,
								SPUtils.TerminalNo, ""));
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < params.size(); i++) {
							sb.append(params.getKey(i));
							sb.append("=");
							sb.append(params.getValue(i));
							sb.append("&");
						}
						String m = sb.toString();
						m = m.substring(0, m.length() - 1);
						path = path + m;
						LogUtil.d("上传文件路径：" + path);
						uploadLogFile(path, logFile);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

	}

	/**
	 * 得到日志文件夹下的日志文件名
	 */
	public Vector<String> getUploadLogFileName() {
		File file = new File(PATH_LOGCAT, filename);
		if (file.exists()) {
			LogUtil.d("文件存在，追加打印信息");
			return new Vector<String>();
		} else {
			return GetVideoFileName(PATH_LOGCAT);
		}
	}

	/**
	 * 删除日志文件
	 */
	private static boolean deleteUploadLogFile(String dirPath, String name) {
		File file = new File(dirPath, name);
		LogUtil.d("旧日志文件删除，操作结果：" + file.delete());
		return file.delete();
	}

	/**
	 * 获取当前目录下所有的.log文件
	 * 
	 * @param fileAbsolutePath
	 * @return
	 */
	private Vector<String> GetVideoFileName(String fileAbsolutePath) {
		Vector<String> vecFile = new Vector<String>();
		File file = new File(fileAbsolutePath);
		File[] subFile = file.listFiles();

		for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
			// 判断是否为文件夹
			if (!subFile[iFileLength].isDirectory()) {
				String filename = subFile[iFileLength].getName();
				// 判断是否为log结尾
				if (filename.trim().toLowerCase().endsWith(".log")) {
					vecFile.add(filename);
				}
			}
		}
		return vecFile;
	}

	/**
	 * 使用asyncHttpClient上送日志文件
	 * 
	 * @param path
	 * @param file
	 */
	public static void uploadLogFile(String path, File file) {
		try {
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			final String ziplogFileName = file.getName();
			final String logFileName = ziplogFileName.split("\\.")[0] + ".log";
			// 传递文件对象给服务器端
			params.put("resources[0]", file);
			client.setTimeout(30000);
			client.post(path, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String content) {
					LogUtil.d("日志文件上送成功,statusCode=" + statusCode + "content:"
							+ content);
					handler.sendEmptyMessage(LOGFILE_UPLOAD_SUCCESS);
					LogUtil.d("开始执行文件删除操作");
					deleteUploadLogFile(PATH_ZIP_LOGCAT, ziplogFileName);
					deleteUploadLogFile(PATH_LOGCAT, logFileName);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable error, String content) {
					LogUtil.i("日志文件上送失败,statusCode=" + statusCode + "content:"
							+ content);
					handler.sendEmptyMessage(LOGFILE_UPLOAD_FAIL);
					error.printStackTrace();
				}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ManageActivity上送日志文件后不删除本地日志继续记录日志
	 * 
	 * @param path
	 * @param file
	 */
	public static void uploadLogFileWithOutDelete(String path, File file) {
		try {
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			final String ziplogFileName = file.getName();
			final String logFileName = ziplogFileName.split("\\.")[0] + ".log";
			// 传递文件对象给服务器端
			params.put("resources[0]", file);
			client.setTimeout(30000);
			client.post(path, params, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String content) {
					LogUtil.d("日志文件上送成功,statusCode=" + statusCode + "content:"
							+ content);
					handler.sendEmptyMessage(LOGFILE_UPLOAD_SUCCESS);
					LogUtil.d("zip文件删除操作");
					deleteUploadLogFile(PATH_ZIP_LOGCAT, ziplogFileName);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers,
						Throwable error, String content) {
					LogUtil.i("日志文件上送失败,statusCode=" + statusCode + "content:"
							+ content);
					handler.sendEmptyMessage(LOGFILE_UPLOAD_FAIL);
					error.printStackTrace();
				}
			});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@HttpResponse(parser = ResultParser.class)
	public class ResponseEntity {
		private String result;

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}
	}

	public class ResultParser implements ResponseParser {
		@Override
		public void checkResponse(UriRequest request) throws Throwable {

		}

		@Override
		public Object parse(Type arg0, Class<?> arg1, String arg2)
				throws Throwable {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
