package com.seclab.eveng.service;

import com.seclab.eveng.document.ExperimentContent;
import org.bson.types.ObjectId;

import java.util.List;

public interface ExperimentContentService {
     String getExperimentContentDoc();
     ExperimentContent getExperimentContentByExpcontentoid(ObjectId expcontentoid);
     List<ExperimentContent> getExperimentContentByCategory(String category);
     List<ExperimentContent> getExperimentContentByExpContentName(String expcontentname,String category);
}
