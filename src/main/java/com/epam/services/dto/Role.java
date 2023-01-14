package com.epam.services.dto;

import java.util.HashMap;
import java.util.Map;

public enum Role {
	ADMIN(1), SUBSCRIBER(2);

	private Role(int id) {
		this.id = id;
	}

	public int getRoleId() {
		return id;
	}

	public static Role valueOf(int id) {
		return BY_ID.get(id);
	}

	private static final Map<Integer, Role> BY_ID = new HashMap<>();

	static {
		for (Role r : values()) {
			BY_ID.put(r.id, r);
		}
	}

	int id;

}
