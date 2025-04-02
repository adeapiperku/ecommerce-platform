package com.ecommerce_platform.service.interf;

import com.ecommerce_platform.dto.CategoryDto;
import com.ecommerce_platform.dto.Response;

public interface CategoryService {

    Response createCategory(CategoryDto categoryRequest);
    Response updateCategory(Long categoryId, CategoryDto categoryRequest);
    Response getAllCategories();
    Response getCategoryById(Long categoryId);
    Response deleteCategory(Long categoryId);
}
