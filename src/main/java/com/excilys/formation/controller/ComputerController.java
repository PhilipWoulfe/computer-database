package com.excilys.formation.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.excilys.formation.dto.CompanyDto;
import com.excilys.formation.dto.ComputerDto;
import com.excilys.formation.exception.ServiceException;
import com.excilys.formation.mapper.DtoMapper;
import com.excilys.formation.mapper.PageMapper;
import com.excilys.formation.mapper.RequestMapper;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/")
public class ComputerController{
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(ComputerController.class);
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ComputerService computerService;
    @Autowired
    private DtoMapper dtoMapper;
    @Autowired
    private PageMapper pageMapper;

    @RequestMapping(path="/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/dashboard.jsp");
        
        
        PageFilter pageFilter = RequestMapper.toPageFilter(request);
        HttpSession session = request.getSession(false);
        if (request.getAttribute("success") != null) {
            System.out.println("ici");
        }
        model.addObject("deleted", null);
        if (session != null && session.getAttribute("deleted") != null) {
            model.addObject("deleted", session.getAttribute("deleted"));
            session.removeAttribute("deleted");
        }
        Page<ComputerDto> computerPage = pageMapper.fromComputerToComputerDto(computerService.getPage(pageFilter));

        model.addObject("pageComputer", computerPage);

        return model;
    }
    
    @RequestMapping(path="/addComputer", method = RequestMethod.GET)
    public ModelAndView addComputerGet(HttpServletRequest request, HttpServletResponse response) {
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/addComputer.jsp");
        model.addObject("computerDto", new ComputerDto());
        model.addObject("success", null);
        model.addObject("listCompanies", listCompanies);
        return model;
    }
    
    @RequestMapping(path="/addComputer", method = RequestMethod.POST)
    public ModelAndView addComputerPost(@Valid @ModelAttribute("computerDto")ComputerDto pComputerDto, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            
            ModelAndView model = new ModelAndView("redirect:/dashboard");
            try {
                computerService.create(dtoMapper.toComputer(pComputerDto));
                model.addObject("success", true); 
            } catch (ServiceException e) {
                LOGGER.info(e.getMessage());
            }
            return model;
        }
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/addComputer.jsp");
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        model.addObject("listCompanies", listCompanies);
        return model;    
    }
    
    @RequestMapping(path="/deleteComputer", method = RequestMethod.POST)
    public ModelAndView deleteComputer(@RequestParam Map<String, String> parameters) {
        ModelAndView model = new ModelAndView("redirect:/dashboard");
        //We get the id list of computers to delete from the parameters
        //and ask the service to delete id
        List<Long> computersId = RequestMapper.toListIds(parameters.get("selection"));
        if (computersId != null) {
            computerService.deleteList(computersId);
        }
        
        return model;
    }
    
    @RequestMapping(path="editComputer", method = RequestMethod.GET)
    public ModelAndView editComputerGet(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/editComputer.jsp");
        String computerId = null;
        if (request.getParameter("id") != null) {
            computerId = request.getParameter("id");
        } else if (request.getAttribute("id") != null) {
            computerId = (String) request.getAttribute("id");
        }
        if (computerId != null && !computerId.trim().isEmpty()) {
            ComputerDto computerDto = dtoMapper.fromComputer(computerService.getById(Long.parseLong(computerId)));
            List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
            model.addObject("listCompanies", listCompanies);
            model.addObject("computerDto", computerDto);
            return model;
        }
        
        return new ModelAndView("redirect:/dashboard");        
    }
    
    @RequestMapping(path="editComputer", method = RequestMethod.POST)
    public ModelAndView editComputerPost(@Valid @ModelAttribute("computerDto")ComputerDto computerDto, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (!bindingResult.hasErrors()) {
            ModelAndView model = new ModelAndView("redirect:/dashboard");
            try {
                computerService.update(dtoMapper.toComputer(computerDto));
                model.addObject("success", true);
                model.addObject("computerDto", computerDto);
            } catch (ServiceException e) {
                LOGGER.info(e.getMessage());
            }
            return model;
        }
        ModelAndView model = new ModelAndView("/WEB-INF/jsp/editComputer.jsp");
        List<CompanyDto> listCompanies = DtoMapper.fromCompanyList(companyService.getAll());
        model.addObject("listCompanies", listCompanies);
        return model;        
    }
    
    @RequestMapping(path="internationalization.js")
    public ModelAndView strings(HttpServletRequest request) {
        // Retrieve the locale of the User
        Locale locale = RequestContextUtils.getLocale(request);
        // Use the path to your bundle
        ResourceBundle bundle = ResourceBundle.getBundle("messages.messages", locale);
        // Call the string.jsp view
        return new ModelAndView("/WEB-INF/jsp/internationalization.jsp", "keys", bundle.getKeys());
    }
}