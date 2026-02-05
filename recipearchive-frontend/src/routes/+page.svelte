<script lang="ts">
	import { goto } from '$app/navigation';
	import { authStore } from '$lib/stores';
	$: if ($authStore.isAuthenticated) {
		goto('/recipes');
	}
</script>
<div class="home">
	<h1>Welcome to RecipeArchive</h1>
	<p>Your personal recipe management collection</p>
	<div class="cta-buttons">
		<a href="/login" class="btn btn-primary">Login</a>
		<a href="/signup" class="btn btn-secondary">Sign Up</a>
	</div>
</div>
<style>
	.home {
		text-align: center;
		padding: 3rem;
		background: white;
		border-radius: 8px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}
	h1 {
		color: #333;
		margin-bottom: 0.5rem;
	}
	p {
		color: #666;
		margin-bottom: 2rem;
	}
	.cta-buttons {
		display: flex;
		gap: 1rem;
		justify-content: center;
	}
	.btn {
		padding: 0.75rem 2rem;
		text-decoration: none;
		border-radius: 4px;
		font-weight: bold;
		transition: all 0.3s;
		border: 2px solid #4caf50;
	}
	.btn-primary {
		background-color: #4caf50;
		color: white;
	}
	.btn-primary:hover {
		background-color: #45a049;
	}
	.btn-secondary {
		background-color: transparent;
		color: #4caf50;
	}
	.btn-secondary:hover {
		background-color: #4caf50;
		color: white;
	}
</style>