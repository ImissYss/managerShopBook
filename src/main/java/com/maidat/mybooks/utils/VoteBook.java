package com.maidat.mybooks.utils;

public enum VoteBook {
    READ("READ"),
    READING("READING"),
    WANT_READ("WANT_READ"),
    REMOVE("REMOVE");

    private String voteName = "";
    VoteBook(String name){
        this.voteName = name;
    }
    public boolean is(String name){
        return voteName.equals(name);
    }
    public String getName(){
        return voteName;
    }

}
