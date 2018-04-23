package cn.wonders.pos_qdg.service;

import java.util.ArrayList;
import java.util.List;

import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.TradingHistoryBean;
import cn.wonders.pos_qdg.db.TradingAbnormalDao;
import cn.wonders.pos_qdg.db.TradingDao;
import cn.wonders.pos_qdg.db.TradingHistoryDao;
import cn.wonders.pos_qdg.util.LogUtil;
import cn.wonders.pos_qdg.util.SPUtils;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
	/*
	 * 清理流水
	 * 开机启动 查询转表失败流水  和 已转表 但删除数据失败  并且清理i流水
 *@作者: dingle
 *@版本: 
 *@version create time：2016-11-26 下午4:26:04
 */
public class ClearTradingService extends Service {

	private Context mContext;

	public ClearTradingService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			mContext = getApplicationContext();
			
			ClearToAbnormal();
			ClearToHistory();
			ClearTrading();
			ClearHistoryTrading();
			ClearAbnormalLiushui();
		}
	/**
	 * 清理以上送服务器异常流水
	 */
	private void ClearAbnormalLiushui() {
		// TODO Auto-generated method stub
		TradingAbnormalDao abDao=TradingAbnormalDao.getInstance(mContext);
		List<TradingAbnormalBean> abnormalList=abDao.queryupSabnormalLiushui("is_abnormal_up","20");
		if(abnormalList!=null&&abnormalList.size()>0){
			Integer deleteList = abDao.deleteList(abnormalList);
			LogUtil.e("异常流水删除结果"+deleteList);
		}
	}

	/**
	 * 清理流水表删除失败数据
	 */
	private void ClearTrading() {
		// TODO Auto-generated method stub
		TradingDao tradingDao = TradingDao.getInstance(mContext);
		ArrayList<TradingBean> deleteAbnormalList = tradingDao.queryByIsAbnormalUP(10);
		if(deleteAbnormalList!=null&&deleteAbnormalList.size()>0){
			Integer deleteList = tradingDao.deleteList(deleteAbnormalList);
			LogUtil.e("流水表删除结果"+deleteList);
		}
	}

	/**
	 * 清理历史数据
	 */
	private void ClearHistoryTrading() {
		// TODO Auto-generated method stub
		TradingHistoryDao historyDao = TradingHistoryDao.getInstance(mContext);
		//默认清除一周之前数据
		long clearTime = (Long) SPUtils.get(mContext, SPUtils.CLEAR_TIME, 648000000L);
		long time = System.currentTimeMillis()-clearTime;
		historyDao.ClearTradingByTranList(time);
	}

	/**
	 * 转存历史表失败
	 */
	private void ClearToHistory() {
		// TODO Auto-generated method stub
		TradingDao tradingDao = TradingDao.getInstance(mContext);
		TradingHistoryDao historyDao = TradingHistoryDao.getInstance(mContext);
		ArrayList<TradingBean> toHistorryList = tradingDao.queryByIsAbnormalUP(12);
		if(toHistorryList!=null){
			LogUtil.e("转入历史表失败流水共"+toHistorryList.size()+"条");
			int i  = 0 ;
			for (TradingBean tradingBean : toHistorryList) {
				int saveHistoryTag = historyDao.saveHistoryTrading(new TradingHistoryBean(tradingBean));
				if(saveHistoryTag==1){
					tradingBean.setIsAbnormalUP(10);
					int updateLiushui = tradingDao.updateLiushui(tradingBean);
					if(updateLiushui==1)
						i++;
				}
			}
			LogUtil.e("转存并更新成功"+i+"条");
		}
	}

	/**
	 * 
	 * 转存异常流水转表失败
	 */
	private void ClearToAbnormal() {
		// TODO Auto-generated method stub
		TradingDao tradingDao = TradingDao.getInstance(mContext);
		TradingAbnormalDao abnormalDao = TradingAbnormalDao.getInstance(mContext);
		ArrayList<TradingBean> toAbnomalList = tradingDao.queryByIsAbnormalUP(11);
		if(toAbnomalList!=null){
			LogUtil.e("转入异常表失败流水共"+toAbnomalList.size()+"条");
			int i = 0;
			for (TradingBean tradingBean : toAbnomalList) {
				int saveAbnormalTag = abnormalDao.saveAbnormalTrading(new TradingAbnormalBean(tradingBean));
				if(saveAbnormalTag==1){
					tradingBean.setIsAbnormalUP(10);
					int updateLiushui = tradingDao.updateLiushui(tradingBean);
					if(updateLiushui==1)
						i++;
				}
			}
			LogUtil.e("转存并更新成功"+i+"条");
		}
	}
	
	
	

}


