import React, { useState, useEffect } from "react";
import { apiService } from "../../services/api";
import OrdersTable from "../../components/order/OrdersTable";
import OrderDetailsModal from "../../components/order/OrderDetailsModal";
import OrderTypeSwitch from "../../components/order/OrderTypeSwitch";
import "../../styles/pages/order/AllOrdersPage.css";
import AdminHeader from "../../components/layout/AdminHeader";
import PaginationComponent from "../../components/common/PaginationComponent";
import { useSearchParams } from "react-router-dom";
import PendingOrdersPage from "./PendingOrdersPage";

const AllOrdersPage = () => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const [searchParams, setSearchParams] = useSearchParams();

  const status = searchParams.get("status");
  const isPendingView = status === "processing";

  useEffect(() => {
    if (!isPendingView) {
      fetchOrders();
    }
  }, [currentPage, isPendingView]);

  const fetchOrders = async () => {
    try {
      setLoading(true);
      const data = await apiService.getOrders(currentPage, 5);
      setOrders(data.content);
      setTotalPages(data.totalPages);
    } catch (error) {
      console.error("Error fetching orders:", error);
    } finally {
      setLoading(false);
    }
  };

  const viewOrderDetails = (order) => {
    setSelectedOrder(order);
  };

  const handleOrderTypeChange = (orderType) => {
    if (orderType === "pending") {
      setSearchParams({ status: "processing" });
    } else {
      setSearchParams({});
    }
    setCurrentPage(0); // Reset to first page when switching
  };

  // If we're in pending view, render the PendingOrdersPage with the switch
  if (isPendingView) {
    return (
      <div className="admin-dashboard">
        <AdminHeader />
        <div className="all-orders-page">
          <OrderTypeSwitch 
            activeType="pending"
            onTypeChange={handleOrderTypeChange}
          />
          <PendingOrdersPage hideHeader={true} />
        </div>
      </div>
    );
  }

  if (loading) {
    return (
      <div className="admin-dashboard">
        <AdminHeader />
        <div className="loading-state">Loading orders...</div>
      </div>
    );
  }

  return (
    <div className="admin-dashboard">
      <AdminHeader />
      <div className="all-orders-page">

        {/* Order Type Switch */}
        <OrderTypeSwitch 
          activeType="all"
          onTypeChange={handleOrderTypeChange}
        />

        {/* Orders Table Section */}
        <div className="orders-table-section">
          <h1 className="page-title">All Orders</h1>
          <p className="page-description">
            Total: {orders.length} orders currently displayed
          </p>
        </div>

        <OrdersTable
          orders={orders}
          onViewDetails={viewOrderDetails}
          showActions={false}
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
        />
      </div>
    </div>
  );
};

export default AllOrdersPage;