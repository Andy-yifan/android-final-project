package com.example.portfolio;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

//https://prasanta-paul.blogspot.com/2010/06/android-parcelable-example.html
public class Info_holder extends ArrayList<Info_holder> implements Parcelable {

    private String name;
    private String businessType;
    private String rating;
    private String ratingDate;
    private String postCode;

    public Info_holder(String name, String businessType, String rating, String ratingDate, String postCode){
        this.name = name;
        this.businessType = businessType;
        this.rating = rating;
        this.ratingDate = ratingDate;
        this.postCode = postCode;
    }

    public static final Creator<Info_holder> CREATOR = new Creator<Info_holder>() {
        @Override
        public Info_holder createFromParcel(Parcel source) {
            return new Info_holder(source);
        }

        @Override
        public Info_holder[] newArray(int size) {
            return new Info_holder[size];
        }
    };

    public Info_holder(Parcel source) {
        name = source.readString();
        businessType = source.readString();
        rating = source.readString();
        ratingDate = source.readString();
        postCode = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.v(TAG, "writeToParcel..."+ flags);
        dest.writeString(name);
        dest.writeString(businessType);
        dest.writeString(rating);
        dest.writeString(ratingDate);
        dest.writeString(postCode);
    }

    public String getName() {
        return name;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getRating() {
        return rating;
    }

    public String getRatingDate() {
        return ratingDate;
    }

    public String getPostCode() {
        return postCode;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setRatingDate(String ratingDate) {
        this.ratingDate = ratingDate;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    public String toString() {
            String result = name+ businessType+rating+ ratingDate + postCode ;
        return result;

    }
    public String getinfo(){
        String result = businessType+rating+ ratingDate + postCode ;
        return result;
    }

}
