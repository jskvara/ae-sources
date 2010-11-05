package entity;

import com.google.appengine.api.datastore.Key;
import java.util.LinkedList;
import java.util.List;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Employee {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private List<ContactInfo> contactInfos = new LinkedList();

	public Employee() {
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<ContactInfo> getContactInfos() {
		return contactInfos;
	}

	public void addContactInfo(ContactInfo contactInfo) {
		this.contactInfos.add(contactInfo);
	}
}

