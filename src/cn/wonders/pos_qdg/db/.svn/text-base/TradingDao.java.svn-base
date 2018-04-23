package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

/*
 * 流水
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-20 下午4:44:24
 */
public class TradingDao {
	private Context mContext;
	private Dao<TradingBean, Integer> dao;
	private static TradingDBHelper helper;

	public static TradingDao instance;

	private TradingDao(Context context) throws SQLException {
		this.mContext = context;
		dao = helper.getDao(TradingBean.class);
	}

	public static synchronized TradingDao getInstance(Context context) {
		if (helper == null)
			helper = TradingDBHelper.getHelper(context);
		if (instance == null) {
			synchronized (TradingDao.class) {

				try {
					instance = new TradingDao(context);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
		}
		return instance;
	}

	public void close() {
		if (helper != null)
			helper.close();
		helper = null;
		dao = null;
	}

	/**
	 * 限制总数据条数
	 * 
	 * @param count
	 *            限制最低条数
	 * @return
	 * @throws SQLException
	 */
	public int limitData(final String count) throws SQLException {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					// 倒序 取count 条数据id 不在id集合里 且满足 isupdate（是否上送银联） = 1 并且
					// SendUPTime（上送银联时间） 不为空 并且 synctime（上送后台时间不为空） != 0
					String sql = "delete from tb_tran_list where id not in (select id from tb_tran_list order by id DESC limit "
							+ count
							+ ") and isupdate = 1 and SendUPTime is not null and synctime != 0";
					int status = dao.executeRaw(sql);
					return status;
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * 根据字段名 和 字段值 查找
	 * 
	 * @param columnName
	 *            目前只有员工编号 字段名称
	 * @param columnValue
	 *            值
	 * @return
	 */
	public List<TradingBean> getLiushui(final String columnName,
			final String columnValue) {
		try {
			Callable<List<TradingBean>> callable = new Callable<List<TradingBean>>() {
				@Override
				public List<TradingBean> call() throws Exception {
					// TODO Auto-generated method stub
					return dao.queryForEq(columnName, columnValue);
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 单条流水入库
	 * 
	 * @return
	 */
	public Integer addLiushui(final TradingBean bean) {

		// 事务操作
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws SQLException {
				return dao.create(bean);
			}
		};

		try {
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	/**
	 * 批量加入流水
	 * 
	 * @return
	 */
	public Integer addLiushuiList(final ArrayList<TradingBean> lists) {
		// 事务操作
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws SQLException {
					for (TradingBean bean : lists) {
						int create = dao.create(bean);
						if (create != 1) {
							return create;
						}
					}
					return 1;
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
			return TransactionManager.callInTransaction(
					helper.getConnectionSource(), callable);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 批量更新
	 * 
	 * @throws SQLException
	 */
	public int updateLiushuiList(final ArrayList<TradingBean> lists) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					for (TradingBean bean : lists) {
						int create = dao.update(bean);
						if (create != 1) {
							return create;
						}
					}
					return 1;
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 数据库删除单条流水
	 * 
	 * @param bean
	 * @return
	 */
	public int deleteTrading(final TradingBean bean) {
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				// TODO Auto-generated method stub
				int delete = dao.delete(bean);
				return delete;
			}
		};
		try {
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	public Integer deleteList(final List<TradingBean> lists) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					return dao.delete(lists);
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 更新
	 * 
	 * @throws SQLException
	 */
	public int updateLiushui(final TradingBean bean) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					return dao.update(bean);
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	public List<TradingBean> queryAllLiushuiList() {
		try {
			Callable<List<TradingBean>> callable = new Callable<List<TradingBean>>() {
				@Override
				public List<TradingBean> call() throws Exception {
					// TODO Auto-generated method stub
					return dao.queryForAll();
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 获取未上送银联总条数
	 * 
	 * @return
	 */
	public Long getNoYLUpdateCount() {
		try {
			Callable<Long> callable = new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					// TODO Auto-generated method stub
					QueryBuilder<TradingBean, Integer> queryBuilder = dao
							.queryBuilder();
					queryBuilder.where().eq("isupdate", "0");
					return queryBuilder.countOf();
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 获取上送银联未上送后台总条数
	 * 
	 * @return
	 */
	public Long getupYLnoUPS() {
		try {
			Callable<Long> callable = new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					// TODO Auto-generated method stub
					QueryBuilder<TradingBean, Integer> queryBuilder = dao
							.queryBuilder();
					queryBuilder.where().
					eq("isupdate", "1").and()
					.eq("synctime", "0").and().eq("is_abnormal_up", 1);
					return queryBuilder.countOf();
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 获取未清算列表
	 * 
	 * @return
	 */
	public List<TradingBean> queryNoYLUpdateBean() {
		try {
			Callable<List<TradingBean>> callable = new Callable<List<TradingBean>>() {
				@Override
				public List<TradingBean> call() throws Exception {
					
					QueryBuilder<TradingBean, Integer> queryBuilder = dao
							.queryBuilder();
					queryBuilder.where().eq("isupdate", "0");
					queryBuilder.limit(150);
					return queryBuilder.query();
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 获取未清算数目
	 * 
	 * @return
	 */
	public Long queryNoYLUpdateNum() {
		try {
			Callable<Long> callable = new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					// TODO Auto-generated method stub
					QueryBuilder<TradingBean, Integer> queryBuilder = dao
							.queryBuilder();
					queryBuilder.where().eq("isupdate", "0");
					return queryBuilder.countOf();
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 获取指定参数流水
	 * 
	 * @return
	 */
	public List<TradingBean> queryParametersBean(final String key,
			final String value) {
		try {
			Callable<List<TradingBean>> callable = new Callable<List<TradingBean>>() {
				@Override
				public List<TradingBean> call() throws Exception {
					// TODO Auto-generated method stub
					return dao.queryForEq(key, value);
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 获取 上送到银联 但没有上送到后台的正常流水
	 * 
	 * @param isLimit
	 *            表示是否限制删选数量
	 */
	public List<TradingBean> queryupYLnoUPS(final boolean isLimit) {

		try {
			Callable<List<TradingBean>> callable = new Callable<List<TradingBean>>() {
				@SuppressWarnings("deprecation")
				@Override
				public List<TradingBean> call() throws Exception {
					// TODO Auto-generated method stub
					QueryBuilder<TradingBean, Integer> queryBuilder = dao
							.queryBuilder();
					queryBuilder.where().eq("isupdate", "1").and()
					.eq("synctime", "0").and().eq("is_abnormal_up", 1);
					if (isLimit) {
						queryBuilder.limit(150);
					}
					return queryBuilder.query();
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 获取 上送到银联且上送到后台的流水
	 */
	public List<TradingBean> queryupYLupUPS() {

		try {
			Callable<List<TradingBean>> callable = new Callable<List<TradingBean>>() {
				@Override
				public List<TradingBean> call() throws Exception {
					// TODO Auto-generated method stub
					QueryBuilder<TradingBean, Integer> queryBuilder = dao
							.queryBuilder();
					queryBuilder.where().eq("isupdate", "1").and()
					.ne("synctime", "0");
					return queryBuilder.query();
				}
			};
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
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
	 * 查询不同异常流水
	 * 
	 * @param i
	 * @return
	 */
	public ArrayList<TradingBean> queryByIsAbnormalUP(final int i) {
		Callable<List<TradingBean>> callable = new Callable<List<TradingBean>>() {
			@Override
			public List<TradingBean> call() throws Exception {
				// TODO Auto-generated method stub
				QueryBuilder<TradingBean, Integer> queryBuilder = dao
						.queryBuilder();
				queryBuilder.where().eq("is_abnormal_up", i + "");
				return queryBuilder.query();
			}
		};
		try {
			if (helper == null)
				helper = TradingDBHelper.getHelper(mContext);
			return (ArrayList<TradingBean>) TransactionManager
					.callInTransaction(helper.getConnectionSource(), callable);
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
				helper = TradingDBHelper.getHelper(mContext);
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


}