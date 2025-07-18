import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import LandingUnauthorized from "./pages/landing/LandingUnauthorized.jsx";
import LandingUser from "./pages/landing/LandingUser.jsx";
import LandingAdmin from "./pages/landing/LandingAdmin";
import LoginPage from "./components/auth/LoginPage";
import LogoutPage from "./components/auth/LogoutPage";
import ProtectedRoute from "./components/routes/ProtectedRoute";
import ProductCategories from "./pages/products/ProductCategories.jsx";
import ProductsByCategory from "./pages/products/ProductsByCategory.jsx";
import Cart from "./pages/cart/Cart.jsx";
import { useAuth } from "./contexts/AuthContext";
import "./styles/landing.css";
import AccountPage from "./pages/user/AccountPage.jsx";
import RegisterPage from "./components/auth/RegisterPage";
import CheckoutPage from "./pages/order/CheckoutPage.jsx";
import AllOrdersPage from "./pages/order/AllOrdersPage.jsx";
import OAuth2RedirectPage from "./components/auth/OAuth2RedirectPage";
import UserManagementPage from "./pages/user/UserManagementPage";
import PendingOrdersPage from "./pages/order/PendingOrdersPage.jsx";

const AppRoutes = () => {
  const { isAuthenticated, isAdmin } = useAuth();

  return (
    <Routes>
      {/* Public routes */}
      <Route
        path="/login"
        element={
          !isAuthenticated() ? (
            <LoginPage />
          ) : isAdmin() ? (
            <Navigate to="/admin" />
          ) : (
            <Navigate to="/user" />
          )
        }
      />
      <Route
        path="/"
        element={
          !isAuthenticated() ? (
            <LandingUnauthorized />
          ) : isAdmin() ? (
            <Navigate to="/admin" />
          ) : (
            <Navigate to="/user" />
          )
        }
      />
      <Route path="/oauth2/redirect" element={<OAuth2RedirectPage />} />
      <Route path="/logout" element={<LogoutPage />} />

      {/* Product routes - accessible to everyone */}
      <Route path="/products/categories" element={<ProductCategories />} />
      <Route
        path="/products/category/:categoryId"
        element={<ProductsByCategory />}
      />
      <Route path="/register" element={<RegisterPage />} />

      {/* Protected user routes */}
      <Route element={<ProtectedRoute requireAdmin={false} />}>
        <Route path="/user" element={<LandingUser />} />
        <Route path="/account" element={<AccountPage />} />
        <Route path="/cart" element={<Cart />} />
        <Route path="/checkout" element={<CheckoutPage />} />
      </Route>

      {/* Protected admin routes */}
      <Route element={<ProtectedRoute requireAdmin={true} />}>
        <Route path="/admin" element={<LandingAdmin />} />
        <Route
          path="/admin/statistics"
          element={<div>Admin Statistics (Coming Soon)</div>}
        />
        <Route path="/admin/users" element={<UserManagementPage />} />
        <Route path="/admin/orders" element={<AllOrdersPage />} />
        <Route path="/admin/orders/pendings" element={<PendingOrdersPage />} />
      </Route>

      {/* Catch-all route */}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

function App() {
  return (
    <Router>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </Router>
  );
}

export default App;
