package com.re.service;


import com.re.dao.rehistory.REHistoryDao;
import com.re.entity.REHistory;

import java.util.List;

public class REHistoryServiceImpl implements REHistoryService {
    private REHistoryDao reHistoryDao;

    public REHistoryServiceImpl(REHistoryDao reHistoryDao) {
        this.reHistoryDao = reHistoryDao;
    }

    @Override
    public List<REHistory> getHistoryItems(Long realEstateId, int skipFirst, int numberOfItems) {
        return reHistoryDao.getHistoryItems(realEstateId, skipFirst, numberOfItems);
    }

    @Override
    public int getNumberOfItems(Long facilityId) {
        return reHistoryDao.getNumberOfItems(facilityId);
    }
}
