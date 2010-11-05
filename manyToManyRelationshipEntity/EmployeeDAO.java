package dao;

import com.google.appengine.api.datastore.Key;
import entity.ContactInfo;
import entity.Employee;
import entity.Relationship;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class EmployeeDAO {
	private PersistenceManager pm = PMF.get();

	public List<ContactInfo> getContactInfos(Key key) {
		Employee e = pm.getObjectById(Employee.class, key);

		Query query = pm.newQuery(Relationship.class);
		query.setFilter("employeeKey == employeeKeyParam");
		query.declareImports("import com.google.appengine.api.datastore.Key;");
		query.declareParameters("Key employeeKeyParam");
		List<Relationship> relationships = (List<Relationship>) query.execute(e.getKey());

		List<ContactInfo> cis = new ArrayList<ContactInfo>();
		for (Relationship relationship : relationships) {
			ContactInfo ci = pm.getObjectById(ContactInfo.class, relationship.getContactInfoKey());
			cis.add(ci);
		}

		return cis;
	}

	public void add() {
		ContactInfo ci = new ContactInfo();
		ContactInfo ci2 = new ContactInfo();
		Employee e = new Employee();
		Employee e2 = new Employee();
		
		try {
			pm.makePersistent(ci);
			pm.makePersistent(ci2);
			pm.makePersistent(e);
			pm.makePersistent(e2);

			Relationship r = new Relationship();
			r.setContactInfoKey(ci.getKey());
			r.setEmployeeKey(e.getKey());
			Relationship r2 = new Relationship();
			r2.setContactInfoKey(ci2.getKey());
			r2.setEmployeeKey(e.getKey());

			pm.makePersistent(r);
			pm.makePersistent(r2);
		} finally {
			pm.close();
		}
	}
}

