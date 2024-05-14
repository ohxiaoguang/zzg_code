package com.zzg.config.db;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class BaseDao {

	public final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Map<String, Object> doSQLQueryToMap(DbPoolConnection.DbNameEnum dbName, String sql, Object... args) throws SQLException {
		Map<String, Object> map = null;
		QueryRunner qr = new QueryRunner(DbPoolConnection.getInstance().getDataSource(dbName));
		map = qr.query(sql, new MapHandler(), args);
		return map;
	}

	public List<Map<String, Object>> doSQLQueryToMapList(DbPoolConnection.DbNameEnum dbName, String sql, Object... args) throws SQLException {
		List<Map<String, Object>> map = null;
		QueryRunner qr = new QueryRunner(DbPoolConnection.getInstance().getDataSource(dbName));
		map = qr.query(sql, new MapListHandler(), args);
		return map;
	}

	/**
	 * 只向目标库插数据
	 * @param connection
	 * @param tableName
	 * @param mapData
	 * @return
	 * @throws SQLException
	 */
	public static void doInsert(DbPoolConnection.DbNameEnum dbName, String tableName, Map<String, Object> mapData) throws SQLException {
		Connection connection = null;
		try {
			connection = DbPoolConnection.getInstance().getDataSource(dbName).getConnection();
			doInsert(connection,tableName,mapData);
		}finally {
			if(connection !=null && !connection.isClosed()) {
				connection.close();
			}
		}
	}

	public long doInsertJSON(DbPoolConnection.DbNameEnum dbName, String tableName, Map<String, Object> mapData)
			throws SQLException {
		StringBuffer insertBuf = new StringBuffer();
		Object[] args = new Object[mapData.keySet().size()];
		insertBuf.append("INSERT INTO ");
		insertBuf.append(tableName);
		insertBuf.append(" SET ");
		int index = 0;
		for (Object key : mapData.keySet()) {
			insertBuf.append(key + " = ?,");

			args[index] = mapData.get(key);
			index++;
		}
		String sql = insertBuf.substring(0, insertBuf.length() - 1);

		Connection connection = null;
		BigInteger num = null;
		try {
			connection = DbPoolConnection.getInstance().getDataSource(dbName).getConnection();
			
			QueryRunner qr = new QueryRunner(true);

			qr.update(connection, sql, args);
			num = (BigInteger) qr.query(connection,"select @@identity",new ScalarHandler(1));
		} catch (Exception e) {
			throw e;
		} finally{
			if(connection !=null && !connection.isClosed()) {
				connection.close();
			}
		}

		return num.longValue();
	}
	
	/**
	 * 只向目标库插数据
	 * @param connection
	 * @param tableName
	 * @param mapData
	 * @return
	 * @throws SQLException 
	 */
	public static boolean doInsert(Connection connection, String tableName, Map<String, Object> mapData) throws SQLException {
		boolean bool = false;
		StringBuffer insertBuf = new StringBuffer();
		Object[] args = new Object[mapData.keySet().size()];
		insertBuf.append("INSERT INTO ");
		insertBuf.append(tableName);
		insertBuf.append(" SET ");
		int index = 0;
		for (Object key : mapData.keySet()) {
			insertBuf.append(key + " = ?,");

			args[index] = toString(mapData.get(key));
			index++;
		}
		String sql = insertBuf.substring(0, insertBuf.length() - 1);

		QueryRunner qr = new QueryRunner(true);

		qr.update(connection, sql, args);

		return bool;
	}

	private static String toString(Object o) {
		if (o != null) {
			return o.toString();
		}
		return null;
	}
	
	public static void update(DbPoolConnection.DbNameEnum dbName, String sql, Object... args) throws SQLException {

		QueryRunner qr;

		qr = new QueryRunner(DbPoolConnection.getInstance().getDataSource(dbName));

		qr.update(sql, args);


	}

}