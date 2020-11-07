package com.fpt.service;

import com.fpt.entity.NotificationDetails;
import com.fpt.entity.Notifications;
import com.fpt.entity.Reports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportService {
    boolean addReport(Reports reports);

    Reports getReportsById(Integer id);

    void addReportUserTable(Integer rid, String uid);

    Reports finReportsById(Integer id);


}
