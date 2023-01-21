package com.epam.dataaccess.dao;


import java.math.BigDecimal;
import java.util.List;

import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;




public interface UserDAO extends DAO<User>{
    public String getSalt(String login) throws DAOException;
    public User getUser(String login, String password) throws DAOException;
    public int changeBlocked(boolean blocked, int id) throws DAOException;
    public void addTariffToUser(int userId, int tariffId) throws DAOException;
    public int removeTariffFromUser(int userId, int tariffId) throws DAOException;
    public BigDecimal getUserBalance(int userId) throws DAOException;
    public List<User> getAllSubscriber() throws DAOException;
    public List<User> getAllUnblockedSubscriber() throws DAOException;
    public List<User> getSubscriberForCharging() throws DAOException;
    public List<User> getSubscriberForView(String searchField, int offset,
			int entriesPerPage) throws DAOException;
    public int getSubscriberNumber(String searchField) throws DAOException;
    public int changePassword(int userId, String newPassword, String salt) throws DAOException;
    public int changePassword(String email, String newPassword, String salt) throws DAOException;
	public boolean getUserStatus(int userId) throws DAOException;
	public String getLoginByEmail(String email) throws DAOException;;
}
