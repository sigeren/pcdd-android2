package com.df.youle.manager;

import com.df.youle.network.bean.UserInfo;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.DbUtils.DbUpgradeListener;
import com.df.youle.app.PcddApp;

public class DBManager implements DbUpgradeListener {

	private static DBManager instance;
	
	private DbUtils db;
	private String DB_NAME = "ziwu_pc";
	private int DB_VERSION = 1;
	
	private DBManager() {
		db = DbUtils.create(PcddApp.applicationContext, DB_NAME, DB_VERSION, this);
	}
	
	public static synchronized DBManager getInstance() {
		if(instance == null) {
			synchronized (DBManager.class) {
				if (instance == null)
					instance = new DBManager();
			}
		}
		return instance;
	}
	
	public DbUtils getDB() {
		return db;
	}

	@Override
	public void onUpgrade(DbUtils db, int arg1, int arg2) {
		try {
			UserInfoManager.setUserId(PcddApp.applicationContext, 0);
			UserInfoManager.setLoginStatus(PcddApp.applicationContext, false);
			db.dropTable(UserInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
