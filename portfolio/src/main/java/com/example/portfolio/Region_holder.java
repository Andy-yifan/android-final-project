package com.example.portfolio;

public class Region_holder {
    private String name;


    private String nameKey;
    private String id;

    public Region_holder(String name, String nameKey,String id){
        this.id = id;
        this.nameKey = nameKey;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return nameKey;
    }

    public String getId() {
        return id;
    }
    //due to the arrayadapter call tostring, have to overwrite the tostring.
    public String toString(){
        return name;
    }


}
