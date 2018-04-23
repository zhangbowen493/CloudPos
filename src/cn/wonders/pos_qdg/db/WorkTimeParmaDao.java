package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import cn.wonders.pos_qdg.bean.WorkTimeParma;
import cn.wonders.pos_qdg.util.TimerTools;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

/*
 * 食堂营业配置参数
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-5-26 下午4:50:25
 */
public class WorkTimeParmaDao {

	private Context mContext;
	private Dao<WorkTimeParma, Integer> workTimeDaoOpe;
	private WorkTimeParmaDatabaseHelper helper;

	public WorkTimeParmaDao(Context context) {
		this.mContext = context;
		try {
			helper = WorkTimeParmaDatabaseHelper.getHelper(mContext);

			workTimeDaoOpe = helper.getDao(WorkTimeParma.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前餐别
	 * @return
	 */
	public WorkTimeParma getCB(){
		WorkTimeParma CB_Str = null;
		
		List<WorkTimeParma> list = queryAllWorkTime();
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				WorkTimeParma parma = list.get(i);
				String startTime = parma.getStartTime();
				String endTime = parma.getEndTime();
				if(TimerTools.isEate(startTime, endTime)){
					CB_Str = parma;
					return CB_Str;
				}
			}
		}
		return CB_Str;
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
	public List<WorkTimeParma> getWorkTime(final String columnName, final String columnValue) {
		try {
			Callable<List<WorkTimeParma>> callable = new Callable<List<WorkTimeParma>>() {
				@Override
				public List<WorkTimeParma> call() throws Exception {
					// TODO Auto-generated method stub
						return workTimeDaoOpe.queryForEq(columnName, columnValue);
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
	public Integer addWorkTimeList(final ArrayList<WorkTimeParma> list) {
		
		if(list!=null && list.size()>0)
		clearAll();
		// 事务操作
		try {
		Callable<Integer> callable = new Callable<Integer>() {
			@Override
			public Integer call() throws SQLException {
				for (WorkTimeParma bean : list) {
					int create = workTimeDaoOpe.create(bean);
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
				return (long) workTimeDaoOpe.deleteBuilder().delete();
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
	public int updateWorkTime(final WorkTimeParma user) {
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
						return workTimeDaoOpe.update(user);
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
	public int deleteWorkTime(final WorkTimeParma bean) {
		
		try {
			Callable<Integer> callable = new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
						return workTimeDaoOpe.delete(bean);
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
	public List<WorkTimeParma> queryAllWorkTime()  {
		try {
		Callable<List<WorkTimeParma>> callable = new Callable<List<WorkTimeParma>>() {
			@Override
			public List<WorkTimeParma> call() throws Exception {
				// TODO Auto-generated method stub
					return workTimeDaoOpe.queryBuilder().orderBy("id", false).query();
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
	 * 获取最近的添加用户
	 * 
	 * @return
	 */
	public WorkTimeParma getLatelyOrder() {
		try {
			return workTimeDaoOpe.queryBuilder().orderBy("id", false)
					.queryForFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
