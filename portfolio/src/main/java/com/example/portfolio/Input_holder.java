package com.example.portfolio;

public class Input_holder {
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

    public Input_holder(String name, String address, String latitude, String longitude, String maxDistanceLimit,
                        String businessTypeId, String schemeTypeKey, String ratingKey, String ratingOperatorKey,
                        String localAuthorityId, String countryId, String sortOptionKey, String pageNumber, String pageSize) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.maxDistanceLimit = maxDistanceLimit;
        this.businessTypeId = businessTypeId;
        this.schemeTypeKey = schemeTypeKey;
        this.ratingKey = ratingKey;
        this.ratingOperatorKey = ratingOperatorKey;
        this.localAuthorityId = localAuthorityId;
        this.countryId = countryId;
        this.sortOptionKey = sortOptionKey;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;

    }
    public String simple_url() {
        StringBuilder s_holder = new StringBuilder();
        s_holder.append("http://api.ratings.food.gov.uk/Establishments?");

        if (address == null){
            s_holder.append(String.format("name=%s&", name));
        }
        else{
            s_holder.append(String.format("address=%s&", address));
        }
        s_holder.append(String.format("sortOptionKey=%s&", sortOptionKey));
        s_holder.append(String.format("pageSize=%s&", pageSize));
        return s_holder.toString();
    }


}
