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

- Recipe ID: big_int (primary key)
- User ID: big_int (foreign key)
- Title: varchar(200)
- Description: text
- Ingredients: text
- Instructions: text
- Allergies: text
- Prep Time: int
- Cooking Time: int
- Servings: int
- Created At: timestamp
- Updated At: timestamp

**User Table:**

- User ID: big_int (primary key)
- First Name: varchar(50)
- Last Name: varchar(50)
- Username: varchar(50)
- Email: varchar(100)
- Password: varchar(255)
- Created At: timestamp
- Updated At: timestamp