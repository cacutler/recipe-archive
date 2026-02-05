<script lang="ts">
	import { authStore } from '$lib/stores';
	import { apiClient } from '$lib/api';
	import { goto } from '$app/navigation';
	async function handleLogout() {
		apiClient.logout();
		authStore.clearAuth();
		await goto('/login');
	}
</script>
<nav class="navbar">
	<div class="navbar-container">
		<a href="/recipes" class="navbar-brand">RecipeArchive</a>
		<ul class="navbar-nav">
			{#if $authStore.isAuthenticated && $authStore.user}
				<li><a href="/recipes">Recipes</a></li>
				<li><a href="/recipes/create">Create Recipe</a></li>
				<li><a href="/profile">Profile</a></li>
				<li>
					<button on:click={handleLogout} class="logout-btn">Logout</button>
				</li>
			{:else}
				<li><a href="/login">Login</a></li>
				<li><a href="/signup">Sign Up</a></li>
			{/if}
		</ul>
	</div>
</nav>
<style>
	.navbar {
		background-color: #333;
		padding: 1rem 0;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}
	.navbar-container {
		max-width: 1200px;
		margin: 0 auto;
		padding: 0 1rem;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
	.navbar-brand {
		color: white;
		font-size: 1.5rem;
		font-weight: bold;
		text-decoration: none;
	}
	.navbar-nav {
		display: flex;
		list-style: none;
		gap: 2rem;
		padding: 0;
		margin: 0;
	}
	.navbar-nav a {
		color: white;
		text-decoration: none;
		transition: color 0.3s;
	}
	.navbar-nav a:hover {
		color: #4caf50;
	}
	.logout-btn {
		background: none;
		border: none;
		color: white;
		cursor: pointer;
		font-size: 1rem;
		transition: color 0.3s;
	}
	.logout-btn:hover {
		color: #4caf50;
	}
</style>