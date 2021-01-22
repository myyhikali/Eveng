package com.seclab.eveng.document;

import com.mongodb.internal.connection.Time;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @program: eveng
 * @description: 实验预约表
 * @author: Icey
 * @create: 2020-12-08 10:49
 **/
@Document(collection="Appointment")
@Data
public class Appointment {
    @Id
    private ObjectId id;
    private Time startTime;
    private Time deadline;
    private ObjectId experimentId;
    private int containerNum;
}
