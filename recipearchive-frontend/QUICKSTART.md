# RecipeArchive Frontend - Quick Start Guide

## Prerequisites

- Node.js (v18 or higher)
- npm or yarn
- Backend running on `http://localhost:8080`

## Quick Start

### 1. Install Dependencies

```bash
cd recipearchive-frontend
npm install
```

### 2. Start Development Server

```bash
npm run dev
```

The frontend will be available at `http://localhost:5173`

### 3. Test the Application

#### Create Test Account
1. Navigate to `http://localhost:5173/signup`
2. Create a new account with:
   - First Name: Test
   - Last Name: User
   - Username: testuser123
   - Email: test@example.com
   - Password: TestPassword123 (minimum 8 characters)

#### Create a Recipe
1. Click "Create New Recipe" after logging in
2. Fill in the recipe form:
   - **Title**: Chocolate Chip Cookies
   - **Description**: Classic homemade cookies
   - **Ingredients** (copy below):
     ```
     2 cups all-purpose flour
     1 cup butter, softened
     3/4 cup sugar
     3/4 cup brown sugar
     2 eggs
     2 teaspoons vanilla extract
     1 teaspoon baking soda
     1/2 teaspoon salt
     2 cups chocolate chips
     ```
   - **Instructions** (copy below):
     ```
     1. Preheat oven to 375°F (190°C)
     2. Cream together butter and sugars until fluffy
     3. Beat in eggs and vanilla extract
     4. In another bowl, combine flour, baking soda, and salt
     5. Gradually blend dry ingredients into creamed mixture
     6. Stir in chocolate chips
     7. Drop rounded tablespoons of dough onto ungreased baking sheets
     8. Bake for 9-12 minutes or until golden brown
     9. Cool on baking sheets for 2 minutes, then transfer to wire racks
     ```
   - **Prep Time**: 15
   - **Cooking Time**: 12
   - **Servings**: 24
   - **Allergies**: Contains eggs, dairy, wheat, and tree nuts

3. Click "Create Recipe"

#### Browse and Search
1. Go to "Recipes" page
2. Try searching for "chocolate" or other keywords
3. Click "View" on any recipe to see full details

#### Edit Recipe
1. From recipe details page, click "Edit"
2. Modify any fields
3. Click "Update Recipe"

#### Update Profile
1. Click "Profile" in the navigation
2. Click "Edit Profile"
3. Update your information
4. Click "Save Changes"

#### Delete Recipe
1. Go to recipe details page
2. Click "Delete" button
3. Confirm deletion

#### Delete Account
1. Go to "Profile"
2. Scroll to "Danger Zone"
3. Click "Delete Account"
4. Confirm - this will delete your account and all your recipes

## Features Overview

### Home Page
- Welcome message with login/signup links
- Automatically redirects to recipes if logged in

### Login
- Username and password authentication
- JWT token-based session management
- Persistent login across page reloads

### Sign Up
- Create new account
- Validation for email format
- Password complexity requirements

### Recipes Listing
- View all recipes in grid layout
- Search by recipe title or description
- Quick view of prep/cook time and servings
- View, Edit, Delete actions for each recipe

### Recipe Details
- Full recipe information display
- Edit button to modify recipe
- Delete button with confirmation
- Displays creation/update timestamps

### Create/Edit Recipe
- Form fields for all recipe details
- Optional fields: description, prep time, cook time, servings, allergies
- Real-time form validation
- Cancel button to discard changes

### User Profile
- View current user information
- Edit profile (name and email)
- View account creation date
- Delete account option with confirmation

## Environment Configuration

### Development
- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- API requests are proxied through Vite dev server

### Build for Production
```bash
npm run build
npm run preview
```

## Common Issues

### Issue: "Cannot connect to backend"
**Solution**:
- Ensure backend is running: `mvn spring-boot:run` or start the Spring Boot application
- Check that it's running on port 8080
- Verify backend CORS configuration allows `http://localhost:5173`

### Issue: "Login fails with 401 error"
**Solution**:
- Double-check username and password
- Ensure user was created successfully
- Check backend logs for validation errors

### Issue: "Token expired"
**Solution**:
- Log out and log back in
- Token will be refreshed automatically on re-login

### Issue: "Recipes don't load"
**Solution**:
- Log in first (authentication required for most endpoints)
- Check browser developer tools (F12) > Network tab for API errors
- Check backend logs for any issues

## API Documentation

All API requests include the JWT token in the Authorization header:
```
Authorization: Bearer <jwt_token>
```

### Authentication Endpoints
- `POST /auth/login` - Login and get JWT token

### User Endpoints
- `POST /users` - Create new user account
- `GET /users/{id}` - Get user information
- `PATCH /users/{id}` - Update user details
- `DELETE /users/{id}` - Delete user account

### Recipe Endpoints
- `GET /recipes` - Get all recipes
- `GET /recipes/{id}` - Get recipe by ID
- `POST /recipes` - Create new recipe
- `PUT /recipes/{id}` - Update recipe
- `DELETE /recipes/{id}` - Delete recipe

## Development Tips

### Code Structure
- **`src/lib/api.ts`** - API client with all backend calls
- **`src/lib/stores.ts`** - Svelte stores for auth and recipe state
- **`src/routes/`** - Page components (each `+page.svelte`)

### Adding Features
1. Update stores if new state is needed
2. Update API client for new endpoints
3. Create page components in routes/
4. Import and use stores in components

### Debugging
- Open browser DevTools (F12)
- Check Network tab for API requests
- Check Console for JavaScript errors
- Check Application tab > Local Storage for stored token

## Next Steps

1. **Run the backend**: Ensure Spring Boot backend is running
2. **Install dependencies**: `npm install`
3. **Start dev server**: `npm run dev`
4. **Create account**: Sign up with test data
5. **Explore features**: Create, edit, and manage recipes

## Support

For issues with the backend API, check:
- [Backend README](../README.md)
- [Backend HELP](../HELP.md)

For SvelteKit documentation:
- [SvelteKit Docs](https://kit.svelte.dev)
- [Svelte Docs](https://svelte.dev)
