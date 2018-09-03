package com.king.learning.POJOs;

public class Rule{
    private int id;
    private String content;

    public Rule(int i, String s){
        id = i;
        content = s;
    }

    public int getId(){
        return id;
    }
    public String getContent(){
        return content;
    }
    public void setId(int i){
        id = i;
    }
    public void setContent(String s){
        content = s;
    }
}