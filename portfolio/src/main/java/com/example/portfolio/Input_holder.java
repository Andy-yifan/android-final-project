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

        if (name != null) {
            s_holder.append(String.format("name=%s&", name));
        }
        if(sortOptionKey!=null){
            s_holder.append(String.format("sortOptionKey=%s&", sortOptionKey));
        }
        if(pageSize!=null){
            s_holder.append(String.format("pageSize=%s&", pageSize));
        }
        if(longitude!=null){
            s_holder.append(String.format("longitude=%s&", longitude));
        }
        if(latitude!=null){
            s_holder.append(String.format("latitude=%s&", latitude));
        }
        if(businessTypeId != null) {
            s_holder.append(String.format("businessTypeId=%s&", businessTypeId));
        }
        if (ratingKey != null) {
            s_holder.append(String.format("ratingKey=%s&", ratingKey));
        }
        if(localAuthorityId != null) {
            s_holder.append(String.format("localAuthorityId=%s&", localAuthorityId));
        }
        if(maxDistanceLimit != null) {
            s_holder.append(String.format("maxDistanceLimit=%s&", maxDistanceLimit));
        }

        return s_holder.toString();
    }



}
