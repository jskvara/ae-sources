package dao;

import com.google.appengine.api.datastore.Key;
import entity.ContactInfo;
import entity.Employee;
import javax.jdo.PersistenceManager;

public class EmployeeDAO {
	private PersistenceManager pm = PMF.get();

	public ContactInfo getContactInfo(Key key) {
		Employee e = pm.getObjectById(Employee.class, key);
		ContactInfo ci = e.getContactInfo();

		return ci;
	}
	
	public void add() {
		ContactInfo ci = new ContactInfo();
		Employee e = new Employee();
		e.setContactInfo(ci);

		try {
			pm.makePersistent(e);
		} finally {
			pm.close();
		}
	}
}

