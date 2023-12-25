package com.example.activitymonitor.report.infrastructure.mongo.repository;

import com.example.activitymonitor.report.domain.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report,String> {

}
