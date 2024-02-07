package com.ecommerce.myshop.controller;

import com.ecommerce.myshop.dataTranferObject.CategoryDto;
import com.ecommerce.myshop.dataTranferObject.ProductDto;
import com.ecommerce.myshop.entity.ImageModel;
import com.ecommerce.myshop.entity.Product;
import com.ecommerce.myshop.entity.ProductCategory;
import com.ecommerce.myshop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping ("/api/action")
@CrossOrigin(origins = "http://localhost:4200")
public class MyShopController {

    ProductService productService;

    public MyShopController(ProductService productService) {
        this.productService = productService;
    }

    //endpoint to get all products
    @GetMapping ("/products")
    Page<Product> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    //endpoint to get all categories
    @GetMapping ("/categories")
    Page<ProductCategory> getAllCategories(Pageable pageable) {
        return productService.getAllCategories(pageable);
    }

    //endpoint to get products containing name
    @GetMapping ("/productsContainingName")
    Page<Product> getProductsContainingName(@RequestParam String name, Pageable pageable) {
        return productService.getProductsContainingName(name, pageable);
    }

    //endpoint to get products by category id
    @GetMapping ("/productsByCategoryId")
    Page<Product> getProductsByCategoryId(@RequestParam Long categoryId, Pageable pageable) {
        return productService.getProductsByCategoryId(categoryId, pageable);
    }


    //endpoint to save product
     @PostMapping ("/saveProduct")
     public Product saveProduct(@RequestBody ProductDto receivedProduct) {
         return productService.saveProduct(receivedProduct);
     }

     //endpoint to save category
     @PostMapping ("/saveCategory")
     public ProductCategory saveCategory(@RequestBody CategoryDto receivedCategory) {
        return productService.saveCategory(receivedCategory);
    }

     //endpoint to delete product
    @DeleteMapping ("/deleteProduct/{productId}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    //endpoint to delete category
    @DeleteMapping ("/deleteCategory/{categoryId}")
    public ResponseEntity<ProductCategory> deleteCategory(@PathVariable Long categoryId) {
        return productService.deleteCategory(categoryId);
    }

    //endpoint to update product
    @PatchMapping ("/updateProduct/{productId}")
    public Product updateProduct(@RequestBody ProductDto receivedProduct, @PathVariable Long productId) {
          return productService.updateProduct(receivedProduct, productId);
    }

    //endpoint to update category
    @PatchMapping ("/updateCategory/{categoryId}")
    public ProductCategory updateCategory(@RequestBody CategoryDto receivedCategory, @PathVariable Long categoryId) {
          return productService.updateCategory(receivedCategory, categoryId);
    }

    //endpoint to get product by id
    @GetMapping ("/product/{productId}")
    public Product getProductById(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }


    @PostMapping("/upload/{productId}")
    public void uplaodImage(@RequestParam("imageFile") MultipartFile file,@PathVariable Long productId) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ImageModel img = new ImageModel(
                file.getOriginalFilename(),
                file.getContentType(),
                compressBytes(file.getBytes()),
                productId
        );
        productService.saveImage(img);
        //return ResponseEntity.status(HttpStatus.OK);
    }

//    @GetMapping(path = { "/get/{imageName}" })
//    public ImageModel getImage(@PathVariable("imageName") String imageName) throws IOException {
//        final Optional<ImageModel> retrievedImage = productService.getImageByName(imageName);
//        ImageModel img = new ImageModel(
//                retrievedImage.get().getName(),
//                retrievedImage.get().getType(),
//                decompressBytes(retrievedImage.get().getPicByte())
//                retrievedImage.get().getProductId()
//        );
//        return img;
//    }


    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

}


