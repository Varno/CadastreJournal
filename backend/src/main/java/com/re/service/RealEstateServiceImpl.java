package com.re.service;

import com.re.dao.realestate.RealEstateDao;
import com.re.entity.RealEstate;

import java.util.ArrayList;
import java.util.List;

public class RealEstateServiceImpl implements RealEstateService {
    private RealEstateDao realEstateDao;
    private final String EMPTY_CADASTRAL = "%";
    public RealEstateServiceImpl(RealEstateDao realEstateDao) {
        this.realEstateDao = realEstateDao;
    }

    @Override
    public List<RealEstate> getItemsFromRange(Long facilityId, String cadastralNumber, int skipFirst, int numberOfItems) {
        //stored procedure doesn't work with null cadastralNumber value
        if(cadastralNumber == null){
            cadastralNumber = EMPTY_CADASTRAL;
        }
        return realEstateDao.getItemsFromRange(facilityId,cadastralNumber, skipFirst, numberOfItems);
    }

    @Override
    public List<RealEstate> getItemsFromRange(int skipFirst, int numberOfItems) {

        List<RealEstate> realEstatesList = realEstateDao.getItemsFromRange(null, EMPTY_CADASTRAL, skipFirst, numberOfItems);
        if(realEstatesList == null){
            realEstatesList = new ArrayList<RealEstate>();
        }
        return realEstatesList;
    }

    @Override
    public int getNumberOfItems(String cadastralNumber) {
        if(cadastralNumber == null){
            cadastralNumber = EMPTY_CADASTRAL;
        }
        return realEstateDao.getNumberOfItems(cadastralNumber);
    }

    @Override
    public void saveOrUdate(RealEstate realEstate) {
        realEstateDao.saveOrUdate(realEstate);
    }
}

