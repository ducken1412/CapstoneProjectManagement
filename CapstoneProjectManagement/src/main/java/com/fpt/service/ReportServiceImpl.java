package com.fpt.service;

import com.fpt.entity.Reports;
import com.fpt.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements  ReportService{
    @Autowired
    private ReportRepository reportRepository;

    @Override
    public boolean addReport(Reports reports) {
        try{
            reportRepository.save(reports);
            return true;
        }catch (Exception e){
            System.out.println("error add report");
        }
        return false;
    }

    @Override
    public Reports getReportsById(Integer id) {
        return reportRepository.getReportsById(id);
    }

    @Override
    public void addReportUserTable(Integer rid, String uid) {
        reportRepository.addReportUserTable(rid,uid);
    }
}
