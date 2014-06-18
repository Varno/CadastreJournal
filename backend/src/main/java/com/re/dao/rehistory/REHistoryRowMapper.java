package com.re.dao.rehistory;


import com.re.entity.REHistory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class REHistoryRowMapper implements RowMapper<REHistory> {
    @Override
    public REHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
        REHistory reHistory = new REHistory();
        reHistory.setId(rs.getLong(REHistoryDaoConstants.FACILITY_HISTORY_ID));
        reHistory.setModifiedDate(dateToCalendar(rs.getDate(REHistoryDaoConstants.MODIFIED_DATE)));
        reHistory.setModifiedBy(rs.getString(REHistoryDaoConstants.MODIFIED_BY));
        reHistory.setModifiedByIp(rs.getString(REHistoryDaoConstants.MODIFIED_BY_IP));
        reHistory.setAction(rs.getString(REHistoryDaoConstants.ACTION));
        reHistory.setDescription(rs.getString(REHistoryDaoConstants.DESCRIPTION));
        return reHistory;
    }

    private static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
