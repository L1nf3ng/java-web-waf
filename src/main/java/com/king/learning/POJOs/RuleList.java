package com.king.learning.POJOs;

import java.util.List;
import com.king.learning.POJOs.Rule;

public class RuleList{
    
    int num;
    List<Rule> rules;

    public RuleList(){}

    public RuleList(int i, List<Rule> s){
        num = i;
        rules = s;
    }

    private void adjustArray(){
        int  i = 1;
        for (Rule rule: rules){
            rule.setId(i);
            i++;
        }
    }

    public int getNum(){
        return num;
    }
    public void setNum(int d){
        num  = d;
    }
    public List<Rule> getRules(){
        return rules;
    }
    public void setRules(List<Rule> s){
        rules = s;
    }

    // 增、删、改
    public void addRules(List<Rule> s){
        rules.addAll(s);
        adjustArray();
    }
    public void delRules(List<Rule> s){
        rules.removeAll(s);
        adjustArray();
    }
    public void modifyRules(int index, String s){
        Rule t = new Rule(index, s);
        rules.set(index-1, t);
        adjustArray();
    }
}

