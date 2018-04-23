package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.QueryBuilder;

import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;
import android.content.Context;

/*
 *@作者: dingle
 *@版本: 
 *@version create time：2016-11-24 上午10:49:22
 */
public class TradingAbnormalDao {

	public static TradingAbnormalDao instance;
	private static TradingAbnormalDBHelper helper;
	private Dao<TradingAbnormalBean, Integer> dao;
	private Context mContext;

	private TradingAbnormalDao(Context context) throws SQLException {
		// TODO Auto-generated constructor stub
		mContext = context;
		dao = helper.getDao(TradingAbnormalBean.class);
	}

	public void close() {
		if(helper!=null)
		helper.close();
		helper = null;
		dao = null;
	}

	public static synchronized TradingAbnormalDao getInstance(Context context) {
		if(helper==null)
			helper = TradingAbnormalDBHelper.getHelper(context);
		if (instance == null) {
			synchronized (TradingAbnormalDao.class) {

				try {
					instance = new TradingAbnormalDao(context);
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
				helper = TradingAbnormalDBHelper.getHelper(mContext);
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
	 * 批量加入流水
	 * 
	 * @return
	 */
	public Integer addLiushui(final ArrayList<TradingAbnormalBean> lists) {
		if(lists==null)return -1;
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				for (TradingAbnormalBean blackr : lists) {
					 int create = dao.create(blackr);
					if (create < 1) {
						return -1;
					}
				}
				return 1;
			}
		};
		try {
			if(helper == null)
				helper = TradingAbnormalDBHelper.getHelper(mContext);
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
	 * 数据库插入单条异常流水
	 * 
	 * @param bean
	 * @return
	 */
	public int saveAbnormalTrading(final TradingAbnormalBean bean) {
		if (bean == null) {
			return -1;
		}
		// 事务操作
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws SQLException {
				return dao.create(bean);
			}
		};
		try {
			if(helper == null)
				helper = TradingAbnormalDBHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
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
	public List<TradingAbnormalBean> queryAllLiushuiList() {
		Callable<List<TradingAbnormalBean>> callable = new Callable<List<TradingAbnormalBean>>() {
			@Override
			public List<TradingAbnormalBean> call() throws Exception {
				// TODO Auto-generated method stub
				return dao.queryForAll();
			}
		};
		try {
			if(helper == null)
				helper = TradingAbnormalDBHelper.getHelper(mContext);
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
	
	/**
	 * 批量更新异常流水
	 * 
	 * @throws SQLException
	 */
	public int updateLiushuiList(final List<TradingAbnormalBean> lists) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					for (TradingAbnormalBean bean : lists) {
						int create = dao.update(bean);
						if (create != 1) {
							return create;
						}
					}
					return 1;
				}
			};
			if (helper == null)
				helper = TradingAbnormalDBHelper.getHelper(mContext);
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
	 * 获取 以上送服务器异常流水
	 */
	public List<TradingAbnormalBean> queryupSabnormalLiushui(final String key,final String value) {

		try {
			Callable<List<TradingAbnormalBean>> callable = new Callable<List<TradingAbnormalBean>>() {
				@Override
				public List<TradingAbnormalBean> call() throws Exception {
					// TODO Auto-generated method stub
					QueryBuilder<TradingAbnormalBean, Integer> queryBuilder = dao
							.queryBuilder();
					queryBuilder.where().eq(key, value);
					return queryBuilder.query();
				}
			};
			if (helper == null)
				helper = TradingAbnormalDBHelper.getHelper(mContext);
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
	 * 更新
	 * 
	 * @throws SQLException
	 */
	public int updateAbnormalLiushui(final TradingAbnormalBean bean) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					return dao.update(bean);
				}
			};
			if (helper == null)
				helper = TradingAbnormalDBHelper.getHelper(mContext);
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
	 * 删除指定流水列表
	 * 
	 * @return
	 */
	public Integer deleteList(final List<TradingAbnormalBean> lists) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					return dao.delete(lists);
				}
			};
			if (helper == null)
				helper = TradingAbnormalDBHelper.getHelper(mContext);
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
}
