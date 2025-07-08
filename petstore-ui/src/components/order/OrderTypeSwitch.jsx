import React from "react";
import { ShoppingCart, Clock } from "lucide-react";
import "../../styles/components/order/OrderTypeSwitch.css";

const OrderTypeSwitch = ({ activeType, onTypeChange }) => {
  return (
    <div className="order-type-switch">
      <div className="order-type-switch__container">
        <button
          className={`order-type-switch__button ${
            activeType === "all" ? "order-type-switch__button--active" : ""
          }`}
          onClick={() => onTypeChange("all")}
        >
          <ShoppingCart size={18} className="order-type-switch__icon" />
          All Orders
        </button>
        <button
          className={`order-type-switch__button ${
            activeType === "pending" ? "order-type-switch__button--active" : ""
          }`}
          onClick={() => onTypeChange("pending")}
        >
          <Clock size={18} className="order-type-switch__icon" />
          Processing Orders
        </button>
      </div>
    </div>
  );
};

export default OrderTypeSwitch;