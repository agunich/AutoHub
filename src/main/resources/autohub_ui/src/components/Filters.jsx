import React, { useState } from 'react';

const Filters = ({ onFilterChange }) => {
    const [selectedYear, setSelectedYear] = useState('');
    const [selectedPrice, setSelectedPrice] = useState('');

    const handleFilterChange = () => {
        onFilterChange({ year: selectedYear, price: selectedPrice });
    };

    return (
        <div className="bg-gray-100 p-4 mt-4 rounded shadow-md">
            <h3 className="text-xl font-bold mb-4">Фильтры</h3>
            <div className="space-y-4">
                <div>
                    <label className="block">Год выпуска</label>
                    <select
                        value={selectedYear}
                        onChange={(e) => setSelectedYear(e.target.value)}
                        className="p-2 rounded"
                    >
                        <option value="">Выберите год</option>
                        <option value="2023">2023</option>
                        <option value="2022">2022</option>
                        <option value="2021">2021</option>
                    </select>
                </div>
                <div>
                    <label className="block">Цена</label>
                    <select
                        value={selectedPrice}
                        onChange={(e) => setSelectedPrice(e.target.value)}
                        className="p-2 rounded"
                    >
                        <option value="">Выберите цену</option>
                        <option value="500000">до 500,000 ₽</option>
                        <option value="1000000">до 1,000,000 ₽</option>
                        <option value="2000000">до 2,000,000 ₽</option>
                    </select>
                </div>
                <button
                    onClick={handleFilterChange}
                    className="bg-blue-600 text-white py-2 px-4 rounded mt-4 hover:bg-blue-700"
                >
                    Применить фильтры
                </button>
            </div>
        </div>
    );
};

export default Filters;