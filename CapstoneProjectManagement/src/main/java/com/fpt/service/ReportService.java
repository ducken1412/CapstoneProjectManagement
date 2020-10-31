package com.fpt.service;

import com.fpt.entity.NotificationDetails;
import com.fpt.entity.Reports;
import org.springframework.stereotype.Service;

@Service
public interface ReportService {
    boolean addReport(Reports reports);
}
