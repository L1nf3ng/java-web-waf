package com.king.learning.POJOs;

import java.util.List;
import com.king.learning.POJOs.Rule;

public class RuleList{
    
    List<Rule> rules;
    public List<Rule> getRules(){
        return rules;
    }
    public void setRules(List<Rule> s){
        rules = s;
    }

    // 增、删、改
    public void addRules(List<Rule> s){
        rules.addAll(s);
    }
    public void delRules(List<Rule> s){
        rules.removeAll(s);
    }
    public void modifyRules(int index, String s){
        Rule t = new Rule(index, s);
        rules.set(index-1, t);
    }
}

