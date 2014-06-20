package com.re.dao.document;

import com.re.dao.CommonHelper;
import com.re.dao.realestate.REDaoConstants;
import com.re.entity.REDocument;
import com.re.entity.RealEstate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class REDocumentRowMapper implements RowMapper<REDocument> {
    @Override
    public REDocument mapRow(ResultSet rs, int i) throws SQLException {
        REDocument document = new REDocument();
        document.setId(rs.getLong(REDocumentDaoConstants.DOCUMENT_ID));
        document.setFileName(rs.getString(REDocumentDaoConstants.FILE_NAME));
        document.setStoredPath(rs.getString(REDocumentDaoConstants.STORED_PATH));
        return document;
    }
}
