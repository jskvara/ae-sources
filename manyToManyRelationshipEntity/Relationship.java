package entity;

import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Relationship {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Key employeeKey;

	@Persistent
	private Key contactInfoKey;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getContactInfoKey() {
		return contactInfoKey;
	}

	public void setContactInfoKey(Key contactInfoKey) {
		this.contactInfoKey = contactInfoKey;
	}

	public Key getEmployeeKey() {
		return employeeKey;
	}

	public void setEmployeeKey(Key employeeKey) {
		this.employeeKey = employeeKey;
	}
}

