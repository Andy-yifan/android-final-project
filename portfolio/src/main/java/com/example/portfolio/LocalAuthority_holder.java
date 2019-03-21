package com.example.portfolio;

public class LocalAuthority_holder {

    private String name;


    private String id;


    public LocalAuthority_holder(String name, String id){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String toString(){
        return name;
    }

}
