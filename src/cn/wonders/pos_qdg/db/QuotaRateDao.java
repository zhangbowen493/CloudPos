package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import cn.wonders.pos_qdg.bean.BlackList;
import cn.wonders.pos_qdg.bean.QuotaRate;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
/**
 * 卡类型定额消费折扣数据库操作类
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-20 下午4:44:24
 */
public class QuotaRateDao {
	private Context mContext;
	private Dao<QuotaRate, Integer> quotaRateDaoOpe;
	private QuotaRateDBHelper helper;

	public QuotaRateDao(Context context) {
		this.mContext = context;
		try {
			helper = QuotaRateDBHelper.getHelper(mContext);

			quotaRateDaoOpe = helper.getDao(QuotaRate.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 根据字段名 和 字段值 查找
	 * 
	 * @param columnName	目前只有员工编号 
	 *            字段名称
	 * @param columnValue
	 *            值
	 * @return
	 */
	public List<QuotaRate> getQuotaRate(final String columnName, final String columnValue) {
		try {
			Callable<List<QuotaRate>> callable = new Callable<List<QuotaRate>>() {
				@Override
				public List<QuotaRate> call() throws Exception {
					// TODO Auto-generated method stub
					return quotaRateDaoOpe.queryForEq(columnName, columnValue);
				}
			};
			return TransactionManager.callInTransaction(helper.getConnectionSource(), callable);
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
	 * 批量加入黑名单
	 * 
	 * @return
	 */
	public Integer addQuotaRate(final ArrayList<QuotaRate> lists) {
		if(lists!=null && lists.size()>0)
			clearAll();
		// 事务操作
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws SQLException {
					for (QuotaRate bean : lists) {
						int create = quotaRateDaoOpe.create(bean);
						if (create != 1) {
							return create;
						}
					}
					return 1;
				}
			};
			return  TransactionManager.callInTransaction(helper.getConnectionSource(),callable);
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
	 * 清理所有数据
	 * @return
	 */
	public Long clearAll(){
		Callable<Long> callable = new Callable<Long>() {
			@Override
			public Long call() throws Exception {
				// TODO Auto-generated method stub
				return (long) quotaRateDaoOpe.deleteBuilder().delete();
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
	 * 更新用户
	 * 
	 * @throws SQLException
	 */
	public int updateBlack(final QuotaRate bean) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					return quotaRateDaoOpe.update(bean);
				}
			};
			return TransactionManager.callInTransaction(helper.getConnectionSource(),callable);
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
	public List<QuotaRate> queryAllList()  {
		try {
			Callable<List<QuotaRate>> callable = new Callable<List<QuotaRate>>() {
				@Override
				public List<QuotaRate> call() throws Exception {
					// TODO Auto-generated method stub
					return quotaRateDaoOpe.queryForAll();
				}
			};
			return TransactionManager.callInTransaction(helper.getConnectionSource(), callable);
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


