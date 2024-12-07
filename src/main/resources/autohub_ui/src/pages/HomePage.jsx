import React, { useState, useEffect } from 'react';
import Header from '../components/Header';
import Filters from '../components/Filters';
import CarCard from '../components/CarCard';

const HomePage = () => {
    const [cars, setCars] = useState([]);
    const [filteredCars, setFilteredCars] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [filters, setFilters] = useState({ year: '', price: '' });

    // Пример статических данных о машинах
    useEffect(() => {
        const fetchCars = () => {
            const fetchedCars = [
                { id: 1, model: 'Toyota Camry', year: 2023, price: 1500000, imageUrl: '/path/to/image1.jpg' },
                { id: 2, model: 'Honda Accord', year: 2022, price: 1300000, imageUrl: '/path/to/image2.jpg' },
                { id: 3, model: 'BMW 3 Series', year: 2021, price: 2000000, imageUrl: '/path/to/image3.jpg' },
                // Добавьте реальные данные или сделайте запрос к API
            ];
            setCars(fetchedCars);
            setFilteredCars(fetchedCars);
        };

        fetchCars();
    }, []);

    useEffect(() => {
        // Применяем фильтры
        const filtered = cars.filter((car) => {
            const matchesSearch = car.model.toLowerCase().includes(searchTerm.toLowerCase());
            const matchesYear = filters.year ? car.year === parseInt(filters.year) : true;
            const matchesPrice = filters.price ? car.price <= parseInt(filters.price) : true;
            return matchesSearch && matchesYear && matchesPrice;
        });

        setFilteredCars(filtered);
    }, [searchTerm, filters, cars]);

    const handleSearch = (searchTerm) => {
        setSearchTerm(searchTerm);
    };

    const handleFilterChange = (newFilters) => {
        setFilters(newFilters);
    };

    return (
        <div>
            <Header onSearch={handleSearch} />
            <main className="container mx-auto p-4">
                <h2 className="text-3xl font-bold mb-4">Автомобили на AutoHub</h2>
                <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                    {filteredCars.map((car) => (
                        <CarCard key={car.id} car={car} />
                    ))}
                </div>
                <Filters onFilterChange={handleFilterChange} />
            </main>
        </div>
    );
};

export default HomePage;