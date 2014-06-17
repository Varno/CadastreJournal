package com.re.dao.realestate;

import com.re.entity.RealEstate;

import java.util.List;

public interface RealEstateDao {
    List<RealEstate> getItemsFromRange(Long facilityId, String cadastralNumber, int startIndex, int endIndex);
    void saveOrUdate(RealEstate realEstate);

    int getNumberOfItems(String cadastralNumber);
}
