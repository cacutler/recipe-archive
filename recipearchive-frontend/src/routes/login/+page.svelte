<script lang="ts">
	import { apiClient } from '$lib/api';
	import { authStore } from '$lib/stores';
	import type { User } from '$lib/types';
	import { goto } from '$app/navigation';

	let username = '';
	let password = '';
	let error = '';
	let loading = false;

	async function handleLogin() {
		if (!username || !password) {
			error = 'Please fill in all fields';
			return;
		}

		loading = true;
		error = '';

		try {
			const response = await apiClient.login(username, password);
			const user: User = {
				id: response.userId,
				firstName: '',
				lastName: '',
				username: username,
				email: ''
			};

			authStore.setUser(user, response.token);
			localStorage.setItem('user', JSON.stringify(user));
			await goto('/recipes');
		} catch (err: any) {
			error = err.message || 'Login failed. Please try again.';
		} finally {
			loading = false;
		}
	}
</script>

<div class="login-container">
	<div class="login-form">
		<h1>Login</h1>
		{#if error}
			<div class="error-text">{error}</div>
		{/if}

		<form on:submit|preventDefault={handleLogin}>
			<div class="form-group">
				<label for="username">Username</label>
				<input
					id="username"
					type="text"
					bind:value={username}
					placeholder="Enter your username"
					disabled={loading}
				/>
			</div>

			<div class="form-group">
				<label for="password">Password</label>
				<input
					id="password"
					type="password"
					bind:value={password}
					placeholder="Enter your password"
					disabled={loading}
				/>
			</div>

			<button type="submit" disabled={loading} class="btn-submit">
				{loading ? 'Logging in...' : 'Login'}
			</button>
		</form>

		<p class="auth-link">
			Don't have an account? <a href="/signup">Sign up here</a>
		</p>
	</div>
</div>

<style>
	.login-container {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 2rem;
	}

	.login-form {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
		width: 100%;
		max-width: 400px;
	}

	h1 {
		text-align: center;
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

	.form-group {
		margin-bottom: 1rem;
	}

	label {
		display: block;
		margin-bottom: 0.5rem;
		color: #333;
		font-weight: 500;
	}

	input {
		width: 100%;
		padding: 0.75rem;
		border: 1px solid #ddd;
		border-radius: 4px;
		font-size: 1rem;
		box-sizing: border-box;
	}

	input:focus {
		outline: none;
		border-color: #4caf50;
		box-shadow: 0 0 5px rgba(76, 175, 80, 0.3);
	}

	.btn-submit {
		width: 100%;
		padding: 0.75rem;
		background-color: #4caf50;
		color: white;
		border: none;
		border-radius: 4px;
		font-size: 1rem;
		font-weight: bold;
		cursor: pointer;
		transition: background-color 0.3s;
	}

	.btn-submit:hover:not(:disabled) {
		background-color: #45a049;
	}

	.btn-submit:disabled {
		background-color: #a5a5a5;
		cursor: not-allowed;
	}

	.auth-link {
		text-align: center;
		margin-top: 1.5rem;
	}

	.auth-link a {
		color: #4caf50;
		text-decoration: none;
		font-weight: bold;
	}

	.auth-link a:hover {
		text-decoration: underline;
	}
</style>