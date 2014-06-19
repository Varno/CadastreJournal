package com.re.service;

import com.re.dao.destination.DestinationDao;
import com.re.dao.document.REDocumentDao;
import com.re.dao.realestate.RealEstateDao;
import com.re.dao.usage.UsageDao;
import com.re.entity.REDestination;
import com.re.entity.REDocument;
import com.re.entity.REUsage;
import com.re.entity.RealEstate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealEstateServiceImpl implements RealEstateService {
    private RealEstateDao realEstateDao;
    private DestinationDao destinationDao;
    private UsageDao usageDao;
    private REDocumentDao reDocumentDao;
    private final String EMPTY_SEARCH = "";

    public RealEstateServiceImpl(RealEstateDao realEstateDao, DestinationDao destinationDao, UsageDao usageDao, REDocumentDao reDocumentDao) {
        this.realEstateDao = realEstateDao;
        this.destinationDao = destinationDao;
        this.usageDao = usageDao;
        this.reDocumentDao=reDocumentDao;
    }

    @Override
    public List<RealEstate> getItemsFromRange(Long facilityId, String searchQuery, int skipFirst, int numberOfItems) {
        //stored procedure doesn't work with null cadastralNumber value
        if(searchQuery == null){
            searchQuery = EMPTY_SEARCH;
        }
        List<RealEstate> realEstatesList = realEstateDao.getItemsFromRange(facilityId,searchQuery, skipFirst, numberOfItems);
        setDestination(realEstatesList);
        setUsage(realEstatesList);
        return realEstatesList;
    }

    @Override
    public List<RealEstate> getItemsFromRange(int skipFirst, int numberOfItems) {

        List<RealEstate> realEstatesList = realEstateDao.getItemsFromRange(null, EMPTY_SEARCH, skipFirst, numberOfItems);
        if(realEstatesList == null){
            realEstatesList = new ArrayList<RealEstate>();
        }
        setDestination(realEstatesList);
        setUsage(realEstatesList);
        return realEstatesList;
    }

    @Override
    public int getNumberOfItems(String searchQuery) {
        if(searchQuery == null){
            searchQuery = EMPTY_SEARCH;
        }
        return realEstateDao.getNumberOfItems(searchQuery);
    }

    @Override
    public List<REDestination> findAllREDestinations() {
        return destinationDao.getAll();
    }

    @Override
    public List<REUsage> findAllREUsages() {
        return usageDao.findAll();
    }

    @Override
    public void saveOrUpdate(RealEstate realEstate) {
        Long id = realEstateDao.saveOrUpdate(realEstate);
        for(REDocument reDocument: realEstate.getReDocumentList()){
            reDocument.setRealEstate(realEstate);
            reDocumentDao.saveOrUpdate(reDocument);
        }
    }


    private List<RealEstate> setDestination(List<RealEstate> realEstateList){
        List<REDestination> reDestinationList = destinationDao.getAll();
        Map<Long, REDestination> destMap = new HashMap<Long, REDestination>();
        for(REDestination reDestination: reDestinationList){
            destMap.put(reDestination.getId(), reDestination);
        }

        for(RealEstate realEstate: realEstateList){
            if(destMap.containsKey(realEstate.getDestinationId()))
            realEstate.setReDestination(destMap.get(realEstate.getDestinationId()));
        }
        return realEstateList;
    }

    private List<RealEstate> setUsage(List<RealEstate> realEstatesList) {
        List<REUsage> reUsagesList = usageDao.findAll();
        Map<Long, REUsage> destMap = new HashMap<Long, REUsage>();
        for(REUsage reUsage: reUsagesList){
            destMap.put(reUsage.getId(), reUsage);
        }

        for(RealEstate realEstate: realEstatesList){
            if(destMap.containsKey(realEstate.getUsageId()))
                realEstate.setReUsage(destMap.get(realEstate.getUsageId()));
        }
        return realEstatesList;
    }
}

