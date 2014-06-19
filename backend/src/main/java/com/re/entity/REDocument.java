package com.re.entity;

import java.io.Serializable;
import java.util.Calendar;

public class REDocument implements Serializable {
    private Long id;
    private RealEstate realEstate;
    private String fileName;
    private String storedPath;
    private Calendar createdDate;
    private String createdBy;
    private Calendar modifiedDate;
    private String modifiedBy;
    private String modifiedByIp;

    public REDocument() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedByIp() {
        return modifiedByIp;
    }

    public void setModifiedByIp(String modifiedByIp) {
        this.modifiedByIp = modifiedByIp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        REDocument that = (REDocument) o;

        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (modifiedBy != null ? !modifiedBy.equals(that.modifiedBy) : that.modifiedBy != null) return false;
        if (modifiedByIp != null ? !modifiedByIp.equals(that.modifiedByIp) : that.modifiedByIp != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) return false;
        if (realEstate != null ? !realEstate.equals(that.realEstate) : that.realEstate != null) return false;
        if (storedPath != null ? !storedPath.equals(that.storedPath) : that.storedPath != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (realEstate != null ? realEstate.hashCode() : 0);
        result = 31 * result + (storedPath != null ? storedPath.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        result = 31 * result + (modifiedByIp != null ? modifiedByIp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "REDocument{" +
                "id=" + id +
                ", realestate=" + realEstate +
                ", storedPath='" + storedPath + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedByIp='" + modifiedByIp + '\'' +
                '}';
    }
}
