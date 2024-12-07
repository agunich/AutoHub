import React, { useState } from 'react';

const Header = ({ onSearch }) => {
    const [searchTerm, setSearchTerm] = useState('');

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
        onSearch(e.target.value); // Передаем значение в родительский компонент
    };

    return (
        <header className="bg-blue-600 text-white p-4">
            <div className="container mx-auto flex justify-between items-center">
                <h1 className="text-3xl font-bold">AutoHub</h1>
                <input
                    type="text"
                    value={searchTerm}
                    onChange={handleSearchChange}
                    placeholder="Поиск автомобилей..."
                    className="p-2 rounded-md w-64"
                />
            </div>
        </header>
    );
};

export default Header;