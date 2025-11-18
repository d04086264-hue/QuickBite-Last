import React, { useState, useEffect } from 'react';
import './DisplayMenu.css';
import FoodItem from '../FoodItem/FoodItem';
import api from '../../../config/api';

const DisplayMenu = ({ category }) => {
  const [foodList, setFoodList] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setCurrentPage(1); 
  }, [category]);

  useEffect(() => {
    fetchProducts();
  }, [currentPage, category]);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      let res;
      
      
      if (!category || category === 'all') {
        res = await api.get(`/app2/api/v1/food/active?page=${currentPage}`);
      } else {
        res = await api.get(`/app2/api/v1/food/category?category=${encodeURIComponent(category)}&page=${currentPage}`);
      }
      
      setFoodList(res.data.content || []);
      setTotalPages(res.data.totalPages || 1);
    } catch (err) {
      setFoodList([]);
      setTotalPages(1);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className='food-display' id='food-display'>
        <h2>Loading menu...</h2>
      </div>
    );
  }

  if (foodList.length === 0) {
    return (
      <div className='food-display' id='food-display'>
        <h2>No food items available</h2>
        <p>Please check back later or contact support.</p>
      </div>
    );
  }

  const handlePageChange = (page) => {
    setCurrentPage(page);
    document.getElementById('food-display')?.scrollIntoView({ behavior: 'smooth' });
  };

  return (
    <div className='food-display' id='food-display'>
      <h2>The best dishes from our restaurant</h2>
      <div className='food-display-list'>
        {foodList.map((item) => (
          <FoodItem
            key={item.id}
            id={item.id}
            name={item.name}
            price={item.price}
            description={item.description}
            image={item.img}
            avgRating={item.avgRating}
            category={item.category}
          />
        ))}
      </div>

      <div className="pagination">
        <button
          disabled={currentPage === 1}
          onClick={() => handlePageChange(currentPage - 1)}
        >
          Prev
        </button>
        {Array.from({ length: totalPages }, (_, i) => i + 1).map((pageNum) => (
          <button
            key={pageNum}
            className={currentPage === pageNum ? 'active' : ''}
            onClick={() => handlePageChange(pageNum)}
          >
            {pageNum}
          </button>
        ))}
        <button
          disabled={currentPage === totalPages}
          onClick={() => handlePageChange(currentPage + 1)}
        >
          Next
        </button>
      </div>
    </div>
  );
};

export default DisplayMenu;