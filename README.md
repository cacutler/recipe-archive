# The Recipe Archive

This is a Spring Boot backend and Svelte/SvelteKit frontend project with a PostgreSQL database for digitizing, storing, and retrieving/viewing family recipes.

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
- Model Mapper
- Spring Boot Starter Security
- JSON Web Token

### Database Design

The database uses PostgreSQL and has the following tables and table columns.

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

## REST Endpoints

| Name                 | Method | Path                   | Controller       |
| -------------------- | ------ | ---------------------- | ---------------- |
| Login                | POST   | /auth/login            | AuthController   |
| Get all recipes      | GET    | /recipes               | RecipeController |
| Get one recipe       | GET    | /recipes/{id}          | RecipeController |
| Get a user's recipes | GET    | /recipes/user/{userId} | RecipeController |
| Create a recipe      | POST   | /recipes               | RecipeController |
| Delete a recipe      | DELETE | /recipes/{id}          | RecipeController |
| Update a recipe      | PUT    | /recipes/{id}          | RecipeController |
| Get all users        | GET    | /users                 | UserController   |
| Get a user           | GET    | /users/{id}            | UserController   |
| Register a user      | POST   | /users                 | UserController   |
| Update a user        | PUT    | /users/{id}            | UserController   |
| Delete a user        | DELETE | /users/{id}            | UserController   |