package com.epam.dataaccess.entity;

import java.io.Serializable;
import java.util.Objects;
/**
 * Role entity class. Matches table 'role' in the persistence layer.
 *
 * @author Hrebenozhko Ruslan
 * @version 1.0
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String description;

	public Role() {
	}

	public Role(int id, String name, String description) {
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
		return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		return Objects.equals(description, other.description) && id == other.id && Objects.equals(name, other.name);
	}
	
	

}
