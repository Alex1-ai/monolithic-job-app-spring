package com.chidi.firstjobapp.company;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyService {
    ResponseEntity<List<Company>> getAllCompanies();

    boolean updateCompany(Long id, Company updatedCompany);

    ResponseEntity<Company> createCompany(Company company);

    ResponseEntity<String> deleteCompanyById(Long id);

    Company getCompanyById(Long id);
}
