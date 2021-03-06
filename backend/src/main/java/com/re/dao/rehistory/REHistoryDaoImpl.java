package com.re.dao.rehistory;

import com.re.dao.realestate.REDaoConstants;
import com.re.entity.REHistory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class REHistoryDaoImpl implements REHistoryDao {
    protected JdbcTemplate jdbcTemplate;

    public REHistoryDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<REHistory> getHistoryItems(Long facilityId, int skipFirst, int numberOfItems) {
        GetREHistoryStoredProcedure getREHistoryStoredProcedure = new GetREHistoryStoredProcedure(jdbcTemplate);
        List result;
        Map data = getREHistoryStoredProcedure.getRealEstateHisoty(facilityId, skipFirst, numberOfItems);
        result = (List) data.get(REDaoConstants.P_CURSOR);
        return result;
    }

    @Override
    public int getNumberOfItems(Long facilityId) {
        GetREHistoryStoredProcedure getREHistoryStoredProcedure = new GetREHistoryStoredProcedure(jdbcTemplate);
        Map data = getREHistoryStoredProcedure.getRealEstateHisoty(facilityId, 0, 0);
        BigDecimal count = (BigDecimal)data.get(REHistoryDaoConstants.P_COUNT);
        return count.intValue();
    }
}
