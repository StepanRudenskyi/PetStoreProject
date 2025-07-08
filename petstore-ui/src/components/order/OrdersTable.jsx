import React from "react";
import { Eye, Edit } from "lucide-react";
import "../../styles/components/order/OrdersTable.css";

const OrdersTable = ({
  orders,
  onViewDetails,
  onUpdateStatus,
  showActions,
}) => {
  if (orders.length === 0) {
    return (
      <div className="orders-table-empty-state">
        <Eye size={48} className="orders-table-empty-state__icon" />
        <h3 className="orders-table-empty-state__title">No Orders Found</h3>
        <p className="orders-table-empty-state__description">
          It looks like there are no orders to display at the moment.
        </p>
      </div>
    );
  }

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
    <div className="orders-table-container">
      <table className="orders-table">
        <thead className="orders-table__head">
          <tr>
            <th className="orders-table__header">Order ID</th>
            <th className="orders-table__header">Customer</th>
            <th className="orders-table__header">Status</th>
            <th className="orders-table__header">Total Amount</th>
            <th className="orders-table__header">Actions</th>
          </tr>
        </thead>
        <tbody className="orders-table__body">
          {orders.map((order) => (
            <tr key={order.id} className="orders-table__row">
              <td className="orders-table__data">#{order.id}</td>
              <td className="orders-table__data">{order.customerName}</td>
              <td className="orders-table__data">
                <span
                  className="status-badge" 
                    style={{ backgroundColor: getStatusColor(order.status) }}
                >
                  {order.status}
                </span>
              </td>
              <td className="orders-table__data">
                ${order.totalAmount.toFixed(2)}
              </td>
              <td className="orders-table__data orders-table__actions">
                <button
                  className="action-btn action-btn--view"
                  onClick={() => onViewDetails(order)}
                >
                  <Eye size={16} className="action-btn__icon" />
                  View
                </button>
                {showActions && (
                  <>
                    <button
                      className="action-btn action-btn--approve"
                      onClick={() => onUpdateStatus(order.id, "COMPLETED")}
                    >
                      Approve
                    </button>
                    <button
                      className="action-btn action-btn--reject"
                      onClick={() => onUpdateStatus(order.id, "CANCELLED")}
                    >
                      Reject
                    </button>
                  </>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default OrdersTable;
