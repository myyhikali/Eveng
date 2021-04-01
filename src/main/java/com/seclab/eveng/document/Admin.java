package com.seclab.eveng.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="Admin")
@Data
public class Admin {
    @Id
    private ObjectId id; //automatic
    private String username;
    private String password;
    private String telephone;
    private String lastIp;
    private Date lastTime;
}
