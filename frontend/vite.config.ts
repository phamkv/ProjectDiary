import { defineConfig } from 'vite'
import { svelte } from '@sveltejs/vite-plugin-svelte'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [svelte()],
  server: {
    proxy: {
      // string shorthand: http://localhost:5173/api -> http://localhost:8080/api
      '/api': 'http://localhost:8080',
      '/auth': 'http://localhost:8080',
    },
  },
})
