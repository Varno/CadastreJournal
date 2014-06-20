package com.re.dao.document;

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

public class GetREDocumentsStoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "RRTEST.get_facility_documents";

    public GetREDocumentsStoredProcedure(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SPROC_NAME);

        RowMapper rowMapper = new REDocumentRowMapper();
        declareParameter(new SqlParameter(REDaoConstants.P_FACILITY_ID,
                OracleTypes.NUMBER));
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

    public Map getDocuments(Long facilityId, int skipFirst, int numberOfItems) {
        Map<String, Object> inParameters = new HashMap<String, Object>();
        Integer count = 0;
        inParameters.put(REDaoConstants.P_FACILITY_ID, facilityId);
        inParameters.put(REDaoConstants.P_SKIP, skipFirst);
        inParameters.put(REDaoConstants.P_TAKE, numberOfItems);
        inParameters.put(REDaoConstants.P_CURSOR, new HashMap());
        inParameters.put(REDaoConstants.P_COUNT, count);

        return execute(inParameters);
    }
}
