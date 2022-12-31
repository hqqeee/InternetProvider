package com.epam.dataaccess.dao;


import java.util.List;

import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;




public interface UserDAO extends DAO<User>{
    public String getSalt(String login) throws DAOException;
    public User getUser(String login, String password) throws DAOException;
    public int changeBlocked(boolean blocked, int id) throws DAOException;
    public int addTariffToUser(int userId, int tariffId) throws DAOException;
    public int removeTariffFromUser(int userId, int tariffId) throws DAOException;
    public List<User> getAllSubscriber() throws DAOException;
    public List<User> getSubscriberForView(String searchField, int offset,
			int entriesPerPage) throws DAOException;
    public int getSubscriberNumber(String searchField) throws DAOException;
    public int changePassword(int userId, String newPassword, String salt) throws DAOException;
}
