package com.seclab.eveng.controller;

import com.seclab.eveng.document.Teacher;
import com.seclab.eveng.service.ManagerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerController {
//    private ManagerService managerService;
//    @Autowired
//    public ManagerController(ManagerService managerService){
//        this.managerService = managerService;
//    }
//
//    @PostMapping("/test")
//    public JSONArray test(@RequestBody Object object){
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("test","No Error Buff get");
//            jsonArray.add(jsonObject);
//            System.out.println("------test");
//            System.out.println(object.getClass());
//            System.out.println(object);
//
//        }catch (Exception e) {
//            jsonObject.put("error", e.getMessage());
//            jsonArray.add(jsonObject);
//        }
//        return jsonArray;
//    }
//
//    /**
//     *
//     * @param manager
//     * @return
//     */
//    @GetMapping("/login")
//    @ResponseBody
//    public Map<String,Object> login(@RequestBody Teacher manager){
//        System.out.println("----login函数已调用");
//        Map<String,Object> data=new HashMap<>();
//        try{
//
//            Teacher managerMessage=managerService.getManager(manager.getManagerName());
//            if(managerMessage!=null&&manager.getPassword().equals(managerMessage.getPassword())){
//                data.put("status","success");
//                data.put("username",managerMessage.getManagerName());
//                data.put("headPortrait", managerMessage.getHeadPortrait());
//                data.put("level",managerMessage.getLevel());
//                return data;
//            }
//            else{
//                data.put("status","login fail");
//                data.put("type", "customer");
//                data.put("errMsg","用户名/密码错误");
//            }
//        }catch (Exception e){
//            System.out.println("----ManagerController:"+e.getMessage());
//            data.put("status","error");
//            data.put("errorMsg", e.getMessage());
//        }
//        return data;
//    }
}
