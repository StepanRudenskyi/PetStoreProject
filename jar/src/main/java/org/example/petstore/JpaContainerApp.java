package org.example.petstore;

import org.example.petstore.model.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootApplication
@Configuration
public class JpaContainerApp {

	public static void main(String[] args) {
		SpringApplication.run(JpaContainerApp.class, args);
	}

	@Component
	public static class DemoBean {

		@PersistenceContext
		EntityManager em;

		@Transactional
		@EventListener(ApplicationReadyEvent.class)
		public void run(ApplicationReadyEvent evt) {

			// em.getTransaction().begin();
			Account acc = new Account();
			acc.setFirstName("John");
			acc.setLastName("Doe");
			acc.setEmail("user@usa.net");
			em.persist(acc);
			// em.getTransaction().commit();

		}

	}
}
