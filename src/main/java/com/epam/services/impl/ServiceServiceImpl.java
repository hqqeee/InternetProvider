package com.epam.services.impl;

import java.util.List;

import com.epam.dataaccess.dao.DAOFactory;
import com.epam.dataaccess.dao.ServiceDAO;
import com.epam.dataaccess.entity.Service;
import com.epam.exception.dao.DAOException;
import com.epam.exception.services.ServiceServiceException;
import com.epam.services.ServiceService;

public class ServiceServiceImpl implements ServiceService{

	private DAOFactory daoFactory;
	
	
	private ServiceServiceImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}


	@Override
	public List<Service> getAllServices() throws ServiceServiceException {
		try {
			ServiceDAO serviceDAO = daoFactory.getServiceDAO();
			return serviceDAO.getAll();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceServiceException("Cannot get services.");
		}
	}

}
