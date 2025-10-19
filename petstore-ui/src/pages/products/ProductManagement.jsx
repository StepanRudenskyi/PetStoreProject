import React, { useState, useEffect } from "react";
import { 
  Plus, 
  Search, 
  Edit, 
  Trash2, 
  Package, 
  AlertCircle,
  CheckCircle,
  X
} from "lucide-react";
import AdminHeader from "../../components/layout/AdminHeader";
import ProductForm from "../../components/admin/ProductForm.jsx";
import { apiService } from "../../services/api";
import styles from  "./ProductManagement.module.css";
import clsx from "clsx";


const ProductManagement = () => {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [selectedCategory, setSelectedCategory] = useState("all");
  const [showForm, setShowForm] = useState(false);
  const [editingProduct, setEditingProduct] = useState(null);
  const [notification, setNotification] = useState(null);

  useEffect(() => {
    fetchInitialData();
  }, []);

  const fetchInitialData = async () => {
    try {
      setLoading(true);
      const categoriesData = await apiService.getProductCategories();
      setCategories(categoriesData);
      
      // Fetch products from all categories
      if (categoriesData.length > 0) {
        const allProducts = [];
        for (const category of categoriesData) {
          const categoryProducts = await apiService.getProductsByCategory(category.id);
          allProducts.push(...categoryProducts);
        }
        setProducts(allProducts);
      }
      setLoading(false);
    } catch (err) {
      setError("Failed to load data. Please try again.");
      setLoading(false);
      console.error(err);
    }
  };

  const showNotification = (message, type = "success") => {
    setNotification({ message, type });
    setTimeout(() => setNotification(null), 3000);
  };

  const handleCreateProduct = () => {
    setEditingProduct(null);
    setShowForm(true);
  };

  const handleEditProduct = (product) => {
    setEditingProduct(product);
    setShowForm(true);
  };

  const handleDeleteProduct = async (productId) => {
    if (!window.confirm("Are you sure you want to delete this product?")) {
      return;
    }

    try {
      await apiService.products.delete(productId);
      setProducts(products.filter(p => p.id !== productId));
      showNotification("Product deleted successfully", "success");
    } catch (err) {
      showNotification("Failed to delete product", "error");
      console.error(err);
    }
  };

  const handleFormSubmit = async (productData) => {
    try {
      if (editingProduct) {
        // Update existing product
        const updated = await apiService.products.update(editingProduct.id, productData.product);
        setProducts(products.map(p => p.id === editingProduct.id ? updated : p));
        showNotification("Product updated successfully", "success");
      } else {
        // Create new product with inventory
        console.log("pre-call product data. image url: ", productData.product.imageUrl)
        const created = await apiService.products.createWithInventory(productData);
        setProducts([...products, created.product]);
        showNotification("Product created successfully", "success");
      }
      setShowForm(false);
      setEditingProduct(null);
    } catch (err) {
      showNotification("Failed to save product", "error");
      console.error(err);
    }
  };

  const filteredProducts = products.filter(product => {
    const matchesSearch = product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         product.description?.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = selectedCategory === "all" || product.categoryName === selectedCategory;
    return matchesSearch && matchesCategory;
  });

  if (loading) {
    return (
      <div className={styles.adminDashboard}>
        <AdminHeader />
        <div className={styles.loadingContainer}>
          <div className={styles.loadingSpinner}></div>
          <p>Loading products...</p>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.adminDashboard}>
      <AdminHeader />
      <main className={styles.adminMain}>
        <div className={styles.adminContainer}>
          {notification && (
            <div className={clsx(styles.notification, styles[notification.type])}>
              {notification.type === "success" ? (
                <CheckCircle size={20} />
              ) : (
                <AlertCircle size={20} />
              )}
              <span>{notification.message}</span>
              <button onClick={() => setNotification(null)}>
                <X size={16} />
              </button>
            </div>
          )}

          <div className={styles.pageHeader}>
            <div className={styles.headerContent}>
              <h1>Product Management</h1>
              <p>Manage your product catalog and inventory</p>
            </div>
            <button className={styles.btnPrimary} onClick={handleCreateProduct}>
              <Plus size={20} />
              Add New Product
            </button>
          </div>

          {error && (
            <div className={styles.errorMessage}>
              <AlertCircle size={20} />
              <span>{error}</span>
            </div>
          )}

          <div className={styles.filtersSection}>
            <div className={styles.searchBox}>
              <Search size={20} />
              <input
                type="text"
                placeholder="Search products..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
              />
            </div>
            <select
              className={styles.categoryFilter}
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <option value="all">All Categories</option>
              {categories.map(cat => (
                <option key={cat.id} value={cat.name}>
                  {cat.name}
                </option>
              ))}
            </select>
          </div>

          <div className={styles.productsGrid}>
            {filteredProducts.length === 0 ? (
              <div className={styles.emptyState}>
                <Package size={48} />
                <h3>No products found</h3>
                <p>Try adjusting your filters or add a new product</p>
              </div>
            ) : (
              filteredProducts.map(product => (
                <div key={product.id} className={styles.productCard}>
                  <div className={styles.productImage}>
                    <img src={product.imageUrl} alt={product.name} />
                  </div>
                  <div className={styles.productInfo}>
                    <h3>{product.name}</h3>
                    <p className={styles.productCategory}>{product.categoryName}</p>
                    <p className={styles.productDescription}>{product.description}</p>
                    <div className={styles.productPrice}>
                      ${product.retailPrice.toFixed(2)}
                    </div>
                  </div>
                  <div className={styles.productActions}>
                    <button 
                      className={`${styles.btnIcon} ${styles.btnEdit}`}
                      onClick={() => handleEditProduct(product)}
                      title="Edit product"
                    >
                      <Edit size={18} />
                    </button>
                    <button 
                      className={`${styles.btnIcon} ${styles.btnDelete}`}
                      onClick={() => handleDeleteProduct(product.id)}
                      title="Delete product"
                    >
                      <Trash2 size={18} />
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </main>

      {showForm && (
        <ProductForm
          product={editingProduct}
          categories={categories}
          onSubmit={handleFormSubmit}
          onClose={() => {
            setShowForm(false);
            setEditingProduct(null);
          }}
        />
      )}
    </div>
  );
};

export default ProductManagement;