package com.re;

import com.re.dao.realestate.RealEstateDao;
import com.re.dao.realestate.RealEstateDaoImpl;
import com.re.dao.rehistory.REHistoryDao;
import com.re.dao.rehistory.REHistoryDaoImpl;
import com.re.service.REHistoryService;
import com.re.service.REHistoryServiceImpl;
import com.re.service.RealEstateService;
import com.re.service.RealEstateServiceImpl;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.sql.DataSource;
import java.util.Locale;


@Configuration
public class ContextConfiguration {

    @Bean
    public RealEstateService realEstateService() {
        return new RealEstateServiceImpl(realEstateDao());
    }

    @Bean
    public RealEstateDao realEstateDao() {
        return new RealEstateDaoImpl(jdbcTemplate());
    }

    @Bean
    public REHistoryDao reHistoryDao() {
        return new REHistoryDaoImpl(jdbcTemplate());
    }

    @Bean
    public REHistoryService reHistoryService(){
        return new REHistoryServiceImpl(reHistoryDao());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    @ConfigurationProperties(prefix="datasource.mine")
    public DataSource dataSource() {
        /**
         * Oracle XE don't want work with RU-Locale:(
         */
        //fixme: resolve problem with default spring boot datasource
        Locale.setDefault(Locale.ENGLISH);
        final BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@//pavel:1521/xe");
        ds.setUsername("system");
        ds.setPassword("work");

        /*final OracleDataSource ds = new OracleDataSource();
        String url = "jdbc:oracle:thin:system@//pavel:1521/xe";
        ds.setURL(url);
        java.util.Properties prop = new java.util.Properties();
        prop.put("user", "SYSTEM");
        prop.put("password", "work");
        prop.put("internal_logon", "sysoper");
        ds.setConnectionProperties(prop);*/

        return ds;


    }
}