package com.zzg.mybatisconf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * * 数据源配置
 *
 */
@Configuration
@Order(1)
@EnableTransactionManagement
public class DataSourceConfig {

	/**
	 * * 注入类 Environment 
	 * * 可以很方便的获取配置文件中的参数
	 */
	@Autowired
	private Environment env;

	@Value("${spring.datasource.druid.filters}")
	private String filters;

	@Value("${spring.datasource.druid.initial-size}")
	private Integer initialSize;

	@Value("${spring.datasource.druid.min-idle}")
	private Integer minIdle;

	@Value("${spring.datasource.druid.max-active}")
	private Integer maxActive;

	@Value("${spring.datasource.druid.max-wait}")
	private Integer maxWait;

	@Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
	private Long timeBetweenEvictionRunsMillis;

	@Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
	private Long minEvictableIdleTimeMillis;

	@Value("${spring.datasource.druid.validation-query}")
	private String validationQuery;

	@Value("${spring.datasource.druid.test-while-idle}")
	private Boolean testWhileIdle;

	@Value("${spring.datasource.druid.test-on-borrow}")
	private boolean testOnBorrow;

	@Value("${spring.datasource.druid.test-on-return}")
	private boolean testOnReturn;

	@Value("${spring.datasource.druid.pool-prepared-statements}")
	private boolean poolPreparedStatements;

	@Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
	private Integer maxPoolPreparedStatementPerConnectionSize;

	/**
	 * 通过Spring JDBC 快速创建 DataSource
	 * 
	 * @return
	 */
	@Bean(name = "masterDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DataSource masterDataSource() throws SQLException {
		return this.initDruidDataSource();
	}

	/**
	 * * 手动创建DruidDataSource * 通过DataSourceProperties * 读取配置
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Bean(name = "slaveDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.slave")
	public DataSource slaveDataSource() throws SQLException {
		return this.initDruidDataSource();
	}




	/**
	 * 初始化DruidDataSource并设置spring.datasource.druid属性
	 * @return
	 * @throws SQLException
	 */
	private DruidDataSource initDruidDataSource() throws SQLException {
		DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		dataSource.setFilters(filters);
		dataSource.setInitialSize(initialSize);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		return dataSource;
	}

	/**
	 * * 构造多数据源连接池 * Master 数据源连接池采用 HikariDataSource * Slave 数据源连接池采用
	 * DruidDataSource
	 * 
	 * @param master
	 * @param slave
	 * @return
	 */
	@Bean
	@Primary
	public DynamicDataSource dataSource(
			@Qualifier("masterDataSource") DataSource master,
			@Qualifier("slaveDataSource") DataSource slave
	) {
		Map<Object, Object> targetDataSources = new HashMap<>(8);
		targetDataSources.put(DatabaseType.master, master);
		targetDataSources.put(DatabaseType.slave, slave);

		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(slave);
		String read = env.getProperty("spring.datasource.read");
		dataSource.setMethodType(DatabaseType.slave, read);
		String write = env.getProperty("spring.datasource.write");
		dataSource.setMethodType(DatabaseType.master, write);

		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DynamicDataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
