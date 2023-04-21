package com.chirag.reportapi.services;

import com.chirag.reportapi.entities.EligibilityDtls;
import com.chirag.reportapi.repositories.EligibilityDtlsRepo;
import com.chirag.reportapi.requests.SearchRequest;
import com.chirag.reportapi.responses.SearchResponse;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

@Service
public class ReportsServiceImpl implements IReportsService {

    @Autowired
    private EligibilityDtlsRepo eligibilityDtlsRepo;

    @Override
    public boolean createPlan(EligibilityDtls eligibilityDtls) {
        EligibilityDtls eligibilityModel = eligibilityDtlsRepo.save(eligibilityDtls);
        return eligibilityModel != null;
    }

    @Override
    public List<String> getPlanName() {
        return eligibilityDtlsRepo.findDistinctPlanName();
    }

    @Override
    public List<String> getPlanStatus() {
        return eligibilityDtlsRepo.findDistinctPlanStatus();
    }

    @Override
    public List<SearchResponse> search(SearchRequest searchRequest) {

        EligibilityDtls eligibilityDtls = new EligibilityDtls();
        String planName = searchRequest.getPlanName();
        if (planName != null && !planName.equals("")) {
            eligibilityDtls.setPlanName(planName);
        }
        String planStatus = searchRequest.getPlanStatus();
        if (planStatus != null && !planStatus.equals("")) {
            eligibilityDtls.setPlanStatus(planStatus);
        }
        LocalDate planStartDate = searchRequest.getPlanStartDate();
        if (planStartDate != null) {
            eligibilityDtls.setPlanStartDate(planStartDate);
        }
        LocalDate planEndDate = searchRequest.getPlanEndDate();
        if (planEndDate != null) {
            eligibilityDtls.setPlanEndDate(planEndDate);
        }
        Example<EligibilityDtls> example = Example.of(eligibilityDtls);
        List<EligibilityDtls> eligibilityDtlsList = eligibilityDtlsRepo.findAll(example);
        List<SearchResponse> searchResponseList = new ArrayList<>();
        eligibilityDtlsList.forEach(eligibilityDtlsModel -> {
            SearchResponse searchResponse = new SearchResponse();
            searchResponse.setPlanName(eligibilityDtlsModel.getPlanName());
            searchResponse.setPlanStatus(eligibilityDtlsModel.getPlanStatus());
            searchResponse.setName(eligibilityDtlsModel.getName());
            searchResponse.setGender(eligibilityDtlsModel.getGender());
            searchResponse.setEmail(eligibilityDtlsModel.getEmail());
            searchResponse.setSsn(eligibilityDtlsModel.getSsn());
            searchResponse.setMobileNo(eligibilityDtlsModel.getMobileNo());
            searchResponseList.add(searchResponse);
        });
        return searchResponseList;
    }

    @Override
    public void generateExcel(HttpServletResponse response) throws Exception {
        List<EligibilityDtls> eligibilityDtlsList = eligibilityDtlsRepo.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Mobileno");
        headerRow.createCell(2).setCellValue("Gender");
        headerRow.createCell(3).setCellValue("SSN");
        int i = 1;
        eligibilityDtlsList.forEach(eligibilityDtls -> {
            HSSFRow dataRow = sheet.createRow(i);
            dataRow.createCell(0).setCellValue(eligibilityDtls.getName());
            dataRow.createCell(1).setCellValue(eligibilityDtls.getMobileNo());
            dataRow.createCell(2).setCellValue(eligibilityDtls.getGender());
            dataRow.createCell(3).setCellValue(eligibilityDtls.getSsn());
        });
        ServletOutputStream servletOutputStream = null;
        try {
            servletOutputStream = response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        workbook.write(servletOutputStream);
    }

    @Override
    public void generatePDF(HttpServletResponse response) {
        List<EligibilityDtls> eligibilityDtlsList = eligibilityDtlsRepo.findAll();
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
        Paragraph p = new Paragraph("Search Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.white);
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Phno", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("SSN", font));
        table.addCell(cell);

        for (EligibilityDtls eligibilityDtls : eligibilityDtlsList) {
            table.addCell(eligibilityDtls.getName());
            table.addCell(eligibilityDtls.getEmail());
            table.addCell(String.valueOf(eligibilityDtls.getName()));
            table.addCell(eligibilityDtls.getGender());
            table.addCell(eligibilityDtls.getSsn());
        }

        document.add(table);
        document.close();
    }
}
