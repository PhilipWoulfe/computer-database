package com.excilys.formation.service.implementation;

import java.util.List;
import org.slf4j.LoggerFactory;
import com.excilys.formation.exception.PersistenceException;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.util.PageFilter;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.persistence.ComputerDao;
import com.excilys.formation.persistence.jdbc.ComputerDaoJdbc;
import com.excilys.formation.service.ComputerService;
import ch.qos.logback.classic.Logger;

/**
 * Service class for Computers.
 * @author kfuster
 *
 */
public class ComputerServiceImpl implements ComputerService {
    final Logger logger = (Logger) LoggerFactory.getLogger(ComputerServiceImpl.class);
    private ComputerDao computerDao;
    private static ComputerServiceImpl computerService;
    /**
     * Constructor for ComputerServiceImpl. Initializes computerDao.
     */
    private ComputerServiceImpl() {
        computerDao = ComputerDaoJdbc.getInstance();
    }
    /**
     * Getter for the ComputerServiceImpl instance. Initializes it if null.
     * @return the instance of ComputerServiceImpl
     */
    public static ComputerServiceImpl getInstance() {
        if (computerService == null) {
            computerService = new ComputerServiceImpl();
        }
        return computerService;
    }
    @Override
    public Computer create(Computer pComputer) {
        try {
            return computerDao.create(pComputer);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
    @Override
    public boolean delete(long pId) {
        try {
            return computerDao.delete(pId);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return false;
    }
    public boolean deleteList(List<Integer> ids) {
        for (Integer id : ids) {
            try {
                computerDao.delete(id);
            } catch (PersistenceException e) {
                logger.info(e.getMessage());
                return false;
            }
        }
        return true;
    }
    @Override
    public Computer getById(long pId) {
        try {
            return computerDao.getById(pId);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
    @Override
    public Page<Computer> getPage(PageFilter pViewDto) {
        try {
            return computerDao.getPage(pViewDto);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
    @Override
    public void update(Computer pComputer) {
        try {
            computerDao.update(pComputer);
        } catch (PersistenceException e) {
            logger.info(e.getMessage());
        }
    }
}
