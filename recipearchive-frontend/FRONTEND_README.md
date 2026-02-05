# RecipeArchive Frontend

A modern SvelteKit frontend for the RecipeArchive Spring Boot backend application.

## Features

- **Authentication**: JWT-based login and signup
- **Recipe Management**: Create, read, update, and delete recipes
- **User Profile**: Update user details and delete account
- **Responsive Design**: Mobile-friendly interface
- **Search**: Search recipes by title and description

## Pages

1. **Home** (`/`) - Welcome page with login/signup links
2. **Login** (`/login`) - User authentication
3. **Sign Up** (`/signup`) - Create new account
4. **Recipes** (`/recipes`) - List all recipes with search functionality
5. **Recipe Details** (`/recipes/:id`) - View recipe details and delete
6. **Create Recipe** (`/recipes/create`) - Create new recipe
7. **Edit Recipe** (`/recipes/:id/edit`) - Update existing recipe
8. **Profile** (`/profile`) - View and update user details, delete account

## Technology Stack

- **SvelteKit**: Modern web framework
- **TypeScript**: Type-safe development
- **Vite**: Fast build tool
- **Svelte Stores**: State management
- **CSS**: Modern styling

## Installation

1. Navigate to the frontend directory:
   ```bash
   cd recipearchive-frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

## Development

Start the development server:

```bash
npm run dev
```

The frontend will be available at `http://localhost:5173`

## Building

Build for production:

```bash
npm run build
```

## API Configuration

The frontend is configured to connect to the backend at `http://localhost:8080`. 

The backend CORS configuration must allow:
- Origin: `http://localhost:5173`
- Credentials: true
- All headers and methods

The current backend `CorsConfig.java` already supports this.

## Authentication Flow

1. User logs in with username and password
2. Backend returns JWT token and user info
3. JWT token is stored in `localStorage`
4. Token is included in Authorization header for authenticated requests
5. On page load, token is checked and user session is restored

## API Endpoints Used

### Authentication
- `POST /auth/login` - Login user

### Users
- `POST /users` - Create new user (signup)
- `GET /users/{id}` - Get user details
- `PATCH /users/{id}` - Update user details
- `DELETE /users/{id}` - Delete user account

### Recipes
- `GET /recipes` - Get all recipes
- `GET /recipes/{id}` - Get recipe by ID
- `GET /recipes/user/{userId}` - Get user's recipes
- `POST /recipes` - Create new recipe
- `PUT /recipes/{id}` - Update recipe
- `DELETE /recipes/{id}` - Delete recipe

## File Structure

```
src/
├── routes/
│   ├── +layout.svelte          # Main layout with navbar
│   ├── +page.svelte            # Home page
│   ├── login/
│   │   └── +page.svelte        # Login page
│   ├── signup/
│   │   └── +page.svelte        # Sign up page
│   ├── recipes/
│   │   ├── +page.svelte        # Recipe list page
│   │   ├── create/
│   │   │   └── +page.svelte    # Create recipe page
│   │   └── [id]/
│   │       ├── +page.svelte    # Recipe details page
│   │       └── edit/
│   │           └── +page.svelte # Edit recipe page
│   └── profile/
│       └── +page.svelte        # User profile page
├── lib/
│   ├── api.ts                  # API client with JWT handling
│   ├── stores.ts               # Svelte stores for state management
│   ├── components/
│   │   └── Navbar.svelte       # Navigation component
│   └── assets/
└── app.css                     # Global styles
```

## Usage Guide

### Login
1. Navigate to `/login`
2. Enter your username and password
3. Click "Login" to authenticate
4. You'll be redirected to the recipes page

### Sign Up
1. Navigate to `/signup`
2. Fill in all required fields:
   - First Name
   - Last Name
   - Username (3-50 characters)
   - Email (valid email format)
   - Password (minimum 8 characters)
3. Click "Sign Up" to create account
4. You'll be automatically logged in and redirected to recipes

### Browse Recipes
1. Go to `/recipes` to see all available recipes
2. Use the search box to filter recipes by title or description
3. Click the "View" button to see recipe details
4. Click "Edit" to modify a recipe (only your recipes)
5. Click "Delete" to remove a recipe

### Create Recipe
1. Click "Create New Recipe" on the recipes page
2. Fill in the recipe details:
   - Title (required)
   - Description
   - Ingredients (required)
   - Instructions (required)
   - Allergies/Warnings
   - Prep Time, Cooking Time, Servings (optional)
3. Click "Create Recipe"

### Edit Recipe
1. Go to the recipe you want to edit
2. Click the "Edit" button
3. Modify the details
4. Click "Update Recipe"

### Manage Profile
1. Click "Profile" in the navigation bar
2. View your current information
3. Click "Edit Profile" to modify your details
4. Click "Save Changes" to update
5. Scroll to "Danger Zone" to delete your account

## Troubleshooting

### Login fails
- Ensure backend is running on `http://localhost:8080`
- Check that username and password are correct
- Verify CORS configuration in backend

### Recipes don't load
- Check that backend is running
- Ensure JWT token is valid (might need to login again)
- Check browser console for API errors

### Can't create/edit recipes
- Make sure you're logged in
- Check that all required fields are filled
- Verify backend validation rules match frontend

## Future Enhancements

- Add recipe categories/tags
- Add recipe ratings and reviews
- Add ingredient measurements and units
- Add recipe images
- Add shopping list functionality
- Add recipe sharing
- Add dark mode
- Add recipe favorites

## License

This project is part of RecipeArchive.
