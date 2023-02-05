package com.epam.dataaccess.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * User entity class. Matches table 'user' in the persistence layer.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String password;
	private String salt;
	private String login;
	private int roleId;
	private boolean blocked;
	private String email;
	private String firstName;
	private String lastName;
	private String city;
	private String address;
	private BigDecimal balance;

	public User() {
	}

	public User(int id, String password, String salt, String login, int roleId, boolean blocked, String email,
			String firstName, String lastName, String city, String address, BigDecimal balance) {
		this.id = id;
		this.password = password;
		this.salt = salt;
		this.login = login;
		this.roleId = roleId;
		this.blocked = blocked;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.address = address;
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", roleId=" + roleId + ", blocked=" + blocked + ", email="
				+ email + ", firstName=" + firstName + ", lastName=" + lastName + ", city=" + city + ", address="
				+ address + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, balance, blocked, city, email, firstName, id, lastName, login, password, roleId,
				salt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(address, other.address) && Objects.equals(balance, other.balance)
				&& blocked == other.blocked && Objects.equals(city, other.city) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(login, other.login)
				&& Objects.equals(password, other.password) && roleId == other.roleId
				&& Objects.equals(salt, other.salt);
	}

}
