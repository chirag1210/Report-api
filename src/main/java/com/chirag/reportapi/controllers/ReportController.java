package com.chirag.reportapi.controllers;

import com.chirag.reportapi.constants.AppConstants;
import com.chirag.reportapi.entities.EligibilityDtls;
import com.chirag.reportapi.props.AppProperties;
import com.chirag.reportapi.requests.SearchRequest;
import com.chirag.reportapi.responses.SearchResponse;
import com.chirag.reportapi.services.IReportsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ReportController {

    private IReportsService reportsService;

    private Map<String, String> messages;

    public ReportController(IReportsService reportsService, AppProperties appProperties) {
        this.reportsService = reportsService;
        this.messages = appProperties.getMessages();
    }

    @GetMapping("/generate-excel")
    public void generateExcel(HttpServletResponse response) throws Exception {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Plans.xls";
        response.setHeader(headerKey, headerValue);
        reportsService.generateExcel(response);
    }

    @GetMapping("/generate-pdf")
    public void generatePdf(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Plans.pdf";
        response.setHeader(headerKey, headerValue);
        reportsService.generatePDF(response);
    }

    @GetMapping("/plans")
    public ResponseEntity<List<String>> getPlanNames() {
        List<String> planNames = reportsService.getPlanName();
        return new ResponseEntity(planNames, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity getPlanStatuses() {
        List<String> planStatus = reportsService.getPlanStatus();
        return new ResponseEntity(planStatus, HttpStatus.OK);
    }

    @PostMapping("/plan")
    public ResponseEntity<String> createPlan(@RequestBody EligibilityDtls eligibilityDtls) {
        boolean isSaved = reportsService.createPlan(eligibilityDtls);
        String response = messages.get(AppConstants.EMPTY_STR);
        if (isSaved) {
            response = messages.get(AppConstants.PLAN_SAVE_SUCC);
        } else {
            response = messages.get(AppConstants.PLAN_SAVE_FAIL);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest request) throws Exception {
        List<SearchResponse> responses = reportsService.search(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
