<script lang="ts">
	import '../app.css';
	import Navbar from '$lib/components/Navbar.svelte';
	import { authStore } from '$lib/stores';
	import { onMount } from 'svelte';
	let { children } = $props();
	onMount(() => {
		// Check if user is logged in on app load
		const token = typeof window !== 'undefined' ? localStorage.getItem('jwt_token') : null;
		const userStr = typeof window !== 'undefined' ? localStorage.getItem('user') : null;
		if (token && userStr) {
			try {
				const user = JSON.parse(userStr);
				authStore.setUser(user, token);
			} catch (e) {
				console.error('Failed to restore user session:', e);
			}
		}
	});
</script>
<Navbar/>
<main class="container">
	{@render children()}
</main>
<style>
	:global(body) {
		margin: 0;
		padding: 0;
		font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
		background-color: #f5f5f5;
	}
	:global(html) {
		scroll-behavior: smooth;
	}
	.container {
		max-width: 1200px;
		margin: 2rem auto;
		padding: 0 1rem;
	}
</style>