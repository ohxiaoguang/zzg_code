package com.zzg.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库连接配置
 * 代码里引用枚举类型的属性
 *
 */
public class DbPoolConnection {

	public static final DbNameEnum db127 = DbNameEnum.db127;
	
	/*此部分为实现单例代码*/
	private static DbPoolConnection databasePool=null;
	
	private DbPoolConnection() {}
	
	public static synchronized DbPoolConnection getInstance() {
		if (null == databasePool) {
			databasePool = new DbPoolConnection();
		}
		return databasePool;
	}
	/*此部分为实现单例代码*/

	/**
	 * 连接池数据库的枚举,增加时去db_config文件夹增加一个同名配置文件
	 * 我们的数据库名以db开头 数据交换前置以jh开头
	 */
	public enum DbNameEnum {

//		db123,
//		db456,
		db127

	}
	
	
	private static Map<String,DruidDataSource> dds = new HashMap<String,DruidDataSource>();
	
	/**
	 * 根据数据库连接池
	 */
	public DruidDataSource getDataSource(DbNameEnum dbName) throws SQLException {
		return dds.get(dbName.toString());
	}
	
	static {
		for (DbNameEnum dbName : DbNameEnum.values()) {
			try {
				Properties properties = loadPropertyFile("db_config/" + dbName.toString()+".properties");
				dds.put(dbName.toString(), (DruidDataSource) DruidDataSourceFactory.createDataSource(properties));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static Properties loadPropertyFile(String fullFile) {
		
		Properties p = new Properties();
		if (fullFile == null || fullFile.equals("")) {
			System.out.println("属性文件为空!~");
		} else {
			// 加载属性文件
			InputStream inStream = DbPoolConnection.class.getClassLoader().getResourceAsStream(fullFile);
			try {
				p.load(inStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return p;
	}
	
}
