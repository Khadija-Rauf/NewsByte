const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  devServer: {
    proxy: {
      '/newsbyte': {
        target: 'http://localhost:9081', // Replace with your backend API URL
        changeOrigin: true,
      },
    },
  },
});
