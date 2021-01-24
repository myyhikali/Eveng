package com.seclab.eveng.service;

import com.seclab.eveng.document.ClassRoom;
import org.bson.types.ObjectId;

import java.util.List;

public interface ClassRoomService {
    List<ClassRoom> getClassesByClassId(List<ObjectId> classid);
    ClassRoom getClassByClassId(ObjectId classid);
    ClassRoom addClass(String className,String teacherId);
}
