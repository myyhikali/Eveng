package com.seclab.eveng.controller;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.Student;
import com.seclab.eveng.document.Teacher;
import com.seclab.eveng.service.ClassRoomService;
import com.seclab.eveng.service.ExperimentContentService;
import com.seclab.eveng.service.StudentService;
import com.seclab.eveng.service.TeacherService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: eveng
 * @description:
 * @author: Icey
 * @create: 2020-12-11 11:34
 **/
@RestController
@RequestMapping("/class")
public class ClassRoomController {
    private final ClassRoomService classRoomService;
    private final TeacherService teacherService;
    private final StudentService studentService;
    @Autowired
    public ClassRoomController(ClassRoomService classRoomService,TeacherService teacherService,StudentService studentService) {
        this.classRoomService = classRoomService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    @GetMapping("/getclassesbyteacherid")
    @ResponseBody
    public JSONArray getClassesByteacherId(@RequestParam String teacherId){
        JSONArray result = new JSONArray();
        try{
            Teacher teacher = teacherService.getTeacherById(teacherId);
            if(teacher == null){
                JSONObject data = new JSONObject();
                data.put("status",201);
                data.put("errMsg","该教师不存在");
            }

            List<ObjectId> classesId = teacher.getClasses();
            List<ClassRoom> classRooms = classRoomService.getClassesByClassId(classesId);
            for (ClassRoom classRoom : classRooms){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("classId",classRoom.getId());
                jsonObject.put("className",classRoom.getClassName());
                result.add(jsonObject);
            }

        }catch (Exception e){

        }
        return result;
    }

    @GetMapping("/getclassmassage")
    @ResponseBody
    public JSONObject getClassesById(@RequestParam String classId){
        JSONObject result = new JSONObject();
        try{
            ObjectId id = new ObjectId(classId);
            ClassRoom classRoom = classRoomService.getClassByClassId(id);
            JSONObject data = new JSONObject();
            if(classRoom == null){
                //JSONObject data = new JSONObject();
                result.put("status",201);
                result.put("errMsg","没有找到该班级");
                return result;
            }
            data.put("status",200);
            data.put("classId",classRoom.getId());
            data.put("classname",classRoom.getClassName());
            List<ObjectId> experimentsId = classRoom.getExperimentsId();

            List<ObjectId> stuid = classRoom.getStudentsId();
            List<Student> students = studentService.getStusByStuid(stuid);
            List<Map<String,Object>> stumsg = new ArrayList<>();
            for(Student student :students){
                Map<String,Object> map = new HashMap<>();
                map.put("id",student.getId());
                map.put("stuid",student.getStudentId());
                map.put("stuname",student.getStudentName());
                stumsg.add(map);
            }
            data.put("stu",stumsg);




        }catch (Exception e){

        }
        return result;
    }

}
