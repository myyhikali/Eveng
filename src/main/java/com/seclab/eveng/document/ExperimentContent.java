package com.seclab.eveng.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @program: eveng
 * @descripton: 实验内容表
 * @author: Icey
 * @create: 2020-12-08 11:34
 **/
@Document(collection = "ExperimentContent")
@Data
public class ExperimentContent {
    private ObjectId id ;
    private String experimentContentName;
    private Object content;
    private int cpuTopLimit;
    private int memoryTopLimit;
    private int storageTopLimit;
}
