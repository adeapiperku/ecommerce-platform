package com.ecommerce_platform.service.impl;

import com.ecommerce_platform.dto.ProductDto;
import com.ecommerce_platform.dto.Response;
import com.ecommerce_platform.model.Category;
import com.ecommerce_platform.model.Product;
import com.ecommerce_platform.exception.NotFoundException;
import com.ecommerce_platform.mapper.ModelDtoMapper;
import com.ecommerce_platform.repository.CategoryRepository;
import com.ecommerce_platform.repository.ProductRepository;
import com.ecommerce_platform.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelDtoMapper modelDtoMapper;

    @Override
    public Response createProduct(Long categoryId, String imageUrl, String name, String description, BigDecimal price) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        // Now the image URL is passed directly as a parameter
        String productImageUrl = imageUrl != null ? imageUrl : "default-image-url";  // Use default if imageUrl is null

        Product product = new Product();
        product.setCategory(category);
        product.setPrice(price);
        product.setName(name);
        product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepository.save(product);
        return Response.builder()
                .status(200)
                .message("Product successfully created")
                .build();
    }

    @Override
    public Response updateProduct(Long productId, Long categoryId, String imageUrl, String name, String description, BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        Category category = null;
        String productImageUrl = imageUrl != null ? imageUrl : product.getImageUrl();  // Keep the old image if new one is not provided

        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("Category not found"));
        }

        if (category != null) product.setCategory(category);
        if (name != null) product.setName(name);
        if (price != null) product.setPrice(price);
        if (description != null) product.setDescription(description);
        product.setImageUrl(productImageUrl);

        productRepository.save(product);
        return Response.builder()
                .status(200)
                .message("Product updated successfully")
                .build();
    }

    @Override
    public Response deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        productRepository.delete(product);

        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    @Override
    public Response getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));
        ProductDto productDto = modelDtoMapper.mapProductToDtoBasic(product);

        return Response.builder()
                .status(200)
                .product(productDto)
                .build();
    }

    @Override
    public Response getAllProducts() {
        List<ProductDto> productList = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .stream()
                .map(modelDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productList)
                .build();
    }

    @Override
    public Response getProductsByCategory(Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        if (products.isEmpty()) {
            throw new NotFoundException("No Products found for this category");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(modelDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }

    @Override
    public Response searchProduct(String searchValue) {
        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(searchValue, searchValue);

        if (products.isEmpty()) {
            throw new NotFoundException("No Products Found");
        }
        List<ProductDto> productDtoList = products.stream()
                .map(modelDtoMapper::mapProductToDtoBasic)
                .collect(Collectors.toList());

        return Response.builder()
                .status(200)
                .productList(productDtoList)
                .build();
    }
}
