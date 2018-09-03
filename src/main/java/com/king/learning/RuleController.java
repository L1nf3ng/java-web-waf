package com.king.learning;

import com.king.learning.constants.Config;
import com.king.learning.POJOs.Rule;
import com.king.learning.POJOs.RuleList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import java.util.ArrayList;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*************************************
 * 编写5个功能，分别是:
 * 显示display
 * 以下功能通过前端接口暴露给用户：
 * 添加add
 * 修改modify
 * 删除delete
 * 部署deploy
 * 基于文件IO实现与openresty-waf的交互
 *************************************/

//@RestController
@Controller
@RequestMapping("/rules")
public class RuleController{

    private RuleList rules;

    public RuleController()throws Exception{
        // Open file and read Rules
        Config config = new Config();
        rules = new RuleList();
        List<Rule> ruleList = new ArrayList<Rule>();
        int num = 1;
        BufferedReader read = new BufferedReader(new FileReader(config.getFilePath()+config.getFileName()));
        String line = null;
        while ( (line = read.readLine()) != null ){
        //  String newLine = "#rule"+Integer.toString(num) + "# " + line.replace("\\", "");
            String newLine= line;
            Rule rule = new Rule(num, newLine);
            ruleList.add(rule);
            num++;
        } 
        read.close();
        rules.setRules(ruleList);
    }

//  @RequestMapping(value="/", produces= MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value="/")
    public String display(Model model){
        model.addAttribute("rules",rules.getRules());
        return "rulePage";
    }

    // Dynamic redirection to certain form 
    @RequestMapping(value="/{FormName}", method= RequestMethod.GET)
    public String DynamicJump( @PathVariable("FormName") String FormName){
        return FormName+"Form";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String addRule(@RequestParam("content") String ruleContent){
        List<Rule> ringList = new ArrayList<Rule>(); 
        Rule rule = new Rule(rules.getRules().size()+1, ruleContent);
        ringList.add(rule);
        rules.addRules(ringList);
        return "redirect:/rules/";
    }

    @RequestMapping(value="/del", method= RequestMethod.POST)
    public String deleteRule(@RequestParam("id") int id){
        rules.getRules().remove(id-1);
        return "redirect:/rules/";
    }

    @RequestMapping(value="/modify", method= RequestMethod.POST)
    public String modifyRule(@RequestParam("id") int id, @RequestParam("content") String ruleContent){
        rules.modifyRules(id, ruleContent);
        return "redirect:/rules/";
    }

};