package com.seclab.eveng.service;

import com.seclab.eveng.document.Student;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.Map;

public interface StudentService {

    JSONObject login(Map<String,String> data);
    JSONObject register(Student student);
}

