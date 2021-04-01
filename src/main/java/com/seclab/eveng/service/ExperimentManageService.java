package com.seclab.eveng.service;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.ExperimentManage;
import com.seclab.eveng.document.Port;
import org.bson.types.ObjectId;

import java.util.List;

public interface ExperimentManageService {
    List<ExperimentManage> getExperimentByeId(List<ObjectId> eid);
    Port startExp(String containerid);
    int endExp(String containerid);
}
