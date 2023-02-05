package com.epam.services.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Service enum. If service not specified use ALL.
 * 
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public enum Service {
	ALL(0), TELEPHONE(1), INTERNET(2), CABLE_TV(3), IP_TV(4);

	private int id;

	public int getId() {
		return id;
	}

	public static Service getServiceByString(String service) {
		if (service != null) {
			if (service.equals("TELEPHONE")) {
				return Service.TELEPHONE;
			} else if (service.equals("INTERNET")) {
				return Service.INTERNET;
			} else if (service.equals("CABLE_TV")) {
				return Service.CABLE_TV;
			} else if (service.equals("IP_TV")) {
				return Service.IP_TV;
			}
		}
		return Service.ALL;
	}

	Service(int id) {
		this.id = id;
	}

	public static Service valueOf(int id) {
		return BY_ID.get(id) != null ? BY_ID.get(id) : Service.ALL;
	}

	private static final Map<Integer, Service> BY_ID = new HashMap<>();

	static {
		for (Service s : values()) {
			BY_ID.put(s.id, s);
		}
	}
}
