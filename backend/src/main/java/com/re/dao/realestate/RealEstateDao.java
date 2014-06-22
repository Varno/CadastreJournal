package com.re.dao.realestate;

import com.re.entity.RealEstate;

import java.util.List;

// Репозитарий объектов недвижимости
public interface RealEstateDao {
    List<RealEstate> getItemsFromRange(Long facilityId, String searchQuery, int startIndex, int endIndex);

    RealEstate getItem(Long facilityId);

    Long saveOrUpdate(RealEstate realEstate);

    int getNumberOfItems(String searchQuery);
}
