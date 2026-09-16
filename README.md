**# Management System Backend**

**## Project Modules**

The project is organized as a multi-module Maven project with the following modules:

- `core` - Shared core functionality across projects

- `user` - User management project

- `employee` - Employee management project

- `product` - Product management project

- `order` - Order project

- `payment` - Payment project

**## Bill of Materials**

This Spring Boot project is built with the following technologies and versions:

**### Core Technologies**

- ****Java****: 21

- ****Spring Boot****: 3.4.11

- ****Spring Cloud****: 2024.0.2

- ****Spring Security****: 6.4.12 (Spring Boot managed version)

- ****Keycloak****: 26.4.5

- ****Kafka****: 3.8.0

**### Database & Migration**

- ****Flyway Core****: 11.14.1

- ****Flyway PostgreSQL****: 12.1.0

- ****PostgreSQL Driver****: 42.7.8

- ****H2 Database****: 2.3.232 (Spring Boot managed version)

- ****CockroachDB****: v25.4.5 (development/testing)

**### Messaging**

- ****Spring Kafka****: 3.1.2 (Spring Boot managed version)

**### Development Tools**

- ****Lombok****: 1.18.42

- ****SpringDoc OpenAPI****: 2.8.13

**### Build Configuration**

- ****Build Tool****: Maven

- ****Compiler Plugin****: Maven Compiler Plugin with Lombok annotation processing

- ****Spring Boot Maven Plugin****: For application packaging and execution

**## Security Features**

- Spring Security with OAuth2 Resource Server

- JWT token authentication

- Role Based Access Control

- OAuth2 Client support via Keycloak

**## API Documentation**

- Automatic OpenAPI 3 documentation via SpringDoc

- Swagger UI interface for API testing and exploration

**## Development Setup Steps - How To Get Project Running**

1. Run the Keycloak container by running the Docker Compose file in the `management-system-keycloak` repository. Once it is running, use the Keycloak Admin UI to import the `management_system` realm and client using the `realm-export.json` file.



2. Run the CockroachDB container by running the Docker Compose file in the `management-system-cockroachdb` repository. Once it is running, connect to CockroachDB running the `dev_connect_cockroach.sh` file and run the following command:

```sql
CREATE DATABASE management_system;
```


3. Configure IntelliJ IDEA to use the `.env` file in the repo when running any of the microservices. Replace the Keycloak secret in the `.env` file with the secret for the `management_system` client from your own Keycloak instance.
