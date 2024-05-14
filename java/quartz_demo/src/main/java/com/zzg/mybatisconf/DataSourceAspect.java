package com.zzg.mybatisconf;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * * 动态处理数据源 根据命名区分
 *
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DataSourceAspect {

	private static Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

	/**
	 * * 切点
	 */
	@Pointcut("execution(* com.zzg.mapper.*.*(..))")
	public void DaoAspect() {
	}

	// 在指定切点的方法之前执行
	@Before("DaoAspect()")
	public void before(JoinPoint point) {
		String method = point.getSignature().getName();
		try {
			for (DatabaseType type : DatabaseType.values()) {
				List<String> values = DynamicDataSource.METHOD_TYPE_MAP.get(type);
				for (String key : values) {
					if (method.startsWith(key)) {
						DatabaseContextHolder.setDatabaseType(type);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
