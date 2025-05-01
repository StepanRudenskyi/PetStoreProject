import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { apiService } from "../../services/api";
import Header from "../../components/layout/Header";
import { formatCurrency } from "../../utils/helpers";
import { useAuth } from "../../contexts/AuthContext";
import "../../styles/checkout.css";

const CheckoutPage = () => {
  const [checkoutData, setCheckoutData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [paymentMethod, setPaymentMethod] = useState("CASH");
  const { isAuthenticated, isAdmin } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCheckoutDetails = async () => {
      try {
        setLoading(true);
        const data = await apiService.getCheckoutData();
        setCheckoutData(data);
        setError(null);
      } catch (err) {
        console.error("Failed to fetch checkout data:", err);
        setError("Failed to load checkout information.");
      } finally {
        setLoading(false);
      }
    };

    fetchCheckoutDetails();
  }, []);

  const handlePaymentMethodChange = (event) => {
    setPaymentMethod(event.target.value);
  };

  const handleProcessCheckout = async () => {
    try {
      setLoading(true);
      const orderData = await apiService.processCheckout(paymentMethod);
      console.log("Checkout successful:", orderData);
      // Optionally navigate to an order confirmation page
      navigate(`/order-confirmation/${orderData.orderId}`);
    } catch (err) {
      console.error("Failed to process checkout:", err);
      setError("Failed to process your order. Please try again.");
    } finally {
      setLoading(false);
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
        <section className="checkout-section">
          <h2>Checkout</h2>
          <p className="loading">Loading checkout details...</p>
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
        <section className="checkout-section">
          <h2>Checkout</h2>
          <p className="error">{error}</p>
        </section>
      </>
    );
  }

  if (
    !checkoutData ||
    !checkoutData.cartItems ||
    checkoutData.cartItems.length === 0
  ) {
    return (
      <>
        <Header
          isAuthenticated={isAuthenticated()}
          isAdmin={isAdmin()}
          variant="dark"
        />
        <section className="checkout-section">
          <h2>Checkout</h2>
          <p className="empty-cart">Your cart is empty. Nothing to checkout.</p>
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
      <section className="checkout-section">
        <h2>Checkout</h2>
        <div className="checkout-details">
          <h3>Order Summary</h3>
          <ul className="checkout-items">
            {checkoutData.cartItems.map((item) => (
              <li key={item.productId} className="checkout-item">
                <span className="item-name">{item.productName}</span>
                <span className="item-quantity">x {item.quantity}</span>
                <span className="item-total">
                  {formatCurrency(item.totalPrice)}
                </span>
              </li>
            ))}
          </ul>
          <div className="checkout-total">
            <strong>Total:</strong> {formatCurrency(checkoutData.totalPrice)}
          </div>

          <h3>Payment Method</h3>
          <div className="payment-options">
            <label>
              <input
                type="radio"
                value="CASH"
                checked={paymentMethod === "CASH"}
                onChange={handlePaymentMethodChange}
              />
              Cash on Delivery
            </label>
            <label>
              <input
                type="radio"
                value="CREDIT_CARD"
                checked={paymentMethod === "CREDIT_CARD"}
                onChange={handlePaymentMethodChange}
              />
              Credit Card
            </label>
            <label>
              <input
                type="radio"
                value="PAY_PAL"
                checked={paymentMethod === "PAY_PAL"}
                onChange={handlePaymentMethodChange}
              />
              PayPal
            </label>
          </div>

          <button
            className="process-checkout-btn"
            onClick={handleProcessCheckout}
            disabled={loading}
          >
            {loading ? "Processing..." : "Process Order"}
          </button>
        </div>
      </section>
    </>
  );
};

export default CheckoutPage;
