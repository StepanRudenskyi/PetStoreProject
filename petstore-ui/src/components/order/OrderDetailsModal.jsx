import React from "react";
import "../../styles/components/order/OrderDetailsModal.css";

const OrderDetailsModal = ({ order, onClose, onUpdateStatus }) => {
  if (!order) return null;

  const getStatusColor = (status) => {
    switch (status) {
      case 'PROCESSING': return '#f59e0b';
      case 'COMPLETED': return '#10b981';
      case 'SHIPPED': return '#3b82f6';
      case 'CANCELLED': return '#ef4444';
      default: return '#6b7280';
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2 className="modal-title">Order Details - #{order.id}</h2>
          <button className="close-btn" onClick={onClose}>
            &times;
          </button>
        </div>
        <div className="modal-body">
          <p className="modal-detail">
            <strong className="modal-detail__label">Customer:</strong>{" "}
            {order.customerName}
          </p>
          <p className="modal-detail">
            <strong className="modal-detail__label">Status: </strong>
            <span
              className="status-badge" 
                style={{ backgroundColor: getStatusColor(order.status) }}
            >
              {order.status}
            </span>
          </p>
          <p className="modal-detail">
            <strong className="modal-detail__label">Total Amount:</strong> $
            {order.totalAmount.toFixed(2)}
          </p>

          <div className="order-lines">
            <h4 className="order-lines__title">Order Items:</h4>
            {order.orderLines.length === 0 ? (
              <p className="order-lines__empty-text">No items in this order.</p>
            ) : (
              order.orderLines.map((line, index) => (
                <div key={index} className="order-line-item">
                  <span className="order-line-item__name">
                    {line.productName} x{line.quantity}
                  </span>
                  <span className="order-line-item__price">
                    ${line.price.toFixed(2)}
                  </span>
                </div>
              ))
            )}
          </div>

          {onUpdateStatus && order.status === "PROCESSING" && (
            <div className="modal-actions">
              <button
                className="modal-action-btn modal-action-btn--approve"
                onClick={() => {
                  onUpdateStatus(order.id, "COMPLETED");
                  onClose();
                }}
              >
                Approve Order
              </button>
              <button
                className="modal-action-btn modal-action-btn--reject"
                onClick={() => {
                  onUpdateStatus(order.id, "CANCELLED");
                  onClose();
                }}
              >
                Reject Order
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default OrderDetailsModal;
