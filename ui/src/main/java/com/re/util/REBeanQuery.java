package com.re.util;


import com.re.components.realestate.RETable;
import com.re.entity.RealEstate;
import com.re.service.RealEstateService;
import org.vaadin.addons.lazyquerycontainer.AbstractBeanQuery;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;

import java.util.List;
import java.util.Map;

public class REBeanQuery extends AbstractBeanQuery<RealEstate> {

    private Map<String, Object> queryConfiguration;
    public REBeanQuery(QueryDefinition definition, Map<String, Object> queryConfiguration,
                       Object[] sortPropertyIds, boolean[] sortStates) {
        super(definition, queryConfiguration, sortPropertyIds, sortStates);
        this.queryConfiguration = queryConfiguration;
    }

    @Override
    protected RealEstate constructBean() {
        return new RealEstate();
    }

    @Override
    public int size() {
        RealEstateService realEstateService = (RealEstateService) queryConfiguration.get("realEstateService");
        return realEstateService.getNumberOfItems(RETable.getSearchQuery());
    }

    @Override
    protected List<RealEstate> loadBeans(int startIndex, int count) {
        RealEstateService realEstateService =
                (RealEstateService)queryConfiguration.get("realEstateService");
        return realEstateService.getItemsFromRange(null, RETable.getSearchQuery(), startIndex, count);
    }

    @Override
    protected void saveBeans(List<RealEstate> realEstateList, List<RealEstate> realEstateList2, List<RealEstate> realEstateList3) {
        throw new UnsupportedOperationException("Данный функционал не поддерживается");
    }
}
