package com.seclab.eveng.service.impl;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.ExperimentManage;
import com.seclab.eveng.document.Port;
import com.seclab.eveng.document.Teacher;
import com.seclab.eveng.service.ExperimentContentService;
import com.seclab.eveng.service.ExperimentManageService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;
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

    public Port startExp(String containerid){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("containerId").is(containerid));
            List<Port> list = getMongoTemplate().find(query,Port.class);
            if(list.size()!=0){
                throw new Exception();
            }

            query = new Query();
            query.addCriteria(Criteria.where("mark").is(0));
            Port port = getMongoTemplate().findOne(query,Port.class);

            Query query1 = new Query();
            query1.addCriteria(Criteria.where("id").is(port.getId()));
            Update update = new Update();
            update.set("containerId",containerid);
            update.set("mark",1);
            getMongoTemplate().upsert(query1 , update,Port.class);

            return port;
        }catch (Exception e){
            return null;
        }
    }

    public int endExp(String containerid){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("containerId").is(containerid));
            if(getMongoTemplate().findOne(query,Port.class)==null){
                throw new Exception();
            }
            Update update = new Update();
            update.set("mark",0);
            update.set("containerId",null);
            getMongoTemplate().upsert(query , update,Port.class);

            return 1;
        }catch (Exception e){
            return 0;
        }
    }

}
