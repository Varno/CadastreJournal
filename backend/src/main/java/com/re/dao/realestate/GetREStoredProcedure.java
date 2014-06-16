package com.re.dao.realestate;

import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.object.StoredProcedure;
import java.util.HashMap;
import java.util.Map;

public class GetREStoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "RRTEST.GET_FACILITIES";

    public GetREStoredProcedure(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SPROC_NAME);

        RowMapper rowMapper = new RERowMapper();
        declareParameter(new SqlParameter(REDaoConstants.P_FACILITY_ID,
                OracleTypes.NUMBER));
        declareParameter(new SqlParameter(REDaoConstants.P_CADASTRAL_NUMBER,
                OracleTypes.VARCHAR));
        declareParameter(new SqlParameter(REDaoConstants.P_SKIP,
                OracleTypes.NUMBER));
        declareParameter(new SqlParameter(REDaoConstants.P_TAKE,
                OracleTypes.NUMBER));
        declareParameter(new SqlOutParameter(REDaoConstants.P_CURSOR,
                OracleTypes.CURSOR, rowMapper));
        declareParameter(new SqlOutParameter(REDaoConstants.P_COUNT,
                OracleTypes.NUMBER, rowMapper));
        compile();
    }

    public Map getRealEstates(Long facilityId, String cadastralNumber, int skipFirst, int numberOfItems) {
        Map<String, Object> inParameters = new HashMap<String, Object>();
        Integer count = 0;
        inParameters.put(REDaoConstants.P_FACILITY_ID, facilityId);
        //fixme: stored procedure doesn't work with null cadastralNumber
        inParameters.put(REDaoConstants.P_CADASTRAL_NUMBER, cadastralNumber);
        inParameters.put(REDaoConstants.P_SKIP, skipFirst);
        inParameters.put(REDaoConstants.P_TAKE, numberOfItems);
        inParameters.put(REDaoConstants.P_CURSOR, new HashMap());
        inParameters.put(REDaoConstants.P_COUNT, count);

        return execute(inParameters);
    }
}
