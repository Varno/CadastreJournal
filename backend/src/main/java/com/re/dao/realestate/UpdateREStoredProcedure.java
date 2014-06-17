package com.re.dao.realestate;

import com.re.dao.realestate.REDaoConstants;
import com.re.entity.RealEstate;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import java.util.HashMap;
import java.util.Map;

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
                OracleTypes.NCLOB));
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

/*    public Map saveOrUpdate(RealEstate realEstate) {
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
    }*/
}
