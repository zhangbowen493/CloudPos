package cn.wonders.pos_qdg.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/*
 *@作者: Administrator
 *@版本: 
 *@version create time：2016-6-17 下午5:18:49
 *
 *	999999  为管理员帐号   初始密码：000000
 *
 */
@DatabaseTable(tableName = "tb_user")
public class User {
	public static final String adminAccount ="0";
	public static final String adminPassword ="0";
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = "account" )
	private String Account;
	@DatabaseField(columnName = "password")
	private String Password;
	@DatabaseField(columnName = "level")
	private int Level; //等级   1 管理员   0  操作员

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}


	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}


	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", Account=" + Account + ", Password="
				+ Password + ", Level=" + Level + "]";
	}

	
	
}
