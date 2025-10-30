# Management System BE

## Project Modules
The project is organized as a multi-module Maven project with the following modules:
- `core` - Shared core functionality
- `employee` - Employee management module
- `product` - Product management module

## Bill of Materials

This Spring Boot project is built with the following technologies and versions:

### Core Technologies
- **Java**: 17
- **Spring Boot**: 3.4.11
- **Spring Cloud**: 2024.0.2

### Database & Migration
- **Flyway Core**: 11.14.1
- **Flyway MySQL**: 11.14.1
- **MySQL Driver**: 9.4.0
- **H2 Database**: (Spring Boot managed version)

### Development Tools
- **Lombok**: 1.18.42
- **SpringDoc OpenAPI**: 2.8.13


### Build Configuration
- **Build Tool**: Maven
- **Compiler Plugin**: Maven Compiler Plugin with Lombok annotation processing
- **Spring Boot Maven Plugin**: For application packaging and execution
