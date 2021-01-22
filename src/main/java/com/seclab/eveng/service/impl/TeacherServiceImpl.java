package com.seclab.eveng.service.impl;

import com.seclab.eveng.document.Teacher;
import com.seclab.eveng.service.TeacherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @program: eveng
 * @description:
 * @author: Icey
 * @create: 2020-12-11 11:50
 **/
@Service
public class TeacherServiceImpl implements TeacherService {
    private final MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate(){
        if(this.mongoTemplate == null){
            System.out.println("----mongoTemplate注入失败----");
        }
        return mongoTemplate;
    }
    @Autowired
    public TeacherServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    @Override
    public Teacher getTeacherById(String teacherId){
        Teacher teacher = null;
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(new ObjectId(teacherId)));
            teacher = getMongoTemplate().findOne(query,Teacher.class);
        }catch (Exception e){

        }
        return teacher;
    }
}
