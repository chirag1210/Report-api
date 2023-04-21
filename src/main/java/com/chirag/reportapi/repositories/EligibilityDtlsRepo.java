package com.chirag.reportapi.repositories;

import com.chirag.reportapi.entities.EligibilityDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EligibilityDtlsRepo extends JpaRepository<EligibilityDtls, Integer> {
    @Query("select distinct planName from EligibilityDtls")
    List<String> findDistinctPlanName();
    @Query("select distinct planStatus from EligibilityDtls")
    List<String> findDistinctPlanStatus();
}
