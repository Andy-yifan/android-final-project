package com.example.portfolio;

public class Holder_bulider {

    private String name;
    private String address;
    private String latitude;
    private String longitude;
    private String maxDistanceLimit;
    private String businessTypeId;
    private String schemeTypeKey;
    private String ratingKey;
    private String ratingOperatorKey;
    private String localAuthorityId;
    private String countryId;
    private String sortOptionKey;
    private String pageNumber;
    private String pageSize;
    public Holder_bulider() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMaxDistanceLimit() {
        return maxDistanceLimit;
    }

    public void setMaxDistanceLimit(String maxDistanceLimit) {
        this.maxDistanceLimit = maxDistanceLimit;
    }

    public String getBusinessTypeId() {
        return businessTypeId;
    }

    public void setBusinessTypeId(String businessTypeId) {
        this.businessTypeId = businessTypeId;
    }

    public String getSchemeTypeKey() {
        return schemeTypeKey;
    }

    public void setSchemeTypeKey(String schemeTypeKey) {
        this.schemeTypeKey = schemeTypeKey;
    }

    public String getRatingKey() {
        return ratingKey;
    }

    public void setRatingKey(String ratingKey) {
        this.ratingKey = ratingKey;
    }

    public String getRatingOperatorKey() {
        return ratingOperatorKey;
    }

    public void setRatingOperatorKey(String ratingOperatorKey) {
        this.ratingOperatorKey = ratingOperatorKey;
    }

    public String getLocalAuthorityId() {
        return localAuthorityId;
    }

    public void setLocalAuthorityId(String localAuthorityId) {
        this.localAuthorityId = localAuthorityId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getSortOptionKey() {
        return sortOptionKey;
    }

    public void setSortOptionKey(String sortOptionKey) {
        this.sortOptionKey = sortOptionKey;

    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public static Holder_bulider inits() {
        return new Holder_bulider();
    }

    public Input_holder build() {
        return new Input_holder(name, address, latitude, longitude, maxDistanceLimit, businessTypeId, schemeTypeKey, ratingKey, ratingOperatorKey, localAuthorityId, countryId, sortOptionKey, pageNumber, pageSize);
    }

}
