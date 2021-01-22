package com.seclab.eveng.controller;

import com.seclab.eveng.document.Teacher;
import com.seclab.eveng.service.ClassRoomService;
import com.seclab.eveng.service.ExperimentContentService;
import com.seclab.eveng.service.TeacherService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public ClassRoomController(ClassRoomService classRoomService,TeacherService teacherService) {
        this.classRoomService = classRoomService;
        this.teacherService = teacherService;
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

            teacher.getClasses()
        }catch (Exception e){

        }
        return result;
    }

}
