import React from 'react';

const CarCard = ({ car }) => {
    return (
        <div className="border p-4 rounded shadow-lg hover:shadow-xl transition-all">
            <img src={car.imageUrl} alt={car.model} className="w-full h-40 object-cover rounded" />
            <h3 className="text-xl mt-2">{car.model}</h3>
            <p className="text-gray-500">{car.year}</p>
            <p className="text-lg font-bold">{car.price} ₽</p>
        </div>
    );
};

export default CarCard;