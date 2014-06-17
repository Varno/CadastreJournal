package com.re.dao.rehistory;

import com.re.entity.REHistory;

import java.util.List;

public interface REHistoryDao {
    List<REHistory> getHistoryItems(Long realEstateId, int skipFirst, int numberOfItems);

    int getNumberOfItems(Long facilityId);

}
