package com.re;

import com.re.dao.destination.DestinationDao;
import com.re.dao.destination.DestinationDaoImpl;
import com.re.dao.document.REDocumentDao;
import com.re.dao.document.REDocumentDaoImpl;
import com.re.dao.realestate.RealEstateDao;
import com.re.dao.realestate.RealEstateDaoImpl;
import com.re.dao.rehistory.REHistoryDao;
import com.re.dao.rehistory.REHistoryDaoImpl;
import com.re.dao.usage.UsageDao;
import com.re.dao.usage.UsageDaoImpl;
import com.re.service.*;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Locale;


@Configuration
public class ContextConfiguration {
    @Bean
    public RealEstateDao realEstateDao() {
        return new RealEstateDaoImpl(jdbcTemplate());
    }

    @Bean
    public DestinationDao destinationDao() {
        return new DestinationDaoImpl(jdbcTemplate());
    }

    @Bean
    public UsageDao usageDao() {
        return new UsageDaoImpl(jdbcTemplate());
    }

    @Bean
    public REHistoryDao reHistoryDao() {
        return new REHistoryDaoImpl(jdbcTemplate());
    }

    @Bean
    public REDocumentDao reDocumentDao() {
        return new REDocumentDaoImpl(jdbcTemplate());
    }

    @Bean
    public REDocumentService reDocumentService() {
        return new REDocumentServiceImpl(reDocumentDao());
    }

    @Bean
    public RealEstateService realEstateService() {
        return new RealEstateServiceImpl(realEstateDao(), destinationDao(), usageDao(), reDocumentDao());
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
    public DataSource dataSource() {
        Locale.setDefault(Locale.ENGLISH);
        final BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@//localhost:1521/xe");
        ds.setUsername("RRTEST");
        ds.setPassword("1111");

        return ds;
    }
}