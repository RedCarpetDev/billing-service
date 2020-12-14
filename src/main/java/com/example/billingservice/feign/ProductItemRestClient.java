package com.example.billingservice.feign;

import com.example.billingservice.models.Customer;
import com.example.billingservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.QueryParam;

@FeignClient(name = "INVENTORY-SERVICE")
public interface ProductItemRestClient {

    @GetMapping(path = "/products")
    PagedModel<Product> pageProducts(); // @RequestParam(value = "page") int page, @RequestParam(value = "size") int size
    @GetMapping(path = "products/{id}")
    Product getProductId(@PathVariable(name = "id") Long id);
}
