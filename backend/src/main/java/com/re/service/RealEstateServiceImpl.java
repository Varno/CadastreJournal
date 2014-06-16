package com.re.service;

import com.re.dao.realestate.RealEstateDao;
import com.re.entity.RealEstate;
import java.util.List;

public class RealEstateServiceImpl implements RealEstateService {
    private RealEstateDao realEstateDao;

    public RealEstateServiceImpl(RealEstateDao realEstateDao) {
        this.realEstateDao = realEstateDao;
    }

    @Override
    public List<RealEstate> getItemsFromRange(Long facilityId, String cadastralNumber, int skipFirst, int numberOfItems) {
        return realEstateDao.getItemsFromRange(facilityId,cadastralNumber, skipFirst, numberOfItems);
    }

    @Override
    public List<RealEstate> getItemsFromRange(int skipFirst, int numberOfItems) {
        return realEstateDao.getItemsFromRange(null, null, skipFirst, numberOfItems);
    }
}

