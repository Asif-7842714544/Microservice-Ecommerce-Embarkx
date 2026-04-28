package com.ecommerce.product.reporsitory;

import com.ecommerce.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();

    @Query("select p from product p where p.active=true and p.stockQuantity>0 and lower(p.name) like lower(concat('%',:keyword,'%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    Optional<Product> findByProductIdAndActiveTrue(Long productId);
}
