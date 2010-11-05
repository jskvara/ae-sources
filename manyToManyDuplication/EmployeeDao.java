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
		for (Key ciKey : e.getContactInfoKeys()) {
			ContactInfo ci = pm.getObjectById(ContactInfo.class, ciKey);
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

			List<Key> ciKeys = e.getContactInfoKeys();
			ciKeys.add(ci.getKey());
			ciKeys.add(ci2.getKey());
			pm.makePersistent(e);

			List<Key> eKeys = ci.getEmployeeKeys();
			eKeys.add(e.getKey());
			ci.setEmployeeKeys(eKeys);
			pm.makePersistent(ci);

			List<Key> e2Keys = ci2.getEmployeeKeys();
			e2Keys.add(e.getKey());
			ci2.setEmployeeKeys(e2Keys);
			pm.makePersistent(ci2);
		} finally {
			pm.close();
		}
	}
}

