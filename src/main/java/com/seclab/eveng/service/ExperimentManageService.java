package com.seclab.eveng.service;

import com.seclab.eveng.document.ClassRoom;
import com.seclab.eveng.document.ExperimentManage;
import org.bson.types.ObjectId;

import java.util.List;

public interface ExperimentManageService {
    List<ExperimentManage> getExperimentByeId(List<ObjectId> eid);
}
