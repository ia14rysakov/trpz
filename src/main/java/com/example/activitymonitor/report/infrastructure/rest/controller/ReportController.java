package com.example.activitymonitor.report.infrastructure.rest.controller;

import com.example.activitymonitor.monitoring.application.abstractfactory.AbstractMonitor;
import com.example.activitymonitor.report.application.visitor.ReportVisitor;
import com.example.activitymonitor.report.domain.Report;
import com.example.activitymonitor.report.infrastructure.rest.dto.ReportRequestDto;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/report")
public class ReportController {

    private Logger logger = Logger.getLogger(ReportController.class.getName());

    private Map<String, ReportVisitor> reportVisitorMap;

    private Map<String, AbstractMonitor> monitorMap;

    public ReportController(List<ReportVisitor> reportVisitorMap, List<AbstractMonitor> monitorList) {

        this.reportVisitorMap = reportVisitorMap.stream()
                .collect(java.util.stream.Collectors.toMap(ReportVisitor::getReportName,
                        java.util.function.Function.identity()));

        this.monitorMap = monitorList
                .stream()
                .collect(Collectors.toMap(AbstractMonitor::getOsType, Function.identity()));
    }

    @PostMapping("/download")
    public Mono<ResponseEntity<Resource>> downloadReport(@RequestBody ReportRequestDto reportRequestDto) {
        logger.info("Report request: " + reportRequestDto);
        return generateReport(reportRequestDto).log()
                .flatMap(this::generatePdfFromReport)
                .map(fileContent -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"report.pdf\"")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new ByteArrayResource(fileContent)));
    }

    private Mono<Report> generateReport(ReportRequestDto reportRequestDto) {
        AbstractMonitor currentMonitor = monitorMap.get(reportRequestDto.getOsType());
        ReportVisitor reportVisitor = reportVisitorMap.get(reportRequestDto.getReportType());
        return currentMonitor.getConcreteMonitoring(reportRequestDto).accept(reportVisitor);
    }

    private Mono<byte[]> generatePdfFromReport(Report report) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Adding content to the PDF
            document.add(new Paragraph("Report Title: " + report.getTitle()));
            document.add(new Paragraph("Timestamp: " + report.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
            document.add(new Paragraph("Duration: " + report.getDuration()));
            document.add(new Paragraph("Data: " + report.getData()));
            document.add(new Paragraph("Summary: " + report.getSummary()));


            document.close();
            return Mono.just(outputStream.toByteArray());
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

}
