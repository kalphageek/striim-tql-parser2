package com.skhynix.datahub.striimtqlparser2.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.skhynix.datahub.striimtqlparser2.common.Constants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ConfigurationProperties(prefix="spring.datasource.hikari.batch")
@EnableJpaRepositories(
	basePackages = { "com.skhynix.datahub.striimtqlparser2.batch" },
  	entityManagerFactoryRef = Constants.BatchEntityManager,
  	transactionManagerRef = Constants.BatchTransactionManager
)
public class BatchDBConfig extends HikariConfig {
	@Primary
	@Bean
	public DataSource batchDataSource() {
	    return new LazyConnectionDataSourceProxy(new HikariDataSource(this));
	}

	@Primary
	@Bean(name = Constants.BatchEntityManager)
	public EntityManagerFactory batchEntityManagerFactory() {
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(this.batchDataSource());
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setPackagesToScan("com.skhynix.datahub.striimtqlparser2.batch");
		factoryBean.setPersistenceUnitName("batch");
		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}

	@Primary
	@Bean(name = Constants.BatchTransactionManager)
	public PlatformTransactionManager batchTransactionManager(
			@Qualifier(Constants.BatchEntityManager) EntityManagerFactory batchEntityManagerFactory) {
		return new JpaTransactionManager(batchEntityManagerFactory);
	}
}