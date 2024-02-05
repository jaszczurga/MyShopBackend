package com.ecommerce.myshop.service;

import com.ecommerce.myshop.Exceptions.CategoryNameExistsException;
import com.ecommerce.myshop.Exceptions.NoCategoryIdFoundInDbException;
import com.ecommerce.myshop.dao.ProductCategoryRepository;
import com.ecommerce.myshop.dao.ProductRepository;
import com.ecommerce.myshop.dataTranferObject.CategoryDto;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.NoSuchElementException;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    @Transactional
    public Product saveProduct(ProductDto receivedProduct) {

        //create product from received product
        Product product = createProductFromDTO(receivedProduct);
        try{
            //assign category to product with condition if category id is null if not null assign category to product
            //if category id is null create new category and assign it to product
            assignCategoryToProduct(receivedProduct, product);
            return productRepository.save(product);
        }catch (NoSuchElementException e){
            throw new NoCategoryIdFoundInDbException( "Category id not found in database. Error message: " + e.getMessage());
        } catch (Exception e){
            throw new CategoryNameExistsException("Category name already exists. Error message: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductCategory saveCategory(CategoryDto receivedProduct) {
        ProductCategory productCategory = new ProductCategory();
        try{
            productCategory.setCategoryName(receivedProduct.getCategoryName());
            return productCategoryRepository.save(productCategory);
        }catch (Exception e){
            throw new CategoryNameExistsException("Category name already exists. Error message: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteProduct(Long productId) {
        try{
            Optional<Product> optionalProduct = productRepository.findById(productId);
            productRepository.deleteById(productId);
            return ResponseEntity.ok("Product " +optionalProduct.get().getProductName()+" deleted successfully");
        }catch (Exception e){
            throw new NoSuchElementException("No such element found in database. Error message: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteCategory(Long categoryId) {
        try{
            Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(categoryId);
            productCategoryRepository.deleteById(categoryId);
            return ResponseEntity.ok("Category " +optionalProductCategory.get().getCategoryName()+" deleted successfully");
        }catch (Exception e){
            throw new NoSuchElementException("No such element found in database. Error message: " + e.getMessage());
        }
    }


    @Override
    @Transactional
    public Product updateProduct(ProductDto receivedProduct , Long productId) {

        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();

        try{
            //try to find by id if not found throw exception
            product = productRepository.findById(productId).get();
        }catch (Exception e){
            throw new NoSuchElementException("No such a product element found in database. Error message: " + e.getMessage());
        }

        try{
            //try to find by id if not found throw exception
            productCategory = productCategoryRepository.findById((long) receivedProduct.getCategory().getId()).get();
        }catch (Exception e){
            throw new NoSuchElementException("No such a category element found in database. Error message: " + e.getMessage());
        }

        //update product from received product
        product = createProductFromDTO(receivedProduct);
        product.setId(productId);
        product.setCategory( productCategory );
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public ProductCategory updateCategory(CategoryDto receivedCategory , Long categoryId) {

        ProductCategory productCategory = new ProductCategory();
        productCategory = productCategoryRepository.findById(categoryId).get();

        try{
            productCategory.setCategoryName( receivedCategory.getCategoryName() );
            return productCategoryRepository.save(productCategory);
        }catch (NoSuchElementException e){
            throw new NoSuchElementException("No such category element found in database. Error message: " + e.getMessage());
        }
        //catch (Exception e){
            //TODO: there is some SQLExeption that is not catched by this catch block try to repair it in the future
            // problem disappers when we try to save ProductCategory without assigned id
           // throw new CategoryNameExistsException("Category name already exists. Error message: " );
        //}
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductCategory> getAllCategories(Pageable pageable) {
        return productCategoryRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getProductsContainingName(String name , Pageable pageable) {
        return productRepository.findByProductNameContaining(name, pageable);
    }

    @Override
    public Page<Product> getProductsByCategoryId(Long categoryId , Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }


    private Product createProductFromDTO(ProductDto receivedProduct) {
        Product product = new Product();
        product.setProductName(receivedProduct.getProductName());
        product.setProductDescription(receivedProduct.getProductDescription());
        product.setProductPrice(receivedProduct.getProductPrice());
        product.setProductImage(receivedProduct.getProductImage());
        product.setProductStockQuantity(receivedProduct.getProductStockQuantity());
        return product;
    }

    private void assignCategoryToProduct(ProductDto receivedProduct, Product product) {
        if (receivedProduct.getCategory().getId() == null) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setCategoryName(receivedProduct.getCategory().getCategoryName());
            product.setCategory(productCategory);
        } else {
            product.setCategory(productCategoryRepository.findById((long) receivedProduct.getCategory().getId()).get());
        }
    }

}
