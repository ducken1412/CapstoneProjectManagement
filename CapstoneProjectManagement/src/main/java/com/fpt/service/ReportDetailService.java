package com.fpt.service;

import com.fpt.entity.ReportDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportDetailService {
    boolean addReportDetail(ReportDetails reportDetails);

    ReportDetails getReportDetailByReportId(Integer id);

    List<Integer> getListReportIdByUserId(String id);
}
