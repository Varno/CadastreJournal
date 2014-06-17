package com.re.config;

import com.re.ContextConfiguration;
import com.re.components.RETable;
import com.re.components.ToolBar;
import com.re.ui.RootUI;
import com.re.views.RealEstateView;
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
    public RealEstateView realEstateView(){
       return new RealEstateView(reTable(), toolBar(), realEstateService());
    }

    @Bean
    @UIScope
    public RETable reTable(){
        return new RETable(reHistoryService());
    }

    @Bean
    @UIScope
    public ToolBar toolBar(){
        return new ToolBar();
    }

    @Bean
    @UIScope
    public RootUI rootUI() throws java.sql.SQLException{
        return new RootUI(realEstateView());
    }
}
