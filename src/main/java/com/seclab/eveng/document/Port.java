package com.seclab.eveng.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Port")
@Data
public class Port {
    @Id
    private ObjectId id;
    private int startPort;
    private int endPort;
    private ObjectId containerId;
    private int mark;// 0为空，1为占用
}
