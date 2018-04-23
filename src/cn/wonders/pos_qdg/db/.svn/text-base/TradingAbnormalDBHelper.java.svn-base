package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import cn.wonders.pos_qdg.bean.TradingAbnormalBean;
import cn.wonders.pos_qdg.bean.TradingBean;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
	/*
	 * 异常交易流水数据DB帮助类
 *@作者: dingle
 *@版本: 
 *@version create time：2016-11-24 上午10:54:12
 */
public class TradingAbnormalDBHelper extends OrmLiteSqliteOpenHelper{

private static final String TABLE_NAME = "pos_abnormal_trading.db";
	
	private Map<String, Dao> daos = new HashMap<String, Dao>();
	
	private static TradingAbnormalDBHelper instance;
	
	private TradingAbnormalDBHelper(Context context)
	{
		super(context, TABLE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		try
		{
			TableUtils.createTable(connectionSource,TradingAbnormalBean.class);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource arg1, int oldVersion,
			int newVersion) {
		// TODO Auto-generated method stub
		Log.e(TABLE_NAME, "oldVersion="+oldVersion +"newVersion="+newVersion);
//		try
//		{
//			TableUtils.dropTable(connectionSource, AbnormalTradingBean.class, true);
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
	public static synchronized TradingAbnormalDBHelper getHelper(Context context)
	{
		if (instance == null)
		{
			synchronized (TradingAbnormalDBHelper.class)
			{
				if (instance == null)
					instance = new TradingAbnormalDBHelper( context.getApplicationContext());
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


