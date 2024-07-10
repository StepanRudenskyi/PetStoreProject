package org.example.petstore;

import org.example.petstore.model.Account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaApp {

	
	public static void main(String[] args) {
	  new JpaApp().demo1();	
	}

	private void demo1() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("petstore");//SessionFactory
		
		
		EntityManager em = emf.createEntityManager(); //Session
		
		
		Account acc = new Account();
		acc.setFirstName("John");
		acc.setLastName("Doe");
		acc.setEmail("user@usa.net");
		
		em.getTransaction().begin();
		em.persist(acc);
		em.getTransaction().commit();
		
		
		
	}

}
