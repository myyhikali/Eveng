package com.seclab.eveng.service;

import com.seclab.eveng.document.ClassRoom;

import java.util.List;

public interface ClassRoomService {
    List<ClassRoom> getClassesByClassId(List<String> clas);
}
