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
import org.springframework.data.mongodb.core.query.Update;
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
            if(data.get("password").equals("")){
                result.put("state",201);
                result.put("errMsg","密码不能为空");
                throw new Exception();
            }
            Query query = new Query();
            Student student;
            if(!data.get("telephone").equals("") ){
                query.addCriteria(Criteria.where("telephone").is(data.get("telephone")));
            }else if(!data.get("email").equals("") ){
                query.addCriteria(Criteria.where("email").is(data.get("email")));
            }else if(!data.get("studentId").equals("")){
                query.addCriteria(Criteria.where("studentId").is(data.get("studentId")));
            }else {
                result.put("errMsg","账号不能为空");
                throw new Exception();
            }

            student = getMongoTemplate().findOne( query, Student.class );
            if(student == null){
                result.put("state",201);
                result.put("errMsg","用户不存在");
                throw new Exception();
            }
            if(student.getPassword().equals(data.get("password"))){
                result.put("state",200);
                result.put("soid",student.getId().toString());
            }else{
                result.put("state",201);
                result.put("errMsg","密码错误");
                throw new Exception();
            }
        }catch (Exception e){
            result.put("state",201);
            System.out.println(e.getMessage());
        }
        return result;
    }

    public JSONObject register(Student student){
        JSONObject result = new JSONObject();
        try {
            Query query = new Query();
            if (student.getStudentId() != null){
                query.addCriteria(Criteria.where("studentId").is(student.getStudentId()));
                if (getMongoTemplate().findOne(query, Student.class) != null) {
                    result.put("status", "201");
                    result.put("errMsg", "学号重复注册");
                    return result;
                }
            }
           // getMongoTemplate().insert(student);
            Student stu = getMongoTemplate().insert(student);
            result.put("soid",stu.getId().toString());
            result.put("status","200");

        }catch (Exception e){
            result.put("status","201");
            result.put("errMsg","学生注册错误");
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
    public Student getStuByStuOid(String stuOid){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(new ObjectId(stuOid)));
            Student student = getMongoTemplate().findOne(query,Student.class);
            return student;
        }catch (Exception e){
            return null;
        }
    }

    public List<Student> getAllStu(){
        List<Student> list = new ArrayList<>();
        try{

            list = getMongoTemplate().findAll(Student.class);

        }catch (Exception e){

        }
        return list;
    }
    public Boolean addStuToClass(String classOid,List<String> stusOid){
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(new ObjectId(classOid)));
            ClassRoom classRoom = getMongoTemplate().findOne(query,ClassRoom.class);
            for(String stuoid :stusOid){
                Query qry = new Query();
                qry.addCriteria(Criteria.where("id").is(new ObjectId(stuoid)));
                Student student = getMongoTemplate().findOne(qry,Student.class);
                if(student.getClasses()==null){
                    List<ObjectId> list = new ArrayList<>();
                    list.add(new ObjectId(classOid));
                    student.setClasses(list);
                }else
                    student.getClasses().add(new ObjectId(classOid));

                Update update = new Update();
                update.set("classes",student.getClasses());
                getMongoTemplate().upsert(qry , update,Student.class );

                if(classRoom.getStudentsId()==null){
                    List<ObjectId> list = new ArrayList<>();
                    list.add(new ObjectId(stuoid));
                    classRoom.setStudentsId(list);
                }else
                    classRoom.getStudentsId().add(new ObjectId(stuoid));
            }
            Update update = new Update();
            update.set("studentsId",classRoom.getStudentsId());
            getMongoTemplate().upsert(query , update,ClassRoom.class);

            return true;
        }catch (Exception e){
            return false;
        }
    }


}


