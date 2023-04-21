# report-api : Create Excel and pdf - download

http://localhost:8080/swagger-ui/index.html

Any task analysis 

1. analysis start from the databases
check any dropdown or prepopulated data

2. For dynamic query use : Query by Element

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
		
Example<EligibilityDtls> example = Example.of(eligibilityDtls);
        List<EligibilityDtls> eligibilityDtlsList = eligibilityDtlsRepo.findAll(example);		

3. Dependency for excel 
<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi-ooxml</artifactId>
			<version>5.2.3</version>
		</dependency>
		
Workbook => sheet => row => cell => cellvalue

4. Dependency for pdf
        <dependency>
			<groupId>com.github.librepdf</groupId>
			<artifactId>openpdf</artifactId>
			<version>1.3.30</version>
		</dependency>

		
