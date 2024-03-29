package com.nutrimate.nutrimatebackend.config;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration
@MapperScan(value = {"com.nutrimate.nutrimatebackend.mapper"},
		sqlSessionFactoryRef = "sqlSessionFactory")
@EnableTransactionManagement
@Log4j2
public class MybatisConfig {
	// https://mybatis.org/spring/ko/factorybean.html
	
	// 생성자 인젝션을 통해 ApplicationContext를 컨테이너로부터 받는다
	private final ApplicationContext applicationContext;
	
	public MybatisConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	// 마이바티스 관련 빈
	@Bean
	SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
		SqlSessionFactory factory = null;
		try {
			SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
			factoryBean.setDataSource(dataSource);// 데이타 소스로 히카리 전달
			// 타입 별칭을 적용할 최상위 패키지 경로 설정
			// (마이바티스 프레임워크는 최상위 패키지부터 하위 패키지까지 @Alias가 적용됨)
			factoryBean.setTypeAliasesPackage("com.nutrimate.nutrimatebackend");
			factoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/**/*.xml"));
			org.apache.ibatis.session.Configuration configuration =
					new org.apache.ibatis.session.Configuration();
			configuration.setMapUnderscoreToCamelCase(true);
			factoryBean.setConfiguration(configuration);
			factory = factoryBean.getObject();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return factory;
		
	}
	
	@Bean
	SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}////////////////
}
