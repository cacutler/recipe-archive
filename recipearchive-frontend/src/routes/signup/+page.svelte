<script lang="ts">
	import { apiClient } from '$lib/api';
	import { authStore } from '$lib/stores';
	import type { User } from '$lib/types';
	import { goto } from '$app/navigation';
	let firstName = '';
	let lastName = '';
	let username = '';
	let email = '';
	let password = '';
	let confirmPassword = '';
	let error = '';
	let loading = false;
	async function handleSignup() {
		if (!firstName || !lastName || !username || !email || !password || !confirmPassword) {// Validate
			error = 'Please fill in all fields';
			return;
		}
		if (password !== confirmPassword) {
			error = 'Passwords do not match';
			return;
		}
		if (password.length < 8) {
			error = 'Password must be at least 8 characters';
			return;
		}
		loading = true;
		error = '';
		try {
			await apiClient.signup({
				firstName,
				lastName,
				username,
				email,
				password
			});
			const loginResponse = await apiClient.login(username, password);// After signup, try to login
			const user: User = {
				id: loginResponse.userId,
				username: loginResponse.username,
				firstName,
				lastName,
				email
			};
			authStore.setUser(user, loginResponse.token);
			localStorage.setItem('user', JSON.stringify(user));
			await goto('/recipes');
		} catch (err: any) {
			error = err.message || 'Sign up failed. Please try again.';
		} finally {
			loading = false;
		}
	}
</script>
<div class="signup-container">
	<div class="signup-form">
		<h1>Create Account</h1>
		{#if error}
			<div class="error-text">{error}</div>
		{/if}
		<form on:submit|preventDefault={handleSignup}>
			<div class="form-row">
				<div class="form-group">
					<label for="firstName">First Name</label>
					<input id="firstName" type="text" bind:value={firstName} placeholder="First name" disabled={loading}>
				</div>
				<div class="form-group">
					<label for="lastName">Last Name</label>
					<input id="lastName" type="text" bind:value={lastName} placeholder="Last name" disabled={loading}>
				</div>
			</div>
			<div class="form-group">
				<label for="username">Username</label>
				<input id="username" type="text" bind:value={username} placeholder="Choose a username" disabled={loading}>
			</div>
			<div class="form-group">
				<label for="email">Email</label>
				<input id="email" type="email" bind:value={email} placeholder="Enter your email" disabled={loading}>
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<input id="password" type="password" bind:value={password} placeholder="Enter password (min 8 characters)" disabled={loading}>
			</div>
			<div class="form-group">
				<label for="confirmPassword">Confirm Password</label>
				<input id="confirmPassword" type="password" bind:value={confirmPassword} placeholder="Confirm password" disabled={loading}>
			</div>
			<button type="submit" disabled={loading} class="btn-submit">{loading ? 'Creating Account...' : 'Sign Up'}</button>
		</form>
		<p class="auth-link">Already have an account? <a href="/login">Login here</a></p>
	</div>
</div>
<style>
	.signup-container {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 2rem;
	}
	.signup-form {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
		width: 100%;
		max-width: 500px;
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
	.form-row {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 1rem;
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