<script lang="ts">
	import { apiClient } from '$lib/api';
	import { authStore } from '$lib/stores';
	import type { User } from '$lib/types';
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	let firstName = '';
	let lastName = '';
	let email = '';
	let error = '';
	let successMessage = '';
	let loading = false;
	let editMode = false;
	onMount(async () => {
		if (!$authStore.isAuthenticated || !$authStore.user) {
			await goto('/login');
			return;
		}
		try {// Load full user details
			const user: User = await apiClient.getUser($authStore.user.id);
			firstName = user.firstName;
			lastName = user.lastName;
			email = user.email;
		} catch (err: any) {
			error = 'Failed to load user details';
		}
	});
	async function handleUpdateProfile() {
		if (!firstName || !lastName || !email) {
			error = 'Please fill in all fields';
			return;
		}
		if (!$authStore.user) {
			error = 'User not found';
			return;
		}
		loading = true;
		error = '';
		successMessage = '';
		try {
			const updated: User = await apiClient.updateUser($authStore.user.id, {firstName, lastName, email});
			authStore.updateUser({...updated, username: $authStore.user.username});
			successMessage = 'Profile updated successfully!';
			editMode = false;
		} catch (err: any) {
			error = err.message || 'Failed to update profile';
		} finally {
			loading = false;
		}
	}
	async function handleDeleteAccount() {
		if (!$authStore.user) {
			error = 'User not found';
			return;
		}
		const confirmed = confirm('Are you sure you want to delete your account? This action cannot be undone and all your recipes will be deleted.');
		if (!confirmed) {
			return;
		}
		loading = true;
		error = '';
		try {
			await apiClient.deleteUser($authStore.user.id);
			apiClient.logout();
			authStore.clearAuth();
			localStorage.removeItem('user');
			await goto('/');
		} catch (err: any) {
			error = err.message || 'Failed to delete account';
			loading = false;
		}
	}
	function cancelEdit() {
		if ($authStore.user) {// Reset to current user data
			onMount(() => {});
			editMode = false;
		}
	}
</script>
{#if !$authStore.isAuthenticated}
	<div class="info">Please log in to view your profile</div>
{:else if $authStore.user}
	<div class="profile-container">
		<div class="profile-card">
			<div class="header">
				<h1>My Profile</h1>
				{#if !editMode}
					<button on:click={() => (editMode = true)} class="btn btn-secondary">Edit Profile</button>
				{/if}
			</div>
			{#if error}
				<div class="error-text">{error}</div>
			{/if}
			{#if successMessage}
				<div class="success-text">{successMessage}</div>
			{/if}
			{#if editMode}
				<form on:submit|preventDefault={handleUpdateProfile}>
					<div class="form-row">
						<div class="form-group">
							<label for="firstName">First Name</label>
							<input id="firstName" type="text" bind:value={firstName} placeholder="First name" disabled={loading} required>
						</div>
						<div class="form-group">
							<label for="lastName">Last Name</label>
							<input id="lastName" type="text" bind:value={lastName} placeholder="Last name" disabled={loading} required>
						</div>
					</div>
					<div class="form-group">
						<label for="email">Email</label>
						<input id="email" type="email" bind:value={email} placeholder="Email address" disabled={loading} required>
					</div>
					<div class="form-actions">
						<button type="submit" disabled={loading} class="btn btn-primary">{loading ? 'Updating...' : 'Save Changes'}</button>
						<button type="button" on:click={cancelEdit} disabled={loading} class="btn btn-secondary">Cancel</button>
					</div>
				</form>
			{:else}
				<div class="profile-info">
					<div class="info-item">
						<span class="label">Username:</span>
						<span class="value">{$authStore.user.username}</span>
					</div>
					<div class="info-item">
						<span class="label">First Name:</span>
						<span class="value">{firstName || 'N/A'}</span>
					</div>
					<div class="info-item">
						<span class="label">Last Name:</span>
						<span class="value">{lastName || 'N/A'}</span>
					</div>
					<div class="info-item">
						<span class="label">Email:</span>
						<span class="value">{email || 'N/A'}</span>
					</div>
				</div>
			{/if}
		</div>
		<div class="danger-zone">
			<h2>Danger Zone</h2>
			<p>Once you delete your account, there is no going back. Please be certain.</p>
			<button on:click={handleDeleteAccount} disabled={loading} class="btn btn-danger-large">Delete Account</button>
		</div>
	</div>
{/if}
<style>
	.profile-container {
		max-width: 600px;
		margin: 0 auto;
	}
	.profile-card {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
		margin-bottom: 2rem;
	}
	.header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 1.5rem;
	}
	h1 {
		color: #333;
		margin: 0;
	}
	h2 {
		color: #333;
		margin-top: 0;
	}
	.error-text {
		color: #d32f2f;
		background-color: #ffebee;
		padding: 0.75rem;
		border-radius: 4px;
		margin-bottom: 1rem;
	}
	.success-text {
		color: #2e7d32;
		background-color: #e8f5e9;
		padding: 0.75rem;
		border-radius: 4px;
		margin-bottom: 1rem;
	}
	.profile-info {
		display: flex;
		flex-direction: column;
		gap: 1.5rem;
	}
	.info-item {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 1rem;
		background: #f5f5f5;
		border-radius: 4px;
	}
	.info-item .label {
		font-weight: bold;
		color: #333;
	}
	.info-item .value {
		color: #666;
	}
	.form-row {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 1rem;
	}
	.form-group {
		margin-bottom: 1.5rem;
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
	.btn-secondary:hover:not(:disabled) {
		background-color: #0b7dda;
	}
	.btn-secondary:disabled {
		background-color: #a5a5a5;
		cursor: not-allowed;
	}
	.danger-zone {
		background: #fff3cd;
		border: 2px solid #ffc107;
		padding: 2rem;
		border-radius: 8px;
	}
	.danger-zone p {
		color: #666;
		margin-bottom: 1.5rem;
	}
	.btn-danger-large {
		width: 100%;
		padding: 1rem;
		background-color: #f44336;
		color: white;
	}
	.btn-danger-large:hover:not(:disabled) {
		background-color: #d32f2f;
	}
	.btn-danger-large:disabled {
		background-color: #a5a5a5;
		cursor: not-allowed;
	}
	.info {
		background: white;
		padding: 2rem;
		border-radius: 8px;
		text-align: center;
		color: #666;
	}
</style>