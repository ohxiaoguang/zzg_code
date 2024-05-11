package com.zzg.mybatisconf;

/**
 * * 保存一个线程安全的 DatabaseType 容器
 *
 */
public class DatabaseContextHolder {
	
    /**
     * * 用于存放多线程环境下的成员变量
     */
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type) {
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType() {
        return contextHolder.get();
    }
}
