package com.excilys.formation.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.mapper.JdbcMapper;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.CompanyDao;
import com.zaxxer.hikari.HikariDataSource;
import ch.qos.logback.classic.Logger;

/**
 * DAO class for companies.
 * @author kfuster
 *
 */
@Repository
public class CompanyDaoJdbc implements CompanyDao {
    private HikariDataSource dataSource;
    private static final Logger LOGGER = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(CompanyDaoJdbc.class);
    private static final String SELECT_ALL = "SELECT * FROM company ORDER BY company.name";
    private static final String SELECT_BY_ID = "SELECT * FROM company WHERE id=?";
    private static final String SELECT_PAGE = "SELECT * FROM company ";
    private static final String COUNT_ALL = "SELECT COUNT(*) as total FROM company";
    private static final String DELETE_COMPANY = "DELETE FROM company WHERE id=?";

    public CompanyDaoJdbc() {
    };

    public void setDataSource(HikariDataSource pDataSource) {
        dataSource = pDataSource;
    }

    @Override
    public Company getById(long pId) throws PersistenceException {
        Company company = null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);) {
            preparedStatement.setLong(1, pId);
            ResultSet resultSet = preparedStatement.executeQuery();
            company = JdbcMapper.mapResultToCompany(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Error in CompanyDao : getById : ", e);
            throw new PersistenceException("Problème lors de la récupération de la compagnie");
        }
        return company;
    }

    @Override
    public void delete(Connection pConnection, long pID) throws PersistenceException {
        try {
            try (PreparedStatement preparedStatementCompany = pConnection.prepareStatement(DELETE_COMPANY)) {
                preparedStatementCompany.setLong(1, pID);
            } catch (SQLException e) {
                pConnection.rollback();
                LOGGER.error("Error in CompanyDao : delete : ", e);
                throw new PersistenceException("Problème lors de la suppression de la compagnie");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in CompanyDao : delete : ", e);
            throw new PersistenceException("Problème lors de la suppression de la compagnie");
        }
    }

    @Override
    public Page<Company> getPage(PageFilter pPageFilter) throws PersistenceException {
        List<Company> allCompanies = new ArrayList<>();
        String queryComputers = SELECT_PAGE;
        queryComputers += " LIMIT ? OFFSET ?";
        Page<Company> pPage = null;
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement preparedStatement = connection.prepareStatement(queryComputers)) {
            preparedStatement.setInt(1, pPageFilter.getElementsByPage());
            preparedStatement.setInt(2, (pPageFilter.getPageNum() - 1) * pPageFilter.getElementsByPage());
            ResultSet resultSet = preparedStatement.executeQuery();
            allCompanies = JdbcMapper.mapResultsToCompanyList(resultSet);
            pPage = new Page<>(pPageFilter.getElementsByPage());
            pPage.setPage(pPageFilter.getPageNum());
            pPage.setElements((allCompanies));
            pPage.setTotalElements(count(connection, ""));
            pPageFilter.setNbPage(pPage.getTotalPages());
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error("Error in CompanyDao : getPage : ", e);
            throw new PersistenceException("Problème lors de la récupération de la page de compagnies");
        }
        return pPage;
    }

    @Override
    public List<Company> getAll() throws PersistenceException {
        List<Company> allCompanies = new ArrayList<>();
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
            allCompanies = JdbcMapper.mapResultsToCompanyList(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Error in CompanyDao : getAll : ", e);
            throw new PersistenceException("Problème lors de la récupération de la page de compagnies");
        }
        return allCompanies;
    }

    /**
     * Count the total number of companies.
     * @param pConnection the Connection to use
     * @param pFilter an optional filter string
     * @return the number of companies in the DB
     */
    private int count(Connection pConnection, String pFilter) {
        String query = COUNT_ALL;
        if (pFilter != null && !pFilter.isEmpty()) {
            query += pFilter;
        }
        int total = 0;
        try (Statement statement = pConnection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
            if (resultSet.next()) {
                total = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            LOGGER.error("Error in CompanyDao : count : ", e);
        }
        return total;
    }
}