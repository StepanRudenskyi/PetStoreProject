import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { apiService } from "../../services/api";
import Header from "../../components/layout/Header";
import "../../styles/products.css";
import { useAuth } from "../../contexts/AuthContext";

const ProductCategories = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { isAuthenticated, isAdmin } = useAuth();

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        setLoading(true);
        const data = await apiService.getProductCategories();
        setCategories(data);
        setError(null);
      } catch (err) {
        console.error("Failed to fetch categories:", err);
        setError("Failed to load product categories. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, []);

  return (
    <>
      <Header
        isAuthenticated={isAuthenticated()}
        isAdmin={isAdmin()}
        variant="dark"
      />

      <section className="products-section">
        <h2>Product Categories</h2>
        {loading && <p className="loading">Loading categories...</p>}
        {error && <p className="error">{error}</p>}

        <div className="category-grid">
          {categories.map((category) => (
            <div key={category.id} className="category-item">
              <Link to={`/products/category/${category.id}`}>
                <img src={category.imageUrl} alt={category.name} />
                <p>{category.name}</p>
              </Link>
            </div>
          ))}
        </div>
      </section>
    </>
  );
};

export default ProductCategories;
