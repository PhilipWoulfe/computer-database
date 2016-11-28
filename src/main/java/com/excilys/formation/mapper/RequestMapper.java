package com.excilys.formation.mapper;

import javax.servlet.http.HttpServletRequest;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.model.util.PageFilter;

public class RequestMapper {
    /**
     * Fills a computerDto from the parameters of a request
     * @param pRequest the HttpServletRequest containing the parameters
     * @return a ComputerDto
     */
    public static ComputerDto toComputerDto(HttpServletRequest pRequest) {
        ComputerDto computerDto = new ComputerDto();
        String id = "";
        if (pRequest.getParameter("computerId") != null) {
            id = pRequest.getParameter("computerId").trim();
        }
        String name = pRequest.getParameter("computerName").trim();
        String introduced = pRequest.getParameter("introduced").trim();
        String discontinued = pRequest.getParameter("discontinued").trim();
        String companyId = pRequest.getParameter("companyId").trim();
        if (!id.isEmpty()) {
            computerDto.setId(Long.parseLong(id));
        }
        if (!name.isEmpty()) {
            computerDto.setName(name);
        }
        if (!introduced.isEmpty()) {
            computerDto.setIntroduced(introduced);
        }
        if (!discontinued.isEmpty()) {
            computerDto.setDiscontinued(discontinued);
        }
        if (!companyId.isEmpty()) {
            long idCompany = Long.parseLong(companyId);
            if (idCompany != 0) {
                computerDto.setCompanyId(idCompany);
            }
        }
        return computerDto;
    }

    public static PageFilter toPageFilter(HttpServletRequest pRequest) {
        PageFilter pageFilter = new PageFilter();
        pageFilter.setElementsByPage(10);
        pageFilter.setPageNum(1);
        if (pRequest.getParameter("page") != null) {
            pageFilter.setPageNum(Integer.parseInt(pRequest.getParameter("page")));
        }
        if (pRequest.getParameter("limit") != null) {
            pageFilter.setElementsByPage(Integer.parseInt(pRequest.getParameter("limit")));
        }
        pRequest.getServletContext().setAttribute("filter", null);
        if (pRequest.getParameter("search") != null) {
            String search = pRequest.getParameter("search").replace("'", "''");
            pageFilter.addCondition("computerName", search);
            pageFilter.addCondition("companyName", search);
            pRequest.getServletContext().setAttribute("filter", search);
        }
        pRequest.getServletContext().setAttribute("column", null);
        if (pRequest.getParameter("column") != null) {
            String column = pRequest.getParameter("column").replace("'", "''");
            pRequest.getServletContext().setAttribute("column", column);
            if ("computerName".equals(column)) {
                column = "computer.name";
            }
            if ("companyName".equals(column)) {
                column = "company.name";
            }
            pageFilter.addCondition("column", column);
        }
        if (pRequest.getParameter("order") != null) {
            String order = pRequest.getParameter("order").replace("'", "''");
            pageFilter.addCondition("order", order);
            pRequest.getServletContext().setAttribute("order", order);
        }
        return pageFilter;
    }
}