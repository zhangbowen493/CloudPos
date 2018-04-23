package cn.wonders.pos_qdg.util;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.Log;
import cn.wonders.pos_qdg.R;

/*
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-17 上午10:49:36
 */
public class BuzzerTools {

	/**
	 * 签到成功
	 */
	public static final int STOP_SERVICE = -100;
	/**
	 * 签到成功
	 */
	public static final int STATE_QD_SUCCESS = 101;
	/**
	 * 签到失败
	 */
	public static final int STATE_QD_FAIL = 102;
	/**
	 * 未开卡
	 */
	public static final int STATE_WKK = 103;
	/**
	 * 请重刷
	 */
	public static final int STATE_QINGCHONGSHUA = 104;
	/**
	 * 已更新
	 */
	public static final int STATE_YIGENGXIN = 105;
	/**
	 * 交易成功
	 */
	public static final int STATE_SUCCESS = 0;
	/**
	 * 非法卡
	 */
	public static final int STATE_ILLGALCARD = 1;
	/**
	 * 非就餐时间
	 */
	public static final int STATE_ILLGALTIME = 2;
	/**
	 * 交易失败
	 */
	public static final int STATE_FAIL = 3;
	/**
	 * 已超限
	 */
	public static final int STATE_OVERRUN = 4;
	/**
	 * 余额不足
	 */
	public static final int STATE_NOBALANCE = 5;
	/**
	 * 初始成功
	 */
	public static final int STATE_INITSUCCESS = 6;
	private static MediaPlayer mediaPlayer;

	private static BuzzerTools instance;

	private BuzzerTools() {
		super();
	}

	public static synchronized BuzzerTools getInstance() {
		if (instance == null) {
			instance = new BuzzerTools();
			// 第二步，为activity注册的默认音频通道。这个一般在onCreate()函数中注册即可。
		}
		return instance;
	}

	/**
	 * 播放一声蜂鸣
	 */
	public void startOKV(Activity activity, int state) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		if (mediaPlayer == null) {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnErrorListener(new OnErrorListener() {
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					mediaPlayer.reset();
					return false;
				}
			});
		} else {
			mediaPlayer.reset();
		}
		// 第五步，注册事件。当播放完毕一次后，重新指向流文件的开头，以准备下次播放。
		mediaPlayer
				.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer player) {
						player.seekTo(0);
						mediaPlayer.stop();
					}
				});
		// 第六步，设定数据源，并准备播放
		AssetFileDescriptor file = null;
		switch (state) {
		case STATE_SUCCESS:
			file = activity.getResources().openRawResourceFd(R.raw.beep);

			break;
		case STATE_ILLGALCARD:
			file = activity.getResources().openRawResourceFd(R.raw.feifaka);

			break;
		case STATE_ILLGALTIME:
			file = activity.getResources().openRawResourceFd(
					R.raw.feijiucanshijian);

			break;
		case STATE_FAIL:
			file = activity.getResources()
					.openRawResourceFd(R.raw.jiaoyishibai);

			break;
		case STATE_OVERRUN:
			file = activity.getResources().openRawResourceFd(R.raw.chaoxian);

			break;
		case STATE_NOBALANCE:
			file = activity.getResources().openRawResourceFd(R.raw.yuebuzu);

			break;
		case STATE_INITSUCCESS:
			file = activity.getResources().openRawResourceFd(R.raw.beep);

			break;
		case STATE_QD_SUCCESS:
			file = activity.getResources().openRawResourceFd(
					R.raw.qiandaochenggong);

			break;
		case STATE_QD_FAIL:
			file = activity.getResources().openRawResourceFd(
					R.raw.qiandaoshibai);

			break;
		case STATE_WKK:
			file = activity.getResources().openRawResourceFd(R.raw.weikaika);

			break;
		case STATE_QINGCHONGSHUA:
			file = activity.getResources().openRawResourceFd(
					R.raw.qingchongshua);

			break;
		case STATE_YIGENGXIN:
			file = activity.getResources().openRawResourceFd(R.raw.yigengxin);

			break;

		default:
			break;
		}
		try {
			if (file != null) {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(10, 10);
				mediaPlayer.prepareAsync();
			} else {
				LogUtil.e("没有语音文件！");
			}
		} catch (IOException ioe) {
			Log.w("TAG", ioe);
		}
		// 第七步，开始播放
		mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.start();
			}
		});
	}

	/**
	 * 
	 * @param activity
	 * @param b
	 *            true 播放 false 停止
	 */
	public void playError(Activity activity, boolean b) {
//		// 第二步，为activity注册的默认音频通道。这个一般在onCreate()函数中注册即可。
//		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//		if (mediaPlayer == null) {
//			mediaPlayer = new MediaPlayer();
//		}
//		if (b) {
//			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//			// 第五步，注册事件。当播放完毕一次后，重新指向流文件的开头，以准备下次播放。
//			mediaPlayer
//					.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//						@Override
//						public void onCompletion(MediaPlayer player) {
//							player.seekTo(0);
//
//							mediaPlayer.start();
//						}
//					});
//
//			// 第六步，设定数据源，并准备播放
//			AssetFileDescriptor file = activity.getResources()
//					.openRawResourceFd(R.raw.beep);
//			try {
//				if (file != null) {
//					mediaPlayer.setDataSource(file.getFileDescriptor(),
//							file.getStartOffset(), file.getLength());
//					file.close();
//					mediaPlayer.setVolume(10, 10);
//					mediaPlayer.prepare();
//				} else {
//					LogUtil.e("没有语音文件！");
//				}
//			} catch (IOException ioe) {
//				Log.w("TAG", ioe);
//			}
//			// 第七步，开始播放
//			mediaPlayer.start();
//		} else {
//			mediaPlayer.stop();
//		}
	}

}
