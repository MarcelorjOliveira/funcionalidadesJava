package br.com.erudio.integrationtests.testcontainers;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/*
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
	
	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8");
		
		private static void startContainers() {
			Startables.deepStart(Stream.of(mysql)).join();
		}
		
		static Map<String, String> createConnectionConfiguration () {
			return Map.of(
					"spring.datasource.url", mysql.getJdbcUrl(),
					"spring.datasource.username", mysql.getUsername(),
					"spring.datasource.password", mysql.getPassword()
			);
		}

		@SuppressWarnings({"unchecked", "rawtypes"})
		@Override
		public void initialize(ConfigurableApplicationContext applicationContext) {
			startContainers();
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			MapPropertySource testContainers = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());
			environment.getPropertySources().addFirst(testContainers);
		}
	}
}
*/

@Testcontainers
@DirtiesContext
public class AbstractIntegrationTest {
	 
	@Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8");
 
    @DynamicPropertySource
    static void registerMySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
}