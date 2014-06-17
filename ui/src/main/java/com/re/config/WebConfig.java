package com.re.config;

import com.re.ContextConfiguration;
import com.re.components.Pagination;
import com.re.components.REItemCard;
import com.re.components.RealEstateList;
import com.re.ui.RootUI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;
import org.vaadin.spring.UIScope;

public class WebConfig extends ContextConfiguration {
    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {

        return new RequestContextListener();
    }

    @Bean
    @UIScope
    public Pagination pagination(){
       return new Pagination();
    }

    @Bean
    @UIScope
    public RealEstateList realEstateList() throws java.sql.SQLException{
        return new RealEstateList(realEstateService(), pagination(), reHistoryService());
    }

    @Bean
    @UIScope
    public RootUI rootUI() throws java.sql.SQLException{
        return new RootUI(realEstateList());
    }
}
