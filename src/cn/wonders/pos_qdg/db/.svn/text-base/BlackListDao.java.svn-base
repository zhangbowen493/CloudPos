package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import cn.wonders.pos_qdg.bean.BlackList;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
/*
 * 黑名单数据库操作类
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-20 下午4:44:24
 */
public class BlackListDao {
	private Context mContext;
	private Dao<BlackList, Integer> BlackDaoOpe;
	private BlackListDBHelper helper;

	public BlackListDao(Context context) {
		this.mContext = context;
		try {
			helper = BlackListDBHelper.getHelper(mContext);

			BlackDaoOpe = helper.getDao(BlackList.class);
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
	public List<BlackList> getBlackList(final String columnName, final String columnValue) {
		try {
			Callable<List<BlackList>> callable = new Callable<List<BlackList>>() {
				@Override
				public List<BlackList> call() throws Exception {
					// TODO Auto-generated method stub
					return BlackDaoOpe.queryForEq(columnName, columnValue);
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
	public Integer addBlackList(final ArrayList<BlackList> blackLists) {

		if(blackLists!=null && blackLists.size()>0)
			clearAll();


		// 事务操作
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws SQLException {
					for (BlackList blackList : blackLists) {
						int create = BlackDaoOpe.create(blackList);
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
				return (long) BlackDaoOpe.deleteBuilder().delete();
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
	public int updateBlack(final BlackList bean) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					return BlackDaoOpe.update(bean);
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
	public List<BlackList> queryAllBlackList()  {
		try {
			Callable<List<BlackList>> callable = new Callable<List<BlackList>>() {
				@Override
				public List<BlackList> call() throws Exception {
					// TODO Auto-generated method stub
					return BlackDaoOpe.queryForAll();
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


