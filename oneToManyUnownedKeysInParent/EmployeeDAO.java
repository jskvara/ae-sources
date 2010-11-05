package dao;

import com.google.appengine.api.datastore.Key;
import entity.ContactInfo;
import entity.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;

public class EmployeeDAO {
	private PersistenceManager pm = PMF.get();

	public List<ContactInfo> getContactInfos(Key key) {
		Employee e = pm.getObjectById(Employee.class, key);

		List<ContactInfo> cis = new ArrayList<ContactInfo>();
		for (Key contactInfoKey : e.getContactInfos()) {
			ContactInfo ci = pm.getObjectById(ContactInfo.class, contactInfoKey);
			cis.add(ci);
		}

		return cis;
	}

	public void add() {
		ContactInfo ci = new ContactInfo();
		ContactInfo ci2 = new ContactInfo();
		Employee e = new Employee();

		try {
			pm.makePersistent(ci);
			pm.makePersistent(ci2);

			List cis = e.getContactInfos();
			cis.add(ci.getKey());
			cis.add(ci2.getKey());

			pm.makePersistent(e);
		} finally {
			pm.close();
		}
	}
}

