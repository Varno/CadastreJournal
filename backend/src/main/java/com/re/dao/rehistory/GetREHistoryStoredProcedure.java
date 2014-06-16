package com.re.dao.rehistory;

import com.re.dao.realestate.REDaoConstants;
import com.re.dao.realestate.RERowMapper;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import java.util.HashMap;
import java.util.Map;

public class GetREHistoryStoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "RRTEST.get_facility_history";

    public GetREHistoryStoredProcedure(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SPROC_NAME);

        RowMapper rowMapper = new REHistoryRowMapper();
        declareParameter(new SqlParameter(REHistoryDaoConstants.P_FACILITY_ID,
                OracleTypes.NUMBER));
        declareParameter(new SqlParameter(REHistoryDaoConstants.P_SKIP,
                OracleTypes.NUMBER));
        declareParameter(new SqlParameter(REHistoryDaoConstants.P_TAKE,
                OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(REHistoryDaoConstants.P_CURSOR,
                OracleTypes.CURSOR, rowMapper));
        compile();
    }

    public Map getRealEstateHisoty(Long facilityId, int skipFirst, int numberOfItems) {
        Map<String, Object> inParameters = new HashMap<String, Object>();
        inParameters.put(REDaoConstants.P_FACILITY_ID, facilityId);
        inParameters.put(REDaoConstants.P_SKIP, skipFirst);
        inParameters.put(REDaoConstants.P_TAKE, numberOfItems);
        inParameters.put(REDaoConstants.P_CURSOR, new HashMap());

        return execute(inParameters);
    }
}
