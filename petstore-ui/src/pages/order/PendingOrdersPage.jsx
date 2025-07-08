import React, { useState, useEffect } from "react";
import { Clock } from "lucide-react";
import { apiService } from "../../services/api";
import ActionCard from "../../components/order/ActionCard";
import OrdersTable from "../../components/order/OrdersTable";
import OrderDetailsModal from "../../components/order/OrderDetailsModal";
import "../../styles/pages/order/PendingOrdersPage.css";
import PaginationComponent from "../../components/common/PaginationComponent";

const PendingOrdersPage = ({ hideHeader = false }) => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  


  useEffect(() => {
    fetchPendingOrders();
  }, [currentPage]);

  const fetchPendingOrders = async () => {
    try {
      setLoading(true);
      console.log("---> entered the fetchPendingOrder");
      const data = await apiService.getOrdersByStatus("processing", currentPage, 5);
      
      setOrders(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Error fetching pending orders:", error);
      setOrders([]); // Set empty array on error
    } finally {
      setLoading(false);
    }
  };

  const updateOrderStatus = async (orderId, newStatus) => {
    // console.log(`Updating order ${orderId} to ${newStatus}`);
    // setOrders((prevOrders) =>
    //   prevOrders.filter((order) => order.id !== orderId)
    // );
    // setSelectedOrder(null);
    try {
      await apiService.updateOrderStatus(orderId, { status: newStatus });
      console.log(`Order ${orderId} successfully updated to ${newStatus}`);
      fetchPendingOrders(); // Re-fetch orders after successful update
      setSelectedOrder(null); // Close the modal
    } catch (error) {
      console.error(`Error updating order ${orderId} status to ${newStatus}:`, error);
      alert(`Failed to update order status: ${error.message}`);
    }
  };

  const viewOrderDetails = (order) => {
    setSelectedOrder(order);
  };

  if (loading) {
    return <div className="loading-state">Loading pending orders...</div>;
  }

  const content = (
    <>
      {/* Action Card Section */}
      {!hideHeader && (
        <div className="action-cards-section">
          <ActionCard
            icon={Clock}
            title="Pending Orders"
            description="Review and approve orders waiting for your attention."
            iconBgClass="icon-bg-amber"
            iconColor="#f59e0b"
          />
        </div>
      )}

      {/* Orders Table Section */}
      <div className="orders-table-section">
        <h1 className="page-title">Pending Orders</h1>
        <p className="page-description">
          Total: {orders.length} pending orders currently displayed
        </p>
      </div>

      <OrdersTable
        orders={orders}
        onViewDetails={viewOrderDetails}
        onUpdateStatus={updateOrderStatus}
        showActions={true}
      />

      {/* Pagination */}
      {totalPages > 1 && (
        <PaginationComponent
          count={totalPages}
          page={currentPage + 1}
          onChange={(event, value) => setCurrentPage(value - 1)}
        />
      )}


      {/* Order Details Modal */}
      <OrderDetailsModal
        order={selectedOrder}
        onClose={() => setSelectedOrder(null)}
        onUpdateStatus={updateOrderStatus}
      />
    </>
  );

  return hideHeader ? content : (
    <div className="pending-orders-page">
      {content}
    </div>
  );
};

export default PendingOrdersPage;