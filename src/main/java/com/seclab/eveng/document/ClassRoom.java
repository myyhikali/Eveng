package com.seclab.eveng.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @program: eveng
 * @description: 班级表
 * @author: Icey
 * @create: 2020-12-08 11:08
 **/
@Document(collection="ClassRoom")
@Data
public class ClassRoom {
    @Id
    private ObjectId id;
    private String className;
    private List<ObjectId> teachersId;
    private List<ObjectId> studentsId;
    private List<ObjectId> experimentsId;
}
