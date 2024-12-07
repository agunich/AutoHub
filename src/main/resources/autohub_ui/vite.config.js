import { defineConfig } from 'vite';

export default defineConfig({
    root: './src', // путь к папке с вашим фронтенд-кодом
    build: {
        outDir: '../dist', // Папка, в которой будет собран ваш проект
    },
    server: {
        proxy: {
            '/api': 'http://localhost:8080', // Прокси-сервер для запросов к бэкенду
        },
    },
});