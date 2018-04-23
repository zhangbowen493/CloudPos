package cn.wonders.pos_qdg.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import cn.wonders.pos_qdg.bean.WhiteList;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
	/*
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-20 下午4:45:02
 */
public class WhiteListDBHelper extends OrmLiteSqliteOpenHelper {
	
	private static final String TABLE_NAME = "sqlite-pos_white.db";
	
	private Map<String, Dao> daos = new HashMap<String, Dao>();
	
	private static WhiteListDBHelper instance;
	
	
	private WhiteListDBHelper(Context context)
	{
		super(context, TABLE_NAME, null, 6);
	}
	

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized WhiteListDBHelper getHelper(Context context)
	{
		context = context.getApplicationContext();
		if (instance == null)
		{
			synchronized (UserDatabaseHelper.class)
			{
				if (instance == null)
					instance = new WhiteListDBHelper(context);
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
	
	
	

//	public WhiteListDBHelper(Context context, String databaseName,
//			CursorFactory factory, int databaseVersion) {
//		super(context, databaseName, factory, databaseVersion);
//		// TODO Auto-generated constructor stub
//	}
//
//	public WhiteListDBHelper(Context context, String databaseName,
//			CursorFactory factory, int databaseVersion, File configFile) {
//		super(context, databaseName, factory, databaseVersion, configFile);
//		// TODO Auto-generated constructor stub
//	}
//
//	public WhiteListDBHelper(Context arg0, String arg1, CursorFactory arg2,
//			int arg3, InputStream arg4) {
//		super(arg0, arg1, arg2, arg3, arg4);
//		// TODO Auto-generated constructor stub
//	}
//
//	public WhiteListDBHelper(Context context, String databaseName,
//			CursorFactory factory, int databaseVersion, int configFileId) {
//		super(context, databaseName, factory, databaseVersion, configFileId);
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		// TODO Auto-generated method stub
		try
		{
			TableUtils.createTable(connectionSource, WhiteList.class);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		try
		{
			TableUtils.dropTable(connectionSource, WhiteList.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

}


