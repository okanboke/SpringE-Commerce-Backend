package com.Bitirme.Finish2.controllers;

import java.util.List;
import java.util.Optional;

import com.Bitirme.Finish2.entities.Category;
import com.Bitirme.Finish2.requests.CategoryCreateRequest;
import com.Bitirme.Finish2.responses.CategoryResponse;
import com.Bitirme.Finish2.responses.ProductResponse2;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.Bitirme.Finish2.entities.Product;
import com.Bitirme.Finish2.requests.ProductCreateRequest;
import com.Bitirme.Finish2.requests.ProductUpdateRequest;
import com.Bitirme.Finish2.responses.ProductResponse;
import com.Bitirme.Finish2.services.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/category")
    public List<Product> getCate() {
        return productService.getCate();
    }

    @GetMapping
    public List<ProductResponse2> getAllProducts(
            @RequestParam Optional<Long> userId) { //requestparam ile userların tüm ürünleri çekme virgül koyarak birden fazla parse edebiliriz
        return productService.getAllProducts(userId);
    }
    @PostMapping
    public Product createOneProduct(
            @RequestBody ProductCreateRequest newProductRequest
            /*,
            @ModelAttribute String categories*/) { //yeni bir ürün ekleme
        return productService.createOneProduct(newProductRequest/*,categories*/);
    }

    @GetMapping("/{productId}")
    public ProductResponse getOneProduct(@PathVariable Long productId) {
        return productService.getOneProductByIdWithLikes(productId);
    }

    @PutMapping("/{productId}")
    public Product updateOneProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest updateProduct) { //var olan bir ürünü güncellemek
        return productService.updateOneProductById(productId, updateProduct);
    }
    @DeleteMapping("/{productId}")
    public void deleteOneProduct(@PathVariable Long productId) { //ürün silme işlemi
        productService.deleteOneProductById(productId);

    }
    @PostMapping("/categoryAdd")
    public Category addCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) {
        return productService.saveCategory(categoryCreateRequest);

    }
}
