package com.epam.dataaccess.entity;

import java.io.Serializable;
/**
 * Service entity class. Matches table 'service' in the persistence layer.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;

	public Service() {
	}

	public Service(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Service [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	
	
	
}
