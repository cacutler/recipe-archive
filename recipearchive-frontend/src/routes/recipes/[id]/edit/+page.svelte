<script lang="ts">
	import { apiClient } from '$lib/api';
	import { authStore, recipeStore } from '$lib/stores';
	import type { Recipe } from '$lib/types';
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	let title = '';
	let description = '';
	let ingredients = '';
	let instructions = '';
	let allergies = '';
	let prepTime: number | string = '';
	let cookingTime: number | string = '';
	let servings: number | string = '';
	let error = '';
	let loading = true;
	let submitting = false;
	onMount(async () => {
		if (!$authStore.isAuthenticated) {
			await goto('/login');
			return;
		}
		try {
			const id = parseInt($page.params.id!);
			const recipe: Recipe = await apiClient.getRecipeById(id);
			recipeStore.selectRecipe(recipe);
			title = recipe.title;
			description = recipe.description;
			ingredients = recipe.ingredients;
			instructions = recipe.instructions;
			allergies = recipe.allergies || '';
			prepTime = recipe.prepTime || '';
			cookingTime = recipe.cookingTime || '';
			servings = recipe.servings || '';
		} catch (err: any) {
			error = err.message || 'Failed to load recipe';
		} finally {
			loading = false;
		}
	});
	async function handleSubmit() {
		if (!title || !ingredients || !instructions) {
			error = 'Please fill in title, ingredients, and instructions';
			return;
		}
		submitting = true;
		error = '';
		try {
			const id = parseInt($page.params.id!);
			const updated: Recipe = await apiClient.updateRecipe(id, {
				title,
				description,
				ingredients,
				instructions,
				allergies: allergies || undefined,
				prepTime: prepTime ? parseInt(String(prepTime)) : undefined,
				cookingTime: cookingTime ? parseInt(String(cookingTime)) : undefined,
				servings: servings ? parseInt(String(servings)) : undefined
			});
			console.log("updated is", updated);
			recipeStore.updateRecipe(id, updated);
			await goto(`/recipes/${id}`);
		} catch (err: any) {
			error = err.message || 'Failed to update recipe';
		} finally {
			submitting = false;
		}
	}
</script>
{#if !$authStore.isAuthenticated}
	<div class="info">Please log in to edit recipes</div>
{:else if loading}
	<div class="loading">Loading recipe...</div>
{:else}
	<div class="edit-recipe">
		<h1>Edit Recipe</h1>
		{#if error}
			<div class="error-text">{error}</div>
		{/if}
		<form on:submit|preventDefault={handleSubmit}>
			<div class="form-group">
				<label for="title">Recipe Title *</label>
				<input id="title" type="text" bind:value={title} placeholder="Enter recipe title" disabled={submitting} required>
			</div>
			<div class="form-group">
				<label for="description">Description</label>
				<textarea id="description" bind:value={description} placeholder="Enter recipe description" disabled={submitting} rows="3"></textarea>
			</div>
			<div class="form-row">
				<div class="form-group">
					<label for="prepTime">Prep Time (minutes)</label>
					<input id="prepTime" type="number" bind:value={prepTime} placeholder="e.g., 15" disabled={submitting} min="0">
				</div>
				<div class="form-group">
					<label for="cookingTime">Cooking Time (minutes)</label>
					<input id="cookingTime" type="number" bind:value={cookingTime} placeholder="e.g., 30" disabled={submitting} min="0">
				</div>
				<div class="form-group">
					<label for="servings">Servings</label>
					<input id="servings" type="number" bind:value={servings} placeholder="e.g., 4" disabled={submitting} min="0">
				</div>
			</div>
			<div class="form-group">
				<label for="ingredients">Ingredients *</label>
				<textarea id="ingredients" bind:value={ingredients} placeholder="Enter ingredients, one per line" disabled={submitting} rows="6" required></textarea>
			</div>
			<div class="form-group">
				<label for="instructions">Instructions *</label>
				<textarea id="instructions" bind:value={instructions} placeholder="Enter cooking instructions" disabled={submitting} rows="6" required></textarea>
			</div>
			<div class="form-group">
				<label for="allergies">Allergies/Warnings</label>
				<textarea id="allergies" bind:value={allergies} placeholder="Note any allergies or important warnings" disabled={submitting} rows="3"></textarea>
			</div>
			<div class="form-actions">
				<button type="submit" disabled={submitting} class="btn btn-primary">{submitting ? 'Updating...' : 'Update Recipe'}</button>
				<a href={`/recipes/${$page.params.id!}`} class="btn btn-secondary">Cancel</a>
			</div>
		</form>
	</div>
{/if}
<style>
	.edit-recipe {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
		max-width: 800px;
		margin: 0 auto;
	}
	h1 {
		color: #333;
		margin-bottom: 1.5rem;
	}
	.error-text {
		color: #d32f2f;
		background-color: #ffebee;
		padding: 0.75rem;
		border-radius: 4px;
		margin-bottom: 1rem;
	}
	.loading {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		text-align: center;
		color: #666;
	}
	.form-group {
		margin-bottom: 1.5rem;
	}
	.form-row {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
		gap: 1rem;
	}
	label {
		display: block;
		margin-bottom: 0.5rem;
		color: #333;
		font-weight: 500;
	}
	input, textarea {
		width: 100%;
		padding: 0.75rem;
		border: 1px solid #ddd;
		border-radius: 4px;
		font-size: 1rem;
		font-family: inherit;
		box-sizing: border-box;
	}
	input:focus, textarea:focus {
		outline: none;
		border-color: #4caf50;
		box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
	}
	input:disabled, textarea:disabled {
		background-color: #f5f5f5;
		cursor: not-allowed;
	}
	.form-actions {
		display: flex;
		gap: 1rem;
		margin-top: 2rem;
	}
	.btn {
		padding: 0.75rem 1.5rem;
		text-decoration: none;
		border-radius: 4px;
		border: none;
		cursor: pointer;
		font-size: 1rem;
		font-weight: bold;
		transition: all 0.3s;
	}
	.btn-primary {
		background-color: #4caf50;
		color: white;
	}
	.btn-primary:hover:not(:disabled) {
		background-color: #45a049;
	}
	.btn-primary:disabled {
		background-color: #a5a5a5;
		cursor: not-allowed;
	}
	.btn-secondary {
		background-color: #2196f3;
		color: white;
	}
	.btn-secondary:hover {
		background-color: #0b7dda;
	}
	.info {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		text-align: center;
		color: #666;
	}
</style>