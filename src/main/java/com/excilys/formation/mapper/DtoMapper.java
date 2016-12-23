package com.excilys.formation.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

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
@Component
public class DtoMapper {
    @Autowired
    private MessageSource messageSource;
    /**
     * Converts a ComputerDto to a Computer.
     * @param pComputerDto the ComputerDto to convert
     * @return a Computer
     */
    public Computer toComputer(ComputerDto pComputerDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(messageSource.getMessage("util.date.format", null, LocaleContextHolder.getLocale()));
        Computer computer = null;
        if (pComputerDto != null) {
            Company company = new Company.CompanyBuilder(pComputerDto.getCompanyName()).id(pComputerDto.getCompanyId())
                    .build();
            ComputerBuilder builder = new Computer.ComputerBuilder(pComputerDto.getName()).id(pComputerDto.getId())
                    .company(company);
            if (pComputerDto.getIntroduced() != null && !pComputerDto.getIntroduced().isEmpty()) {
                builder.dateIntro(LocalDate.parse(pComputerDto.getIntroduced(), formatter));
            }
            if (pComputerDto.getDiscontinued() != null && !pComputerDto.getDiscontinued().isEmpty()) {
                builder.dateDisc(LocalDate.parse(pComputerDto.getDiscontinued(), formatter)).build();
            }
            computer = builder.build();
        }
        return computer;
    }

    /**
     * Converts a List of ComputerDtos to a List of Computer.
     * @param pListComputerDto the List of ComputerDto to convert
     * @return a List of Computers
     */
    public List<Computer> toComputerList(List<ComputerDto> pListComputerDto) {
        if (pListComputerDto != null) {
            List<Computer> computers = new ArrayList<>();
            pListComputerDto.forEach(computer -> computers.add(toComputer(computer)));
            return computers;
        } else {
            return null;
        }
    }

    /**
     * Converts a Computer to a ComputerDto.
     * @param pComputer the Computer to convert
     * @return a ComputerDto
     */
    public ComputerDto fromComputer(Computer pComputer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(messageSource.getMessage("util.date.format", null, LocaleContextHolder.getLocale()));
        ComputerDto computerDto = null;
        if (pComputer != null) {
            computerDto = new ComputerDto();
            computerDto.setId(pComputer.getId());
            computerDto.setName(pComputer.getName());
            LocalDate dateIntro = pComputer.getIntroduced();
            if (dateIntro != null) {
                computerDto.setIntroduced(dateIntro.format(formatter));
            }
            LocalDate dateDisc = pComputer.getDiscontinued();
            if (dateDisc != null) {
                computerDto.setDiscontinued(dateDisc.format(formatter));
            }
            Company company = pComputer.getCompany();
            computerDto.setCompanyId(company.getId());
            computerDto.setCompanyName(company.getName());
        }
        return computerDto;
    }

    /**
     * Converts a List of Computers to a List of ComputerDto.
     * @param pComputers the List of Computers to convert
     * @return a List of ComputerDto
     */
    public List<ComputerDto> fromComputerList(List<Computer> pComputers) {
        if (pComputers != null) {
            List<ComputerDto> computersDto = new ArrayList<>();
            pComputers.forEach(computer -> computersDto.add(fromComputer(computer)));
            return computersDto;
        } else {
            return null;
        }
    }

    /**
     * Converts a CompanyDto to a Company.
     * @param pCompanyDto the CompanyDto to convert
     * @return a Company
     */
    public static Company toCompany(CompanyDto pCompanyDto) {
        return new Company.CompanyBuilder(pCompanyDto.getName()).id(pCompanyDto.getId()).build();
    }

    /**
     * Converts a List of CompanyDto to a List of Company.
     * @param pListCompanyDto the List of CompanyDto to convert
     * @return a List of Company
     */
    public static List<Company> toCompanyList(List<CompanyDto> pListCompanyDto) {
        if (pListCompanyDto != null) {
            List<Company> companies = new ArrayList<>();
            pListCompanyDto.forEach(company -> companies.add(toCompany(company)));
            return companies;
        } else {
            return null;
        }
    }

    /**
     * Converts a Company to a CompanyDto.
     * @param pCompany the Company to convert
     * @return a CompanyDto
     */
    public static CompanyDto fromCompany(Company pCompany) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(pCompany.getId());
        companyDto.setName(pCompany.getName());
        return companyDto;
    }

    /**
     * Converts a List of Company to a List of CompanyDto.
     * @param pListCompanies the List of Company to convert
     * @return a List of CompanyDto
     */
    public static List<CompanyDto> fromCompanyList(List<Company> pListCompanies) {
        if (pListCompanies != null) {
            List<CompanyDto> companiesDto = new ArrayList<>();
            pListCompanies.forEach(company -> companiesDto.add(fromCompany(company)));
            return companiesDto;
        } else {
            return null;
        }
    }
}