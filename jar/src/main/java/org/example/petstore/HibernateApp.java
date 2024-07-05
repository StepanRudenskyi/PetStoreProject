package org.example.petstore;

import org.example.petstore.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateApp {

	// Main driver method
	public static void main(String[] args) {
		new HibernateApp().demo();
	}

	public void demo() {
		// Create Configuration
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		configuration.addAnnotatedClass(Account.class);

		// Create Session Factory and auto-close with try-with-resources.
		try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {

			// Initialize Session Object
			Session session = sessionFactory.openSession();

			Account acc = new Account();
			acc.setFirstName("John");
			acc.setLastName("Doe");
			acc.setEmail("user@usa.net");

			session.beginTransaction();
			session.persist(acc);
			session.getTransaction().commit();
		}
	}
}
