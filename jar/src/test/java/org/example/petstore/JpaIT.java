package org.example.petstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest(classes = {PetstoreTestConfig.class})
public class JpaIT {

	@PersistenceContext
	private EntityManager em;

	@Test
	public void testSchema() {
       //no-op

	}

}
