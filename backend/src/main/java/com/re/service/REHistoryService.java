package com.re.service;

import com.re.entity.REHistory;
import java.util.List;

public interface REHistoryService {
    List<REHistory> getHistoryItems(Long realEstateId, int skipFirst, int numberOfItems);

    int getNumberOfItems(Long facilityId);

    List<REHistory> getHistoryItems(int skipFirst, int numberOfItems);

    int getNumberOfItems();
}
