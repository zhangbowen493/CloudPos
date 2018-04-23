package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.TradingHistoryBean;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
	/*
 *@作者: dingle
 *@版本: 
 *@version create time：2016-11-25 下午2:32:24
 */
public class TradingHistoryDao {

	public static TradingHistoryDao instance;
	private static TradingHistoryHelper helper;
	private Dao dao;
	private Context mContext;
	

	private TradingHistoryDao(Context context) throws SQLException {
		// TODO Auto-generated constructor stub
			mContext = context;
			dao = helper.getDao(TradingHistoryBean.class);
	}
	
	public void close(){
		if(helper!=null)
		helper.close();
		helper = null;
		dao = null;
	}
	
	public static synchronized TradingHistoryDao getInstance(Context context){
		if(helper==null)
			helper = TradingHistoryHelper.getHelper(context);
		if(instance==null){
			synchronized (TradingAbnormalDao.class) {
				
				try {
					instance = new TradingHistoryDao(context);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		return instance;
	}

	/**
	 * 将流水数据存入到流水历史表中
	 * @param liushui
	 * @return
	 */
	public int saveHistoryTrading(final TradingHistoryBean liushui) {
		// TODO 返回1添加成功   -1添加失败
		if(liushui==null)
			return -1;
		
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				return dao.create(liushui);
			}
		};
		try {
			if(helper==null)
				helper = TradingHistoryHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	/**
	 * 批量将流水数据存入到流水历史表中
	 * @param liushui
	 * @return
	 */
	public int saveHistoryTradingList(final List<TradingHistoryBean> list) {
		if(list==null)return -1;
		// TODO 返回1添加成功   -1添加失败
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				return dao.create(list);
			}
		};
		try {
			if(helper==null)
				helper = TradingHistoryHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}

	
	/**
	 * 批量删除指定流水列表
	 * 
	 * @return
	 */
	public Integer deleteList(final List<TradingHistoryBean> lists) {
		if(lists==null)return -1;
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				return dao.delete(lists);
			}
		};
		try {
			if(helper==null)
				helper = TradingHistoryHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
	}
	
	
	/**
	 * 查询所有
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<TradingHistoryBean> queryAllLiushuiList() {
		Callable<List<TradingHistoryBean>> callable = new Callable<List<TradingHistoryBean>>() {
			@Override
			public List<TradingHistoryBean> call() throws Exception {
				// TODO Auto-generated method stub
				return dao.queryForAll();
			}
		};
		try {
			if(helper==null)
				helper = TradingHistoryHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询数据总条数
	 * @return
	 */
	public Long getCount() {
		Callable<Long> callable = new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				// TODO Auto-generated method stub
				return dao.countOf();
			}
		};
		try {
			if (helper == null)
				helper = TradingHistoryHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (long) 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return (long) 0;
		}
	}
	/**
	 * 根据时间清理过期时间
	 * @param time
	 */
	public Integer ClearTradingByTranList(final long time) {
		// TODO Auto-generated method stub
		
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				PreparedDelete prepare = (PreparedDelete) dao.deleteBuilder().where().le("trantime", String.valueOf(time)).prepare();
				return dao.delete(prepare);
			}
		};
		try {
			if(helper==null)
				helper = TradingHistoryHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * 清理所有数据
	 * @return
	 */
	public Long clearAll(){
		Callable<Long> callable = new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				// TODO Auto-generated method stub
				return (long) dao.deleteBuilder().delete();
			}
		};
		try {
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}


