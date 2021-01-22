package com.seclab.eveng.service;
import com.seclab.eveng.document.Teacher;
import java.util.List;

public interface ManagerService {
    Teacher getManager(String managerName);
    List<Teacher> getAllManagers();

}
