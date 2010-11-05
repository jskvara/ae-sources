package dao;

import com.google.appengine.api.datastore.Key;
import entity.ContactInfo;
import entity.Employee;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class EmployeeDAO {
	private PersistenceManager pm = PMF.get();

	public List<ContactInfo> getContactInfos(Key key) {
		Employee e = pm.getObjectById(Employee.class, key);
		Query query = pm.newQuery(ContactInfo.class);
		query.setFilter("employeeKey == employeeKeyParam");
		query.declareImports("import com.google.appengine.api.datastore.Key;");
		query.declareParameters("Key employeeKeyParam");
		List<ContactInfo> cis = (List<ContactInfo>) query.execute(e.getKey());

		return cis;
	}

	public void add() {
		ContactInfo ci = new ContactInfo();
		Employee e = new Employee();

		try {
			pm.makePersistent(e);
			ci.setEmployeeKey(e.getKey());
			pm.makePersistent(ci);
		} finally {
			pm.close();
		}
	}
}

