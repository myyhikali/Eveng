package com.seclab.eveng.controller;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.ExperimentContent;
import com.seclab.eveng.document.ExperimentManage;
import com.seclab.eveng.document.Student;
import com.seclab.eveng.service.ClassRoomService;
import com.seclab.eveng.service.ExperimentContentService;
import com.seclab.eveng.service.ExperimentManageService;
import com.seclab.eveng.service.StudentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: eveng
 * @description: user manage
 * @author: Icey
 * @create: 2020-10-22 14:39
 **/
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final ClassRoomService classRoomService;
    private final ExperimentContentService experimentContentService ;
    private final ExperimentManageService experimentManageService;
    @Autowired
    public StudentController(StudentService studentService,
                             ClassRoomService classRoomService,
                             ExperimentContentService experimentContentService,
                             ExperimentManageService experimentManageService
                             ) {
        this.studentService = studentService;
        this.classRoomService = classRoomService ;
        this.experimentContentService = experimentContentService;
        this.experimentManageService = experimentManageService;
    }
    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "hello!";
    }

    @GetMapping("/login")
    @ResponseBody
    public JSONObject login(@RequestParam Map<String,String> data){
        // System.out.println("----login函数已调用");
        JSONObject result = new JSONObject() ;
        try{
            result = studentService.login(data);
        }catch (Exception e){

        }
        return result;
    }
    @GetMapping("/register")
    @ResponseBody
    public JSONObject register(@RequestParam Map<String,String> data){
        JSONObject result = new JSONObject();
        try{
            //{studentId:null,studentName,telephone,email:null,password,authority}
            if(data.get("studentName") == "" ){
                //result.put("status","201");
                result.put("errMsg","名字不能为空");
                throw new Exception();
            }
            if(data.get("telephone") == ""){
                result.put("errMsg","电话不能为空");
                throw new Exception();
            }
            if(data.get("password") == ""){
                result.put("errMsg","密码不能为空");
                throw new Exception();
            }
            Student student = new Student();
            student.setStudentId(data.get("studentId"));
            student.setStudentName(data.get("studentName"));
            student.setTelephone(data.get("telephone"));
            student.setEmail(data.get("email"));
            student.setPassword(data.get("password"));
            student.setAuthority(data.get("authority"));//0校内人员/1校外人员
            student.setRegisterDate(new Date(System.currentTimeMillis()));
            student.setScore(0);
            student.setState(2);
            student.setClasses(null);
            result = studentService.register(student);
//            result.put("status","200");
        }catch (Exception e){
            result.put("status","201");
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/findallclassbysid")
    @ResponseBody
    public JSONObject findAllClassBySid(@RequestParam String stuoid){

        JSONObject result = new JSONObject() ;
        try{
            Student student = studentService.getStuByStuOid(stuoid);
            if(student == null){
                throw new Exception();
            }
            result.put("status","200");
            List<String> list = new ArrayList<>();
            for (ObjectId objectId : student.getClasses()){
                list.add(objectId.toString());
            }
            result.put("classes",list);

        }catch (Exception e){
            result.put("status","201");
        }
        return result;
    }



}
