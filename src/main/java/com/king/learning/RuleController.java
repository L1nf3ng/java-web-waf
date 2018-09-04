package com.king.learning;

import com.king.learning.constants.Config;
import com.king.learning.POJOs.Rule;
import com.king.learning.POJOs.RuleList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

 //@Controller
@RestController
@RequestMapping("/rules")
public class RuleController{

    private RuleList totalRules;

    public RuleController()throws Exception{
        // Open file and read Rules
        Config config = new Config();
        totalRules = new RuleList();
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
        totalRules.setRules(ruleList);
        totalRules.setNum(ruleList.size());
    }

    // Dynamic redirection to certain form 
    @RequestMapping(value="/{FormName}", method= RequestMethod.GET)
    public String DynamicJump( @PathVariable("FormName") String FormName){
        return FormName+"Form";
    }

    @RequestMapping(value="/", produces= MediaType.APPLICATION_JSON_VALUE)
    public RuleList display(Model model){
        model.addAttribute("rules",totalRules.getRules());
        return totalRules;
    }

    @RequestMapping(value="/add", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public RuleList addRule(@RequestBody RuleList newList){
        System.out.println("************************************");
        System.out.println("*----------------------------------*");
        System.out.println("what you get is: "+newList.getNum()+" rules!");
        totalRules.setNum( totalRules.getNum()+newList.getNum() );
        totalRules.addRules( newList.getRules() );
        return totalRules;
    }

    @RequestMapping(value="/del", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public RuleList deleteRule(@RequestBody RuleList delList){
        return totalRules;
    }

    @RequestMapping(value="/modify", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public RuleList modifyRule(@RequestParam("id") int id, @RequestParam("content") String ruleContent){
        totalRules.modifyRules(id, ruleContent);
        return totalRules;
    }

};