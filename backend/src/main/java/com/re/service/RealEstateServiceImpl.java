package com.re.service;

import com.re.dao.CommonHelper;
import com.re.dao.destination.DestinationDao;
import com.re.dao.document.REDocumentDao;
import com.re.dao.realestate.RealEstateDao;
import com.re.dao.usage.UsageDao;
import com.re.entity.REDestination;
import com.re.entity.REDocument;
import com.re.entity.REUsage;
import com.re.entity.RealEstate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealEstateServiceImpl implements RealEstateService {
    // Кэширование справочников
    private static List<REUsage> cachedUsages;
    private static List<REDestination> cachedDestinations;

    private RealEstateDao realEstateDao;
    private DestinationDao destinationDao;
    private UsageDao usageDao;
    private REDocumentDao reDocumentDao;
    private final String EMPTY_SEARCH = "";

    public RealEstateServiceImpl(RealEstateDao realEstateDao, DestinationDao destinationDao, UsageDao usageDao,
                                 REDocumentDao reDocumentDao) {
        this.realEstateDao = realEstateDao;
        this.destinationDao = destinationDao;
        this.usageDao = usageDao;
        this.reDocumentDao = reDocumentDao;
    }

    @Override
    public List<RealEstate> getItemsFromRange(Long facilityId, String searchQuery, int skipFirst, int numberOfItems) {
        if (searchQuery == null) {
            searchQuery = EMPTY_SEARCH;
        }
        List<RealEstate> realEstatesList = realEstateDao.getItemsFromRange(facilityId, searchQuery, skipFirst, numberOfItems);
        setDestination(realEstatesList);
        setUsage(realEstatesList);
        return realEstatesList;
    }

    @Override
    public List<RealEstate> getItemsFromRange(int skipFirst, int numberOfItems) {

        List<RealEstate> realEstatesList = realEstateDao.getItemsFromRange(null, EMPTY_SEARCH, skipFirst, numberOfItems);
        if (realEstatesList == null) {
            realEstatesList = new ArrayList<RealEstate>();
        }
        setDestination(realEstatesList);
        setUsage(realEstatesList);
        return realEstatesList;
    }

    @Override
    public int getNumberOfItems(String searchQuery) {
        if (searchQuery == null) {
            searchQuery = EMPTY_SEARCH;
        }
        return realEstateDao.getNumberOfItems(searchQuery);
    }

    @Override
    public RealEstate getItem(Long facilityId) {
        RealEstate result = realEstateDao.getItem(facilityId);
        result.setReUsage(getUsageById(result.getUsageId()));
        result.setReDestination(getDestinationById(result.getDestinationId()));
        List<REDocument> docs = reDocumentDao.getDocuments(facilityId);
        for (REDocument doc : docs){
            doc.setRealEstate(result);
            File fileDocument = new File(doc.getStoredPath() + "/" + doc.getFileName());
            if(fileDocument.exists() && !fileDocument.isDirectory()){
                doc.setDocument(fileDocument);
            }
        }
        result.setReDocumentList(docs);

        return result;
    }

    @Override
    public List<REDestination> findAllREDestinations() {
        if (cachedDestinations == null)
            cachedDestinations = destinationDao.getAll();
        return cachedDestinations;
    }

    @Override
    public List<REUsage> findAllREUsages() {
        if (cachedUsages == null)
            cachedUsages = usageDao.findAll();
        return cachedUsages;
    }

    private REUsage getUsageById(Long id) {
        REUsage result = null;
        for (REUsage usage : findAllREUsages())
            if (usage.getId() == id) {
                result = usage;
                break;
            }
        return result;
    }

    private REDestination getDestinationById(Long id) {
        REDestination result = null;
        for (REDestination destination : findAllREDestinations())
            if (destination.getId() == id) {
                result = destination;
                break;
            }
        return result;
    }

    @Override
    public Long saveOrUpdate(RealEstate entity) throws IOException {
        Long id = null;

        if (entity.getId() == null) {
            id = realEstateDao.saveOrUpdate(entity);
            entity.setId(id);
        }
        else
            realEstateDao.saveOrUpdate(entity);

        Path target = Paths.get(CommonHelper.BASE_PATH +"/VAADIN/documents/" + entity.getId() + "/");
        if (!Files.exists(target)) {
            Files.createDirectory(target);
        }
        for (REDocument reDocument : entity.getReDocumentList()) {
            if(reDocument.getId() == null){
                reDocument.setRealEstate(entity);
                reDocument.setStoredPath(target.toString());
                Files.move(reDocument.getTempDocumentFile().toPath(), target.resolve(reDocument.getTempDocumentFile().getName()));
            }
            reDocumentDao.saveOrUpdate(reDocument);
        }
        return id;
    }


    private List<RealEstate> setDestination(List<RealEstate> realEstateList) {
        List<REDestination> reDestinationList = destinationDao.getAll();
        Map<Long, REDestination> destMap = new HashMap<Long, REDestination>();
        for (REDestination reDestination : reDestinationList) {
            destMap.put(reDestination.getId(), reDestination);
        }

        for (RealEstate realEstate : realEstateList) {
            if (destMap.containsKey(realEstate.getDestinationId()))
                realEstate.setReDestination(destMap.get(realEstate.getDestinationId()));
        }
        return realEstateList;
    }

    private List<RealEstate> setUsage(List<RealEstate> realEstatesList) {
        List<REUsage> reUsagesList = usageDao.findAll();
        Map<Long, REUsage> destMap = new HashMap<Long, REUsage>();
        for (REUsage reUsage : reUsagesList) {
            destMap.put(reUsage.getId(), reUsage);
        }

        for (RealEstate realEstate : realEstatesList) {
            if (destMap.containsKey(realEstate.getUsageId()))
                realEstate.setReUsage(destMap.get(realEstate.getUsageId()));
        }
        return realEstatesList;
    }
}

