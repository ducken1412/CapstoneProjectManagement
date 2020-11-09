package com.fpt.service;

import com.fpt.entity.TaskDetails;
import com.fpt.repository.ReportRepository;
import com.fpt.repository.TaskDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDetailsServiceImpl implements TaskDetailsService{

    @Autowired
    private TaskDetailsRepository taskDetailsRepository;

    @Override
    public boolean saveTaskDetails(List<TaskDetails> taskDetails) {
        try{
            taskDetailsRepository.saveAll(taskDetails);
            return true;
        }catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Override
    public Integer findMaxWeek() {
        try{
            return taskDetailsRepository.getMaxWeek();
        }catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
