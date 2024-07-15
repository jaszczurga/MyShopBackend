package com.ecommerce.myshop.service;

import com.ecommerce.myshop.Exceptions.ConflictException;
import com.ecommerce.myshop.Exceptions.IllegalArgumentException;
import com.ecommerce.myshop.Exceptions.NotFoundException;
import com.ecommerce.myshop.dao.ImageRepository;
import com.ecommerce.myshop.dao.ProductCategoryRepository;
import com.ecommerce.myshop.dao.ProductRepository;
import com.ecommerce.myshop.dataTranferObject.CategoryDto;
import com.ecommerce.myshop.dataTranferObject.ImageDto;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.ImageModel;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private ProductCategoryRepository productCategoryRepository;

    private ImageRepository imageRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.imageRepository = imageRepository;
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

            Product product1=  productRepository.save(product);
            product.getImages().forEach(image -> image.setProduct(product1));

            return productRepository.save(product);
        }catch (NotFoundException e){
            throw new NotFoundException( "Category id not found in database. Error MessageDto: " + e.getMessage());
        }catch (ConflictException e){
            throw new ConflictException("Category name already exists. Error MessageDto: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductCategory saveCategory(CategoryDto receivedProduct) {
        ProductCategory productCategory = new ProductCategory();
        try{
            productCategory.setCategoryName(receivedProduct.getCategoryName());
            return productCategoryRepository.save(productCategory);
        }catch (ConflictException e){
            throw new ConflictException("Category name already exists. Error MessageDto: " + e.getMessage());
        }
    }

 @Override
@Transactional
public ResponseEntity<Product> deleteProduct(Long productId) {
    if(productId == null) throw new IllegalArgumentException( "Product id is null");
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (!optionalProduct.isPresent()) {
        throw new NotFoundException( "No such element found in database.");
    }
    productRepository.deleteById(productId);
    return ResponseEntity.ok(optionalProduct.get());
}

  @Override
@Transactional
public ResponseEntity<ProductCategory> deleteCategory(Long categoryId) {
        if(categoryId == null) throw new IllegalArgumentException( "Category id is null");
    Optional<ProductCategory> optionalProductCategory = productCategoryRepository.findById(categoryId);
    if (!optionalProductCategory.isPresent()) {
        throw new NotFoundException( "No such element found in database.");
    }
    productCategoryRepository.deleteById(categoryId);
    return ResponseEntity.ok(optionalProductCategory.get());
}


    @Override
    @Transactional
    public Product updateProduct(ProductDto receivedProduct , Long productId) {
        if(productId == null) throw new IllegalArgumentException( "Product id is null");
        if(receivedProduct.getCategory().getId() == null) throw new IllegalArgumentException( "Category id is null");

        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();

            //try to find by id if not found throw exception
            product = productRepository.findById(productId).orElseThrow(
                    () -> new NotFoundException("No such a product element found in database.")
            );

            //try to find by id if not found throw exception
            productCategory = productCategoryRepository.findById((long) receivedProduct.getCategory().getId()).orElseThrow(
                    () -> new NotFoundException("No such a category element found in database."));

        //update product from received product
        product = createProductFromDTO(receivedProduct);
        product.setCategory( productCategory );
        product.setId(productId);

        Product finalProduct = product;
        receivedProduct.getImages().forEach( image -> image.setProduct( finalProduct ));
        product.setImages( receivedProduct.getImages() );
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public ProductCategory updateCategory(CategoryDto receivedCategory , Long categoryId) {
        if(categoryId == null) throw new IllegalArgumentException( "Category id is null");

        ProductCategory productCategory = new ProductCategory();
        productCategory = productCategoryRepository.findById(categoryId).get();

        try{
            productCategory.setCategoryName( receivedCategory.getCategoryName() );
            return productCategoryRepository.save(productCategory);
        }catch (NotFoundException e){
            throw new NotFoundException("No such category element found in database. Error MessageDto: " + e.getMessage());
        }
        catch (ConflictException e){
            throw new ConflictException("Category name already exists. Error MessageDto: " );
        }
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
        if(categoryId == null) throw new IllegalArgumentException( "Category id is null");
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Product getProductById(Long productId) {
        if(productId == null) throw new IllegalArgumentException( "Product id is null");
        return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("No such element found in database."));
    }

    @Override
    public Page<ImageDto> getImagesByProductId(Long productId, Pageable pageable) {
        if(productId == null) throw new IllegalArgumentException( "Product id is null");

        Page<ImageModel> imageModel = imageRepository.findByProductId(productId, pageable);
        //create imageDto
        Page<ImageDto> imageDtoPage = imageModel.map(image -> {
            ImageDto imageDto = new ImageDto();
            imageDto.setId(image.getId());
            imageDto.setName(image.getName());
            imageDto.setType(image.getType());
            imageDto.setPicByte(image.getPicByte());
            return imageDto;
        });

        return imageDtoPage;
    }

    @Override
    public ResponseEntity<ImageModel> deleteImage(Long imageId) {
        if(imageId == null) throw new IllegalArgumentException( "Image id is null");

        try{
            Optional<ImageModel> optionalImageModel = imageRepository.findById(imageId);
            imageRepository.deleteById(imageId);
            return ResponseEntity.ok(optionalImageModel.get());
        }catch (NotFoundException e){
            throw new NotFoundException("No such element found in database. Error MessageDto: " + e.getMessage());
        }
    }




    private Product createProductFromDTO(ProductDto receivedProduct) {
        Product product = new Product();
        product.setProductName(receivedProduct.getProductName());
        product.setProductDescription(receivedProduct.getProductDescription());
        product.setProductPrice(receivedProduct.getProductPrice());
        product.setImages( receivedProduct.getImages() );
        product.setProductStockQuantity(receivedProduct.getProductStockQuantity());

        return product;
    }

    private void assignCategoryToProduct(ProductDto receivedProduct, Product product) {
        if (receivedProduct.getCategory().getId() == null && productCategoryRepository.findByCategoryName( receivedProduct.getCategory().getCategoryName())==null) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setCategoryName(receivedProduct.getCategory().getCategoryName());
            product.setCategory(productCategory);
        } else {
            if(productCategoryRepository.findByCategoryName( receivedProduct.getCategory().getCategoryName())==null) {
                product.setCategory( productCategoryRepository.findById( (long) receivedProduct.getCategory().getId() ).get() );
            }else {
                product.setCategory( productCategoryRepository.findByCategoryName( receivedProduct.getCategory().getCategoryName() ) );
            }
        }
    }

}
