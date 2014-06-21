package com.re.dao.document;


import com.re.dao.realestate.GetREStoredProcedure;
import com.re.dao.realestate.REDaoConstants;
import com.re.entity.REDocument;
import com.re.entity.RealEstate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class REDocumentDaoImpl implements REDocumentDao{
    protected JdbcTemplate jdbcTemplate;

    public REDocumentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long saveOrUpdate(REDocument reDocument) {
        UpdateREDocumentsStoredProcedure updateREDocumentsStoredProcedure = new UpdateREDocumentsStoredProcedure(jdbcTemplate);
        Map inputs = new HashMap();
        inputs.put(REDocumentDaoConstants.P_DOCUMENT_ID, reDocument.getId());
        inputs.put(REDocumentDaoConstants.P_FACILITY_ID, reDocument.getRealEstate().getId());
        inputs.put(REDocumentDaoConstants.P_FILE_NAME, reDocument.getFileName());
        inputs.put(REDocumentDaoConstants.P_STORED_PATH, reDocument.getStoredPath());
        inputs.put(REDocumentDaoConstants.P_USER_NAME, "TestUser");
        inputs.put(REDocumentDaoConstants.P_USER_IP, "127.0.0.1");
        Map result = updateREDocumentsStoredProcedure.execute(inputs);
        BigDecimal count = (BigDecimal)result.get(REDocumentDaoConstants.P_INSERTED_ID);
        return count.longValue();
    }

    @Override
    public List<REDocument> getDocuments(long reId)
    {
        GetREDocumentsStoredProcedure proc = new GetREDocumentsStoredProcedure(jdbcTemplate);
        List result;
        Map data = proc.getDocuments(reId, 0, 10);
        result = (List) data.get(REDaoConstants.P_CURSOR);
        return result;
    }

    @Override
    public void deleteDocument(REDocument reDocument) {
        DeleteREDocumentStoredProcedure  proc = new DeleteREDocumentStoredProcedure(jdbcTemplate);
        Map inputs = new HashMap();
        inputs.put(REDocumentDaoConstants.P_DOCUMENT_ID, reDocument.getId());
        inputs.put(REDocumentDaoConstants.P_USER_NAME, "TestUser");
        inputs.put(REDocumentDaoConstants.P_USER_IP, "127.0.0.1");
        proc.execute(inputs);
    }
}
