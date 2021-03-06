package com.seclab.eveng.service;

import com.seclab.eveng.document.Student;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface StudentService {

    JSONObject login(Map<String,String> data);
    JSONObject register(Student student);
    List<Student> getStusByStuid(List<ObjectId> stuid);
    Student getStuByStuOid(String stuOid);
    List<Student> getAllStu();
    Boolean addStuToClass(String classOid,List<String> stusOid);
}

