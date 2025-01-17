// import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'

// // https://vitejs.dev/config/
// export default defineConfig({
//   plugins: [vue()],
// })


import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'url'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)), // '@' points to 'src'
      '@assets': fileURLToPath(new URL('./src/assets', import.meta.url)) // '@assets' points to 'src/assets'
    }
  },
  server: {
    host: '0.0.0.0',
    port: 5173,
    strictPort: true
  }
})
