package com.excilys.formation.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.Computer.ComputerBuilder;

/**
 * Mapper class for DTOs.
 * @author kfuster
 *
 */
public class DtoMapper {
    /**
     * Converts a Computer to a ComputerDto.
     * @param pComputerDto
     * @return a Computer
     */
    public static Computer toComputer(ComputerDto pComputerDto) {
        Computer computer = null;
        if(pComputerDto != null) {
            Company company = new Company.CompanyBuilder(pComputerDto.getCompanyName()).id(pComputerDto.getCompanyId()).build();
            ComputerBuilder builder = new Computer.ComputerBuilder(pComputerDto.getName()).id(pComputerDto.getId()).company(company);
            if(pComputerDto.getIntroduced() != null && !pComputerDto.getIntroduced().isEmpty()) {
                builder.dateIntro(LocalDate.parse(pComputerDto.getIntroduced()));
            }
            if(pComputerDto.getDiscontinued() != null && !pComputerDto.getDiscontinued().isEmpty()) {
                builder.dateDisc(LocalDate.parse(pComputerDto.getDiscontinued())).build();
            }
            computer = builder.build();
        }
        return computer;
    }
    /**
     * Converts a List of Computers to a List of ComputerDtos.
     * @param pListComputerDto
     * @return a List of Computers
     */
    public static List<Computer> toComputerList(List<ComputerDto> pListComputerDto) {
        List<Computer> computers = null;
        if (pListComputerDto != null) {
            computers = new ArrayList<>();
            for (ComputerDto computer : pListComputerDto) {
                computers.add(toComputer(computer));
            }
        }
        return computers;
    }
    /**
     * Converts a Computer to a ComputerDto.
     * @param pComputer
     * @return a ComputerDto
     */
    public static ComputerDto fromComputer(Computer pComputer) {
        ComputerDto computerDto = null;
        if(pComputer != null) {
            computerDto = new ComputerDto();
            computerDto.setId(pComputer.getId());
            computerDto.setName(pComputer.getName());
            LocalDate dateIntro = pComputer.getIntroduced();
            if (dateIntro != null) {
                computerDto.setIntroduced(dateIntro.toString());
            }
            LocalDate dateDisc = pComputer.getDiscontinued();
            if (dateDisc != null) {
                computerDto.setDiscontinued(dateDisc.toString());
            }
            Company company = pComputer.getCompany();
            computerDto.setCompanyId(company.getId());
            computerDto.setCompanyName(company.getName());
        }
        return computerDto;
    }
    /**
     * Converts a List of Computers to a List of ComputerDto.
     * @param pComputers
     * @return a List of ComputerDto
     */
    public static List<ComputerDto> fromComputerList(List<Computer> pComputers) {
        List<ComputerDto> computersDto = null;
        if (pComputers != null) {
            computersDto = new ArrayList<>();
            for (Computer computer : pComputers) {
                computersDto.add(fromComputer(computer));
            }
        }
        return computersDto;
    }
    /**
     * Converts a CompanyDto to a Company.
     * @param pCompanyDto
     * @return a Company
     */
    public static Company toCompany(CompanyDto pCompanyDto) {
        return new Company.CompanyBuilder(pCompanyDto.getName()).id(pCompanyDto.getId()).build();       
    }
    /**
     * Converts a List of CompanyDto to a List of Company.
     * @param pListCompanyDto
     * @return a List of Company
     */
    public static List<Company> toCompanyList(List<CompanyDto> pListCompanyDto) {
        List<Company> companies = null;
        if (pListCompanyDto != null) {
            companies = new ArrayList<>();
            for (CompanyDto company : pListCompanyDto) {
                companies.add(toCompany(company));
            }
        }
        return companies;
    }
    /**
     * Converts a Company to a CompanyDto.
     * @param pCompany
     * @return
     */
    public static CompanyDto fromCompany(Company pCompany) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(pCompany.getId());
        companyDto.setName(pCompany.getName());
        return companyDto;
    }
    /**
     * Converts a List of Company to a List of CompanyDto.
     * @param pListCompanies
     * @return
     */
    public static List<CompanyDto> fromCompanyList(List<Company> pListCompanies) {
        List<CompanyDto> companiesDto = null;
        if (pListCompanies != null) {
            companiesDto = new ArrayList<>();
            for (Company company : pListCompanies) {
                companiesDto.add(fromCompany(company));
            }
        }
        return companiesDto;
    }
}