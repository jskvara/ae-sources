package entity;

import com.google.appengine.api.datastore.Key;
import java.util.ArrayList;
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

	private List<Key> contactInfoKeys = new ArrayList<Key>();

	public Employee() {
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public List<Key> getContactInfoKeys() {
		return contactInfoKeys;
	}

	public void setContactInfoKeys(List<Key> contactInfoKeys) {
		this.contactInfoKeys = contactInfoKeys;
	}
}

