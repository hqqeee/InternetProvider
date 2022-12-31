package com.epam.services;

import java.util.List;

import com.epam.dataaccess.entity.Service;
import com.epam.exception.services.ServiceServiceException;

public interface ServiceService {
	List<Service> getAllServices() throws ServiceServiceException;
}
