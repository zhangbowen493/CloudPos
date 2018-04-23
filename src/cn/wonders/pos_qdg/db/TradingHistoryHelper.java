package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;
import cn.wonders.pos_qdg.bean.TradingHistoryBean;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
	/*
 *@作者: dingle
 *@版本: 
 *@version create time：2016-11-25 下午2:21:03
 */
public class TradingHistoryHelper extends OrmLiteSqliteOpenHelper{

private static final String TABLE_NAME = "pos_trading_history.db";
	
	private Map<String, Dao> daos = new HashMap<String, Dao>();
	
	private static TradingHistoryHelper instance;
	
	private TradingHistoryHelper(Context context)
	{
		super(context, TABLE_NAME, null, 1);
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		try
		{
			TableUtils.createTable(connectionSource,TradingHistoryBean.class);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int oldVersion,
			int newVersion) {
		// TODO Auto-generated method stub
//		Log.e(TABLE_NAME, "oldVersion="+oldVersion +"newVersion="+newVersion);
//		if(oldVersion<7 ){
//			try {
//				getDao(TradingBean.class).executeRaw("ALTER TABLE `tb_tran_list` ADD COLUMN is_abnormal_up TEXT DEFAULT 0;");
//				getDao(TradingBean.class).executeRaw("ALTER TABLE `tb_tran_list` ADD COLUMN batch_number TEXT DEFAULT 0;");
//				getDao(TradingBean.class).executeRaw("ALTER TABLE `tb_tran_list` ADD COLUMN abnormal_code TEXT DEFAULT 0;");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				
//			}
//		try
//		{
//			TableUtils.dropTable(connectionSource, TradingBean.class, true);
//			onCreate(database, connectionSource);
//		} catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
	
	}
	

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized TradingHistoryHelper getHelper(Context context)
	{
		if (instance == null)
		{
			synchronized (TradingHistoryHelper.class)
			{
				if (instance == null)
					instance = new TradingHistoryHelper( context.getApplicationContext());
			}
		}

		return instance;
	}

	public synchronized Dao getDao(Class clazz) throws SQLException
	{
		Dao dao = null;
		String className = clazz.getSimpleName();

		if (daos.containsKey(className))
		{
			dao = daos.get(className);
		}
		if (dao == null)
		{
			dao = super.getDao(clazz);
			daos.put(className, dao);
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@Override
	public void close()
	{
		super.close();

		for (String key : daos.keySet())
		{
			Dao dao = daos.get(key);
			dao = null;
		}
	}
	

}


