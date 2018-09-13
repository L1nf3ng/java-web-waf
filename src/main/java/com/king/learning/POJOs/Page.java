package com.king.learning.POJOs;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import com.king.learning.POJOs.Rule;

public class Page{
    
    // 本页的编号
    int page_no;
    // 本页里的规则数目
    int num; 
    // 本页存储地址
    String filepath;
    // 本页中规则的序号及内容
    List<Rule> rules;

    // constructor 1
    public Page(){}
    // constructor 2
    public Page(int i ,List<Rule> d){
        num = i;
        rules = d;
    }
    public Page(int no, String s){
        page_no  = no;
        filepath = s;
    }

    private void adjustArray(){
        int  i = 1;
        for (Rule rule: rules){
            rule.setId(i);
            i++;
        }
    }

    public void store() throws Exception{
        PrintWriter writer = new PrintWriter(new File(this.filepath));
        for (Rule content : rules){
            writer.println(content);
        }
        writer.close();
    }

    public void restore() throws Exception{
        rules = new ArrayList<Rule>();
        int number = 1;
        BufferedReader read = new BufferedReader(new FileReader(this.filepath));
        String line = null;
        while ( (line = read.readLine()) != null ){
            String newLine= line;
            Rule rule = new Rule(number, newLine);
            rules.add(rule);
            number++;
        } 
        this.num = rules.size();
        read.close();
    }

    public int getPageNo(){
        return page_no;
    }
    public void setPageNo(int s){
        page_no = s;
    }
    public int getNum(){
        return num;
    }
    public void setNum(int d){
        num  = d;
    }
    public String getFilePath(){
        return filepath;
    }
    public void setFilePath(String s){
        filepath = s;
    }
    public List<Rule> getRules(){
        return rules;
    }
    public void setRules(List<Rule> s){
        rules = s;
    }

    // 增、
    public void addRules(List<Rule> s){
        rules.addAll(s);
        adjustArray();
    }
    // 删、
    public void delRules(List<Rule> s){
        for( Rule rule: s){
            int id = rule.getId();
            rules.remove(id-1);
        }
        adjustArray();
    }
    // 改、
    public void modifyRules(List<Rule> s){
        for( Rule rule:s ){
            int id = rule.getId();
            rules.set(id-1, rule);
        }
        adjustArray();
    }
}
