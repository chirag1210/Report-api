package com.chirag.reportapi.responses;

import lombok.Data;

@Data
public class SearchResponse {

    private String name;
    private Long mobileNo;
    private String email;
    private String gender;
    private String ssn;
    private String planName;
    private String planStatus;
}
