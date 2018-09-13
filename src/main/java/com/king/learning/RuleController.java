package com.king.learning;

import com.king.learning.constants.Config;
import com.king.learning.POJOs.Page;

import java.io.*;
import java.util.*;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

class MetaInfo{
    private int page_num;
    private Map<Integer, String> name_list;

    public MetaInfo(int s, Map<Integer, String> d){
        page_num = s;
        name_list = d;
    }

    public int getPageNum(){
        return page_num;
    }
    public void setPageNum(int s ){
        page_num = s;
    }
    public Map<Integer,String> getNameList(){
        return name_list;
    }
    public void setNameList(Map<Integer, String> s){
        name_list = s;
    }
}

@RestController
@RequestMapping("/rules")
public class RuleController{

    private MetaInfo metainfo;
    private List<Page> pageLists;

    public RuleController()throws Exception{
        // 1st Step , initialize MetaInfo object! 
        int page_num = 0;
        Map<Integer, String> names = new HashMap<Integer, String>();
        pageLists = new ArrayList<Page>();
        File file = new File(Config.filePath);    
        for (String filename: file.list()) {
            String catalog = filename.substring(0, filename.indexOf("."));
            names.put(page_num+1, catalog);
            Page page1 = new Page(page_num+1, Config.filePath + filename);
            page1.restore();
            pageLists.add(page1);
            page_num ++;
        }
        metainfo = new MetaInfo(page_num, names);
    }

    // 返回元数据Meta-Info，其中包含分类数和分类名称
    @RequestMapping(value="/", produces= MediaType.APPLICATION_JSON_VALUE)
    public MetaInfo display(){
        return metainfo;
    }

    // 查询接口，依据Json中的page_no来确定返回页的内容
    @RequestMapping(value="/select", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public Page selectRule(@RequestBody Page newPage){
        // 列表中的下标比pageNO小1
        int currentNo = newPage.getPageNo()-1;
        Page page = pageLists.get(currentNo);
        return page;
    }

    // 添加接口
    @RequestMapping(value="/add", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public Page addRule(@RequestBody Page newPage){
        Page page = pageLists.get(newPage.getPageNo()-1);
        page.setNum( page.getNum()+newPage.getNum() );
        page.addRules(newPage.getRules());
        return page;
    }

    // 删除接口
    @RequestMapping(value="/del", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public Page deleteRule(@RequestBody Page delPage){
        Page page = pageLists.get(delPage.getPageNo()-1);
        page.setNum( page.getNum()-delPage.getNum() );
        page.delRules( delPage.getRules() );
        return page;
    }

    // 修改接口
    @RequestMapping(value="/modify", method= RequestMethod.POST, produces= MediaType.APPLICATION_JSON_VALUE)
    public Page modifyRule(@RequestBody Page modPage){
        Page page = pageLists.get(modPage.getPageNo()-1);
        page.modifyRules( modPage.getRules() );
        return page;
    }

}