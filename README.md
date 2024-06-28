# Customer Search Application

This project is a Spring Boot application that provides a RESTful API for searching customers by their first name. It uses PostgreSQL as the database and is containerized using Docker.

## Features

- Customer search by firstName, lastName, and companyName.
- RESTful API endpoints for managing and searching customers.
- Pagination support for search results.
- OpenAPI defined the contract between front end and back end service.
- Logging with Log4j2.
- Integration with PostgreSQL as data store.
- Dockerized for easy deployment.

## Prerequisites

- Java 17
- Maven 3.6.3 or higher
- Docker

## Getting Started

### Clone the Repository

```sh
git clone https://github.com/fangz003/search-spring-boot.git
```

### Build the application
```sh
mvn clean install
```

### Run PostgreSQL in docker container

```sh
docker-compose up -d
```