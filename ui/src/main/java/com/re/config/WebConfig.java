package com.re.config;

import com.re.ContextConfiguration;
import com.re.components.history.HistoryTab;
import com.re.components.history.HistoryTable;
import com.re.components.realestate.RETab;
import com.re.components.realestate.RETable;
import com.re.components.realestate.ToolBar;
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
       return new RealEstateView(historyTab(), reTab());
    }

    @Bean
    @UIScope
    public RETab reTab(){
        return new RETab(reTable(), toolBar(), realEstateService());
    }

    @Bean
    @UIScope
    public HistoryTab historyTab(){
        return new HistoryTab(historyTable(), reHistoryService());
    }

    @Bean
    @UIScope
    public HistoryTable historyTable(){
        return new HistoryTable();
    }


    @Bean
    @UIScope
    public RETable reTable(){
        return new RETable(realEstateService(), reHistoryService());
    }

    @Bean
    @UIScope
    public ToolBar toolBar(){
        return new ToolBar();
    }

    @Bean
    @UIScope
    public RootUI rootUI() {
        return new RootUI(realEstateView());
    }
}
