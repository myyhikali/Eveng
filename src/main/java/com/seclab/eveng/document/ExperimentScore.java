package com.seclab.eveng.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * @program: eveng
 * @description: 实验成绩表
 * @author: Icey
 * @create: 2020-12-08 11:41
 **/
@Document(collection = "ExperimentScore")
@Data
public class ExperimentScore {
    private ObjectId id;
    private ObjectId ExperimentManageId;
    private Map<ObjectId,Double> score;
}
