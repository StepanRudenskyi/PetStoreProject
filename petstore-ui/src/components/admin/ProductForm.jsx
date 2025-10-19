import React, { useState, useEffect } from "react";
import { X, Upload, Image as ImageIcon, AlertCircle } from "lucide-react";
import { apiService } from "../../services/api";
import "./ProductForm.css";

const ProductForm = ({ product, categories, onSubmit, onClose }) => {
  const [formData, setFormData] = useState({
    name: "",
    description: "",
    retailPrice: "",
    imageUrl: "",
    categoryName: "",
    quantity: "",
    wholeSalePrice: "",
    bestBefore: "",
  });

  const [imageFile, setImageFile] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);
  const [uploading, setUploading] = useState(false);
  const [errors, setErrors] = useState({});
  const [isDragging, setIsDragging] = useState(false);

  useEffect(() => {
    if (product) {
      setFormData({
        name: product.name || "",
        description: product.description || "",
        retailPrice: product.retailPrice || "",
        imageUrl: product.imageUrl || "",
        categoryName: product.categoryName || "",
        quantity: "",
        wholeSalePrice: "",
        bestBefore: "",
      });
      setImagePreview(product.imageUrl);
    }
  }, [product]);

const validateForm = () => {
  const newErrors = {};

  if (!formData.name.trim()) {
    newErrors.name = "Product name is required";
  }

  if (!formData.retailPrice || parseFloat(formData.retailPrice) <= 0) {
    newErrors.retailPrice = "Retail price must be greater than zero";
  }

  if (!formData.categoryName) {
    newErrors.categoryName = "Category is required";
  }

  if (!product && !imageFile) {
    newErrors.imageUrl = "Product image is required";
  }

  if (!product) {
    if (!formData.quantity || parseInt(formData.quantity) < 0) {
      newErrors.quantity = "Quantity must be 0 or greater";
    }

    if (!formData.wholeSalePrice || parseFloat(formData.wholeSalePrice) <= 0) {
      newErrors.wholeSalePrice = "Wholesale price must be greater than zero";
    }
  }

  setErrors(newErrors);
  return Object.keys(newErrors).length === 0;
};

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    // Clear error for this field
    if (errors[name]) {
      setErrors(prev => ({ ...prev, [name]: null }));
    }
  };

  const handleImageSelect = (file) => {
    if (file && file.type.startsWith("image/")) {
      setImageFile(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setImagePreview(reader.result);
      };
      reader.readAsDataURL(file);
      if (errors.imageUrl) {
        setErrors(prev => ({ ...prev, imageUrl: null }));
      }
    }
  };

  const handleFileInputChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      handleImageSelect(file);
    }
  };

  const handleDragOver = (e) => {
    e.preventDefault();
    setIsDragging(true);
  };

  const handleDragLeave = (e) => {
    e.preventDefault();
    setIsDragging(false);
  };

  const handleDrop = (e) => {
    e.preventDefault();
    setIsDragging(false);
    const file = e.dataTransfer.files[0];
    if (file) {
      handleImageSelect(file);
    }
  };

  const uploadImage = async () => {
    if (!imageFile) return formData.imageUrl;

    setUploading(true);
    try {
      const response = await apiService.products.uploadImage(imageFile);
      return response.imageUrl;
    } catch (error) {
      console.error("Image upload failed:", error);
      throw new Error("Failed to upload image");
    } finally {
      setUploading(false);
    }
  };

const handleSubmit = async (e) => {
  e.preventDefault();

  if (!validateForm()) {
    return;
  }

  try {
    let imageUrl = formData.imageUrl;

    if (imageFile) {
      const uploadedUrl  = await uploadImage();
      imageUrl = uploadedUrl;
      // setFormData(prev => ({ ...prev, imageUrl }));
    }

    const productDto = {
      name: formData.name,
      description: formData.description,
      retailPrice: parseFloat(formData.retailPrice),
      imageUrl: imageUrl, 
      categoryName: formData.categoryName,
    };
    console.log("image url from dto: ", productDto.imageUrl);

    if (product) {
      productDto.id = product.id;
      await onSubmit({ product: productDto });
    } else {
      const inventoryDto = {
        quantity: parseInt(formData.quantity),
        wholeSalePrice: parseFloat(formData.wholeSalePrice),
        inventoryDate: new Date().toISOString(),
        bestBefore: formData.bestBefore || null,
      };

      await onSubmit({
        product: productDto,
        inventory: inventoryDto,
      });
    }
  } catch (error) {
    setErrors({ submit: "Failed to save product. Please try again." });
    console.error(error);
  }
};




  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <div className="modal-header">
          <h2>{product ? "Edit Product" : "Add New Product"}</h2>
          <button className="btn-close" onClick={onClose}>
            <X size={24} />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="product-form">
          {errors.submit && (
            <div className="error-banner">
              <AlertCircle size={20} />
              <span>{errors.submit}</span>
            </div>
          )}

          {/* Image Upload Section */}
          <div className="form-section">
            <label className="form-label">Product Image</label>
            <div
              className={`image-upload-area ${isDragging ? "dragging" : ""} ${
                errors.imageUrl ? "error" : ""
              }`}
              onDragOver={handleDragOver}
              onDragLeave={handleDragLeave}
              onDrop={handleDrop}
            >
              {imagePreview ? (
                <div className="image-preview">
                  <img src={imagePreview} alt="Preview" />
                  <button
                    type="button"
                    className="btn-change-image"
                    onClick={() => document.getElementById("file-input").click()}
                  >
                    Change Image
                  </button>
                </div>
              ) : (
                <div className="upload-placeholder">
                  <ImageIcon size={48} />
                  <p>Drag and drop an image here</p>
                  <p className="upload-subtext">or</p>
                  <button
                    type="button"
                    className="btn-upload"
                    onClick={() => document.getElementById("file-input").click()}
                  >
                    <Upload size={20} />
                    Browse Files
                  </button>
                </div>
              )}
              <input
                id="file-input"
                type="file"
                accept="image/*"
                onChange={handleFileInputChange}
                style={{ display: "none" }}
              />
            </div>
            {errors.imageUrl && (
              <span className="error-text">{errors.imageUrl}</span>
            )}
          </div>

          {/* Product Details */}
          <div className="form-section">
            <label className="form-label">
              Product Name <span className="required">*</span>
            </label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleInputChange}
              className={errors.name ? "error" : ""}
              placeholder="Enter product name"
            />
            {errors.name && <span className="error-text">{errors.name}</span>}
          </div>

          <div className="form-section">
            <label className="form-label">Description</label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleInputChange}
              rows="3"
              placeholder="Enter product description"
            />
          </div>

          <div className="form-row">
            <div className="form-section">
              <label className="form-label">
                Category <span className="required">*</span>
              </label>
              <select
                name="categoryName"
                value={formData.categoryName}
                onChange={handleInputChange}
                className={errors.categoryName ? "error" : ""}
              >
                <option value="">Select category</option>
                {categories.map((cat) => (
                  <option key={cat.id} value={cat.name}>
                    {cat.name}
                  </option>
                ))}
              </select>
              {errors.categoryName && (
                <span className="error-text">{errors.categoryName}</span>
              )}
            </div>

            <div className="form-section">
              <label className="form-label">
                Retail Price <span className="required">*</span>
              </label>
              <input
                type="number"
                name="retailPrice"
                value={formData.retailPrice}
                onChange={handleInputChange}
                className={errors.retailPrice ? "error" : ""}
                placeholder="0.00"
                step="0.01"
                min="0"
              />
              {errors.retailPrice && (
                <span className="error-text">{errors.retailPrice}</span>
              )}
            </div>
          </div>

          {/* Inventory Section (only for new products) */}
          {!product && (
            <>
              <div className="form-divider">
                <span>Inventory Information</span>
              </div>

              <div className="form-row">
                <div className="form-section">
                  <label className="form-label">
                    Initial Quantity <span className="required">*</span>
                  </label>
                  <input
                    type="number"
                    name="quantity"
                    value={formData.quantity}
                    onChange={handleInputChange}
                    className={errors.quantity ? "error" : ""}
                    placeholder="0"
                    min="0"
                  />
                  {errors.quantity && (
                    <span className="error-text">{errors.quantity}</span>
                  )}
                </div>

                <div className="form-section">
                  <label className="form-label">
                    Wholesale Price <span className="required">*</span>
                  </label>
                  <input
                    type="number"
                    name="wholeSalePrice"
                    value={formData.wholeSalePrice}
                    onChange={handleInputChange}
                    className={errors.wholeSalePrice ? "error" : ""}
                    placeholder="0.00"
                    step="0.01"
                    min="0"
                  />
                  {errors.wholeSalePrice && (
                    <span className="error-text">{errors.wholeSalePrice}</span>
                  )}
                </div>
              </div>

              <div className="form-section">
                <label className="form-label">Best Before Date</label>
                <input
                  type="date"
                  name="bestBefore"
                  value={formData.bestBefore}
                  onChange={handleInputChange}
                />
              </div>
            </>
          )}

          <div className="form-actions">
            <button type="button" className="btn-secondary" onClick={onClose}>
              Cancel
            </button>
            <button
              type="submit"
              className="btn-primary"
              disabled={uploading}
            >
              {uploading
                ? "Uploading..."
                : product
                ? "Update Product"
                : "Create Product"}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ProductForm;