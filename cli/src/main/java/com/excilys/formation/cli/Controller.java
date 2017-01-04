package com.excilys.formation.cli;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.mapper.PageMapper;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;
import ch.qos.logback.classic.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@Component
public class Controller {
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(Controller.class);
    private static Client client = ClientBuilder.newClient();
    private static final String BASE_URL = "http://localhost:8080";
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;
    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private PageMapper pageMapper;

    /**
     * Get a Page<Company> from the service and converts it to Page<CompanyDto>.
     * @param pPageFilter the PageFilter containing the parameters of the page
     * @return a Page<CompanyDto>
     */
    public Page<CompanyDto> getPageCompany(PageFilter pPageFilter) {
        if (pPageFilter != null) {
            return PageMapper.fromCompanyToCompanyDto(companyService.getPage(pPageFilter));
        }
        return null;
    }

    /**
     * Asks the service to delete a Company.
     * @param pId the id of the Company to delete
     */
    public void deleteCompany(long pId) {
        try {
            companyService.delete(pId);
        } catch (ServiceException e) {
            LOGGER.info(e.getMessage());
        }
    }

    /**
     * Asks the service to create a Computer. The given ComputerDto will be
     * converted to a Computer then send to the service.
     * @param pComputerDto the ComputerDto containing the informations of the
     *            Computer.
     * @return a ComputerDto
     */
    public ComputerDto createComputer(ComputerDto pComputerDto) {
        WebTarget target = client.target(BASE_URL).path("computer");
        return target.request().post(Entity.entity(pComputerDto, MediaType.APPLICATION_JSON)).readEntity(ComputerDto.class);
    }

    /**
     * Asks the service to update a Computer. The given ComputerDto will be
     * converted to a Computer then send to the service.
     * @param pComputerDto ComputerDto containing the informations
     */
    public void updateComputer(ComputerDto pComputerDto) {
        WebTarget target = client.target(BASE_URL).path("computer");
        target.request().put(Entity.entity(pComputerDto, MediaType.APPLICATION_JSON));
    }

    /**
     * Get a Page<Computer> from the service and converts it to
     * Page<ComputerDto>.
     * @param pPageFilter the PageFilter containing the parameters of the page
     * @return a Page<Computer>
     */
    public Page<ComputerDto> getPageComputer(PageFilter pPageFilter) {
        if (pPageFilter != null) {
            WebTarget target = client.target(BASE_URL).path("computer/" + pPageFilter.getElementsByPage() + "/" + pPageFilter.getPageNum());
            return pageMapper.fromComputerToComputerDto(target.request().get().readEntity(new GenericType<Page<Computer>>(){}));
        }
        return null;
    }

    /**
     * Get a Computer from the service and converts it to ComputerDto.
     * @param pId the id of the Computer to get
     * @return a ComputerDto
     */
    public ComputerDto getComputerById(long pId) {
        WebTarget target = client.target(BASE_URL).path("computer/" + pId);
        return target.request().get().readEntity(ComputerDto.class);
    }

    /**
     * Asks the service to delete a Computer.
     * @param pId the id of the Computer to delete
     */
    public void deleteComputer(long pId) {
        WebTarget target = client.target(BASE_URL).path("computer/" + pId);
        target.request().delete();
    }
}