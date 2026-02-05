/**
 * Type definitions for RecipeArchive Frontend
 */

export interface User {
	id: number;
	firstName: string;
	lastName: string;
	username: string;
	email: string;
	createdAt?: string;
	updatedAt?: string;
}

export interface Recipe {
	id: number;
	userId: number;
	title: string;
	description: string;
	ingredients: string;
	instructions: string;
	allergies?: string;
	prepTime?: number;
	cookingTime?: number;
	servings?: number;
	createdAt?: string;
	updatedAt?: string;
}

export interface LoginResponse {
	token: string;
	username: string;
	userId: number;
}

export interface ApiError {
	message: string;
	error?: string;
}

export interface AuthState {
	isAuthenticated: boolean;
	user: User | null;
	token: string | null;
	loading: boolean;
	error: string | null;
}

export interface RecipeState {
	recipes: Recipe[];
	selectedRecipe: Recipe | null;
	loading: boolean;
	error: string | null;
}