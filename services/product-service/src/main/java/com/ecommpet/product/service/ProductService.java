package com.ecommpet.product.service;

import com.ecommpet.product.dto.ProductPurchaseRequest;
import com.ecommpet.product.dto.ProductPurchaseResponse;
import com.ecommpet.product.dto.ProductRequest;
import com.ecommpet.product.dto.ProductResponse;
import com.ecommpet.product.entity.Product;
import com.ecommpet.product.exception.ProductPurchaseException;
import com.ecommpet.product.mapper.ProductMapper;
import com.ecommpet.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        List<Integer> productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size() ){
            throw new ProductPurchaseException("1 or more products not exists");
        }

        List<ProductPurchaseRequest> storesRequest = request.stream().
                sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        ArrayList<ProductPurchaseResponse> purchaseProducts =  new ArrayList<>();
        for (int i = 0; i < purchaseProducts.size(); i++) {
            Product product = storedProducts.get(i);
            ProductPurchaseRequest productRequest = storesRequest.get(i);
            if (product.getAvailableQuantity()<productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with id :"+productRequest.productId());
            }
            double newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchaseProducts.add(productMapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }
        return purchaseProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId).map(productMapper::toProductResponse).
                orElseThrow(()-> new EntityNotFoundException("Product not found with id : "+productId));
    }

    public List<ProductResponse> findAll() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
