package com.epam.services.dto;

import java.math.BigDecimal;
import java.util.Objects;
/**
 * UserDTO class. Contains fields required by view.
 * 
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class UserDTO {
	private int id;
	private boolean blocked;
	private String login;
	private BigDecimal balance;
	private String firstName;
	private String lastName;
	private String email;
	private String city;
	private String address;
	private Role role;

	public UserDTO() {
	}

	

	public UserDTO(int id, boolean blocked, String login, BigDecimal balance, String firstName, String lastName,
			String email, String city, String address, Role role) {
		this.id = id;
		this.blocked = blocked;
		this.login = login;
		this.balance = balance;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.city = city;
		this.address = address;
		this.role = role;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	

	public BigDecimal getBalance() {
		return balance;
	}



	public void setBalance(BigDecimal balance) {
		this.balance = balance;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", blocked=" + blocked + ", login=" + login + ", balance=" + balance
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", city=" + city
				+ ", address=" + address + ", role=" + role + "]";
	}



	@Override
	public int hashCode() {
		return Objects.hash(address, balance, blocked, city, email, firstName, id, lastName, login, role);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(address, other.address) && Objects.equals(balance, other.balance)
				&& blocked == other.blocked && Objects.equals(city, other.city) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName) && Objects.equals(login, other.login) && role == other.role;
	}

	
	
	
}
