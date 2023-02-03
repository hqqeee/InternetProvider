package com.epam.dataaccess.dao;


import java.math.BigDecimal;
import java.util.List;

import com.epam.dataaccess.entity.User;
import com.epam.exception.dao.DAOException;


/**
 * User DAO interface. Has methods needed by services to access persistence layer.
 * @author ruslan
 *
 */

public interface UserDAO extends DAO<User>{
	
	/**
	 * This method returns salt of the user by login.
	 * @param login login of the user.
	 * @return salt(neede for hashing)
	 * @throws DAOException is thrown when SQLException occurs.
	 */
    public String getSalt(String login) throws DAOException;
    
    /**
     * Return user by login and Password if exists.
     * @param login login of the user.
     * @param password password(hashed) of the user.
     * @return User entity.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public User getUser(String login, String password) throws DAOException;
    
    /**
     * This method sets blocked status for the user.
     * @param blocked new blocked status(true - blocked, false - unbloced)
     * @param id id of the user.
     * @return 0 if status not changed, 1 if status changed.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public int changeBlocked(boolean blocked, int id) throws DAOException;
    
    /**
     * This method add record to user has tariff table.
     * @param userId id of the user.
     * @param tariffId if of the traiff.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public void addTariffToUser(int userId, int tariffId) throws DAOException;
    
    /**
     * This method removes record from user has tariff table.
     * @param userId if of the user.
     * @param tariffId if of the tariff.
     * @return 0 if record wasn't deleted, 1 if deleted
     * @throws DAOException is thrown when SQLException occurs.
     */
    public int removeTariffFromUser(int userId, int tariffId) throws DAOException;
    
    /**
     * This method return balance of the user.
     * @param userId id of the user.
     * @return balance of the user.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public BigDecimal getUserBalance(int userId) throws DAOException;
    
    /**
     * This method return list of the all Users.
     * @return all users with role subscriber.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public List<User> getAllSubscriber() throws DAOException;
    
    /**
     * This method return list of the all users with role Subscriber and status unblocked.
     * @return all users with role Subscriber and status unblocked.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public List<User> getAllUnblockedSubscriber() throws DAOException;
    
    /**
     * This method return list of the all users with role Subscriber, status unblocked and with tariffs
     * with days until next payment eq 0.
     * @return user that needs to be charged.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public List<User> getSubscriberForCharging() throws DAOException;
    
    /**
     * This method return filtered list of subscriber for view with pagination.
     * @param searchField part of login to filter.
     * @param offset offset of the records.
     * @param entriesPerPage number of records to get.
     * @return filtered list of users.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public List<User> getSubscriberForView(String searchField, int offset,
			int entriesPerPage) throws DAOException;
    
    /**
     * This method return number of the user filtered by part of login.
     * @param searchField part of login to filter.
     * @return number of subscriber which login contains "login part"
     * @throws DAOException is thrown when SQLException occurs.
     */
    public int getSubscriberNumber(String searchField) throws DAOException;
    
    /**
     * This method update User record in the persistence layer with new password and salt. 
     * @param userId id of the user.
     * @param newPassword new password(hashed)
     * @param salt salt(needed to hash).
     * @return 1 if password changed.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public int changePassword(int userId, String newPassword, String salt) throws DAOException;
    
    /**
     * This method update User record in the persistence layer with new password and salt. 
     * @param email email of the user to change password.
     * @param newPassword new password(hashed)
     * @param salt salt(needed to hash).
     * @return 1 if password changed.
     * @throws DAOException is thrown when SQLException occurs.
     */
    public int changePassword(String email, String newPassword, String salt) throws DAOException;
    
    /**
     * Return user blocked status.
     * @param userId id of the user which status will be returned.
     * @return true if user blocked, false if unblocked.
     * @throws DAOException is thrown when SQLException occurs.
     */
	public boolean getUserStatus(int userId) throws DAOException;
	
	/**
	 * Return login of the user by its email
	 * @param email email of the user.
	 * @return login of the user.
	 * @throws DAOException is thrown when SQLException occurs.
	 */
	public String getLoginByEmail(String email) throws DAOException;;
}
