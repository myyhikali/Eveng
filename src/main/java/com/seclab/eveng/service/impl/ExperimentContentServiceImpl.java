package com.seclab.eveng.service.impl;

import com.seclab.eveng.document.ExperimentContent;
import com.seclab.eveng.service.ExperimentContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: eveng
 * @description: 实验内容 具体方法定义
 * @author: Icey
 * @create: 2020-12-09 11:01
 **/
@Service
public class ExperimentContentServiceImpl implements ExperimentContentService {
    private final MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate(){
        if(this.mongoTemplate == null){
            System.out.println("----mongoTemplate注入失败----");
        }
        return mongoTemplate;
    }
    @Autowired
    public ExperimentContentServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }
    public String getExperimentContentDoc(){
        String url = "http://localhost:8080/doc/";
        try{
            return url+"experiment1.doc";
        }catch (Exception e){

            return null;
        }

    }
}
