package com.re.dao.realestate;

import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class UpdateREStoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "RRTEST.update_facility";

    public UpdateREStoredProcedure(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SPROC_NAME);

        declareParameter(new SqlParameter(REDaoConstants.P_FACILITY_ID,
                OracleTypes.NUMBER));
        declareParameter(new SqlParameter(REDaoConstants.P_CADASTRAL_NUMBER,
                OracleTypes.VARCHAR));
        declareParameter(new SqlParameter(REDaoConstants.P_SQUARE,
                OracleTypes.NUMBER, 2));
        declareParameter(new SqlParameter(REDaoConstants.P_DESTINATION_ID,
                OracleTypes.NUMBER));
        declareParameter(new SqlParameter(REDaoConstants.P_AREA_DESCRIPTION,
                OracleTypes.VARCHAR));
        declareParameter(new SqlParameter(REDaoConstants.P_USAGE_ID,
                OracleTypes.NUMBER));
        declareParameter(new SqlParameter(REDaoConstants.P_ADDRESS,
                OracleTypes.NVARCHAR));
        declareParameter(new SqlParameter(REDaoConstants.P_USER_NAME,
                OracleTypes.NVARCHAR));
        declareParameter(new SqlParameter(REDaoConstants.P_USER_IP,
                OracleTypes.NVARCHAR));
        declareParameter(new SqlOutParameter(REDaoConstants.P_INSERTED_ID,
                OracleTypes.NUMBER));
        compile();
    }
}
