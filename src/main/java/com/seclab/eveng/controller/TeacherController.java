package com.seclab.eveng.controller;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.Student;
import com.seclab.eveng.document.Teacher;
import com.seclab.eveng.service.ClassRoomService;
import com.seclab.eveng.service.StudentService;
import com.seclab.eveng.service.TeacherService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final ClassRoomService classRoomService;
    @Autowired
    public TeacherController(StudentService studentService,TeacherService teacherService,ClassRoomService classRoomService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.classRoomService = classRoomService;
    }

    @GetMapping("/getallstudent")
    @ResponseBody
    public JSONArray getAllStudent(@RequestParam String tOId){
        JSONArray result = new JSONArray();
        try{
            Teacher teacher = teacherService.getTeacherById(tOId);
            if(teacher == null){
                throw new Exception();
            }
            List<Student> list = studentService.getAllStu();
            for(Student student : list){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",student.getId().toString());
                jsonObject.put("stuId",student.getStudentId());
                jsonObject.put("stuName",student.getStudentName());
                result.add(jsonObject);
            }
        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","201");
            jsonObject.put("errMsg","无权限");
            result.add(jsonObject);
        }
        return result;
    }
    @GetMapping("/getclassesbyteacherid")
    @ResponseBody
    public JSONArray getClassesByteacherId(@RequestParam String toid){
        JSONArray result = new JSONArray();
        try{
            Teacher teacher = teacherService.getTeacherById(toid);
            if(teacher == null){
                throw new Exception();
            }

            List<ObjectId> classesId = teacher.getClasses();
            List<ClassRoom> classRooms = classRoomService.getClassesByClassId(classesId);
            for (ClassRoom classRoom : classRooms){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("classId",classRoom.getId().toString());
                jsonObject.put("className",classRoom.getClassName());
                result.add(jsonObject);
            }

        }catch (Exception e){
            JSONObject data = new JSONObject();
            data.put("status",201);
            result.add(data);
        }
        return result;
    }
    @GetMapping("/addnewclass")
    @ResponseBody
    public JSONObject addNewClass(@RequestParam Map<String,String> data){
        JSONObject result = new JSONObject();
        try{
            if(teacherService.getTeacherById(data.get("toid"))==null){
                throw new Exception();
            }
            ClassRoom classRoom = classRoomService.addClass(data.get("classname"),data.get("toid"));
            if(classRoom == null){
                throw new Exception();
            }
            result.put("status","200");
            result.put("classid",classRoom.getId().toString());
        }catch (Exception e){
            result.put("status","201");
            result.put("errMsg","创建失败");

        }
        return result;
    }

    @GetMapping("/addstustoclass")
    @ResponseBody
    public JSONObject addStusToClass(@RequestBody  Map<String,Object> data){
        JSONObject result = new JSONObject();
        try{
            String classOid = data.get("classoid").toString();
            ArrayList<String> stusOid = (ArrayList<String>) data.get("stusoid");

            if(studentService.addStuToClass(classOid,stusOid))
                result.put("status","200");
            else
                result.put("status","201");
        }catch (Exception e){
            result.put("status","201");
            result.put("errMsg","加入失败");
        }
        return result;
    }


}
