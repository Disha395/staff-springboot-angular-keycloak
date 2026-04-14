package com.college.management;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Staff Management System API",
				version = "1.0.0",
				description = "RESTful API for managing college staff members. Provides endpoints for CRUD operations, searching, and filtering staff by department and salary.",
				contact = @Contact(
						name = "College Management Team",
						email = "support@college.com",
						url = "https://college.com"
				),
				license = @License(
						name = "MIT License",
						url = "https://opensource.org/licenses/MIT"
				)
		),
		servers = {
				@Server(
						description = "Local Development Server",
						url = "http://localhost:8081"
				),
				@Server(
						description = "Production Server",
						url = "https://api.college.com"
				)
		}
)
public class StaffManagementSystemApplication {

	// Logger instance for this class
	private static final Logger logger = LoggerFactory.getLogger(StaffManagementSystemApplication.class);

	public static void main(String[] args) {
		logger.info("======================================");
		logger.info("Starting Staff Management System...");
		logger.info("======================================");

		// Start the Spring Boot application
		ConfigurableApplicationContext context = SpringApplication.run(StaffManagementSystemApplication.class, args);

		// Get environment to display application info
		Environment env = context.getEnvironment();
		String appName = env.getProperty("spring.application.name");
		String port = env.getProperty("server.port");

		logger.info("======================================");
		logger.info("Application: {} started successfully!", appName);
		logger.info("Server running on port: {}", port);
		logger.info("Swagger UI: http://localhost:{}/swagger-ui/index.html", port);
		logger.info("API Docs: http://localhost:{}/v3/api-docs", port);
		logger.info("======================================");
	}
}