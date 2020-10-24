package com.fpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpt.entity.HistoryRecords;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRecordsRepository extends JpaRepository<HistoryRecords, Integer>{

    @Query("SELECT h FROM HistoryRecords h WHERE h.user.id = ?1")
    List<HistoryRecords> getHistoryRecordsByUserId(String id);
}
