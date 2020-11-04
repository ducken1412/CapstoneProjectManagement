package com.fpt.service;

import com.fpt.entity.ReportDetails;
import com.fpt.repository.ReportDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportDetailServiceImpl implements ReportDetailService{
    @Autowired
    private ReportDetailRepository reportDetailRepository;

    @Override
    public boolean addReportDetail(ReportDetails reportDetails) {
        try{
            reportDetailRepository.save(reportDetails);
            return true;
        }catch (Exception e){
            System.out.println("error add report detail");
        }
        return false;
    }

    @Override
    public ReportDetails getReportDetailByReportId(Integer id) {
        return reportDetailRepository.getReportDetailByReportId(id);
    }

    @Override
    public List<Integer> getListReportIdByUserId(String id) {
        return reportDetailRepository.getListReportIdByUserId(id);
    }
}
