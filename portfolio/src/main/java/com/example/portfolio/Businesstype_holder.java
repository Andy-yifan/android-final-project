package com.example.portfolio;

public class Businesstype_holder {

    //we need name to display, id to search, have to hold bouth

    private String name;
    private String id;

    public Businesstype_holder(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getBusinessId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

}
