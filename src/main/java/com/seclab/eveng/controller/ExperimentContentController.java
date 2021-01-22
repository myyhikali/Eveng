package com.seclab.eveng.controller;

import com.seclab.eveng.service.ExperimentContentService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: eveng
 * @description:
 * @author: Icey
 * @create: 2020-12-10 18:31
 **/
@RestController
@RequestMapping("/experimentcontent")
public class ExperimentContentController {
    private final ExperimentContentService experimentContentService;

    @Autowired
    public ExperimentContentController(ExperimentContentService experimentContentService) {
        this.experimentContentService = experimentContentService;
    }

    @GetMapping("/getcontentdoc")
    @ResponseBody
    public JSONObject getContentDoc(){
        JSONObject result = new JSONObject() ;
        try{
            result.put("docUrl",experimentContentService.getExperimentContentDoc());
            result.put("status",200);

        }catch (Exception e){
            result.put("status",201);
        }
        return result;
    }
}
