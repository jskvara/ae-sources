package dao;

import com.google.appengine.api.datastore.Key;
import entity.ContactInfo;
import entity.Employee;
import java.util.List;
import javax.jdo.PersistenceManager;

public class EmployeeDAO {
	private PersistenceManager pm = PMF.get();

	public List<ContactInfo> getContactInfos(Key key) {
		Employee e = pm.getObjectById(Employee.class, key);
		List<ContactInfo> cis = e.getContactInfos();

		return cis;
	}

	public void add() {
		Employee e = new Employee();
		e.addContactInfo(new ContactInfo());
		e.addContactInfo(new ContactInfo());

		try {
			pm.makePersistent(e);
		} finally {
			pm.close();
		}
	}
}

