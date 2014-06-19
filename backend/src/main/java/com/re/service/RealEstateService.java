package com.re.service;

import com.re.entity.REDestination;
import com.re.entity.REUsage;
import com.re.entity.RealEstate;

import java.util.List;

public interface RealEstateService {
    List<RealEstate> getItemsFromRange(Long facilityId, String cadastralNumber, int startIndex, int endIndex);
    List<RealEstate> getItemsFromRange(int skipFirst, int numberOfItems);

    void saveOrUpdate(RealEstate realEstate);

    int getNumberOfItems(String searchQuery);

    List<REDestination> findAllREDestinations();
    List<REUsage> findAllREUsages();
}
