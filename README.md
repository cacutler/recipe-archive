# The Recipe Archive

This is a Spring Boot backend project with a MySQL database for digitizing, storing, and retrieving/viewing family recipes.  I am thinking of using Svelte or a similar JavaScript framework, like Angular or React/Next, for my frontend in the future.

## Project Details

**Language and Language Version:** Java 21

**Spring Boot Version:** 4.0.2

**Build Tool:** Maven

**Packaging:** Jar

**Configuration:** YAML

**Dependencies:**

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- MySQL Connector J
- Spring Boot Starter Validation
- Lombok
- Flyway Core

### Database Design

The database uses MySQL and has the following tables and table columns.

**Recipe Table:**

- Recipe ID: int (primary key)
- User ID: int (foreign key)
- Title: varchar(100)
- Description: text
- Prep Time: int
- Cooking Time: int
- Ingredients: JSON (Array)
- Steps: JSON (Array)
- Allergies: JSON (Array)

**User Table:**

- User ID: int (primary key)
- First Name: varchar(50)
- Last Name: varchar(50)
- Email: varchar(255)
- Password: varchar(255)