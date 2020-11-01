package com.fpt.service;

import com.fpt.entity.ReportDetails;
import com.fpt.repository.ReportDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
