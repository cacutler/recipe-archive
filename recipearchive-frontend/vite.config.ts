import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [sveltekit()],
	server: {
		proxy: {
			'/auth': {
				target: 'http://localhost:8080',
				changeOrigin: true,
				rewrite: (path) => path
			},
			'/users': {
				target: 'http://localhost:8080',
				changeOrigin: true,
				rewrite: (path) => path
			},
			'/recipes': {
				target: 'http://localhost:8080',
				changeOrigin: true,
				rewrite: (path) => path
			}
		}
	}
});
