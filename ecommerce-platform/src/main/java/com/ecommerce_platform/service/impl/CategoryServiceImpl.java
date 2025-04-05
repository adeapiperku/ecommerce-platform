package com.ecommerce_platform.service.impl;

import com.ecommerce_platform.dto.CategoryDto;
import com.ecommerce_platform.dto.Response;
import com.ecommerce_platform.model.Category;
import com.ecommerce_platform.exception.NotFoundException;
import com.ecommerce_platform.mapper.ModelDtoMapper;
import com.ecommerce_platform.repository.CategoryRepository;
import com.ecommerce_platform.service.interf.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelDtoMapper modelDtoMapper;


    @Override
    public Response createCategory(CategoryDto categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response updateCategory(Long categoryId, CategoryDto categoryRequest) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return Response.builder()
                .status(200)
                .message("category updated successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(modelDtoMapper::mapCategoryToDtoBasic)
                .collect(Collectors.toList());

        return  Response.builder()
                .status(200)
                .categoryList(categoryDtoList)
                .build();
    }

    @Override
    public Response getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        CategoryDto categoryDto = modelDtoMapper.mapCategoryToDtoBasic(category);
        return Response.builder()
                .status(200)
                .category(categoryDto)
                .build();
    }

    @Override
    public Response deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category Not Found"));
        categoryRepository.delete(category);
        return Response.builder()
                .status(200)
                .message("Category was deleted successfully")
                .build();
    }
}
