package com.Bitirme.Finish2.services;

import java.util.*;
import java.util.stream.Collectors;

import com.Bitirme.Finish2.entities.Category;
import com.Bitirme.Finish2.repository.CategoryRepository;
import com.Bitirme.Finish2.requests.CategoryCreateRequest;
import com.Bitirme.Finish2.responses.CategoryResponse;
import com.Bitirme.Finish2.responses.ProductResponse2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.Bitirme.Finish2.entities.Product;
import com.Bitirme.Finish2.entities.User;
import com.Bitirme.Finish2.repository.ProductRepository;
import com.Bitirme.Finish2.requests.ProductCreateRequest;
import com.Bitirme.Finish2.requests.ProductUpdateRequest;
import com.Bitirme.Finish2.responses.LikeResponse;
import com.Bitirme.Finish2.responses.ProductResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private UserService userService; //ürün eklerken kontrol yapmak için kullanıcı servisinden yapacağız
    private LikeService likeService;
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    public ProductService(ProductRepository productRepository, UserService userService,
                          CategoryRepository categoryRepository,CategoryService categoryService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @Autowired
    public void setLikeService(LikeService likeService) {
        this.likeService = likeService;
    }
    public List<ProductResponse2> getAllProducts(Optional<Long> userId) { //controllerdan parse eder jpa ile tüm ürünleri çeker
        List<Product> list; //product listesini database den çekiyoruz

        if(userId.isPresent()) { //eğer userid geldiyse ürünlerini repodan çek
            list = productRepository.findByUserId(userId.get());
        }else
            list = productRepository.findAll();
        return list.stream().map(p -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(
                    Optional.ofNullable(null), Optional.of(p.getId())); //her bir ürün için o ürünün likelarını çekiyoruz

            return new ProductResponse2(p, likes);}).collect(Collectors.toList()); //product listesini productresponse listesine mapliyoruz

    } //ve productresponse listesini dönüyoruz

    //find product by category
        public Product getOneProductById(Long productId) { //path'e göre ürün çekme
        return productRepository.findById(productId).orElse(null); //jpa'nın id ye
    }


    public ProductResponse getOneProductByIdWithLikes(Long productId) { //ürün beğenileri...
        Product product = productRepository.findById(productId).orElse(null);
        List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null), Optional.of(productId));
        return new ProductResponse(product, likes);
    }

    public Product createOneProduct(ProductCreateRequest newProductRequest/*,String categories*/) {
        User user = userService.getOneUserById(newProductRequest.getUserId()); //user kontrolü yapıp post ekleyeceğiz

        if(user == null)
            return null;
        Product toSave = new Product();
        //Category cate = new Category();

        toSave.setId(newProductRequest.getId());
        toSave.setText(newProductRequest.getText());
        toSave.setTitle(newProductRequest.getTitle());
        toSave.setPrice(newProductRequest.getPrice());
        toSave.setImage(newProductRequest.getImage());
        toSave = addCategoriesToProduct(toSave,newProductRequest.getCategories());
        /*cate.setCategoryName(newProductRequest.getCategoryName());
        categoryRepository.save(cate);*/

        toSave.setUser(user);
        toSave.setCreateDate(new Date()); //ürün eklenme zamanı

        //catReq.setCategoryName(newProductRequest.getCategoryName());
        return productRepository.save(toSave);

        //repoya gidip yeni post ekleyecek
    }
    private Product addCategoriesToProduct(Product toSave, String categories) { //kategori ekleme metodu
        String [] cates = categories.split(",");
        Category category = null;
        for (String str : cates) {
            category = categoryRepository.findById(Long.parseLong(str)).get();
            toSave.getCategories().add(category);

        }
        return toSave;
    }

    public Product updateOneProductById(Long productId, ProductUpdateRequest updateProduct) { //var olan bir ürünü güncellemek
        Optional<Product> product = productRepository.findById(productId); //ürün id kontrolü yapıyoruz
        if(product.isPresent()) {
            Product toUpdate = product.get();
            toUpdate.setText(updateProduct.getText());
            toUpdate.setTitle(updateProduct.getTitle());
            productRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deleteOneProductById(Long productId) {
        productRepository.deleteById(productId); //ürün silme
    }

    public Category saveCategory(CategoryCreateRequest categoryCreateRequest) {
        Category categorySave = new Category();
        categorySave.setCategoryName(categoryCreateRequest.getCategoryName());
        categoryRepository.save(categorySave);
        return categorySave;
    }

    public List<Product> getCate() {
        return productRepository.findAll();
    }
}
