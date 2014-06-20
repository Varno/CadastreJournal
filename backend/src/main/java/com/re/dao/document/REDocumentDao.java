package com.re.dao.document;

import com.re.entity.REDocument;

import java.util.List;

public interface REDocumentDao {
    Long saveOrUpdate(REDocument reDocument);

    List<REDocument> getDocuments(long reId);
}
