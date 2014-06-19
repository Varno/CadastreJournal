package com.re.service;


import com.re.dao.document.REDocumentDao;
import com.re.entity.REDocument;

public class REDocumentServiceImpl implements REDocumentService {
    private REDocumentDao reDocumentDao;

    public REDocumentServiceImpl(REDocumentDao reDocumentDao) {
        this.reDocumentDao = reDocumentDao;
    }

    @Override
    public Long saveOrUpdate(REDocument reDocument) {
        return reDocumentDao.saveOrUpdate(reDocument);
    }
}
