package com.csp.mvn.hibDemo;

import java.nio.channels.SeekableByteChannel;
import java.util.List;

import javax.persistence.OrderBy;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

/**
 * Hello world!
 *
 */
public class App {

	private static SessionFactory factory;

	private static Session session;

	
	List<Employee> list;
	private static Transaction tx;

	public static void main(String[] args) {
		System.out.println("Hello World!");

		try {
			factory = new Configuration().configure().buildSessionFactory();
			System.out.println(factory);
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		session = factory.openSession();
		tx = session.beginTransaction();
		App ap = new App();

		
		/*
		for (int i = 0; i < 10; i++) {
			Integer empID1 = ap.addEmployee("Zara", "Ali", 1000);
			Integer empID2 = ap.addEmployee("Daisy", "Das", 5000);
			Integer empID3 = ap.addEmployee("John", "Paul", 10000);
		}

*/
		// ap.getEmployees();

		// ap.getEmployeeBySalry(5000);

		// ap.deleteEmpById(3);

		//ap.limitResults();
		
		ap.getEmployeeByCriteria();
		ap.getEmployeeByCriteria();
		ap.getEmployeeByCriteria();
		tx.commit();
		session.close();
	}

	public void getEmployees() {
		Query query = session.createQuery("From Employee");
		List<Employee> list = query.list();
		System.out.println(list);
	}

	public void getEmployeeBySalry(int salary) {
		Query q = session.createQuery("from Employee E where E.salary=" + salary);
		List<Employee> ls = q.list();
		System.out.println(ls);
	}

	public void deleteEmpById(int id) {
		Query q = session.createQuery("DELETE from Employee E where E.id= :id");
		q.setParameter("id", id);
		int rows = q.executeUpdate();
		System.out.println("Rows Affected :" + rows);
	}

	public Integer addEmployee(String fname, String lname, int salary) {
		Integer employeeID = null;

		try {
			Employee employee = new Employee();
			employee.setFirstName(fname);
			employee.setLastName(lname);
			employee.setSalary(salary);
			employeeID = (Integer) session.save(employee);

		} catch (HibernateException e) {
		} finally {

		}
		return employeeID;
	}

	public void limitResults() {
		Query q = session.createQuery("from Employee");
		q.setFirstResult(6);
		q.setMaxResults(26);
		List<Employee> result = q.list();
		System.out.println(result);
	}
	
	
	
	
	public void getEmployeeByCriteria() {
		
		NativeQuery<Employee> cr = session.createSQLQuery("SELECT *  from Employee");
		
		List<Employee> list = cr.list();
		
		System.out.println(list);
	}

}
