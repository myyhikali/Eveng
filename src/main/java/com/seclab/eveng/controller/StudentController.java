package com.seclab.eveng.controller;

import com.seclab.eveng.document.Student;
import com.seclab.eveng.service.StudentService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
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
    @GetMapping("register")
    @ResponseBody
    public JSONObject register(@RequestParam Map<String,String> data){
        JSONObject result = new JSONObject();
        try{
            //{studentId:null,studentName,telephone,email:null,password,authority}
            if(data.get("studentName") == null){
                //result.put("status","201");
                result.put("errMsg","名字不能为空");
                throw new Exception();
            }
            if(data.get("telephone") == null){
                result.put("errMsg","电话不能为空");
                throw new Exception();
            }
            Student student = new Student();
            student.setStudentId(data.get("studentId"));
            student.setStudentName(data.get("studentName"));
            student.setTelephone(data.get("telephone"));
            student.setEmail("email");
            student.setPassword("password");
            student.setAuthority("authority");
            student.setRegisterDate(new Date(System.currentTimeMillis()));
            student.setScore(0);
            student.setState(2);
            student.setClasses(null);
            result = studentService.register(student);
            //result.put("status","200");
        }catch (Exception e){
            result.put("status","201");
            System.out.println(e.getMessage());
        }
        return result;
    }
}
