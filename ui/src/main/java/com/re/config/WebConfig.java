package com.re.config;

import com.re.ContextConfiguration;
import com.re.components.history.HistoryTab;
import com.re.components.history.HistoryTable;
import com.re.components.realestate.RETab;
import com.re.components.realestate.ToolBar;
import com.re.ui.RootUI;
import com.re.views.RealEstateView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.SpringViewProvider;

public class WebConfig extends ContextConfiguration {

    @Autowired
    public ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {

        return new RequestContextListener();
    }

    @Bean
    public SpringViewProvider springViewProvider(){
        return new SpringViewProvider(applicationContext);
    }

    @UIScope
    public RETab reTab(){
        return new RETab(toolBar(), realEstateService(), reHistoryService(), reDocumentService());
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
    public ToolBar toolBar(){
        return new ToolBar();
    }

    @Bean
    @UIScope
    public RealEstateView realEstateView(){
        return new RealEstateView(historyTab(), reTab());
    }

    @Bean
    @UIScope
    public RootUI rootUI() {
        return new RootUI();
    }
}
