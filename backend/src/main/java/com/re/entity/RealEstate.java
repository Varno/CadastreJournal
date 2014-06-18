package com.re.entity;



public class RealEstate {
    public static final String FIELD_CADASTRAL_NUMBER = "cadastralNumber";
    public static final String FIELD_SQUARE = "square";

    private Long id;
    private String cadastralNumber;
    private Double square;
    private Long destinationId;
    private String areaDescription;
    private Long usageId;
    private String address;
    private REDestination reDestination;
    private REUsage reUsage;

    public RealEstate() {
        this.cadastralNumber = "";
        this.square = 0D;
        this.areaDescription = "";
        this.address = "";
    }

    public REDestination getReDestination() {
        return reDestination;
    }

    public void setReDestination(REDestination reDestination) {
        this.reDestination = reDestination;
    }

    public REUsage getReUsage() {
        return reUsage;
    }

    public void setReUsage(REUsage reUsage) {
        this.reUsage = reUsage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCadastralNumber() {
        return cadastralNumber;
    }

    public void setCadastralNumber(String cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public Double getSquare() {
        return square;
    }

    public void setSquare(Double square) {
        this.square = square;
    }

    public Long getDestinationId() {
        return 1L; //destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public String getAreaDescription() {
        return areaDescription;
    }

    public void setAreaDescription(String areaDescription) {
        this.areaDescription = areaDescription;
    }

    public Long getUsageId() {
        return 1L; //usageId;
    }

    public void setUsageId(Long usageId) {
        this.usageId = usageId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RealEstate that = (RealEstate) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (areaDescription != null ? !areaDescription.equals(that.areaDescription) : that.areaDescription != null)
            return false;
        if (cadastralNumber != null ? !cadastralNumber.equals(that.cadastralNumber) : that.cadastralNumber != null)
            return false;
        if (destinationId != null ? !destinationId.equals(that.destinationId) : that.destinationId != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reDestination != null ? !reDestination.equals(that.reDestination) : that.reDestination != null)
            return false;
        if (reUsage != null ? !reUsage.equals(that.reUsage) : that.reUsage != null) return false;
        if (square != null ? !square.equals(that.square) : that.square != null) return false;
        if (usageId != null ? !usageId.equals(that.usageId) : that.usageId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cadastralNumber != null ? cadastralNumber.hashCode() : 0);
        result = 31 * result + (square != null ? square.hashCode() : 0);
        result = 31 * result + (destinationId != null ? destinationId.hashCode() : 0);
        result = 31 * result + (areaDescription != null ? areaDescription.hashCode() : 0);
        result = 31 * result + (usageId != null ? usageId.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (reDestination != null ? reDestination.hashCode() : 0);
        result = 31 * result + (reUsage != null ? reUsage.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", cadastralNumber='" + cadastralNumber + '\'' +
                ", square=" + square +
                ", destinationId=" + destinationId +
                ", areaDescription='" + areaDescription + '\'' +
                ", usageId=" + usageId +
                ", address='" + address + '\'' +
                ", reDestination=" + reDestination +
                ", reUsage=" + reUsage +
                '}';
    }
}