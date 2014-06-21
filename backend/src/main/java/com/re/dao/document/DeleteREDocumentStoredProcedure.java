package com.re.dao.document;

import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class DeleteREDocumentStoredProcedure extends StoredProcedure {
    private static final String SPROC_NAME = "RRTEST.delete_facility_document";

    public DeleteREDocumentStoredProcedure(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, SPROC_NAME);

        declareParameter(new SqlParameter(REDocumentDaoConstants.P_DOCUMENT_ID,
                OracleTypes.NUMBER));
        declareParameter(new SqlParameter(REDocumentDaoConstants.P_USER_NAME,
                OracleTypes.VARCHAR));
        declareParameter(new SqlParameter(REDocumentDaoConstants.P_USER_IP,
                OracleTypes.VARCHAR));
        compile();
    }
}
