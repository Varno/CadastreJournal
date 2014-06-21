package com.re.service;

import com.re.entity.REDocument;

public interface REDocumentService {
    Long saveOrUpdate(REDocument reDocument);

    void delete(REDocument reDocument);
}
