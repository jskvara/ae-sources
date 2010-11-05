package dao;

import com.google.appengine.api.datastore.Key;
import entity.ContactInfo;
import entity.Employee;
import javax.jdo.PersistenceManager;

public class EmployeeDAO {
	private PersistenceManager pm = PMF.get();

	public ContactInfo getContactInfo(Key key) {
		Employee e = pm.getObjectById(Employee.class, key);
		ContactInfo ci = pm.getObjectById(ContactInfo.class, e.getContactInfoKey());

		return ci;
	}

	public void add() {
		ContactInfo ci = new ContactInfo();
		try {
			pm.makePersistent(ci);

			Employee e = new Employee();
			e.setContactInfoKey(ci.getKey());

			pm.makePersistent(e);
		} finally {
			pm.close();
		}
	}
}

