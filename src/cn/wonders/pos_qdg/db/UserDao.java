package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import cn.wonders.pos_qdg.bean.User;
import cn.wonders.pos_qdg.util.LogUtil;
import android.content.Context;

/*
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-5-26 下午4:50:25
 */
public class UserDao {

	private Context mContext;
	private Dao<User, Integer> userDaoOpe;
	private UserDatabaseHelper helper;

	public UserDao(Context context) {
		this.mContext = context;
		try {
			helper = UserDatabaseHelper.getHelper(mContext);

			userDaoOpe = helper.getDao(User.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 根据字段名 和 字段值 查找
	 * 
	 * @param columnName
	 *            字段名称
	 * @param columnValue
	 *            值
	 * @return
	 */
	public List<User> getUser(final String columnName, final String columnValue) {
		try {
			Callable<List<User>> callable = new Callable<List<User>>() {
				@Override
				public List<User> call() throws Exception {
					// TODO Auto-generated method stub
						return userDaoOpe.queryForEq(columnName, columnValue);
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
	 * 批量加入用户
	 * 
	 * @return
	 */
	public Integer addUserList(final ArrayList<User> userList) {
		// 事务操作
		try {
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws SQLException {
				for (User user : userList) {
					int create = userDaoOpe.create(user);
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
				return (long) userDaoOpe.deleteBuilder().delete();
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
	public int updateUser(final User user) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
						return userDaoOpe.update(user);
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
	 * 删除用户
	 * 
	 * @throws SQLException
	 */
	public int deleteUser(final User user) {
		
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
						return userDaoOpe.delete(user);
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
	 * 查询所有用户
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<User> queryAllUser()  {
		try {
		Callable<List<User>> callable = new Callable<List<User>>() {
			@Override
			public List<User> call() throws Exception {
				// TODO Auto-generated method stub
					return userDaoOpe.queryBuilder().orderBy("id", false).query();
//					return userDaoOpe.queryForAll();
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
	 * 获取全部
	 * 
	 * @return
	 */
	public List<User> getAllUser() {
		try {
			return userDaoOpe.queryBuilder().orderBy("id", true).query();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取最近的添加用户
	 * 
	 * @return
	 */
	public User getLatelyOrder() {
		try {
			return userDaoOpe.queryBuilder().orderBy("id", false)
					.queryForFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
