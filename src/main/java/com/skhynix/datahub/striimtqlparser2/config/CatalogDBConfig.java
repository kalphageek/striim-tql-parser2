package com.skhynix.datahub.striimtqlparser2.config;

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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix="spring.datasource.hikari.catalog")
@EnableJpaRepositories(
  entityManagerFactoryRef = Constants.CatalogEntityManager,
  transactionManagerRef = Constants.CatalogTransactionManager,
  basePackages = { "com.skhynix.datahub.striimtqlparser2.catalog.repository" }
)
public class CatalogDBConfig  extends HikariConfig {

	@Bean(name = Constants.CatalogDataSource)
	public DataSource batchDataSource() {
		return new LazyConnectionDataSourceProxy(new HikariDataSource(this));
	}

	@Bean(name = Constants.CatalogEntityManager)
	public EntityManagerFactory batchEntityManagerFactory() {
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(this.batchDataSource());
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setPackagesToScan("com.skhynix.datahub.striimtqlparser2.catalog.entity");
//		factoryBean.setPersistenceUnitName(Constants.CatalogEntityManager);
		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}

	@Bean(name = Constants.CatalogTransactionManager)
	public PlatformTransactionManager batchTransactionManager(
			@Qualifier(Constants.CatalogEntityManager) EntityManagerFactory batchEntityManagerFactory) {
		return new JpaTransactionManager(batchEntityManagerFactory);
	}
}