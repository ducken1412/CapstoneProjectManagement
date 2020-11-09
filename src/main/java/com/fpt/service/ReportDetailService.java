package com.fpt.service;

import com.fpt.entity.ReportDetails;
import com.fpt.entity.Reports;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReportDetailService {
    boolean addReportDetail(ReportDetails reportDetails);

    ReportDetails getReportDetailByReportId(Integer id);

    List<Integer> getListReportIdByUserId(String id);

    //pagination ReportDetails by user id login
    Page<ReportDetails> getTitlePagginByUserId(Pageable pageable, String id);
}
