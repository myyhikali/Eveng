package com.seclab.eveng.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="Teacher")
@Data
public class Teacher {
    @Id
    private ObjectId id;
    private String teacherName;
    private String password;
    //private String headPortrait;
    private String level;//0:教师 1： 助教
    private List<String> experiments;
    private List<String> classes;


}