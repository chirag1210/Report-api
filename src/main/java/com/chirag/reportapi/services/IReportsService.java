package com.chirag.reportapi.services;

import com.chirag.reportapi.entities.EligibilityDtls;
import com.chirag.reportapi.requests.SearchRequest;
import com.chirag.reportapi.responses.SearchResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface IReportsService {

    public boolean createPlan(EligibilityDtls eligibilityDtls);

    public List<String> getPlanName();

    public List<String> getPlanStatus();

    public List<SearchResponse> search(SearchRequest searchRequest) throws Exception;

    public void generateExcel(HttpServletResponse response) throws Exception;

    public void generatePDF(HttpServletResponse response);
}
