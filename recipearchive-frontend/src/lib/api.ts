// API client with JWT token handling

import type { User, Recipe, LoginResponse } from './types';

// Use relative paths in development, absolute URL in production
const API_BASE_URL = typeof window !== 'undefined' && window.location.hostname !== 'localhost'
	? 'http://localhost:8080'
	: '';

export interface ApiResponse<T> {
	data?: T;
	message?: string;
	error?: string;
}

export class ApiClient {
	private getAuthToken(): string | null {
		if (typeof window !== 'undefined') {
			return localStorage.getItem('jwt_token');
		}
		return null;
	}

	private setAuthToken(token: string): void {
		if (typeof window !== 'undefined') {
			localStorage.setItem('jwt_token', token);
		}
	}

	private removeAuthToken(): void {
		if (typeof window !== 'undefined') {
			localStorage.removeItem('jwt_token');
		}
	}

	private async request<T>(
		method: string,
		endpoint: string,
		body?: unknown,
		requiresAuth = true
	): Promise<T> {
		const headers: HeadersInit = {
			'Content-Type': 'application/json'
		};

		const token = this.getAuthToken();
		if (requiresAuth && token) {
			headers['Authorization'] = `Bearer ${token}`;
		}

		const options: RequestInit = {
			method,
			headers
		};

		if (body) {
			options.body = JSON.stringify(body);
		}

		const response = await fetch(`${API_BASE_URL}${endpoint}`, options);

		if (!response.ok) {
			const errorData = await response.json();
			throw new Error(errorData.message || `API Error: ${response.status}`);
		}

		if (response.status === 204) {
			return undefined as T;
		}

		return response.json();
	}

	// Auth endpoints
	async login(username: string, password: string): Promise<LoginResponse> {
		const response = await this.request<LoginResponse>(
			'POST',
			'/auth/login',
			{ username, password },
			false
		);
		if (response.token) {
			this.setAuthToken(response.token);
		}
		return response;
	}

	async signup(userData: {
		firstName: string;
		lastName: string;
		username: string;
		email: string;
		password: string;
	}): Promise<User> {
		return this.request('POST', '/users', userData, false);
	}

	logout(): void {
		this.removeAuthToken();
	}

	// User endpoints
	async getUser(id: number): Promise<User> {
		return this.request('GET', `/users/${id}`);
	}

	async updateUser(
		id: number,
		updates: {
			firstName?: string;
			lastName?: string;
			email?: string;
		}
	): Promise<User> {
		return this.request('PATCH', `/users/${id}`, updates);
	}

	async deleteUser(id: number): Promise<void> {
		return this.request('DELETE', `/users/${id}`);
	}

	// Recipe endpoints
	async getAllRecipes(): Promise<Recipe[]> {
		return this.request('GET', '/recipes', undefined, false);
	}

	async getRecipeById(id: number): Promise<Recipe> {
		return this.request('GET', `/recipes/${id}`, undefined, false);
	}

	async getUserRecipes(userId: number): Promise<Recipe[]> {
		return this.request('GET', `/recipes/user/${userId}`, undefined, false);
	}

	async createRecipe(recipeData: {
		title: string;
		description: string;
		ingredients: string;
		instructions: string;
		allergies?: string;
		prepTime?: number;
		cookingTime?: number;
		servings?: number;
		userId: number;
	}): Promise<Recipe> {
		return this.request('POST', '/recipes', recipeData);
	}

	async updateRecipe(
		id: number,
		updates: {
			title?: string;
			description?: string;
			ingredients?: string;
			instructions?: string;
			allergies?: string;
			prepTime?: number;
			cookingTime?: number;
			servings?: number;
		}
	): Promise<Recipe> {
		return this.request('PUT', `/recipes/${id}`, updates);
	}

	async deleteRecipe(id: number): Promise<void> {
		return this.request('DELETE', `/recipes/${id}`);
	}
}

export const apiClient = new ApiClient();