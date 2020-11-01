package com.fpt.service;

import com.fpt.entity.ReportDetails;
import org.springframework.stereotype.Service;

@Service
public interface ReportDetailService {
    boolean addReportDetail(ReportDetails reportDetails);

}
