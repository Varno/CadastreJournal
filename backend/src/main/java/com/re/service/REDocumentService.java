package com.re.service;

import com.re.entity.REDocument;

import java.io.IOException;

public interface REDocumentService {
    Long saveOrUpdate(REDocument reDocument);

    void delete(REDocument reDocument) throws IOException;
}
