import React, { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { apiService } from "../../services/api";
import Header from "../../components/layout/Header";
import { formatCurrency } from "../../utils/helpers";
import { useAuth } from "../../contexts/AuthContext";
import "../../styles/products.css";

const ProductsByCategory = () => {
  const { categoryId } = useParams();
  const [products, setProducts] = useState([]);
  const [category, setCategory] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { isAuthenticated, isAdmin } = useAuth();
  const [quantities, setQuantities] = useState({});

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const data = await apiService.getProductsByCategory(categoryId);

        if (Array.isArray(data)) {
          setProducts(data);
          const initialQuantities = {};
          data.forEach((product) => {
            initialQuantities[product.id] = 1;
          });
          setQuantities(initialQuantities);
          if (data.length > 0 && data[0].categoryName) {
            setCategory({ name: data[0].categoryName });
          }
        } else {
          setProducts(data.products || []);
          setCategory(data.category || {});
          const initialQuantities = {};
          (data.products || []).forEach((product) => {
            initialQuantities[product.id] = 1;
          });
          setQuantities(initialQuantities);
        }
        setError(null);
      } catch (err) {
        console.error("Failed to fetch products:", err);
        setError("Failed to load products. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [categoryId]);

  const handleQuantityChange = (productId, newQuantity) => {
    setQuantities((prev) => ({
      ...prev,
      [productId]: newQuantity,
    }));
  };

  const handleAddToCart = async (productId) => {
    try {
      const quantity = quantities[productId] || 1;
      await apiService.addToCart(productId, quantity);
      alert(`${quantity} item(s) added to cart!`);
    } catch (err) {
      console.error("Failed to add product to cart:", err);
      alert("Failed to add product to cart. Please try again.");
    }
  };

  return (
    <>
      <Header
        isAuthenticated={isAuthenticated()}
        isAdmin={isAdmin()}
        variant="dark"
      />

      <section className="products-section">
        <h2>{category?.name || "Products"}</h2>
        {loading && <p className="loading">Loading products...</p>}
        {error && <p className="error">{error}</p>}

        <div className="products-grid">
          {products.map((product) => (
            <div key={product.id} className="product-item">
              <div className="product-image">
                <img
                  src={product.imageUrl || "/images/placeholder-product.png"}
                  alt={product.name}
                />
              </div>
              <div className="product-info">
                <h3>{product.name}</h3>
                <p className="product-price">
                  {formatCurrency(product.retailPrice || product.price)}
                </p>
                <p className="product-description">{product.description}</p>

                {isAuthenticated() && (
                  <div className="product-actions">
                    <QuantitySelector
                      initialValue={quantities[product.id] || 0}
                      onChange={(newQuantity) =>
                        handleQuantityChange(product.id, newQuantity)
                      }
                      max={99}
                    />
                    <button
                      className="add-to-cart-btn"
                      onClick={() => handleAddToCart(product.id)}
                    >
                      Add to Cart
                    </button>
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
        {products.length === 0 && !loading && !error && (
          <p className="no-products">No products found in this category.</p>
        )}
      </section>
    </>
  );
};

const QuantitySelector = ({ initialValue = 0, max = 99, onChange }) => {
  const [quantity, setQuantity] = useState(initialValue);

  const decreaseValue = () => {
    if (quantity > 0) {
      const newValue = quantity - 1;
      setQuantity(newValue);
      onChange && onChange(newValue);
    }
  };

  const increaseValue = () => {
    if (quantity < max) {
      const newValue = quantity + 1;
      setQuantity(newValue);
      onChange && onChange(newValue);
    }
  };

  const handleInputChange = (e) => {
    let value = parseInt(e.target.value) || 0;
    value = Math.min(Math.max(0, value), max);
    setQuantity(value);
    onChange && onChange(value);
  };

  return (
    <div className="quantity">
      <button
        className="minus"
        onClick={decreaseValue}
        disabled={quantity <= 0}
      >
        -
      </button>
      <input
        type="number"
        className="input-box"
        value={quantity}
        onChange={handleInputChange}
        min="0"
        max={max}
      />
      <button
        className="plus"
        onClick={increaseValue}
        disabled={quantity >= max}
      >
        +
      </button>
    </div>
  );
};

export default ProductsByCategory;
