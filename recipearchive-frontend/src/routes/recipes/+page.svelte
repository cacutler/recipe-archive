<script lang="ts">
	import { apiClient } from '$lib/api';
	import { authStore, recipeStore } from '$lib/stores';
	import type { Recipe } from '$lib/types';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';

	let searchQuery = '';

	onMount(async () => {
		// Redirect to login if not authenticated
		if (!$authStore.isAuthenticated) {
			await goto('/login');
			return;
		}

		// Load all recipes
		recipeStore.setLoading(true);
		try {
			const recipes = await apiClient.getAllRecipes();
			recipeStore.setRecipes(recipes);
		} catch (err: any) {
			recipeStore.setError(err.message || 'Failed to load recipes');
		} finally {
			recipeStore.setLoading(false);
		}
	});

	$: filteredRecipes = ($recipeStore.recipes as Recipe[]).filter(
		(recipe) =>
			recipe.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
			recipe.description.toLowerCase().includes(searchQuery.toLowerCase())
	);

	async function deleteRecipe(id: number) {
		if (!confirm('Are you sure you want to delete this recipe?')) {
			return;
		}

		try {
			await apiClient.deleteRecipe(id);
			recipeStore.deleteRecipe(id);
		} catch (err: any) {
			alert('Failed to delete recipe: ' + (err.message || 'Unknown error'));
		}
	}
</script>

{#if !$authStore.isAuthenticated}
	<div class="info">Please log in to view recipes</div>
{:else}
	<div class="header">
		<h1>Recipes</h1>
		<a href="/recipes/create" class="btn btn-primary">Create New Recipe</a>
	</div>

	<div class="search-area">
		<input
			type="text"
			placeholder="Search recipes..."
			bind:value={searchQuery}
			class="search-input"
		/>
	</div>

	{#if $recipeStore.error}
		<div class="error-text">{$recipeStore.error}</div>
	{/if}

	{#if $recipeStore.loading}
		<div class="loading">Loading recipes...</div>
	{:else if filteredRecipes.length === 0}
		<div class="empty-state">
			<p>No recipes found</p>
			<a href="/recipes/create" class="btn btn-secondary">Create your first recipe</a>
		</div>
	{:else}
		<div class="recipes-grid">
			{#each filteredRecipes as recipe (recipe.id)}
				<div class="recipe-card">
					<h3>{recipe.title}</h3>
					<p class="description">{recipe.description}</p>
					<div class="recipe-info">
						{#if recipe.prepTime}
							<span class="info-item">⏱️ Prep: {recipe.prepTime}m</span>
						{/if}
						{#if recipe.cookingTime}
							<span class="info-item">🍳 Cook: {recipe.cookingTime}m</span>
						{/if}
						{#if recipe.servings}
							<span class="info-item">🍽️ Servings: {recipe.servings}</span>
						{/if}
					</div>
					<div class="card-actions">
						<a href={`/recipes/${recipe.id}`} class="btn btn-sm btn-primary">View</a>
						<a href={`/recipes/${recipe.id}/edit`} class="btn btn-sm btn-secondary">Edit</a>
						<button on:click={() => deleteRecipe(recipe.id)} class="btn btn-sm btn-danger">
							Delete
						</button>
					</div>
				</div>
			{/each}
		</div>
	{/if}
{/if}

<style>
	.header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 2rem;
	}

	h1 {
		color: #333;
		margin: 0;
	}

	.search-area {
		margin-bottom: 2rem;
	}

	.search-input {
		width: 100%;
		padding: 0.75rem;
		border: 1px solid #ddd;
		border-radius: 4px;
		font-size: 1rem;
	}

	.search-input:focus {
		outline: none;
		border-color: #4caf50;
		box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
	}

	.error-text {
		color: #d32f2f;
		background-color: #ffebee;
		padding: 1rem;
		border-radius: 4px;
		margin-bottom: 1rem;
	}

	.loading {
		text-align: center;
		padding: 2rem;
		color: #666;
	}

	.empty-state {
		text-align: center;
		padding: 3rem;
		background: white;
		border-radius: 8px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}

	.empty-state p {
		color: #666;
		margin-bottom: 1rem;
	}

	.recipes-grid {
		display: grid;
		grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
		gap: 1.5rem;
	}

	.recipe-card {
		background: white;
		border-radius: 8px;
		padding: 1.5rem;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
		transition: box-shadow 0.3s, transform 0.3s;
	}

	.recipe-card:hover {
		box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
		transform: translateY(-2px);
	}

	.recipe-card h3 {
		color: #333;
		margin: 0 0 0.5rem 0;
	}

	.description {
		color: #666;
		margin: 0 0 1rem 0;
		font-size: 0.95rem;
	}

	.recipe-info {
		display: flex;
		flex-wrap: wrap;
		gap: 0.75rem;
		margin-bottom: 1rem;
		font-size: 0.9rem;
	}

	.info-item {
		color: #666;
		background: #f5f5f5;
		padding: 0.25rem 0.5rem;
		border-radius: 4px;
	}

	.card-actions {
		display: flex;
		gap: 0.5rem;
	}

	.btn {
		padding: 0.5rem 1rem;
		text-decoration: none;
		border-radius: 4px;
		border: none;
		cursor: pointer;
		font-size: 0.9rem;
		transition: all 0.3s;
	}

	.btn-sm {
		padding: 0.4rem 0.8rem;
		font-size: 0.85rem;
	}

	.btn-primary {
		background-color: #4caf50;
		color: white;
	}

	.btn-primary:hover {
		background-color: #45a049;
	}

	.btn-secondary {
		background-color: #2196f3;
		color: white;
	}

	.btn-secondary:hover {
		background-color: #0b7dda;
	}

	.btn-danger {
		background-color: #f44336;
		color: white;
	}

	.btn-danger:hover {
		background-color: #d32f2f;
	}

	.info {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		text-align: center;
		color: #666;
	}
</style>