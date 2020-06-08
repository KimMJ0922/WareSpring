package database.config;

import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.zaxxer.hikari.HikariDataSource;

@Configuration 
@MapperScan(value="card.*",sqlSessionFactoryRef="oracleSqlSessionFactory") 
public class DatabaseConfig {
	@Bean(name = "oracleDataSource", destroyMethod = "close")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource oracleDataSource() 
	{ 
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	} 
	@Bean(name = "oracleSqlSessionFactory") 
	@Primary public SqlSessionFactory oracleSqlSessionFactory(@Qualifier("oracleDataSource") DataSource 
			   oracleDataSource, ApplicationContext applicationContext) throws Exception 
	{ 
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(oracleDataSource);
		//mapper config 파일 지정
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mapper/mybatis-config.xml"));
		
		//mapper xml 직접 지정
		//sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mapper/*.xml")); 

		return sqlSessionFactoryBean.getObject();
		} 
	@Bean(name = "oracleSqlSessionTemplate") 
	@Primary 
	public SqlSessionTemplate oracleSqlSessionTemplate(SqlSessionFactory oracleSqlSessionFactory) throws Exception 
	{ 
		return new SqlSessionTemplate(oracleSqlSessionFactory);
	} 

}