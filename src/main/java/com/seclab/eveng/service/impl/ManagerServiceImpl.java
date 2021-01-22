package com.seclab.eveng.service.impl;

import com.seclab.eveng.document.Teacher;
import com.seclab.eveng.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate(){
        if(this.mongoTemplate == null){
            System.out.println("----mongoTemplate注入失败----");
        }
        return mongoTemplate;
    }
    @Autowired
    public ManagerServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }
    @Override
    public Teacher getManager(String managerName){
        Query query = new Query();
        query.addCriteria(Criteria.where("managerName").is(managerName));
        Teacher manager=getMongoTemplate().findOne(query, Teacher.class);
        if(manager==null) {
            return null;
        }
        return manager;
    }
    @Override
    public List<Teacher> getAllManagers(){
        return getMongoTemplate().findAll(Teacher.class);
    }

}
