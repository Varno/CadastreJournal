package com.re.dao.realestate;

import com.re.dao.CommonHelper;
import com.re.dao.realestate.REDaoConstants;
import com.re.dao.rehistory.REHistoryDaoConstants;
import com.re.entity.RealEstate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RERowMapper implements RowMapper<RealEstate> {
    @Override
    public RealEstate mapRow(ResultSet rs, int i) throws SQLException {
        RealEstate realEstate = new RealEstate();
        realEstate.setId(rs.getLong(REDaoConstants.FACILITY_ID));
        realEstate.setCadastralNumber(rs.getString(REDaoConstants.CADASTRAL_NUMBER));
        realEstate.setSquare(rs.getDouble(REDaoConstants.SQUARE));
        realEstate.setDestinationId(rs.getLong(REDaoConstants.DESTINATION_ID));
        realEstate.setAreaDescription(rs.getString(REDaoConstants.AREA_DESCRIPTION));
        realEstate.setUsageId(rs.getLong(REDaoConstants.USAGE_ID));
        realEstate.setAddress(rs.getString(REDaoConstants.ADDRESS));
        realEstate.setModifiedDate(CommonHelper.dateToCalendar(rs.getTimestamp(REDaoConstants.MODIFIED_DATE)));

        return realEstate;
    }


}
