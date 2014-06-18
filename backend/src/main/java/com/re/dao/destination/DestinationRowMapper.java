package com.re.dao.destination;

import com.re.entity.REDestination;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Сергей on 17.06.2014.
 */
public class DestinationRowMapper implements RowMapper<REDestination> {
    @Override
    public REDestination mapRow(ResultSet rs, int rowNum) throws SQLException {
        REDestination reDestination = new REDestination();
        reDestination.setId(rs.getLong(REDestinationDaoConstants.DESTINATION_ID));
        reDestination.setDescription(rs.getString(REDestinationDaoConstants.DESCRIPTION));
        return reDestination;
    }
}
