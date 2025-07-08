import React, { useState, useEffect } from 'react';
import { useParams, Link } from "react-router-dom";
import { apiService } from "../../services/api";
import Header from "../../components/layout/Header";
import { useAuth } from "../../contexts/AuthContext";

const StatusToast = ({ isVisible, onClose, productName, quantity }) => {
  useEffect(() => {
    if (isVisible) {
      const timer = setTimeout(() => {
        onClose();
      }, 2500); // Auto-close after 2.5 seconds
      
      return () => clearTimeout(timer);
    }
  }, [isVisible, onClose]);

  if (!isVisible) return null;

  return (
    <div className="toast-overlay">
      <div className="status-toast">
        <div className="status-icon">✓</div>
        <div className="status-message">
          <span className="status-text">Added to cart</span>
          <span className="status-details">{quantity}× {productName}</span>
        </div>
      </div>
    </div>
  );
};

// Updated ProductsByCategory Component with Real API Integration
const ProductsByCategory = () => {
  const { categoryId } = useParams();
  const [products, setProducts] = useState([]);
  const [category, setCategory] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { isAuthenticated, isAdmin } = useAuth();
  const [quantities, setQuantities] = useState({});
  
  // Toast state
  const [toastVisible, setToastVisible] = useState(false);
  const [toastData, setToastData] = useState({});

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
      const product = products.find(p => p.id === productId);
      
      // Call your actual API
      await apiService.addToCart(productId, quantity);
      
      // Show minimalistic toast notification
      setToastData({
        productName: product.name,
        quantity: quantity
      });
      setToastVisible(true);
      
    } catch (err) {
      console.error("Failed to add product to cart:", err);
      // You could also create an error toast here
      alert("Failed to add product to cart. Please try again.");
    }
  };

  const handleCloseToast = () => {
    setToastVisible(false);
  };

  return (

    <><Header
          isAuthenticated={isAuthenticated()}
          isAdmin={isAdmin()}
          variant="dark" />
          
          <div >
              <section className="products-section">
                  <h2> {category?.name || "Products"} </h2>
                  {loading && <p className="loading">Loading products...</p>}
                  {error && <p className="error">{error}</p>}

                  <div className="products-grid">
                      {products.map((product) => (
                          <div key={product.id} className="product-item">
                              <div className="product-image">
                                  <img
                                      src={product.imageUrl}
                                      alt={product.name}
                                     />
                              </div>
                              <div className="product-info">
                                  <h3 >
                                      {product.name}
                                  </h3>
                                  <p className="product-price" >
                                      ${product.retailPrice || product.price}
                                  </p>
                                  <p className="product-description" style={{ margin: '0.5rem 0 1rem', color: '#666', fontSize: '0.9rem' }}>
                                      {product.description}
                                  </p>

                                  <div className="product-actions">
                                      <QuantitySelector
                                          initialValue={quantities[product.id] || 1}
                                          onChange={(newQuantity) => handleQuantityChange(product.id, newQuantity)}
                                          max={99} />
                                      <button
                                          className="add-to-cart-btn"
                                          onClick={() => handleAddToCart(product.id)}
                                      >
                                          Add to Cart
                                      </button>
                                  </div>
                              </div>
                          </div>
                      ))}
                  </div>
              </section>

              {/* Minimalistic Status Toast */}
              <StatusToast
                  isVisible={toastVisible}
                  onClose={handleCloseToast}
                  productName={toastData.productName}
                  quantity={toastData.quantity} />

              
          </div></>
  );
};

// Quantity Selector Component (unchanged)
const QuantitySelector = ({ initialValue = 1, max = 99, onChange }) => {
  const [quantity, setQuantity] = useState(initialValue);

  const decreaseValue = () => {
    if (quantity > 1) {
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
    let value = parseInt(e.target.value) || 1;
    value = Math.min(Math.max(1, value), max);
    setQuantity(value);
    onChange && onChange(value);
  };

  return (
    <div className="quantity">
      <button
        className="minus"
        onClick={decreaseValue}
        disabled={quantity <= 1}
      >
        -
      </button>
      <input
        type="number"
        className="input-box"
        value={quantity}
        onChange={handleInputChange}
        min="1"
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