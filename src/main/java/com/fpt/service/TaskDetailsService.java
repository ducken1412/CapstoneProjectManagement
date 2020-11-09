package com.fpt.service;

import com.fpt.entity.Reports;
import com.fpt.entity.TaskDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskDetailsService {
    boolean saveTaskDetails(List<TaskDetails> taskDetails);
    Integer findMaxWeek();

}
