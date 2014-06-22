package com.re.entity;

import java.io.Serializable;

// Тип сущности - справочник разрешенного использования
public class REUsage implements Serializable {
    private Long id;
    private String description;

    public REUsage() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        REUsage reUsage = (REUsage) o;

        if (description != null ? !description.equals(reUsage.description) : reUsage.description != null) return false;
        if (id != null ? !id.equals(reUsage.id) : reUsage.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "REUsage{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}

