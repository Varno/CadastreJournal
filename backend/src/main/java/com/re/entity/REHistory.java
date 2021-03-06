package com.re.entity;

import java.io.Serializable;
import java.util.Calendar;

// Тип сущности - история изменения объекта недвижимости
public class REHistory implements Serializable {
    public static final String FIELD_ID = "id";
    public static final String FIELD_MODIFIED_DATE = "modifiedDate";
    public static final String FIELD_MODIFIED_BY = "modifiedBy";
    public static final String FIELD_MODIFIED_BY_IP = "modifiedByIp";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_ACTION = "action";

    private Long id;
    private Calendar modifiedDate;
    private String modifiedBy;
    private String modifiedByIp;
    private String description;
    private String action;

    public REHistory() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        REHistory reHistory = (REHistory) o;

        if (description != null ? !description.equals(reHistory.description) : reHistory.description != null)
            return false;
        if (id != null ? !id.equals(reHistory.id) : reHistory.id != null) return false;
        if (modifiedBy != null ? !modifiedBy.equals(reHistory.modifiedBy) : reHistory.modifiedBy != null) return false;
        if (modifiedByIp != null ? !modifiedByIp.equals(reHistory.modifiedByIp) : reHistory.modifiedByIp != null)
            return false;
        if (modifiedDate != null ? !modifiedDate.equals(reHistory.modifiedDate) : reHistory.modifiedDate != null)
            return false;
/*
        if (realEstateId != null ? !realEstateId.equals(reHistory.realEstateId) : reHistory.realEstateId != null)
            return false;
*/

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        result = 31 * result + (modifiedBy != null ? modifiedBy.hashCode() : 0);
        result = 31 * result + (modifiedByIp != null ? modifiedByIp.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "REHistory{" +
                "id=" + id +
                ", action=" + action +
                ", modifiedDate=" + modifiedDate +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedByIp='" + modifiedByIp + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

