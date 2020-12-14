package com.example.billingservice;

import com.example.billingservice.entities.Bill;
import com.example.billingservice.entities.ProductItem;
import com.example.billingservice.feign.CustomerRestClient;
import com.example.billingservice.feign.ProductItemRestClient;
import com.example.billingservice.models.Customer;
import com.example.billingservice.models.Product;
import com.example.billingservice.repositories.BillRepository;
import com.example.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient){
        return args -> {
            Customer customer = customerRestClient.getCustomerById(1L);
            // pour ajouter une facture
            Bill bill= billRepository.save(new Bill(null,new Date(),null,customer.getId(),null));
            PagedModel<Product> productPagedModel = productItemRestClient.pageProducts();
            productPagedModel.forEach(p->{
                ProductItem productItem = new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1 + new Random().nextInt(100)); //nombre aleatoire entre 1 et 101
                productItem.setBill(bill);
                productItem.setProductID(p.getId());
                productItemRepository.save(productItem);
            });
           /* System.out.println("-----------------------------");
            System.out.println(customer.getId());
            System.out.println(customer.getName());
            System.out.println(customer.getEmail());*/
        };
    }

}
