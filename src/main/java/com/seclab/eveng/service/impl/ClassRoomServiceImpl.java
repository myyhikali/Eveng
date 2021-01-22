package com.seclab.eveng.service.impl;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.Student;
import com.seclab.eveng.service.ClassRoomService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: eveng
 * @description:
 * @author: Icey
 * @create: 2020-12-11 11:33
 **/
@Service
public class ClassRoomServiceImpl implements ClassRoomService {
    private final MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate(){
        if(this.mongoTemplate == null){
            System.out.println("----mongoTemplate注入失败----");
        }
        return mongoTemplate;
    }
    @Autowired
    public ClassRoomServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    public List<ClassRoom> getClassesByClassId(List<ObjectId> classesId){
        List<ClassRoom> list = new ArrayList<>();
        try{
            for (ObjectId classId : classesId ){
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(classId));
                list.add(getMongoTemplate().findOne(query,ClassRoom.class));
            }

        }catch (Exception e){

        }
        return list;
    }

    public ClassRoom getClassByClassId(ObjectId classid){
        ClassRoom classRoom = null;
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(classid));
            classRoom = getMongoTemplate().findOne(query,ClassRoom.class);
        }catch (Exception e){

        }
        return classRoom;
    }

}
