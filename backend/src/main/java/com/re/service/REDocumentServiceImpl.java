package com.re.service;


import com.re.dao.document.REDocumentDao;
import com.re.entity.REDocument;

import java.io.IOException;
import java.nio.file.Files;

public class REDocumentServiceImpl implements REDocumentService {
    private REDocumentDao reDocumentDao;

    public REDocumentServiceImpl(REDocumentDao reDocumentDao) {
        this.reDocumentDao = reDocumentDao;
    }

    @Override
    public Long saveOrUpdate(REDocument reDocument) {
        return reDocumentDao.saveOrUpdate(reDocument);
    }

    @Override
    public void delete(REDocument reDocument) throws IOException {
        Files.delete(reDocument.getDocument().toPath());
        reDocumentDao.deleteDocument(reDocument);
    }
}
