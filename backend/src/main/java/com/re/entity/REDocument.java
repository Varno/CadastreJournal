package com.re.entity;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

// Тип дочерней (связанной) сущности - документ объекта недвижисти
public class REDocument implements Serializable {
    private Long id;
    private Long realEstateId;
    private RealEstate realEstate;
    private String fileName;
    private String storedPath;
    private File document;
    /**
     * Temporary document file that stored in "java.io.tmpdir" before the new REDocument form committed
     */
    private File tempDocumentFile;

    public REDocument() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public File getTempDocumentFile() {
        return tempDocumentFile;
    }

    public void setTempDocumentFile(File tempDocumentFile) {
        this.tempDocumentFile = tempDocumentFile;
    }

    public RealEstate getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(RealEstate realEstate) {
        this.realEstate = realEstate;
    }

    public String getStoredPath() {
        return storedPath;
    }

    public void setStoredPath(String storedPath) {
        this.storedPath = storedPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getRealEstateId() {
        return realEstateId;
    }

    public void setRealEstateId(Long realEstateId) {
        this.realEstateId = realEstateId;
    }

    public File getDocument() {
        return document;
    }

    public void setDocument(File document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        REDocument that = (REDocument) o;

        if (fileName != null ? !fileName.equals(that.fileName) : that.fileName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (realEstate != null ? !realEstate.equals(that.realEstate) : that.realEstate != null) return false;
        if (realEstateId != null ? !realEstateId.equals(that.realEstateId) : that.realEstateId != null) return false;
        if (storedPath != null ? !storedPath.equals(that.storedPath) : that.storedPath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (realEstateId != null ? realEstateId.hashCode() : 0);
        result = 31 * result + (realEstate != null ? realEstate.hashCode() : 0);
        result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
        result = 31 * result + (storedPath != null ? storedPath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "REDocument{" +
                "id=" + id +
                ", realEstateId=" + realEstateId +
                ", realEstate=" + realEstate +
                ", fileName='" + fileName + '\'' +
                ", storedPath='" + storedPath + '\'' +
                '}';
    }
}
