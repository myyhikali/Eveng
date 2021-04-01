package com.seclab.eveng.controller;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.ExperimentContent;
import com.seclab.eveng.document.ExperimentManage;
import com.seclab.eveng.document.Port;
import com.seclab.eveng.service.ClassRoomService;
import com.seclab.eveng.service.ExperimentContentService;
import com.seclab.eveng.service.ExperimentManageService;
import com.seclab.eveng.service.StudentService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: eveng
 * @description:
 * @author: Icey
 * @create: 2020-12-10 18:31
 **/
@RestController
@RequestMapping("/experiment")
public class ExperimentController {
    private final ExperimentContentService experimentContentService;
    private final StudentService studentService;
    private final ClassRoomService classRoomService;
    private final ExperimentManageService experimentManageService;
    @Autowired
    public ExperimentController(StudentService studentService,
                                ClassRoomService classRoomService,
                                ExperimentContentService experimentContentService,
                                ExperimentManageService experimentManageService) {
        this.studentService = studentService;
        this.classRoomService = classRoomService ;
        this.experimentContentService = experimentContentService;
        this.experimentManageService = experimentManageService;
    }

    @GetMapping("/getcontentdoc")
    @ResponseBody
    public JSONObject getContentDoc(){
        JSONObject result = new JSONObject() ;
        try{
            result.put("docUrl",experimentContentService.getExperimentContentDoc());
            result.put("status",200);

        }catch (Exception e){
            result.put("status",201);
        }
        return result;
    }

    @GetMapping("/findexpbycoid")
    @ResponseBody
    public JSONArray findExpByCid(@RequestParam String classoid){
        JSONArray result = new JSONArray();
        try{
            ClassRoom classRoom = classRoomService.getClassByClassId(new ObjectId(classoid));
            List<ObjectId> expsoid = classRoom.getExperimentsId();
            List<ExperimentManage> experimentManages = experimentManageService.getExperimentByeId(expsoid);
            for(ExperimentManage experimentManage : experimentManages){
                ObjectId expcontentOid = experimentManage.getExperimentalContentId();
                ExperimentContent experimentContent = experimentContentService.getExperimentContentByExpcontentoid(expcontentOid);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("experimentManageOid",experimentManage.getId().toString());
                jsonObject.put("experimentContentName",experimentManage.getExperimentContentName());
//                jsonObject.put("beginTime",experimentManage.getBeginTime());
//                jsonObject.put("deadline",experimentManage.getDeadline());
                jsonObject.put("containerOccupation",experimentManage.getContainerOccupation());
                jsonObject.put("containerTopLimit",experimentManage.getContainerTopLimit());

                jsonObject.put("experimentContentOid",experimentContent.getId().toString());
                jsonObject.put("cpuTopLimit",experimentContent.getCpuTopLimit());
                jsonObject.put("memoryTopLimit",experimentContent.getMemoryTopLimit());
                jsonObject.put("storageTopLimit",experimentContent.getStorageTopLimit());
                jsonObject.put("url",experimentContent.getUrl());
                result.add(jsonObject);
            }


        }catch (Exception e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","201");
            jsonObject.put("errMsg","error");
            result.add(jsonObject);
        }
        return result;
    }

    @GetMapping("/findexpbycategory")
    @ResponseBody
    public JSONObject findExpByCategory(@RequestParam String category){
        JSONObject result = new JSONObject();

        try{
            JSONArray data = new JSONArray();
            List<ExperimentContent> experimentContents = experimentContentService.getExperimentContentByCategory(category);
            for(ExperimentContent experimentContent : experimentContents){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("experimentContentOid",experimentContent.getId().toString());
                jsonObject.put("experimentContentName",experimentContent.getExperimentContentName());
                jsonObject.put("cpuTopLimit",experimentContent.getCpuTopLimit());
                jsonObject.put("memoryTopLimit",experimentContent.getMemoryTopLimit());
                jsonObject.put("storageTopLimit",experimentContent.getStorageTopLimit());
                jsonObject.put("url","http://localhost:8080/pdf/"+experimentContent.getUrl());
                data.add(jsonObject);
            }
            result.put("status","200");
            result.put("experimentlist",data);
        }catch (Exception e){
            result.put("status","201");
            result.put("errMsg","error");
        }
        return result;
    }

    @GetMapping("/findexpbyexpcontentname")
    @ResponseBody
    public JSONObject findExpByExpContentName(@RequestParam Map<String,String> data){
        JSONObject result = new JSONObject();
        try{
            JSONArray array = new JSONArray();
            List<ExperimentContent> experimentContents = experimentContentService.getExperimentContentByExpContentName(data.get("contentname"),data.get("category"));
            for(ExperimentContent experimentContent : experimentContents){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("experimentContentOid",experimentContent.getId().toString());
                jsonObject.put("experimentContentName",experimentContent.getExperimentContentName());
                jsonObject.put("cpuTopLimit",experimentContent.getCpuTopLimit());
                jsonObject.put("memoryTopLimit",experimentContent.getMemoryTopLimit());
                jsonObject.put("storageTopLimit",experimentContent.getStorageTopLimit());
                jsonObject.put("url","http://localhost:8080/pdf/"+experimentContent.getUrl());
                array.add(jsonObject);
            }
            result.put("status","200");
            result.put("experimentlist",array);

        }catch (Exception e){
            result.put("status","201");
            result.put("errMsg","error");
        }
        System.out.println("-----"+result);
        return result;
    }

    @GetMapping("/startexp")
    @ResponseBody
    public JSONObject startExp(@RequestParam Map<String,String> data){
        JSONObject result = new JSONObject();
        try{
            String coid = data.get("classoid");
            String uoid = data.get("useroid");
            String expoid = data.get("experimentoid");
            String containerid = coid+"_"+uoid+"_"+expoid;

            Port port = experimentManageService.startExp(containerid);
            if (port == null){
                throw new Exception();
            }
            result.put("containerid",containerid);
            result.put("startport",port.getStartPort());
            result.put("endport",port.getEndPort());

            result.put("status",200);

        }catch (Exception e){
            result.put("status",201);
            result.put("msg","端口忙碌");
        }
        return result;
    }

    @GetMapping("/endexp")
    @ResponseBody
    public JSONObject endExp(@RequestParam String containerid){
        JSONObject result = new JSONObject();
        try{
            if(experimentManageService.endExp(containerid)!=1){
                throw new Exception();
            }
            result.put("status",200);
            result.put("msg","成功关闭");
        }catch (Exception e){
            result.put("status",201);
            result.put("msg","error");
        }
        return result;
    }
}
