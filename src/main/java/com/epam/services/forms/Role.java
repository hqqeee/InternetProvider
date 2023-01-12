package com.epam.services.forms;

public enum Role {
	ADMIN(1),
	SUBSCRIBER(2);
	
	int id;
	
	private Role(int id){
		this.id = id;
	}
	
	public int getRoleId() {
		return id;
	}
	
}
