package com.seclab.eveng.service.impl;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.ExperimentManage;
import com.seclab.eveng.service.ExperimentContentService;
import com.seclab.eveng.service.ExperimentManageService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExperimentManageServiceImpl implements ExperimentManageService {
    private final MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate(){
        if(this.mongoTemplate == null){
            System.out.println("----mongoTemplate注入失败----");
        }
        return mongoTemplate;
    }
    @Autowired
    public ExperimentManageServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }

    public List<ExperimentManage> getExperimentByeId(List<ObjectId> eid){
        List<ExperimentManage> list = new ArrayList<>();
        try{
            for (ObjectId id : eid ){
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(id));
                list.add(getMongoTemplate().findOne(query, ExperimentManage.class));
            }

        }catch (Exception e){

        }
        return list;
    }
}
