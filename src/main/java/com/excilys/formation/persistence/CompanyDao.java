package com.excilys.formation.persistence;

import com.excilys.formation.entity.Company;
import com.excilys.formation.exception.PersistenceException;

public interface CompanyDao extends BaseDao<Company> {
    /**
     * Get a company by its id.
     * @param pId the company's Id
     * @return a Company
     * @throws PersistenceException in case of problems while getting the company
     */
    Company getById(int pId) throws PersistenceException;
}