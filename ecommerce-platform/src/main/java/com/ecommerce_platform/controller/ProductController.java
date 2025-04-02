package com.ecommerce_platform.controller;

import com.ecommerce_platform.dto.Response;
import com.ecommerce_platform.exception.InvalidCredentialsException;
import com.ecommerce_platform.service.interf.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> createProduct(
            @RequestParam Long categoryId,
            @RequestParam(required = false) String imageUrl,  // Now you accept imageUrl as a string parameter
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam BigDecimal price
    ){
      if (categoryId == null || name.isEmpty() || description.isEmpty() || price == null){
          throw new InvalidCredentialsException("All Fields are Required");
      }
      // Use imageUrl from the request body
      return ResponseEntity.ok(productService.createProduct(categoryId, imageUrl, name, description, price));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateProduct(
            @RequestParam Long productId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String imageUrl,  // Now you accept imageUrl as a string parameter
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) BigDecimal price
    ){
        return ResponseEntity.ok(productService.updateProduct(productId, categoryId, imageUrl, name, description, price));
    }

    @DeleteMapping("/delete/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @GetMapping("/get-by-product-id/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable Long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Response> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get-by-category-id/{categoryId}")
    public ResponseEntity<Response> getProductsByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<Response> searchForProduct(@RequestParam String searchValue){
        return ResponseEntity.ok(productService.searchProduct(searchValue));
    }
}
