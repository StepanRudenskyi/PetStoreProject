import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom"; // Import useNavigate
import { apiService } from "../../services/api";
import Header from "../../components/layout/Header";
import { formatCurrency } from "../../utils/helpers";
import { useAuth } from "../../contexts/AuthContext";
import "../../styles/cart.css";

const CartPage = () => {
  const [cartData, setCartData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { isAuthenticated, isAdmin } = useAuth();
  const navigate = useNavigate(); // Initialize useNavigate

  const fetchCart = async () => {
    try {
      setLoading(true);
      const data = await apiService.getCart();
      setCartData(data);
      setError(null);
    } catch (err) {
      console.error("Failed to fetch cart:", err);
      setError("Failed to load cart. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCart();
  }, []);

  const handleRemoveItem = async (productId) => {
    try {
      await apiService.removeFromCart(productId);
      // After successful removal, refresh the cart data
      fetchCart();
    } catch (err) {
      console.error(`Failed to remove product ${productId}:`, err);
      setError(`Failed to remove item. Please try again.`);
    }
  };

  const handleProceedToCheckout = async () => {
    try {
      // The API call to /checkout will happen on the CheckoutPage.
      navigate("/checkout");
    } catch (error) {
      console.error("Error navigating to checkout:", error);
      setError("Failed to proceed to checkout.");
    }
  };

  if (loading) {
    return (
      <>
        <Header
          isAuthenticated={isAuthenticated()}
          isAdmin={isAdmin()}
          variant="dark"
        />
        <section className="cart-section">
          <h2>Your Cart</h2>
          <p className="loading">Loading cart...</p>
        </section>
      </>
    );
  }

  if (error) {
    return (
      <>
        <Header
          isAuthenticated={isAuthenticated()}
          isAdmin={isAdmin()}
          variant="dark"
        />
        <section className="cart-section">
          <h2>Your Cart</h2>
          <p className="error">{error}</p>
          <Link to="/products" className="back-to-shop">
            Back to Shopping
          </Link>
        </section>
      </>
    );
  }

  if (!cartData || !cartData.cartItems || cartData.cartItems.length === 0) {
    return (
      <>
        <Header
          isAuthenticated={isAuthenticated()}
          isAdmin={isAdmin()}
          variant="dark"
        />
        <section className="cart-section">
          <h2>Your Cart</h2>
          <p className="empty-cart">Your cart is empty.</p>
          <Link to="/products/categories" className="back-to-shop">
            Back to Shopping
          </Link>
        </section>
      </>
    );
  }

  return (
    <>
      <Header
        isAuthenticated={isAuthenticated()}
        isAdmin={isAdmin()}
        variant="dark"
      />
      <section className="cart-section">
        <h2>Your Cart</h2>
        <div className="cart-items">
          {cartData.cartItems.map((item) => (
            <div key={item.productId} className="cart-item">
              <div className="item-info">
                <h3 className="item-name">{item.productName}</h3>
              </div>
              <div className="item-details">
                <p className="item-price-quantity">
                  {formatCurrency(item.totalPrice / item.quantity)} x{" "}
                  {item.quantity}
                </p>
                <p className="item-total-price">
                  | {formatCurrency(item.totalPrice)}
                </p>
              </div>
              <div className="item-actions">
                <button
                  className="remove-item-btn"
                  onClick={() => handleRemoveItem(item.productId)}
                >
                  Remove
                </button>
              </div>
            </div>
          ))}
        </div>
        <div className="cart-summary">
          <h3 className="total-price">
            Total: {formatCurrency(cartData.totalPrice)}
          </h3>
          <button className="checkout-btn" onClick={handleProceedToCheckout}>
            Proceed to Checkout
          </button>
          <Link to="/products/categories" className="continue-shopping">
            Continue Shopping
          </Link>
        </div>
      </section>
    </>
  );
};

export default CartPage;
