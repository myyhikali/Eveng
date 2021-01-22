package com.seclab.eveng.document;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="Student")
@Data
public class Student {
    @Id
    private ObjectId id;//require automatic generation
    private String studentId; //学号
    private String studentName; // 用户昵称,最长最允许25个字符
    private String telephone; // 用户电话
    private String email; // 用户邮箱
    private String password; // 用户密码
    private int score; // 用户得分
    private int state; // 用户状态：0禁止 | 1未实名 | 2正常
    private String authority;// 校内人员/校外人员
    //private Date lastTime; // 上一次登陆时间
    private Date registerDate;
    private ObjectId classes;

}
