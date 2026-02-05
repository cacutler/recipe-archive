<script lang="ts">
	import { apiClient } from '$lib/api';
	import { authStore, recipeStore } from '$lib/stores';
	import type { Recipe } from '$lib/types';
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	let loading = true;
	let error = '';
	onMount(async () => {
		if (!$authStore.isAuthenticated) {
			await goto('/login');
			return;
		}
		try {
			const id = parseInt($page.params.id!);
			const recipe: Recipe = await apiClient.getRecipeById(id);
			recipeStore.selectRecipe(recipe);
		} catch (err: any) {
			error = err.message || 'Failed to load recipe';
		} finally {
			loading = false;
		}
	});
	async function handleDelete() {
		if (!$recipeStore.selectedRecipe) return;
		if (!confirm('Are you sure you want to delete this recipe?')) {
			return;
		}
		try {
			await apiClient.deleteRecipe($recipeStore.selectedRecipe.id);
			recipeStore.deleteRecipe($recipeStore.selectedRecipe.id);
			await goto('/recipes');
		} catch (err: any) {
			error = err.message || 'Failed to delete recipe';
		}
	}
	function formatDate(dateString?: string) {
		if (!dateString) return 'N/A';
		return new Date(dateString).toLocaleDateString();
	}
</script>
{#if !$authStore.isAuthenticated}
	<div class="info">Please log in to view recipes</div>
{:else if loading}
	<div class="loading">Loading recipe...</div>
{:else if error}
	<div class="error-text">{error}</div>
{:else if $recipeStore.selectedRecipe}
	<div class="recipe-detail">
		<div class="header">
			<h1>{$recipeStore.selectedRecipe.title}</h1>
			<div class="actions">
				<a href={`/recipes/${$recipeStore.selectedRecipe.id}/edit`} class="btn btn-secondary">Edit</a>
				<button on:click={handleDelete} class="btn btn-danger">Delete</button>
				<a href="/recipes" class="btn btn-primary">Back to Recipes</a>
			</div>
		</div>
		<div class="recipe-meta">
			<div class="meta-item">
				<span class="label">Created:</span>
				<span>{formatDate($recipeStore.selectedRecipe.createdAt)}</span>
			</div>
			<div class="meta-item">
				<span class="label">Updated:</span>
				<span>{formatDate($recipeStore.selectedRecipe.updatedAt)}</span>
			</div>
		</div>
		<div class="recipe-info">
			{#if $recipeStore.selectedRecipe.prepTime}
				<div class="info-box">
					<span class="label">⏱️ Prep Time</span>
					<span class="value">{$recipeStore.selectedRecipe.prepTime} minutes</span>
				</div>
			{/if}
			{#if $recipeStore.selectedRecipe.cookingTime}
				<div class="info-box">
					<span class="label">🍳 Cooking Time</span>
					<span class="value">{$recipeStore.selectedRecipe.cookingTime} minutes</span>
				</div>
			{/if}
			{#if $recipeStore.selectedRecipe.servings}
				<div class="info-box">
					<span class="label">🍽️ Servings</span>
					<span class="value">{$recipeStore.selectedRecipe.servings}</span>
				</div>
			{/if}
		</div>
		{#if $recipeStore.selectedRecipe.description}
			<section class="recipe-section">
				<h2>Description</h2>
				<p>{$recipeStore.selectedRecipe.description}</p>
			</section>
		{/if}
		{#if $recipeStore.selectedRecipe.ingredients}
			<section class="recipe-section">
				<h2>Ingredients</h2>
				<div class="ingredients-text">{$recipeStore.selectedRecipe.ingredients}</div>
			</section>
		{/if}
		{#if $recipeStore.selectedRecipe.instructions}
			<section class="recipe-section">
				<h2>Instructions</h2>
				<div class="instructions-text">{$recipeStore.selectedRecipe.instructions}</div>
			</section>
		{/if}
		{#if $recipeStore.selectedRecipe.allergies}
			<section class="recipe-section warning">
				<h2>⚠️ Allergies</h2>
				<p>{$recipeStore.selectedRecipe.allergies}</p>
			</section>
		{/if}
	</div>
{:else}
	<div class="empty-state">
		<p>Recipe not found</p>
		<a href="/recipes" class="btn btn-primary">Back to Recipes</a>
	</div>
{/if}
<style>
	.header {
		display: flex;
		justify-content: space-between;
		align-items: start;
		margin-bottom: 2rem;
		gap: 2rem;
	}
	.header h1 {
		margin: 0;
		color: #333;
	}
	.actions {
		display: flex;
		gap: 0.5rem;
	}
	.recipe-meta {
		display: flex;
		gap: 2rem;
		margin-bottom: 2rem;
		color: #666;
		font-size: 0.9rem;
	}
	.meta-item {
		display: flex;
		flex-direction: column;
	}
	.meta-item .label {
		font-weight: bold;
		color: #333;
	}
	.recipe-info {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
		gap: 1rem;
		margin-bottom: 2rem;
	}
	.info-box {
		background: #f5f5f5;
		padding: 1rem;
		border-radius: 4px;
		display: flex;
		flex-direction: column;
	}
	.info-box .label {
		font-weight: bold;
		color: #333;
		margin-bottom: 0.5rem;
	}
	.info-box .value {
		color: #4caf50;
		font-size: 1.25rem;
	}
	.recipe-section {
		background: white;
		padding: 1.5rem;
		border-radius: 8px;
		margin-bottom: 1.5rem;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}
	.recipe-section.warning {
		background: #fff3cd;
		border-left: 4px solid #ffc107;
	}
	.recipe-section h2 {
		color: #333;
		margin-top: 0;
		margin-bottom: 1rem;
	}
	.ingredients-text, .instructions-text {
		white-space: pre-wrap;
		color: #666;
		line-height: 1.6;
	}
	.recipe-detail p {
		color: #666;
		line-height: 1.6;
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
	.loading, .error-text {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		text-align: center;
	}
	.error-text {
		color: #d32f2f;
		background-color: #ffebee;
	}
	.empty-state {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		text-align: center;
		color: #666;
	}
	.info {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		text-align: center;
		color: #666;
	}
</style>