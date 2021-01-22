package com.seclab.eveng.service.impl;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.Student;
import com.seclab.eveng.document.Teacher;
import com.seclab.eveng.service.StudentService;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: eveng
 * @description:
 * @author: Icey
 * @create: 2020-10-19 20:12
 **/
@Service
public class StudentServiceImpl implements StudentService {

    private final MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate(){
        if(this.mongoTemplate == null){
            System.out.println("----mongoTemplate注入失败----");
        }
        return mongoTemplate;
    }
    @Autowired
    public StudentServiceImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate=mongoTemplate;
    }
    /**
     * username pwd verifycode
     * @param
     * @return errMsg state:{200,201}
     */
    @Override
    public JSONObject login(Map<String,String> data){
        JSONObject result = new JSONObject() ;
        try{
            if(data.get("telephone") == null && data.get("email") == null && data.get("studentNumber") == null){
                result.put("state",201);
                result.put("errMsg","ID can't be null");
            }
            if(data.get("password") == null){
                result.put("state",201);
                result.put("errMsg","password can't be null");
            }
            Query query = new Query();
            Student student;
            if(data.get("telephone") != null ){
                query.addCriteria(Criteria.where("telephone").is(data.get("telephone")));
            }else if(data.get("email") != null ){
                query.addCriteria(Criteria.where("telephone").is(data.get("email")));
            }else {
                query.addCriteria(Criteria.where("telephone").is(data.get("studentNumber")));
            }
            student = getMongoTemplate().findOne( query, Student.class );
            if(student == null){
                result.put("state",201);
                result.put("errMsg","user not exit");
            }
            if(student.getPassword().equals(data.get("password"))){
                result.put("state",200);

            }else{
                result.put("state",201);
                result.put("errMsg","password error");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public JSONObject register(Student student){
        JSONObject result = new JSONObject();
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("studentId").is(student.getStudentId()));
            if(getMongoTemplate().findOne(query, Student.class)!=null){
                result.put("status","201");
                result.put("errMsg","学号重复注册");
                return result;
            }
            getMongoTemplate().insert(student);
            result.put("status","200");

        }catch (Exception e){
            result.put("status","201");
            result.put("errMsg","学生插入数据错误");
        }
        return result;

    }

    public List<Student> getStusByStuid(List<ObjectId> stuid){
        List<Student> list = new ArrayList<>();
        try{
            for (ObjectId id : stuid ){
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(id));
                list.add(getMongoTemplate().findOne(query,Student.class));
            }

        }catch (Exception e){

        }
        return list;
    }


}


