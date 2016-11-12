package com.excilys.formation.service;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.entity.Company;
import com.excilys.formation.pagination.Page;

/**
 * Interface for CompanyServices
 * @author kfuster
 *
 */
public interface CompanyService extends BaseService<Company>{
    /**
     * Populate a list of Company according to the Page parameters
     * @param pPageCompany the Page containing the parameters and the list
     */
    Page<CompanyDto> getPage(Page<CompanyDto> pPageCompany);
}
