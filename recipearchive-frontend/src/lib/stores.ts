import { writable } from 'svelte/store';
import type { User, AuthState, Recipe, RecipeState } from './types';

export type { User, AuthState, Recipe, RecipeState };

const initialAuthState: AuthState = {
	isAuthenticated: false,
	user: null,
	token: null,
	loading: false,
	error: null
};

function createAuthStore() {
	const { subscribe, set, update } = writable<AuthState>(initialAuthState);

	return {
		subscribe,
		setUser: (user: User, token: string) => {
			update((state) => ({
				...state,
				isAuthenticated: true,
				user,
				token
			}));
		},
		clearAuth: () => {
			set(initialAuthState);
		},
		setLoading: (loading: boolean) => {
			update((state) => ({ ...state, loading }));
		},
		setError: (error: string | null) => {
			update((state) => ({ ...state, error }));
		},
		updateUser: (user: User) => {
			update((state) => ({ ...state, user }));
		}
	};
}

export const authStore = createAuthStore();

// Recipe loading state
const initialRecipeState: RecipeState = {
	recipes: [],
	selectedRecipe: null,
	loading: false,
	error: null
};

function createRecipeStore() {
	const { subscribe, set, update } = writable<RecipeState>(initialRecipeState);

	return {
		subscribe,
		setRecipes: (recipes: Recipe[]) => {
			update((state) => ({ ...state, recipes }));
		},
		selectRecipe: (recipe: Recipe | null) => {
			update((state) => ({ ...state, selectedRecipe: recipe }));
		},
		addRecipe: (recipe: Recipe) => {
			update((state) => ({
				...state,
				recipes: [recipe, ...state.recipes]
			}));
		},
		updateRecipe: (id: number, updates: Partial<Recipe>) => {
			update((state) => ({
				...state,
				recipes: state.recipes.map((r) => (r.id === id ? { ...r, ...updates } : r)),
				selectedRecipe: state.selectedRecipe?.id === id ? { ...state.selectedRecipe, ...updates } : state.selectedRecipe
			}));
		},
		deleteRecipe: (id: number) => {
			update((state) => ({
				...state,
				recipes: state.recipes.filter((r) => r.id !== id),
				selectedRecipe: state.selectedRecipe?.id === id ? null : state.selectedRecipe
			}));
		},
		setLoading: (loading: boolean) => {
			update((state) => ({ ...state, loading }));
		},
		setError: (error: string | null) => {
			update((state) => ({ ...state, error }));
		}
	};
}

export const recipeStore = createRecipeStore();