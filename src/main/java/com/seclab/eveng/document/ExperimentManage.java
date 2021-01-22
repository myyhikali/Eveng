package com.seclab.eveng.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @program: eveng
 * @description: 实验管理表
 * @author: Icey
 * @create: 2020-12-08 11:20
 **/
@Document(collection = "ExperimentManage")
@Data
public class ExperimentManage {
    private ObjectId id;
    private ObjectId experimentalContentId;
    private String experimentContentName;
    private List<ObjectId> teacherId;
    private Date setTime;
    private Date beginTime;
    private Date deadline;
    private int containerTopLimit;
    private int containerOccupation;
    private double score;
}
